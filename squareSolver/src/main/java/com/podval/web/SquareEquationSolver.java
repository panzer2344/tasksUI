
/*
*
* todo: set styles in input text fields. change color, and count of symbols to mb 6 or 7. make error handler in client, that
* todo: handle case, in which server send Nan, Nan. Or change server output, which split to cases: normal, discriminant < 0,
* todo: and divsion by zero in linear case. And also create input validation on server with sending to client.
*
* */


package com.podval.web;

class SquareEquationSolver extends EquationSolver{

    SquareEquationSolver(SquareEquation equation){ super(equation); }

    public Solution solve(){

        double discriminant =  equation.getLinearCoeff() * equation.getLinearCoeff() - 4 * equation.getSquareCoeff() * equation.getFreeCoeff();

        if(discriminant < 0){

            solution = new Solution("Error: Discriminant < 0");

        }else if(discriminant == 0){

            double x = - equation.getLinearCoeff() / ( 2 * equation.getSquareCoeff() );

            solution = new Solution(x, x);

        }else if(discriminant > 0){

            double x1 = (-equation.getLinearCoeff() + Math.sqrt(discriminant)) / (2 * equation.getSquareCoeff());
            double x2 = (-equation.getLinearCoeff() - Math.sqrt(discriminant)) / (2 * equation.getSquareCoeff());

            solution = new Solution(x1, x2);

        }

        return (Solution) solution.clone();

    }

}
