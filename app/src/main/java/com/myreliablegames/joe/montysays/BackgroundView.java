package com.myreliablegames.joe.montysays;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;


/**
 * Created by Joe on 1/14/2016.
 * <p/>
 * Controls scrolling background
 */
public class BackgroundView extends SurfaceView implements SurfaceHolder.Callback {

    private BackgroundThread backgroundThread;
    private Bitmap bitmap;
    private Context context;
    private int width;
    private int tilesWide;
    private int height;
    private int tilesHigh;
    private int xOffset;
    private int yOffset;
    private long time;
    private int difficulty;

    public BackgroundView(Context context) {
        super(context);
        initilize(context);
    }

    public BackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray attributes = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.BackgroundView,
                0, 0);
        try {
            difficulty = attributes.getInteger(R.styleable.BackgroundView_difficulty, 0);
        } finally {
            attributes.recycle();
        }
        initilize(context);
    }

    private void initilize(Context context) {

        this.context = context;
        getHolder().addCallback(this);

        backgroundThread = new BackgroundThread(getHolder(), this);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fishtile);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        width = size.x;
        tilesWide = (width / bitmap.getWidth()) + 1;
        tilesWide = tilesWide * 2; // double tiles to give a buffer zone

        height = size.y;
        tilesHigh = (height / bitmap.getHeight()) + 1;
        tilesHigh = tilesHigh * 2; // double tiles to give a buffer zone

        time = System.nanoTime();
        xOffset = 0;
        yOffset = 0;
    }


    @Override
    public void onDraw(Canvas canvas) {

        long delta = System.nanoTime() - time;
        time = System.nanoTime();

        xOffset = xOffset + ((int) ((delta / 10000000)));
        yOffset = yOffset + ((int) ((delta / 10000000)));

        if (xOffset > (bitmap.getWidth())) {
            xOffset = 0;
        }

        if (yOffset > (bitmap.getHeight())) {
            yOffset = 0;
        }

        if (canvas != null) {
            // colors for different difficulty backgrounds
            switch (difficulty) {
                case 0: {
                    canvas.drawColor(Color.rgb(238, 213, 183));
                    break;
                }
                case 1: {
                    canvas.drawColor(Color.rgb(154, 205, 50));
                    break;
                }
                case 2: {
                    canvas.drawColor(Color.rgb(153, 255, 255));
                    break;
                }
                case 3: {
                    canvas.drawColor(Color.rgb(205, 73, 73));
                    break;
                }
            }
            // draw repeating tiles
            for (int i = 0; i < tilesWide; i++) {
                for (int j = 0; j < tilesHigh; j++) {
                    canvas.drawBitmap(bitmap, (i * bitmap.getWidth()) - xOffset, j * bitmap.getHeight() - yOffset, null);
                }
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        initilize(context);
        backgroundThread.setRunning(true);
        backgroundThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        backgroundThread.setRunning(false);
        while (retry) {
            try {
                backgroundThread.join();
                retry = false;
            } catch (InterruptedException e) {
                // we will try it again and again...
            }
        }
    }
}
