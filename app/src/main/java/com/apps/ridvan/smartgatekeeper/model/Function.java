package com.apps.ridvan.smartgatekeeper.model;

/**
 * Created by Ridvan on 15/03/2017.
 */

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class Function implements Parcelable {

    private int id;
    private String name;
    private int type;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.type);
        dest.writeInt(this.additionalProperties.size());
        for (Map.Entry<String, Object> entry : this.additionalProperties.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeParcelable((Parcelable) entry.getValue(), flags);
        }
    }

    public Function() {
    }

    protected Function(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.type = in.readInt();
        int additionalPropertiesSize = in.readInt();
        this.additionalProperties = new HashMap<String, Object>(additionalPropertiesSize);
        for (int i = 0; i < additionalPropertiesSize; i++) {
            String key = in.readString();
            Object value = in.readParcelable(Object.class.getClassLoader());
            this.additionalProperties.put(key, value);
        }
    }

    public static final Parcelable.Creator<Function> CREATOR = new Parcelable.Creator<Function>() {
        @Override
        public Function createFromParcel(Parcel source) {
            return new Function(source);
        }

        @Override
        public Function[] newArray(int size) {
            return new Function[size];
        }
    };
}