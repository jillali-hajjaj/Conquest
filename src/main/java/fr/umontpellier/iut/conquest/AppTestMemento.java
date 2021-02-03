package fr.umontpellier.iut.conquest;

public class AppTestMemento {
    public static void main(String[] args) {
        // Déclaration :
        BoardCaretaker caretaker = new BoardCaretaker();
        Board board = new Board(5);
        Player player1 = new Player(null, null, null, 1);
        Player player2 = new Player(null, null, null, 2);

        board.initField(player1, player2);

        BoardMemento memento = board.saveToMemento();
        caretaker.addMemento(memento);

        board.movePawn(new Move(0, 0, 1, 1));

        System.out.println(board.toString());

        BoardMemento memento2 = board.saveToMemento();
        caretaker.addMemento(memento2);

        board.movePawn(new Move(1, 1, 2, 2));

        // Avant annulé
        System.out.println(board.toString());

        BoardMemento memento3 = caretaker.getMemento();
        board.undoFromMemento(memento3);

        Board boardInMemento3 = new Board(memento3.getField());

        // Annulé
        System.out.println(board.toString());

    }
}
