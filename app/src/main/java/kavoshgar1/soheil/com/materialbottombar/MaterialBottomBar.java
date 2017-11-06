package kavoshgar1.soheil.com.materialbottombar;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Random;

import Listeners.OnClickBottomItem;
import Listeners.OnClickNestedBottomItem;
import Models.NestedBottombar;

/**
 * Created by soheilmohammadi on 10/23/17.
 */

public class MaterialBottomBar extends LinearLayout{

    private static final String TAG = "MaterialBottomBar";

    private  final RectF[] rectF = {null};


    private Paint paint_circle ;


    private  static  int current_bottom_item = 0 ;

    private  LinearLayout  containerView ;

    private  ArrayList<Bitmap> icons ;
    private  ArrayList<String> titles ;
    private  ArrayList<Integer> background_colors = new ArrayList<>();
    private  ArrayList<NestedBottombar> nestedList = new ArrayList<>();
    private  int text_color =  0 ;
    private  OnClickBottomItem listener ;
    private  OnClickNestedBottomItem listenerNested ;
    private  int radiusCircle = 0 ;


    private  ArrayList<Button> btnsList = new ArrayList<>();

    private  ArrayList<ArrayList<Float>> points_bitmap_onCircle = new ArrayList<>();



    public MaterialBottomBar(Context context) {
        super(context);
        setWillNotDraw(false);
        init();
    }

