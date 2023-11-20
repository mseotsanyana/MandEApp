package com.me.mseotsanyana.mande.infrastructure.ports.session;

import com.me.mseotsanyana.mande.OLD.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.infrastructure.ports.base.IBaseView;

import java.util.Map;

public interface iOrganizationAccountPresenter extends iPresenter {
    interface View extends IBaseView {
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

