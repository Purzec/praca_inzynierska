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


    public List<ArmyTokenDto> initElfArmy(Context context) {

        Drawable drawable = context.getApplicationContext().getDrawable(R.drawable.zwiadowca);
        Drawable drawable1 = context.getApplicationContext().getDrawable(R.drawable.lucznik);
        Drawable drawable2 = context.getApplicationContext().getDrawable(R.drawable.token3a);

        List<ArmyTokenDto> armyTokenDtoList = new ArrayList<>();
        Attack attack = new Attack(1, AttackType.SHORT, 1, Directions.FORWARD_LEFT);
        Attack attack1 = new Attack(2, AttackType.LONG, 1, Directions.FORWARD);


        ArmyTokenDto tokenDto = new ArmyTokenDto(1, "zwiadowca", 2, Collections.singletonList(attack), 1, drawable, 1);
        ArmyTokenDto tokenDto1 = new ArmyTokenDto(2, "zwiadowca", 2, Collections.singletonList(attack), 1, drawable, 1);
        ArmyTokenDto tokenDto2 = new ArmyTokenDto(3, "łucznik", 3, Collections.singletonList(attack1), 1, drawable1, 1);
        ArmyTokenDto tokenDto3 = new ArmyTokenDto(4, "łucznik", 3, Collections.singletonList(attack1), 1, drawable1, 1);
        ArmyTokenDto tokenDto4 = new ArmyTokenDto(5, "łucznik", 3, Collections.singletonList(attack1), 1, drawable1, 1);
        ArmyTokenDto tokenDto5 = new ArmyTokenDto(6, "łucznik", 3, Collections.singletonList(attack1), 1, drawable1, 1);
        ArmyTokenDto tokenDto6 = new ArmyTokenDto(7, "łucznik", 3, Collections.singletonList(attack1), 1, drawable1, 1);
        ArmyTokenDto tokenDto7 = new ArmyTokenDto(8, "dowodca", 0, Collections.singletonList(attack1), 1, drawable2, 1);

        armyTokenDtoList.add(tokenDto);
        armyTokenDtoList.add(tokenDto1);
        armyTokenDtoList.add(tokenDto2);
        armyTokenDtoList.add(tokenDto3);
        armyTokenDtoList.add(tokenDto4);
        armyTokenDtoList.add(tokenDto5);
        armyTokenDtoList.add(tokenDto6);
        armyTokenDtoList.add(tokenDto7);

        return armyTokenDtoList;
    }


    public List<AttackDto> initElfArmyAttack() {
        AttackDto attack = new AttackDto(1,1, AttackType.SHORT, 1, Directions.FORWARD_LEFT);
        AttackDto attack1 = new AttackDto(2, 2,AttackType.SHORT, 1, Directions.FORWARD_LEFT);
        AttackDto attack2 = new AttackDto(3, 3,AttackType.LONG, 1, Directions.FORWARD);
        AttackDto attack3 = new AttackDto(4, 4,AttackType.LONG, 1, Directions.FORWARD);
        AttackDto attack4 = new AttackDto(5, 5,AttackType.LONG, 1, Directions.FORWARD);
        AttackDto attack5 = new AttackDto(6, 6,AttackType.LONG, 1, Directions.FORWARD);
        AttackDto attack6 = new AttackDto(7, 7,AttackType.LONG, 1, Directions.FORWARD);
        AttackDto attack7 = new AttackDto(8, 8,AttackType.SHORT, 1, Directions.ALL_DIRECTION);

        List<AttackDto> attacks = new ArrayList<>();
        attacks.add(attack);
        attacks.add(attack1);
        attacks.add(attack2);
        attacks.add(attack3);
        attacks.add(attack4);
        attacks.add(attack5);
        attacks.add(attack6);
        attacks.add(attack7);



        return attacks;
    }
}
