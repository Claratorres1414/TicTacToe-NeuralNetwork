import java.util.Arrays;
import java.util.Scanner;

public class TicTacToe {
    private static final Scanner sc = new Scanner(System.in);
    public int[] board = new int[9];
    private static final int human = -1;
    private static final int computer = 1;


    public  void printBoard() {
        for (int i = 0; i < board.length; i++) {
            String symbol = switch (board[i]){
                case -1 -> " X ";
                case 1 -> " O ";
                default -> " # ";
            };
            if (i == 2 || i == 5 || i == 8) {
                System.out.println(symbol);
            }else {
                System.out.print(symbol);
            }
        }
    }

    /**
     * @param position: Int representing the position in the board
     * @param player: 0 to represent Human and 1 to represent the AI
     * @return TRUE if movement was valid
     */
    public Boolean makeMove(int position, int player) {
        if (board[position] == 0) {
            board[position] = player;
            return true;
        }
        return false;
    }

    public Boolean isFull(){
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 0) {
                return false;
            }
        }
        return true;
    }

    public void humanTurn() {
        printBoard();
        System.out.print("Enter your move (0-8): ");
        int playerMovement = sc.nextInt();
        while(playerMovement < 0 || playerMovement > 8) {
            System.out.print("Invalid move! Try again: ");
            playerMovement = sc.nextInt();
        }
        Boolean move = makeMove(playerMovement, human);
        while(!move) {
            System.out.print("Invalid move! Try again: ");
            playerMovement = sc.nextInt();
            while (playerMovement < 0 || playerMovement > 8) {
                System.out.print("Invalid move! Try again: ");
                playerMovement = sc.nextInt();
            }
            move = makeMove(playerMovement, human);
        }
    }

    public void computerTurn(TicTacToe game, NeuralNetwork neuralNetwork) {
        //Input matriz
        double[] boardInput = Arrays.stream(game.board)  // Stream<int[]>
                .mapToDouble(x -> (double) x)                 // converte para double
                .toArray();

        double[] moveProbabilities = neuralNetwork.forward(boardInput);

        System.out.println("--- AI moved ---");
        int aiMovement = (int)(Math.random() * 9);

        Boolean move = makeMove(aiMovement, computer);
        while(!move) {
            aiMovement = (int) (Math.random() * 9);
            move = makeMove(aiMovement, computer);
        }
    }

    public int checkWinner() {
        int[][] winningCombinations = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // linhas
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // colunas
                {0, 4, 8}, {2, 4, 6}             // diagonais
        };

        for (int[] combo : winningCombinations) {
            int a = combo[0];
            int b = combo[1];
            int c = combo[2];

            if (board[a] != 0 && board[a] == board[b] && board[b] == board[c]) {
                int winner = board[a];
                return winner; // -1 ou 1, ou seja, humano ou IA
            }
        }

        return 0; // ninguém venceu
    }
}
