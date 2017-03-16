package com.apps.ridvan.smartgatekeeper.model;

/**
 * Created by Ridvan on 15/03/2017.
 */


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FunctionListData implements Parcelable {

    private List<Function> functions = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public List<Function> getFunctions() {
        return functions;
    }

    public void setFunctions(List<Function> functions) {
        this.functions = functions;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.functions);
        dest.writeInt(this.additionalProperties.size());
        for (Map.Entry<String, Object> entry : this.additionalProperties.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeParcelable((Parcelable) entry.getValue(), flags);
        }
    }

    public FunctionListData() {
    }

    protected FunctionListData(Parcel in) {
        this.functions = new ArrayList<Function>();
        in.readList(this.functions, Function.class.getClassLoader());
        int additionalPropertiesSize = in.readInt();
        this.additionalProperties = new HashMap<String, Object>(additionalPropertiesSize);
        for (int i = 0; i < additionalPropertiesSize; i++) {
            String key = in.readString();
            Object value = in.readParcelable(Object.class.getClassLoader());
            this.additionalProperties.put(key, value);
        }
    }

    public static final Parcelable.Creator<FunctionListData> CREATOR = new Parcelable.Creator<FunctionListData>() {
        @Override
        public FunctionListData createFromParcel(Parcel source) {
            return new FunctionListData(source);
        }

        @Override
        public FunctionListData[] newArray(int size) {
            return new FunctionListData[size];
        }
    };
}