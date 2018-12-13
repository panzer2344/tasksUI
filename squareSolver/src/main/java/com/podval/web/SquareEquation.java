package com.podval.web;

class SquareEquation{

    private double squareCoeff;
    private double linearCoeff;
    private double freeCoeff;
    private String error;

    public SquareEquation(double squareCoeff, double linearCoeff, double freeCoeff) {
        this.squareCoeff = squareCoeff;
        this.linearCoeff = linearCoeff;
        this.freeCoeff = freeCoeff;
    }

    public double getSquareCoeff() {
        return squareCoeff;
    }

    public void setSquareCoeff(double squareCoeff) {
        this.squareCoeff = squareCoeff;
    }

    public double getLinearCoeff() {
        return linearCoeff;
    }

    public void setLinearCoeff(double linearCoeff) {
        this.linearCoeff = linearCoeff;
    }

    public double getFreeCoeff() {
        return freeCoeff;
    }

    public void setFreeCoeff(double freeCoeff) {
        this.freeCoeff = freeCoeff;
    }

    @Override
    public String toString() {
        return "SquareEquation{" +
                "squareCoeff=" + squareCoeff +
                ", linearCoeff=" + linearCoeff +
                ", freeCoeff=" + freeCoeff +
                '}';
    }
}
