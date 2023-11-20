package com.me.mseotsanyana.mande.PL.presenters.session;

import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;

import java.util.List;
import java.util.Map;

public interface iOrganizationAccountPresenter extends iPresenter {
    interface View extends iBaseView {
        void onReadOrganizationAccountsFailed(String msg);
        void onReadOrganizationAccountsSucceeded(Map<String, Object> orgAccountsMap,
                                                 String operation);

        /*EditText getNameEditText();
        EditText getEmailEditText();
        EditText getWebsiteEditText();

        String getResourceString(int resourceID);*/
    }

    void readOrganizationAccounts();
}

