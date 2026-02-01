package com.svalero.saludate.presenter;

import com.svalero.saludate.contract.UserContract;
import com.svalero.saludate.model.UserModel;

public class UserPresenter implements UserContract.Presenter {

    //region Properties

    private final UserContract.View view;
    private final UserContract.Model model;

    //endregion

    //region Constructor

    public UserPresenter(UserContract.View view) {
        this.view = view;
        this.model = new UserModel();
    }

    //endregion

    //region Override from contract

    @Override
    public void signOut() {
        model.signOut();
        view.goToLoginActivity();
    }

    //endregion
}
