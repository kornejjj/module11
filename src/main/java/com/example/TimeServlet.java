package com.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        // Отримання параметра 'timezone'
        String timezoneParam = req.getParameter("timezone");
        ZoneId zoneId;

        // Перевірка, чи передано часовий пояс
        if (timezoneParam != null && !timezoneParam.isEmpty()) {
            try {
                zoneId = ZoneId.of(timezoneParam);
            } catch (Exception e) {
                // Якщо часовий пояс некоректний, використаємо UTC за замовчуванням
                zoneId = ZoneId.of("UTC");
            }
        } else {
            zoneId = ZoneId.of("UTC"); // За замовчуванням UTC
        }

        // Отримання поточного часу
        Instant now = Instant.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(zoneId);
        String formattedTime = formatter.format(now);

        // Виведення HTML сторінки
        out.println("<html><body>");
        out.println("<h1>Current Time: " + formattedTime + " " + zoneId + "</h1>");
        out.println("</body></html>");
    }
}

