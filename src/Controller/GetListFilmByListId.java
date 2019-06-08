package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.ConnectDB;

public class GetListFilmByListId extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Integer> listId = new ArrayList<Integer>();
		String[] listIdString = null;
		try {
			if (!request.getParameter("listId").isEmpty())
				listIdString = request.getParameterValues("listId");
		} catch (NullPointerException e) {
		} catch (IllegalArgumentException f) {
		}
		for (String IdString : listIdString) {
			listId.add(Integer.parseInt(IdString));
		}
		String film = ConnectDB.getListFilmByListId(listId);
		PrintWriter out = response.getWriter();
		out.println(film);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}
