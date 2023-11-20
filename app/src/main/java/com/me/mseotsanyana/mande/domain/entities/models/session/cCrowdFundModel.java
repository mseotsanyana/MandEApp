package com.me.mseotsanyana.mande.domain.entities.models.session;

import com.me.mseotsanyana.mande.domain.entities.models.wpb.cIncomeModel;

public class cCrowdFundModel {
    private long incomeID;
    private long funderID;
    private long beneficiaryID;
    private double amount;

    private cIncomeModel incomeModel;
    private CFunderModel funderModel;
    private CBeneficiaryModel beneficiaryModel;


    public long getIncomeID() {
        return incomeID;
    }

    public void setIncomeID(long incomeID) {
        this.incomeID = incomeID;
    }

    public long getFunderID() {
        return funderID;
    }

    public void setFunderID(long funderID) {
        this.funderID = funderID;
    }

    public long getBeneficiaryID() {
        return beneficiaryID;
    }

    public void setBeneficiaryID(long beneficiaryID) {
        this.beneficiaryID = beneficiaryID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public cIncomeModel getIncomeModel() {
        return incomeModel;
    }

    public void setIncomeModel(cIncomeModel incomeModel) {
        this.incomeModel = incomeModel;
    }

    public CFunderModel getFunderModel() {
        return funderModel;
    }

    public void setFunderModel(CFunderModel funderModel) {
        this.funderModel = funderModel;
    }

    public CBeneficiaryModel getBeneficiaryModel() {
        return beneficiaryModel;
    }

    public void setBeneficiaryModel(CBeneficiaryModel beneficiaryModel) {
        this.beneficiaryModel = beneficiaryModel;
    }
}
