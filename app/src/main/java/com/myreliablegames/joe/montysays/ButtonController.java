package com.myreliablegames.joe.montysays;

import android.annotation.TargetApi;
import android.app.Activity;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joe on 1/7/2016.
 */
public class ButtonController extends DebouncedOnClickListener {

    private List<ImageButton> buttons;
    private Activity activity;
    private Handler handler;
    private MemoryGame game;
    final SoundPool soundPool;


    public ButtonController(List<ImageButton> buttons, Activity activity, MemoryGame game) {
        super(100);

        this.buttons = buttons;
        this.activity = activity;
        this.game = game;
        soundPool = getSoundPool();
        setListeners(this.buttons);
        handler = new Handler();

    }

    private SoundPool getSoundPool() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getNewSoundPool();
        } else {
            return getOldSoundPool();
        }

    }


    private void setListeners(List<ImageButton> list) {

        for (ImageButton b : list) {
            b.setOnClickListener(this);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected SoundPool getNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .build();
        SoundPool soundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();

        return soundPool;
    }

    @SuppressWarnings("deprecation")
    protected SoundPool getOldSoundPool() {
        return new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
    }

    @Override
    public void onDebouncedClick(View v) {

        if (game.isPlayerTurn()) {

            switch (v.getId()) {

                case R.id.easyButton1: {
                    addMoveAndIndicate(1);
                    break;
                }
                case R.id.easyButton2: {
                    addMoveAndIndicate(2);
                    break;
                }
                case R.id.normalButton1: {
                    addMoveAndIndicate(1);
                    break;
                }
                case R.id.normalButton2: {
                    addMoveAndIndicate(2);
                    break;
                }
                case R.id.normalButton3: {
                    addMoveAndIndicate(3);
                    break;
                }
                case R.id.normalButton4: {
                    addMoveAndIndicate(4);
                    break;
                }
                case R.id.hardButton1: {
                    addMoveAndIndicate(1);
                    break;
                }
                case R.id.hardButton2: {
                    addMoveAndIndicate(2);
                    break;
                }
                case R.id.hardButton3: {
                    addMoveAndIndicate(3);
                    break;
                }
                case R.id.hardButton4: {
                    addMoveAndIndicate(4);
                    break;
                }
                case R.id.hardButton5: {
                    addMoveAndIndicate(5);
                    break;
                }
                case R.id.hardButton6: {
                    addMoveAndIndicate(6);
                    break;
                }
            }
        }
    }


    private void playSound(int sound) {

        final MediaPlayer mediaPlayer = MediaPlayer.create(activity, sound);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.release();
            }
        });
        mediaPlayer.start();
    }

    private void changeButtonImage(ImageButton button, int pos) {


        switch (pos) {

            case 1: {
                imageFlip(button, R.drawable.button1, R.drawable.button1press);
                break;
            }
            case 2: {
                imageFlip(button, R.drawable.button2, R.drawable.button2press);
                break;
            }
            case 3: {
                imageFlip(button, R.drawable.button3, R.drawable.button3press);
                break;
            }
            case 4: {
                imageFlip(button, R.drawable.button4, R.drawable.button4press);
                break;
            }
            case 5: {
                imageFlip(button, R.drawable.button5, R.drawable.button5press);
                break;
            }
            case 6: {
                imageFlip(button, R.drawable.button6, R.drawable.button6press);
                break;
            }

        }


    }



    private void imageFlip(final ImageButton button, final int normalResource, int pressedResource) {

        button.setImageDrawable(ContextCompat.getDrawable(activity, pressedResource));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                button.setImageDrawable(ContextCompat.getDrawable(activity, normalResource));

            }
        }, 200);

    }

    private void addMoveAndIndicate(int buttonNumber) {

        game.addPlayerMove(buttonNumber);
        Log.d("Player Moves", Integer.toString(buttonNumber));
        indicite(buttonNumber);
        game.check();



    }

    public void indicite(int buttonNumber) {

        switch (buttonNumber) {

            case 1: {
                playSound(R.raw.sound1);
                changeButtonImage(buttons.get(0), 1);
                break;
            }
            case 2: {
                playSound(R.raw.sound2);
                changeButtonImage(buttons.get(1), 2);
                break;
            }
            case 3: {
                playSound(R.raw.sound3);
                changeButtonImage(buttons.get(2), 3);
                break;
            }
            case 4: {
                playSound(R.raw.sound4);
                changeButtonImage(buttons.get(3), 4);
                break;
            }
            case 5: {
                playSound(R.raw.sound5);
                changeButtonImage(buttons.get(4), 5);
                break;
            }
            case 6: {
                playSound(R.raw.sound6);
                changeButtonImage(buttons.get(5), 6);
                break;
            }

        }

    }

    public void indicateMoveList(final ArrayList l) {
        for (int i = 0; i < l.size(); i++) {
            final int finalI = i;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    indicite((int) l.get(finalI));
                }
            }, 1000 + (500 * i));

        }

        // start players turn after indications
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                game.startPlayerTurn();
            }
        }, 1000 + (500 * l.size()));

    }

    public void onBackPressed(){
        handler.removeCallbacksAndMessages(null);
    }




}
