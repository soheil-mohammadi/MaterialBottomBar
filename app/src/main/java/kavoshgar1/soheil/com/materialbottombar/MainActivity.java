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
        material_bottom_bar.setTitles(titles).setIcons(icons).setParentContainerRoot(container_activity_main).setOverlayView(text_activity_main).setBackgroundColors(colors).setFirstBackgroundBottomBar(Color.parseColor("#689F38")).setNestedBottomBar(nestedBottombars, new OnClickNestedBottomItem() {
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
