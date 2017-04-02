package com.in28minutes.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.in28minutes.todo.TodoService;

/*

 // Method descriptor #15 ()V
 public void init() throws javax.servlet.ServletException;

 // Method descriptor #37 (Ljavax/servlet/ServletRequest;Ljavax/servlet/ServletResponse;)V
 public void service(javax.servlet.ServletRequest arg0, javax.servlet.ServletResponse arg1) throws javax.servlet.ServletException, java.io.IOException;

 // Method descriptor #15 ()V
 public void destroy();

 */
@WebServlet(urlPatterns = "/login.do")
public class LoginServlet extends HttpServlet {

	private LoginService userValidationService = new LoginService();
	private TodoService todoService = new TodoService();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(
				request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String password = request.getParameter("password");

		boolean isUserValid = userValidationService.isUserValid(name, password);

		if (isUserValid) {
			request.getSession().setAttribute("name", name);
			response.sendRedirect("/list-todos.do");
		} else {
			request.setAttribute("errorMessage", "Invalid Credentials!");
			request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(
					request, response);
		}
	}

}