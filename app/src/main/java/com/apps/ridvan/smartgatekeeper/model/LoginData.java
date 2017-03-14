package com.apps.ridvan.smartgatekeeper.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ridvan on 13/03/2017.
 */

public class LoginData implements Parcelable {

    private String login;
    private String password;

    public LoginData() {
    }

    protected LoginData(Parcel in) {
        this.login = in.readString();
        this.password = in.readString();
    }


    public static final Creator<LoginData> CREATOR = new Creator<LoginData>() {
        @Override
        public LoginData createFromParcel(Parcel in) {
            return new LoginData(in);
        }

        @Override
        public LoginData[] newArray(int size) {
            return new LoginData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.login);
        dest.writeString(this.password);
    }

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
}
