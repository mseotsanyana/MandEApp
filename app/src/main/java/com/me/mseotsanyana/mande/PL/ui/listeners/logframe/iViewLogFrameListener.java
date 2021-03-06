package com.me.mseotsanyana.mande.PL.ui.listeners.logframe;

import android.util.SparseBooleanArray;

import com.me.mseotsanyana.mande.BLL.model.logframe.cLogFrameModel;

import java.util.List;
import java.util.Map;

import kotlin.Pair;

public interface iViewLogFrameListener {
    void onClickBMBLogFrame(int index, cLogFrameModel logFrameModel);
    void onClickCreateSubLogFrame(String logFrameID, cLogFrameModel logFrameModel);
    void onClickUpdateLogFrame(int position, cLogFrameModel logFrameModel);
    void onClickDeleteLogFrame(String logframeID);
    void onLongClickLogFrame(SparseBooleanArray selectedItems);
    List<String> onGetLogframeServerIDs();
    List<String> onGetComponentServerIDs();
}
