package com.me.mseotsanyana.mande.application.interactors.wpb.Impl;

import com.me.mseotsanyana.mande.application.ports.base.executor.IExecutor;
import com.me.mseotsanyana.mande.application.ports.base.executor.IMainThread;
import com.me.mseotsanyana.mande.application.interactors.wpb.iUploadAWPBInteractor;
import com.me.mseotsanyana.mande.application.ports.base.CAbstractInteractor;
import com.me.mseotsanyana.mande.application.repository.awpb.iUploadAWPBRepository;
import com.me.mseotsanyana.mande.application.structures.IResponseDTO;

public class cUploadAWPBInteractorImpl extends CAbstractInteractor<IResponseDTO>
        implements iUploadAWPBInteractor {
    private Callback callback;
    private iUploadAWPBRepository uploadAWPBRepository;

    public cUploadAWPBInteractorImpl(IExecutor threadExecutor, IMainThread mainThread,
                                     iUploadAWPBRepository uploadAWPBRepository,
                                     Callback callback) {
        super(threadExecutor, mainThread, null);



        if (uploadAWPBRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.uploadAWPBRepository = uploadAWPBRepository;
        this.callback = callback;
    }

    /* notify on the main thread */
    private void notifyError(String msg){
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUploadAWPBCompleted(msg);
            }
        });
    }

    /* notify on the main thread */
    private void postMessage(String msg){
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUploadAWPBCompleted(msg);
            }
        });
    }


    @Override
    public void run() {

        /* upload all AWPB module records */

        uploadAWPBRepository.deleteTasks();
        uploadAWPBRepository.deleteActivityTasks();
        uploadAWPBRepository.deletePrecedingTasks();
        uploadAWPBRepository.deleteTaskMilestones();
        uploadAWPBRepository.deleteTaskAssignments();
        uploadAWPBRepository.deleteTimesheets();
        uploadAWPBRepository.deleteUserComments();
        uploadAWPBRepository.deleteUserComments();
        uploadAWPBRepository.deleteDocuments();
        uploadAWPBRepository.deleteInvoices();
        uploadAWPBRepository.deleteInvoiceTimesheet();
        uploadAWPBRepository.deleteInternals();
        uploadAWPBRepository.deleteExternals();
        uploadAWPBRepository.deleteTransactions();
        uploadAWPBRepository.deleteJournals();

        /* upload all AWPB module records */

        if(uploadAWPBRepository.addTaskFromExcel()){
            postMessage("Task Entity Added Successfully!");
        }else {
            notifyError("Failed to Add Task Entity");
        }

        if(uploadAWPBRepository.addDocumentFromExcel()){
            postMessage("Document Entity Added Successfully!");
        }else {
            notifyError("Failed to Add Document Entity");
        }

        if(uploadAWPBRepository.addInvoiceFromExcel()){
            postMessage("Invoice Entity Added Successfully!");
        }else {
            notifyError("Failed to Add Invoice Entity");
        }

        if(uploadAWPBRepository.addTransactionFromExcel()){
            postMessage("Transaction Entity Added Successfully!");
        }else {
            notifyError("Failed to Add Transaction Entity");
        }


        if(uploadAWPBRepository.addJournalFromExcel()){
            postMessage("Journal Entity Added Successfully!");
        }else {
            notifyError("Failed to Add Journal Entity");
        }
    }

    @Override
    public void postResult(IResponseDTO resultMap) {

    }

    @Override
    public void postError(String errorMessage) {

    }
}
