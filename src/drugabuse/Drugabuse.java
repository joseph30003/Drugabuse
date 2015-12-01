package drugabuse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

public class Drugabuse {

	public static List<String> Relations_searcher (int aid, String column1, String table, Connection conn )  {
		List<String> relations = new ArrayList<String>();
		try{   
		
			String query = "SELECT "+column1+" FROM "+table+" where "+"aid"+" ="+aid;
		 
		
	      // create the java statement
	      Statement st = conn.createStatement();
	       
	      
	      ResultSet rs = st.executeQuery(query);
	      while (rs.next()){
	    	
	    	   	  
	    	    relations.add(rs.getString(column1).replace(",", "")) ;
	    	 
	      }
		}
		catch (Exception e)
	    {
	      System.err.println("Got an exception! ");
	      System.err.println(e.getMessage());
	    }
		return relations;
	}

	
	public static List<String> Relations_searcher (int aid, String column1, String column2, String table, Connection conn )  {
		List<String> relations = new ArrayList<String>();
		int count=0;
		//int last_score=0;
		try{   
		
			String query = "SELECT "+column1+","+column2+" FROM "+table+" where "+"aid"+" ="+aid+" and cid>0 order by score";
		 
		
	      // create the java statement
	      Statement st = conn.createStatement();
	       
	      
	      ResultSet rs = st.executeQuery(query);
	      while (rs.next()&& count<5){
	    	  
	    	    
	    	    count++;  
	    	    relations.add(rs.getString(column1)+","+rs.getString(column2)) ;
	    	 
	      }
		}
		catch (Exception e)
	    {
	      System.err.println("Got an exception! ");
	      System.err.println(e.getMessage());
	    }
		return relations;
	}
	
	
	
	
	
	
	public static void Edges_update(String node1, String type1, String node2, String type2,Connection conn){
		String node_1=null,node_2=null;
		int score = 0;
			if(node1.contains(",")){
		    String[] node=node1.split(",");
		    node_1=node[0];
		    score=Integer.parseInt(node[1]);
		    }else{
		    	node_1=node1;
		    }
			
			if(node2.contains(",")){
			    String[] node=node2.split(",");
			    node_2=node[0];
			    score=Integer.parseInt(node[1]);
			    }else{
			    	node_2=node2;
			    }
			
			
			try{
	    		
				String query = "select connects,scores from edges_withoutAID where node1_name=\""+node_1+"\" and node2_name=\""+node_2+"\"" ;
				ResultSet rs = conn.createStatement().executeQuery(query);
			      
			     
				if(rs.next()){
					
					int connect=rs.getInt("connects");
					score=rs.getInt("scores")+score;
					connect++;
					PreparedStatement rs_u = (PreparedStatement) conn.prepareStatement("update edges_withoutAID set connects = ?,scores= ? where node1_name = ? and node2_name = ? ");
					rs_u.setInt(1, connect);
					rs_u.setInt(2, score);
					rs_u.setString(3, node_1);
					rs_u.setString(4, node_2);
					rs_u.execute();
				}
				
								
    			else{
    				PreparedStatement pst_user =  (PreparedStatement) conn.prepareStatement("INSERT INTO edges_withoutAID(node1_name,node1_type,node2_name,node2_type,connects,scores) VALUES(?,?,?,?,?,?)");
    	            pst_user.setString(1,node_1);
    	            pst_user.setString(2,type1);
    	            pst_user.setString(3,node_2);
    	            pst_user.setString(4,type2);
    	            pst_user.setInt(5,1);
    	            pst_user.setInt(6,score);
    	            pst_user.execute();
    			}
    			
    	}
    	catch (Exception e)
        {
          System.err.println("Got an exception! ");
          System.err.println(e.getMessage());
        }
		
	}
	
	
	public static void Edges_creator(List<String> list1,String type1,List<String> list2,String type2,Connection conn){
	
		if( !(list1.isEmpty()) && !(list2.isEmpty()))
		for(int i=0;i<list1.size();i++){
			for(int j=0;j<list2.size();j++){
				Edges_update(list1.get(i),type1,list2.get(j),type2,conn);
			}
		}
		
	}
	
	
	public static void Network_creator (int aid,Connection conn){
	
		  List<String> cid=Relations_searcher(aid,"cid","score","assay_gs_disease",conn);
	      List<String> disease=Relations_searcher(aid,"disease","aidtodisease_u",conn);
	      List<String> gs=Relations_searcher(aid,"gs","aidtoGS",conn);
	      
	      
	      Edges_creator(gs,"gene",disease,"disease",conn);
	      
	      Edges_creator(gs,"gene",cid,"cid",conn);
	     
	      Edges_creator(disease,"disease",cid,"cid",conn);
		  gs.clear();
		  cid.clear();
		  disease.clear();
	      
	}
	
	public static void main(String[] args)
	  { 
		int count=0;
	    try
	    {
	      String myUrl = "jdbc:mysql://biomedinformatics.is.umbc.edu/drugabuse";
	      Connection conn = DriverManager.getConnection(myUrl, "weijianqin", "weijianqin");
	     String query_search = "select * from aid_gs_disease order by aid";
	     ResultSet rs = conn.createStatement().executeQuery(query_search);
	     while(rs.next()){
	         count++;
	         //System.out.println(rs.getInt("aid"));
	    	 Network_creator(rs.getInt("aid"),conn);
	      
	     }
	     
	     System.out.println(count++);
	     
	      
	    }
	    catch (Exception e)
	    {
	      System.err.println("Got an exception! ");
	      e.printStackTrace();
	    }
	  }
	
}

