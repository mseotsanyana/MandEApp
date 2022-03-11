package com.me.mseotsanyana.mande.PL.presenters.session;

import com.me.mseotsanyana.mande.BLL.model.session.cStakeholderModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;

import java.util.List;

public interface iStakeholderPresenter extends iPresenter {
    interface View extends iBaseView {
        void onClickCreateStakeholder();

        void onCreateStakeholderFailed(String msg);
        void onCreateStakeholderSucceeded(String msg);
        void onReadStakeholdersFailed(String msg);
        void onReadStakeholdersSucceeded(cStakeholderModel stakeholderModel, String operation);

        void onReadStakeholderMembersFailed(String msg);
        void onReadStakeholderMembersSucceeded(List<cUserProfileModel> userProfileModels);

        /*EditText getNameEditText();
        EditText getEmailEditText();
        EditText getWebsiteEditText();

        String getResourceString(int resourceID);*/
    }

    /* implemented in PresenterImpl to link ui with InteractorImpl */
    void createStakeholder(cStakeholderModel stakeholderModel);

    void readStakeholders();
    void readStakeholderMembers();

    void removeListener();
}

