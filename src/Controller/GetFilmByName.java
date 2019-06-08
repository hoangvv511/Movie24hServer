package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Common.Constant;
import Model.ConnectDB;

public class GetFilmByName extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = "";
		int skip = 1;
		String sortString = "Id";
		int limit = Constant.LIMIT;
		try {
			if (!request.getParameter("name").isEmpty())
				name = request.getParameter("name");
		} catch (NullPointerException e) {
		} catch (IllegalArgumentException f) {
		}
		try {
			if (!request.getParameter("page").isEmpty())
				skip = Integer.parseInt(request.getParameter("page"));
		} catch (NullPointerException e) {
		} catch (NumberFormatException f) {
		}
		try {
			if (!request.getParameter("sort").isEmpty())
				sortString = request.getParameter("sort");
		} catch (NullPointerException e) {
		} catch (IllegalArgumentException f) {
		}
		List<String> film = ConnectDB.getFilmByName(name, sortString, (skip - 1) * limit, limit);
		PrintWriter out = response.getWriter();
		out.println(film);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}
