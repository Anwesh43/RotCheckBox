package com.anwesome.ui.rotcheckboxdemo;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anwesome.camera.rotcheckbox.RotCheckBox;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RotCheckBox rotCheckBox = new RotCheckBox(this,"Yes");
        RotCheckBox rotCheckBox2 = new RotCheckBox(this,"No");
        rotCheckBox.show(100,100);
        rotCheckBox2.show(300,300);



    }
}
