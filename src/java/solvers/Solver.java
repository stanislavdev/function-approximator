package sample.solvers;

import java.util.List;

abstract class Solver {
    List<Double> xList;
    List<Double> yList;

    Solver(List<Double> xList, List<Double> yList) {
        this.xList = xList;
        this.yList = yList;
    }
}
