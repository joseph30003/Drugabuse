package drugabuse;

import java.net.*;
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
}