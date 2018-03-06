package sample.solvers;

import java.util.List;

public class LagrangeSolver extends Solver implements Interpolation {

    public LagrangeSolver(List<Double> xList, List<Double> yList) {
        super(xList, yList);
    }

    public double getInterpolationFunction(double x) {
        double resultOfInterpolation = 0;
        for (int i = 0; i < AMOUNT_OF_POINTS; i++) {
            double basisPolynomial = 1;
            for (int j = 0; j < AMOUNT_OF_POINTS; j++) {
                if (i != j) {
                    basisPolynomial *= (x - xList.get(j)) / (xList.get(i) - xList.get(j));
                }
            }
            resultOfInterpolation += yList.get(i) * basisPolynomial;
        }
        return resultOfInterpolation;
    }
}
