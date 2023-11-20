package com.me.mseotsanyana.mande.domain.entities.models.session;

import android.os.Parcel;
import android.os.Parcelable;

public class cAgreementModel implements Parcelable {
    protected cAgreementModel(Parcel in) {
    }

    public static final Creator<cAgreementModel> CREATOR = new Creator<cAgreementModel>() {
        @Override
        public cAgreementModel createFromParcel(Parcel in) {
            return new cAgreementModel(in);
        }

        @Override
        public cAgreementModel[] newArray(int size) {
            return new cAgreementModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
