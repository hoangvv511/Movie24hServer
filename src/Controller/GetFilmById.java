package Controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.ConnectDB;

public class GetFilmById extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int Id = 0;
		try {
			if (!request.getParameter("id").isEmpty())
				Id = Integer.parseInt(request.getParameter("id"));
		} catch (NullPointerException e) {
		} catch (NumberFormatException f) {
		}
		String film = ConnectDB.getFilmById(Id);
		PrintWriter out = response.getWriter();
		out.println(film);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}
