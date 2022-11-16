package com.example.pracainzynierska.commons;

import android.app.Application;
import android.content.res.Resources;
import android.widget.RelativeLayout;

import com.example.pracainzynierska.model.ArmyToken;
import com.example.pracainzynierska.model.Hex;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class HexUtils extends Application {

    static float systemWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    static float systemHeight = Resources.getSystem().getDisplayMetrics().heightPixels;


    public static ArmyToken setHexToBoard(List<Hex> hex, ArmyToken armyToken, int idPola) {
        armyToken.setX(hex.get(idPola).getHexpositionX() - armyToken.getLayoutParams().width / 2);
        armyToken.setY(hex.get(idPola).getHexpositionY() - armyToken.getLayoutParams().height / 2);

        return armyToken;
    }

    //3,
    public static ArmyToken setToLobby(ArmyToken imageView, int slot) {
        float centerHeightImageView = imageView.getLayoutParams().height / 2;
        float centerWidthImageView = imageView.getLayoutParams().width / 2;

        imageView.setX(systemWidth / 10 - centerWidthImageView);
        switch (slot) {
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

    public static ArmyToken setToDraft(int selectableTokenQuantity, List<ArmyToken> armyTokensPool, List<ArmyToken> armyTokensdiscard, RelativeLayout lobby) {
        // pobierz ustaloną liczbę losowych tokenów do lobby
        List<ArmyToken> draftLobby = new ArrayList<>();
        for (int i = 0; i < selectableTokenQuantity; i++) {
            draftLobby.add(takeRandomObjectFromList(armyTokensPool));
        }
        int i = 1;
//uzyskalismy liste tokenów do pokazania w lobby
//pobieramy rozmiary widoku na jakim mają się pojawić

        //to statystyki lewego górnego rogu
        float layoutX = lobby.getX();
        float layoutY = lobby.getY();

        int slot=1;
        for (ArmyToken armyToken : draftLobby) {
            armyToken.setBackground(armyToken.getImgToDatabase());
            lobby.addView(setToDraftSlot(lobby,armyToken,slot));
            slot++;
        }
        return null;
    }


    public static int takeOnNerbyEmptyPlace(ArmyToken hex, List<Hex> listatest) {
        float pozycjaKoncowaObrazkaX = hex.getX() + (float) hex.getWidth() / 2;
        float pozycjaKoncowaObrazkaY = hex.getY() + (float) hex.getHeight() / 2;
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

        hex.setX(najbliższyHex.getHexpositionX() - hex.getWidth() / 2);
        hex.setY(najbliższyHex.getHexpositionY() - hex.getHeight() / 2);

        return najbliższyHex.getId();

    }

    private static float zmierzOdleglosc(float a, float b, float palecX, float palecY) {
        float odlegloscX = Math.abs(palecX - a);
        float odlegloscY = Math.abs(palecY - b);
        return odlegloscX * odlegloscX + odlegloscY * odlegloscY;
    }


    private static ArmyToken takeRandomObjectFromList(List<ArmyToken> armyTokens) {
        int listSize = armyTokens.size();
        Random random = new Random();
        int idToTake = random.nextInt(listSize - 1);
        return armyTokens.remove(idToTake);
    }


    private static ArmyToken setToDraftSlot(RelativeLayout relativeLayout,ArmyToken armyToken, int slot) {
        float layoutX = relativeLayout.getX();
        float layoutY = relativeLayout.getY();
        switch (slot) {
            case 1:
                armyToken.setX((float) (layoutX * 0.1));
                armyToken.setY((float) (layoutY * 1.8));

                break;
            case 2:
                armyToken.setX((float) (layoutX * 1.3));
                armyToken.setY((float) (layoutY * 1.8));

                break;
            case 3:
                armyToken.setX((float) (layoutX * 2.5));
                armyToken.setY((float) (layoutY * 1.8));
                break;
        }
        return armyToken;
    }
}
