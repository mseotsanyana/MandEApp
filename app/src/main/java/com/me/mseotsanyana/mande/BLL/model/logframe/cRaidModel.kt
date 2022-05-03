package com.me.mseotsanyana.mande.BLL.model.logframe

import com.me.mseotsanyana.mande.BLL.model.wpb.cHumanSetModel
import com.me.mseotsanyana.mande.BLL.model.common.cFrequencyModel
import com.me.mseotsanyana.mande.BLL.model.raid.cRAIDLikelihoodModel
import com.me.mseotsanyana.mande.BLL.model.raid.cRAIDImpactModel
import com.me.mseotsanyana.mande.BLL.model.raid.cRobotModel
import java.util.*

open class cRaidModel {
    var raidID: Long = 0
    var serverID: Long = 0
    var ownerID: Long = 0
    var orgID: Long = 0
    var groupBITS = 0
    var permsBITS = 0
    var statusBITS = 0
    var score = 0
    var name: String? = null
    var description: String? = null
    var status: String? = null
    var startDate: Date? = null
    var endDate: Date? = null
    var createdDate: Date? = null
    var modifiedDate: Date? = null
    var syncedDate: Date? = null
    var logFrameModel: cLogFrameModel
    var raidCategoryModel: cRaidCategoryModel
    var originatorModel: cHumanSetModel
    var ownerModel: cHumanSetModel
    var frequencyModel: cFrequencyModel
    var raidLikelihoodModel: cRAIDLikelihoodModel
    var raidImpactModel: cRAIDImpactModel
    var robotModel: cRobotModel

    init {
        logFrameModel = cLogFrameModel()
        raidCategoryModel = cRaidCategoryModel()
        originatorModel = cHumanSetModel()
        ownerModel = cHumanSetModel()
        frequencyModel = cFrequencyModel()
        raidLikelihoodModel = cRAIDLikelihoodModel()
        raidImpactModel = cRAIDImpactModel()
        robotModel = cRobotModel()
    }
}