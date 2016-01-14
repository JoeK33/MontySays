package com.myreliablegames.joe.montysays;

import android.app.Activity;
import android.media.MediaPlayer;
import android.widget.ImageButton;

import java.util.List;

/**
 * Created by Joe on 1/9/2016.
 * <p/>
 * The main game class.  Controls game workings.
 */
public class MemoryGame {

    private ButtonController buttonController;
    private MoveHolder moveHolder;
    private ScoreManager scoreDisplayManager;
    private Activity activity;
    private boolean isPlayerTurn;
    private int numButtons;

    public MemoryGame(List<ImageButton> buttons, Activity activity) {

        this.activity = activity;
        buttonController = new ButtonController(buttons, activity, this);
        moveHolder = new MoveHolder();
        isPlayerTurn = false;
        numButtons = buttons.size();
        scoreDisplayManager = new ScoreManager(activity);

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

        // player loses
        if (!moveHolder.checkMoves()) {
            isPlayerTurn = false;
            buttonController.lossIndicate(moveHolder.getLastCorrectMove());
            playLoseSoundAndStartNewGame();

            // player is right
        } else if (moveHolder.roundOver()) {
            scoreDisplayManager.saveHighScore(moveHolder.getComputerMoves().size());
            scoreDisplayManager.updateHighScore();
            scoreDisplayManager.setCurrentScore(moveHolder.getComputerMoves().size());
            isPlayerTurn = false;
            addComputerMove();
            indicateComputerMoves();
            moveHolder.clearPlayerMoves();
        }


    }

    private void newGame() {
        // sets score to 1 to return to 1st round
        scoreDisplayManager.setCurrentScore(1);
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
