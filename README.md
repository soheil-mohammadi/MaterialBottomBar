# MaterialBottomBar
An material android library  for Beauty Bottom Bar :)
It works for api +15 ;) 

<img src="https://github.com/soheil-mohammadi/MaterialBottomBar/blob/master/intro_lib.gif" width="40%"/> 

# HOW TO USE THIS LIBRARY ?!
<img src="https://github.com/soheil-mohammadi/MaterialBottomBar/blob/master/Screen_Shot.png" width="40%"/> 
Add it in your root build.gradle at the end of repositories:

```gradle

allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
``` 
</br>
Add the dependency :

```gradle
	dependencies {
	       compile 'com.github.soheil-mohammadi:MaterialBottomBar:2.0.3'
	}

```


# Xml :

```xml
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:id="@+id/container_activity_main"
    android:orientation="vertical"
    android:layout_height="match_parent"
    tools:context="kavoshgar1.soheil.com.materialbottombar.MainActivity">

    <TextView
        android:id="@+id/text_activity_main"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:onClick="onClick"
        android:layout_weight="1"
        android:text="Enjoy From Material Bottom Bar :) "
        android:gravity="center"
        android:textSize="17sp"
        android:textColor="@android:color/black"/>

    <kavoshgar1.soheil.com.materialbottombar.MaterialBottomBar
        android:id="@+id/material_bottom_bar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"

        />

</LinearLayout>

```

# Review Xml File :

In the above we define a  <b>MaterialBottomBarView</b>  and a simple a TextView .

# Java File :

```java
package kavoshgar1.soheil.com.materialbottombar;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import Listeners.OnClickBottomItem;
import Listeners.OnClickNestedBottomItem;
import Models.NestedBottombar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MaterialBottomBar material_bottom_bar ;
    private LinearLayout container_activity_main ;
    private TextView text_activity_main ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        material_bottom_bar = (MaterialBottomBar) findViewById(R.id.material_bottom_bar);
        container_activity_main =(LinearLayout) findViewById(R.id.container_activity_main);
        text_activity_main =(TextView) findViewById(R.id.text_activity_main);

        initMaterialBottomBar();

    }

    private void initMaterialBottomBar() {
        ArrayList<String> titles = new ArrayList<>();
        titles.add("photo");
        titles.add("support");
        titles.add("education");

        ArrayList<Bitmap> icons = new ArrayList<>();
        icons.add(BitmapFactory.decodeResource(getResources() , R.drawable.photo));
        icons.add(BitmapFactory.decodeResource(getResources() , R.drawable.support));
        icons.add(BitmapFactory.decodeResource(getResources() , R.drawable.education));


        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#64B5F6"));
        colors.add(Color.parseColor("#689F38"));
        colors.add(Color.parseColor("#EF5350"));



        final ArrayList<NestedBottombar> nestedBottombars = new ArrayList<>();
        ArrayList<Bitmap> iconsNested = new ArrayList<>();
        iconsNested.add(BitmapFactory.decodeResource(getResources() , R.drawable.telegram));
        iconsNested.add(BitmapFactory.decodeResource(getResources() , R.drawable.whatsapp));

        nestedBottombars.add(new NestedBottombar(2 , iconsNested));
        material_bottom_bar.setTitles(titles).setIcons(icons).setParentContainerRoot(container_activity_main).  setOverlayView(text_activity_main).setBackgroundColors(colors).setFirstBackgroundBottomBar(Color.parseColor("#689F38")).setNestedBottomBar(nestedBottombars, new OnClickNestedBottomItem() {
            @Override
            public void onClicked(int id) {
                switch (id) {
                    case  1 :
                        Toast.makeText(MainActivity.this , "TelegramSupport" ,Toast.LENGTH_SHORT).show();
                        break;

                    case 2 :
                        Toast.makeText(MainActivity.this , "Whatsapp Support" ,Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }).setTextsColor(Color.WHITE).addListener(new OnClickBottomItem() {
            @Override
            public void onClicked(int id) {
                switch (id) {

                    case  1 :
                        final AlertDialog.Builder alert = new  AlertDialog.Builder (MainActivity.this);
                        alert.setTitle("Dialog Test");
                        alert.setNegativeButton("Photo Library !", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                               // material_bottom_bar.closeItem();
                                material_bottom_bar.close();
                                dialogInterface.dismiss();
                            }
                        }).show();
                        break;


                    case 2 :
                        //TODO
                        break;


                    case 3 :
                        //TODO
                        break;
                }
            }
        }).build(null);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case  R.id.text_activity_main :
                material_bottom_bar.open();
                break;
        }
    }
}


```


# Review Java File :

In the above we initialize TextView and MaterialBottomBar in onCretae method .

-------------------------------------------------------------------------------------
##Contact :
You can send your comments for improve this library to me ;) </br>
Email : mad4r20@gmail.com </br>
Telegram : <a href="https://t.me/p_soheil_mohammadi_p">Soheil Mohammadi</a> </br>

-------------------------------------------------------------------------------------
Good Luck :)
