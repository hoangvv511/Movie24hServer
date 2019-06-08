package Controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.ConnectDB;

public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = "";
		String password = "";
		String name = "";
		try {
			if (!request.getParameter("username").isEmpty())
				username = request.getParameter("username");
		} catch (NullPointerException e) {
		} catch (IllegalArgumentException f) {
		}
		try {
			if (!request.getParameter("password").isEmpty())
				password = request.getParameter("password");
		} catch (NullPointerException e) {
		} catch (IllegalArgumentException f) {
		}
		try {
			if (!request.getParameter("name").isEmpty())
				name = request.getParameter("name");
		} catch (NullPointerException e) {
		} catch (IllegalArgumentException f) {
		}
		String user = ConnectDB.registerUser(username, password, name);
		PrintWriter out = response.getWriter();
		out.println(user);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}
