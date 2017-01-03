package com.gmail.romppainen.matti.juha.scorekeeper;

/**
 * Created by Juha on 3.1.2017.
 */

public class Player {

    private int id;
    private String name;
    private int created;
    private boolean favorite;
    private boolean selected;
    private int lastGame;

    Player(String name) {
        this.name = name;
    }

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
}
