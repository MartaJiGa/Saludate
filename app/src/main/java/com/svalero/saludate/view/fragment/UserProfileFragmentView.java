package com.svalero.saludate.view.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseUser;
import com.svalero.saludate.R;
import com.svalero.saludate.contract.UserProfileContract;
import com.svalero.saludate.domain.UserData;
import com.svalero.saludate.presenter.UserProfilePresenter;
import com.svalero.saludate.util.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class UserProfileFragmentView extends Fragment implements UserProfileContract.View {

    //region Properties

    private View view;
    private UserProfilePresenter presenter;
    private TextInputEditText edtName;
    private TextInputEditText edtSurname;
    private TextInputEditText edtEmail;
    private TextInputEditText edtPassword;
    private TextInputEditText edtNewPassword;
    private TextInputEditText edtConfirmNewPassword;
    private TextInputEditText edtBirthDate;
    private Calendar selectedDate;
    private SimpleDateFormat dateFormatter;
    private FirebaseUser firebaseUser;
    private UserData userData;
    private UserData newUserData;
    private Spinner sexSpinner;
    private int selectedSexPosition;
    private Button btnSave;
    private int completedTasks;
    private List<String> errorList;

    //endregion

    //region Lifecycle

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        this.presenter = new UserProfilePresenter(this, this.getContext());
        firebaseUser = presenter.getUser();

        userData = new UserData(firebaseUser.getUid());
        presenter.getUserData(firebaseUser);

        initializeValues();

        return view;
    }

    //endregion

    //region Override from contract

    @Override
    public void clearInputs() {
        edtPassword.setText("");
        edtNewPassword.setText("");
        edtConfirmNewPassword.setText("");
    }

    @Override
    public void setDataInControls(UserData retrievedUserData) {
        userData = retrievedUserData;

        setValuesInEditText();
        setBirthDate(userData.getBirthdate());
        setValueInSexSpinner();
    }

    @Override
    public void savedUserDataSuccess() {
        completedTasks++;
        checkIfTasksAreCompleted();
    }

    @Override
    public void savedUserDataError(String message) {
        errorList.add(R.string.error_saving_user_info + ": " + message);
        checkIfTasksAreCompleted();
    }

    @Override
    public void updateEmailSuccess() {
        completedTasks++;
        checkIfTasksAreCompleted();
    }

    @Override
    public void updateEmailError(String message) {
        errorList.add(R.string.error_saving_user_info + ": " + message);
        checkIfTasksAreCompleted();
    }

    @Override
    public void showError(String message) {
        errorList.add(R.string.error_retrieving_user_info + ": " + message);
        checkIfTasksAreCompleted();
    }

    //endregion

    //region Methods

    private void initializeValues(){
        initializeEditText();
        initializeSpinner();
        initializeButtonListeners();

        completedTasks = 0;
        errorList = new ArrayList<>();
    }

    private void initializeEditText(){
        edtName = view.findViewById(R.id.edt_user_profile_name);
        edtSurname = view.findViewById(R.id.edt_user_profile_surname);
        edtEmail = view.findViewById(R.id.edt_user_profile_email);
        edtPassword = view.findViewById(R.id.edt_user_profile_password);
        edtNewPassword = view.findViewById(R.id.edt_user_profile_new_password);
        edtConfirmNewPassword = view.findViewById(R.id.edt_user_profile_confirm_new_password);
        edtBirthDate = view.findViewById(R.id.edt_birthdate);

        selectedDate = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        edtBirthDate.setOnClickListener(v -> showDatePickerDialog());
    }

    private void setValuesInEditText(){
        edtName.setText(userData.getName());
        edtSurname.setText(userData.getSurname());
        edtEmail.setText(firebaseUser.getEmail());
    }

    private void setValueInSexSpinner(){
        switch (userData.getSex()){
            case Constants.FEMALE:
                sexSpinner.setSelection(1);
                break;
            case Constants.MALE:
                sexSpinner.setSelection(2);
                break;
            case Constants.INTERSEX:
                sexSpinner.setSelection(3);
                break;
            default:
                sexSpinner.setSelection(0);
        }
    }

    private void initializeSpinner(){
        sexSpinner = view.findViewById(R.id.sex_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this.requireContext(),
                R.array.sex_array,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexSpinner.setAdapter(adapter);

        sexSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSexPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    public void initializeButtonListeners(){
        btnSave = view.findViewById(R.id.btn_user_profile_save);

        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                saveUserData();
            }
        });
    }

    public void saveUserData(){
        String email, password, newPassword, confirmNewPassword;
        email = String.valueOf(edtEmail.getText());
        password = String.valueOf(edtPassword.getText());
        newPassword = String.valueOf(edtNewPassword.getText());
        confirmNewPassword = String.valueOf(edtConfirmNewPassword.getText());

        if(email.isEmpty()){
            Toast.makeText(this.getContext(), R.string.error_empty_email, Toast.LENGTH_SHORT).show();
            return;
        }

        newUserData = new UserData();

        newUserData.setFirebaseUserId(firebaseUser.getUid());
        newUserData.setName(String.valueOf(edtName.getText()));
        newUserData.setSurname(String.valueOf(edtSurname.getText()));
        newUserData.setSex(selectedSexPosition);
        newUserData.setBirthdate(getBirthDate());

        if(checkAtLeastOneUserDataDoesNotMatch()){
            presenter.updateUser(userData);
        }

        if(!Objects.requireNonNull(firebaseUser.getEmail()).equalsIgnoreCase(email)){
            presenter.updateUserEmail(email);
        }

        if(!user.getPassword().equals(password)){
            Toast.makeText(this.getContext(), R.string.error_password, Toast.LENGTH_SHORT).show();
        } else if(!newPassword.equals(confirmNewPassword)){
            Toast.makeText(this.getContext(), R.string.error_passwords_comparison, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkAtLeastOneUserDataDoesNotMatch(){
        boolean mustUpdateUserData = false;

        if(!userData.getName().equalsIgnoreCase(newUserData.getName())){
            mustUpdateUserData = true;
        }
        if(!userData.getSurname().equalsIgnoreCase(newUserData.getSurname())){
            mustUpdateUserData = true;
        }
        if(!userData.getBirthdate().equalsIgnoreCase(newUserData.getBirthdate())){
            mustUpdateUserData = true;
        }
        if(userData.getSex() != newUserData.getSex()){
            mustUpdateUserData = true;
        }

        return mustUpdateUserData;
    }

    private void showDatePickerDialog() {
        int year = selectedDate.get(Calendar.YEAR);
        int month = selectedDate.get(Calendar.MONTH);
        int day = selectedDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    selectedDate.set(selectedYear, selectedMonth, selectedDay);
                    edtBirthDate.setText(dateFormatter.format(selectedDate.getTime()));
                },
                year, month, day
        );

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        Calendar minDate = Calendar.getInstance();
        minDate.add(Calendar.YEAR, -150);
        datePickerDialog.getDatePicker().setMinDate(minDate.getTimeInMillis());

        datePickerDialog.show();
    }

    private String getBirthDate() {
        return edtBirthDate.getText().toString();
    }

    private void setBirthDate(String dateString) {
        if (dateString != null && !dateString.isEmpty()) {
            edtBirthDate.setText(dateString);
            try {
                selectedDate.setTime(dateFormatter.parse(dateString));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            edtBirthDate.setText(dateFormatter.format(selectedDate.getTime()));
        }
    }

    private void showSaveMessageSuccess(){
        Toast.makeText(this.getContext(), R.string.success_saving_user, Toast.LENGTH_SHORT).show();
    }

    private void showSaveMessageError(){
        String errorMessage = "";
        for (int i = 0; i < errorList.size(); i++) {
            if(i > 0){
                errorMessage += "\n";
            }
            errorMessage += errorList.get(i);
        }
        new AlertDialog.Builder(this.getContext())
                .setTitle(R.string.errors_while_saving)
                .setMessage(errorMessage)
                .setPositiveButton(R.string.ok, null)
                .show();
        Toast.makeText(this.getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    private void checkIfTasksAreCompleted(){
        if(completedTasks == Constants.USER_PROFILE_TASKS){
            if(errorList.size() > 0){
                showSaveMessageError();
            }
            else{
                showSaveMessageSuccess();
            }
        }
    }

    //endregion
}