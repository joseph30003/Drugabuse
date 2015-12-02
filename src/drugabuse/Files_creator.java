package drugabuse;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class Files_creator {

	public static void writetofile(String line,String file) throws IOException {
		// Construct BufferedReader from FileReader
		//PrintWriter out = new PrintWriter(new FileWriter(log, true));
		PrintWriter fw0 = new PrintWriter(new FileWriter("C:/Users/xf37538/Documents/"+file+".cvs",true));
		
					
		    fw0.write(line+"\n");
		    fw0.close();
					
	}
}
