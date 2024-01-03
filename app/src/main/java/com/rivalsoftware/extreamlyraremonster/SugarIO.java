package com.rivalsoftware.extreamlyraremonster;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class SugarIO {
    public static String Load(Context context,String fileName)
    {
        String result = "";
        try
        {
            InputStream inputStream = context.openFileInput(fileName);
            if(inputStream!=null)
            {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String recieveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((recieveString= bufferedReader.readLine())!=null)
                {
                    stringBuilder.append("\n").append(recieveString);
                }

                inputStream.close();
                result = stringBuilder.toString();
            }
        }
        catch (IOException e)
        {
            Log.e("Exception","Ошибка чтения: "+e.toString());
        }
        return result;
    }
    public static void Save(Context context, String fileName, String data)
    {
        try
        {

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e)
        {
            Log.e("Exception","Ошибка записи: "+e.toString());
        }
    }
}
