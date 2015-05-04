package DBMS;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedList;

import DBMS.Column;

public class ResultSetMetaData_Implementer implements java.sql.ResultSetMetaData {
	private ArrayList<Column> columns ;
	private String table_name;
	public ResultSetMetaData_Implementer(String x ,ArrayList<Column> y ){
		columns.addAll(y);
		table_name = x;
	}

	@Override
	public int getColumnCount() throws SQLException {
		return columns.size();
	}
	@Override
	public String getColumnLabel(int column) throws SQLException {
		return getColumnName(column);
	}
	@Override
	public String getColumnName(int column) throws SQLException {
		return columns.get(column).name;
	}
	@Override
	public String getTableName(int column) throws SQLException {
		return table_name;
	}
	@Override
	public int getColumnType(int column) throws SQLException {
		if(columns.get(column).DataType.equals("varchar"))
			return Types.VARCHAR;
		return Types.INTEGER;
	}
	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}
	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}
	@Override
	public boolean isAutoIncrement(int column) throws SQLException {
		return false;
	}
	@Override
	public boolean isCaseSensitive(int column) throws SQLException {
		return false;
	}
	@Override
	public boolean isSearchable(int column) throws SQLException {
		return false;
	}
	@Override
	public boolean isCurrency(int column) throws SQLException {
		return false;
	}
	@Override
	public int isNullable(int column) throws SQLException {
		return 0;
	}
	@Override
	public boolean isSigned(int column) throws SQLException {
		return false;
	}
	@Override
	public int getColumnDisplaySize(int column) throws SQLException {
		return 0;
	}
	@Override
	public String getSchemaName(int column) throws SQLException {
		return null;
	}
	@Override
	public int getPrecision(int column) throws SQLException {
		return 0;
	}
	@Override
	public int getScale(int column) throws SQLException {
		return 0;
	}
	@Override
	public String getCatalogName(int column) throws SQLException {
		return null;
	}
	@Override
	public String getColumnTypeName(int column) throws SQLException {
		return null;
	}
	@Override
	public boolean isReadOnly(int column) throws SQLException {
		return false;
	}
	@Override
	public boolean isWritable(int column) throws SQLException {
		return false;
	}
	@Override
	public boolean isDefinitelyWritable(int column) throws SQLException {
		return false;
	}
	@Override
	public String getColumnClassName(int column) throws SQLException {
		return null;
	}


}
