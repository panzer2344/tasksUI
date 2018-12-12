package com.podval.web;

class LinearEquationSolver extends EquationSolver{


    LinearEquationSolver(SquareEquation equation){ super(equation); }

    public Solution solve(){

        double x = - equation.getFreeCoeff() / equation.getLinearCoeff();

        solution = new Solution( x, x);

        return (Solution) solution.clone();

    }


}
