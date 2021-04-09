package edu.web.login.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login.go")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	request.getRequestDispatcher("/WEB-INF/login/login.jsp").forward(request, response);
	
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String userid = request.getParameter("userid");
	String password = request.getParameter("password");
	if(userid.equals("test") && password.equals("1234")) {
		HttpSession session = request.getSession();
		session.setAttribute("userid", userid);
		session.setMaxInactiveInterval(600);
		
		//설정된 url 가져오기
		String url = (String) session.getAttribute("targetURL");
		System.out.println("targetURL : " + url);
		
		if(url != null && !url.equals("")) {
			// 글 작성 버튼을 누르고 로그인을 한 경우
			response.sendRedirect(url);
			session.removeAttribute("targetURL");
		} else { //targetURL이 존재하지 않는 경우
			// 로그인만 수행한 경우
			response.sendRedirect("index.jsp");
		}
		
	} else {
		response.sendRedirect("login.go");
		
	}
}

}
