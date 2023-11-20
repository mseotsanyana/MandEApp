package com.me.mseotsanyana.mande.infrastructure.utils.responsemodel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mseotsanyana on 2017/02/24.
 */

public class Object implements Parcelable {
    @INodeChildID
    private String childID;
    @INodeParentID
    private String parentID;

    private int type;
    private java.lang.Object modelObject;

    public Object(String childID, String parentID, int type, java.lang.Object object) {
        this.childID  = childID;
        this.parentID = parentID;
        this.type     = type;
        this.modelObject = object;
    }

    public Object(java.lang.Object object) {
        this.modelObject = object;
    }

    protected Object(Parcel in) {
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

    public static final Creator<Object> CREATOR = new Creator<Object>() {
        @Override
        public Object createFromParcel(Parcel in) {
            return new Object(in);
        }

        @Override
        public Object[] newArray(int size) {
            return new Object[size];
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

    public java.lang.Object getModelObject() {
        return modelObject;
    }

    public void setModelObject(java.lang.Object modelObject) {
        this.modelObject = modelObject;
    }

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
