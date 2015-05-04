package DBMS;

import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;


public class Resultset_Implementer implements ResultSet {
	
	ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
	ArrayList<Column> columnNames=new ArrayList<Column>();
	int currentrow=-1;
	String WD="Wrong DataType..";
	boolean closed=false;
	String table_nm;

	Resultset_Implementer(ArrayList<ArrayList<String>> Table,ArrayList<Column> Names,String table_name){		
		 table=Table;
		 columnNames=Names;
		 table_nm = table_name;
	}
	
	public boolean absolute(int row){	
		if(row<0){
			currentrow=table.size()+row;
		}
		else {
			currentrow=row;
		}
		if(currentrow<0 || currentrow>table.size()){
			return false;
		}
		return true;
	}
	
	public void afterLast(){
		currentrow=table.size();
	}
	
	public void beforeFirst(){
		currentrow=-1;
	}
	
	public void close(){
		currentrow=-1;
		table.clear();
		columnNames.clear();
		closed=true;
	}
	
	public int findColumn(String columnLabel){
		int i=0;
		try{
		while(i!=-1){
			if(columnNames.get(i).name.equals(columnLabel)){
				return i;
			}
			i++;		
		}
		}
		catch(Exception e){
			System.out.print("Column Not Found ");
		}
			return i-1;	
	}
	
	public boolean first(){
		currentrow=0;
		if(currentrow>=table.size()){
			return false;
		}
		else{
			return true;
		}
	}
	
	public java.sql.Array getArray(int columnIndex){
		java.sql.Array arr=null;
		return arr;
	}
	
	public boolean getBoolean(int columnIndex){	
		ArrayList<String> row = table.get(currentrow);
		 if(row.get(columnIndex).contains("0")){
			return false;
		 }
		 else if(row.get(columnIndex).contains("1")){
				return true;
		 }
		 else{
			 return false;
		 }
	}
	
	public boolean getBoolean(String columnLabel){
		ArrayList<String> row = table.get(currentrow);
		int col = 0;
		for(int i=0;i<columnNames.size();i++){
			if(columnNames.get(i).name.equals(columnLabel)){
				col=i;
			}
		}
		System.out.print(row.get(col));

		if(row.get(col).contains("0")){
			return false;
		 }
		 else if(row.get(col).contains("1")){
				return true;
		 }
		 else{
			 return false;
		 }
	}
	
