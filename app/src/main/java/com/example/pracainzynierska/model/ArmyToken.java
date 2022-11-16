package com.example.pracainzynierska.model;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.pracainzynierska.R;
import com.example.pracainzynierska.commons.HexUtils;

import java.util.List;

import lombok.Data;

/**
 * Obiekt jednostki
 */
@Data
public class ArmyToken extends View {

    static float systemWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    static float systemHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

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

    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
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



    public boolean confirmPositionToken(ViewGroup viewGroup, Context context, ArmyToken armyToken, List<Hex> hexList, int idPola) {
// pobierz rozmiary ekrany
        armyToken.setOnTouchListener(null);
        ImageView ok = new ImageView(context);
        ok.setImageDrawable(context.getDrawable(R.drawable.ok));
        ViewGroup.LayoutParams lpOk = new ViewGroup.LayoutParams(viewGroup.getWidth() / 10, viewGroup.getHeight() / 5);
        ok.setLayoutParams(lpOk);

        float centerHeightImageViewOk = ok.getLayoutParams().height / 2;
        float centerWidthImageViewOk = ok.getLayoutParams().width / 2;
        ok.setX((float) (systemWidth * 0.80 - centerWidthImageViewOk));
        ok.setY((float) (systemHeight * 0.80 - centerHeightImageViewOk));
        viewGroup.addView(ok);

        ImageView no = new ImageView(context);
        no.setImageDrawable(context.getDrawable(R.drawable.no));
        ViewGroup.LayoutParams lpNo = new ViewGroup.LayoutParams(viewGroup.getWidth() / 10, viewGroup.getHeight() / 5);
        no.setLayoutParams(lpNo);

        float centerHeightImageViewNo = no.getLayoutParams().height / 2;
        float centerWidthImageViewNo = no.getLayoutParams().width / 2;
        no.setX((float) (systemWidth * 0.95 - centerWidthImageViewNo));
        no.setY((float) (systemHeight * 0.80 - centerHeightImageViewNo));
        viewGroup.addView(no);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hexList.get(idPola).setBusy(true);
                hexList.get(idPola).setArmyToken(armyToken);
                viewGroup.removeView(ok);
                viewGroup.removeView(no);
                armyToken.setOnTouchListener(null);
                armyToken.setOnBoard(true);
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // przesun armyTOken na pozycje poczatkową do wolnego slotu w lobby
                HexUtils.setToLobby(armyToken, 1);
                viewGroup.removeView(ok);
                viewGroup.removeView(no);
            }
        });

return armyToken.isOnBoard();

    }
}
