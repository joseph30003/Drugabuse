package drugabuse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;

public class Node_extractor {
	
	public static void Nodes_add (String node, String type, String table, Connection conn )  {
		
		try{   
		
			String query = "SELECT * FROM "+table+" where "+"node = \""+node+"\" and type = \""+type+"\"";
		 
		
	      // create the java statement
	      Statement st = conn.createStatement();
	       
	      
	      ResultSet rs = st.executeQuery(query);
	     if (!rs.next()){
	    	
	    	    PreparedStatement rs_u = (PreparedStatement) conn.prepareStatement("INSERT INTO "+table+"(node,type) VALUES(?,?)");
				
				rs_u.setString(1, node);
				rs_u.setString(2, type);
				rs_u.execute();
	    	   	  
	    	   
	      }
		}
		catch (Exception e)
	    {
	      System.err.println("Got an exception! ");
	      System.err.println(e.getMessage());
	    }
		
	}
	
	
	
	public static void Nodes_extrator (String column1, String column2, String table, Connection conn ) {
	
		try
		{
			String query = "SELECT "+column1+","+column2+"FROM"+table;
		
	      ResultSet rs = conn.createStatement().executeQuery(query);
	      while(rs.next()){
	    	  Nodes_add(rs.getString(column1),rs.getString(column2),table,conn);
	      }
		}
		catch (Exception e)
	    {
	      System.err.println("Got an exception! ");
	      e.printStackTrace();
	    }
	}
	
	
	
	
	public static void main(String[] args)
	  { 
		
	    try
	    {
	      String myUrl = "jdbc:mysql://biomedinformatics.is.umbc.edu/drugabuse";
	      Connection conn = DriverManager.getConnection(myUrl, "weijianqin", "weijianqin");
	      
	      String query_2 = "SELECT id,name,type FROM nodes_withid";
	      ResultSet rs_2 = conn.createStatement().executeQuery(query_2);
	      while(rs_2.next()){
	    	   
	    	  Files_creator.writetofile(rs_2.getString(1)+","+rs_2.getString(2)+","+rs_2.getString(3),"nodes_id_list");
	      }
	     
	      
	    }
	    catch (Exception e)
	    {
	      System.err.println("Got an exception! ");
	      e.printStackTrace();
	    }
	  }
	
	

}
