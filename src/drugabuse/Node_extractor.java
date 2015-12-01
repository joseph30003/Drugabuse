package drugabuse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

public class Node_extractor {
	
	public static void Nodes_add (String node, String type, String table, Connection conn )  {
		
		try{   
		
			String query = "SELECT * FROM "+table+" where "+"node"+" ="+node;
		 
		
	      // create the java statement
	      Statement st = conn.createStatement();
	       
	      
	      ResultSet rs = st.executeQuery(query);
	     if (!rs.next()){
	    	
	    	    PreparedStatement rs_u = (PreparedStatement) conn.prepareStatement("INSERT INTO "+table+"(node,type) VALUES(?,?)");
				
				rs_u.setString(1, node);
				rs_u.setString(1, type);
				rs_u.execute();
	    	   	  
	    	   
	      }
		}
		catch (Exception e)
	    {
	      System.err.println("Got an exception! ");
	      System.err.println(e.getMessage());
	    }
		
	}
	
	
	
	public static void main(String[] args)
	  { 
		int count=0;
	    try
	    {
	      String myUrl = "jdbc:mysql://biomedinformatics.is.umbc.edu/drugabuse";
	      Connection conn = DriverManager.getConnection(myUrl, "weijianqin", "weijianqin");
	    
	     
	     System.out.println(count++);
	     
	      
	    }
	    catch (Exception e)
	    {
	      System.err.println("Got an exception! ");
	      e.printStackTrace();
	    }
	  }
	
	

}
