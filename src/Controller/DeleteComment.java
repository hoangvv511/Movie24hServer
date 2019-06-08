package Controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.ConnectDB;

public class DeleteComment extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = 0;
		int idComment = 0;
		try {
			if (!request.getParameter("id").isEmpty())
				id = Integer.parseInt(request.getParameter("id"));
		} catch (NullPointerException e) {
		} catch (NumberFormatException f) {
		}
		try {
			if (!request.getParameter("idComment").isEmpty())
				idComment = Integer.parseInt(request.getParameter("idComment"));
		} catch (NullPointerException e) {
		} catch (NumberFormatException f) {
		}
		String deleteComment = ConnectDB.deleteComment(id, idComment);
		PrintWriter out = response.getWriter();
		out.println(deleteComment);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}
