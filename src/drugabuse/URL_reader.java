package drugabuse;

import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.io.*;

public class URL_reader {
    public static String CID_read(String CID) throws Exception {

        URL oracle = new URL("https://pubchem.ncbi.nlm.nih.gov/rest/pug/compound/cid/"+CID+"/property/IUPACname/TXT");
        BufferedReader in = new BufferedReader(
        new InputStreamReader(oracle.openStream()));

        String inputLine = null;
        inputLine = in.readLine();
        in.close();
        return inputLine;
    }
    
    public static void main(String[] args) throws Exception{
    	String myUrl = "jdbc:mysql://biomedinformatics.is.umbc.edu/drugabuse";
	      Connection conn = DriverManager.getConnection(myUrl, "weijianqin", "weijianqin");
    	
    	
    	
    	
    	Test.Name_nodes(7675, conn);
    }
    
    
    
}