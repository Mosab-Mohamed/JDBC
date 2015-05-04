package DBMS;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.SQLWarning;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import DBMS.DataBase;


public class Statement_Implementer implements java.sql.Statement {
	private Connection con;
	private ArrayList<String> batch ;
	private boolean closed;
	private int QueryTimeout;
	public Statement_Implementer (Connection x){
		closed = false;
		batch = new ArrayList<String>();
		con = x;
		QueryTimeout = 999999999;
	}
	DataBase dbms = new DataBase();
	@Override
	public int[] executeBatch() throws SQLException,SQLTimeoutException {	
		if(!closed){
			boolean flag=false;
			int[] BatchResponse = new int[batch.size()];
			ArrayList<Integer> zeros = new ArrayList<Integer>();
			for(int i=0;i<batch.size();i++){
				String sql  = batch.get(i);
				ExecutorService executor = Executors.newSingleThreadExecutor();
		        @SuppressWarnings("unchecked")
				Future<String> future = executor.submit(new Callable(){
		    	    public String call() throws  SQLException{
		    	    	
		    	    	String ans = new String();
		    	    	try {
		    	    		ans=dbms.input(sql);
		    			}catch (Throwable e){
		    			}		
		    	        return ans;      
		    	    }
		        });
		        String ans = new String();
		        try {
		           ans= future.get(QueryTimeout, TimeUnit.SECONDS);
		        } catch (TimeoutException | InterruptedException e) {
		        	flag=true;
		        	BatchResponse[i] = EXECUTE_FAILED;
		            throw new SQLTimeoutException(e.getMessage());
		        } catch (ExecutionException e) {
		        	flag=true;
		        	BatchResponse[i] = EXECUTE_FAILED;
				}
		        executor.shutdownNow();
		        if(ans!=null&&(ans.equals(dbms.TABLE_NOT_FOUND)||ans.equals(dbms.COLUMN_NOT_FOUND)||ans.equals(dbms.TABLE_ALREADY_EXISTS)||ans.equals(dbms.PARSING_ERROR)||ans.equals(dbms.DB_NOT_FOUND)||ans.equals(dbms.COLUMN_TYPE_MISMATCH)||ans.equals(dbms.NOT_MATCH_CRITERIA))){
	    			flag=true;
		        	BatchResponse[i] = EXECUTE_FAILED;
	    		}
		        else{
					String[] parts = batch.get(i).split(" ");
					parts[0] = parts[0].toLowerCase();
					if(parts[0].equals("insert"))
						BatchResponse[i] = 1;
					else if(parts[0].equals("update")||parts[0].equals("delete")){
						String[] getnum = ans.split(" ");
						BatchResponse[i] =Integer.parseInt(getnum[0]);
					}
					else{
						//zeros.add(i);
						BatchResponse[i] =0;
					}
		        }
			}
//			for(int j=0;j<BatchResponse.length;j++){
//				if(!zeros.contains(j)&&BatchResponse[j]==0)
//					BatchResponse[j] = EXECUTE_FAILED;
//			}
			if(flag){
				throw new BatchUpdateException(BatchResponse);
			}
				
			return BatchResponse;
		}
		else
			throw new SQLException("the statement is closed");		
	}
	
