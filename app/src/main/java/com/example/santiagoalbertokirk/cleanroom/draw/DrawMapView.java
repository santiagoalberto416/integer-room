package com.example.santiagoalbertokirk.cleanroom.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.example.santiagoalbertokirk.cleanroom.R;

/**
 * Created by Santiago on 25/11/2017.
 */

public class DrawMapView extends View {

    private Rect rectangle;
    private int widthOfView;
    private int posX;
    private int posY;
    private Paint paint;

    public DrawMapView(Context context, int screenWidth, int x, int y, int widthOfIndicator) {
        super(context);
        if(x>96){
            x = 96;
        }
        if(y>96){
            y = 96;
        }

        this.posX = ( x * screenWidth ) / 100;
        this.posY = ( y * screenWidth ) / 100;
        this.widthOfView = widthOfIndicator;
        // create the Paint and set its color
        paint = new Paint();
        paint.setColor(context.getColor(R.color.colorPrimary));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawCircle(posX+(widthOfView), posY+(widthOfView), widthOfView, paint);
    }
}
