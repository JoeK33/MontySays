package com.myreliablegames.joe.montysays;

import android.app.Activity;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.ImageButton;

import java.util.List;

/**
 * Created by Joe on 1/9/2016.
 */
public class MemoryGame {

    private ButtonController buttonController;
    private MoveHolder moveHolder;
    private ScoreManager scoreManager;
    private Activity activity;
    private boolean isPlayerTurn;
    private int numButtons;

    public MemoryGame(List<ImageButton> buttons, Activity activity) {
        this.activity = activity;
        buttonController = new ButtonController(buttons, activity, this);
        moveHolder = new MoveHolder();
        isPlayerTurn = false;
        numButtons = buttons.size();
        scoreManager = new ScoreManager(activity);


        newGame();
    }

    public boolean isPlayerTurn() {
        return isPlayerTurn;
    }

    public void startPlayerTurn() {
        isPlayerTurn = true;
    }

    public void addPlayerMove(int move) {
        moveHolder.addPlayerMove(move);
    }

    private void addComputerMove() {
        moveHolder.addComputerMove((int) (Math.random() * numButtons) + 1);
    }

    private void indicateComputerMoves() {
        buttonController.indicateMoveList(moveHolder.getComputerMoves());
    }

    public void check() {

        if (!moveHolder.checkMoves()) {
            // player loses
            isPlayerTurn = false;
            playLoseSoundAndStartNewGame();
        } else if (moveHolder.roundOver()) {
            scoreManager.saveHighScore(moveHolder.getComputerMoves().size());
            scoreManager.updateHighScore();
            isPlayerTurn = false;
            addComputerMove();
            indicateComputerMoves();
            scoreManager.setCurrentScore(moveHolder.getComputerMoves().size());
            moveHolder.clearPlayerMoves();
        }


    }

    private void newGame() {


        scoreManager.setCurrentScore(1);

        Log.d("PlayerMoves", moveHolder.playerMoves.toString());
        Log.d("ComputerMoves", moveHolder.computerMoves.toString());

        isPlayerTurn = false;
        moveHolder.clearMoves();
        addComputerMove();
        indicateComputerMoves();


    }

    public void onBackPressed() {
        buttonController.onBackPressed();
    }

    private void playLoseSoundAndStartNewGame() {



        final MediaPlayer mediaPlayer = MediaPlayer.create(activity, R.raw.losesound);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.release();
                newGame();
            }
        });
        mediaPlayer.start();
    }
}
