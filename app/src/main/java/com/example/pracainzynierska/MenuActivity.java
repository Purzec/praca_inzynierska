package com.example.pracainzynierska;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowInsets;
import android.widget.Button;

import com.example.pracainzynierska.commons.ArmyTokenUtils;
import com.example.pracainzynierska.databinding.ActivityMenuBinding;
import com.example.pracainzynierska.model.Attack;
import com.example.pracainzynierska.model.DTO.ArmyTokenDto;
import com.example.pracainzynierska.model.DTO.AttackDto;
import com.example.pracainzynierska.model.fragments.CreateRoomFragments;
import com.example.pracainzynierska.model.fragments.LoginFragment;

import java.io.ByteArrayOutputStream;
import java.sql.Statement;
import java.util.List;


public class MenuActivity extends AppCompatActivity{



    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
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

    private ActivityMenuBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mContentView = binding.menu;
        initDatabase();
        String fragmentName = getIntent().getStringExtra("fragmentName");
        if (fragmentName != null) {
            switch (fragmentName) {
                case "CreateRoomFragment":
                    CreateRoomFragments createRoomFragments = new CreateRoomFragments();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainerView, createRoomFragments, "loginFragment")
                            .addToBackStack(null)
                            .commit();
                    break;
            }
        }
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


    SQLiteDatabase db;
    ArmyTokenUtils armyTokenUtils = new ArmyTokenUtils();

    //todo jp zmienic baze danych i tabele gdzy nie bedize juz testu
    private void initDatabase() {
        db = openOrCreateDatabase("PracaInzynierskaTest2", MODE_PRIVATE, null);
        String sqlDB = "CREATE TABLE IF NOT EXISTS" +
                " ARMY_TOKENS_TEST (Id INTEGER, name VARCHAR, " +
                "life INTEGER,image BLOB,ARMY_OWNER_ID INTEGER)";
        db.execSQL(sqlDB);
        String sqlCount = "SELECT count(*) FROM ARMY_TOKENS_TEST WHERE ARMY_OWNER_ID = 1";
        Cursor cursor = db.rawQuery(sqlCount, null);
        cursor.moveToFirst();
        int ilosc = cursor.getInt(0);
        cursor.close();
        System.out.println("ilosc:" +ilosc);
        if (ilosc == 0) {
            String sqlArmyToken = "INSERT INTO ARMY_TOKENS_TEST VALUES (?,?,?,?,?)";
            SQLiteStatement statement = db.compileStatement(sqlArmyToken);
            List<ArmyTokenDto> armyElfTokensA = armyTokenUtils.initElfArmyA(getApplicationContext());
            addToDAtabaseArmyToken(armyElfTokensA, statement);
            List<ArmyTokenDto> armyElfTokensB = armyTokenUtils.initElfArmyB(getApplicationContext());
            addToDAtabaseArmyToken(armyElfTokensB, statement);
            List<ArmyTokenDto> armyHumanTokensC = armyTokenUtils.initHumanArmyC(getApplicationContext());
            addToDAtabaseArmyToken(armyHumanTokensC, statement);
            List<ArmyTokenDto> armyHumanTokensD = armyTokenUtils.initHumanArmyD(getApplicationContext());
            addToDAtabaseArmyToken(armyHumanTokensD, statement);
        }

        String sqlAttackList = "CREATE TABLE IF NOT EXISTS ARMY_ATTACK_TEST" +
                " (ID Integer, TOKEN_ID INTEGER, ATTACK_TYPE VARCHAR, STRENGHT INTEGER, DIRECTIONS VARCHAR, INITIATIVE Integer)";
        db.execSQL(sqlAttackList);
        String sqlCountArmyAttack = "SELECT count(*) FROM ARMY_ATTACK_TEST";
        Cursor cursorArmyAttack = db.rawQuery(sqlCountArmyAttack, null);
        cursorArmyAttack.moveToFirst();
        int countArmyAttack = cursorArmyAttack.getInt(0);
        cursorArmyAttack.close();
        System.out.println("countArmyAttack:" +countArmyAttack);
        if (countArmyAttack == 0) {
            String sqlArmyAttacks = "INSERT INTO ARMY_ATTACK_TEST VALUES(?,?,?,?,?,?)";
            SQLiteStatement statementArmyAttacks = db.compileStatement(sqlArmyAttacks);
            List<AttackDto> elfArmyAttackA = armyTokenUtils.initElfArmyAttackA();
            addToDatabaseAttackToken(elfArmyAttackA, statementArmyAttacks);
            List<AttackDto> elfArmyAttackB = armyTokenUtils.initElfArmyAttackB();
            addToDatabaseAttackToken(elfArmyAttackB, statementArmyAttacks);
            List<AttackDto> humanArmyAttackC = armyTokenUtils.initHumanArmyAttackC();
            addToDatabaseAttackToken(humanArmyAttackC, statementArmyAttacks);
            List<AttackDto> humanArmyAttackD = armyTokenUtils.initHumanArmyAttackD();
            addToDatabaseAttackToken(humanArmyAttackD, statementArmyAttacks);
        }
    }

    private void addToDatabaseAttackToken(List<AttackDto> elfArmyAttack, SQLiteStatement statement) {
        for (AttackDto attackDto : elfArmyAttack) {
            statement.bindLong(1, attackDto.getId());
            statement.bindLong(2, attackDto.getTokenID());
            statement.bindString(3, attackDto.getAttackType().name());
            statement.bindLong(4, attackDto.getStrenght());
            statement.bindString(5,attackDto.getDirections().name());
            statement.bindLong(6,attackDto.getInitiative());
            statement.executeInsert();
        }
    }

    private void addToDAtabaseArmyToken(List<ArmyTokenDto> armyTokenDtoList, SQLiteStatement statement) {
        for (ArmyTokenDto tokenDto : armyTokenDtoList) {
            statement.bindLong(1, tokenDto.getId());
            statement.bindString(2, tokenDto.getName());
            statement.bindLong(3, tokenDto.getLife());
            Bitmap bitmap = ((BitmapDrawable) tokenDto.getImgToDatabase()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] bitmapdata = stream.toByteArray();
            statement.bindBlob(4, bitmapdata);
            statement.bindLong(5, tokenDto.getArmyOwnerId());
            statement.executeInsert();
        }
    }
}