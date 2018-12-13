package com.podval.web;


import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class solveSquareServlet extends HttpServlet {

    protected boolean isValid(StringBuilder sb, Gson gson){

        Data data = gson.fromJson(sb.toString(), Data.class);

        if("".equals(data.squareCoeff) || "".equals(data.linearCoeff) || "".equals(data.freeCoeff)) {

            return false;

        }else if(!data.squareCoeff.matches("(^[-]?\\d+$)") ||
                !data.linearCoeff.matches("(^[-]?\\d+$)") ||
                !data.freeCoeff.matches("(^[-]?\\d+$)")
        ) {

            return false;

        }

        return true;

    }

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

            if(!isValid(sb, gson)){

                response.getOutputStream().print(gson.toJson(new Solution("Error: Bad data!")));
                response.getOutputStream().flush();
                return;

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

