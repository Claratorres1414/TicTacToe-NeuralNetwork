import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
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

    /**
     * Mean Squared Error Loss Function.

     * Calculate how much the Network goy it wrong.
     * This is used to penalize the model.
     */
    public double mse(double[] output, double[] target) {
        List<AbstractMap.SimpleEntry<Double, Double>> zipped = zip(output, target);

        double sum = zipped.stream()
                .mapToDouble(pair -> {
                    double erro = pair.getKey() - pair.getValue();
                    return erro * erro;
                })
                .sum();
        return sum / output.length;
    }

    public List<AbstractMap.SimpleEntry<Double, Double>> zip(double[] input, double[] other) {
        List<AbstractMap.SimpleEntry<Double, Double>> zipped = new ArrayList<>();
        int size = Math.min(input.length, other.length);

        for (int i = 0; i < size; i++) {
            zipped.add(new AbstractMap.SimpleEntry<>(input[i], other[i]));
        }

        return zipped;
    }

    /**
     * Backpropagation:
     * - Loss Function: Mean Square Error (MSE).
     * - Propagate the error backwards in the network.
     * - Updating the weights.
     */
    public void backPropagation(double[] input, double[] target, double[] output, double learningRate) {
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