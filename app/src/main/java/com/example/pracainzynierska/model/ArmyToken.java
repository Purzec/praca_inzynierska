package com.example.pracainzynierska.model;

import java.util.List;

import lombok.Data;

/**
 * Obiekt jednostki
 */
@Data
public class ArmyToken {

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
     * Adres do zdjęć
     */
    private String imgAddress;


}
