package com.example.pracainzynierska;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.pracainzynierska.databinding.ActivityTutorialBinding;
import com.example.pracainzynierska.model.Hex;
import com.example.pracainzynierska.model.view.HexBoard;

import java.util.ArrayList;


public class TutorialActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout imgWithButton;
    private Button button;
    private ImageButton infoButton;
    private ImageView playuerHexagonMaskView;
    private ViewGroup viewGroup;
    private HexBoard hexBoard;
    private ArrayList<Hex> listatest;
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
            // Delayed removal of status and navigation bar
            if (Build.VERSION.SDK_INT >= 30) {
                mContentView.getWindowInsetsController().hide(
                        WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
            } else {
                // Note that some of these constants are new as of API 16 (Jelly Bean)
                // and API 19 (KitKat). It is safe to use them, as they are inlined
                // at compile-time and do nothing on earlier devices.
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
        viewGroup = findViewById(R.id.RelativeLayout1);
        viewGroup.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                System.out.println(hexBoard.getScreenWidth());
                listatest = hexBoard.pobierzKordy();
                System.out.println(listatest.get(0).getHexpositionX());
                System.out.println(listatest.get(0).getHexpositionY());

                playuerHexagonMaskView = new ImageView(getApplicationContext());
                playuerHexagonMaskView.setImageDrawable(getDrawable(R.drawable.token_1));
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(viewGroup.getWidth() / 10, viewGroup.getHeight() / 5);
                playuerHexagonMaskView.setLayoutParams(lp);
                viewGroup.addView(playuerHexagonMaskView);
                playuerHexagonMaskView.setX(listatest.get(2).getHexpositionX() - playuerHexagonMaskView.getLayoutParams().width / 2);
                playuerHexagonMaskView.setY(listatest.get(2).getHexpositionY() - playuerHexagonMaskView.getLayoutParams().height / 2);
            }
        });


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        delayedHide(100);
        button = findViewById(R.id.closeButton);
        infoButton = findViewById(R.id.infoButton);
        infoButton.setOnClickListener(this);
        button.setOnClickListener(this);
        imgWithButton = findViewById(R.id.imgWithButton);
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
                break;
            case R.id.infoButton:
                imgWithButton.setVisibility(View.VISIBLE);
                break;
        }
    }
}