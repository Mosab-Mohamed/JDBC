package DBMS;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.junit.Test;

public class JDBC_Test {
	

	
	@Test
	public void TestAll() throws SQLException {
		
		Driver_Implementer D = new Driver_Implementer();
		
		Properties info = new Properties();
		
		D.getPropertyInfo("jdbc:mySubprotocol:D1", info);
		
		assertEquals(true,D.acceptsURL("jdbc:mySubprotocol:D3"));
		
		info.put(1,"a"); 		info.put(2,"1");
		
		D.connect("jdbc:mySubprotocol:D1", info);
		
		Connection_Implementer C = new Connection_Implementer("D3");
		
		Statement stat = C.createStatement();
		
		assertEquals(1,stat.executeUpdate("INSERT INTO Items VALUES (1,'Orange',12);"));
		
		assertEquals(1,stat.executeUpdate("INSERT INTO Items VALUES (2,'Moz',13);"));
		
		assertEquals(1,stat.executeUpdate("INSERT INTO Items VALUES (3,'tofa7',14);"));
		
		stat.addBatch("INSERT INTO Items VALUES (4,'sfndy',15);");
		
		stat.addBatch("DELETE FROM Items WHERE Name='sfndy';");
		
		assertEquals(1,stat.executeBatch()[0]);
		
		assertEquals(1,stat.executeBatch()[1]);
		
		ResultSet s = stat.executeQuery("select * from Items where ItemID=1");
		
		assertEquals(true,s.first());
		
		assertEquals(true,s.absolute(0));
		
		assertEquals(1.0,s.getFloat(0),0);
		
		assertEquals(true,s.isFirst());
		
		assertEquals("1",s.getString(0));
		
	}
	
	
	

}
