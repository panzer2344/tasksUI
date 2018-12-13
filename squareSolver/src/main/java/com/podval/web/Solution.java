package com.podval.web;

class Solution implements Cloneable{
    private double x1;
    private double x2;
    private String error;

    Solution(double x1, double x2){
        this.x1 = x1;
        this.x2 = x2;
        error  = new String("");
    }

    Solution(double x1, double x2, String error){
        this.x1 = x1;
        this.x2 = x2;
        this.error = error;
    }

    Solution(Solution solution){

        this.x1 = solution.x1;
        this.x2 = solution.x2;
        this.error = solution.error;
    }

    Solution(String error){
        this.error = error;
    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "x1=" + x1 +
                ", x2=" + x2 +
                '}';
    }

    @Override
    protected Object clone() {
        return new Solution(this.x1, this.x2, this.error);

    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
