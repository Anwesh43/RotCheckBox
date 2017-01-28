package com.anwesome.camera.rotcheckbox;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anweshmishra on 29/01/17.
 */
public class RotCheckBoxGroup{
    private boolean laid=false,added=false;
    private List<RotCheckBox> rotCheckBoxes = new ArrayList<>();
    private int dimen,width=1000;
    private String value;
    private int selectedIndex = -1;
    private Activity activity;
    private RotCheckBoxListener rotCheckBoxListener;

    public RotCheckBoxListener getRotCheckBoxListener() {
        return rotCheckBoxListener;
    }

    public void setRotCheckBoxListener(RotCheckBoxListener rotCheckBoxListener) {
        this.rotCheckBoxListener = rotCheckBoxListener;
        for(RotCheckBox rotCheckBox:rotCheckBoxes) {
            rotCheckBox.setRotCheckBoxListener(rotCheckBoxListener);
        }
    }

    public RotCheckBoxGroup(Activity activity) {
        this.activity = activity;
    }

    public void show(int... ycordinate) {
        RotCheckBoxViewGroup rotCheckBoxViewGroup = new RotCheckBoxViewGroup(activity);
        if(ycordinate.length == 1) {
            rotCheckBoxViewGroup.setX(0);
            rotCheckBoxViewGroup.setY(ycordinate[0]);
        }
        this.activity.addContentView(rotCheckBoxViewGroup,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }
    public void selectCheckBox(int newIndex) {
        if(selectedIndex!=-1 && selectedIndex!=newIndex) {
            rotCheckBoxes.get(selectedIndex).unselectCheckBox();
        }
        value = rotCheckBoxes.get(newIndex).getText();
        selectedIndex = newIndex;
    }
    public String getValue() {
        return value;
    }
    public void addRotCheckBox(String text) {
        RotCheckBox rotCheckBox = new RotCheckBox(text);
        rotCheckBox.setGroupIndex(rotCheckBoxes.size());
        rotCheckBox.setGroup(this);
        if(rotCheckBoxListener!=null) {
            rotCheckBox.setRotCheckBoxListener(rotCheckBoxListener);
        }
        rotCheckBoxes.add(rotCheckBox);
    }
    private class RotCheckBoxViewGroup extends ViewGroup {
        public RotCheckBoxViewGroup(Context context) {
            super(context);
        }
        public void onLayout(boolean changed,int a,int b,int worig,int horig) {

            if(!laid) {
                int x=width/10,h=0,gap = 5*dimen/4,s=0;
                View child = null;
                for(int i=0;i<getChildCount();i++) {
                    child = getChildAt(i);

                    if(x+child.getMeasuredWidth() > width) {
                        x=width/10;
                        child.layout(x,h*gap,x+child.getMeasuredWidth(),h*gap+child.getMeasuredHeight());
                        h++;
                    }
                    else{
                        child.layout(x,h*gap,x+child.getMeasuredWidth(),h*gap+child.getMeasuredHeight());
                        x+=child.getMeasuredWidth();
                        s++;
                    }
                }
                laid = true;
            }
        }
        public void onMeasure(int wspec,int hspec) {
            Display display = getDisplay();
            Point size = new Point();
            display.getRealSize(size);
            int w = size.x,h = size.y;
            dimen = h/20;
            width = w;
            if(w>h) {
                dimen = w/20;
            }
            if(!added) {
                for (RotCheckBox rotCheckBox : rotCheckBoxes) {
                    rotCheckBox.createView(this,dimen);
                    View view = rotCheckBox.getRotCheckBoxView();
                    measureChild(view,wspec,hspec);
                }
                added = true;
            }
            int hOfChild = dimen;
            int wOfChild = 0;
            for(int i=0;i<getChildCount();i++) {
                View view = getChildAt(i);
                wOfChild+=view.getMeasuredWidth();
                if(wOfChild>w) {
                    wOfChild = 0;
                    hOfChild+=5*dimen/4;
                }
            }
            setMeasuredDimension(w,hOfChild);
        }

    }

}