	public Date getDate(int columnIndex){
		ArrayList<String> row = table.get(currentrow);
		java.util.Date utilDate = null;
		try {
			utilDate = new SimpleDateFormat("dd MMM yyyy").parse(row.get(columnIndex));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		return  sqlDate;	
	}
	
	public Date getDate(String columnLabel){
		ArrayList<String> row = table.get(currentrow);
		int column = 0;
		for(int i=0;i<columnNames.size();i++){
			if(columnNames.get(i).name.equals(columnLabel)){
				column=i;
			}
		}
		java.util.Date utilDate = null;
		try {
			utilDate = new SimpleDateFormat("dd MMM yyyy").parse(row.get(column));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		return  sqlDate;	
	}
	
	public double getDouble(int columnIndex)throws SQLException{
		ArrayList<String> row = table.get(currentrow);
		try{
		double x=Double.valueOf(row.get(columnIndex));
		return x;	
		}
		catch (Exception e){
			throw new SQLException();
		}
	}
	
	public double getDouble(String columnLabel)throws SQLException{
		ArrayList<String> row = table.get(currentrow);
		try{
		int column = 0;
		for(int i=0;i<columnNames.size();i++){
			if(columnNames.get(i).name.equals(columnLabel)){
				column=i;
			}
		}
		double x=Double.valueOf(row.get(column));
		return x;	
		}
		catch (Exception e){
			throw new SQLException();
		}
	}
	
	public int getFetchDirection(){
		return  ResultSet.FETCH_UNKNOWN;
	}
	
	public float getFloat(int columnIndex)throws SQLException{
		ArrayList<String> row = table.get(currentrow);
		try{
		float x=Float.valueOf(row.get(columnIndex));	
		return x;
		}catch (Exception e){
			throw new SQLException();
		}
	}
	
	public float getFloat(String columnLabel) throws SQLException{
		ArrayList<String> row = table.get(currentrow);
		try{
		int column = 0;
		for(int i=0;i<columnNames.size();i++){
			if(columnNames.get(i).name.equals(columnLabel)){
				column=i;
			}
		}
		float x=Float.valueOf(row.get(column));
		return x;	
		}
		catch (Exception e){
			throw new SQLException();
		}
	}
	
	public int getInt(int columnIndex) throws SQLException{
		ArrayList<String> row = table.get(currentrow);
		try{
		int x=Integer.valueOf(row.get(columnIndex));	
		return x;
		}
		catch (Exception e){
			throw new SQLException();
		}
	}
		
	
	public int getInt(String columnLabel) throws SQLException{
		ArrayList<String> row = table.get(currentrow);
		try{
		int column = 0;
		for(int i=0;i<columnNames.size();i++){
			if(columnNames.get(i).name.equals(columnLabel)){
				column=i;
			}
		}
		int x=Integer.valueOf(row.get(column));
		return x;
		}
		catch (Exception e){
			throw new SQLException();
		}
	}
	
	public long getLong(int columnIndex) throws SQLException{
		ArrayList<String> row = table.get(currentrow);
		try{
		long x=Long.valueOf(row.get(columnIndex));	
		return x;	
		}
		catch (Exception e){
			throw new SQLException();
		}
	}
	
	public long getLong(String columnLabel) throws SQLException{
		ArrayList<String> row = table.get(currentrow);
		try{
		int column = 0;
		for(int i=0;i<columnNames.size();i++){
			if(columnNames.get(i).name.equals(columnLabel)){
				column=i;
			}
		}
		long x=Long.valueOf(row.get(column));
		return x;	
		}
		catch (Exception e){
			throw new SQLException();
		}
	}
	
	public ResultSetMetaData getMetaData() throws SQLException{
		ResultSetMetaData rsm = new ResultSetMetaData_Implementer(table_nm,columnNames);
		return rsm;
	}
	
	public Object getObject(int columnIndex){
		ArrayList<String> row = table.get(currentrow);
		Object x=row.get(columnIndex);	
		return x;
	}
	
//	Statement getStatement(){
//		Statement statement = new Statement()
//		return statement;
//	}
	
	public String getString(int columnIndex) throws SQLException{
		ArrayList<String> row = table.get(currentrow);
		try{
		String x=row.get(columnIndex);	
		return x;
		}
		catch (Exception e){
			throw new SQLException();
		}
	}
	
	public String getString(String columnLabel) throws SQLException{
		ArrayList<String> row = table.get(currentrow);
		boolean happenend = false ;
		try{
			int column = 0;
			for(int i=0;i<columnNames.size();i++){
				if(columnNames.get(i).name.equals(columnLabel)){
					column=i;
					happenend = true ;
				}
			}
			if(happenend){
				String x=row.get(column);	
				return x;
			}
			else
				return WD;
		}
		
		catch (Exception e){
			throw new SQLException();
		}
	}
	
	public boolean isAfterLast(){
		if(table.size()==0){
			return false;
		}
		else if(currentrow==table.size()){
			return true;
		}
		else{
			return false;
		}	
	}
	
	public boolean isBeforeFirst(){
		if(table.size()==0){
			return false;
		}
		else if(currentrow==-1){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean isClosed(){
		if(closed=true){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean isFirst(){
		if(currentrow==0 && table.size()>0){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean isLast(){
		if(currentrow==table.size()-1 && table.size()>0){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean last(){
		currentrow=table.size()-1;
		if(currentrow<0){
			return false;
		}
		else{
			return true;
		}
	}
	
	public boolean next(){
		currentrow++;
		if(currentrow>=table.size()){
			return false;
		}
		else{
			return true;
		}
	}
	
	public boolean previous(){
		currentrow--;
		if(currentrow<0){
			return false;
		}
		else{
			return true;
		}
	}

	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void cancelRowUpdates() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearWarnings() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteRow() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public java.sql.Array getArray(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getAsciiStream(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getAsciiStream(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getBigDecimal(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getBigDecimal(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getBigDecimal(int arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getBigDecimal(String arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getBinaryStream(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getBinaryStream(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Blob getBlob(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Blob getBlob(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte getByte(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte getByte(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte[] getBytes(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getBytes(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader getCharacterStream(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader getCharacterStream(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Clob getClob(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Clob getClob(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getConcurrency() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getCursorName() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getDate(int arg0, Calendar arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getDate(String arg0, Calendar arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getFetchSize() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHoldability() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Reader getNCharacterStream(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader getNCharacterStream(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NClob getNClob(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NClob getNClob(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNString(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNString(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObject(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObject(int arg0, Map<String, Class<?>> arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObject(String arg0, Map<String, Class<?>> arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getObject(int arg0, Class<T> arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getObject(String arg0, Class<T> arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ref getRef(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ref getRef(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRow() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public RowId getRowId(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RowId getRowId(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLXML getSQLXML(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLXML getSQLXML(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public short getShort(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public short getShort(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Statement getStatement() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time getTime(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time getTime(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time getTime(int arg0, Calendar arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time getTime(String arg0, Calendar arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp getTimestamp(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp getTimestamp(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp getTimestamp(int arg0, Calendar arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp getTimestamp(String arg0, Calendar arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getType() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public URL getURL(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URL getURL(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getUnicodeStream(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getUnicodeStream(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertRow() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveToCurrentRow() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveToInsertRow() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshRow() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean relative(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rowDeleted() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rowInserted() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rowUpdated() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setFetchDirection(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFetchSize(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateArray(int arg0, java.sql.Array arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateArray(String arg0, java.sql.Array arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAsciiStream(int arg0, InputStream arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAsciiStream(String arg0, InputStream arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAsciiStream(int arg0, InputStream arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAsciiStream(String arg0, InputStream arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAsciiStream(int arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAsciiStream(String arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBigDecimal(int arg0, BigDecimal arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBigDecimal(String arg0, BigDecimal arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBinaryStream(int arg0, InputStream arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBinaryStream(String arg0, InputStream arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBinaryStream(int arg0, InputStream arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBinaryStream(String arg0, InputStream arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBinaryStream(int arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBinaryStream(String arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBlob(int arg0, Blob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBlob(String arg0, Blob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBlob(int arg0, InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBlob(String arg0, InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBlob(int arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBlob(String arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBoolean(int arg0, boolean arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBoolean(String arg0, boolean arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateByte(int arg0, byte arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateByte(String arg0, byte arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBytes(int arg0, byte[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBytes(String arg0, byte[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCharacterStream(int arg0, Reader arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCharacterStream(String arg0, Reader arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCharacterStream(int arg0, Reader arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCharacterStream(String arg0, Reader arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCharacterStream(int arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCharacterStream(String arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateClob(int arg0, Clob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateClob(String arg0, Clob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateClob(int arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateClob(String arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateClob(int arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateClob(String arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDate(int arg0, Date arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDate(String arg0, Date arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDouble(int arg0, double arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDouble(String arg0, double arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateFloat(int arg0, float arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateFloat(String arg0, float arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateInt(int arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateInt(String arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateLong(int arg0, long arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateLong(String arg0, long arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNCharacterStream(int arg0, Reader arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNCharacterStream(String arg0, Reader arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNCharacterStream(int arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNCharacterStream(String arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNClob(int arg0, NClob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNClob(String arg0, NClob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNClob(int arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNClob(String arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNClob(int arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNClob(String arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNString(int arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNString(String arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNull(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNull(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateObject(int arg0, Object arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateObject(String arg0, Object arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateObject(int arg0, Object arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateObject(String arg0, Object arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRef(int arg0, Ref arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRef(String arg0, Ref arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRow() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRowId(int arg0, RowId arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRowId(String arg0, RowId arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateSQLXML(int arg0, SQLXML arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateSQLXML(String arg0, SQLXML arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateShort(int arg0, short arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateShort(String arg0, short arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateString(int arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateString(String arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTime(int arg0, Time arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTime(String arg0, Time arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTimestamp(int arg0, Timestamp arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTimestamp(String arg0, Timestamp arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean wasNull() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	
	
}
