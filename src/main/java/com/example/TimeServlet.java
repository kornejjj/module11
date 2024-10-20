package com.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {

    private TemplateEngine templateEngine;

    @Override
    public void init() throws ServletException {
        super.init();
        // Ініціалізація Thymeleaf Template Engine
        templateEngine = new TemplateEngine();
        // Додайте необхідні конфігурації тут (якщо потрібно)
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");

        // Отримання параметра timezone
        String timezoneParam = req.getParameter("timezone");
        ZoneId zoneId;

        // Визначення часового поясу
        if (timezoneParam != null && !timezoneParam.isEmpty()) {
            try {
                zoneId = ZoneId.of(timezoneParam);
            } catch (Exception e) {
                zoneId = ZoneId.of("UTC");
            }
        } else {
            zoneId = ZoneId.of("UTC");
        }

        // Отримання поточного часу
        Instant now = Instant.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(zoneId);
        String formattedTime = formatter.format(now);

        // Створення контексту для Thymeleaf
        Context context = new Context(Locale.getDefault());
        context.setVariable("currentTime", formattedTime);
        context.setVariable("timezone", zoneId.getId());

        // Обробка шаблону та виведення результату
        templateEngine.process("timeTemplate", context, resp.getWriter());
    }
}
