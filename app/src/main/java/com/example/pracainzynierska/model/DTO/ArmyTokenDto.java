package com.example.pracainzynierska.model.DTO;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.example.pracainzynierska.model.Attack;

import java.util.List;

public class ArmyTokenDto {

    /**
     * Id zetonu
     */
    private int id;

    /**
     * Nazwa żetonu
     */
    private String name;


    /**
     * Punkty zycia tokenu
     */
    private int life;

    /**
     * Obrazek wczytany do bazy danych
     */
    private Drawable imgToDatabase;

    /**
     * Wlasciciel armii
     */
    private int armyOwnerId;

    private String owner;


    /**
     * Czy wybralismy token na drafcie
     */
    private boolean draftDiscard = false;


    /**
     * Liczba obrotów tokenu
     */
    private int rotationQuantity = 0;


    /**
     * ikona obrazka X
     */
    private Drawable cancelDrawable;

    /**
     * Slot w lobby
     */
    private int lobbySlot;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

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



    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public Drawable getImgToDatabase() {
        return imgToDatabase;
    }

    public void setImgToDatabase(Drawable imgToDatabase) {
        this.imgToDatabase = imgToDatabase;
    }

    public int getArmyOwnerId() {
        return armyOwnerId;
    }

    public void setArmyOwnerId(int armyOwnerId) {
        this.armyOwnerId = armyOwnerId;
    }

    public ArmyTokenDto() {
    }

    public ArmyTokenDto(int id, String name, int life, Drawable imgToDatabase, int armyOwnerId) {
        this.id = id;
        this.name = name;
        this.life = life;
        this.imgToDatabase = imgToDatabase;
        this.armyOwnerId = armyOwnerId;
    }
}
