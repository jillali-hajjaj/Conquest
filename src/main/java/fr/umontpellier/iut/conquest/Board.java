package fr.umontpellier.iut.conquest;

import java.util.ArrayList;
import java.util.List;

/**
 * Modélise un plateau.
 */
public class Board {
    /**
     * Tableau des pions.
     */
    private Pawn[][] field;

    /**
     * Constructeur.
     *
     * @param size : la taille du plateau.
     */
    public Board(int size) {
        field = new Pawn[size][size];
    }
    /*Constructeur par copie*/
    public Board(Board board){
        this.field = new Pawn[board.field.length][board.field.length];
        for (int i = 0; i < board.field.length; i++) {
            for (int j = 0; j < board.field.length; j++) {
                if(board.field[i][j]!=null) {
                    this.field[i][j] = new Pawn(board.field[i][j].getPlayer());
                }
            }
        }
    }

    /**
     * Constructeur pour Test.
     *
     * @param field : plateau prédéfini.
     */
    public Board(Pawn[][] field) {
        this.field = field;
    }

    /**
     * Les méthodes suivantes sont utilisées pour les tests automatiques. Il ne faut pas les utiliser.
     */
    public Pawn[][] getField() {
        return field;
    }

    /**
     * Retourne la taille du plateau.
     */
    public int getSize() {
        return field.length;
    }

    /**
     * Affiche le plateau.
     */
    public String toString() {
        int size = field.length;
        StringBuilder b = new StringBuilder();
        for (int r = -1; r < size; r++) {
            for (int c = -1; c < size; c++) {
                if (r == -1 && c == -1) {
                    b.append("_");
                } else if (r == -1) {
                    b.append("_").append(c);
                } else if (c == -1) {
                    b.append(r).append("|");
                } else if (field[r][c] == null) {
                    b.append("_ ");
                } else if (field[r][c].getPlayer().getColor() == 1) {
                    b.append("X ");
                } else {
                    b.append("O ");
                }
            }
            b.append("\n");
        }
        b.append("---");
        return b.toString();
    }

    /**
     * Initialise le plateau avec les pions de départ.
     * Rappel :
     * - player1 commence le jeu avec un pion en haut à gauche (0,0) et un pion en bas à droite.
     * - player2 commence le jeu avec un pion en haut à droite et un pion en bas à gauche.
     */
    public void initField(Player player1, Player player2) {
        field[0][0]=new Pawn(player1);
        int size=field.length;
        field[size-1][size-1]=new Pawn(player1);
        field[0][size-1]=new Pawn(player2);
        field[size-1][0]=new Pawn(player2);
    }

    /**
     * Vérifie si un coup est valide.
     * Rappel :
     * - Les coordonnées du coup doivent être dans le plateau.
     * - Le pion bougé doit appartenir au joueur.
     * - La case d'arrivée doit être libre.
     * - La distance entre la case d'arrivée est au plus 2.
     */
    public boolean isValid(Move move, Player player) {

        boolean resultat=true;
        int size = field.length;
        if (move.getColumn1()>=size||move.getColumn1()<0||move.getColumn2()>=size||move.getColumn2()<0||move.getRow1()>=size||move.getRow1()<0||move.getRow2()>=size||move.getRow2()<0){
            resultat=false;
        } else if (field[move.getRow1()][move.getColumn1()] == null){
            resultat = false;
        } else if(!field[move.getRow1()][move.getColumn1()].getPlayer().equals(player)){
            resultat = false;
        } else if((field[move.getRow2()][move.getColumn2()] != null)){
            resultat = false;
        } else if(distance(move.getRow1(),move.getColumn1(),move.getRow2(),move.getColumn2())==-1){
            resultat= false;
        }
        return resultat;
    }

