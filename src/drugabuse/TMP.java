package drugabuse;

import java.io.*;
import java.util.HashMap;

public class TMP {
	


	public static void main(String[] args) throws IOException {
		
	    HashMap<Integer, Integer> datamap = new HashMap<Integer, Integer>();
	    
		File file = new File("C:/Users/xf37538/Documents/data-nexcade/1.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		String line=null;
		
		int key=0,val=0;
		
		while ((line = br.readLine()) != null) {
				
					
	   
		 String[] tmp=line.replaceAll("\\s+"," ").split(" ");
		 key=Integer.parseInt(tmp[1]);
		 val=Integer.parseInt(tmp[0]);
		 datamap.put(key,val);		
		 
		}
		
		int[] ser={19549,19542,17946,17373,20649,18699,1396,7578,7675,16885,17485,19097,17890,17599,19792,1248,1257,14089,19526,6675,5440,10232};
		
		for(int i=0;i<ser.length;i++){
	    int id=datamap.get(ser[i]);
	    System.out.print(id+" ");}
	
		br.close();

	}
}
