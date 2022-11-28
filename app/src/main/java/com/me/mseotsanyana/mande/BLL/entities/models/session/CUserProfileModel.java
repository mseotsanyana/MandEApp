package com.me.mseotsanyana.mande.BLL.entities.models.session;

import com.me.mseotsanyana.mande.BLL.entities.interfaces.IUserProfileState;

import java.util.Date;

public class CUserProfileModel {
   IUserProfileState activeState;

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

    public CUserProfileModel() {

    }

   public CUserProfileModel(String email, String password){
       this.setEmail(email);
       this.setPassword(password);
       setActiveState(new CSignInModel(this));
   }

    public CUserProfileModel(String email){
        this.setEmail(email);
        setActiveState(new CResetPasswordModel(this));
    }

    public CUserProfileModel(byte[] imageData, String name, String surname,
                             String designation, String email, String password) {
        this.imageData = imageData;
        this.name = name;
        this.surname = surname;
        this.designation = designation;
        this.email = email;
        this.password = password;
    }

    public CUserProfileModel(String userServerID, String photoUri, String name, String surname,
                             String designation, String email, String password){
        this.userServerID = userServerID;
        this.photoUrl = photoUri;
        this.name = name;
        this.surname = surname;
        this.designation = designation;
        this.email = email;
        this.password = password;
    }

    public IUserProfileState getActiveState() {
        return activeState;
    }

    public void setActiveState(IUserProfileState activeState) {
        this.activeState = activeState;
    }

    void activateEvent(){
       activeState.activateEvent();
    }

    void signInEvent(String email, String password){
        activeState.signInEvent(email, password);
    }

    void signUpEvent(){
       activeState.signUpEvent();
    }
    void cancelEvent(){
       activeState.cancelEvent();
    }
    void submitEvent(){
       activeState.submitEvent();
    }
    void resetPasswordEvent(){
       activeState.resetPasswordEvent();
    }
    void changePasswordEvent(){
       activeState.changePasswordEvent();
    }
    void logoutEvent(){
       activeState.logoutEvent();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
}
