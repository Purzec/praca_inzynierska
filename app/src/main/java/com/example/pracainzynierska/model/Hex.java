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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Hex {

    private int id;
    private Map<String, Integer> neighbours = new HashMap<>();
    private List<Directions> directionList;
    private float HexpositionX;
    private float HexpositionY;
    private boolean busy = false;
    static float systemWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    static float systemHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private ImageView imageView;
    private int tokenID;
    private int rotationQuantity;


    public Hex(float hexpositionX, float hexpositionY) {
        HexpositionX = hexpositionX;
        HexpositionY = hexpositionY;

        this.neighbours.put("1", -1);
        this.neighbours.put("2", -1);
        this.neighbours.put("3", -1);
        this.neighbours.put("4", -1);
        this.neighbours.put("5", -1);
        this.neighbours.put("6", -1);
    }

    public Hex() {
    }

    public void addNeighbours(Hex hexNeighbour, Directions neighbourHexDirection) {
        this.neighbours.put(String.valueOf(neighbourHexDirection.getDirectionValue()), hexNeighbour.getId());
    }

    public int getNeighbours(Directions hexDirection) {
        int kierunek = hexDirection.getDirectionValue();
        this.neighbours.get(kierunek);
        return this.neighbours.get(kierunek);
    }


    public Hex(List<Hex> neighbours, List<Directions> directionList, float hexpositionX, float hexpositionY) {

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

    public Map<String, Integer> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(Map<String, Integer> neighbours) {
        this.neighbours = neighbours;
    }

    public List<Directions> getDirectionList() {
        return directionList;
    }

    public void setDirectionList(List<Directions> directionList) {
        this.directionList = directionList;
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

    public int getRotationQuantity() {
        return rotationQuantity;
    }

    public void setRotationQuantity(int rotationQuantity) {
        this.rotationQuantity = rotationQuantity;
    }

    public void setTokenID(int tokenID) {
        this.tokenID = tokenID;
    }

    public Map<String, Object> toMap() {
        Map<String, Integer> neighboursMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : neighbours.entrySet()) {
            neighboursMap.put(String.valueOf(entry.getKey()), entry.getValue());
        }
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("neighbours", neighboursMap);
        result.put("directionList", directionList);
        result.put("HexpositionX", HexpositionX);
        result.put("HexpositionY", HexpositionY);
        result.put("busy", busy);
        result.put("tokenID", tokenID);
        result.put("rotationQuantity", rotationQuantity);
        return result;
    }
}
