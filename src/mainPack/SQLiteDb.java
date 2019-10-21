/*Diese Klasse definiert die verbindunf zur SQLite Datenbank
 */


package mainPack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLiteDb {
	
	Connection c = null;
	Statement stmt = null;
	
	SQLiteDb(){
		try {
			Class.forName("org.sqlite.JDBC");
			
			c = DriverManager.getConnection("jdbc:sqlite:./resources/ChatBotData.sqlite3");
			System.out.println("Connection to DB");
			
			
		}catch (Exception e) {
			System.out.println("Error " + e);
		}
	}
	
	public ResultSet getSyns() {
		try {
			this.stmt = c.createStatement();
			ResultSet re = stmt.executeQuery("SELECT * FROM synWords");
			
			return re;
			
		}catch (Exception e) {
			System.out.println("Error2 " + e.getMessage());
			return null;
		}
		
	}
	
	public ResultSet getkonjs() {
		try {
			this.stmt = c.createStatement();
			ResultSet re = stmt.executeQuery("SELECT * FROM konjWords");
			
			return re;
			
		}catch (Exception e) {
			System.out.println("Error2 " + e.getMessage());
			return null;
		}
		
	}
	
	public ResultSet getŔeactions() {
		try {
			this.stmt = c.createStatement();
			ResultSet re = stmt.executeQuery("SELECT * FROM reactionTable");
			
			return re;
			
		}catch (Exception e) {
			System.out.println("Error2 " + e.getMessage());
			return null;
		}
		
	}
	
	public void closeConn() {
		try {
			c.close();
		}catch (Exception e) {
			System.out.println("Error in closing: " + e.getMessage());
		}
	}

}
