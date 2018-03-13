package com.example.yidan.breakoutgame;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Created by Yidan on 12/2/2017.
 */

public class FileIO {



    //Define the file path in the internal storage.
    public String fileName = "file.txt";

    //Parameter: context is the object of Context, lists is data what we need to write in file.
    //Function: Write the sorted records into write, write mode is APPEND.
    public void writeFileApp(Context context,  List<String[]> lists){
        try {
            String stringMessage = "";
            //Create fileoutputstream for the file
            FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_APPEND);

            String [] rec = new String[2];
            String Message = "";
            for(int i = 0; i < lists.size();i++){
                rec = lists.get(i);
                Message = rec[0]  + "|" + rec [1]+ "\n";
                fileOutputStream.write(Message.getBytes());
                fileOutputStream.flush();
            }

            //Use fileoutputstream to write message into file line by line.
            //Add "|" to seperate each item


            //Close the fileoutputstream
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Function: Write the sorted records into write, write mode is MODE_PRIVATE.
    //Except the write mode, other functions are the same from above.
    public void writeFilePri(Context context, List<String[]> lists){
        try {

            //Use fileoutputstream to write message into file line by line.
            //Add "|" to seperate each item
            FileOutputStream fileOutputStream2 = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            String [] rec = new String[2];
            String stringMessage2 = "";
            for(int i = 0; i < lists.size();i++){
                rec = lists.get(i);
                stringMessage2 = rec[0]  + "|" + rec [1]+ "\n";
                fileOutputStream2.write(stringMessage2.getBytes());
                fileOutputStream2.flush();
            }


            fileOutputStream2.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Parameter: context is the object of Context
    //Return: sorted List.
    //Function: Read the sorted lists from the file

    public List<String[]> readFile(Context context){
        List<String[]> lists = new ArrayList<String[]>();
        List<String[]> newlists = new ArrayList<String[]>();
        String message = "";
        try {

            String [] rec = new String[2];
            String Message;

            //Create fileoutputstream for the file
            FileInputStream fileInputStream = context.openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();

            //Read content from file line by line. Then store the items to string array from each line by splitint "|"
            while ((Message=bufferedReader.readLine())!=null){
                stringBuffer.append(Message + "\n");
                rec = Message.split("\\|");
                lists.add(rec);
            }

            //Close the fileinputstream
            fileInputStream.close();

            //The algorithm for sort the high scores
            int size = lists.size();
            String []name = new String[size];
            String []score = new String[size];

            for(int i = 0; i<size;i++){
                String []item = new String[2];
                item = lists.get(i);
                name[i] = item[0];
                score[i] = item[1];
            }

            List< Integer > score_int = new ArrayList<>();
            for(int j = 0; j < size; j++){
                score_int.add(Integer.parseInt(score[j]));
            }
            Collections.sort(score_int, new Comparator< Integer >() {
             @Override
             public int compare(Integer a, Integer b) {
                                     if ( a > b ) {
                                             return -1;
                                         } else {
                                             return 1;
                                        }
                                 }
             });

            String []scorenew = new String [size];
            for(int k = 0; k < size; k++){
                scorenew[k]  = score_int.get(k).toString();
            }


            for(int q = 0; q<size;q++){
                for(int w = 0;w < size; w++){
                    String []item2 = new String[2];
                    item2 = lists.get(w);
                    if(scorenew[q].equals(item2[1])){
                        newlists.add(item2);
                    }
                }
            }



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newlists;
    }


}
