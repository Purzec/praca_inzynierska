package com.example.pracainzynierska;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.pracainzynierska.commons.HexUtils;
import com.example.pracainzynierska.databinding.ActivityTutorial2Binding;
import com.example.pracainzynierska.model.ArmyToken;
import com.example.pracainzynierska.model.Hex;
import com.example.pracainzynierska.model.gameStatus.Player;
import com.example.pracainzynierska.model.view.HexBoard;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class TutorialActivity2 extends AppCompatActivity implements View.OnClickListener {
    private static final boolean AUTO_HIDE = true;
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler(Looper.myLooper());
    private View mContentView;
    private RelativeLayout imgWithButton;
    private RelativeLayout draft;
    private Button button;
    private Button acceptButton;
    private ImageButton infoButton;
    private ImageButton nextButton;
    private ArmyToken playerBase;
    private ArrayList<Hex> listatest;
    private ViewGroup viewGroup;
    private ViewGroup relativeLayout;
    private HexBoard hexBoard;
    private int etap = 1;
    private int bylo = 0;
    private View mControlsView;
    private Player player = new Player();
    SQLiteDatabase db;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            if (Build.VERSION.SDK_INT >= 30) {
                mContentView.getWindowInsetsController().hide(
                        WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
            } else {
                mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            }
        }
    };

    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    private ActivityTutorial2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTutorial2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mContentView = binding.RelativeLayout1;
        relativeLayout = findViewById(R.id.RelativeLayout1);
        mVisible = true;
        button = findViewById(R.id.closeButton);
        button.setOnClickListener(this);
        hexBoard = findViewById(R.id.hexBoard);
        listatest = hexBoard.pobierzKordy();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        imgWithButton = findViewById(R.id.imgWithButton);
        delayedHide(1);
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }


    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.closeButton:
                imgWithButton.setVisibility(View.INVISIBLE);
                getQuiz(etap);

                break;
            case R.id.infoButton:
                imgWithButton.setVisibility(View.VISIBLE);
                System.out.println(imgWithButton.getLayoutParams().width);
                System.out.println(imgWithButton.getLayoutParams().height);
                imgWithButton.setX((float) (Resources.getSystem().getDisplayMetrics().widthPixels * 0.2));
                imgWithButton.setY((float) (Resources.getSystem().getDisplayMetrics().heightPixels * 0.10));
                imgWithButton.bringToFront();
                break;
            case R.id.nextButton:
                if (playerBase.isOnBoard()) {
                    //      startActivity(new Intent(TutorialActivity.this, TutorialActivity2.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Postaw najpierw żeton na planszy", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.acceptDraft:
//sprawdzamy czy odrzucił jeden token conajmniej
                if (player.getDraft().stream().allMatch(ArmyToken::isDraftDiscard)) {
                    Toast.makeText(getApplicationContext(), "KLIKNĄŁEŚ WSZYSTKIE TOKENY CONAJMNIEJ JEDEN MUSISZ DOBRAĆ", Toast.LENGTH_SHORT).show();
                } else if (player.getDraft().stream().anyMatch(ArmyToken::isDraftDiscard)) {
                    //  draft.setVisibility(View.GONE);
                    //dodaj do lobby
                    player.setLobby(player.getDraft().stream().filter(armyToken -> !armyToken.isDraftDiscard()).collect(Collectors.toList()));
                    Toast.makeText(getApplicationContext(), String.valueOf(player.getLobby().size()), Toast.LENGTH_SHORT).show();
                    player.getDraft().stream().forEach(armyToken -> draft.removeView(armyToken));
                    HexUtils.setToLobby(player.getLobby(), relativeLayout,listatest,getApplicationContext());
                    draft.setVisibility(View.GONE);
                } else if (player.getDraft().stream().noneMatch(ArmyToken::isDraftDiscard)) {
                    Toast.makeText(getApplicationContext(), "MUSISZ ODRZUCIĆ CONAJMNIEJ JEDEN TOKEN", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }


    //todo jp dodać nowe tokeny baz i zabezpieczenie przed nastepnym stworzeniem tokenu
    private void getQuiz(int etap) {
        switch (etap) {
            case 1:
                int ownerArmyId = 1;
                draft = findViewById(R.id.draft);
                //pobranie wszystkich tokenów nalezących do armi ktorą wybral sobie gracz
                List<ArmyToken> tokensPlayer = new ArrayList<>();
                db = openOrCreateDatabase("PracaInzynierskaTest", MODE_PRIVATE, null);
                Cursor c = db.rawQuery("SELECT * FROM ARMY_TOKENS_TEST WHERE ARMY_OWNER_ID = ?", new String[]{String.valueOf(ownerArmyId)});
                if (c.moveToFirst()) {
                    do {
                        ArmyToken armyToken = new ArmyToken(getApplicationContext());
                        armyToken.setId(c.getInt(c.getColumnIndexOrThrow("Id")));
                        armyToken.setName(c.getString(c.getColumnIndexOrThrow("name")));
                        armyToken.setInitiative(c.getInt(c.getColumnIndexOrThrow("initiative")));
                        armyToken.setLife(c.getInt(c.getColumnIndexOrThrow("life")));

                        byte[] image = c.getBlob(c.getColumnIndexOrThrow("image"));
                        Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
                        Drawable drawable = new BitmapDrawable(getResources(), bmp);
                        armyToken.setCancelDrawable(getDrawable(R.drawable.cancel));
                        armyToken.setImgToDatabase(drawable);
                        armyToken.setLayoutParams(new ViewGroup.LayoutParams((int) (mContentView.getWidth() * 0.20), (int) (mContentView.getHeight() * 0.40)));
                        tokensPlayer.add(armyToken);
                    } while (c.moveToNext());
                }
                db.close();
                c.close();
                //pobralismy tokenów do listy
                //pobieramy widok na jakim mielisby się znaleźć
                acceptButton = findViewById(R.id.acceptDraft);
                acceptButton.setOnClickListener(this);
                draft.setVisibility(View.VISIBLE);
                // pobieramy do 3 tokenów zaleznie od tego ile jest w lobby z listy dostepnych
                player.setDraft(HexUtils.setToDraft(3, tokensPlayer, new ArrayList<>(), draft));



                /*1.pobranie zasobów obu graczy
                 * 2.zrobienie widoku z 3 tokenami
                 * 3. zrobienie opcji zaznaczania tokenów
                 * 4.przypisanie im X znaczka ze chcemy go dorzucic
                 * */


                break;

        }


    }
}