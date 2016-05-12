package ua.nure.khainson.SummaryTask4.web.listener;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Context listener.
 * 
 * @author P.Khainson
 * 
 */
public class ContextListener implements ServletContextListener {

	private static final Logger LOG = Logger.getLogger(ContextListener.class);

	public void contextDestroyed(ServletContextEvent event) {
		log("Servlet context destruction starts");
		// no op
		log("Servlet context destruction finished");
	}

	public void contextInitialized(ServletContextEvent event) {
		log("Servlet context initialization starts");

		initLog4J(event.getServletContext());
		initCommandContainer();
		initLocales(event);

		log("Servlet context initialization finished");
	}

	/**
	 * Initializes log4j framework.
	 * 
	 * @param servletContext
	 */
	private void initLog4J(ServletContext servletContext) {
		log("Log4J initialization started");
		try {
			PropertyConfigurator.configure(servletContext
					.getRealPath("WEB-INF/log4j.properties"));
			LOG.debug("Log4j has been initialized");
		} catch (Exception ex) {
			log("Cannot configure Log4j");
			LOG.error("errorMessage --> " + ex.getMessage());
		}
		log("Log4J initialization finished");
	}

	/**
	 * Initializes CommandContainer.
	 * 
	 * @param servletContext
	 */
	private void initCommandContainer() {

		// initialize commands container
		// just load class to JVM
		try {
			Class.forName("ua.nure.khainson.SummaryTask4.web.command.CommandContainer");
		} catch (ClassNotFoundException ex) {
			throw new IllegalStateException(
					"Cannot initialize Command Container");
		}
	}

	private void log(String msg) {
		System.out.println("[ContextListener] " + msg);
	}

	private void initLocales(ServletContextEvent event) {
		log("Locales initialization started");
		String localesFileName = event.getServletContext().getInitParameter(
				"locales");

		// obtain reale path on server
		String localesFileRealPath = event.getServletContext().getRealPath(
				localesFileName);

		// locad descriptions
		Properties locales = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(localesFileRealPath);
			locales.load(fis);
		} catch (IOException e) {
			LOG.error("errorMessage --> " + e.getMessage());
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					LOG.error("errorMessage --> " + e.getMessage());
				}
			}
		}

		// save descriptions to servlet context
		event.getServletContext().setAttribute("locales", locales);
		locales.list(System.out);
		log("Locales initialization fiished");
	}
}