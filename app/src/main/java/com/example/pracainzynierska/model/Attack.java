package com.example.pracainzynierska.model;

import com.example.pracainzynierska.model.enums.AttackType;
import com.example.pracainzynierska.model.enums.Directions;

import lombok.Data;

/**
 * Obiekt definiujący atakct jednostki
 */
@Data
public class Attack {

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

    public Attack(int id, AttackType attackType, int strenght, Directions directions) {
        this.id = id;
        this.attackType = attackType;
        this.strenght = strenght;
        this.directions = directions;
    }

    public Attack() {
    }

    public Attack(int id, int tokenID, AttackType attackType, int strenght, Directions directions) {
        this.id = id;
        this.tokenID = tokenID;
        this.attackType = attackType;
        this.strenght = strenght;
        this.directions = directions;
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
        tokenID = tokenID;
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
}