	@Override
	public ResultSet executeQuery(String sql) throws SQLException, SQLTimeoutException {
		if(!closed){
			String[] parts = sql.split(" ");
			parts[0] = parts[0].toLowerCase();
			if(!parts[0].equals("select"))
				throw new SQLException("it is not a select query");
			String ans = new String();
			try {
				ans = dbms.input(sql);
			} catch (Throwable e) {
				// will not catch anything here only manzar
				throw new SQLException(e.getMessage());
			}
			if(ans!=null&&(ans.equals(dbms.TABLE_NOT_FOUND)||ans.equals(dbms.COLUMN_NOT_FOUND)||ans.equals(dbms.TABLE_ALREADY_EXISTS)||ans.equals(dbms.PARSING_ERROR)||ans.equals(dbms.DB_NOT_FOUND)||ans.equals(dbms.COLUMN_TYPE_MISMATCH)||ans.equals(dbms.NOT_MATCH_CRITERIA)))
				throw new SQLException(ans);	
			
			ExecutorService executor = Executors.newSingleThreadExecutor();
			selector s = new selector(sql);
			Future<String> future = executor.submit(s);
	        try {
		           future.get(QueryTimeout, TimeUnit.SECONDS);
		        } catch (TimeoutException | InterruptedException e) {
		            throw new SQLTimeoutException(e.getMessage());
		        } catch (ExecutionException e) {
					throw new SQLException(e.getMessage());
				}
	        return new Resultset_Implementer(s.arrr,s.finalcol,s.tn);
		}
		else
			throw new SQLException("the statement is closed");
	}
	
	
	@Override
	public int executeUpdate(String sql) throws SQLException,SQLTimeoutException {
		if(!closed){
			String[] parts = sql.split(" ");
			parts[0] = parts[0].toLowerCase();
			if(parts[0].equals("select"))
				throw new SQLException("it is a select Statement, can't excute an update");
			ExecutorService executor = Executors.newSingleThreadExecutor();
	        @SuppressWarnings("unchecked")
			Future<String> future = executor.submit(new Callable(){
		    	    public String call() throws  SQLException{
		    	    	String ans = new String();
		    	    	try {
		    	    		ans=dbms.input(sql);
		    			}
		    			catch (Throwable e){
		    				// will not catch anything here only manzar
		    				throw new SQLException(e.getMessage());
		    			}		
		    	        return ans;      
		    	    }
		    	
	        });
	        String ans = new String();
	        try {
	           ans= future.get(QueryTimeout, TimeUnit.SECONDS);
	        } catch (TimeoutException | InterruptedException e) {
	            throw new SQLTimeoutException(e.getMessage());
	        } catch (ExecutionException e) {
				throw new SQLException(e.getMessage());
			}
	        executor.shutdownNow();
	        if(ans.equals(dbms.TABLE_NOT_FOUND)||ans.equals(dbms.COLUMN_NOT_FOUND)||ans.equals(dbms.TABLE_ALREADY_EXISTS)||ans.equals(dbms.PARSING_ERROR)||ans.equals(dbms.DB_NOT_FOUND)||ans.equals(dbms.COLUMN_TYPE_MISMATCH)||ans.equals(dbms.NOT_MATCH_CRITERIA))
    			throw new SQLException(ans);	
    		
			
			if(parts[0].equals("insert"))
				return 1;
			else if(parts[0].equals("update")||parts[0].equals("delete")){
				String[] getnum = ans.split(" ");
				return Integer.parseInt(getnum[0]);
			}
			else
				return 0;
		}
		else
			throw new SQLException("the statement is closed");	
		
	}

	
	@Override
	public boolean execute(String sql) throws SQLException,SQLTimeoutException {

		if(!closed){
			ExecutorService executor = Executors.newSingleThreadExecutor();
	        @SuppressWarnings("unchecked")
			Future<String> future = executor.submit(new Callable(){
		    	    public String call() throws  SQLException{
		    	    	String ans = null;
		    	    	try {
		    	    		ans=dbms.input(sql);	
		    			}
		    			catch (Throwable e){
		    				// will not catch anything here only manzar
		    				throw new SQLException(e.getMessage());
		    			}		
		    	        return ans;      
		    	    }
		    	
	        });
	        String ans = new String();
	        try {
	            ans=future.get(QueryTimeout, TimeUnit.SECONDS);
	        } catch (TimeoutException | InterruptedException e) {
	            throw new SQLTimeoutException(e.getMessage());
	        } catch (ExecutionException e) {
				throw new SQLException(e.getMessage());
			}
	        executor.shutdownNow();
	        if(ans!=null&&(ans.equals(dbms.TABLE_NOT_FOUND)||ans.equals(dbms.COLUMN_NOT_FOUND)||ans.equals(dbms.TABLE_ALREADY_EXISTS)||ans.equals(dbms.PARSING_ERROR)||ans.equals(dbms.DB_NOT_FOUND)||ans.equals(dbms.COLUMN_TYPE_MISMATCH)||ans.equals(dbms.NOT_MATCH_CRITERIA)))
				throw new SQLException(ans);
	        String[] parts = sql.split(" ");
			if(parts[0].toLowerCase().equals("select"))
				return true;
			return false;
		}	
		else
			throw new SQLException("the statement is closed");	
	}
	
