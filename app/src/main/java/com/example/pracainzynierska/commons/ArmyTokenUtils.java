package com.example.pracainzynierska.commons;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pracainzynierska.R;
import com.example.pracainzynierska.model.Attack;
import com.example.pracainzynierska.model.DTO.ArmyTokenDto;
import com.example.pracainzynierska.model.DTO.AttackDto;
import com.example.pracainzynierska.model.enums.AttackType;
import com.example.pracainzynierska.model.enums.Directions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArmyTokenUtils extends AppCompatActivity {

    //stworzenie armii elfów

    public List<ArmyTokenDto> initElfArmyA(Context context) {

        Drawable drawable1 = context.getApplicationContext().getDrawable(R.drawable.token1a);
        Drawable drawable2 = context.getApplicationContext().getDrawable(R.drawable.token2a);
        Drawable drawable3 = context.getApplicationContext().getDrawable(R.drawable.token3a);
        Drawable drawable4 = context.getApplicationContext().getDrawable(R.drawable.token4a);
        Drawable drawable5 = context.getApplicationContext().getDrawable(R.drawable.token5a);
        Drawable drawable6 = context.getApplicationContext().getDrawable(R.drawable.token6a);
        Drawable drawable7 = context.getApplicationContext().getDrawable(R.drawable.token7a);
        Drawable drawable8 = context.getApplicationContext().getDrawable(R.drawable.token8a);
        Drawable drawable9 = context.getApplicationContext().getDrawable(R.drawable.token9a);

        List<ArmyTokenDto> armyTokenDtoList = new ArrayList<>();


        ArmyTokenDto tokenDto1 = new ArmyTokenDto(1, "zwiadowca", 1, drawable1, 1);
        ArmyTokenDto tokenDto2 = new ArmyTokenDto(2, "zwiadowca", 1, drawable1, 1);
        ArmyTokenDto tokenDto3 = new ArmyTokenDto(3, "zwiadowca", 1, drawable1, 1);

        ArmyTokenDto tokenDto4 = new ArmyTokenDto(4, "łucznik", 1, drawable2, 1);
        ArmyTokenDto tokenDto5 = new ArmyTokenDto(5, "łucznik", 1, drawable2, 1);
        ArmyTokenDto tokenDto6 = new ArmyTokenDto(6, "łucznik", 1, drawable2, 1);

        ArmyTokenDto tokenDto7 = new ArmyTokenDto(7, "dowodca", 20, drawable3, 1);

        ArmyTokenDto tokenDto8 = new ArmyTokenDto(8, "snajper", 1, drawable4, 1);
        ArmyTokenDto tokenDto9 = new ArmyTokenDto(9, "snajper", 1, drawable4, 1);
        ArmyTokenDto tokenDto10 = new ArmyTokenDto(10, "snajper", 1, drawable4, 1);

        ArmyTokenDto tokenDto11 = new ArmyTokenDto(11, "kusznik", 1, drawable5, 1);
        ArmyTokenDto tokenDto12 = new ArmyTokenDto(12, "kusznik", 1, drawable5, 1);
        ArmyTokenDto tokenDto13 = new ArmyTokenDto(13, "kusznik", 1, drawable5, 1);

        ArmyTokenDto tokenDto14 = new ArmyTokenDto(14, "elitarny zwiadowca", 1, drawable6, 1);
        ArmyTokenDto tokenDto15 = new ArmyTokenDto(15, "elitarny zwiadowca", 1, drawable6, 1);
        ArmyTokenDto tokenDto16 = new ArmyTokenDto(16, "elitarny zwiadowca", 1, drawable6, 1);

        ArmyTokenDto tokenDto17 = new ArmyTokenDto(17, "wojowniczka", 1, drawable7, 1);
        ArmyTokenDto tokenDto18 = new ArmyTokenDto(18, "wojowniczka", 1, drawable7, 1);
        ArmyTokenDto tokenDto19 = new ArmyTokenDto(19, "wojowniczka", 1, drawable7, 1);


        ArmyTokenDto tokenDto20 = new ArmyTokenDto(20, "wojownik", 1, drawable8, 1);
        ArmyTokenDto tokenDto21 = new ArmyTokenDto(21, "wojownik", 1, drawable8, 1);
        ArmyTokenDto tokenDto22 = new ArmyTokenDto(22, "wojownik", 1, drawable8, 1);

        ArmyTokenDto tokenDto23 = new ArmyTokenDto(23, "mag", 1, drawable9, 1);
        ArmyTokenDto tokenDto24 = new ArmyTokenDto(24, "mag", 1, drawable9, 1);
        ArmyTokenDto tokenDto25 = new ArmyTokenDto(25, "mag", 1, drawable9, 1);

        armyTokenDtoList.add(tokenDto1);
        armyTokenDtoList.add(tokenDto2);
        armyTokenDtoList.add(tokenDto3);
        armyTokenDtoList.add(tokenDto4);
        armyTokenDtoList.add(tokenDto5);
        armyTokenDtoList.add(tokenDto6);
        armyTokenDtoList.add(tokenDto7);
        armyTokenDtoList.add(tokenDto8);
        armyTokenDtoList.add(tokenDto9);
        armyTokenDtoList.add(tokenDto10);
        armyTokenDtoList.add(tokenDto11);
        armyTokenDtoList.add(tokenDto12);
        armyTokenDtoList.add(tokenDto13);
        armyTokenDtoList.add(tokenDto14);
        armyTokenDtoList.add(tokenDto15);
        armyTokenDtoList.add(tokenDto16);
        armyTokenDtoList.add(tokenDto17);
        armyTokenDtoList.add(tokenDto18);
        armyTokenDtoList.add(tokenDto19);
        armyTokenDtoList.add(tokenDto20);
        armyTokenDtoList.add(tokenDto21);
        armyTokenDtoList.add(tokenDto22);
        armyTokenDtoList.add(tokenDto23);
        armyTokenDtoList.add(tokenDto24);
        armyTokenDtoList.add(tokenDto25);

        return armyTokenDtoList;
    }


    public List<AttackDto> initElfArmyAttackA() {
        AttackDto attack1 = new AttackDto(1, 1, AttackType.SHORT, 1, Directions.FORWARD_LEFT, 2);
        AttackDto attack2 = new AttackDto(2, 2, AttackType.SHORT, 1, Directions.FORWARD_LEFT, 2);
        AttackDto attack3 = new AttackDto(3, 3, AttackType.SHORT, 1, Directions.FORWARD_LEFT, 2);

        AttackDto attack4 = new AttackDto(4, 4, AttackType.LONG, 1, Directions.FORWARD, 2);
        AttackDto attack5 = new AttackDto(5, 4, AttackType.LONG, 1, Directions.FORWARD, 1);
        AttackDto attack6 = new AttackDto(6, 5, AttackType.LONG, 1, Directions.FORWARD, 2);
        AttackDto attack7 = new AttackDto(7, 5, AttackType.LONG, 1, Directions.FORWARD, 1);
        AttackDto attack8 = new AttackDto(8, 6, AttackType.LONG, 1, Directions.FORWARD, 2);
        AttackDto attack9 = new AttackDto(9, 6, AttackType.LONG, 1, Directions.FORWARD, 1);

        AttackDto attack10 = new AttackDto(10, 7, AttackType.SHORT, 1, Directions.FORWARD, 0);
        AttackDto attack53 = new AttackDto(10, 7, AttackType.SHORT, 1, Directions.FORWARD_RIGHT, 0);
        AttackDto attack54 = new AttackDto(10, 7, AttackType.SHORT, 1, Directions.BACK_RIGHT, 0);
        AttackDto attack55 = new AttackDto(10, 7, AttackType.SHORT, 1, Directions.BACK, 0);
        AttackDto attack56 = new AttackDto(10, 7, AttackType.SHORT, 1, Directions.BACK_LEFT, 0);
        AttackDto attack57 = new AttackDto(10, 7, AttackType.SHORT, 1, Directions.FORWARD_LEFT, 0);

        AttackDto attack11 = new AttackDto(11, 8, AttackType.LONG, 1, Directions.BACK_RIGHT, 3);
        AttackDto attack12 = new AttackDto(12, 9, AttackType.LONG, 1, Directions.BACK_RIGHT, 3);
        AttackDto attack13 = new AttackDto(13, 10, AttackType.LONG, 1, Directions.BACK_RIGHT, 3);

        AttackDto attack14 = new AttackDto(14, 11, AttackType.LONG, 2, Directions.FORWARD_LEFT, 2);
        AttackDto attack15 = new AttackDto(15, 12, AttackType.LONG, 2, Directions.FORWARD_LEFT, 2);
        AttackDto attack16 = new AttackDto(16, 13, AttackType.LONG, 2, Directions.FORWARD_LEFT, 2);


        AttackDto attack17 = new AttackDto(17, 14, AttackType.LONG, 1, Directions.FORWARD_LEFT, 3);
        AttackDto attack18 = new AttackDto(18, 14, AttackType.SHORT, 2, Directions.FORWARD, 3);
        AttackDto attack19 = new AttackDto(19, 14, AttackType.LONG, 1, Directions.FORWARD_LEFT, 2);
        AttackDto attack20 = new AttackDto(20, 14, AttackType.SHORT, 2, Directions.FORWARD, 2);
        AttackDto attack21 = new AttackDto(21, 15, AttackType.LONG, 1, Directions.FORWARD_LEFT, 3);
        AttackDto attack22 = new AttackDto(22, 15, AttackType.SHORT, 2, Directions.FORWARD, 3);
        AttackDto attack23 = new AttackDto(23, 15, AttackType.LONG, 1, Directions.FORWARD_LEFT, 2);
        AttackDto attack24 = new AttackDto(24, 15, AttackType.SHORT, 2, Directions.FORWARD, 2);
        AttackDto attack25 = new AttackDto(25, 16, AttackType.LONG, 1, Directions.FORWARD_LEFT, 3);
        AttackDto attack26 = new AttackDto(26, 16, AttackType.SHORT, 2, Directions.FORWARD, 3);
        AttackDto attack27 = new AttackDto(27, 16, AttackType.LONG, 1, Directions.FORWARD_LEFT, 2);
        AttackDto attack28 = new AttackDto(28, 16, AttackType.SHORT, 2, Directions.FORWARD, 2);

        AttackDto attack29 = new AttackDto(29, 17, AttackType.SHORT, 2, Directions.FORWARD, 3);
        AttackDto attack30 = new AttackDto(30, 18, AttackType.SHORT, 2, Directions.FORWARD, 3);
        AttackDto attack31 = new AttackDto(31, 19, AttackType.SHORT, 2, Directions.FORWARD, 3);

        AttackDto attack32 = new AttackDto(32, 20, AttackType.SHORT, 1, Directions.FORWARD_RIGHT, 1);
        AttackDto attack33 = new AttackDto(33, 20, AttackType.SHORT, 1, Directions.BACK_RIGHT, 1);
        AttackDto attack34 = new AttackDto(34, 20, AttackType.SHORT, 1, Directions.FORWARD_LEFT, 1);
        AttackDto attack35 = new AttackDto(35, 21, AttackType.SHORT, 1, Directions.FORWARD_RIGHT, 1);
        AttackDto attack36 = new AttackDto(36, 21, AttackType.SHORT, 1, Directions.BACK_RIGHT, 1);
        AttackDto attack37 = new AttackDto(37, 21, AttackType.SHORT, 1, Directions.FORWARD_LEFT, 1);
        AttackDto attack38 = new AttackDto(38, 22, AttackType.SHORT, 1, Directions.FORWARD_RIGHT, 1);
        AttackDto attack39 = new AttackDto(39, 22, AttackType.SHORT, 1, Directions.BACK_RIGHT, 1);
        AttackDto attack40 = new AttackDto(40, 22, AttackType.SHORT, 1, Directions.FORWARD_LEFT, 1);

        AttackDto attack41 = new AttackDto(41, 23, AttackType.LONG, 1, Directions.FORWARD, 0);
        AttackDto attack42 = new AttackDto(42, 23, AttackType.LONG, 1, Directions.FORWARD_RIGHT, 0);
        AttackDto attack43 = new AttackDto(43, 23, AttackType.LONG, 1, Directions.BACK_RIGHT, 0);
        AttackDto attack44 = new AttackDto(44, 23, AttackType.LONG, 1, Directions.FORWARD_LEFT, 0);
        AttackDto attack45 = new AttackDto(45, 24, AttackType.LONG, 1, Directions.FORWARD, 0);
        AttackDto attack46 = new AttackDto(46, 24, AttackType.LONG, 1, Directions.FORWARD_RIGHT, 0);
        AttackDto attack47 = new AttackDto(47, 24, AttackType.LONG, 1, Directions.BACK_RIGHT, 0);
        AttackDto attack48 = new AttackDto(48, 24, AttackType.LONG, 1, Directions.FORWARD_LEFT, 0);
        AttackDto attack49 = new AttackDto(49, 25, AttackType.LONG, 1, Directions.FORWARD, 0);
        AttackDto attack50 = new AttackDto(50, 25, AttackType.LONG, 1, Directions.FORWARD_RIGHT, 0);
        AttackDto attack51 = new AttackDto(51, 25, AttackType.LONG, 1, Directions.BACK_RIGHT, 0);
        AttackDto attack52 = new AttackDto(52, 25, AttackType.LONG, 1, Directions.FORWARD_LEFT, 0);


        List<AttackDto> attacks = new ArrayList<>();

        attacks.add(attack1);
        attacks.add(attack2);
        attacks.add(attack3);
        attacks.add(attack4);
        attacks.add(attack5);
        attacks.add(attack6);
        attacks.add(attack7);
        attacks.add(attack8);
        attacks.add(attack9);
        attacks.add(attack10);
        attacks.add(attack11);
        attacks.add(attack12);
        attacks.add(attack13);
        attacks.add(attack14);
        attacks.add(attack15);
        attacks.add(attack16);
        attacks.add(attack17);
        attacks.add(attack18);
        attacks.add(attack19);
        attacks.add(attack20);
        attacks.add(attack21);
        attacks.add(attack22);
        attacks.add(attack23);
        attacks.add(attack24);
        attacks.add(attack25);
        attacks.add(attack26);
        attacks.add(attack27);
        attacks.add(attack28);
        attacks.add(attack29);
        attacks.add(attack30);
        attacks.add(attack31);
        attacks.add(attack32);
        attacks.add(attack33);
        attacks.add(attack34);
        attacks.add(attack35);
        attacks.add(attack36);
        attacks.add(attack37);
        attacks.add(attack38);
        attacks.add(attack39);
        attacks.add(attack40);
        attacks.add(attack41);
        attacks.add(attack42);
        attacks.add(attack43);
        attacks.add(attack44);
        attacks.add(attack45);
        attacks.add(attack46);
        attacks.add(attack47);
        attacks.add(attack48);
        attacks.add(attack49);
        attacks.add(attack50);
        attacks.add(attack51);
        attacks.add(attack52);
        attacks.add(attack53);
        attacks.add(attack54);
        attacks.add(attack55);
        attacks.add(attack56);
        attacks.add(attack57);

        return attacks;
    }

    public List<ArmyTokenDto> initElfArmyB(Context context) {

        Drawable drawable1 = context.getApplicationContext().getDrawable(R.drawable.token1b);
        Drawable drawable2 = context.getApplicationContext().getDrawable(R.drawable.token2b);
        Drawable drawable3 = context.getApplicationContext().getDrawable(R.drawable.token3b);
        Drawable drawable4 = context.getApplicationContext().getDrawable(R.drawable.token4b);
        Drawable drawable5 = context.getApplicationContext().getDrawable(R.drawable.token5b);
        Drawable drawable6 = context.getApplicationContext().getDrawable(R.drawable.token6b);
        Drawable drawable7 = context.getApplicationContext().getDrawable(R.drawable.token7b);
        Drawable drawable8 = context.getApplicationContext().getDrawable(R.drawable.token8b);
        Drawable drawable9 = context.getApplicationContext().getDrawable(R.drawable.token9b);
        Drawable drawable10 = context.getApplicationContext().getDrawable(R.drawable.token10c);

        List<ArmyTokenDto> armyTokenDtoList = new ArrayList<>();


        ArmyTokenDto tokenDto1 = new ArmyTokenDto(26, "zwiadowca", 1, drawable1, 2);
        ArmyTokenDto tokenDto2 = new ArmyTokenDto(27, "zwiadowca", 1, drawable1, 2);
        ArmyTokenDto tokenDto3 = new ArmyTokenDto(28, "zwiadowca", 1, drawable1, 2);

        ArmyTokenDto tokenDto4 = new ArmyTokenDto(29, "łucznik", 1, drawable2, 2);
        ArmyTokenDto tokenDto5 = new ArmyTokenDto(30, "łucznik", 1, drawable2, 2);
        ArmyTokenDto tokenDto6 = new ArmyTokenDto(31, "łucznik", 1, drawable2, 2);

        ArmyTokenDto tokenDto7 = new ArmyTokenDto(32, "dowodca", 20, drawable3, 2);

        ArmyTokenDto tokenDto8 = new ArmyTokenDto(33, "snajper", 1, drawable4, 2);
        ArmyTokenDto tokenDto9 = new ArmyTokenDto(34, "snajper", 1, drawable4, 2);
        ArmyTokenDto tokenDto10 = new ArmyTokenDto(35, "snajper", 1, drawable4, 2);

        ArmyTokenDto tokenDto11 = new ArmyTokenDto(36, "kusznik", 1, drawable5, 2);
        ArmyTokenDto tokenDto12 = new ArmyTokenDto(37, "kusznik", 1, drawable5, 2);
        ArmyTokenDto tokenDto13 = new ArmyTokenDto(38, "kusznik", 1, drawable5, 2);

        ArmyTokenDto tokenDto14 = new ArmyTokenDto(39, "elitarny zwiadowca", 1, drawable6, 2);
        ArmyTokenDto tokenDto15 = new ArmyTokenDto(40, "elitarny zwiadowca", 1, drawable6, 2);
        ArmyTokenDto tokenDto16 = new ArmyTokenDto(41, "elitarny zwiadowca", 1, drawable6, 2);

        ArmyTokenDto tokenDto17 = new ArmyTokenDto(42, "wojowniczka", 1, drawable7, 2);
        ArmyTokenDto tokenDto18 = new ArmyTokenDto(43, "wojowniczka", 1, drawable7, 2);
        ArmyTokenDto tokenDto19 = new ArmyTokenDto(44, "wojowniczka", 1, drawable7, 2);


        ArmyTokenDto tokenDto20 = new ArmyTokenDto(45, "wojownik", 1, drawable8, 2);
        ArmyTokenDto tokenDto21 = new ArmyTokenDto(46, "wojownik", 1, drawable8, 2);
        ArmyTokenDto tokenDto22 = new ArmyTokenDto(47, "wojownik", 1, drawable8, 2);

        ArmyTokenDto tokenDto23 = new ArmyTokenDto(48, "mag", 1, drawable9, 2);
        ArmyTokenDto tokenDto24 = new ArmyTokenDto(49, "mag", 1, drawable9, 2);
        ArmyTokenDto tokenDto25 = new ArmyTokenDto(50, "mag", 1, drawable9, 2);

        armyTokenDtoList.add(tokenDto1);
        armyTokenDtoList.add(tokenDto2);
        armyTokenDtoList.add(tokenDto3);
        armyTokenDtoList.add(tokenDto4);
        armyTokenDtoList.add(tokenDto5);
        armyTokenDtoList.add(tokenDto6);
        armyTokenDtoList.add(tokenDto7);
        armyTokenDtoList.add(tokenDto8);
        armyTokenDtoList.add(tokenDto9);
        armyTokenDtoList.add(tokenDto10);
        armyTokenDtoList.add(tokenDto11);
        armyTokenDtoList.add(tokenDto12);
        armyTokenDtoList.add(tokenDto13);
        armyTokenDtoList.add(tokenDto14);
        armyTokenDtoList.add(tokenDto15);
        armyTokenDtoList.add(tokenDto16);
        armyTokenDtoList.add(tokenDto17);
        armyTokenDtoList.add(tokenDto18);
        armyTokenDtoList.add(tokenDto19);
        armyTokenDtoList.add(tokenDto20);
        armyTokenDtoList.add(tokenDto21);
        armyTokenDtoList.add(tokenDto22);
        armyTokenDtoList.add(tokenDto23);
        armyTokenDtoList.add(tokenDto24);
        armyTokenDtoList.add(tokenDto25);

        return armyTokenDtoList;
    }

    public List<AttackDto> initElfArmyAttackB() {
        AttackDto attack1 = new AttackDto(52, 26, AttackType.SHORT, 1, Directions.FORWARD_LEFT, 2);
        AttackDto attack2 = new AttackDto(53, 27, AttackType.SHORT, 1, Directions.FORWARD_LEFT, 2);
        AttackDto attack3 = new AttackDto(54, 28, AttackType.SHORT, 1, Directions.FORWARD_LEFT, 2);

        AttackDto attack4 = new AttackDto(55, 29, AttackType.LONG, 1, Directions.FORWARD, 2);
        AttackDto attack5 = new AttackDto(56, 29, AttackType.LONG, 1, Directions.FORWARD, 1);
        AttackDto attack6 = new AttackDto(57, 30, AttackType.LONG, 1, Directions.FORWARD, 2);
        AttackDto attack7 = new AttackDto(58, 30, AttackType.LONG, 1, Directions.FORWARD, 1);
        AttackDto attack8 = new AttackDto(59, 31, AttackType.LONG, 1, Directions.FORWARD, 2);
        AttackDto attack9 = new AttackDto(60, 31, AttackType.LONG, 1, Directions.FORWARD, 1);

        AttackDto attack10 = new AttackDto(61, 32, AttackType.SHORT, 1, Directions.FORWARD, 0);
        AttackDto attack53 = new AttackDto(61, 32, AttackType.SHORT, 1, Directions.FORWARD_RIGHT, 0);
        AttackDto attack54 = new AttackDto(61, 32, AttackType.SHORT, 1, Directions.BACK_RIGHT, 0);
        AttackDto attack55 = new AttackDto(61, 32, AttackType.SHORT, 1, Directions.BACK, 0);
        AttackDto attack56 = new AttackDto(61, 32, AttackType.SHORT, 1, Directions.BACK_LEFT, 0);
        AttackDto attack57 = new AttackDto(61, 32, AttackType.SHORT, 1, Directions.FORWARD_LEFT, 0);

        AttackDto attack11 = new AttackDto(62, 33, AttackType.LONG, 1, Directions.BACK_RIGHT, 3);
        AttackDto attack12 = new AttackDto(63, 34, AttackType.LONG, 1, Directions.BACK_RIGHT, 3);
        AttackDto attack13 = new AttackDto(64, 35, AttackType.LONG, 1, Directions.BACK_RIGHT, 3);

        AttackDto attack14 = new AttackDto(65, 36, AttackType.LONG, 2, Directions.FORWARD_LEFT, 2);
        AttackDto attack15 = new AttackDto(66, 37, AttackType.LONG, 2, Directions.FORWARD_LEFT, 2);
        AttackDto attack16 = new AttackDto(67, 38, AttackType.LONG, 2, Directions.FORWARD_LEFT, 2);


        AttackDto attack17 = new AttackDto(68, 39, AttackType.LONG, 1, Directions.FORWARD_LEFT, 3);
        AttackDto attack18 = new AttackDto(69, 39, AttackType.SHORT, 2, Directions.FORWARD, 3);
        AttackDto attack19 = new AttackDto(70, 39, AttackType.LONG, 1, Directions.FORWARD_LEFT, 2);
        AttackDto attack20 = new AttackDto(71, 39, AttackType.SHORT, 2, Directions.FORWARD, 2);
        AttackDto attack21 = new AttackDto(72, 40, AttackType.LONG, 1, Directions.FORWARD_LEFT, 3);
        AttackDto attack22 = new AttackDto(73, 40, AttackType.SHORT, 2, Directions.FORWARD, 3);
        AttackDto attack23 = new AttackDto(74, 40, AttackType.LONG, 1, Directions.FORWARD_LEFT, 2);
        AttackDto attack24 = new AttackDto(75, 40, AttackType.SHORT, 2, Directions.FORWARD, 2);
        AttackDto attack25 = new AttackDto(76, 41, AttackType.LONG, 1, Directions.FORWARD_LEFT, 3);
        AttackDto attack26 = new AttackDto(77, 41, AttackType.SHORT, 2, Directions.FORWARD, 3);
        AttackDto attack27 = new AttackDto(78, 41, AttackType.LONG, 1, Directions.FORWARD_LEFT, 2);
        AttackDto attack28 = new AttackDto(79, 41, AttackType.SHORT, 2, Directions.FORWARD, 2);

        AttackDto attack29 = new AttackDto(80, 42, AttackType.SHORT, 2, Directions.FORWARD, 3);
        AttackDto attack30 = new AttackDto(81, 43, AttackType.SHORT, 2, Directions.FORWARD, 3);
        AttackDto attack31 = new AttackDto(82, 44, AttackType.SHORT, 2, Directions.FORWARD, 3);

        AttackDto attack32 = new AttackDto(83, 45, AttackType.SHORT, 1, Directions.FORWARD_RIGHT, 1);
        AttackDto attack33 = new AttackDto(84, 45, AttackType.SHORT, 1, Directions.BACK_RIGHT, 1);
        AttackDto attack34 = new AttackDto(85, 45, AttackType.SHORT, 1, Directions.FORWARD_LEFT, 1);
        AttackDto attack35 = new AttackDto(86, 46, AttackType.SHORT, 1, Directions.FORWARD_RIGHT, 1);
        AttackDto attack36 = new AttackDto(87, 46, AttackType.SHORT, 1, Directions.BACK_RIGHT, 1);
        AttackDto attack37 = new AttackDto(88, 46, AttackType.SHORT, 1, Directions.FORWARD_LEFT, 1);
        AttackDto attack38 = new AttackDto(89, 47, AttackType.SHORT, 1, Directions.FORWARD_RIGHT, 1);
        AttackDto attack39 = new AttackDto(90, 47, AttackType.SHORT, 1, Directions.BACK_RIGHT, 1);
        AttackDto attack40 = new AttackDto(91, 47, AttackType.SHORT, 1, Directions.FORWARD_LEFT, 1);

        AttackDto attack41 = new AttackDto(92, 48, AttackType.LONG, 1, Directions.FORWARD, 0);
        AttackDto attack42 = new AttackDto(93, 48, AttackType.LONG, 1, Directions.FORWARD_RIGHT, 0);
        AttackDto attack43 = new AttackDto(94, 48, AttackType.LONG, 1, Directions.BACK_RIGHT, 0);
        AttackDto attack44 = new AttackDto(95, 48, AttackType.LONG, 1, Directions.FORWARD_LEFT, 0);
        AttackDto attack45 = new AttackDto(96, 49, AttackType.LONG, 1, Directions.FORWARD, 0);
        AttackDto attack46 = new AttackDto(97, 49, AttackType.LONG, 1, Directions.FORWARD_RIGHT, 0);
        AttackDto attack47 = new AttackDto(98, 49, AttackType.LONG, 1, Directions.BACK_RIGHT, 0);
        AttackDto attack48 = new AttackDto(99, 49, AttackType.LONG, 1, Directions.FORWARD_LEFT, 0);
        AttackDto attack49 = new AttackDto(100, 50, AttackType.LONG, 1, Directions.FORWARD, 0);
        AttackDto attack50 = new AttackDto(101, 50, AttackType.LONG, 1, Directions.FORWARD_RIGHT, 0);
        AttackDto attack51 = new AttackDto(102, 50, AttackType.LONG, 1, Directions.BACK_RIGHT, 0);
        AttackDto attack52 = new AttackDto(103, 50, AttackType.LONG, 1, Directions.FORWARD_LEFT, 0);


        List<AttackDto> attacks = new ArrayList<>();

        attacks.add(attack1);
        attacks.add(attack2);
        attacks.add(attack3);
        attacks.add(attack4);
        attacks.add(attack5);
        attacks.add(attack6);
        attacks.add(attack7);
        attacks.add(attack8);
        attacks.add(attack9);
        attacks.add(attack10);
        attacks.add(attack11);
        attacks.add(attack12);
        attacks.add(attack13);
        attacks.add(attack14);
        attacks.add(attack15);
        attacks.add(attack16);
        attacks.add(attack17);
        attacks.add(attack18);
        attacks.add(attack19);
        attacks.add(attack20);
        attacks.add(attack21);
        attacks.add(attack22);
        attacks.add(attack23);
        attacks.add(attack24);
        attacks.add(attack25);
        attacks.add(attack26);
        attacks.add(attack27);
        attacks.add(attack28);
        attacks.add(attack29);
        attacks.add(attack30);
        attacks.add(attack31);
        attacks.add(attack32);
        attacks.add(attack33);
        attacks.add(attack34);
        attacks.add(attack35);
        attacks.add(attack36);
        attacks.add(attack37);
        attacks.add(attack38);
        attacks.add(attack39);
        attacks.add(attack40);
        attacks.add(attack41);
        attacks.add(attack42);
        attacks.add(attack43);
        attacks.add(attack44);
        attacks.add(attack45);
        attacks.add(attack46);
        attacks.add(attack47);
        attacks.add(attack48);
        attacks.add(attack49);
        attacks.add(attack50);
        attacks.add(attack51);
        attacks.add(attack52);
        attacks.add(attack53);
        attacks.add(attack54);
        attacks.add(attack55);
        attacks.add(attack56);
        attacks.add(attack57);

        return attacks;
    }

    public List<ArmyTokenDto> initHumanArmyC(Context context) {

        Drawable lotrzyk = context.getApplicationContext().getDrawable(R.drawable.token1c);
        Drawable lucznik = context.getApplicationContext().getDrawable(R.drawable.token2c);
        Drawable gwardzista = context.getApplicationContext().getDrawable(R.drawable.token4c);
        Drawable wojownik = context.getApplicationContext().getDrawable(R.drawable.token5c);
        Drawable zabojca = context.getApplicationContext().getDrawable(R.drawable.token6c);
        Drawable weteran = context.getApplicationContext().getDrawable(R.drawable.token7c);
        Drawable specjalista = context.getApplicationContext().getDrawable(R.drawable.token8c);
        Drawable szeregowy = context.getApplicationContext().getDrawable(R.drawable.token9c);
        Drawable dowódca = context.getApplicationContext().getDrawable(R.drawable.token10c);

        List<ArmyTokenDto> armyTokenDtoList = new ArrayList<>();


        ArmyTokenDto tokenDto1 = new ArmyTokenDto(51, "łotrzyk", 1, lotrzyk, 3);
        ArmyTokenDto tokenDto2 = new ArmyTokenDto(52, "łotrzyk", 1, lotrzyk, 3);
        ArmyTokenDto tokenDto3 = new ArmyTokenDto(53, "łotrzyk", 1, lotrzyk, 3);

        ArmyTokenDto tokenDto4 = new ArmyTokenDto(54, "łucznik", 1, lucznik, 3);
        ArmyTokenDto tokenDto5 = new ArmyTokenDto(55, "łucznik", 1, lucznik, 3);
        ArmyTokenDto tokenDto6 = new ArmyTokenDto(56, "łucznik", 1, lucznik, 3);

        ArmyTokenDto tokenDto8 = new ArmyTokenDto(57, "gwardzista", 1, gwardzista, 3);
        ArmyTokenDto tokenDto9 = new ArmyTokenDto(58, "gwardzista", 1, gwardzista, 3);
        ArmyTokenDto tokenDto10 = new ArmyTokenDto(59, "gwardzista", 1, gwardzista, 3);

        ArmyTokenDto tokenDto11 = new ArmyTokenDto(60, "wojownik", 1, wojownik, 3);
        ArmyTokenDto tokenDto12 = new ArmyTokenDto(61, "wojownik", 1, wojownik, 3);
        ArmyTokenDto tokenDto13 = new ArmyTokenDto(62, "wojownik", 1, wojownik, 3);

        ArmyTokenDto tokenDto14 = new ArmyTokenDto(63, "zabójca", 1, zabojca, 3);
        ArmyTokenDto tokenDto15 = new ArmyTokenDto(64, "zabójca", 1, zabojca, 3);
        ArmyTokenDto tokenDto16 = new ArmyTokenDto(65, "zabójca", 1, zabojca, 3);

        ArmyTokenDto tokenDto17 = new ArmyTokenDto(66, "weteran", 1, weteran, 3);
        ArmyTokenDto tokenDto18 = new ArmyTokenDto(67, "weteran", 1, weteran, 3);
        ArmyTokenDto tokenDto19 = new ArmyTokenDto(68, "weteran", 1, weteran, 3);

        ArmyTokenDto tokenDto20 = new ArmyTokenDto(69, "specjalista", 1, specjalista, 3);
        ArmyTokenDto tokenDto21 = new ArmyTokenDto(70, "specjalista", 1, specjalista, 3);
        ArmyTokenDto tokenDto22 = new ArmyTokenDto(71, "specjalista", 1, specjalista, 3);

        ArmyTokenDto tokenDto23 = new ArmyTokenDto(72, "szeregowy", 1, szeregowy, 3);
        ArmyTokenDto tokenDto24 = new ArmyTokenDto(73, "szeregowy", 1, szeregowy, 3);
        ArmyTokenDto tokenDto25 = new ArmyTokenDto(74, "szeregowy", 1, szeregowy, 3);

        ArmyTokenDto tokenDto7 = new ArmyTokenDto(75, "dowodca", 20, dowódca, 3);

        armyTokenDtoList.add(tokenDto1);
        armyTokenDtoList.add(tokenDto2);
        armyTokenDtoList.add(tokenDto3);
        armyTokenDtoList.add(tokenDto4);
        armyTokenDtoList.add(tokenDto5);
        armyTokenDtoList.add(tokenDto6);
        armyTokenDtoList.add(tokenDto7);
        armyTokenDtoList.add(tokenDto8);
        armyTokenDtoList.add(tokenDto9);
        armyTokenDtoList.add(tokenDto10);
        armyTokenDtoList.add(tokenDto11);
        armyTokenDtoList.add(tokenDto12);
        armyTokenDtoList.add(tokenDto13);
        armyTokenDtoList.add(tokenDto14);
        armyTokenDtoList.add(tokenDto15);
        armyTokenDtoList.add(tokenDto16);
        armyTokenDtoList.add(tokenDto17);
        armyTokenDtoList.add(tokenDto18);
        armyTokenDtoList.add(tokenDto19);
        armyTokenDtoList.add(tokenDto20);
        armyTokenDtoList.add(tokenDto21);
        armyTokenDtoList.add(tokenDto22);
        armyTokenDtoList.add(tokenDto23);
        armyTokenDtoList.add(tokenDto24);
        armyTokenDtoList.add(tokenDto25);

        return armyTokenDtoList;
    }

    public List<ArmyTokenDto> initHumanArmyD(Context context) {

        Drawable lotrzyk = context.getApplicationContext().getDrawable(R.drawable.token1d);
        Drawable lucznik = context.getApplicationContext().getDrawable(R.drawable.token2d);
        Drawable gwardzista = context.getApplicationContext().getDrawable(R.drawable.token4d);
        Drawable wojownik = context.getApplicationContext().getDrawable(R.drawable.token5d);
        Drawable zabojca = context.getApplicationContext().getDrawable(R.drawable.token6d);
        Drawable weteran = context.getApplicationContext().getDrawable(R.drawable.token7d);
        Drawable specjalista = context.getApplicationContext().getDrawable(R.drawable.token8d);
        Drawable szeregowy = context.getApplicationContext().getDrawable(R.drawable.token9d);
        Drawable dowódca = context.getApplicationContext().getDrawable(R.drawable.token10d);

        List<ArmyTokenDto> armyTokenDtoList = new ArrayList<>();


        ArmyTokenDto tokenDto1 = new ArmyTokenDto(76, "łotrzyk", 1, lotrzyk, 4);
        ArmyTokenDto tokenDto2 = new ArmyTokenDto(77, "łotrzyk", 1, lotrzyk, 4);
        ArmyTokenDto tokenDto3 = new ArmyTokenDto(78, "łotrzyk", 1, lotrzyk, 4);

        ArmyTokenDto tokenDto4 = new ArmyTokenDto(79, "łucznik", 1, lucznik, 4);
        ArmyTokenDto tokenDto5 = new ArmyTokenDto(80, "łucznik", 1, lucznik, 4);
        ArmyTokenDto tokenDto6 = new ArmyTokenDto(81, "łucznik", 1, lucznik, 4);

        ArmyTokenDto tokenDto8 = new ArmyTokenDto(82, "gwardzista", 1, gwardzista,4);
        ArmyTokenDto tokenDto9 = new ArmyTokenDto(83, "gwardzista", 1, gwardzista, 4);
        ArmyTokenDto tokenDto10 = new ArmyTokenDto(84, "gwardzista", 1, gwardzista, 4);

        ArmyTokenDto tokenDto11 = new ArmyTokenDto(85, "wojownik", 1, wojownik, 4);
        ArmyTokenDto tokenDto12 = new ArmyTokenDto(86, "wojownik", 1, wojownik, 4);
        ArmyTokenDto tokenDto13 = new ArmyTokenDto(87, "wojownik", 1, wojownik, 4);

        ArmyTokenDto tokenDto14 = new ArmyTokenDto(88, "zabójca", 1, zabojca, 4);
        ArmyTokenDto tokenDto15 = new ArmyTokenDto(89, "zabójca", 1, zabojca, 4);
        ArmyTokenDto tokenDto16 = new ArmyTokenDto(90, "zabójca", 1, zabojca, 4);

        ArmyTokenDto tokenDto17 = new ArmyTokenDto(91, "weteran", 1, weteran, 4);
        ArmyTokenDto tokenDto18 = new ArmyTokenDto(92, "weteran", 1, weteran, 4);
        ArmyTokenDto tokenDto19 = new ArmyTokenDto(93, "weteran", 1, weteran, 4);

        ArmyTokenDto tokenDto20 = new ArmyTokenDto(94, "specjalista", 1, specjalista, 4);
        ArmyTokenDto tokenDto21 = new ArmyTokenDto(95, "specjalista", 1, specjalista, 4);
        ArmyTokenDto tokenDto22 = new ArmyTokenDto(96, "specjalista", 1, specjalista, 4);

        ArmyTokenDto tokenDto23 = new ArmyTokenDto(97, "szeregowy", 1, szeregowy, 4);
        ArmyTokenDto tokenDto24 = new ArmyTokenDto(98, "szeregowy", 1, szeregowy, 4);
        ArmyTokenDto tokenDto25 = new ArmyTokenDto(99, "szeregowy", 1, szeregowy, 4);

        ArmyTokenDto tokenDto7 = new ArmyTokenDto(100, "dowodca", 20, dowódca, 4);

        armyTokenDtoList.add(tokenDto1);
        armyTokenDtoList.add(tokenDto2);
        armyTokenDtoList.add(tokenDto3);
        armyTokenDtoList.add(tokenDto4);
        armyTokenDtoList.add(tokenDto5);
        armyTokenDtoList.add(tokenDto6);
        armyTokenDtoList.add(tokenDto7);
        armyTokenDtoList.add(tokenDto8);
        armyTokenDtoList.add(tokenDto9);
        armyTokenDtoList.add(tokenDto10);
        armyTokenDtoList.add(tokenDto11);
        armyTokenDtoList.add(tokenDto12);
        armyTokenDtoList.add(tokenDto13);
        armyTokenDtoList.add(tokenDto14);
        armyTokenDtoList.add(tokenDto15);
        armyTokenDtoList.add(tokenDto16);
        armyTokenDtoList.add(tokenDto17);
        armyTokenDtoList.add(tokenDto18);
        armyTokenDtoList.add(tokenDto19);
        armyTokenDtoList.add(tokenDto20);
        armyTokenDtoList.add(tokenDto21);
        armyTokenDtoList.add(tokenDto22);
        armyTokenDtoList.add(tokenDto23);
        armyTokenDtoList.add(tokenDto24);
        armyTokenDtoList.add(tokenDto25);

        return armyTokenDtoList;
    }

    public List<AttackDto> initHumanArmyAttackC() {
        AttackDto attack1 = new AttackDto(104, 51, AttackType.LONG, 1, Directions.FORWARD, 3);
        AttackDto attack2 = new AttackDto(105, 52, AttackType.LONG, 1, Directions.FORWARD, 3);
        AttackDto attack3 = new AttackDto(106, 53, AttackType.LONG, 1, Directions.FORWARD, 3);

        AttackDto attack4 = new AttackDto(107, 54, AttackType.LONG, 1, Directions.FORWARD_LEFT, 1);
        AttackDto attack5 = new AttackDto(108, 54, AttackType.LONG, 1, Directions.FORWARD, 1);
        AttackDto attack6 = new AttackDto(109, 54, AttackType.LONG, 1, Directions.FORWARD_RIGHT, 1);
        AttackDto attack7 = new AttackDto(110, 55, AttackType.LONG, 1, Directions.FORWARD_LEFT, 1);
        AttackDto attack8 = new AttackDto(111, 55, AttackType.LONG, 1, Directions.FORWARD, 1);
        AttackDto attack9 = new AttackDto(112, 55, AttackType.LONG, 1, Directions.FORWARD_RIGHT, 1);
        AttackDto attack10 = new AttackDto(113, 56, AttackType.LONG, 1, Directions.FORWARD_LEFT, 1);
        AttackDto attack11 = new AttackDto(114, 56, AttackType.LONG, 1, Directions.FORWARD, 1);
        AttackDto attack12 = new AttackDto(115, 56, AttackType.LONG, 1, Directions.FORWARD_RIGHT, 1);

        AttackDto attack13 = new AttackDto(116, 57, AttackType.SHORT, 1, Directions.BACK_RIGHT, 3);
        AttackDto attack15 = new AttackDto(117, 57, AttackType.SHORT, 1, Directions.FORWARD_RIGHT, 3);
        AttackDto attack16 = new AttackDto(118, 58, AttackType.SHORT, 1, Directions.BACK_RIGHT, 3);
        AttackDto attack18 = new AttackDto(119, 58, AttackType.SHORT, 1, Directions.FORWARD_RIGHT, 3);
        AttackDto attack19 = new AttackDto(120, 59, AttackType.SHORT, 1, Directions.BACK_RIGHT, 3);
        AttackDto attack21 = new AttackDto(121, 59, AttackType.SHORT, 1, Directions.FORWARD_RIGHT, 3);

        AttackDto attack22 = new AttackDto(122, 60, AttackType.SHORT, 3, Directions.FORWARD_RIGHT, 1);
        AttackDto attack23 = new AttackDto(123, 61, AttackType.SHORT, 3, Directions.FORWARD_RIGHT, 1);
        AttackDto attack24 = new AttackDto(124, 62, AttackType.SHORT, 3, Directions.FORWARD_RIGHT, 1);

        AttackDto attack25 = new AttackDto(125, 63, AttackType.LONG, 1, Directions.BACK, 3);
        AttackDto attack26 = new AttackDto(126, 64, AttackType.LONG, 1, Directions.BACK, 3);
        AttackDto attack27 = new AttackDto(127, 65, AttackType.LONG, 1, Directions.BACK, 3);

        AttackDto attack28 = new AttackDto(128, 66, AttackType.SHORT, 2, Directions.FORWARD_RIGHT, 2);
        AttackDto attack29 = new AttackDto(129, 67, AttackType.SHORT, 2, Directions.FORWARD_RIGHT, 2);
        AttackDto attack30 = new AttackDto(130, 68, AttackType.SHORT, 2, Directions.FORWARD_RIGHT, 2);

        AttackDto attack31 = new AttackDto(131, 69, AttackType.SHORT, 1, Directions.FORWARD_LEFT, 3);
        AttackDto attack32 = new AttackDto(132, 69, AttackType.LONG, 1, Directions.FORWARD_LEFT, 3);
        AttackDto attack33 = new AttackDto(133, 70, AttackType.SHORT, 1, Directions.FORWARD_LEFT, 3);
        AttackDto attack34 = new AttackDto(134, 70, AttackType.LONG, 1, Directions.FORWARD_LEFT, 3);
        AttackDto attack35 = new AttackDto(135, 71, AttackType.SHORT, 1, Directions.FORWARD_LEFT, 3);
        AttackDto attack36 = new AttackDto(136, 71, AttackType.LONG, 1, Directions.FORWARD_LEFT, 3);


        AttackDto attack37 = new AttackDto(137, 72, AttackType.SHORT, 1, Directions.FORWARD, 1);
        AttackDto attack38 = new AttackDto(138, 73, AttackType.SHORT, 1, Directions.FORWARD, 1);
        AttackDto attack39 = new AttackDto(139, 74, AttackType.SHORT, 1, Directions.FORWARD, 1);

        AttackDto attack40 = new AttackDto(140, 75, AttackType.SHORT, 1, Directions.FORWARD, 0);
        AttackDto attack41 = new AttackDto(140, 75, AttackType.SHORT, 1, Directions.FORWARD_RIGHT, 0);
        AttackDto attack42 = new AttackDto(140, 75, AttackType.SHORT, 1, Directions.BACK_RIGHT, 0);
        AttackDto attack43 = new AttackDto(140, 75, AttackType.SHORT, 1, Directions.BACK, 0);
        AttackDto attack44 = new AttackDto(140, 75, AttackType.SHORT, 1, Directions.BACK_LEFT, 0);
        AttackDto attack45 = new AttackDto(140, 75, AttackType.SHORT, 1, Directions.FORWARD_LEFT, 0);


        List<AttackDto> attacks = new ArrayList<>();

        attacks.add(attack1);
        attacks.add(attack2);
        attacks.add(attack3);
        attacks.add(attack4);
        attacks.add(attack5);
        attacks.add(attack6);
        attacks.add(attack7);
        attacks.add(attack8);
        attacks.add(attack9);
        attacks.add(attack10);
        attacks.add(attack11);
        attacks.add(attack12);
        attacks.add(attack13);
        attacks.add(attack15);
        attacks.add(attack16);
        attacks.add(attack18);
        attacks.add(attack19);
        attacks.add(attack21);
        attacks.add(attack22);
        attacks.add(attack23);
        attacks.add(attack24);
        attacks.add(attack25);
        attacks.add(attack26);
        attacks.add(attack27);
        attacks.add(attack28);
        attacks.add(attack29);
        attacks.add(attack30);
        attacks.add(attack31);
        attacks.add(attack32);
        attacks.add(attack33);
        attacks.add(attack34);
        attacks.add(attack35);
        attacks.add(attack36);
        attacks.add(attack37);
        attacks.add(attack38);
        attacks.add(attack39);
        attacks.add(attack40);
        attacks.add(attack41);
        attacks.add(attack42);
        attacks.add(attack43);
        attacks.add(attack44);
        attacks.add(attack45);

        return attacks;
    }

    public List<AttackDto> initHumanArmyAttackD() {
        AttackDto attack1 = new AttackDto(141, 76, AttackType.LONG, 1, Directions.FORWARD, 3);
        AttackDto attack2 = new AttackDto(142, 77, AttackType.LONG, 1, Directions.FORWARD, 3);
        AttackDto attack3 = new AttackDto(143, 78, AttackType.LONG, 1, Directions.FORWARD, 3);

        AttackDto attack4 = new AttackDto(144, 79, AttackType.LONG, 1, Directions.FORWARD_LEFT, 1);
        AttackDto attack5 = new AttackDto(145, 79, AttackType.LONG, 1, Directions.FORWARD, 1);
        AttackDto attack6 = new AttackDto(146, 79, AttackType.LONG, 1, Directions.FORWARD_RIGHT, 1);
        AttackDto attack7 = new AttackDto(147, 80, AttackType.LONG, 1, Directions.FORWARD_LEFT, 1);
        AttackDto attack8 = new AttackDto(148, 80, AttackType.LONG, 1, Directions.FORWARD, 1);
        AttackDto attack9 = new AttackDto(149, 80, AttackType.LONG, 1, Directions.FORWARD_RIGHT, 1);
        AttackDto attack10 = new AttackDto(150, 81, AttackType.LONG, 1, Directions.FORWARD_LEFT, 1);
        AttackDto attack11 = new AttackDto(151, 81, AttackType.LONG, 1, Directions.FORWARD, 1);
        AttackDto attack12 = new AttackDto(152, 81, AttackType.LONG, 1, Directions.FORWARD_RIGHT, 1);

        AttackDto attack13 = new AttackDto(153, 82, AttackType.SHORT, 1, Directions.BACK_RIGHT, 3);
        AttackDto attack15 = new AttackDto(154, 82, AttackType.SHORT, 1, Directions.FORWARD_RIGHT, 3);
        AttackDto attack16 = new AttackDto(155, 83, AttackType.SHORT, 1, Directions.BACK_RIGHT, 3);
        AttackDto attack18 = new AttackDto(156, 83, AttackType.SHORT, 1, Directions.FORWARD_RIGHT, 3);
        AttackDto attack19 = new AttackDto(157, 84, AttackType.SHORT, 1, Directions.BACK_RIGHT, 3);
        AttackDto attack21 = new AttackDto(158, 84, AttackType.SHORT, 1, Directions.FORWARD_RIGHT, 3);

        AttackDto attack22 = new AttackDto(159, 85, AttackType.SHORT, 3, Directions.FORWARD_RIGHT, 1);
        AttackDto attack23 = new AttackDto(160, 86, AttackType.SHORT, 3, Directions.FORWARD_RIGHT, 1);
        AttackDto attack24 = new AttackDto(161, 87, AttackType.SHORT, 3, Directions.FORWARD_RIGHT, 1);

        AttackDto attack25 = new AttackDto(162, 88, AttackType.LONG, 1, Directions.BACK, 3);
        AttackDto attack26 = new AttackDto(163, 89, AttackType.LONG, 1, Directions.BACK, 3);
        AttackDto attack27 = new AttackDto(164, 90, AttackType.LONG, 1, Directions.BACK, 3);

        AttackDto attack28 = new AttackDto(165, 91, AttackType.SHORT, 2, Directions.FORWARD_RIGHT, 2);
        AttackDto attack29 = new AttackDto(166, 92, AttackType.SHORT, 2, Directions.FORWARD_RIGHT, 2);
        AttackDto attack30 = new AttackDto(167, 93, AttackType.SHORT, 2, Directions.FORWARD_RIGHT, 2);

        AttackDto attack31 = new AttackDto(168, 94, AttackType.SHORT, 1, Directions.FORWARD_LEFT, 3);
        AttackDto attack32 = new AttackDto(169, 94, AttackType.LONG, 1, Directions.FORWARD_LEFT, 3);
        AttackDto attack33 = new AttackDto(170, 95, AttackType.SHORT, 1, Directions.FORWARD_LEFT, 3);
        AttackDto attack34 = new AttackDto(171, 95, AttackType.LONG, 1, Directions.FORWARD_LEFT, 3);
        AttackDto attack35 = new AttackDto(172, 96, AttackType.SHORT, 1, Directions.FORWARD_LEFT, 3);
        AttackDto attack36 = new AttackDto(173, 96, AttackType.LONG, 1, Directions.FORWARD_LEFT, 3);


        AttackDto attack37 = new AttackDto(174, 97, AttackType.SHORT, 1, Directions.FORWARD, 1);
        AttackDto attack38 = new AttackDto(175, 98, AttackType.SHORT, 1, Directions.FORWARD, 1);
        AttackDto attack39 = new AttackDto(176, 99, AttackType.SHORT, 1, Directions.FORWARD, 1);

        AttackDto attack40 = new AttackDto(177, 100, AttackType.SHORT, 1, Directions.FORWARD, 0);
        AttackDto attack41 = new AttackDto(177, 100, AttackType.SHORT, 1, Directions.FORWARD_RIGHT, 0);
        AttackDto attack42 = new AttackDto(177, 100, AttackType.SHORT, 1, Directions.BACK_RIGHT, 0);
        AttackDto attack43 = new AttackDto(177, 100, AttackType.SHORT, 1, Directions.BACK, 0);
        AttackDto attack44 = new AttackDto(177, 100, AttackType.SHORT, 1, Directions.BACK_LEFT, 0);
        AttackDto attack45 = new AttackDto(177, 100, AttackType.SHORT, 1, Directions.FORWARD_LEFT, 0);


        List<AttackDto> attacks = new ArrayList<>();

        attacks.add(attack1);
        attacks.add(attack2);
        attacks.add(attack3);
        attacks.add(attack4);
        attacks.add(attack5);
        attacks.add(attack6);
        attacks.add(attack7);
        attacks.add(attack8);
        attacks.add(attack9);
        attacks.add(attack10);
        attacks.add(attack11);
        attacks.add(attack12);
        attacks.add(attack13);
        attacks.add(attack15);
        attacks.add(attack16);
        attacks.add(attack18);
        attacks.add(attack19);
        attacks.add(attack21);
        attacks.add(attack22);
        attacks.add(attack23);
        attacks.add(attack24);
        attacks.add(attack25);
        attacks.add(attack26);
        attacks.add(attack27);
        attacks.add(attack28);
        attacks.add(attack29);
        attacks.add(attack30);
        attacks.add(attack31);
        attacks.add(attack32);
        attacks.add(attack33);
        attacks.add(attack34);
        attacks.add(attack35);
        attacks.add(attack36);
        attacks.add(attack37);
        attacks.add(attack38);
        attacks.add(attack39);
        attacks.add(attack40);
        attacks.add(attack41);
        attacks.add(attack42);
        attacks.add(attack43);
        attacks.add(attack44);
        attacks.add(attack45);

        return attacks;
    }

    public static AttackType getAttackFromString(String attack) {
        if (attack.equals("SHORT")) {
            return AttackType.SHORT;
        } else {
            return AttackType.LONG;
        }
    }

    public static Directions getDirectionFromString(String direction) {
        switch (direction) {
            case "FORWARD":
                return Directions.FORWARD;

            case "FORWARD_RIGHT":
                return Directions.FORWARD_RIGHT;

            case "BACK_RIGHT":
                return Directions.BACK_RIGHT;

            case "BACK":
                return Directions.BACK;

            case "BACK_LEFT":
                return Directions.BACK_LEFT;

            case "FORWARD_LEFT":
                return Directions.FORWARD_LEFT;

            case "ALL_DIRECTION":
                return Directions.ALL_DIRECTION;

        }
        return null;
    }

}
