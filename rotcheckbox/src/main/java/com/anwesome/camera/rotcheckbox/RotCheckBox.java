package com.anwesome.camera.rotcheckbox;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.hardware.display.DisplayManager;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by anweshmishra on 28/01/17.
 */
public class RotCheckBox {
    private String text;
    private Activity activity;
    private RotCheckBoxListener rotCheckBoxListener;
    private RotCheckBoxView rotCheckBoxView;
    private RotCheckBoxGroup group;
    private int groupIndex = -1;
    public void unselectCheckBox() {
        rotCheckBoxView.unselect();
    }
    public String getText() {
        return text;
    }
    public void setGroup(RotCheckBoxGroup rotCheckBoxGroup) {
        this.group = rotCheckBoxGroup;
    }
    public RotCheckBox(Activity activity,String text) {
        this.activity = activity;
        this.text = text;
    }
    public RotCheckBox(String text) {
        this.text = text;
    }
    public void setRotCheckBoxListener(RotCheckBoxListener rotCheckBoxListener) {
        this.rotCheckBoxListener = rotCheckBoxListener;
    }
    public void setGroupIndex(int index) {
        this.groupIndex = index;
    }
    public int hashCode() {
        return text.hashCode()+(rotCheckBoxView!=null?rotCheckBoxView.hashCode():0);
    }
    public void createView(ViewGroup viewGroup,int dimen) {
        rotCheckBoxView = new RotCheckBoxView(viewGroup.getContext());
        if(viewGroup!=null) {
            viewGroup.addView(rotCheckBoxView,new ViewGroup.LayoutParams(5*dimen/2,dimen));
        }

    }
    public RotCheckBoxView getRotCheckBoxView() {
        return rotCheckBoxView;
    }
    public void show(int... coords) {
        if(activity!=null && rotCheckBoxView==null) {
            rotCheckBoxView = new RotCheckBoxView(activity);
            Point size = getScreenDimensions();
            int w = size.x,h = size.y,viewDimen = h/20;
            if(w>h) {
                viewDimen = w/20;
            }
            if(coords.length==2) {
                int x = coords[0];
                int y = coords[1];
                rotCheckBoxView.setX(x);
                rotCheckBoxView.setY(y);
            }
            activity.addContentView(rotCheckBoxView,new ViewGroup.LayoutParams(5*viewDimen,viewDimen));
        }
    }
    private Point getScreenDimensions() {
        Point size = new Point();
        DisplayManager displayManager = (DisplayManager)activity.getSystemService(Context.DISPLAY_SERVICE);
        Display display = displayManager.getDisplay(0);
        if(display != null) {
            display.getRealSize(size);
        }
        return  size;

    }
    private class RotCheckBoxView extends View{
        private boolean isAnimated = false;
        private float deg = 0,scale = 0,speedDeg = 72,speedScale = 0.2f,dir = -1;
        private int font = 15,time = 0;
        private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        public RotCheckBoxView(Context context) {
            super(context);
        }
        public void onDraw(Canvas canvas) {
            int w = canvas.getWidth(),h = canvas.getHeight();
            paint.setStrokeWidth(h/20);
            if(time == 0) {
                paint.setTextSize(h/2);
                setProperFont(w-h-h/20);
            }
            float r = h/4;
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.parseColor("#BDBDBD"));
            canvas.drawRoundRect(new RectF(h/20,h/20,19*h/20,19*h/20),r,r,paint);
            paint.setStyle(Paint.Style.FILL);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.tick);
            bitmap = Bitmap.createScaledBitmap(bitmap,h/2,h/2,true);
            canvas.save();
            canvas.translate(h/2,h/2);
            canvas.scale(scale,scale);
            canvas.rotate(deg);
            canvas.drawBitmap(bitmap,-bitmap.getWidth()/2,-bitmap.getHeight()/2,paint);
            canvas.restore();
            paint.setColor(Color.BLACK);
            canvas.drawText(text,h+h/20,5*h/8,paint);
            time++;
            if(isAnimated) {
                deg+=speedDeg*dir;
                scale+=speedScale*dir;
                if(deg >= 360 || deg<=0) {
                    if(deg<=0) {
                        deg = 0;
                        scale = 0;
                    }
                    if(deg>=360) {
                        deg = 360;
                        scale = 1;
                        if(rotCheckBoxListener != null) {
                            rotCheckBoxListener.onComplete();
                        }
                    }
                    isAnimated = false;
                }
                try {
                    Thread.sleep(50);
                    invalidate();
                } catch (Exception ex) {

                }
            }
        }
        private void setProperFont(int w) {
            float textSize = paint.measureText(text);
            if(textSize>=w) {
                paint.setTextSize(paint.getTextSize()-1);
                setProperFont(w);
            }
        }
        public void unselect() {
            if(dir == 1 && !isAnimated) {
                dir = -1;
                isAnimated = true;
                invalidate();
            }
        }
        public boolean onTouchEvent(MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN && !isAnimated) {
                dir*=-1;
                isAnimated = true;
                if(group!=null && scale == 0) {
                    group.selectCheckBox(groupIndex);
                }
                postInvalidate();
            }
            return true;
        }
    }

}
