package fr.umontpellier.iut.conquest;

public class BoardMemento {

    private Pawn[][] field;

    public BoardMemento(Pawn[][] fieldNew){
        field = new Pawn[fieldNew.length][fieldNew.length];
        for (int i = 0; i < fieldNew.length; i++) {
            for (int j = 0; j < fieldNew.length; j++) {
                if(fieldNew[i][j]!=null) {
                    field[i][j] = new Pawn(fieldNew[i][j].getPlayer());
                }
            }
        }
    }

    public Pawn[][] getField(){
        return field;
    }
}

