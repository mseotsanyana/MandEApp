package com.me.mseotsanyana.mande.application.interactors.shared.Impl;

import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.interactors.shared.iUploadGlobalInteractor;
import com.me.mseotsanyana.mande.application.repository.preference.iUploadGlobalRepository;
import com.me.mseotsanyana.mande.application.structures.IResponseDTO;

public class cUploadGlobalInteractorImpl extends CAbstractInteractor<IResponseDTO<Object>>
        implements iUploadGlobalInteractor {
    private Callback callback;
    private iUploadGlobalRepository uploadGlobalRepository;

    public cUploadGlobalInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                       iUploadGlobalRepository uploadGlobalRepository,
                                       Callback callback) {
        super(threadExecutor, mainThread, null);



        if (uploadGlobalRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.uploadGlobalRepository = uploadGlobalRepository;
        this.callback = callback;
    }

    /* notify on the main thread */
    private void notifyError(String msg){
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUploadGlobalCompleted(msg);
            }
        });
    }

    /* notify on the main thread */
    private void postMessage(String msg){
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUploadGlobalCompleted(msg);
            }
        });
    }

    @Override
    public void run() {
        /* delete all global module records */

        uploadGlobalRepository.deleteFrequencies();
        uploadGlobalRepository.deletePeriods();
        uploadGlobalRepository.deleteFiscalYears();
        uploadGlobalRepository.deleteCharts();

        /* upload all global module records */

        if(uploadGlobalRepository.addFrequencyFromExcel()){
            postMessage("Frequency Entity Added Successfully!");
        }else {
            notifyError("Failed to Add Frequency Entity");
        }

        if(uploadGlobalRepository.addFiscalYearFromExcel()){
            postMessage("FiscalYear Entity Added Successfully!");
        }else {
            notifyError("Failed to Add FiscalYear Entity");
        }

        if(uploadGlobalRepository.addChartFromExcel()){
            postMessage("Chart Entity Added Successfully!");
        }else {
            notifyError("Failed to Add Chart Entity");
        }
    }

    @Override
    public void postResult(IResponseDTO resultMap) {

    }

    @Override
    public void postError(String errorMessage) {

    }
}
