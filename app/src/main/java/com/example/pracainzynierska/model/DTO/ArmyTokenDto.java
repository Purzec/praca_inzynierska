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
     * Inicjatywa
     */
    private int initiative;

    /**
     * Lista ataków
     */
    private List<Attack> attacks;

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

    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public List<Attack> getAttacks() {
        return attacks;
    }

    public void setAttacks(List<Attack> attacks) {
        this.attacks = attacks;
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

    public ArmyTokenDto(int id, String name, int initiative, List<Attack> attacks, int life, Drawable imgToDatabase, int armyOwnerId) {
        this.id = id;
        this.name = name;
        this.initiative = initiative;
        this.attacks = attacks;
        this.life = life;
        this.imgToDatabase = imgToDatabase;
        this.armyOwnerId = armyOwnerId;
    }
}