    public MaterialBottomBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        init();
    }

    public MaterialBottomBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        init();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }


    private  void init() {

        paint_circle = new Paint();
        paint_circle.setAntiAlias(true);
        paint_circle.setColor(Color.RED);
        paint_circle.setStyle(Paint.Style.FILL);


        View view = inflate(getContext() , R.layout.material_bottom_bar , this);
        containerView = view.findViewById(R.id.container_material_bottom_bar);

    }


    public MaterialBottomBar setIcons(ArrayList<Bitmap> icons) {
        this.icons =  icons ;
        return  this ;
    }


    public MaterialBottomBar setFirstBackgroundBottomBar(String color) {
        containerView.setBackgroundColor(Color.parseColor(color));
        paint_circle.setColor(Color.parseColor(color));
        return  this ;
    }


    public MaterialBottomBar setFirstBackgroundBottomBar(int color) {
        containerView.setBackgroundColor(color);
        paint_circle.setColor(color);
        return  this ;
    }


    public MaterialBottomBar setTitles(ArrayList<String> titles) {
        this.titles = titles ;
        return  this ;
    }

    public MaterialBottomBar setBackgroundColors(ArrayList<Integer> colors) {
        this.background_colors = colors ;
        return  this ;
    }

    public MaterialBottomBar setTextsColor(int color) {
        this.text_color = color ;
        return  this ;
    }

    public MaterialBottomBar addListener(OnClickBottomItem listener) {
        this.listener = listener ;
        return  this ;
    }



    public MaterialBottomBar setNestedBottomBar(ArrayList<NestedBottombar> nestedList , OnClickNestedBottomItem listenerNested){
        this.nestedList = nestedList ;
        this.listenerNested = listenerNested ;
        return  this ;
    }

    public MaterialBottomBar setOverlayView(View view){
        view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                handleTouchNested(event);
                return false;
            }
        });
        return  this ;
    }

    public MaterialBottomBar setParentContainerRoot(View view){
        ( (ViewGroup) view ).setClipChildren(false);
        return  this ;
    }



    public MaterialBottomBar superTouchListener(MotionEvent event) {
        handleTouchNested(event);
        return  this ;
    }


    public void closeItem() {
        if(current_bottom_item != 0 ){
            final Button btn = findViewById(current_bottom_item);
            points_bitmap_onCircle.clear();
            rectF[0] = new RectF( btn.getLeft() , - 8 * btn.getTop(), btn.getRight() , btn.getHeight());
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (radiusCircle != 0) {
                        try {
                            radiusCircle --  ;
                            postInvalidate();
                            Thread.sleep(5);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                  btn.startAnimation(getReverseAnimationbtn(btn));
                }
            });
            thread.start();
        }

    }


    private void handleTouchNested(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            if(points_bitmap_onCircle.size() > 0 ) {
                for (int i = 0; i <points_bitmap_onCircle.size() ; i++) {
                    for (int j = 0; j <points_bitmap_onCircle.get(i).size() -1 ; j++) {
                        if( (int) Math.abs(event.getRawX() - points_bitmap_onCircle.get(i).get(j)) <= 25) {
                            listenerNested.onClicked(i+1);
                        }
                    }
                }
            }
        }
    }


    private  void setCircleAnimation(final Button btn){
        if(points_bitmap_onCircle.size() > 0 ){
            points_bitmap_onCircle.clear();
        }
        radiusCircle = 0 ;
        final float counter = 1.5f* Math.abs(rectF[0].top) ;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (radiusCircle != counter) {
                    try {
                        radiusCircle ++  ;
                        postInvalidate();
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                manageNestedAndInvalidate( btn,rectF[0].centerX() , rectF[0].centerY());
            }
        });
        thread.start();
    }





    public void build(@Nullable Typeface itemFont) {
        for (int i = 0 ; i < titles.size() ; i++) {
            final Button btn = new Button(getContext());
            btn.setId(i+1);
            btn.setLayoutParams(new LayoutParams(0 , LayoutParams.MATCH_PARENT , 1));
            btn.setText(titles.get(i));
            if(itemFont != null){
                btn.setTypeface(itemFont);
            }
            btn.setPadding( 0,20 ,0 , 0);
            final Bitmap icon =icons.get(i);
            btn.setBackgroundColor(Color.TRANSPARENT);
            btn.setCompoundDrawablesWithIntrinsicBounds(null , new BitmapDrawable(getResources(), icon), null , null);
            if(text_color != 0) {
                btn.setTextColor(text_color);
            }
            btnsList.add(btn);
            containerView.addView(btn);

            final int finalI = i+1;
            btn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    current_bottom_item = btn.getId() ;
                    circular_revealAnimation(finalI-1);
                    rectF[0] = new RectF( btn.getLeft() , - 8 * btn.getTop(), btn.getRight() , btn.getHeight());
                    itemAnimation(finalI);
                    setCircleAnimation(btn);
                    listener.onClicked(btn.getId());
                }
            });
        }


    }

    private void itemAnimation(final int position) {
        for (int j = 0; j <containerView.getChildCount() ; j++) {
            Button btn = (Button) containerView.getChildAt(j);

            if(btn.getId() != position) {
                btn.startAnimation(getReverseAnimationbtn(btn));
            }else {
                btn.startAnimation(getAnimationbtn(btn));
            }
        }
    }

    private void circular_revealAnimation(final int position) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Animator circular_reveal = ViewAnimationUtils.createCircularReveal(containerView , containerView.getWidth() / 2 , containerView.getHeight() / 2 , 0 , containerView.getHeight());
            circular_reveal.setDuration(200);
            circular_reveal.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                    if(background_colors.size()>0) {
                        containerView.setBackgroundColor(background_colors.get(position));
                        paint_circle.setColor(background_colors.get(position));
                    }else  {
                        int min = 0;
                        int max = 255;
                        Random r = new Random();
                        int random_color = Color.rgb(r.nextInt((max - min) + 1) + min , r.nextInt((max - min) + 1) + min , r.nextInt((max - min) + 1) + min);
                        containerView.setBackgroundColor(random_color);
                        paint_circle.setColor(random_color);
                    }


                }

                @Override
                public void onAnimationEnd(Animator animator) {

                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });

            circular_reveal.start();
        }else {
            int colorFrom = Color.TRANSPARENT;
            int colorTo =  background_colors.get(position);
            final ObjectAnimator colorAnimation = ObjectAnimator.ofObject(containerView, "backgroundColor", new ArgbEvaluator(), colorFrom, colorTo);
            colorAnimation.setDuration(500);

            colorAnimation.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    paint_circle.setColor(background_colors.get(position));

                }

                @Override
                public void onAnimationEnd(Animator animator) {

                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            colorAnimation.start();
        }
    }


    private  AnimationSet getAnimationbtn(Button btn) {
        TranslateAnimation transAnim = new TranslateAnimation(1, 1 ,  btn.getBottom() ,  -1.5f * btn.getTop());
        transAnim.setDuration(500);
        RotateAnimation rotate_anim = new RotateAnimation( 0 , 360 , btn.getWidth() / 2, btn.getHeight() /2);
        rotate_anim.setDuration(500);
        AnimationSet anims= new AnimationSet(getContext() , null);
        anims.addAnimation(rotate_anim);
        anims.addAnimation(transAnim);
        anims.setFillAfter(true);
        return  anims ;
    }


    private  AnimationSet getReverseAnimationbtn(Button btn) {
        TranslateAnimation transAnim = new TranslateAnimation(1, 1 ,  -1.5f * btn.getTop()  ,  btn.getTop());
        transAnim.setDuration(500);
        RotateAnimation rotate_anim = new RotateAnimation( 360 , 0 , btn.getWidth() / 2, btn.getHeight() /2);
        rotate_anim.setDuration(500);
        AnimationSet anims= new AnimationSet(getContext() , null);
        anims.addAnimation(rotate_anim);
        anims.addAnimation(transAnim);
        return  anims ;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(rectF[0] != null) {
            canvas.drawCircle(rectF[0].centerX(), rectF[0].centerY(), radiusCircle, paint_circle);
            if (points_bitmap_onCircle.size() > 0) {
                for (int i = 0; i < nestedList.size(); i++) {
                    for (int j = 0; j <nestedList.get(i).bitmaps.size() ; j++) {
                        for (int w = 0; w < points_bitmap_onCircle.get(j).size() - 1; w++) {
                            canvas.drawBitmap(nestedList.get(i).bitmaps.get(j), points_bitmap_onCircle.get(j).get(w), points_bitmap_onCircle.get(j).get(w + 1), new Paint());
                        }

                    }

                }
            }
        }

    }



    private  void manageNestedAndInvalidate( Button btn,float center_x_circle , float center_y_circle){

        if (nestedList.size() > 0) {
            for (int i = 0; i < nestedList.size(); i++) {
                if(nestedList.get(i).wich_item == btn.getId()) {
                    for (int j = 0; j <nestedList.get(i).bitmaps.size() ; j++) {
                        int degree ;
                        switch (nestedList.get(i).bitmaps.size()) {
                            case 1 :
                                degree = 90;
                                break;

                            case 2 :
                                degree = 70 ;
                                break;

                            default:
                                degree = ((180 / nestedList.get(i).bitmaps.size()) - 10);
                                break;
                        }

                        float point_x = center_x_circle + (radiusCircle * (float) Math.cos(( degree * (j+1)) * Math.PI / 180));
                        float point_y = center_y_circle + (radiusCircle * (float) Math.sin(-(degree * (j+1)) * Math.PI / 180 ));

                        ArrayList<Float> point  =  new ArrayList<>();
                        point.add(point_x);
                        if(j==0) {
                            point.add(point_y - (radiusCircle/(nestedList.get(i).bitmaps.size() * nestedList.get(i).bitmaps.size())));
                        }else {
                            point.add(point_y - (radiusCircle/nestedList.get(i).bitmaps.size()));
                        }
                        points_bitmap_onCircle.add(point);

                    }

                }

            }

            postInvalidate();


        }else  {
            postInvalidate();
        }
    }


}
