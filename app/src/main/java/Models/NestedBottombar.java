package Models;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by soheilmohammadi on 10/25/17.
 */

public class NestedBottombar {

    public  int wich_item ;
    public ArrayList<Bitmap> bitmaps ;

    public NestedBottombar(int wich_item , ArrayList<Bitmap> bitmaps ) {
        this.wich_item = wich_item ;
        this.bitmaps = bitmaps ;
    }

}
