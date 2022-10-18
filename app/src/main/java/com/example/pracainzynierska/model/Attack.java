package com.example.pracainzynierska.model;

import com.example.pracainzynierska.model.enums.AttackType;

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



}
