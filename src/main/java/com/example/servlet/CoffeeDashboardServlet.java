package com.example.servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.io.IOException;
import java.util.*;

import com.example.entity.CoffeeRecommendation;

@WebServlet("/coffeeDashboard")
public class CoffeeDashboardServlet extends HttpServlet {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        List<CoffeeRecommendation> recommendations = new ArrayList<>();
        
        if (session != null && session.getAttribute("userCoffeePreferences") != null) {
            String[] preferences = (String[]) session.getAttribute("userCoffeePreferences");
            
            for (String preference : preferences) {
                TypedQuery<CoffeeRecommendation> query = entityManager.createQuery(
                    "SELECT c FROM CoffeeRecommendation c WHERE c.coffeeType = :type", 
                    CoffeeRecommendation.class);
                query.setParameter("type", preference);
                
                try {
                    CoffeeRecommendation recommendation = query.getSingleResult();
                    recommendations.add(recommendation);
                } catch (Exception e) {
                    System.err.println("No recommendation found for: " + preference);
                }
            }
        }
        
        request.setAttribute("recommendations", recommendations);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/coffeeDashboard.jsp");
        dispatcher.forward(request, response);
    }
}