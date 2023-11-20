package com.me.mseotsanyana.mande.domain.entities.models.wpb;

import android.os.Parcel;
import android.os.Parcelable;

import com.me.mseotsanyana.mande.domain.entities.models.logframe.cInputModel;
import com.me.mseotsanyana.mande.domain.entities.models.session.CFunderModel;

import java.util.HashSet;
import java.util.Set;

public class cIncomeModel extends cInputModel implements Parcelable {
    /* information from table fund */
    private Set<CFunderModel> funderModelSet;

    public cIncomeModel(){
        funderModelSet = new HashSet<>();
    }

    protected cIncomeModel(Parcel in) {
    }

    public static final Creator<cIncomeModel> CREATOR = new Creator<cIncomeModel>() {
        @Override
        public cIncomeModel createFromParcel(Parcel in) {
            return new cIncomeModel(in);
        }

        @Override
        public cIncomeModel[] newArray(int size) {
            return new cIncomeModel[size];
        }
    };

    public Set<CFunderModel> getFunderModelSet() {
        return funderModelSet;
    }

    public void setFunderModelSet(Set<CFunderModel> funderModelSet) {
        this.funderModelSet = funderModelSet;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
