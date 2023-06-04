package com.example.examplemod.ramen_mod.event;

import java.util.ArrayList;

public class SensingBase {

    ArrayList<Long> time = new ArrayList<>();
    ArrayList<Double> rawX = new ArrayList<>();
    ArrayList<Double> rawY = new ArrayList<>();

    ArrayList<Double> x = new ArrayList<>();
    ArrayList<Double> y = new ArrayList<>();

    ArrayList<Double> dx = new ArrayList<>();
    ArrayList<Double> dy = new ArrayList<>();

    ArrayList<Double> norms = new ArrayList<>();

    void initSensorValues(double x, double y, long time) {
        rawX.add(x);
        rawY.add(y);
        this.time.add(time);
    }

    void calculation() {
        smoothingFilter();
        differentialFilter();
        normXY();
    }

    void smoothingFilter() {

        if (rawX.size() < 10 || rawY.size() < 10) {
            return;
        }

        int sumX = 0;

        for (int i = rawX.size() - 1; i >= Math.max(0, rawX.size() - 5); i--) {
            sumX += rawX.get(i);
        }

        double averageX = (double) sumX / 5;
        x.add(averageX);


        int sumY = 0;

        for (int i = rawY.size() - 1; i >= Math.max(0, rawY.size() - 5) - 5; i--) {
            sumY += rawY.get(i);
        }

        double averageY = (double) sumY / 5;
        y.add(averageY);
    }

    void differentialFilter() {
        int countX = x.size();
        int countY = y.size();

        if (countX < 2 || countY < 2) {
            return;
        }

        double xValue = x.get(countX - 1) - x.get(countX - 2);
        double timexValue = time.get(countX - 1) - time.get(countX - 2);
        double dxValue = xValue / timexValue;
        dx.add(dxValue);

        double yValue = y.get(countY - 1) - y.get(countY - 2);
        double timeyValue = time.get(countY - 1) - time.get(countY - 2);
        double dyValue = yValue / timeyValue;
        dy.add(dyValue);
    }

    void normXY() {
        if (x.size() < 2 || y.size() < 2) {
            return;
        }

        int countX = dx.size();
        int countY = dy.size();

        double xValue = dx.get(countX - 1);
        double yValue = dy.get(countY - 1);

        while (Double.isInfinite(xValue) || Double.isNaN(xValue)) {
            countX--;
            if (countX < 1) {
                return; // データがない場合は処理を終了する
            }
            xValue = dx.get(countX - 1);
        }

        while (Double.isInfinite(yValue) || Double.isNaN(yValue)) {
            countY--;
            if (countY < 1) {
                return; // データがない場合は処理を終了する
            }
            yValue = dy.get(countY - 1);
        }

        double norm = Math.sqrt(xValue * xValue + yValue * yValue);
        norms.add(norm);
    }

    boolean checkSensor() {

        if (norms.size() < 2) {
            return false;
        }

        if (norms.get(norms.size() - 1) > 100) {
            System.out.println("shake");

            return true;
        } else {
            return false;
        }
    }
}
