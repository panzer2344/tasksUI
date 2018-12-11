package com.podval.web;


import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class solveSquare extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Gson gson = new Gson();

        SquareEquation equation = new SquareEquation(
                Double.parseDouble(req.getParameter("squareCoeff")),
                Double.parseDouble(req.getParameter("linearCoeff")),
                Double.parseDouble(req.getParameter("freeCoeff"))
        );

        Solution solution = new Solution(equation.getSquareCoeff(), equation.getLinearCoeff());

        resp.getWriter().write(gson.toJson(solution));
        resp.getWriter().flush();

    }

}

class SquareEquation{

    private double squareCoeff;
    private double linearCoeff;
    private double freeCoeff;

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

class Solution{
    private double x1;
    private double x2;

    Solution(double x1, double x2){
        this.x1 = x1;
        this.x2 = x2;
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
}
