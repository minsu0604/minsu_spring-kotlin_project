package bitc.fullstack405.fun_spring.intercepter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginCheck implements HandlerInterceptor {

    // 로그인 체크
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception{
        HttpSession session = req.getSession();

        String userId = (String)session.getAttribute("userId");

        if (userId == null || userId.equals("")) {
            res.sendRedirect("login");

            return false;
        }
        else {
            session.setMaxInactiveInterval(60 * 60 * 1);
        }
        return true;
    }
}
