package com.myreliablegames.joe.montysays;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.TextView;

/**
 * Created by Joe on 1/9/2016.
 * <p/>
 * Keeps track of displaying and saving scores.
 */
public class ScoreManager {

    private TextView scoreView;
    private TextView highScoreView;
    private int difficulty;
    private Activity activity;

    public ScoreManager(Activity activity) {

        this.activity = activity;
        scoreView = (TextView) activity.findViewById(R.id.currentScore);
        highScoreView = (TextView) activity.findViewById(R.id.highScore);
        difficulty = (int) activity.getIntent().getExtras().get("difficulty");

        updateHighScore();
        setCurrentScore(1);

    }

    // displays the saved high score.
    public void updateHighScore() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);

        switch (difficulty) {
            case 0: {
                highScoreView.setText(Integer.toString(preferences.getInt("easyScore", 0)));
                break;
            }
            case 1: {
                highScoreView.setText(Integer.toString(preferences.getInt("normalScore", 0)));
                break;
            }
            case 2: {
                highScoreView.setText(Integer.toString(preferences.getInt("hardScore", 0)));
                break;
            }
            default: {
                // do nothing
                break;
            }
        }
    }


    public void setCurrentScore(int score) {
        scoreView.setText(Integer.toString(score));
    }

    public void saveHighScore(int score) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = preferences.edit();

        switch (difficulty) {
            case 0: {

                if (score > preferences.getInt("easyScore", 0)) {
                    editor.putInt("easyScore", score).apply();
                }
                break;
            }
            case 1: {
                if (score > preferences.getInt("normalScore", 0)) {
                    editor.putInt("normalScore", score).apply();
                }
                break;
            }
            case 2: {
                if (score > preferences.getInt("hardScore", 0)) {
                    editor.putInt("hardScore", score).apply();
                }
                break;
            }
            default: {
                // do nothing
                break;
            }
        }

    }
}
