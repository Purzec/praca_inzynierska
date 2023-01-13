package com.example.pracainzynierska;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pracainzynierska.commons.ArmyTokenUtils;
import com.example.pracainzynierska.commons.HexUtils;
import com.example.pracainzynierska.databinding.ActivityGameBinding;
import com.example.pracainzynierska.model.ArmyToken;
import com.example.pracainzynierska.model.Attack;
import com.example.pracainzynierska.model.DTO.ArmyTokenDto;
import com.example.pracainzynierska.model.Hex;
import com.example.pracainzynierska.model.enums.AttackType;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;


public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    SQLiteDatabase db;
    List<ArmyTokenDto> tmpArmy;
    ValueEventListener valueEventListener;
    ListView listView;
    ImageButton button;
    Button acceptButtonDraft;
    String playerName = "";
    String roomName = "";
    String role = "";
    FirebaseDatabase database;
    DatabaseReference room, messageRef;
    RelativeLayout waiting, listViewArmy, draftView;
    ConstraintLayout boardView;
    Board board = new Board();
    HexBoard hexBoard;
    TextView player1hp, player2hp;
    ArmyTokenUtils armyTokenUtils = new ArmyTokenUtils();
    HexUtils hexUtils = new HexUtils();
    List<Hex> localHexList;
    private boolean stopThread = false;

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
    private List<ArmyTokenDto> chosenArmy;

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
            } else {
                role = "quest";
            }
        }
        hexBoard = findViewById(R.id.hexBoard);
        listViewArmy = findViewById(R.id.listViewArmy);
        listView = findViewById(R.id.listArmy);
        draftView = findViewById(R.id.draft);
        player1hp = findViewById(R.id.player1hp);
        player2hp = findViewById(R.id.player2hp);
        acceptButtonDraft = findViewById(R.id.acceptDraft);
        acceptButtonDraft.setOnClickListener(this);

        if (savedInstanceState == null) {
            messageRef = FirebaseDatabase.getInstance().getReference("rooms/" + roomName);
            board.setHexBoard(hexBoard.pobierzKordy());
            messageRef.addValueEventListener(valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // pobierz wartości wszystkich pól z bazy danych i ustaw je w obiekcie klasy Board
                    if (dataSnapshot.child("message").getValue() == null){
                        Bundle bundle = new Bundle();
                        bundle.putString("fragmentName", "CreateRoomFragment");
                        Intent intent = new Intent(GameActivity.this, MenuActivity.class);
                        System.out.println("koniec gry");
                        intent.putExtras(bundle);
                        //wroc na intent menu z fragmentem po zalogowaniu
                        startActivity(intent);
                        finish();
                    }
                    board.setMessage(dataSnapshot.child("message").getValue(String.class));
                    board.setLastRound((Boolean.TRUE.equals(dataSnapshot.child("lastRound").getValue(boolean.class))));
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
                    if (dataSnapshot.child("hexBoard").getValue() != null) {
                        localHexList = board.getHexBoard();
                        List<Map<String, Object>> hexBoardData = dataSnapshot.child("hexBoard").getValue(new GenericTypeIndicator<List<Map<String, Object>>>() {
                        });
                        List<Hex> hexBoard = new ArrayList<>();

                        if (hexBoardData != null) {
                            for (Map<String, Object> hexData : hexBoardData) {
                                Hex hex = new Hex();
                                Long id = (Long) hexData.get("id");
                                hex.setId(id.intValue());
                                Long tokenID = (Long) hexData.get("tokenID");
                                hex.setTokenID(tokenID.intValue());
                                Long rotationQuantity = (Long) hexData.get("rotationQuantity");
                                hex.setRotationQuantity(rotationQuantity.intValue());
                                hex.setNeighbours(arrayListToMap((List<Long>) hexData.get("neighbours")));
                                hex.setHexpositionX(convertToFloat(hexData.get("HexpositionX")));
                                hex.setHexpositionY(convertToFloat(hexData.get("HexpositionY")));
                                hex.setBusy((Boolean) hexData.get("busy"));

                                hexBoard.add(hex);
                            }
                            board.setHexBoard(hexBoard);
                        }
                    }

                    updateView(board, localHexList);
                    if (board.getPlayer1() != null && board.getPlayer2() != null) {
                        if (board.getPlayer1().getChosenArmy() != null && board.getPlayer2().getChosenArmy() != null) {
                            player1hp.setText(String.valueOf("Zdrowie dowódcy gracza " + board.getPlayer1().getNick() + " : " + board.getPlayer1().getHpBoss()));
                            player2hp.setText(String.valueOf("Zdrowie dowódcy gracza " + board.getPlayer2().getNick() + " : " + board.getPlayer2().getHpBoss()));
                            player1hp.setVisibility(View.VISIBLE);
                            player2hp.setVisibility(View.VISIBLE);
                        }
                    }
                    if (!board.isLastRound()) {
                        game(board);
                    }else {
                        if (board.isLastRound()) {
                            messageRef.removeEventListener(valueEventListener);
                            Toast toast = Toast.makeText(getApplicationContext(), getWinner(board.getPlayer1(), board.getPlayer2()), Toast.LENGTH_LONG);
                            toast.show();

                            View toastView = toast.getView();
                            toastView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                                @Override
                                public void onViewAttachedToWindow(View v) {
                                    // Toast jest wyświetlony
                                }

                                @Override
                                public void onViewDetachedFromWindow(View v) {
                                    // Toast jest już nie widoczny
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference roomRef = database.getReference("rooms/" + roomName);
                                    roomRef.removeValue();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("fragmentName", "CreateRoomFragment");
                                    Intent intent = new Intent(GameActivity.this, MenuActivity.class);
                                    System.out.println("koniec gry");
                                    intent.putExtras(bundle);
                                    //wroc na intent menu z fragmentem po zalogowaniu
                                    startActivity(intent);
                                    finish();
                                }
                            });


                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    System.out.println("bład");
                }
            });
        }
    }

    public float convertToFloat(Object value) {
        if (value instanceof Double) {
            return ((Double) value).floatValue();
        } else if (value instanceof Long) {
            return ((Long) value).floatValue();
        } else {
            throw new IllegalArgumentException("value is not a Double or Long");
        }
    }

    public Map<String, Integer> arrayListToMap(List<Long> list) {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 1; i < list.size(); i++) {
            map.put(String.valueOf(i), list.get(i).intValue());
        }
        return map;
    }

    private void updateView(Board board, List<Hex> localHexList) {
        //mamy dwie listy lokalną i aktualną na bazie
        if (localHexList != null) {
            List<Hex> localBusyHex = localHexList.stream().filter(Hex::isBusy).collect(Collectors.toList());
            List<Hex> globalBusyHex = board.getHexBoard().stream().filter(Hex::isBusy).collect(Collectors.toList());
            Set<Integer> firstListIds = localBusyHex.stream().map(Hex::getTokenID).collect(Collectors.toSet());
            List<Hex> idToGenerate = globalBusyHex.stream()
                    .filter(e -> !firstListIds.contains(e.getTokenID()))
                    .collect(Collectors.toList());
            for (Hex hex : idToGenerate) {
                ArmyTokenDto tokenByID = getTokenByID(hex.getTokenID());
                tokenByID.setRotationQuantity(hex.getRotationQuantity());
                ArmyToken armyToken = createArmyToken(tokenByID, getApplicationContext(), mContentView);
                armyToken.setOnBoard(true);
                armyToken.setCurrentHex(hex);
                HexUtils.setHexToBoard(board.getHexBoard(), armyToken, hex.getId());
                armyToken.setVisibility(View.VISIBLE);
                boardView.addView(armyToken);
                armyToken.setOnTouchListener(null);
            }
            if (globalBusyHex.size() == board.getHexBoard().size() || board.isLastRound()) {
                Toast.makeText(getApplicationContext(), "BITWAAA", Toast.LENGTH_SHORT).show();
                List<ArmyToken> armyTokensForBattle = new ArrayList<>();
                //pobranie tokenów z widoku
                findArmyTokensForBattle(boardView, armyTokensForBattle);
                removeTokensFromLobby(armyTokensForBattle, boardView);
                //sprawdzmy czy kierunki sie dobrze zaktualizowały
                updateAllAttacksDirections(armyTokensForBattle);
                //bitwa z tokenami
                //pobierami max inicjatywe
                int maxInitiative = getMaxInitiative(armyTokensForBattle);
                for (int i = maxInitiative; i >= 0; i--) {
                    //mamy tutaj bitwe dla konretnej inicjatywy najpierw 3
                    List<ArmyToken> armyTokensByInitiative = getArmyTokensByInitiative(armyTokensForBattle, i);
                    //na tych tokenach wykonujemy atak
                    //pobierz tokena
                    for (ArmyToken armyToken : armyTokensByInitiative) {
                        performAttacks(armyToken, i, armyTokensForBattle);
                    }
                    removeDeadTokens(armyTokensForBattle, boardView, board.getHexBoard());
                }
                //remove from lobby
                board.getPlayer2().setLobbyID(new ArrayList<>());
                board.getPlayer1().setLobbyID(new ArrayList<>());
                board.getPlayer2().setHpBoss(getHealthOfCommander(board.getPlayer2().getIdChosenArmy(), armyTokensForBattle));
                board.getPlayer1().setHpBoss(getHealthOfCommander(board.getPlayer1().getIdChosenArmy(), armyTokensForBattle));




                //wykonujemy atak dla tokenów o inicjatywie x
                // usuwamy te tokeny ktore maja mniej niz 0
                //


//musimy pobrac z widoku wyszstkie tokeny jakie sie na nim znajdują i są na planszy
            }
        }
    }

    public String getWinner(Player player1, Player player2) {
        if (player1.getHpBoss() > player2.getHpBoss()) {
            return "ZWYCIĘZCĄ ZOSTAŁ GRACZ: " + player1.getNick();
        } else if (player2.getHpBoss() > player1.getHpBoss()) {
            return "ZWYCIĘZCĄ ZOSTAŁ GRACZ: " + player2.getNick();
        } else {
            return "REMIS";
        }
    }


    public void removeTokensFromLobby(List<ArmyToken> armyTokens, ViewGroup parentView) {
        Iterator<ArmyToken> iterator = armyTokens.iterator();
        while (iterator.hasNext()) {
            ArmyToken token = iterator.next();
            if (!token.isOnBoard()) {
                parentView.removeView(token);
                iterator.remove();
            }
        }
    }

    public int getHealthOfCommander(int ownerArmyId, List<ArmyToken> armyTokens) {
        for (ArmyToken token : armyTokens) {
            if (token.getArmyOwnerId() == ownerArmyId && token.getName().equals("dowodca")) {
                return token.getLife();
            }
        }
        return 0;
    }


    public void removeDeadTokens(List<ArmyToken> armyTokens, ViewGroup parentView, List<Hex> hexList) {
        Iterator<ArmyToken> iterator = armyTokens.iterator();
        while (iterator.hasNext()) {
            ArmyToken token = iterator.next();
            if (token.getLife() <= 0) {
                releaseHex(token, hexList);
                parentView.removeView(token);
                iterator.remove();
            }
        }
    }

    public void releaseHex(ArmyToken currentToken, List<Hex> hexList) {
        Hex currentHex = currentToken.getCurrentHex();
        for (Hex hex : hexList) {
            if (hex.getId() == currentHex.getId()) {
                hex.setBusy(false);
                break;
            }
        }
    }

    public void performAttacks(ArmyToken armyToken, int i, List<ArmyToken> allToken) {
        List<Attack> attacksByInitiative = getAttacksByInitiative(i, armyToken);
        //pobierz ataki o konkretnej inicjatywie
        for (Attack attack : attacksByInitiative) {
            // Sprawdzamy rodzaj ataku
            if (attack.getAttackType() == AttackType.LONG) {
                executeAttacksLong(armyToken.getCurrentHex(), armyToken, i, allToken, AttackType.LONG);
                System.out.println("Hello World");
            } else if (attack.getAttackType() == AttackType.SHORT) {
                executeAttacksShort(armyToken.getCurrentHex(), armyToken, i, allToken, AttackType.SHORT);
            }
        }
    }

    /**
     * Metoda executeAttacks przyjmuje trzy argumenty: hex na którym znajduje się token, token którego ataki mają zostać wykonane oraz inicjatywę,
     * która określa które ataki z listy ataków tokenu mają zostać wykonane.
     * Metoda pobiera mapę sąsiadów hexa na którym znajduje się token.
     * Następnie iteruje przez listę ataków tokenu i dla każdego ataku o inicjatywie takiej jak przekazana do metody,
     * pobiera wartość kierunku ataku i sprawdza czy istnieje hex o takim id na liście sąsiadów hexa na którym znajduje się token.
     * Jeśli tak, to pobiera token znajdujący się na tym hexie i odejmuje od jego punktów życia wartość siły ataku.
     */
    public void executeAttacksShort(Hex currentHex, ArmyToken currentToken, int initiative, List<ArmyToken> allArmyTokens, AttackType attackType) {
        Map<String, Integer> neighbours = currentHex.getNeighbours();
        for (int i = 0; i < currentToken.getAttacks().size(); i++) {
            if (currentToken.getAttacks().get(i).getInitiative() == initiative && currentToken.getAttacks().get(i).getAttackType() == attackType) {
                int directionValue = currentToken.getAttacks().get(i).getDirections().getDirectionValue();
                if (neighbours.get(String.valueOf(directionValue)) != null) {
                    int neighbourId = neighbours.get(String.valueOf(directionValue));
                    if (neighbourId < 0) {
                        return;
                    }
                    Hex neighbourHex = findHexById(neighbourId, board.getHexBoard());
                    System.out.println("hex id: " + neighbourHex.getId());
                    ArmyToken neighbourToken = findTokenByHexId(neighbourHex.getId(), allArmyTokens);
                    if (neighbourToken != null && neighbourToken.getArmyOwnerId() != currentToken.getArmyOwnerId()) {
                        neighbourToken.setLife(neighbourToken.getLife() - currentToken.getAttacks().get(i).getStrenght());
                    }
                }
            }
        }
    }

    /**
     * Metoda ta służy do znalezienia wrogiego ArmyToken znajdującego się w sąsiednim hexie w danym kierunku.
     * Pobiera aktualnie rozpatrywany ArmyToken oraz kierunek, w którym ma być szukany wrogi ArmyToken.
     * Następnie pobiera hex bieżącego ArmyToken i mapę jego sąsiadów.
     * Sprawdza sąsiada w danym kierunku i pobiera ArmyToken znajdujący się na tym hexie.
     * Jeśli ArmyToken ten ma takie samo ownerArmyId jak ArmyToken bieżący to pobiera mapę sąsiadów tego hexa i sprawdza sąsiada w tym samym kierunku.
     * Powtarza to tak długo, aż znajdzie hex z ArmyToken o innym ownerArmyId lub nie znajdzie już żadnego hexa.
     * Zwraca znaleziony wrogi ArmyToken.
     *
     * @param currentToken  - aktualnie rozpatrywany ArmyToken
     * @param direction     - kierunek, w którym ma być szukany wrogi ArmyToken
     * @param allArmyTokens - lista wszystkich ArmyTokenów
     * @return - znaleziony wrogi ArmyToken
     */
    public ArmyToken findAdjacentEnemy(ArmyToken currentToken, int direction, List<ArmyToken> allArmyTokens) {
        Hex currentHex = currentToken.getCurrentHex();
        Map<String, Integer> neighbours = currentHex.getNeighbours();
        if (neighbours != null) {
            int neighbourId = neighbours.get(String.valueOf(direction));
            if (neighbourId < 0) {
                return null;
            }
            Hex neighbourHex = findHexById(neighbourId, board.getHexBoard());
            ArmyToken neighbourToken = findTokenByHexId(neighbourHex.getId(), allArmyTokens);
            while (neighbourToken != null && neighbourToken.getArmyOwnerId() == currentToken.getArmyOwnerId()) {
                neighbours = neighbourHex.getNeighbours();
                if (neighbours != null) {
                    neighbourId = neighbours.get(String.valueOf(direction));
                    if (neighbourId < 0) {
                        return null;
                    }
                    neighbourHex = findHexById(neighbourId, board.getHexBoard());
                    neighbourToken = findTokenByHexId(neighbourHex.getId(), allArmyTokens);
                } else {
                    break;
                }
            }
            return neighbourToken;
        } else {
            return null;
        }
    }


    /**
     * Metoda executeAttacks przyjmuje trzy argumenty: hex na którym znajduje się token, token którego ataki mają zostać wykonane oraz inicjatywę,
     * która określa które ataki z listy ataków tokenu mają zostać wykonane.
     * Metoda pobiera mapę sąsiadów hexa na którym znajduje się token.
     * Następnie iteruje przez listę ataków tokenu i dla każdego ataku o inicjatywie takiej jak przekazana do metody,
     * pobiera wartość kierunku ataku i sprawdza czy istnieje hex o takim id na liście sąsiadów hexa na którym znajduje się token.
     * Jeśli tak, to pobiera token znajdujący się na tym hexie i odejmuje od jego punktów życia wartość siły ataku.
     */
    public void executeAttacksLong(Hex currentHex, ArmyToken currentToken, int initiative, List<ArmyToken> allArmyTokens, AttackType attackType) {
        Map<String, Integer> neighbours = currentHex.getNeighbours();
        for (int i = 0; i < currentToken.getAttacks().size(); i++) {
            if (currentToken.getAttacks().get(i).getInitiative() == initiative && currentToken.getAttacks().get(i).getAttackType() == attackType) {
                int directionValue = currentToken.getAttacks().get(i).getDirections().getDirectionValue();
                if (neighbours.get(String.valueOf(directionValue)) != null) {
                    ArmyToken adjacentEnemy = findAdjacentEnemy(currentToken, directionValue, allArmyTokens);
                    if (adjacentEnemy != null) {
                        //mamy token ktoremu musimy odjac zdrowie
                        adjacentEnemy.setLife(adjacentEnemy.getLife() - currentToken.getAttacks().get(i).getStrenght());
                    }

                }
            }
        }
    }

    public ArmyToken findTokenByHexId(int hexId, List<ArmyToken> tokens) {
        for (ArmyToken token : tokens) {
            if (token.getCurrentHex().getId() == hexId) {
                return token;
            }
        }
        return null;
    }

    public Hex findHexById(int id, List<Hex> hexList) {
        return hexList.stream().filter(hex -> hex.getId() == id).findFirst().orElse(null);
    }

    /**
     * Pobieranie ataków z ArmyToken o zadanej inicjatywie
     *
     * @param initiative - inicjatywa ataków, które chcemy pobrać
     * @return - lista ataków o zadanej inicjatywie
     */
    public List<Attack> getAttacksByInitiative(int initiative, ArmyToken armyToken) {
        // Tworzymy pustą listę, do której będziemy dodawać ataki o odpowiedniej inicjatywie
        List<Attack> attacksByInitiative = new ArrayList<>();
        // Iterujemy po liście ataków tokenu
        for (Attack attack : armyToken.getAttacks()) {
            // Jeśli inicjatywa ataku jest równa zadanej inicjatywie, dodajemy go do listy
            if (attack.getInitiative() == initiative) {
                attacksByInitiative.add(attack);
            }
        }
        // Zwracamy listę ataków o zadanej inicjatywie
        return attacksByInitiative;
    }

    public void updateAllAttacksDirections(List<ArmyToken> armyTokens) {
        for (ArmyToken armyToken : armyTokens) {
            armyToken.updateAttackDirections();
        }
    }

    /**
     * Metoda ta jest używana do przeszukania wszystkich dzieci elementów typu ViewGroup (takich jak ConstraintLayout),
     * a następnie dodając do listy ArmyToken każde dziecko, które jest instancją tej klasy.
     *
     * @param parent     - element ViewGroup, który jest przeszukiwany w celu znalezienia dzieci typu ArmyToken
     * @param armyTokens - lista, do której są dodawane znalezione elementy ArmyToken
     */
    private void findArmyTokensForBattle(ViewGroup parent, List<ArmyToken> armyTokens) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            // Sprawdzenie czy aktualnie przeszukiwane dziecko jest instancją ArmyToken
            if (child instanceof ArmyToken) {
                ArmyToken token = (ArmyToken) child;
                // Sprawdzenie czy token znajduje się na planszy
                armyTokens.add(token);
            }
            // Sprawdzenie czy aktualnie przeszukiwane dziecko jest instancją ViewGroup
            else if (child instanceof ViewGroup) {
                //Rekurencyjne wywołanie metody dla aktualnie przeszukiwanego dziecka typu ViewGroup
                findArmyTokensForBattle((ViewGroup) child, armyTokens);
            }
        }
    }

    /**
     * Metoda zwraca największą inicjatywę w liście ArmyToken
     *
     * @param armyTokens lista tokenów armii
     * @return największa inicjatywa
     */
    private int getMaxInitiative(List<ArmyToken> armyTokens) {
        int maxInitiative = 0;
        for (ArmyToken armyToken : armyTokens) {
            for (Attack attack : armyToken.getAttacks()) {
                if (attack.getInitiative() > maxInitiative) {
                    maxInitiative = attack.getInitiative();
                }
            }
        }
        return maxInitiative;
    }

    /**
     * Metoda zwraca listę tokenów armii posiadających atak o podanej inicjatywie
     *
     * @param armyTokens lista tokenów armii
     * @param initiative inicjatywa ataku
     * @return lista tokenów z atakiem o podanej inicjatywie
     */
    private List<ArmyToken> getArmyTokensByInitiative(List<ArmyToken> armyTokens, int initiative) {
        List<ArmyToken> result = new ArrayList<>();
        for (ArmyToken armyToken : armyTokens) {
            for (Attack attack : armyToken.getAttacks()) {
                if (attack.getInitiative() == initiative) {
                    result.add(armyToken);
                    break;
                }
            }
        }
        return result;
    }

    private void game(Board board) {
        if (board.getPlayer1() != null && board.getPlayer2() != null) {
            System.out.println("Wywołano metodę game");
            waiting.setVisibility(View.GONE);
            if (role.equals("host")) {
                if (board.getMessage().equals("host")) {
                    System.out.println("Ekran widzi gracz: " + board.getPlayer1().getNick());
                    gameTurn(board.getPlayer1());
                    isClickable(board.getPlayer1());
                }
            } else {
                if (board.getMessage().equals("quest")) {
                    System.out.println("Ekran widzi gracz: " + board.getPlayer2().getNick());
                    gameTurn(board.getPlayer2());
                    isClickable(board.getPlayer2());
                }
            }

        }

    }

    private void gameTurn(Player player) {
        //zrobić zabezpieczenie zeby wykonywało sie tylko w sytuacji kiedy klikamy endturn
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
                            player.setChosenArmy(armyTokenGet(getPlayerNumber(chosenArmy, board.getMessage())));
                            endTurn(view);
                        }
                    });
                }
                break;
            case 2:
                player.setFlag(false);
                ArmyToken armyTokens = createArmyToken(getBoss(player.getChosenArmy()), getApplicationContext(), mContentView);
                player.getChosenArmy().remove(player.getChosenArmy().stream().filter(tokenDto -> tokenDto.getName().equals("dowodca")).findFirst().get());
                player.setHpBoss(armyTokens.getLife());
                player.setIdChosenArmy(armyTokens.getArmyOwnerId());
                List<ArmyToken> test = new LinkedList<>();
                test.add(armyTokens);
                player.setLobbyID(test.stream().map(ArmyToken::getId).collect(Collectors.toList()));
                HexUtils.setToLobby(boardView, board.getHexBoard(), getApplicationContext(), messageRef, board, test, role);
                armyTokens.setVisibility(View.VISIBLE);
                System.out.println("postawienie dowódcy");
                //zrobic usuniecie dowodcy z armii

                player.setEtap(3);
                break;
            default:
                if (!board.isUpdating()) {
                    board.setUpdating(true);
                    noClickable();
                    System.out.println("tury normalne");
                    //draft steworzenie 3 losowych tokenoów i usuniecie ich z listy
                    List<ArmyToken> random3Tokens = createArmyTokens(pickRandomElements(player.getChosenArmy(), board), getApplicationContext(), mContentView);
                    draftView.setVisibility(View.VISIBLE);
                    draftView.bringToFront();
                    player.setDraft(HexUtils.setToDraft(random3Tokens, draftView));
                }
                break;
        }
    }

    public static List<ArmyTokenDto> pickRandomElements(List<ArmyTokenDto> list, Board board) {
        Random rand = new Random();
        List<ArmyTokenDto> result = new ArrayList<>();
        int size = list.size();
        if (size <= 3) {
            board.setLastRound(true);
            return list;
        }
        for (int i = 0; i < 3; i++) {
            int randomIndex = rand.nextInt(list.size());
            result.add(list.get(randomIndex));
            list.remove(randomIndex);
        }
        return result;
    }

    private ArmyTokenDto getBoss(List<ArmyTokenDto> chosenArmy) {
        return chosenArmy.stream().filter(tokenDto -> tokenDto.getName().equals("dowodca")).findFirst().get();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.acceptDraft:
                System.out.println("1");
                // player flaga ustawic na true
                if (role.equals("host")) {
                    if (board.getMessage().equals("host")) {
                        acceptDraft(board.getPlayer1());
                    }
                } else {
                    if (board.getMessage().equals("quest")) {
                        acceptDraft(board.getPlayer2());
                    }
                }

                break;
            case R.id.finishRound:
                endTurn(view);
                break;
        }
    }

    public void acceptDraft(Player player) {
        System.out.println("2");
        if (player.getDraft().stream().allMatch(ArmyToken::isDraftDiscard)) {
            Toast.makeText(getApplicationContext(), "KLIKNĄŁEŚ WSZYSTKIE TOKENY CONAJMNIEJ JEDEN MUSISZ DOBRAĆ", Toast.LENGTH_SHORT).show();
        } else if (player.getDraft().stream().anyMatch(ArmyToken::isDraftDiscard)) {
            //  draft.setVisibility(View.GONE);
            //dodaj do lobby
            List<ArmyToken> test = new LinkedList<>();
            test = player.getDraft().stream().filter(armyToken -> !armyToken.isDraftDiscard()).collect(Collectors.toList());

            List<ArmyToken> armyTokens = player.getDraft().stream().filter(armyToken -> !armyToken.isDraftDiscard()).collect(Collectors.toList());
            player.setLobbyID(armyTokens.stream().map(ArmyToken::getId).collect(Collectors.toList()));
            Toast.makeText(getApplicationContext(), String.valueOf(player.getLobbyID().size()), Toast.LENGTH_SHORT).show();
            player.getDraft().stream().forEach(armyToken -> draftView.removeView(armyToken));
            player.setDraft(null);
            HexUtils.setToLobby(boardView, board.getHexBoard(), getApplicationContext(), messageRef, board, test, role);
            draftView.setVisibility(View.INVISIBLE);

            updateBoard();
        } else if (player.getDraft().stream().noneMatch(ArmyToken::isDraftDiscard)) {
            Toast.makeText(getApplicationContext(), "MUSISZ ODRZUCIĆ CONAJMNIEJ JEDEN TOKEN", Toast.LENGTH_SHORT).show();
        }
    }

    public void endTurn(View view) {
        board.setUpdating(false);
        if (board.getMessage().equals("quest")) {
            noClickable();
            board.setMessage("host");
        } else {
            noClickable();
            board.setMessage("quest");
        }
        updateBoard();
    }

    private void updateBoard() {
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


    private void isClickable(Player player) {
        if (player.getLobbyID().size() == 0 && player.getEtap() >= 2 && draftView.getVisibility() != View.VISIBLE) {
            button.setEnabled(true);
            button.setVisibility(View.VISIBLE);
            button.setImageDrawable(getDrawable(R.drawable.ok_green));
        }
    }

    private void makeButtonGreen() {
        button.setEnabled(true);
        button.setVisibility(View.VISIBLE);
        button.setImageDrawable(getDrawable(R.drawable.ok));
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

    public ArmyTokenDto getTokenByID(int tokenID) {

        System.out.println(tokenID);
        //pobranie wszystkich tokenów nalezących do armi ktorą wybral sobie gracz
        ArmyTokenDto armyToken = new ArmyTokenDto();
        db = openOrCreateDatabase("PracaInzynierskaTest2", MODE_PRIVATE, null);
        Cursor c = db.rawQuery("SELECT * FROM ARMY_TOKENS_TEST WHERE ID = ?", new String[]{String.valueOf(tokenID)});
        if (c.moveToFirst()) {
            do {

                armyToken.setId(c.getInt(c.getColumnIndexOrThrow("Id")));
                armyToken.setName(c.getString(c.getColumnIndexOrThrow("name")));
                armyToken.setLife(c.getInt(c.getColumnIndexOrThrow("life")));
                armyToken.setArmyOwnerId(c.getInt(c.getColumnIndexOrThrow("ARMY_OWNER_ID")));

            } while (c.moveToNext());
        }
        db.close();
        c.close();
        return armyToken;
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
                armyToken.setArmyOwnerId(c.getInt(c.getColumnIndexOrThrow("ARMY_OWNER_ID")));
                tokensPlayer.add(armyToken);
            } while (c.moveToNext());
        }
        db.close();
        c.close();
        return tokensPlayer;
    }


    public List<ArmyToken> createArmyTokens(List<ArmyTokenDto> armyTokenDtoList, Context context, View view) {
        List<ArmyToken> result = new LinkedList<>();
        for (ArmyTokenDto tokenDto : armyTokenDtoList) {
            ArmyToken armyToken = createArmyToken(tokenDto, context, mContentView);
            result.add(armyToken);
        }
        return result;
    }

    public ArmyToken createArmyToken(ArmyTokenDto tokenDto, Context context, View view) {
        db = openOrCreateDatabase("PracaInzynierskaTest2", MODE_PRIVATE, null);
        Cursor cursorToken = db.rawQuery("SELECT * FROM ARMY_TOKENS_TEST WHERE ID = ?", new String[]{String.valueOf(tokenDto.getId())});
        ArmyToken armyToken = new ArmyToken(context);
        if (cursorToken.moveToFirst()) {
            do {
                armyToken.setId(cursorToken.getInt(cursorToken.getColumnIndexOrThrow("Id")));
                armyToken.setName(cursorToken.getString(cursorToken.getColumnIndexOrThrow("name")));
                armyToken.setLife(cursorToken.getInt(cursorToken.getColumnIndexOrThrow("life")));
                armyToken.setArmyOwnerId(cursorToken.getInt(cursorToken.getColumnIndexOrThrow("ARMY_OWNER_ID")));
                armyToken.setRotationQuantity(tokenDto.getRotationQuantity());
                armyToken.setRotation(tokenDto.getRotationQuantity() * 60);

                byte[] image = cursorToken.getBlob(cursorToken.getColumnIndexOrThrow("image"));
                Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
                Drawable drawable = new BitmapDrawable(getResources(), bmp);
                armyToken.setCancelDrawable(getDrawable(R.drawable.cancel));
                armyToken.setImgToDatabase(drawable);
                armyToken.setBackground(armyToken.getImgToDatabase());
                armyToken.setLayoutParams(new ViewGroup.LayoutParams((int) (view.getWidth() / 10), (int) (view.getHeight() / 5)));
                armyToken.setAttacks(createTokenAttakcs(tokenDto));

            } while (cursorToken.moveToNext());
        }
        cursorToken.close();
        db.close();
        armyToken.setVisibility(View.INVISIBLE);
        return armyToken;
    }

    private List<Attack> createTokenAttakcs(ArmyTokenDto tokenDto) {
        Cursor cursorAttacks = db.rawQuery("SELECT * FROM ARMY_ATTACK_TEST WHERE TOKEN_ID = ?", new String[]{String.valueOf(tokenDto.getId())});
        List<Attack> attackList = new ArrayList<>();
        if (cursorAttacks.moveToFirst()) {
            do {
                Attack attack = new Attack();
                attack.setId(cursorAttacks.getInt(cursorAttacks.getColumnIndexOrThrow("ID")));
                attack.setTokenID(cursorAttacks.getInt(cursorAttacks.getColumnIndexOrThrow("TOKEN_ID")));
                attack.setAttackType(ArmyTokenUtils.getAttackFromString(cursorAttacks.getString(cursorAttacks.getColumnIndexOrThrow("ATTACK_TYPE"))));
                attack.setStrenght(cursorAttacks.getInt(cursorAttacks.getColumnIndexOrThrow("STRENGHT")));
                attack.setDirections(ArmyTokenUtils.getDirectionFromString(cursorAttacks.getString(cursorAttacks.getColumnIndexOrThrow("DIRECTIONS"))));
                attack.setInitiative(cursorAttacks.getInt(cursorAttacks.getColumnIndexOrThrow("INITIATIVE")));
                attackList.add(attack);
            } while (cursorAttacks.moveToNext());
        }
        cursorAttacks.close();

        return attackList;
    }

    private void lobbyEmpty(Player player) {
        if (player.getLobbyID().isEmpty()) {
            makeButtonGreen();
        }
    }

}