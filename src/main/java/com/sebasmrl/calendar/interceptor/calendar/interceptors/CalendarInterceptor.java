package com.sebasmrl.calendar.interceptor.calendar.interceptors;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("calendarInterceptor")
public class CalendarInterceptor implements HandlerInterceptor{

    @Value("${config.calendar.open}")
    private Integer open;

    @Value("${config.calendar.close}")
    private Integer close;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        if(hour >= open && hour <close){
            StringBuilder sb = new StringBuilder("Bienvenidos a horario de atencion a clientes");
            sb.append(", atendemos desde las ");
            sb.append(open);
            sb.append(" hrs.");
            sb.append(" hasta las ");
            sb.append(close);
            sb.append(" hrs.");
            sb.append("Gracias por su visita.");

            request.setAttribute("message", sb.toString());
            return true;
        }

        ObjectMapper om = new ObjectMapper();
        Map<String, Object> data = new HashMap<String, Object>();
        StringBuilder message = new StringBuilder("Cerrado fuera del Horario de atencion");
        message.append(", por favor visitenos entre las ");
        message.append(open);
        message.append(" y las ");
        message.append(close);
        message.append(" hrs., Gracias. ");
        data.put("message", message.toString());
        data.put("date", new Date());

        String json = om.writeValueAsString(data);
        response.setContentType("application/json");
        response.setStatus(HttpStatus.LOCKED.value());
        response.getWriter().write(json);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
            
    }

}
