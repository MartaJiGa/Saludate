package com.svalero.saludate.contract;

public interface UserContract {
    interface Model {
        void signOut();
    }

    interface View {
        void goToLoginActivity();
    }

    interface Presenter {
        void signOut();
    }
}
