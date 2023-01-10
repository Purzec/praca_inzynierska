package com.example.pracainzynierska.model;


import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pracainzynierska.R;
import com.example.pracainzynierska.commons.HexUtils;
import com.example.pracainzynierska.model.enums.Directions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Hex{

    private int id;
    private List<Hex> neighbours;
    private List<Directions> directionList;
    private float HexpositionX;
    private float HexpositionY;
    private boolean busy = false;
    static float systemWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    static float systemHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private ImageView imageView;
    private int tokenID;




    public Hex() {
        this.directionList = new ArrayList<Directions>(6);
        this.neighbours = new ArrayList<Hex>(6);
        this.neighbours.add(null);
        this.neighbours.add(null);
        this.neighbours.add(null);
        this.neighbours.add(null);
        this.neighbours.add(null);
        this.neighbours.add(null);
        this.directionList.add(0, Directions.FORWARD);
        this.directionList.add(1, Directions.FORWARD_RIGHT);
        this.directionList.add(2, Directions.BACK_RIGHT);
        this.directionList.add(3, Directions.BACK);
        this.directionList.add(4, Directions.BACK_LEFT);
        this.directionList.add(5, Directions.FORWARD_LEFT);
    }


    public Hex(float hexpositionX, float hexpositionY) {
        HexpositionX = hexpositionX;
        HexpositionY = hexpositionY;

        this.directionList = new ArrayList<Directions>(6);
        this.neighbours = new ArrayList<Hex>(6);
        this.neighbours.add(null);
        this.neighbours.add(null);
        this.neighbours.add(null);
        this.neighbours.add(null);
        this.neighbours.add(null);
        this.neighbours.add(null);
        this.directionList.add(0, Directions.FORWARD);
        this.directionList.add(1, Directions.FORWARD_RIGHT);
        this.directionList.add(2, Directions.BACK_RIGHT);
        this.directionList.add(3, Directions.BACK);
        this.directionList.add(4, Directions.BACK_LEFT);
        this.directionList.add(5, Directions.FORWARD_LEFT);
    }

    public Hex(Context applicationContext) {

    }

    public void addNeighbours(Hex hexNeighbour, Directions neighbourHexDirection) {
        this.neighbours.set(neighbourHexDirection.getDirectionValue(), hexNeighbour);
    }

    public Hex getNeighbours(Directions hexDirection) {
        int kierunek = hexDirection.getDirectionValue();
        this.neighbours.get(kierunek);
        return this.neighbours.get(kierunek);
    }


    public Hex(List<Hex> neighbours, List<Directions> directionList, float hexpositionX, float hexpositionY) {
        this.neighbours = neighbours;
        this.directionList = directionList;
        HexpositionX = hexpositionX;
        HexpositionY = hexpositionY;
    }


    public String pokazSasiadaNaHExie(Hex nasz, Directions podajkierunek) {
        int kierunek = podajkierunek.getDirectionValue();
        String sasiad = Optional.ofNullable(neighbours.get(kierunek)).toString();
        System.out.println(Optional.of(sasiad).orElse("Brak sasiada"));
        return Optional.of(sasiad).orElse("Brak sasiada");
    }

    public float getHexpositionX() {
        return HexpositionX;
    }

    public void setHexpositionX(float hexpositionX) {
        HexpositionX = hexpositionX;
    }

    public float getHexpositionY() {
        return HexpositionY;
    }

    public void setHexpositionY(float hexpositionY) {
        HexpositionY = hexpositionY;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
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
}
