package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import sample.solvers.Interpolation;
import sample.solvers.LagrangeSolver;
import sample.solvers.MNKSolver;

import java.util.ArrayList;
import java.util.List;


public class Controller {
    @FXML
    private LineChart<Number, Number> mainChart;

    @FXML
    private TextField yTextField1;

    @FXML
    private TextField yTextField2;

    @FXML
    private TextField yTextField3;

    @FXML
    private TextField yTextField4;

    @FXML
    private TextField yTextField5;

    private List<XYChart.Series<Number, Number>> points;
    private List<XYChart.Data<Number, Number>> dataList;

    private List<Double> xValues;
    private List<Double> yValues;

    private Interpolation solver;

    @FXML
    public void initialize() {
        mainChart.setLegendVisible(false);

        points = new ArrayList<>();
        dataList = new ArrayList<>();

        for (int i = 0; i < Interpolation.AMOUNT_OF_POINTS; i++) {
            points.add(new XYChart.Series<>());
            dataList.add(new XYChart.Data<>((double) (i + 1), 1.0));
        }

        addPointsOnTheChart();

        yTextField1.setText(points.get(0).getData().get(0).getYValue().toString());
        yTextField2.setText(points.get(1).getData().get(0).getYValue().toString());
        yTextField3.setText(points.get(2).getData().get(0).getYValue().toString());
        yTextField4.setText(points.get(3).getData().get(0).getYValue().toString());
        yTextField5.setText(points.get(4).getData().get(0).getYValue().toString());

        addListenersOnPoints(dataList.get(0), yTextField1);
        addListenersOnPoints(dataList.get(1), yTextField2);
        addListenersOnPoints(dataList.get(2), yTextField3);
        addListenersOnPoints(dataList.get(3), yTextField4);
        addListenersOnPoints(dataList.get(4), yTextField5);
    }

    @FXML
    void mainButtonClick(ActionEvent event) {
        mainChart.getData().clear();
        createXList();
        createYList();
        for (int i = 0; i < Interpolation.AMOUNT_OF_POINTS; i++) {
            setCoordinatesForData(dataList.get(i), xValues.get(i), yValues.get(i));
        }
        addPointsOnTheChart();
        addLagrangeLineOnTheChart();
        addMNKLineOnTheChart();
    }

    private void createXList() {
        xValues = new ArrayList<>();
        for (int i = 0; i < Interpolation.AMOUNT_OF_POINTS; i++) {
            xValues.add((double) (i + 1));
        }
    }

    private void createYList() {
        yValues = new ArrayList<>();
        yValues.add(Double.parseDouble(yTextField1.getText()));
        yValues.add(Double.parseDouble(yTextField2.getText()));
        yValues.add(Double.parseDouble(yTextField3.getText()));
        yValues.add(Double.parseDouble(yTextField4.getText()));
        yValues.add(Double.parseDouble(yTextField5.getText()));
    }

    private void addPointsOnTheChart() {
        int i = 0;
        for (XYChart.Series<Number, Number> point : points) {
            point.getData().clear();
            point.getData().add(dataList.get(i));
            i++;
        }
        mainChart.getData().addAll(points);
    }

    private void addMNKLineOnTheChart() {
        int numberOfPointsForBuildingTheLine = 10 * (Interpolation.AMOUNT_OF_POINTS + 1);
        double scaleCoefficient = 0.1;
        solver = new MNKSolver(xValues, yValues);
        XYChart.Series<Number, Number> mnkLineSeries = new XYChart.Series<>();
        for (int i = 0; i <= numberOfPointsForBuildingTheLine; i++) {
            mnkLineSeries.getData().add(new XYChart.Data<>(
                    scaleCoefficient * i, solver.getInterpolationFunction(scaleCoefficient * i)));
        }
        mainChart.getData().add(mnkLineSeries);
    }

    private void addLagrangeLineOnTheChart() {
        int numberOfPointsForBuildingTheLine = 10 * (Interpolation.AMOUNT_OF_POINTS + 1);
        double scaleCoefficient = 0.1;
        solver = new LagrangeSolver(xValues, yValues);
        XYChart.Series<Number, Number> lagrangeLineSeries = new XYChart.Series<>();
        for (int i = 0; i <= numberOfPointsForBuildingTheLine; i++) {
            lagrangeLineSeries.getData().add(new XYChart.Data<>(
                    scaleCoefficient*i, solver.getInterpolationFunction(scaleCoefficient*i)));
        }
        mainChart.getData().add(lagrangeLineSeries);
    }


    private void addListenersOnPoints(XYChart.Data<Number, Number> data, TextField yTextField) {
        int cursorSlowing = 1000;
        data.getNode().setOnMouseDragged((MouseEvent e) -> {
            data.setYValue((Double) data.getYValue() - (e.getY() / cursorSlowing));
            yTextField.setText(String.valueOf(data.getYValue()));
        });
    }

    private void setCoordinatesForData(XYChart.Data<Number, Number> data, double x, double y) {
        data.setXValue(x);
        data.setYValue(y);
    }
}
