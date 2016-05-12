package ua.nure.khainson.SummaryTask4.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class NoCacheFilter implements Filter {
	private static final Logger LOG = Logger.getLogger(NoCacheFilter.class);

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		LOG.debug("Filter starts");
		HttpServletResponse response = (HttpServletResponse) res;
		// forbidden cache of pages(only through request to the server)
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0);
		response.setHeader("Pragma", "no-cache");

		chain.doFilter(req, res);

		LOG.debug("Filter starts");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

}
