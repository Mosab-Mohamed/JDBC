package DBMS;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws SQLException {
		System.out.println("Welcome to JDBC .....\n");
//		System.out.println("Please wait while loading ....\n");
//		try {
//		    Thread.sleep(3000);                 
//		} catch(InterruptedException ex) {
//		    Thread.currentThread().interrupt();
//		}
//		System.out.println("Done, Thanks for your patience :P\n");
		Driver_Implementer d = new Driver_Implementer();
		String url=new String();
		String u,p,db;
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter your Username: ");
		u = scan.nextLine();
		System.out.println("Enter your Password: ");
		p = scan.nextLine();
		Properties info = new Properties() ;
		info.put(1,u);
		info.put(2,p);
		System.out.println("This is a list of Databases you can connect to \nWrite down the name of one of them\n");
		DriverPropertyInfo[] information = d.getPropertyInfo(url, info);
		//System.out.println(information[0].choices);
		for (int i=0; i<d.n.size() ; i++)
		{
			System.out.println(d.n.get(i));
		}
		System.out.println("Enter Here .... \n");
		db = scan.nextLine();
		url = "jdbc:mySubprotocol:"+db;
		Connection con;
		Statement stat = null;
		if (d.acceptsURL(url))
		{
			PrintWriter writer = null;
			try {
				writer = new PrintWriter("currentdatabase.txt", "UTF-8");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			writer.println(db);
			writer.close();
			try{
				con = DriverManager.getConnection(url, info);
				stat = con.createStatement();
				System.out.println("Enter the SQL(s) and finish by Entering 0");
				String in = scan.nextLine();
//				while (!(in.equals("0")))
//				{
//					stat.addBatch(in);
//					in = scan.nextLine();
//				}
				ResultSet s = stat.executeQuery(in);
				s.absolute(0);
				System.out.println(s.first());
				stat.close();
				con.close();
			}
			catch(SQLException e){
				System.out.println(e.getMessage());
			}
		}
	}

}
