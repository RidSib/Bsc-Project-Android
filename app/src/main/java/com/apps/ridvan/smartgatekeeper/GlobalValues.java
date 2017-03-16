package com.apps.ridvan.smartgatekeeper;

import android.app.Application;

import com.apps.ridvan.smartgatekeeper.model.FunctionListData;

/**
 * Created by Ridvan on 15/03/2017.
 */

public class GlobalValues extends Application {

    private FunctionListData functionList;
    private String url;
    private String login;
    private String password;

    public FunctionListData getFunctionList() {
        return functionList;
    }

    public void setFunctionList(FunctionListData functionList) {
        this.functionList = functionList;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
