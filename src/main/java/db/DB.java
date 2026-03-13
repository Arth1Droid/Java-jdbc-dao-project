package db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {
	
	private static Connection connection = null;
	
	public static Connection getConnection() {
		if(connection == null) {
			try {
				Properties props = loadProperties();
				String url = props.getProperty("dburl");
				connection = DriverManager.getConnection(url,props);
		    }  
			catch(SQLException ex) {
				throw new DbException(ex.getMessage());
			}
		}
		return connection;
	}
	
	public static void closeConnection() {
		if(connection != null) {
			try {
				connection.close();
			}
			catch(SQLException ex) {
				throw new DbException(ex.getMessage());
			}
		}
	}

	private static Properties loadProperties() {
		try(InputStream is = DB.class.getClassLoader().getResourceAsStream("db.properties")){
			Properties props = new Properties();
			props.load(is);
			return props;
		}
		catch(IOException ex) {
			throw new DbException(ex.getMessage());
		}
	}
	
	public static void closeStatement(Statement st) {
		if(st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	public static void closeResultSet(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
}
