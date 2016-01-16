package com.myreliablegames.joe.montysays;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Joe on 1/14/2016.
 * Background thread for scrolling
 */
public class BackgroundThread extends Thread {

    private final SurfaceHolder surfaceHolder;
    private BackgroundView backgroundView;
    private boolean run = false;

    public BackgroundThread(SurfaceHolder surfaceHolder, BackgroundView backgroundView) {
        this.surfaceHolder = surfaceHolder;
        this.backgroundView = backgroundView;
    }

    public void setRunning(boolean run) {
        this.run = run;
    }

    @Override
    @SuppressLint("WrongCall")
    public void run() {
        Canvas canvas;
        while (run) {
            canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder) {
                    backgroundView.onDraw(canvas);
                }
            } finally {
                // do this in a finally so that if an exception is thrown
                // during the above, we don't leave the Surface in an
                // inconsistent state
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            backgroundView.postInvalidate();
        }
    }
}
