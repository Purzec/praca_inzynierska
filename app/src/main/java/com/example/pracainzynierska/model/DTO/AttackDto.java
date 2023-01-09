package com.example.pracainzynierska.model.DTO;

import com.example.pracainzynierska.model.enums.AttackType;
import com.example.pracainzynierska.model.enums.Directions;

public class AttackDto {

    /**
     * Id ataku w bazie danych
     */
    private int id;

    /**
     * ID tokenu atakującego
     */
    private int tokenID;

    /**
     * Rodzaj ataku
     */
    private AttackType attackType;

    /**
     * siła ataku;
     */
    private int strenght;

    /**
     * kierunek
     */
    private Directions directions;

    private int initiative;

    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTokenID() {
        return tokenID;
    }

    public void setTokenID(int tokenID) {
        this.tokenID = tokenID;
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public void setAttackType(AttackType attackType) {
        this.attackType = attackType;
    }

    public int getStrenght() {
        return strenght;
    }

    public void setStrenght(int strenght) {
        this.strenght = strenght;
    }

    public Directions getDirections() {
        return directions;
    }

    public void setDirections(Directions directions) {
        this.directions = directions;
    }

    public AttackDto(int id, int tokenID, AttackType attackType, int strenght, Directions directions, int initiative) {
        this.id = id;
        this.tokenID = tokenID;
        this.attackType = attackType;
        this.strenght = strenght;
        this.directions = directions;
        this.initiative = initiative;
    }
}
