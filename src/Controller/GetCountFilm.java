package Controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Common.Country;
import Common.Type;
import Model.ConnectDB;

public class GetCountFilm extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String country = "";
		String type = "";
		String name = "";
		int count = ConnectDB.getCountAllFilm();
		try {
			if (!request.getParameter("type").isEmpty()) {
				type = request.getParameter("type");
				type = Type.getTypeByCode(type);
				count = ConnectDB.getCountFilmByType(type);
			}
		} catch (NullPointerException e) {
		} catch (IllegalArgumentException f) {
		}
		try {
			if (!request.getParameter("country").isEmpty()) {
				country = request.getParameter("country");
				country = Country.getCountryByCode(country);
				count = ConnectDB.getCountFilmByCountry(country);
			}
		} catch (NullPointerException e) {
		} catch (IllegalArgumentException f) {
		}
		try {
			if (!request.getParameter("name").isEmpty()) {
				name = request.getParameter("name");
				count = ConnectDB.getCountFilmByName(name);
			}
		} catch (NullPointerException e) {
		} catch (IllegalArgumentException f) {
		}
		PrintWriter out = response.getWriter();
		out.println(count);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}
