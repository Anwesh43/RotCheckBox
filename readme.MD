#RotCheckBox
##Android Library to create awesome checkbox library.

##Usage
###To Create Individual CheckBoxes(In Activity file)
```
    RotCheckBox checkBox = new RotCheckBox(this,"Yes");
```
###To add checkBoxListener
```
    checkBox.setRotCheckBoxListener(new RotCheckBoxListener(){
        public void onComplete() {
            ...
            //Stuff you want to here
        }
    });
```
###To show checkbox
```
    checkBox.show();
```

###To show checkbox in a particular coordinate say x,y
```
    checkBox.show(x,y)
```
