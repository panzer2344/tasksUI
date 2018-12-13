package com.podval.web;

abstract class EquationSolver{

    protected SquareEquation equation;
    protected Solution solution;

    EquationSolver(SquareEquation equation){

        this.equation = equation;

    }

    public abstract Solution solve();

    public SquareEquation getEquation() {
        return equation;
    }

    public Solution getSolution() {
        return solution;
    }
}
