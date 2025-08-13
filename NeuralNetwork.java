public class NeuralNetwork {
    private final double[][] inputToHiddenWeights;
    private final double[][] outputToHiddenWeights;

    /*
        TODO:
             - Loss Function: MSE
             - Backpropagation
             - Conectar a rede no jogo
             - Add Bias to Forward: Fitting Data
     */



    public NeuralNetwork(int inputSize, int hiddenSize, int outputSize) {
        //uma matriz 3X3, que é o jogo
        //A quantidade de neurônios depende da complexidadde do problema, como o jogo é simples ele pode ser input + 1
        //A saída vai ser 9 também, porque o output que queremos é o status do jogo

        inputToHiddenWeights = new double[inputSize][hiddenSize];
        outputToHiddenWeights = new double[hiddenSize][outputSize];

        initializeInputWeights(inputToHiddenWeights);
        initializeOutputWeights(outputToHiddenWeights);
    }

    /**private void initializeWeights(double[][] weights) {
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[0].length; j++) {
                weights[i][j] = Math.random() * 2 -1; //Aleatório entre -1 e 1
            }
        }
    }*/

    private void initializeInputWeights(double[][] weights) {
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[0].length; j++) {
                if (isWinningPosition(i)) {
                    weights[i][j] = 1.0;
                } else {
                    weights[i][j] = 0.1;
                }
            }
        }
    }

    private void initializeOutputWeights(double[][] weights) {
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[0].length; j++) {
                if (i % 3 == 0) {
                    weights[i][j] = 1.0;
                } else {
                    weights[i][j] = 0.1;
                }
            }
        }
    }

    private boolean isWinningPosition(int index) {
        return index == 0 || index == 2 || index == 6 || index == 8;
    }

    public double[] forward(double[] input) {
        double[] hidden = dot(input, inputToHiddenWeights);

        return dot(hidden, outputToHiddenWeights);
    }

    private double sigmoid (double x) {
        return 1 / (1 + Math.exp(-x));
    }

    private double[] dot(double[] inputs, double[][] weights) {
        int outputSize = weights[0].length;
        double[] outputs = new double[outputSize];

        for (int j = 0; j < outputSize; j++) {
            double sum = 0;
            for (int i = 0; i < inputs.length; i++) {
                sum += inputs[i] * weights[i][j];
            }
            outputs[j] = sigmoid(sum);
        }
        return outputs;
    }
}