package DBMS;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;


public class arr implements java.sql.Array {

	String str;
	arr(String s){
		str=s;
	}
	public String returnString(){
		return str;
	}
	@Override
	public void free() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getArray() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getArray(Map<String, Class<?>> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getArray(long arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getArray(long arg0, int arg1, Map<String, Class<?>> arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getBaseType() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getBaseTypeName() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultSet getResultSet() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultSet getResultSet(Map<String, Class<?>> arg0)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultSet getResultSet(long arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultSet getResultSet(long arg0, int arg1,
			Map<String, Class<?>> arg2) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
