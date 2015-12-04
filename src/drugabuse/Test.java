package drugabuse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args)
	  { 
		String[] attribute={"network.Degree","network.Closeness_Centrality","network.Betweenness_Centrality"};
		String[] type={"gene","disease","cid"};
		String filename="result.txt";
		List<String> node = new ArrayList<String>();
		
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
	    	   Files_creator.writetofile(rs.getString(1)+","+rs.getString(2)+","+rs.getDouble(3),filename);
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
