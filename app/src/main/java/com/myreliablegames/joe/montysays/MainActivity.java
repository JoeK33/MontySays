package com.myreliablegames.joe.montysays;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        Button easyButton = (Button) findViewById(R.id.easyButton);
        Button normalButton = (Button) findViewById(R.id.normalButton);
        Button hardButton = (Button) findViewById(R.id.hardButton);

        easyButton.setOnClickListener(this);
        normalButton.setOnClickListener(this);
        hardButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.easyButton: {
                Intent intent = new Intent(this, EasyActivity.class);
                intent.putExtra("difficulty", 0);
                startActivity(intent);
                break;
            }
            case R.id.normalButton: {
                Intent intent = new Intent(this, NormalActivity.class);
                intent.putExtra("difficulty", 1);
                startActivity(intent);
                break;
            }
            case R.id.hardButton: {
                Intent intent = new Intent(this, HardActivity.class);
                intent.putExtra("difficulty", 2);
                startActivity(intent);
                break;
            }

        }
    }
}
