package com.apps.ridvan.smartgatekeeper.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ridvan on 14/03/2017.
 */

public class FunctionActivateDate implements Parcelable {

    private String login;
    private String password;
    private int function;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getFunction() {
        return function;
    }

    public void setFunction(int function) {
        this.function = function;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.login);
        dest.writeString(this.password);
        dest.writeInt(this.function);
    }

    public FunctionActivateDate() {
    }

    protected FunctionActivateDate(Parcel in) {
        this.login = in.readString();
        this.password = in.readString();
        this.function = in.readInt();
    }

    public static final Parcelable.Creator<FunctionActivateDate> CREATOR = new Parcelable.Creator<FunctionActivateDate>() {
        @Override
        public FunctionActivateDate createFromParcel(Parcel source) {
            return new FunctionActivateDate(source);
        }

        @Override
        public FunctionActivateDate[] newArray(int size) {
            return new FunctionActivateDate[size];
        }
    };
}