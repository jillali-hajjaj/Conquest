package fr.umontpellier.iut.conquest.strategies;

import fr.umontpellier.iut.conquest.Board;
import fr.umontpellier.iut.conquest.BoardMemento;
import fr.umontpellier.iut.conquest.Move;
import fr.umontpellier.iut.conquest.Player;

import java.util.ArrayList;


public class Minmax implements Strategy {
    /**
     * Profondeur.
     */
    private int value;

    public Minmax(int scan) {
        value = scan;
    }

    public Move getMove(Board board, Player player) {
        Board test = new Board(board);
        return getBestMove(test, player);
    }

    private Move getBestMove(Board board, Player player){
        Move bestMove = null;
        int size = board.getSize();
        int maxscore = -(size*size);
        int beta =size*size;
        int alpha =-(size*size);
        int score;
        for (Move move: board.getValidMoves(player)){
            Board test = new Board(board);
            test.movePawn(move);
            score = getMinScore(test,player,value,alpha,beta);
            if (score>maxscore){
                maxscore=score;
                bestMove= move;
            }
        }
        return bestMove;
    }

    private int getMaxScore(Board board, Player player, int val,int alpha, int beta){
        if (player.getGame().isFinished()||val==0){
            return board.getNbPawns(player);
        } else {
            int size=board.getSize();
            int maxScore = -(size*size);
            int testScore;
            for (Move move : board.getValidMoves(player)){
                Board test = new Board(board);
                test.movePawn(move);
                testScore = getMinScore(test, player, val-1,alpha,beta);
                if(testScore>beta){
                    return testScore;
                }
                if(alpha>=beta){
                    break;
                }
                maxScore = Math.max(maxScore,testScore);
                alpha = Math.max(alpha,testScore);
            }
            return maxScore;
        }
    }

    private int getMinScore(Board board, Player player, int val,int alpha, int beta){
        if (player.getGame().isFinished()||val==0){
            return board.getNbPawns(player)-board.getNbPawns(player.getGame().getOtherPlayer(player));
        } else {
            int size = board.getSize();
            int minScore = size*size;
            for (Move move: board.getValidMoves(player.getGame().getOtherPlayer(player))){
                Board test = new Board(board);
                test.movePawn(move);
                int testScore = getMaxScore(board,player,val-1,alpha,beta);
                if(testScore<alpha){
                    return testScore;
                }
                if(alpha>=beta){
                    break;
                }
                minScore = Math.min(minScore,testScore);
                beta = Math.min(beta,testScore);
            }
            return minScore;
        }
    }
}