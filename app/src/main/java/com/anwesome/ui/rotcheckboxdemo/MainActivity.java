package com.anwesome.ui.rotcheckboxdemo;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.anwesome.camera.rotcheckbox.RotCheckBox;
import com.anwesome.camera.rotcheckbox.RotCheckBoxGroup;
import com.anwesome.camera.rotcheckbox.RotCheckBoxListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RotCheckBox rotCheckBox = new RotCheckBox(this,"Yes");
        RotCheckBox rotCheckBox2 = new RotCheckBox(this,"No");
        rotCheckBox.setRotCheckBoxListener(new RotCheckBoxListener() {
            @Override
            public void onComplete() {
                Toast.makeText(MainActivity.this,"Yes",Toast.LENGTH_SHORT).show();
            }
        });
        rotCheckBox2.setRotCheckBoxListener(new RotCheckBoxListener() {
            @Override
            public void onComplete() {
                Toast.makeText(MainActivity.this,"No",Toast.LENGTH_SHORT).show();
            }
        });
        rotCheckBox.show(100,100);
        rotCheckBox2.show(300,300);
        RotCheckBoxGroup rotCheckBoxGroup = new RotCheckBoxGroup(this);
        rotCheckBoxGroup.addRotCheckBox("Red");
        rotCheckBoxGroup.addRotCheckBox("Green");
        rotCheckBoxGroup.addRotCheckBox("Blue");
        rotCheckBoxGroup.show(500);

    }
}
