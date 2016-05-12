package ua.nure.khainson.SummaryTask4.web.command;


import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Before;
import org.junit.Test;

public class AddFundsCommandTest {

	//private AddFundsCommand classUnderTest;

	private IDatabaseTester databaseTester;

	private String dbConnectionURI = null;

	@Before
	public void setUp() throws Exception {
		//classUnderTest = new AddFundsCommand();

		// Connect to Derby and create the schema and the tables needed
		setUpDerbyDB();

		// populate the tables using dbunit
		setUpDbUnit();
	}

	/**
	 * This method will connect to Derby and populate the tables with the data
	 * provided in the dataset. It will not create the schema and tables, this
	 * needs to be done before running this method.
	 */
	private void setUpDbUnit() throws Exception {
		// initialize your database connection here
		databaseTester = new JdbcDatabaseTester(
				"org.apache.derby.jdbc.EmbeddedDriver", getDBConnectionURI(),
				"", "", "sumtask4db");

		// load the dataset
		IDataSet dataSet = new FlatXmlDataSetBuilder()
				.build(new FileInputStream(
						"src/test/resources/add_funds_dataset.xml"));
		databaseTester.setDataSet(dataSet);
		databaseTester.onSetup();

		// Inject the connection into the classUnderTest
		//Connection derbyConnection = databaseTester.getConnection()
		//		.getConnection();
		//classUnderTest.setConnection(derbyConnection);
	}

	/**
	 * Runs the DbUnit tearDown and then attempts to delete the Derby DB in the
	 * file system
	 */
	protected void tearDown() throws Exception {
		databaseTester.onTearDown();
	}

	/**
	 * For each test case run we want a unique db name so that they don't stomp
	 * on each other
	 */
	private synchronized String getDBConnectionURI() {
		if (dbConnectionURI == null) {
			dbConnectionURI = "jdbc:derby:memory:";
			int randNum = (int) (Math.random() * 100000);
			dbConnectionURI += "derbyDB_" + randNum + ";create=true";
		}

		return dbConnectionURI;
	}

	/**
	 * Connects to Derby and creates the Schema and Tables needed for this test
	 * case
	 */
	private void setUpDerbyDB() throws SQLException, ClassNotFoundException {
		Connection derbyConnection = null;
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			derbyConnection = DriverManager.getConnection(getDBConnectionURI());
			derbyConnection.createStatement().execute(
					"CREATE SCHEMA " +"sumtask4db");

			StringBuffer userTbl = new StringBuffer();
			userTbl.append("CREATE TABLE ").append("sumtask4db").append(".")
					.append("sumtask4db.accounts").append("(");
			userTbl.append("USERNAME VARCHAR(30) NOT NULL, NAME VARCHAR(100)");
			userTbl.append(", PRIMARY KEY (USERNAME))");
			derbyConnection.createStatement().execute(userTbl.toString());
		} finally {
			if (derbyConnection != null) {
				derbyConnection.close();
			}
		}
	}

	/**
     */
	@Test
	public void test() {
	}

	
}


