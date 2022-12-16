package com.example.pracainzynierska;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.pracainzynierska.commons.HexUtils;
import com.example.pracainzynierska.databinding.ActivityTutorialBinding;
import com.example.pracainzynierska.model.ArmyToken;
import com.example.pracainzynierska.model.Hex;
import com.example.pracainzynierska.model.gameStatus.Player;
import com.example.pracainzynierska.model.view.HexBoard;

import java.util.ArrayList;
import java.util.Arrays;


public class TutorialActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout imgWithButton;
    private Button button;
    private ImageButton infoButton;
    private ImageButton nextButton;
    private ArmyToken playerBase;
    private ViewGroup viewGroup;
    private HexBoard hexBoard;
    private ArrayList<Hex> listatest;
    private int etap = 1;
    private int bylo = 0;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler(Looper.myLooper());
    private View mContentView;
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
    private View mControlsView;
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

    boolean confirmed = false;
    private ActivityTutorialBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTutorialBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //dac jakis obiekt zeby mial z czeg odane zaciagnac
        mContentView = binding.RelativeLayout1;
        mVisible = true;
        hexBoard = findViewById(R.id.hexBoard);
        nextButton = findViewById(R.id.nextButton);
        playerBase = new ArmyToken(getApplicationContext());
        nextButton.setOnClickListener(this);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        delayedHide(100);
        button = findViewById(R.id.closeButton);
        button.setOnClickListener(this);
        imgWithButton = findViewById(R.id.imgWithButton);
        viewGroup = findViewById(R.id.RelativeLayout1);
        listatest = hexBoard.pobierzKordy();


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
            case R.id.nextButton:
                if (playerBase.isOnBoard()) {
                    startActivity(new Intent(TutorialActivity.this, TutorialActivity2.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Postaw najpierw żeton na planszy", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    //todo jp dodać nowe tokeny baz i zabezpieczenie przed nastepnym stworzeniem tokenu
    private void getQuiz(int etap) {
        switch (etap) {
            case 1:
                if (bylo == 0) {
                    Player player = new Player();
                    player.setLobby(Arrays.asList(playerBase));
                    bylo++;
                    //dodac sprawdzenie czy juz było wywołane todo jp
                    //dodanie tokenu bazy gracza
                    playerBase.setBackground(getDrawable(R.drawable.token3a));
                    //   playerBase.setContextClickable(getApplicationContext());
                    //  playerBase.getImageView().setImageDrawable(getDrawable(R.drawable.token_1));
                    ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(viewGroup.getWidth() / 10, viewGroup.getHeight() / 5);
                    playerBase.setLayoutParams(lp);
                    playerBase.setLobbySlot(0);
                    viewGroup.addView(playerBase);
                    HexUtils.goToLobbySlot(playerBase);

                    ArmyToken enemyBase = new ArmyToken(getApplicationContext());
                    enemyBase.setImageView(new ImageView(getApplicationContext()));
                    enemyBase.setBackground(getDrawable(R.drawable.token3b));
                    enemyBase.setLayoutParams(new ViewGroup.LayoutParams(viewGroup.getWidth() / 10, viewGroup.getHeight() / 5));
                    HexUtils.setHexToBoard(listatest, enemyBase, 2);
                    viewGroup.addView(enemyBase);
                    System.out.println("ACTION_UP");

                    playerBase.setOnTouchListener(onTouchListener(playerBase,player));
                }
                break;

        }


    }

    private int xDelta;
    private int yDelta;

    private View.OnTouchListener onTouchListener(ArmyToken tokenG,Player player) {
        return new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int x = (int) event.getRawX();
                final int y = (int) event.getRawY();
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        System.out.println("ACTION_DOWN");
                        ConstraintLayout.LayoutParams lParams = (ConstraintLayout.LayoutParams)
                                view.getLayoutParams();
                        xDelta = x - lParams.leftMargin;
                        yDelta = y - lParams.topMargin;
                        break;
                    case MotionEvent.ACTION_UP:
                        int idPola = HexUtils.takeOnNerbyEmptyPlace(tokenG, listatest);
                        tokenG.confirmPositionToken(viewGroup, getApplicationContext(), tokenG, listatest, idPola, player);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        System.out.println("ACTION_MOVE");
                        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view
                                .getLayoutParams();
                        layoutParams.startToEnd = x - xDelta;
                        layoutParams.topToBottom = y - yDelta;
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