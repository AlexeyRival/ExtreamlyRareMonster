package com.rivalsoftware.extreamlyraremonster;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Random;

/**
Тестирование генератора монстров
 */
public class MonsterTester {
    @Test
    public void ShinyExists() {

        boolean isShiny=false;
        Random random = new Random();
        int mediumChance=1024;
        float mediumRareness=1f;
        Monster monster;
        for(int i=0;i<2048;++i)
        {
            monster= MonsterFactory.GetRandomMonster(random);
            if(monster.shiny){
                isShiny=true;
                mediumChance=(i+mediumChance)/2;
            }
            mediumRareness=(mediumRareness+monster.rareness)*.5f;
        }
        System.out.println("Средний шанс шайни 1/"+mediumChance);
        System.out.println("Средняя редкость "+mediumRareness);
        assertTrue(isShiny);
    }
    @Test
    public void EveryMonsterExists()
    {
        Random random = new Random();
        Monster monster;
        int existed=0;
        for(int i=0;i<BaseMonsterData.values().length;++i)
        {
            for(int ii=0;ii<512;++ii)
            {
                monster= MonsterFactory.GetRandomMonster(random);
                if(monster.name.equals(BaseMonsterData.values()[i].name))
                {
                    existed++;
                    break;
                }
            }
        }
        System.out.println("Попадается монстров "+existed+"/"+BaseMonsterData.values().length);
        assertEquals(existed,BaseMonsterData.values().length);
    }
}
