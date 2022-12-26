package com.example.pracainzynierska;

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
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.pracainzynierska.commons.HexUtils;
import com.example.pracainzynierska.databinding.ActivityGameBinding;
import com.example.pracainzynierska.model.ArmyToken;
import com.example.pracainzynierska.model.DTO.ArmyTokenDto;
import com.example.pracainzynierska.model.Hex;
import com.example.pracainzynierska.model.gameStatus.Board;
import com.example.pracainzynierska.model.gameStatus.Player;
import com.example.pracainzynierska.model.view.HexBoard;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    SQLiteDatabase db;
    private ArrayList<Hex> listatest;
    ListView listView;
    ImageButton button;
    String playerName = "";
    String roomName = "";
    String role = "";
    FirebaseDatabase database;
    DatabaseReference messageRef, room, test;
    RelativeLayout waiting, listViewArmy;
    ConstraintLayout boardView;
    Board board = new Board();
    HexBoard hexBoard;
    ValueEventListener test2;
    private DatabaseReference mDatabase;
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


        mDatabase = FirebaseDatabase.getInstance().getReference();


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
            } else {
                role = "quest";
            }
        }
        hexBoard = findViewById(R.id.hexBoard);
        if (savedInstanceState == null) {
            room = database.getReference("rooms/" + roomName);
            room.child("message").setValue(role);
            // everything else that doesn't update UI
            System.out.println("dodano listenera");
            addRoomEventListener();
        }
    }

    private void addRoomEventListener() {
        room.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                board = snapshot.getValue(Board.class);
                if (role.equals("host")) {
                    if (board.getMessage().equals("quest")) {
                        button.setEnabled(true);
                        button.setVisibility(View.VISIBLE);
                        game(board);
                    }
                } else {
                    if (board.getMessage().equals("host")) {
                        button.setEnabled(true);
                        button.setVisibility(View.VISIBLE);
                        game(board);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void game(Board board) {
        if (board.getPlayer1() != null && board.getPlayer2() != null) {
            System.out.println("Wywołano metodę game");
            waiting.setVisibility(View.GONE);
            if (board.getMessage().equals("host")) {
                System.out.println("Ekran widzi gracz: " + board.getPlayer1().getNick());
                board.setMessage(role);
                gameTurn(board.getPlayer1());

            } else {
                System.out.println("Ekran widzi gracz: " + board.getPlayer2().getNick());
                board.setMessage(role);
                gameTurn(board.getPlayer2());
            }
        }
    }

    private void gameTurn(Player player/*, DataSnapshot snapshot*/) {
        switch (player.getEtap()) {
            case 1:
                if (player.getChosenArmy()==null) {
                    System.out.println("wybór armii");
                    listViewArmy = findViewById(R.id.listViewArmy);
                    listView = findViewById(R.id.listArmy);
                    List<String> armyList = Arrays.asList("Elf", "Human");
                    listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, armyList));
                    listViewArmy.setVisibility(View.VISIBLE);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            String chosenArmy = armyList.get(i);
                            System.out.println(chosenArmy);
                            player.setChosenArmy(armyTokenGet());
                            //  player.setEtap(2);
                            listViewArmy.setVisibility(View.GONE);
                            endTurn(view);
                        }
                    });
                }

                break;
            case 2:
                System.out.println("postawienie dowódcy");
                //pobierz gracza o nazwie dowódca
             /*   ArmyTokenDto tokenDto = player.getChosenArmy().stream().filter(armyTokenDto -> armyTokenDto.getName().equals("dowodca")).collect(Collectors.toList()).get(0);
                // przerób dto na obiekt taki ładny
                ArmyToken armyToken = createArmyToken(tokenDto, getApplicationContext());
                armyToken.setBackground(armyToken.getImgToDatabase());
                HexUtils.setToLobby(Arrays.asList(armyToken), boardView, listatest, getApplicationContext(), player);*/
                break;
            default:
                System.out.println("tury normalne");
                break;
        }
    }

    @Override
    public void onClick(View view) {
        endTurn(view);
    }

    public void endTurn(View view) {
        button.setEnabled(false);
        button.setVisibility(View.GONE);
        board.setMessage(role);
        room.setValue(board);
        Thread.currentThread().interrupt();
    }


    public List<ArmyTokenDto> armyTokenGet() {
        int ownerArmyId = 1;
        //pobranie wszystkich tokenów nalezących do armi ktorą wybral sobie gracz
        List<ArmyTokenDto> tokensPlayer = new ArrayList<>();
        db = openOrCreateDatabase("PracaInzynierskaTest", MODE_PRIVATE, null);
        Cursor c = db.rawQuery("SELECT * FROM ARMY_TOKENS_TEST WHERE ARMY_OWNER_ID = ?", new String[]{String.valueOf(ownerArmyId)});
        if (c.moveToFirst()) {
            do {
                ArmyTokenDto armyToken = new ArmyTokenDto();
                armyToken.setId(c.getInt(c.getColumnIndexOrThrow("Id")));
                armyToken.setName(c.getString(c.getColumnIndexOrThrow("name")));
                armyToken.setInitiative(c.getInt(c.getColumnIndexOrThrow("initiative")));
                armyToken.setLife(c.getInt(c.getColumnIndexOrThrow("life")));
                tokensPlayer.add(armyToken);
            } while (c.moveToNext());
        }
        db.close();
        c.close();
        return tokensPlayer;
    }

    //todo jp pobierz z bazy i dodaj do niego listę armii


    public ArmyToken createArmyToken(ArmyTokenDto tokenDto, Context context) {
        db = openOrCreateDatabase("PracaInzynierskaTest", MODE_PRIVATE, null);
        Cursor c = db.rawQuery("SELECT * FROM ARMY_TOKENS_TEST WHERE ID = ?", new String[]{String.valueOf(tokenDto.getId())});
        List<ArmyToken> armyTokens = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                ArmyToken armyToken = new ArmyToken(context);
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
                armyTokens.add(armyToken);
            } while (c.moveToNext());
        }
        db.close();
        c.close();
        return armyTokens.get(0);
    }

}