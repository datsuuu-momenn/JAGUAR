package com.example.servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

import org.primefaces.shaded.json.JSONObject;

@WebServlet("/storePreferences")
public class CoffeePreferencesServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 获取用户选择的咖啡类型
        String[] selectedCoffees = request.getParameterValues("checkboxGroup");
        
        // 获取或创建 Session
        HttpSession session = request.getSession(true);
        
        // 将选择存储在 Session 中
        session.setAttribute("userCoffeePreferences", selectedCoffees);
        
        // 设置 Session 超时时间（可选，这里设置为30分钟）
        session.setMaxInactiveInterval(30 * 60);
        
        // 检查是否是AJAX请求
        String xRequestedWith = request.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equals(xRequestedWith)) {
            // 如果是AJAX请求，返回JSON响应
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("status", "success");
            jsonResponse.put("message", "咖啡偏好已保存");
            jsonResponse.put("redirect", "coffeeDashboard");
            
            out.print(jsonResponse.toString());
            out.flush();
        } else {
            // 如果不是AJAX请求，直接重定向
            response.sendRedirect("coffeeDashboard");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        JSONObject jsonResponse = new JSONObject();
        
        if (session != null && session.getAttribute("userCoffeePreferences") != null) {
            String[] preferences = (String[]) session.getAttribute("userCoffeePreferences");
            jsonResponse.put("preferences", preferences);
            jsonResponse.put("status", "success"); 
        } else {
            jsonResponse.put("status", "no_preferences");
        }
        
        out.print(jsonResponse.toString());
        out.flush();
    }
}