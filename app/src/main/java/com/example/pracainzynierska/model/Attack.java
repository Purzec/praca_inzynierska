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

    public Attack(int id, AttackType attackType, int strenght, Directions directions) {
        this.id = id;
        this.attackType = attackType;
        this.strenght = strenght;
        this.directions = directions;
    }
}