    /**
     * Déplace un pion.
     *
     * @param move : un coup valide.
     *             Rappel :
     *             - Si le pion se déplace à distance 1 alors il se duplique pour remplir la case d'arrivée et la case de départ.
     *             - Si le pion se déplace à distance 2 alors il ne se duplique pas : la case de départ est maintenant vide et la case d'arrivée remplie.
     *             - Dans tous les cas, une fois que le pion est déplacé, tous les pions se trouvant dans les cases adjacentes à sa case d'arrivée prennent sa couleur.
     */
    public void movePawn(Move move) {
        int distance = distance(move.getRow1(),move.getColumn1(),move.getRow2(),move.getColumn2());

        switch (distance){
            case 1 :
                field[move.getRow2()][move.getColumn2()] = new Pawn(field[move.getRow1()][move.getColumn1()].getPlayer());
                break;
            case 2 : field[move.getRow2()][move.getColumn2()] = new Pawn(field[move.getRow1()][move.getColumn1()].getPlayer());
                field[move.getRow1()][move.getColumn1()] = null;
                break;
        }

        if (distance>0){
            Player player =field[move.getRow2()][move.getColumn2()].getPlayer();
            int colonneG = move.getColumn2()-1;
            int ligneG = move.getRow2()-1;
            int colonneD = move.getColumn2()+1;
            int ligneD = move.getRow2()+1;
            int dimension = field.length;
            if (colonneG>=0&&field[move.getRow2()][colonneG]!=null) {
                field[move.getRow2()][colonneG].setPlayer(player);
            }
            if (colonneD!=dimension&&field[move.getRow2()][colonneD]!=null) {
                field[move.getRow2()][colonneD].setPlayer(player);
            }
            if (ligneG>=0&&field[ligneG][move.getColumn2()]!=null) {
                field[ligneG][move.getColumn2()].setPlayer(player);
            }
            if (ligneD!=dimension&&field[ligneD][move.getColumn2()]!=null) {
                field[ligneD][move.getColumn2()].setPlayer(player);
            }

            if (colonneG>=0&&ligneG>=0&&field[ligneG][colonneG]!=null){
                field[ligneG][colonneG].setPlayer(player);
            }
            if (colonneD!=dimension&&ligneD!=dimension&&field[ligneD][colonneD]!=null){
                field[ligneD][colonneD].setPlayer(player);
            }

            if (colonneG>=0&&ligneD!=dimension&&field[ligneD][colonneG]!=null){
                field[ligneD][colonneG].setPlayer(player);
            }
            if (colonneD!=dimension&&ligneG>=0&&field[ligneG][colonneD]!=null){
                field[ligneG][colonneD].setPlayer(player);
            }

        }
    }

    public int distance(int iP1,int jP1,int iP2,int jP2){
        int difCol = Math.abs(jP1-jP2);
        int difRow = Math.abs(iP1-iP2);
        if(difRow==1&&difCol<2||difCol==1&&difRow<2||difRow==0&&difCol<2||difCol==0&&difRow<2){
            return 1;
        } else if (difCol==2&&difRow<=2||difRow==2&&difCol<=2||difCol==0&&difRow<=2||difRow==0&&difCol<=2){
            return 2;
        } else {
            return -1;
        }
    }

    /**
     * Retourne la liste de tous les coups valides de player.
     * S'il n'y a de coup valide, retourne une liste vide.
     */
    public List<Move> getValidMoves(Player player) {
        ArrayList<Move> resultat = new ArrayList<>();
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j <field.length ; j++) {
                for (int z=i-2; z<=i+2; z++){
                    for (int y=j-2; y<=j+2; y++){
                        Move move = new Move(i,j,z,y);
                        if(isValid(move,player)){
                            resultat.add(move);
                        }
                    }
                }
            }
        }
        return resultat;
    }

    /**
     * Retourne le nombre pions d'un joueur.
     */
    public int getNbPawns(Player player) {
        int nbPawns=0;
        int taille = field.length;
        for (int i=0; i<taille; i++){
            for (int j=0; j<taille; j++){
                if (field[i][j] != null) {
                    if (field[i][j].getPlayer().equals(player)) {
                        nbPawns += 1;
                    }
                }
            }
        }
        return nbPawns;
    }

    /*sauvegarde le coup du joueur*/

    public BoardMemento saveToMemento(){
        BoardMemento boardmemento = new BoardMemento(field);
        return boardmemento;
    }
    public void undoFromMemento(BoardMemento memento)
    {
        this.field = memento.getField();
    }

}
