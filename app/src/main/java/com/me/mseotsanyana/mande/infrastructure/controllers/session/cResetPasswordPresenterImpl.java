package com.me.mseotsanyana.mande.interfaceadapters.controllers.session;

import com.me.mseotsanyana.mande.domain.entities.models.session.CUserProfileModel;
import com.me.mseotsanyana.mande.usecases.executor.iExecutor;
import com.me.mseotsanyana.mande.usecases.executor.iMainThread;
import com.me.mseotsanyana.mande.usecases.interactors.session.userprofile.Impl.cResetPasswordInteractorImpl;
import com.me.mseotsanyana.mande.usecases.interactors.session.userprofile.iResetPasswordInteractor;
import com.me.mseotsanyana.mande.usecases.repository.session.iUserProfileRepository;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.session.iResetPasswordPresenter;

public class cResetPasswordPresenterImpl extends cAbstractPresenter
        implements iResetPasswordPresenter, iResetPasswordInteractor.Callback{
    //private static String TAG = cResetPasswordPresenterImpl.class.getSimpleName();

    private View view;
    private final iUserProfileRepository userProfileRepository;

    public cResetPasswordPresenterImpl(iExecutor executor, iMainThread mainThread,
                                       View view,
                                       iUserProfileRepository userProfileRepository) {
        super(executor, mainThread);

        this.view = view;
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public void sendPasswordResetEmail(CUserProfileModel userProfileModel) {
        iResetPasswordInteractor resetPasswordInteractor = new cResetPasswordInteractorImpl(
                executor,
                mainThread,this,
                userProfileRepository,
                userProfileModel);

        view.showProgress();

        resetPasswordInteractor.execute();
    }

    @Override
    public void onResetPasswordSucceeded(String msg) {
        if(this.view != null) {
            this.view.onResetPasswordSucceeded(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onResetPasswordFailed(String msg) {
        if(this.view != null) {
            this.view.onResetPasswordFailed(msg);
            this.view.hideProgress();
        }
    }

    /*============================= General Presenter methods ============================= */

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {
        if(this.view != null){
            this.view.hideProgress();
        }
    }

    @Override
    public void destroy() {
        this.view = null;
    }

    @Override
    public void onError(String message) {

    }

}
