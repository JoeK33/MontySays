package com.myreliablegames.joe.montysays;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.widget.ImageButton;

import java.util.ArrayList;

public class EasyActivity extends Activity {

    private MemoryGame memoryGame
            ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        ImageButton button1 = (ImageButton) findViewById(R.id.easyButton1);
        ImageButton button2 = (ImageButton) findViewById(R.id.easyButton2);

        ArrayList<ImageButton> list = new ArrayList<>();
        list.add(button1);
        list.add(button2);

        memoryGame = new MemoryGame(list, this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        memoryGame.onBackPressed();
    }

}
