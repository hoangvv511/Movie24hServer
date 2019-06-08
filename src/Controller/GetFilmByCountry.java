package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Common.Constant;
import Common.Country;
import Model.ConnectDB;

public class GetFilmByCountry extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sortString = "Id";
		String countryString = "";
		int skip = 1;
		int limit = Constant.LIMIT;
		try {
			if (!request.getParameter("sort").isEmpty())
				sortString = request.getParameter("sort");
		} catch (NullPointerException e) {
		} catch (IllegalArgumentException f) {
		}
		try {
			if (!request.getParameter("country").isEmpty())
				countryString = request.getParameter("country");
		} catch (NullPointerException e) {
		} catch (IllegalArgumentException f) {
		}
		try {
			if (!request.getParameter("page").isEmpty())
				skip = Integer.parseInt(request.getParameter("page"));
		} catch (NullPointerException e) {
		} catch (NumberFormatException f) {
		}
		if (!countryString.isEmpty()) {
			countryString = Country.getCountryByCode(countryString);
		}
		List<String> films = ConnectDB.getFilmByCountry(countryString, sortString, (skip - 1) * limit, limit);
		PrintWriter out = response.getWriter();
		out.println(films);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}
