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
		
		response.sendRedirect("index.jsp");
	} else {
		response.sendRedirect("login.go");
		
	}
}

}
