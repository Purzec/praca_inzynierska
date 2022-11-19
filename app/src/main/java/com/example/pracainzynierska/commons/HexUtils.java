package com.example.pracainzynierska.commons;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

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
    public static void setToLobby(List<ArmyToken> armyTokens, ViewGroup relativeLayout,List<Hex> listatest,Context context) {
        int slot = 0;
        for (ArmyToken armyToken : armyTokens) {
            armyToken.setLayoutParams(new ViewGroup.LayoutParams(relativeLayout.getWidth() / 10, relativeLayout.getHeight() / 5));
            float centerHeightImageView = armyToken.getLayoutParams().height / 2;
            float centerWidthImageView = armyToken.getLayoutParams().width / 2;
            armyToken.setX(systemWidth / 10 - centerWidthImageView);
            switch (slot) {
                case 0:
                    armyToken.setY((float) (systemHeight * 0.80 - centerHeightImageView));
                    break;
                case 1:
                    armyToken.setY((float) (systemHeight * 0.60 - centerHeightImageView));
                    break;
                case 2:
                    armyToken.setY((float) (systemHeight * 0.40 - centerHeightImageView));
                    break;
            }
            slot++;
            relativeLayout.addView(armyToken);
            armyToken.setOnClickListener(null);
            armyToken.setOnTouchListener(onTouchListener(armyToken,listatest,relativeLayout,context));
        }
    }

    public static List<ArmyToken> setToDraft(int selectableTokenQuantity, List<ArmyToken> armyTokensPool, List<ArmyToken> armyTokensdiscard, RelativeLayout lobby) {
        // pobierz ustaloną liczbę losowych tokenów do lobby
        List<ArmyToken> draftLobby = new ArrayList<>();
        for (int i = 0; i < selectableTokenQuantity; i++) {
            draftLobby.add(takeRandomObjectFromList(armyTokensPool));
        }

        int slot = 1;
        for (ArmyToken armyToken : draftLobby) {
            armyToken.setBackground(armyToken.getImgToDatabase());
            //dodać plany jak kliklniesz
            armyToken.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (armyToken.isDraftDiscard()) {
                        armyToken.setDraftDiscard(false);
                        armyToken.setBackground(armyToken.getImgToDatabase());
                    } else {
                        armyToken.setDraftDiscard(true);
                        armyToken.setBackground(armyToken.getCancelDrawable());
                    }
                }
            });
            lobby.addView(setToDraftSlot(lobby, armyToken, slot));
            slot++;
        }
        return draftLobby;
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


    private static ArmyToken setToDraftSlot(RelativeLayout relativeLayout, ArmyToken armyToken, int slot) {
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


    private static View.OnTouchListener onTouchListener(ArmyToken tokenG, List listatest, ViewGroup viewGroup, Context context) {

        return new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int x = (int) event.getRawX();
                final int y = (int) event.getRawY();
                ConstraintLayout.LayoutParams lParams = (ConstraintLayout.LayoutParams)
                        view.getLayoutParams();
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        System.out.println("ACTION_DOWN");
                        break;
                    case MotionEvent.ACTION_UP:
                        System.out.println("ACTION_UP");
                        int idPola = HexUtils.takeOnNerbyEmptyPlace(tokenG, listatest);
                        tokenG.confirmPositionToken(viewGroup, context.getApplicationContext(), tokenG, listatest, idPola);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        System.out.println("ACTION_MOVE");
                        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view
                                .getLayoutParams();
                        layoutParams.startToEnd = x - x - lParams.leftMargin;
                        layoutParams.topToBottom = y -  y - lParams.topMargin;
                        layoutParams.rightMargin = 0;
                        layoutParams.bottomMargin = 0;
                        //pobrac srodek obrazka
                        tokenG.setX(x - tokenG.getWidth());
                        tokenG.setY(y - tokenG.getHeight() / 2);
                        view.setLayoutParams(layoutParams);
                        break;
                }
                viewGroup.invalidate();
                return true;
            }
        };
    }
}
