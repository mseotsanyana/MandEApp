package com.me.mseotsanyana.mande.domain.entities.models.session;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.Exclude;

import java.util.Date;

public class cUserProfileModel_old implements Parcelable {
    private String userServerID;

    private String userOwnerID;

    private String photoUrl;
    private byte[] imageData;

    private String name;
    private String surname;
    private String designation;
    private String email;
    private String password;
    private String confirmPassword;

    private String phone;
    private String website;
    private String location;

    private Date createdDate;
    private Date modifiedDate;

    public cUserProfileModel_old(byte[] imageData, String name, String surname,
                                 String designation, String email, String password) {
        this.imageData = imageData;
        this.name = name;
        this.surname = surname;
        this.designation = designation;
        this.email = email;
        this.password = password;
    }

    public cUserProfileModel_old(String userServerID, String photoUri, String name, String surname,
                                 String designation, String email, String password){
        this.userServerID = userServerID;
        this.photoUrl = photoUri;
        this.name = name;
        this.surname = surname;
        this.designation = designation;
        this.email = email;
        this.password = password;
    }

    public cUserProfileModel_old() {

    }

    public String getUserServerID() {
        return userServerID;
    }

    public void setUserServerID(String userServerID) {
        this.userServerID = userServerID;
    }

    public String getUserOwnerID() {
        return userOwnerID;
    }

    public void setUserOwnerID(String userOwnerID) {
        this.userOwnerID = userOwnerID;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Exclude
    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Exclude
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Exclude
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected cUserProfileModel_old(Parcel in) {
        userServerID = in.readString();
        photoUrl = in.readString();
        name = in.readString();
        surname = in.readString();
        designation = in.readString();
        location = in.readString();
        email = in.readString();
        website = in.readString();
        phone = in.readString();
    }

    public static final Creator<cUserProfileModel_old> CREATOR = new Creator<cUserProfileModel_old>() {
        @Override
        public cUserProfileModel_old createFromParcel(Parcel in) {
            return new cUserProfileModel_old(in);
        }

        @Override
        public cUserProfileModel_old[] newArray(int size) {
            return new cUserProfileModel_old[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userServerID);
        parcel.writeString(photoUrl);
        parcel.writeString(name);
        parcel.writeString(surname);
        parcel.writeString(designation);
        parcel.writeString(location);
        parcel.writeString(email);
        parcel.writeString(website);
        parcel.writeString(phone);
    }
}
