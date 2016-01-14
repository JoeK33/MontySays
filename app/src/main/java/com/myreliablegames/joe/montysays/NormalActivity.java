package com.myreliablegames.joe.montysays;


import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.widget.ImageButton;

import java.util.ArrayList;

public class NormalActivity extends Activity {

    private MemoryGame memoryGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        ImageButton button1 = (ImageButton) findViewById(R.id.normalButton1);
        ImageButton button2 = (ImageButton) findViewById(R.id.normalButton2);
        ImageButton button3 = (ImageButton) findViewById(R.id.normalButton3);
        ImageButton button4 = (ImageButton) findViewById(R.id.normalButton4);


        ArrayList<ImageButton> list = new ArrayList<>();
        list.add(button1);
        list.add(button2);
        list.add(button3);
        list.add(button4);

        memoryGame = new MemoryGame(list, this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        memoryGame.onBackPressed();
    }


}
