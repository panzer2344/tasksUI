package com.podval.web;

class Data{
    String squareCoeff;
    String linearCoeff;
    String freeCoeff;
    String error;

    Data(String squareCoeff, String linearCoeff, String freeCoeff){
        this.squareCoeff = squareCoeff;
        this.linearCoeff = linearCoeff;
        this.freeCoeff = freeCoeff;
    }

    public String getSquareCoeff() {
        return squareCoeff;
    }

    public void setSquareCoeff(String squareCoeff) {
        this.squareCoeff = squareCoeff;
    }

    public String getLinearCoeff() {
        return linearCoeff;
    }

    public void setLinearCoeff(String linearCoeff) {
        this.linearCoeff = linearCoeff;
    }

    public String getFreeCoeff() {
        return freeCoeff;
    }

    public void setFreeCoeff(String freeCoeff) {
        this.freeCoeff = freeCoeff;
    }
}
