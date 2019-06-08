package Controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.ConnectDB;

public class AddComment extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = 0;
		String name = "";
		String content = "";
		String date = "";
		try {
			if (!request.getParameter("id").isEmpty())
				id = Integer.parseInt(request.getParameter("id"));
		} catch (NullPointerException e) {
		} catch (NumberFormatException f) {
		}
		try {
			if (!request.getParameter("name").isEmpty()) {
				name = request.getParameter("name");
			}
		} catch (NullPointerException e) {
		} catch (IllegalArgumentException f) {
		}
		try {
			if (!request.getParameter("content").isEmpty()) {
				content = request.getParameter("content");
			}
		} catch (NullPointerException e) {
		} catch (IllegalArgumentException f) {
		}
		try {
			if (!request.getParameter("date").isEmpty()) {
				date = request.getParameter("date");
			}
		} catch (NullPointerException e) {
		} catch (IllegalArgumentException f) {
		}
		String addComment = ConnectDB.addComment(id, name, content, date);
		PrintWriter out = response.getWriter();
		out.println(addComment);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}
