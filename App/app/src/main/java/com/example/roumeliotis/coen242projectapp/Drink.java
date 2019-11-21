package com.example.roumeliotis.coen242projectapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Drink implements Parcelable {

    private long remote_id = -1;            //ID in server database
    private String mixer = null;            // Mixer type
    private String alcohol = null;          // Alcohol type
    private boolean doubleAlcohol = false;  // Double

    public Drink(long remote_id, String mixer, String alcohol, boolean doubleAlcohol) {
        this.remote_id = remote_id;
        this.mixer = mixer;
        this.alcohol = alcohol;
        this.doubleAlcohol = doubleAlcohol;
    }

    protected Drink(Parcel in) {
        remote_id = in.readLong();
        mixer = in.readString();
        alcohol = in.readString();
        doubleAlcohol = in.readByte() != 0;  //doubleAlcohol == true if byte != 0
    }

    public static final Creator<Drink> CREATOR = new Creator<Drink>() {
        @Override
        public Drink createFromParcel(Parcel in) {
            return new Drink(in);
        }

        @Override
        public Drink[] newArray(int size) {
            return new Drink[size];
        }
    };


    // Get and set remoteID
    public long getRemote_id() {
        return remote_id;
    }
    public void setRemote_id(long remote_id) {
        this.remote_id = remote_id;
    }

    // Get and set mixer
    public String getMixer() { return mixer; }
    public void setMixer(String mixer) { this.mixer = mixer; }

    // Get and set alcohol
    public String getAlcohol(){ return alcohol; }
    public void setAlcohol(String alcohol){ this.alcohol = alcohol; }

    // Get and set double
    public boolean getDouble(){ return doubleAlcohol; }
    public void setDouble(boolean doubleAlcohol){ this.doubleAlcohol = doubleAlcohol; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(remote_id);
        parcel.writeString(mixer);
        parcel.writeString(alcohol);
        parcel.writeByte((byte) (doubleAlcohol ? 1 : 0));     //if doubleAlcohol == true, byte == 1
    }

}