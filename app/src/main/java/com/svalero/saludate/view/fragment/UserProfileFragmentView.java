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
    private TextInputEditText edtCurrentPassword;
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
    private int userProfileTasks;

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

        userProfileTasks++;
        userData = new UserData(firebaseUser.getUid());
        presenter.getUserData(firebaseUser);

        initializeValues();

        return view;
    }

    //endregion

    //region Override from contract

    @Override
    public void clearInputs() {
        edtCurrentPassword.setText("");
        edtNewPassword.setText("");
        edtConfirmNewPassword.setText("");
    }

    @Override
    public void setDataInControls(UserData retrievedUserData) {
        if(retrievedUserData == null) {
            edtEmail.setText(firebaseUser.getEmail());
            return;
        }

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
        completedTasks++;
        errorList.add(getString(R.string.error_saving_user_info) + ": " + message);
        checkIfTasksAreCompleted();
    }

    @Override
    public void updatePasswordSuccess() {
        completedTasks++;
        checkIfTasksAreCompleted();
    }

    @Override
    public void updatePasswordError(String message) {
        completedTasks++;
        errorList.add(getString(R.string.error_saving_user_info) + ": " + message);
        checkIfTasksAreCompleted();
    }

    @Override
    public void showError(String message) {
        errorList.add(getString(R.string.error_retrieving_user_info) + ": " + message);
        checkIfTasksAreCompleted();
    }

    //endregion

    //region Methods

    private void initializeValues(){
        initializeEditText();
        initializeSpinner();
        initializeButtonListeners();

        initializeTasks();
        errorList = new ArrayList<>();
    }

    private void initializeEditText(){
        edtName = view.findViewById(R.id.edt_user_profile_name);
        edtSurname = view.findViewById(R.id.edt_user_profile_surname);
        edtEmail = view.findViewById(R.id.edt_user_profile_email);
        edtCurrentPassword = view.findViewById(R.id.edt_user_profile_current_password);
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
        initializeTasks();

        String email, currentPassword, newPassword, confirmNewPassword;
        email = String.valueOf(edtEmail.getText()).trim();
        currentPassword = String.valueOf(edtCurrentPassword.getText()).trim();
        newPassword = String.valueOf(edtNewPassword.getText()).trim();
        confirmNewPassword = String.valueOf(edtConfirmNewPassword.getText()).trim();

        newUserData = new UserData();

        newUserData.setFirebaseUserId(firebaseUser.getUid());
        newUserData.setName(String.valueOf(edtName.getText()).trim());
        newUserData.setSurname(String.valueOf(edtSurname.getText()).trim());
        newUserData.setSex(selectedSexPosition);
        newUserData.setBirthdate(getBirthDate());

        boolean isUserDataUpdate = false, isPasswordUpdate = false;

        if(checkAtLeastOneUserDataDoesNotMatch()){
            isUserDataUpdate = true;
            userProfileTasks++;
        }

        if(!newPassword.isEmpty() && newPassword.equals(confirmNewPassword)){
            isPasswordUpdate = true;
            userProfileTasks++;
        } else if(!newPassword.equals(confirmNewPassword)) {
            Toast.makeText(this.getContext(), R.string.error_passwords_comparison, Toast.LENGTH_SHORT).show();
        }

        if(isUserDataUpdate || isPasswordUpdate)
            updateUserInfo(isUserDataUpdate, isPasswordUpdate, currentPassword, newPassword);
        else
            Toast.makeText(this.getContext(), R.string.no_changes_saving_user_info, Toast.LENGTH_SHORT).show();
    }

    private boolean checkAtLeastOneUserDataDoesNotMatch(){
        boolean mustUpdateUserData = false;

        if((userData.getName() == null && !newUserData.getName().equals("")) ||
                (userData.getName() != null && !userData.getName().equalsIgnoreCase(newUserData.getName())))
            mustUpdateUserData = true;

        if((userData.getSurname() == null && !newUserData.getSurname().equals("")) ||
                (userData.getSurname() != null && !userData.getSurname().equalsIgnoreCase(newUserData.getSurname())))
            mustUpdateUserData = true;

        if((userData.getBirthdate() == null && !newUserData.getBirthdate().equals("")) ||
                (userData.getBirthdate() != null && !userData.getBirthdate().equalsIgnoreCase(newUserData.getBirthdate())))
            mustUpdateUserData = true;

        if(userData.getSex() != newUserData.getSex())
            mustUpdateUserData = true;

        return mustUpdateUserData;
    }

    private void updateUserInfo(boolean isUserDataUpdate, boolean isPasswordUpdate, String currentPassword, String newPassword){
        if(isUserDataUpdate)
            presenter.updateUser(newUserData);

        if(isPasswordUpdate)
            presenter.updateUserPassword(currentPassword, newPassword);
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
        else edtBirthDate.setText(dateFormatter.format(selectedDate.getTime()));
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
    }

    private void checkIfTasksAreCompleted(){
        if(completedTasks == userProfileTasks){
            if(errorList.size() > 0)
                showSaveMessageError();
            else
                showSaveMessageSuccess();
        }
    }

    private void initializeTasks(){
        errorList = new ArrayList<>();
        userProfileTasks = 0;
        completedTasks = 0;
    }

    //endregion
}