	@Override
	public void close() throws SQLException {
		closed = true;
	}
	@Override
	public int getQueryTimeout() throws SQLException {
		return QueryTimeout;
	}
	@Override
	public void setQueryTimeout(int seconds) throws SQLException {
		QueryTimeout = seconds;
	}
	@Override
	public void addBatch(String sql) throws SQLException {
		batch.add(sql);
	}
	@Override
	public void clearBatch() throws SQLException {
		batch.clear();
	}
	
	@Override
	public Connection getConnection() throws SQLException {
		return con;
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
	public int getMaxFieldSize() throws SQLException {
		return 0;
	}

	@Override
	public void setMaxFieldSize(int max) throws SQLException {
		
	}

	@Override
	public int getMaxRows() throws SQLException {
		return 0;
	}

	@Override
	public void setMaxRows(int max) throws SQLException {
		
	}

	@Override
	public void setEscapeProcessing(boolean enable) throws SQLException {
		
	}

	@Override
	public void cancel() throws SQLException {
		
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		return null;
	}

	@Override
	public void clearWarnings() throws SQLException {
		
	}

	@Override
	public void setCursorName(String name) throws SQLException {
		
	}

	@Override
	public ResultSet getResultSet() throws SQLException {
		return null;
	}

	@Override
	public int getUpdateCount() throws SQLException {
		return 0;
	}

	@Override
	public boolean getMoreResults() throws SQLException {
		return false;
	}

	@Override
	public void setFetchDirection(int direction) throws SQLException {
		
	}

	@Override
	public int getFetchDirection() throws SQLException {
		return 0;
	}

	@Override
	public void setFetchSize(int rows) throws SQLException {
		
	}

	@Override
	public int getFetchSize() throws SQLException {
		return 0;
	}

	@Override
	public int getResultSetConcurrency() throws SQLException {
		return 0;
	}

	@Override
	public int getResultSetType() throws SQLException {
		return 0;
	}

	@Override
	public boolean getMoreResults(int current) throws SQLException {
		return false;
	}

	@Override
	public ResultSet getGeneratedKeys() throws SQLException {
		return null;
	}

	@Override
	public int executeUpdate(String sql, int autoGeneratedKeys)
			throws SQLException {
		return 0;
	}

	@Override
	public int executeUpdate(String sql, int[] columnIndexes)
			throws SQLException {
		return 0;
	}

	@Override
	public int executeUpdate(String sql, String[] columnNames)
			throws SQLException {
		return 0;
	}

	@Override
	public boolean execute(String sql, int autoGeneratedKeys)
			throws SQLException {
		return false;
	}

	@Override
	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		return false;
	}

	@Override
	public boolean execute(String sql, String[] columnNames)
			throws SQLException {
		return false;
	}

	@Override
	public int getResultSetHoldability() throws SQLException {
		return 0;
	}

	@Override
	public boolean isClosed() throws SQLException {
		return false;
	}

	@Override
	public void setPoolable(boolean poolable) throws SQLException {
		
	}

	@Override
	public boolean isPoolable() throws SQLException {
		return false;
	}

	@Override
	public void closeOnCompletion() throws SQLException {
		
	}

	@Override
	public boolean isCloseOnCompletion() throws SQLException {
		return false;
	}
//	public static void main(String[] args) {
//		System.out.println("hh");
//		Statement_Implementer s= new Statement_Implementer();
//		try {
//			if(s.execute("Select * from Persons;"))
//				System.out.println("yes");
//			else
//				System.out.println("oh");
//
//		} catch (SQLException e) {
//			System.out.println("msh 3aref");
//		}
//	}

	
}
class selector implements Callable<String> {
	String sql;
	ArrayList<ArrayList<String>> arrr=new ArrayList<ArrayList<String>>();
	ArrayList<Column> arr=new ArrayList<Column>();
	ArrayList<Column> finalcol=new ArrayList<Column>();

