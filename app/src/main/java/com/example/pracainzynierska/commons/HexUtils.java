package com.example.pracainzynierska.commons;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.pracainzynierska.R;
import com.example.pracainzynierska.model.ArmyToken;
import com.example.pracainzynierska.model.Hex;

import java.util.List;



public class HexUtils extends Application {

     static float systemWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
     static float systemHeight = Resources.getSystem().getDisplayMetrics().heightPixels;


    public static ImageView setHexToBoard(Hex hex, ImageView imageView){
        imageView.setX(hex.getHexpositionX() - imageView.getLayoutParams().width / 2);
        imageView.setY(hex.getHexpositionY() - imageView.getLayoutParams().height / 2);

        return imageView;
    }
//3,
    public static ArmyToken getToLobby(ArmyToken imageView, int slot){
         float centerHeightImageView = imageView.getLayoutParams().height / 2;
         float centerWidthImageView = imageView.getLayoutParams().width / 2;

        imageView.setX(systemWidth/10 - centerWidthImageView);
        switch (slot){
            case 1:
                imageView.setY((float) (systemHeight * 0.80 - centerHeightImageView));
                break;
            case 2:
                imageView.setY((float) (systemHeight * 0.60 - centerHeightImageView));
                break;
            case 3:
                imageView.setY((float) (systemHeight * 0.40 - centerHeightImageView));
                break;
        }
        return imageView;
    }



    public static void takeOnNerbyEmptyPlace(Hex hex,List<Hex>listatest) {
        float pozycjaKoncowaObrazkaX = hex.getImageView().getX() + (float) hex.getImageView().getWidth() / 2;
        float pozycjaKoncowaObrazkaY = hex.getImageView().getY() + (float) hex.getImageView().getHeight() / 2;
        float pierwszaPozycja;
        float drugaPozycja = 6480000;

        Hex najbliższyHex = null;
        for (Hex hexy : listatest) {
            if (!hexy.isBusy()) {
                pierwszaPozycja = zmierzOdleglosc(pozycjaKoncowaObrazkaX, pozycjaKoncowaObrazkaY, hexy.getHexpositionX(), hexy.getHexpositionY());
                if (pierwszaPozycja < drugaPozycja) {
                    najbliższyHex = hexy;
                    drugaPozycja = pierwszaPozycja;
                }
            }
        }

        hex.getImageView().setX(najbliższyHex.getHexpositionX() - hex.getImageView().getWidth() / 2);
        hex.getImageView().setY(najbliższyHex.getHexpositionY() - hex.getImageView().getHeight() / 2);



    }

    private static float zmierzOdleglosc(float a, float b, float palecX, float palecY) {
        float odlegloscX = Math.abs(palecX - a);
        float odlegloscY = Math.abs(palecY - b);
        return odlegloscX * odlegloscX + odlegloscY * odlegloscY;
    }
}