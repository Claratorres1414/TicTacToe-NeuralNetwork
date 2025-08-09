import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final int human = -1;
    private static final int computer = 1;
    private static NeuralNetwork neuralNetwork = new NeuralNetwork(9, 10, 9);

    public static void main(String[] args) {
        //boot
        while(true) {
            TicTacToe game = new TicTacToe();
            System.out.println("Let's start a new Tic-Tac-Toe game!");
            //if the game does not have a winner or is not full
            while(game.checkWinner() == 0 && !game.isFull()) {
                game.humanTurn();

                if(game.checkWinner() == human) {
                    game.printBoard();
                    System.out.println("Congratulations! You won!");
                    break;
                }

                if(game.isFull()){
                    break;
                }

                game.computerTurn(game, neuralNetwork);

                if(game.checkWinner() == computer) {
                    game.printBoard();
                    System.out.println("Not bad...but AI won!");
                }
            }
        }
    }
}
