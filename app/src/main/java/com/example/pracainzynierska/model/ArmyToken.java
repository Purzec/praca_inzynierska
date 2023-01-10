package com.example.pracainzynierska.model;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.pracainzynierska.R;
import com.example.pracainzynierska.commons.HexUtils;
import com.example.pracainzynierska.model.gameStatus.Board;
import com.example.pracainzynierska.model.gameStatus.Player;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import lombok.Data;

/**
 * Obiekt jednostki
 */
@Data
public class ArmyToken extends View {

    static float systemWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    static float systemHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    static float rotationAngle = 60;

    /**
     * Id zetonu
     */
    private int id;

    /**
     * Nazwa żetonu
     */
    private String name;


    /**
     * Lista ataków
     */
    private List<Attack> attacks;
    /**
     * Zdjęcie
     */
    private ImageView imageView;

    /**
     * Punkty zycia tokenu
     */
    private int life;

    /**
     * Obrazek wczytany do bazy danych
     */
    private Drawable imgToDatabase;

    /**
     * Czy wybralismy token na drafcie
     */
    private boolean draftDiscard = false;


    /**
     * Liczba obrotów tokenu
     */
    private int rotationQuantity = 0;


    /**
     * ikona obrazka X
     */
    private Drawable cancelDrawable;

    /**
     * Slot w lobby
     */
    private int lobbySlot;

    /**
     * Bazodanowe id armii by sprawdzać kto jest sojusznikiem
     */
    private int armyOwnerId;


    public int getArmyOwnerId() {
        return armyOwnerId;
    }

    public void setArmyOwnerId(int armyOwnerId) {
        this.armyOwnerId = armyOwnerId;
    }

    public int getLobbySlot() {
        return lobbySlot;
    }

    public void setLobbySlot(int lobbySlot) {
        this.lobbySlot = lobbySlot;
    }

    public int getRotationQuantity() {
        return rotationQuantity;
    }

    public void setRotationQuantity(int rotationQuantity) {
        this.rotationQuantity = rotationQuantity;
    }

    public Drawable getCancelDrawable() {
        return cancelDrawable;
    }

    public void setCancelDrawable(Drawable cancelDrawable) {
        this.cancelDrawable = cancelDrawable;
    }

    private boolean onBoard = false;

    public boolean isOnBoard() {
        return onBoard;
    }

    public void setOnBoard(boolean onBoard) {
        this.onBoard = onBoard;
    }

    public ArmyToken(Context context) {
        super(context);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<Attack> getAttacks() {
        return attacks;
    }

    public void setAttacks(List<Attack> attacks) {
        this.attacks = attacks;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public Drawable getImgToDatabase() {
        return imgToDatabase;
    }

    public void setImgToDatabase(Drawable imgToDatabase) {
        this.imgToDatabase = imgToDatabase;
    }


    public boolean isDraftDiscard() {
        return draftDiscard;
    }

    public void setDraftDiscard(boolean draftDiscard) {
        this.draftDiscard = draftDiscard;
    }

    public boolean confirmPositionToken(ViewGroup viewGroup, Context context, ArmyToken armyToken, List<Hex> hexList, int idPola, Player player, DatabaseReference databaseReference, Board board) {
// pobierz rozmiary ekrany
        //dodać przyciski obrotu ekranu
        board.setUpdating(true);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(viewGroup.getWidth() / 10, viewGroup.getHeight() / 5);
        ImageView leftArrow = new ImageView(context);
        leftArrow.setImageDrawable(context.getDrawable(R.drawable.arrow));
        leftArrow.setLayoutParams(lp);
        float centerHeightLeftArrow = leftArrow.getLayoutParams().height / 2;
        float centerWidthLeftArrow = leftArrow.getLayoutParams().width / 2;


        ImageView rightArrow = new ImageView(context);
        rightArrow.setImageDrawable(context.getDrawable(R.drawable.arrow_right));
        rightArrow.setLayoutParams(lp);
        float centerHeightRightArrow = rightArrow.getLayoutParams().height / 2;
        float centerWidthRightArrow = rightArrow.getLayoutParams().width / 2;


        leftArrow.setX((float) (systemWidth * 0.80 - centerWidthLeftArrow));
        leftArrow.setY((float) (systemHeight * 0.65 - centerHeightLeftArrow));
        rightArrow.setX((float) (systemWidth * 0.95 - centerWidthRightArrow));
        rightArrow.setY((float) (systemHeight * 0.65 - centerHeightRightArrow));

        leftArrow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //klikniecie powoduje obrót
                float awialableRotation = armyToken.getRotation();
                armyToken.setRotation(awialableRotation - rotationAngle);
                rotationQuantity--;
                if (rotationQuantity < 0) {
                    rotationQuantity = 5;
                }

                System.out.println(armyToken.getRotationQuantity());
            }
        });

        rightArrow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                float awialableRotation = armyToken.getRotation();
                armyToken.setRotation(awialableRotation + rotationAngle);
                rotationQuantity++;
                if (rotationQuantity > 5) {
                    rotationQuantity = 0;
                }
                System.out.println(armyToken.getRotationQuantity());
            }
        });

        viewGroup.addView(leftArrow);
        viewGroup.addView(rightArrow);



        ImageView ok = new ImageView(context);
        ok.setImageDrawable(context.getDrawable(R.drawable.ok));
        ok.setLayoutParams(lp);

        float centerHeightImageViewOk = ok.getLayoutParams().height / 2;
        float centerWidthImageViewOk = ok.getLayoutParams().width / 2;
        ok.setX((float) (systemWidth * 0.80 - centerWidthImageViewOk));
        ok.setY((float) (systemHeight * 0.80 - centerHeightImageViewOk));
        viewGroup.addView(ok);

        ImageView no = new ImageView(context);
        no.setImageDrawable(context.getDrawable(R.drawable.no));
        no.setLayoutParams(lp);

        float centerHeightImageViewNo = no.getLayoutParams().height / 2;
        float centerWidthImageViewNo = no.getLayoutParams().width / 2;
        no.setX((float) (systemWidth * 0.95 - centerWidthImageViewNo));
        no.setY((float) (systemHeight * 0.80 - centerHeightImageViewNo));
        viewGroup.addView(no);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hexList.get(idPola).setBusy(true);
                hexList.get(idPola).setTokenID(armyToken.getId());
                viewGroup.removeView(ok);
                viewGroup.removeView(no);
                viewGroup.removeView(leftArrow);
                viewGroup.removeView(rightArrow);
                armyToken.setOnTouchListener(null);
                armyToken.setOnBoard(true);
                player.getLobby().remove(armyToken);
                databaseReference.updateChildren(board.toMap())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // operacja zakończyła się powodzeniem
                                System.out.println("sukces");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // operacja nie powiodła się
                                System.out.println("bład");
                            }
                        });
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // przesun armyTOken na pozycje poczatkową do wolnego slotu w lobby
                viewGroup.removeView(ok);
                viewGroup.removeView(no);
                viewGroup.removeView(leftArrow);
                viewGroup.removeView(rightArrow);
                HexUtils.goToLobbySlot(armyToken);
                armyToken.setOnTouchListener(HexUtils.onTouchListener(armyToken,hexList,viewGroup,context,player,databaseReference,board));
            }
        });

        return armyToken.isOnBoard();

    }
}
