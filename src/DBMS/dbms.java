package DBMS;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public interface dbms {
	public static final String TABLE_NOT_FOUND = "This Table doesn't exists in database";
	public static final String COLUMN_NOT_FOUND = "This column doesn't exists in this table";
	public static final String TABLE_ALREADY_EXISTS = "This Table already exists";
	public static final String PARSING_ERROR = "bad formated input";
	public static final String DB_NOT_FOUND = "No database exists";
	public static final String COLUMN_TYPE_MISMATCH = "Entered value doesn't match column type";
	public static final String Con_DB = "DB created";
	public static final String Con_Table = "Table created";
	public static final String Con_insert = "insertion Complete";
	public static final String Con_Delete = "Row/s deleted";
	public static final String Con_Update = "Row/s Updated";
	public static final String NOT_MATCH_CRITERIA = "no row exists with this criteria";

	/**
	 * This function will take the input String like select * from table_name
	 * and return the results in String
	 * @throws Throwable 
	 * */
	public String input(String input) throws Throwable;
	public String createDatabase(String databaseName) throws Throwable;
	public String insert(ArrayList<Column> arr, String table_name) throws Throwable;
	public String delete(String inequality, String table_name) throws Throwable;
	public String select(ArrayList<Column> arr, String inequality,String table_name) throws Throwable;
	public String createTable(ArrayList<Column> arr, String table_name) throws Throwable;
	public String update(ArrayList<Column> arr,String inequality,String table_name) throws Throwable;

}
