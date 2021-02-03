package fr.umontpellier.iut.conquest.strategies;

import fr.umontpellier.iut.conquest.Board;
import fr.umontpellier.iut.conquest.Move;
import fr.umontpellier.iut.conquest.Player;

import java.util.ArrayList;


public class Naive implements Strategy {

    public Move getMove(Board board, Player player){
           ArrayList<Move> l = new ArrayList<>(player.getGame().getBoard().getValidMoves(player));
           return l.get(0);
    }
}
