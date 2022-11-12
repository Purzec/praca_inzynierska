package com.example.pracainzynierska.commons;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pracainzynierska.R;
import com.example.pracainzynierska.model.ArmyToken;
import com.example.pracainzynierska.model.Attack;
import com.example.pracainzynierska.model.DTO.ArmyTokenDto;
import com.example.pracainzynierska.model.enums.AttackType;
import com.example.pracainzynierska.model.enums.Directions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ArmyTokenUtils extends AppCompatActivity {


    public List<ArmyTokenDto> initHumanArmy(Context context) {

        Drawable drawable = context.getApplicationContext().getDrawable(R.drawable.zwiadowca);
        Drawable drawable1 = context.getApplicationContext().getDrawable(R.drawable.lucznik);

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

        armyTokenDtoList.add(tokenDto);
        armyTokenDtoList.add(tokenDto1);
        armyTokenDtoList.add(tokenDto2);
        armyTokenDtoList.add(tokenDto3);
        armyTokenDtoList.add(tokenDto4);
        armyTokenDtoList.add(tokenDto5);
        armyTokenDtoList.add(tokenDto6);

        return armyTokenDtoList;
    }


}
