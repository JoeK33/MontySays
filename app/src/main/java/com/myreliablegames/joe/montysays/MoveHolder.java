package com.myreliablegames.joe.montysays;

import java.util.ArrayList;

/**
 * Created by Joe on 1/9/2016.
 * <p/>
 * Holds moves played each round.
 */
public class MoveHolder {

    ArrayList<Integer> playerMoves;
    ArrayList<Integer> computerMoves;

    public MoveHolder() {
        playerMoves = new ArrayList<>();
        computerMoves = new ArrayList<>();
    }

    public void addPlayerMove(int i) {
        playerMoves.add(i);
    }

    public void addComputerMove(int i) {
        computerMoves.add(i);
    }

    public boolean checkMoves() {

        for (int i = 0; i < playerMoves.size(); i++) {
            if (computerMoves.get(i) != playerMoves.get(i)) {
                return false;
            }
        }
        return true;
    }

    public void clearMoves() {
        this.playerMoves.clear();
        this.computerMoves.clear();
    }

    public void clearPlayerMoves() {
        this.playerMoves.clear();
    }

    public int getLastCorrectMove() {
        int playerLastMoveIndex = playerMoves.size() - 1;
        return computerMoves.get(playerLastMoveIndex);
    }

    public ArrayList<Integer> getComputerMoves() {
        return computerMoves;
    }

    public boolean roundOver() {
        return playerMoves.size() == computerMoves.size();
    }
}