	String tn;
	public selector(String sq){
		sql=sq;
		try {
			regex(sql);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
    @Override
    public String call() throws Exception {
        try {
			regex(sql);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
    }
    public void regex (String input) throws Throwable{
		LogicalValidation validation  = new LogicalValidation();

    	if(input.toLowerCase().matches("select(\\s+)(\\*)(\\s+)from(\\s+)([;\\w]*+)")){
			String[] splitString = (input.split("\\s+"));
			String table_name=splitString[3].replace(";","");
			
				select(arr, null , table_name);
				//make a new object of your class and call function from your class that returns 
				//an answer string
		}
		else if(input.toLowerCase().matches("select(\\s+)([*\\w]*+)([,\\w*+]*)(\\s+)from(\\s+)(\\w*+)(\\s+)where([\\s+\\w]*+)([=><])(['\\s+\\w]*+)")){
			input=input.replace(",", " ");
			String[] splitString = (input.split("\\s+"));
			int start=input.indexOf(splitString[4]);
			String ineq=input.substring(start+6);
			
			if(splitString[1].equals("*")){
				
					select(arr,ineq,splitString[3]);
				
					//make a new object of your class and call function from your class that returns 
					//an answer string
			}
			else{	
				int i;
				for( i=1;;i++){
					if(splitString[i].toLowerCase().replace(" ", "").equals("from")){
						break;
					}
					splitString[i]=splitString[i].replace(" ", "");
					Column c=new Column(splitString[i],null);
					arr.add(c);
				}
				
					select(arr, ineq,splitString[i+1]);             // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
					//make a new object of your class and call function from your class that returns 
					//an answer string
			}	
		}
		
		else if(input.toLowerCase().matches("select(\\s+)([*\\w]*+)([,\\w*+]*)(\\s+)from(\\s+)([;\\w]*+)")){
			input=input.replace(",", " ");
			String[] splitString = (input.split("\\s+"));
			int i;
			for( i=1;;i++){
				if(splitString[i].toLowerCase().replace(" ", "").equals("from")){
					break;
				}
				splitString[i]=splitString[i].replace(" ", "");
				Column c=new Column(splitString[i],null);
				arr.add(c);
			}
			 select(arr, null,splitString[i+1]);             // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
				//make a new object of your class and call function from your class that returns 
				//an answer string
			
		}
		
    }
    public String DataBaseName() throws IOException
	{
		BufferedReader in=new BufferedReader(new FileReader("currentdatabase.txt"));
		String databasename= in.readLine();
		return databasename;
	}
    
    public String select(ArrayList<Column> arr, String inequality,String table_name) throws Throwable {
		
		XMLParser x = new XMLParser(DataBaseName()+"\\"+table_name+".xml");
		tn=table_name;
		String selectedElement, value;
		char condition = 0;
		ArrayList<String> elementList = new ArrayList<String>();
		if(arr.isEmpty())
			elementList.add("*");
		else
			for(int j=0; j<elementList.size(); j++)
				elementList.add(arr.get(j).name);
		if(inequality == null)
		{
			condition='#';
			selectedElement=null;
			value=null;
		}
		else
		{
			if(inequality.contains("="))
				condition='=';
			else if(inequality.contains(">"))
				condition='>';
			else if(inequality.contains("<"))
				condition='<';
			String array[]= inequality.split("[<=>]");
			selectedElement=array[0].replaceAll(" ","");
			value=array[1].replace("'", "");
			///
			value=value.trim();
			///
		}
		x.selectElement(elementList, selectedElement, condition, value);
		arrr.addAll(x.dbTable);
		return null;
		
	}
    public void getType() throws Throwable{
    	Schema s  = new Schema();
    	ArrayList<Column> types=s.GetFromSchema(tn);
    	for(int i = 0;i<arr.size();i++){
    		for(int j=0;j<types.size();j++){
    			if(arr.get(i).name.toLowerCase().equals(types.get(j).name.toLowerCase())){
    				finalcol.add(types.get(j));
    			}
    		}
    	}
    }
}
//class updater implements Callable<String> {
//	String sql;
//	public updater(String x){
//		sql=x;
//	}
//	
//	public String call() throws Exception {
//		String ans = null;
//    	
//				
//        return ans;      
//	}
//	public String helper(){
//		return dbms.input(sql);	
//	}
//	
//}



