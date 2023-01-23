package com.example.pracainzynierska.model.enums;

/**
 * Kierunki w hexie
 */
public enum Directions {

    FORWARD(1),
    FORWARD_RIGHT(2),
    BACK_RIGHT(3),
    BACK(4),
    BACK_LEFT(5),
    FORWARD_LEFT(6),
    ALL_DIRECTION(7);

    private int v;

    Directions(int paramV) {
        this.v=paramV;
    }

    public  int getDirectionValue()
    {
        return  this.v;
    }
}
