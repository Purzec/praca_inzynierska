package com.example.pracainzynierska.model.enums;

/**
 * Kierunki w hexie
 */
public enum Directions {

    FORWARD(0),
    FORWARD_RIGHT(1),
    BACK_RIGHT(2),
    BACK(3),
    BACK_LEFT(4),
    FORWARD_LEFT(5),
    ALL_DIRECTION(6);

    private int v;

    Directions(int paramV) {
        this.v=paramV;
    }

    public  int getDirectionValue()
    {
        return  this.v;
    }
}
