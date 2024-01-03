package com.rivalsoftware.extreamlyraremonster;

import android.graphics.Bitmap;

import java.util.Random;

public class MonsterFactory {
    public static Monster GetRandomMonster(Random random)
    {
        Monster monster = new Monster();
        int baseid = random.nextInt(BaseMonsterData.values().length);
        monster.name = BaseMonsterData.values()[baseid].name;
        monster.basePicId=BaseMonsterData.values()[baseid].resource;

        int rareness=1;
        if(random.nextInt(512)==0)
        {
            monster.shiny=true;
            rareness+=10;
        }
        monster.rareness=rareness;
        return monster;
    }
}
