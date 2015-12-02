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
	
	
	
	public static void main(String[] args)
	  { 
		
	    try
	    {
	      String myUrl = "jdbc:mysql://biomedinformatics.is.umbc.edu/drugabuse";
	      Connection conn = DriverManager.getConnection(myUrl, "weijianqin", "weijianqin");
	      String query_1 = "SELECT node1_name,node1_type FROM edges_withoutAID";
	      ResultSet rs_1 = conn.createStatement().executeQuery(query_1);
	      while(rs_1.next()){
	    	  Nodes_add(rs_1.getString("node1_name"),rs_1.getString("node1_type"),"nodes_withoutAID",conn);
	      }
	      String query_2 = "SELECT node2_name,node2_type FROM edges_withoutAID";
	      ResultSet rs_2 = conn.createStatement().executeQuery(query_2);
	      while(rs_2.next()){
	    	  Nodes_add(rs_2.getString("node2_name"),rs_2.getString("node2_type"),"nodes_withoutAID",conn);
	      }
	     
	      
	    }
	    catch (Exception e)
	    {
	      System.err.println("Got an exception! ");
	      e.printStackTrace();
	    }
	  }
	
	

}
