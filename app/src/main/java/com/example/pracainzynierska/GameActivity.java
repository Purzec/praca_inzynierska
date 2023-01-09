package com.example.pracainzynierska;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
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
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.pracainzynierska.commons.ArmyTokenUtils;
import com.example.pracainzynierska.commons.HexUtils;
import com.example.pracainzynierska.databinding.ActivityGameBinding;
import com.example.pracainzynierska.model.ArmyToken;
import com.example.pracainzynierska.model.Attack;
import com.example.pracainzynierska.model.DTO.ArmyTokenDto;
import com.example.pracainzynierska.model.Hex;
import com.example.pracainzynierska.model.gameStatus.Board;
import com.example.pracainzynierska.model.gameStatus.Player;
import com.example.pracainzynierska.model.view.HexBoard;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    SQLiteDatabase db;
    List<ArmyTokenDto> tmpArmy;
    ListView listView;
    ImageButton button;
    String playerName = "";
    String roomName = "";
    String role = "";
    FirebaseDatabase database;
    DatabaseReference room, messageRef;
    RelativeLayout waiting, listViewArmy;
    ConstraintLayout boardView;
    Board board = new Board();
    HexBoard hexBoard;
    ArmyTokenUtils armyTokenUtils = new ArmyTokenUtils();


    private static final String HOST = "host";
    private static final String QUEST = "quest";


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

    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
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

    private ActivityGameBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mContentView = binding.constraintLayout;
        button = findViewById(R.id.finishRound);
        button.setEnabled(false);
        button.setVisibility(View.GONE);
        button.setOnClickListener(this);
        boardView = findViewById(R.id.constraintLayout);
        database = FirebaseDatabase.getInstance();
        Bundle extras = getIntent().getExtras();
        playerName = extras.getString("player");
        waiting = findViewById(R.id.waiting);
        if (extras != null) {
            roomName = extras.getString("roomName");
            if (roomName.equals(playerName)) {
                role = "host";
                //dodac tutaj inicjalizacje pola hexów u ktoregoś z graczy
            } else {
                role = "quest";
            }
        }
        hexBoard = findViewById(R.id.hexBoard);
        listViewArmy = findViewById(R.id.listViewArmy);
        listView = findViewById(R.id.listArmy);

        if (savedInstanceState == null) {

            messageRef = FirebaseDatabase.getInstance().getReference("rooms/" + roomName);

            messageRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // pobierz wartości wszystkich pól z bazy danych i ustaw je w obiekcie klasy Board
                    board.setMessage(dataSnapshot.child("message").getValue(String.class));
                    board.setRound(dataSnapshot.child("round").getValue(String.class));
                    board.setP1info(dataSnapshot.child("p1info").getValue(Player.class));
                    board.setP2info(dataSnapshot.child("p2info").getValue(Player.class));
                    if (dataSnapshot.child("player1").getValue(Player.class) == null) {
                        board.setPlayer1(dataSnapshot.child("p1info").getValue(Player.class));
                    } else {
                        board.setPlayer1(dataSnapshot.child("player1").getValue(Player.class));
                    }
                    if (dataSnapshot.child("player2").getValue(Player.class) == null) {
                        board.setPlayer2(dataSnapshot.child("p2info").getValue(Player.class));
                    } else {
                        board.setPlayer2(dataSnapshot.child("player2").getValue(Player.class));
                    }
                    board.setHexBoard(dataSnapshot.child("hexBoard").getValue(new GenericTypeIndicator<List<Hex>>() {
                    }));
                    game(board);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    System.out.println("bład");
                }
            });
        }
    }

    private void game(Board board) {
        if (board.getPlayer1() != null && board.getPlayer2() != null) {
            System.out.println("Wywołano metodę game");
            waiting.setVisibility(View.GONE);
            if (role.equals("host")) {
                if (board.getMessage().equals("host")) {
                    System.out.println("Ekran widzi gracz: " + board.getPlayer1().getNick());
                    gameTurn(board.getPlayer1());
                }
            } else {
                if (board.getMessage().equals("quest")) {
                    System.out.println("Ekran widzi gracz: " + board.getPlayer2().getNick());
                    gameTurn(board.getPlayer2());
                }
            }
        }
    }

    private void gameTurn(Player player) {
        switch (player.getEtap()) {
            case 1:
                if (player.getChosenArmy() == null) {
                    System.out.println("wybór armii");
                    List<String> armyList = Arrays.asList("Elf", "Human");
                    listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, armyList));
                    listViewArmy.setVisibility(View.VISIBLE);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            listViewArmy.setVisibility(View.GONE);
                            player.setEtap(2);
                            String chosenArmy = armyList.get(i);
                            player.setChosenArmy(armyTokenGet(getPlayerNumber(chosenArmy,board.getMessage())));
                            isClickable();
                            makeButtonGreen();
                        }
                    });
                }
                break;
            case 2:
                isClickable();
                System.out.println("postawienie dowódcy");
                break;
            default:
                isClickable();
                System.out.println("tury normalne");
                break;
        }
    }

    @Override
    public void onClick(View view) {
        endTurn(view);
    }

    public void endTurn(View view) {

        if (board.getMessage().equals("quest")) {
            // jeśli tak, to ustaw ture gracza "quest" i wiadomość "quest"
            noClickable();
            board.setMessage("host");
        } else {
            // w przeciwnym razie ustaw ture gracza "host" i wiadomość "host"
            noClickable();
            board.setMessage("quest");
        }


        messageRef.updateChildren(board.toMap())
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

    public List<ArmyTokenDto> armyTokenGet(int ownerArmyId) {

        System.out.println(ownerArmyId);
        //pobranie wszystkich tokenów nalezących do armi ktorą wybral sobie gracz
        List<ArmyTokenDto> tokensPlayer = new ArrayList<>();
        db = openOrCreateDatabase("PracaInzynierskaTest2", MODE_PRIVATE, null);
        Cursor c = db.rawQuery("SELECT * FROM ARMY_TOKENS_TEST WHERE ARMY_OWNER_ID = ?", new String[]{String.valueOf(ownerArmyId)});
        if (c.moveToFirst()) {
            do {
                ArmyTokenDto armyToken = new ArmyTokenDto();
                armyToken.setId(c.getInt(c.getColumnIndexOrThrow("Id")));
                armyToken.setName(c.getString(c.getColumnIndexOrThrow("name")));
                armyToken.setLife(c.getInt(c.getColumnIndexOrThrow("life")));
                armyToken.setArmyOwnerId(c.getColumnIndexOrThrow("ARMY_OWNER_ID"));
                tokensPlayer.add(armyToken);
            } while (c.moveToNext());
        }
        db.close();
        c.close();
        return tokensPlayer;
    }

    public ArmyToken createArmyToken(ArmyTokenDto tokenDto, Context context) {
        db = openOrCreateDatabase("PracaInzynierskaTest2", MODE_PRIVATE, null);
        Cursor cursorToken = db.rawQuery("SELECT * FROM ARMY_TOKENS_TEST WHERE ID = ?", new String[]{String.valueOf(tokenDto.getId())});
        List<ArmyToken> armyTokens = new ArrayList<>();
        if (cursorToken.moveToFirst()) {
            do {
                ArmyToken armyToken = new ArmyToken(context);
                armyToken.setId(cursorToken.getInt(cursorToken.getColumnIndexOrThrow("Id")));
                armyToken.setName(cursorToken.getString(cursorToken.getColumnIndexOrThrow("name")));
                armyToken.setInitiative(cursorToken.getInt(cursorToken.getColumnIndexOrThrow("initiative")));
                armyToken.setLife(cursorToken.getInt(cursorToken.getColumnIndexOrThrow("life")));
                armyToken.setArmyOwnerId(cursorToken.getColumnIndexOrThrow("ARMY_OWNER_ID"));

                byte[] image = cursorToken.getBlob(cursorToken.getColumnIndexOrThrow("image"));
                Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
                Drawable drawable = new BitmapDrawable(getResources(), bmp);
                armyToken.setCancelDrawable(getDrawable(R.drawable.cancel));
                armyToken.setImgToDatabase(drawable);
                armyToken.setLayoutParams(new ViewGroup.LayoutParams((int) (mContentView.getWidth() * 0.20), (int) (mContentView.getHeight() * 0.40)));
                armyToken.setAttacks(createTokenAttakcs(tokenDto,context));
                armyTokens.add(armyToken);
            } while (cursorToken.moveToNext());
        }
        cursorToken.close();

        db.close();
        return armyTokens.get(0);
    }

    private void isClickable() {
        button.setEnabled(true);
        button.setVisibility(View.VISIBLE);
    }

    private void makeButtonGreen() {
        button.setEnabled(true);
        button.setVisibility(View.VISIBLE);
        button.setImageDrawable(getDrawable(R.drawable.ok_green));
    }

    private void noClickable() {
        button.setEnabled(false);
        button.setVisibility(View.GONE);
    }

    public int getPlayerNumber(String army, String message) {
        switch (message) {
            case "host":
                switch (army) {
                    case "Elf":
                        return 1;
                    case "Human":
                        return 3;
                }
                break;
            case "quest":
                switch (army) {
                    case "Elf":
                        return 2;
                    case "Human":
                        return 4;
                }
                break;
        }
        return -1;
    }

    private  List<Attack> createTokenAttakcs(ArmyTokenDto tokenDto, Context context){
        Cursor cursorAttacks = db.rawQuery("SELECT * FROM ARMY_ATTACK_TEST WHERE TOKEN_ID = ?", new String[]{String.valueOf(tokenDto.getId())});
        List<Attack> attackList = new ArrayList<>();
        if (cursorAttacks.moveToFirst()) {
            do {
                Attack attack = new Attack();
                attack.setId(cursorAttacks.getInt(cursorAttacks.getColumnIndexOrThrow("Id")));
                attack.setTokenID(cursorAttacks.getInt(cursorAttacks.getColumnIndexOrThrow("TOKEN_ID")));
                attack.setAttackType(armyTokenUtils.getAttackFromString(cursorAttacks.getString(cursorAttacks.getColumnIndexOrThrow("ATTACK_TYPE"))));
                attack.setStrenght(cursorAttacks.getInt(cursorAttacks.getColumnIndexOrThrow("STRENGHT")));
                attack.setDirections(armyTokenUtils.getDirectionFromString(cursorAttacks.getString(cursorAttacks.getColumnIndexOrThrow("DIRECTIONS"))));
                attack.setInitiative(cursorAttacks.getInt(cursorAttacks.getColumnIndexOrThrow("INITIATIVE")));
                attackList.add(attack);
            } while (cursorAttacks.moveToNext());
        }
        cursorAttacks.close();

        return attackList;
    }
}