package DBMS;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Logger;


public class Driver_Implementer implements Driver {
	DataBase DB = new DataBase();
	Connection Conn = null ;
	//String url ;        // Database name

	static {
		try {
			DriverManager.registerDriver(new Driver_Implementer());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	ArrayList<String> n = new ArrayList<String>();
	
	@Override
	public boolean acceptsURL(String url) throws SQLException {
		String[] db = url.split(":");
		for (int i=0 ; i<n.size() ; i++){
			if (n.get(i).equalsIgnoreCase(db[2])){
				System.out.println("Found DataBase in Folder\n");
				return true;
			}
		}
		System.out.println("DataBases Not Found in Folder\n");
		return false;
	}

	@SuppressWarnings("resource")
	@Override
	public Connection connect(String url, Properties info) throws SQLException {
		String[] db = url.split(":");
		Scanner scan = null;
		String Username = info.get(1).toString();     String Password = info.get(2).toString();		
		try {
			scan = new Scanner(new File("login.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (scan.hasNextLine())
		{
			if (Username.equals(scan.next())){
				System.out.println("Right Username ");
				if (Password.equals(scan.next())){
					System.out.println("Right Password ");
					Conn = new Connection_Implementer(db[2]);
					System.out.println("Connection Done Successfully");
					return Conn;
				}
				else{
					//System.out.println("Wrong Password ");
					continue;
				}
					
			}
			else{
				//System.out.println("Wrong Username ");
				scan.nextLine();
				continue;
			}
		}
		return null;
	}
	
	@Override
	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
		Scanner scan = null ;
		try {
			scan = new Scanner(new File("DataBaseNames.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int j=0;
		while (scan.hasNext()){
			n.add(scan.nextLine());
			j++;
		}
		DriverPropertyInfo[] Information = new DriverPropertyInfo[1];
		String[] Names_Arr = new String[n.size()];
		Names_Arr = n.toArray(Names_Arr);
		//Information[0].choices = Names_Arr;
		return Information;
	}

	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public int getMajorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMinorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean jdbcCompliant() {
		// TODO Auto-generated method stub
		return false;
	}

}
