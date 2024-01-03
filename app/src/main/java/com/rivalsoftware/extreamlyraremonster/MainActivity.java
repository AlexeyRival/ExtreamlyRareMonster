package com.rivalsoftware.extreamlyraremonster;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.gson.reflect.TypeToken;
import com.rivalsoftware.extreamlyraremonster.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Monster monster;
    private Random random;
    private ArrayList<Monster> dex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        random = new Random();
        dex = new ArrayList<Monster>();
        LoadDex();

        monster = new Monster("Оддиш", R.drawable.oddish,false);
        DrawMonster(monster);
    }

    public void CatchButton(View view) {
        ThrowBall();
    }
    public void SkipButton(View view)
    {
        SkipMonster();
    }
    private void ThrowBall()
    {
        TextView txt = findViewById(R.id.result);
        if(random.nextBoolean())
        {
            txt.setText(monster.name+" пойман!");
            dex.add(monster);
        }
        else
        {
            txt.setText(monster.name+" ушёл!");
        }
        monster = MonsterFactory.GetRandomMonster(random);
        DrawMonster(monster);
    }

    public void DrawDex()
    {
        LinearLayout myRoot =findViewById(R.id.mainscroll);
        for(int i=0;i<dex.size();++i)
        {
            View v = LayoutInflater.from(this).inflate(R.layout.monster_data_layout, null);
            ((TextView)(v.findViewById(R.id.subMonsterName))).setText(dex.get(i).name);
            ((TextView)(v.findViewById(R.id.monsterPP))).setText(dex.get(i).rareness+" PP");
            ((ImageView)(v.findViewById(R.id.subMonsterPic))).setImageBitmap(Upscale(MakeMonsterSprite(dex.get(i))));
            myRoot.addView(v);
        }
        SaveDex();
    }
    public void SaveDex()
    {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String json = gson.toJson(dex);
        Log.i("JSON",json);
        SugarIO.Save(getApplicationContext(),"dex.txt",json);
    }
    public void LoadDex()
    {
       String data= SugarIO.Load(getApplicationContext(),"dex.txt");
       if(!Objects.equals(data, ""))
       {
           Log.i("JSON",data);
           GsonBuilder builder = new GsonBuilder();
           Gson gson = builder.create();
           dex = gson.fromJson(data,new TypeToken<ArrayList<Monster>>(){}.getType());
       }
    }
    private void SkipMonster()
    {
        TextView txt = findViewById(R.id.result);
        txt.setText(monster.name+" уходит восвоясе!");
        monster = MonsterFactory.GetRandomMonster(random);
        DrawMonster(monster);
    }
    public void DrawMonster(Monster monster)
    {
        ImageView img = findViewById(R.id.monsterPic);
        Bitmap bitmap = MakeMonsterSprite(monster);
        img.setImageBitmap(Upscale(bitmap));
        TextView txt = findViewById(R.id.monsterName);
        txt.setText(monster.name+"\n("+monster.rareness+" РР)");
    }
    public Bitmap MakeMonsterSprite(Monster monster)
    {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),monster.basePicId).copy(Bitmap.Config.ARGB_8888,true);
        if(monster.shiny)
        {
            int[] pixels = new int[bitmap.getWidth()*bitmap.getHeight()];
            float[] hsv = new float[3];
            bitmap.getPixels(pixels,0,bitmap.getWidth(),0,0,bitmap.getWidth(),bitmap.getHeight());

            for(int i=0;i<pixels.length;++i)
            {

                Color.colorToHSV(pixels[i],hsv);
                hsv[0]=(hsv[0]+.4340121f)%1.0f;
                pixels[i]=Color.HSVToColor(hsv);
            }
            bitmap.setPixels(pixels,0,bitmap.getWidth(),0,0,bitmap.getWidth(),bitmap.getHeight());
        }
        return bitmap;
    }
    public Bitmap Upscale(Bitmap in)
    {
        return Bitmap.createScaledBitmap(in,256,256,false);
    }
}