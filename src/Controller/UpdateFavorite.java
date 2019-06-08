package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.ConnectDB;

public class UpdateFavorite extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = "";
		int insert = -1;
		int delete = -1;
		try {
			if (!request.getParameter("username").isEmpty())
				username = request.getParameter("username");
		} catch (NullPointerException e) {
		} catch (IllegalArgumentException f) {
		}
		try {
			if (!request.getParameter("insert").isEmpty())
				insert = Integer.parseInt(request.getParameter("insert"));
		} catch (NullPointerException e) {
		} catch (NumberFormatException f) {
		}
		try {
			if (!request.getParameter("delete").isEmpty())
				delete = Integer.parseInt(request.getParameter("delete"));
		} catch (NullPointerException e) {
		} catch (NumberFormatException f) {
		}
		List<Integer> user = ConnectDB.updateFavorite(username, insert, delete);
		PrintWriter out = response.getWriter();
		out.println(user);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}
