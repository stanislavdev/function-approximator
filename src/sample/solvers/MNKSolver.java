package sample.solvers;

import org.ejml.simple.SimpleMatrix;

import java.util.List;

public class MNKSolver extends Solver implements Interpolation {
    private SimpleMatrix X;
    private SimpleMatrix Y;

    public MNKSolver(List<Double> xList, List<Double> yList) {
        super(xList, yList);
        fillMatrixX();
        fillMatrixY();
    }

    private void fillMatrixX() {
        X = new SimpleMatrix(AMOUNT_OF_POINTS, POLYNOMIAL_DEGREE+1);
        for (int i = 0; i < X.getMatrix().numRows; i++) {
            for (int j = 0; j < POLYNOMIAL_DEGREE; j++) {
                X.set(i, j, Math.pow(xList.get(i), POLYNOMIAL_DEGREE - j));
            }
            X.set(i,POLYNOMIAL_DEGREE, 1);
        }
    }

    private void fillMatrixY() {
        Y = new SimpleMatrix(AMOUNT_OF_POINTS, 1);
        for (int i = 0; i < AMOUNT_OF_POINTS; i++) {
            Y.set(i, yList.get(i));
        }
    }

    public double getInterpolationFunction(double x) {
        SimpleMatrix coefficientMatrix = (((X.transpose().mult(X)).invert()).mult(X.transpose())).mult(Y);
        double resultOfInterpolation = 0;
        for (int i = 0; i <= POLYNOMIAL_DEGREE; i++) {
            resultOfInterpolation += coefficientMatrix.get(i)*Math.pow(x, POLYNOMIAL_DEGREE-i);
        }
        return resultOfInterpolation;
    }
}
