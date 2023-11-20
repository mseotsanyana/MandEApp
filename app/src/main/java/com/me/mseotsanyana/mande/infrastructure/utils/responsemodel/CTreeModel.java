package com.me.mseotsanyana.mande.infrastructure.utils.responsemodel;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * Created by mseotsanyana on 2017/02/24.
 */

public class CTreeModel implements Parcelable {
    @INodeChildID
    private String childID;
    @INodeParentID
    private String parentID;

    private int type;
    private Object modelObject;

    public CTreeModel(String childID, String parentID, int type, Object object) {
        this.childID  = childID;
        this.parentID = parentID;
        this.type     = type;
        this.modelObject = object;
    }

    public CTreeModel(Object object) {
        this.modelObject = object;
    }

    protected CTreeModel(Parcel in) {
        childID = in.readString();
        parentID = in.readString();
        type = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(childID);
        dest.writeString(parentID);
        dest.writeInt(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CTreeModel> CREATOR = new Creator<CTreeModel>() {
        @Override
        public CTreeModel createFromParcel(Parcel in) {
            return new CTreeModel(in);
        }

        @Override
        public CTreeModel[] newArray(int size) {
            return new CTreeModel[size];
        }
    };

    public String getChildID() {
        return childID;
    }

    public void setChildID(String childID) {
        this.childID = childID;
    }

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getModelObject() {
        return modelObject;
    }

    public void setModelObject(Object modelObject) {
        this.modelObject = modelObject;
    }

    @NonNull
    @Override
    public String toString() {
        return "CTreeModel{" +
                "childID='" + childID + '\'' +
                ", parentID='" + parentID + '\'' +
                ", type=" + type +
                ", modelObject=" + modelObject +
                '}';
    }
}
