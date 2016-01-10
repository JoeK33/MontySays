package com.myreliablegames.joe.montysays;

import java.util.ArrayList;

/**
 * Created by Joe on 1/9/2016.
 */
public class MoveHolder {

    ArrayList playerMoves;
    ArrayList computerMoves;

    public MoveHolder(){
        playerMoves = new ArrayList();
        computerMoves = new ArrayList();
    }

    public void addPlayerMove(int i){
        playerMoves.add(i);
    }

    public void addComputerMove(int i){
        computerMoves.add(i);
    }

    public boolean checkMoves(){

        for(int i = 0; i < playerMoves.size(); i++){
            if(computerMoves.get(i) != playerMoves.get(i)){
                return false;
            }
        }
        return true;
    }

    public void clearMoves(){
        this.playerMoves.clear();
        this.computerMoves.clear();
    }

    public void clearPlayerMoves(){
        this.playerMoves.clear();
    }

    public ArrayList getComputerMoves(){
        return computerMoves;
    }

    public int getNumOfComputerMoves(){

        if(!computerMoves.isEmpty()) {
            return computerMoves.size();
        }else return 0;
    }

    public ArrayList getPlayerMoves(){
        return playerMoves;
    }

    public boolean roundOver(){
        return playerMoves.size() == computerMoves.size();
    }
}
