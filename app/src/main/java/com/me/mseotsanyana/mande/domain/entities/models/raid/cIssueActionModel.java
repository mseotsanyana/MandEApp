package com.me.mseotsanyana.mande.domain.entities.models.raid;

public class cIssueActionModel extends cActionModel {
    private cIssueReviewModel issueReviewModel;

    public cIssueReviewModel getIssueReviewModel() {
        return issueReviewModel;
    }

    public void setIssueReviewModel(cIssueReviewModel issueReviewModel) {
        this.issueReviewModel = issueReviewModel;
    }
}