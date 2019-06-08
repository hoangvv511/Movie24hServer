package Filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class AddHeaderFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletResponse res = (HttpServletResponse) response;
		res.addHeader("Access-Control-Allow-Headers", "access-control-allow-origin,content-type");
		res.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
		res.addHeader("Access-Control-Allow-Origin", "*");
		res.addHeader("Allow", "POST, GET, OPTIONS, PUT, DELETE");
		chain.doFilter(request, response);
	}

}
