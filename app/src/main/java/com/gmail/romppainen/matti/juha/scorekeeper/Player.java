package com.gmail.romppainen.matti.juha.scorekeeper;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Juha on 3.1.2017.
 */

class Player implements Parcelable {

    private int id;
    private String name;
    private int created;
    private boolean favorite;
    private boolean selected;
    private int lastGame;

    Player() {}

    Player(int id, String name, int created, boolean favorite, boolean selected, int lastGame) {
        this.id = id;
        this.name = name;
        this.created = created;
        this.favorite = favorite;
        this.selected = selected;
        this.lastGame = lastGame;
    }

    int getId() { return this.id; }
    void setId(int id) {this.id = id; }

    String getName() { return this.name; }
    void setName(String name) {this.name = name; }

    int getCreated() { return this.created; }
    void setCreated(int created) {this.created = created; }

    boolean getFavorite() { return this.favorite; }
    void setFavorite(boolean favorite) {this.favorite = favorite; }

    boolean getSelected() { return this.selected; }
    void setSelected(boolean selected) {this.selected = selected; }

    int getLastGame() { return this.lastGame; }
    void setLastGame(int last_game) {this.lastGame = last_game; }

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
        out.writeByte((byte) (favorite ? 1 : 0));
        out.writeByte((byte) (selected ? 1 : 0));
        out.writeInt(lastGame);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Player> CREATOR = new Parcelable.Creator<Player>() {
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Player(Parcel in) {
        id = in.readInt();
        name = in.readString();
        created = in.readInt();
        favorite = in.readByte() != 0;
        selected = in.readByte() != 0;
        lastGame = in.readInt();
    }
}
