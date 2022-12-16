package com.example.pracainzynierska;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.pracainzynierska.R;
import com.example.pracainzynierska.databinding.ActivityGameBinding;
import com.example.pracainzynierska.model.gameStatus.Board;
import com.example.pracainzynierska.model.gameStatus.Player;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GameActivity extends AppCompatActivity {

    ImageButton button;
    String playerName = "";
    String roomName = "";
    String role = "";
    String message = "";
    FirebaseDatabase database;
    DatabaseReference messageRef, room;
    RelativeLayout relativeLayout;
    Board board;
    private DatabaseReference mDatabase;

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
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
        }
    };
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    private ActivityGameBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mDatabase = FirebaseDatabase.getInstance().getReference();

        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        mContentView = binding.RelativeLayout1;

        button = findViewById(R.id.finishRound);
        button.setEnabled(false);

        database = FirebaseDatabase.getInstance();
        Bundle extras = getIntent().getExtras();
        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        playerName = extras.getString("player");

        relativeLayout = findViewById(R.id.waiting);
        if (extras != null) {
            roomName = extras.getString("roomName");
            if (roomName.equals(playerName)) {
                role = "host";
            } else {
                role = "quest";
            }
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send message
                button.setEnabled(false);
                message = role + ":POKED!";
                messageRef.setValue(message);
            }
        });
        //nasłuchiwanie porzed nadchodząca wiadomościa
        room = database.getReference("rooms/" + roomName);
        addRoomEventListener();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        delayedHide(100);
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

    private void addRoomEventListener() {
        room.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Player> players = new ArrayList();
                //jak zmienią się jakies informacje to leci ta metoda
                snapshot.getChildren().forEach(dataSnapshot -> {
                    Player value = dataSnapshot.getValue(Player.class);
                    players.add(value);
                });
                // Metoda zamieniająca mapę na object
                game(players);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //error - rettry
            }
        });
    }


    private void game(List<Player> players) {
        if (players.size()==2){
            relativeLayout.setVisibility(View.GONE);
            //zrobic po kolei, czyli najpierw jeden gracz wybiera armie potem drugi

        }
    }
}