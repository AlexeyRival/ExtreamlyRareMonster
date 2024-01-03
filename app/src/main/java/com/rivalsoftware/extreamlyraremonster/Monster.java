package com.rivalsoftware.extreamlyraremonster;

public class Monster {
    public String name;
    public int basePicId;
    public boolean shiny;
    public int rareness=1;
    public Monster(String name, int basePicId, boolean shiny) {
        this.name = name;
        this.basePicId = basePicId;
        this.shiny = shiny;
    }
    public Monster()
    {
        name="missingnever";
        basePicId=0;
        shiny=false;
    }

}
