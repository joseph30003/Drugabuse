package drugabuse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Test {

	public static String Name_nodes(int id,Connection conn) throws Exception {
		String name=null;
		try
	    {
	      
	      String query = "select nodes_withid.name,nodes_withid.type from nodes_withid,network where nodes_withid.id = "+id;
	     
	      ResultSet rs = conn.createStatement().executeQuery(query);
	      
	     if(rs.next()){
	    	  
	    	  if(rs.getString(2).equals("cid")){
	    		  name = URL_reader.CID_read(rs.getString(1));
	    		  System.out.println(name);
	    	  }else{
	    		  name = rs.getString(1);
	    	  }
	    	  
	    	  
	    	  
	      }
	    }
	    catch (Exception e)
	    {
	      System.err.println("Got an exception! ");
	      e.printStackTrace();
	    }
		
		
		return name;
		
	}
	
	
	
	
	
	public static void main(String[] args) throws Exception
	  { 
		String[] attribute={"network.Degree","network.Closeness_Centrality","network.Betweenness_Centrality"};
		String[] type={"gene","disease","cid"};
		String filename="result_3.txt";
		List<String> node = new ArrayList<String>();
		String name;
		int count=0;
	    try
	    {
	      String myUrl = "jdbc:mysql://biomedinformatics.is.umbc.edu/drugabuse";
	      Connection conn = DriverManager.getConnection(myUrl, "weijianqin", "weijianqin");
	      for(int i=0;i<attribute.length;i++){
	    	  Files_creator.writetofile(attribute[i]+"********************************",filename);
	    	  
	    	  for(int j=0;j<type.length;j++){
	    		  Files_creator.writetofile(type[j]+"---------------------------------",filename);	  
	      String query = "select nodes_withid.id,nodes_withid.name,"+attribute[i]+" from nodes_withid,network where nodes_withid.id = network.id and nodes_withid.`type` = \""+type[j]+"\" order by "+attribute[i]+" desc";
	      System.out.println(query);
	      ResultSet rs = conn.createStatement().executeQuery(query);
	      count=0;
	      while(rs.next()&&count<5){
	    	   count++;
	    	   name=Name_nodes(rs.getInt(1),conn);
	    	   Files_creator.writetofile(rs.getString(1)+","+name+","+rs.getDouble(3),filename);
	    	   if(!node.contains(rs.getString(1))){
	    		   node.add(rs.getString(1));
	    	   }
	      }
	      }
	      }
	      Files_creator.writetofile("nodes list+++++++++++++++++",filename);
	      for(int k=0;k<node.size();k++){
	    	  Files_creator.writetofile(node.get(k),filename);
	      }
	    }
	    catch (Exception e)
	    {
	      System.err.println("Got an exception! ");
	      e.printStackTrace();
	    }
	  }
	
}
