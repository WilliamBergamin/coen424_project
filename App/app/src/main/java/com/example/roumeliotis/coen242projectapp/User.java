package com.example.roumeliotis.coen242projectapp;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable{

    private long remote_id = -1;     //ID in server database
    private String name = null;     // Name of the user
    private String email = null;    // Email of the user
    private String password = null; // Password of the user

    public User(long remote_id, String name, String email, String password) {
        this.remote_id = remote_id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    protected User(Parcel in) {
        remote_id = in.readLong();
        name = in.readString();
        email = in.readString();
        password = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };


    // Get and set remoteID
    public long getRemote_id() {
        return remote_id;
    }
    public void setRemote_id(long remote_id) {
        this.remote_id = remote_id;
    }

    // Get and set name
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // Get and set email
    public String getEmail(){ return email; }
    public void setEmail(String email){ this.email = email; }

    // Get and set password
    public String getPassword(){ return password; }
    public void setPassword(String password){ this.password = password; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(remote_id);
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeString(password);
    }


}