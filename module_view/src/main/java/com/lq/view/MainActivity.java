package com.lq.view;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Person[] pers = {null,new Person(1),new Person(2),new Person(3)};
        System.out.println("修改之前=====>");
        for (Person per : pers) {
            System.out.println(per);
        }
        for (int i = 0; i < pers.length; i++) {
            Person per = pers[i];
            if (per!=null) {
                if (per.getAge() ==1) {
                    pers[i] = null;
                }
            }
        }
        System.out.println("修改之后=====>");
        for (Person per : pers) {
            System.out.println(per);
        }
    }
}