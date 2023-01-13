package com.example.pracainzynierska.commons;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.pracainzynierska.R;
import com.example.pracainzynierska.model.ArmyToken;
import com.example.pracainzynierska.model.Attack;
import com.example.pracainzynierska.model.DTO.ArmyTokenDto;
import com.example.pracainzynierska.model.Hex;
import com.example.pracainzynierska.model.enums.Directions;
import com.example.pracainzynierska.model.gameStatus.Board;
import com.example.pracainzynierska.model.gameStatus.Player;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class HexUtils extends Application {

    static float systemWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    static float systemHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    SQLiteDatabase db;

    public static ArmyToken setHexToBoard(List<Hex> hex, ArmyToken armyToken, int idPola) {
        armyToken.setX(hex.get(idPola).getHexpositionX() - armyToken.getLayoutParams().width / 2);
        armyToken.setY(hex.get(idPola).getHexpositionY() - armyToken.getLayoutParams().height / 2);

        return armyToken;
    }

    public static void setToLobby(ViewGroup viewgroup, List<Hex> listatest, Context context, DatabaseReference databaseReference,Board board,List<ArmyToken> armyTokens,String role) {
        int slot =0;
        for (ArmyToken armyToken : armyTokens) {
            armyToken.setLayoutParams(new ViewGroup.LayoutParams(viewgroup.getWidth() / 10, viewgroup.getHeight() / 5));
            float centerHeightImageView = armyToken.getLayoutParams().height / 2;
            float centerWidthImageView = armyToken.getLayoutParams().width / 2;
            armyToken.setX(systemWidth / 10 - centerWidthImageView);
            switch (slot) {
                case 0:
                    armyToken.setLobbySlot(0);
                    armyToken.setY((float) (systemHeight * 0.80 - centerHeightImageView));
                    break;
                case 1:
                    armyToken.setLobbySlot(1);
                    armyToken.setY((float) (systemHeight * 0.60 - centerHeightImageView));
                    break;
                case 2:
                    armyToken.setLobbySlot(2);
                    armyToken.setY((float) (systemHeight * 0.40 - centerHeightImageView));
                    break;
            }
            slot++;
            viewgroup.addView(armyToken);
            armyToken.setOnClickListener(null);
            armyToken.setOnTouchListener(onTouchListener(armyToken, listatest, viewgroup, context,databaseReference,board,role));
        }
    }

    public static List<ArmyToken> setToDraft(List<ArmyToken> randomToken, RelativeLayout lobby) {
        int slot = 1;
        for (ArmyToken armyToken : randomToken) {
            armyToken
                    .setLayoutParams(new ViewGroup.LayoutParams((int)
                            (lobby.getWidth() * 0.30), (int) (lobby.getHeight() * 0.40)));

            armyToken.setBackground(armyToken.getImgToDatabase());
            armyToken.setVisibility(View.VISIBLE);
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
        return randomToken;
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


    public static View.OnTouchListener onTouchListener(ArmyToken tokenG, List listatest, ViewGroup viewGroup, Context context, DatabaseReference databaseReference, Board board,String role) {

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
                        tokenG.confirmPositionToken(viewGroup, context.getApplicationContext(), tokenG, board.getHexBoard(), idPola,databaseReference, board,role);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        System.out.println("ACTION_MOVE");
                        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view
                                .getLayoutParams();
                        layoutParams.startToEnd = x - x - lParams.leftMargin;
                        layoutParams.topToBottom = y - y - lParams.topMargin;
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

    public static void goToLobbySlot(ArmyToken armyToken) {
        float centerHeightImageView = armyToken.getLayoutParams().height / 2;
        float centerWidthImageView = armyToken.getLayoutParams().width / 2;
        armyToken.setX(systemWidth / 10 - centerWidthImageView);
        switch (armyToken.getLobbySlot()) {
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

    }

    public static Player returnCurrentPlayer(Board board,String role){
        if (role.equals("host")) {
            if (board.getMessage().equals("host")) {
               return board.getPlayer1();
            }
        } else {
            if (board.getMessage().equals("quest")) {
             return board.getPlayer2();
            }
        }
        System.out.println("nie znaleziono gracza");
        return null;
    }

   /* public List<Hex> getChainOfNeighboursInDirection(Hex hex, Directions direction) {
        List<Hex> neighboursChain = new ArrayList<>();
        Hex currentHex = hex;
        while (currentHex.getNeighbours(direction) != null) {
            currentHex = currentHex.getNeighbours(direction);
            neighboursChain.add(currentHex);
        }
        return neighboursChain;
    }*/

    public void assignCurrentHexForAllTokens(List<ArmyToken> tokenList, List<Hex> hexList) {
        for (ArmyToken token : tokenList) {
            for (Hex hex : hexList) {
                if (hex.getTokenID() == token.getId()) {
                    token.setCurrentHex(hex);
                    break;
                }
            }
        }
    }

}
