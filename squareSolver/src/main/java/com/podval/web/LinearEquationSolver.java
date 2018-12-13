package com.podval.web;

class LinearEquationSolver extends EquationSolver{


    LinearEquationSolver(SquareEquation equation){ super(equation); }

    public Solution solve(){

        double x = - equation.getFreeCoeff() / equation.getLinearCoeff();

        if(equation.getLinearCoeff() != 0) {
            solution = new Solution(x, x);
        } else {
            solution = new Solution("Error: Bad data - const = zero");
        }

        return (Solution) solution.clone();

    }


}
