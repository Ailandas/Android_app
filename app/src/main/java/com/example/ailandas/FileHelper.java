package com.example.ailandas;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Tan on 2/18/2016.
 */
public class FileHelper {


    static final int READ_BLOCK_SIZE = 100;
    boolean exists=false;
    public void WriteBtn(Context x, String text, String textFileName) {
        // add-write text into file
        text=text+"\n";


            try {
                FileOutputStream fileout = x.openFileOutput(textFileName, Context.MODE_APPEND);
                OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                outputWriter.write(text);
                outputWriter.close();

                Toast.makeText(x, "Sėkmingai pridėta!",
                        Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public void AddNewUser(Context context, String username, String password, String email){
            String userData=username+","+password+","+email+"\n";

            try {
                FileOutputStream fileout = context.openFileOutput("users.txt", Context.MODE_APPEND);
                OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                outputWriter.write(userData);
                outputWriter.close();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }



    public String ReadBtn(Context x, String textFileName) {
        //reading text from file
        try {
            FileInputStream fileIn = x.openFileInput(textFileName);
            InputStreamReader InputRead = new InputStreamReader(fileIn);

            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            String s = "";
            int charRead;

            while ((charRead = InputRead.read(inputBuffer)) > 0) {
                // char to string conversion
                String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                s += readstring;
            }
            InputRead.close();
            return s;


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean getExistance(Context x, String textFileName){
        try {
            FileInputStream fileIn = x.openFileInput(textFileName);
            InputStreamReader InputRead = new InputStreamReader(fileIn);
            boolean result=false;
            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            String s = "";
            int charRead;

            if ((charRead = InputRead.read(inputBuffer)) > 0) {
                // char to string conversion
                result=true;
            }
            InputRead.close();

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public void SortTxtFile(Context x, String textFileName){

        try {
            String temp=ReadBtn(x,textFileName);
            String[] lines = temp.split("\\r?\\n");
            List<String> lineListas = new ArrayList<String>();
           lineListas=Arrays.asList(lines);
            Collections.sort(lineListas);
            String[] arr = lineListas.toArray(new String[0]);
           String sorted=convertArrayToString(arr);
           SaveSorted(x,sorted,textFileName);




        }
        catch (Exception exc){
            System.out.println(exc.getMessage());
        }
    }
    private void SaveSorted(Context x,String text, String textFileName){
        try {

            FileOutputStream fileout = x.openFileOutput(textFileName, MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
            outputWriter.write(text);
            outputWriter.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String convertArrayToString(String[] strArray) {
        String output="";
        for(int i=0; i<strArray.length;i++){
            output=output+strArray[i]+"\n";
        }
        return output;
    }
    public boolean DeleteItem(long deletable,Context x, String textFileName){
       try {
           String content = ReadBtn(x,textFileName);
           String[] lines = content.split("\\r?\\n");
           List<String> lineListas = new ArrayList<String>();
           lineListas = Arrays.asList(lines);
           List modifiableList = new ArrayList(Arrays.asList(lines));
           int i;
           String delete = "";
           for (String line : lineListas) {
               if (line.contains(Long.toString(deletable))) {
                   i = lineListas.indexOf(line);
                   delete = lineListas.get(i);
               }
           }
           modifiableList.remove(delete);

           String[] arr = new String[modifiableList.size()];
           modifiableList.toArray(arr);
           String AfterDeletion = convertArrayToString(arr);
           SaveSorted(x, AfterDeletion,textFileName);
           return true;
       }
       catch (Exception exc){
           return false;
       }
    }
    public void addImageUriToCertainUser(String username, Context x, String uri){
        String data=ReadBtn(x,"users.txt");
        String[] lines = data.split("\\r?\\n");
        String newContent="";
        for(int i=0; i<lines.length; i++){
            String[] SingleEntry=lines[i].split(",");
            if(SingleEntry[0].contains(username)){
                newContent+=SingleEntry[0]+","+SingleEntry[1]+","+SingleEntry[2]+","+uri+"\n";
            }
            else{
                for(int j=0; j<SingleEntry.length; j++){
                    if(j==SingleEntry.length-1){
                        newContent+=SingleEntry[j]+"\n";
                    }
                    else{
                        newContent+=SingleEntry[j]+",";
                    }
                }
            }
        }


        SaveSorted(x,newContent,"users.txt");
    }
}