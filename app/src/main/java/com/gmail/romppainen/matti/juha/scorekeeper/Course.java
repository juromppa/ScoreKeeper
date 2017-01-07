package com.gmail.romppainen.matti.juha.scorekeeper;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Juha on 7.1.2017.
 */

class Course implements Parcelable {

    private int id;
    private String name;
    private int created;
    private int par;
    private ArrayList<Integer> holes;

    Course() {}

    Course(int id, String name, int created, int par, ArrayList<Integer> holes) {
        this.id = id;
        this.name = name;
        this.created = created;
        this.par = par;
        this.holes = holes;
    }

    int getId() { return this.id; }
    void setId(int id) {this.id = id; }

    String getName() { return this.name; }
    void setName(String name) {this.name = name; }

    int getCreated() { return this.created; }
    void setCreated(int created) {this.created = created; }

    int getPar() { return this.par; }
    void setPar(int par) {this.par = par; }

    ArrayList getHoles() { return this.holes; }
    void setHoles(ArrayList<Integer> holes) {this.holes = holes; }

    /* everything below here is for implementing Parcelable */

    // 99.9% of the time you can just ignore this
    @Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(name);
        out.writeInt(created);
        out.writeInt(par);
        out.writeList(holes);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Course> CREATOR = new Parcelable.Creator<Course>() {
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Course(Parcel in) {
        id = in.readInt();
        name = in.readString();
        created = in.readInt();
        par = in.readInt();
        holes = in.readArrayList(null);
    }
}
