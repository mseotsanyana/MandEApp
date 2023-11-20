package com.me.mseotsanyana.mande.application.interactors.monitoring.Impl;

import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.interactors.monitoring.iUploadMonitoringInteractor;
import com.me.mseotsanyana.mande.application.repository.monitor.iUploadMonitoringRepository;
import com.me.mseotsanyana.mande.application.structures.IResponseDTO;

public class cUploadMonitoringInteractorImpl extends CAbstractInteractor<IResponseDTO<Object>>
        implements iUploadMonitoringInteractor {
    private Callback callback;
    private iUploadMonitoringRepository uploadMonitoringRepository;

    public cUploadMonitoringInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                           iUploadMonitoringRepository uploadMonitoringRepository,
                                           Callback callback) {
        super(threadExecutor, mainThread, null);



        if (uploadMonitoringRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.uploadMonitoringRepository = uploadMonitoringRepository;
        this.callback = callback;
    }

    /* notify on the main thread */
    private void notifyError(String msg){
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUploadMonitoringCompleted(msg);
            }
        });
    }

    /* notify on the main thread */
    private void postMessage(String msg){
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUploadMonitoringCompleted(msg);
            }
        });
    }

    @Override
    public void run() {
        /* delete all monitoring module records */
        uploadMonitoringRepository.deleteMethods();
        uploadMonitoringRepository.deleteMOVs();
        uploadMonitoringRepository.deleteDataSources();
        uploadMonitoringRepository.deleteQuantitativeTypes();
        uploadMonitoringRepository.deleteIndicatorTypes();
        uploadMonitoringRepository.deleteCriteriaScores();
        uploadMonitoringRepository.deleteQualitativeCriteria();
        uploadMonitoringRepository.deleteQualitativeSets();
        uploadMonitoringRepository.deleteQualitativeScoreSets();

        uploadMonitoringRepository.deleteTargets();
        uploadMonitoringRepository.deleteQualitativeTargets();
        uploadMonitoringRepository.deleteQuantitativeTargets();
        uploadMonitoringRepository.deleteArrayTargets();
        uploadMonitoringRepository.deleteMatrixTargets();
        uploadMonitoringRepository.deleteDataCollectors();
        uploadMonitoringRepository.deleteIndicators();
        uploadMonitoringRepository.deleteQualitativeIndicators();
        uploadMonitoringRepository.deleteQuantitativeIndicators();
        uploadMonitoringRepository.deleteArrayIndicators();
        uploadMonitoringRepository.deleteMatrixIndicators();

        uploadMonitoringRepository.deleteMilestones();
        uploadMonitoringRepository.deleteIndicatorMilestones();

        uploadMonitoringRepository.deleteQuantitativeMilestone();

        /* upload all monitoring module records */
        if(uploadMonitoringRepository.addMethodFromExcel()){
            postMessage("Method Entity Added Successfully!");
        }else {
            notifyError("Failed to Add Method Entity");
        }

        if(uploadMonitoringRepository.addMOVsFromExcel()){
            postMessage("MOV Entity Added Successfully!");
        }else {
            notifyError("Failed to Add MOV Entity");
        }

        if(uploadMonitoringRepository.addDataSourceFromExcel()){
            postMessage("Data Source Entity Added Successfully!");
        }else {
            notifyError("Failed to Add Data Source Entity");
        }

        if(uploadMonitoringRepository.addQuantitativeTypeFromExcel()){
            postMessage("Quantitative Type Entity Added Successfully!");
        }else {
            notifyError("Failed to Add Quantitative Type Entity");
        }

        if(uploadMonitoringRepository.addIndicatorTypeFromExcel()){
            postMessage("Indicator Type Entity Added Successfully!");
        }else {
            notifyError("Failed to Add Indicator Type Entity");
        }

        if(uploadMonitoringRepository.addCriteriaScoreFromExcel()){
            postMessage("Criteria Score Entity Added Successfully!");
        }else {
            notifyError("Failed to Add Criteria Score Entity");
        }

        if(uploadMonitoringRepository.addQualitativeSetFromExcel()){
            postMessage("Qualitative Set Entity Added Successfully!");
        }else {
            notifyError("Failed to Add Qualitative Set Entity");
        }

        if(uploadMonitoringRepository.addDataCollectorFromExcel()){
            postMessage("Data Collector Entity Added Successfully!");
        }else {
            notifyError("Failed to Add Data Collector Entity");
        }

        /*if(uploadMonitoringRepository.addTargetFromExcel()){
            postMessage("Target Entity Added Successfully!");
        }else {
            notifyError("Failed to Add Target Entity");
        }*/

        if(uploadMonitoringRepository.addIndicatorFromExcel()){
            postMessage("Indicator Entity Added Successfully!");
        }else {
            notifyError("Failed to Add Indicator Entity");
        }
/*
        if(uploadMonitoringRepository.addMilestoneFromExcel()){
            postMessage("Milestone Entity Added Successfully!");
        }else {
            notifyError("Failed to Add Milestone Entity");
        }

        if(uploadMonitoringRepository.addProgressFromExcel()){
            postMessage("Progress Entity Added Successfully!");
        }else {
            notifyError("Failed to Add Progress Entity");
        }


        if(uploadMonitoringRepository.addQualitativeCriteriaFromExcel()){
            postMessage("Qualitative Criteria Entity Added Successfully!");
        }else {
            notifyError("Failed to Add Qualitative Criteria Entity");
        }
*/
    }

    @Override
    public void postResult(IResponseDTO resultMap) {

    }

    @Override
    public void postError(String errorMessage) {

    }
}
