package sample.solvers;

public interface Interpolation {
    int AMOUNT_OF_POINTS = 5;
    int POLYNOMIAL_DEGREE = 3;

    double getInterpolationFunction(double x);
}
