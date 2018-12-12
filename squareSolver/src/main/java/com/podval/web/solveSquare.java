package com.podval.web;


import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class solveSquare extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        Gson gson = new Gson();

        try {
            StringBuilder sb = new StringBuilder();
            String s;
            while ((s = request.getReader().readLine()) != null) {
                sb.append(s);
            }

            SquareEquation equation = (SquareEquation) gson.fromJson(sb.toString(), SquareEquation.class);

            EquationSolver solver;

            if(equation.getSquareCoeff() == 0){

                solver = new LinearEquationSolver(equation);

            } else {

                solver = new SquareEquationSolver(equation);

            }


            response.getOutputStream().print(gson.toJson(solver.solve()));
            response.getOutputStream().flush();


        } catch (Exception ex) {
            ex.printStackTrace();
            response.getOutputStream().print(ex.getMessage());
            response.getOutputStream().flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}

