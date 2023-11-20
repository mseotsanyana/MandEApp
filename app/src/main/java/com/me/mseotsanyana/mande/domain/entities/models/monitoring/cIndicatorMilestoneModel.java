package com.me.mseotsanyana.mande.domain.entities.models.monitoring;

import com.me.mseotsanyana.mande.domain.entities.models.wpb.cMilestoneModel;

public class cIndicatorMilestoneModel extends cMilestoneModel {
    private cIndicatorModel  indicatorModel;

    public cIndicatorModel getIndicatorModel() {
        return indicatorModel;
    }

    public void setIndicatorModel(cIndicatorModel indicatorModel) {
        this.indicatorModel = indicatorModel;
    }
}
