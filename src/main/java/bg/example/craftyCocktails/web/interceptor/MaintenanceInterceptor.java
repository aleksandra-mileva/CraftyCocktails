package bg.example.craftyCocktails.web.interceptor;

import java.time.LocalTime;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

@Configuration
public class MaintenanceInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request,
      HttpServletResponse response,
      Object handler) throws Exception {

    var requestURI = request.getRequestURI();
    if (!requestURI.equals("/maintenance")) {
      LocalTime now = LocalTime.now();
      if (now.getHour() >= 24) {
        response.sendRedirect("/maintenance");
        return false;
      }
    }
    return HandlerInterceptor.super.preHandle(request, response, handler);
  }
}
