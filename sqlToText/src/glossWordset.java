import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
public class glossWordset {
	 private static final String dbClassName = "com.mysql.jdbc.Driver";
	 private static final String CONNECTION =
             "jdbc:mysql://127.0.0.1/wn_pro_mysql";
	 public static void main(String[] args) throws ClassNotFoundException,SQLException, IOException{
		    FileOutputStream out1 = null;
		    FileOutputStream out2 = null;
		    out1 = new FileOutputStream("glossary.txt");
		    Properties p = new Properties();
		    p.put("user","root");
		    p.put("password","sachi123");
		    String query1 = "SELECT * FROM wn_gloss "; //source for the data set is wordnet
		    String query2 = "SELECT * FROM wn_synset ";
		    Connection c = DriverManager.getConnection(CONNECTION,p);
		    Statement s = c.createStatement();
		    ResultSet glossary = s.executeQuery(query1); //holds all the meanings of the words
		    
		   
		   
		    int i=1;
		    String str;
		    while(glossary.next()){
		    	 
		    	 str = Integer.toString(i)+" ";
		    	 out1.write(str.getBytes());
		    	 out1.write(glossary.getString(2).getBytes());
		    	 out1.write("\n".getBytes());
		    	 i++;
		    	 }
		    
		    ResultSet words = s.executeQuery(query2);//writing the words into words.txt file
		    out2 = new FileOutputStream("words.txt");
		    i = 0;
		    String id ="";
		    while(words.next()){
		    	if(!id.equals(words.getString(1))){ //only if the current id is not equal to the previous id
			    	 i++;
			    	 id = words.getString(1);
			    	 }
		    	 str = Integer.toString(i)+" ";
		    	 out2.write(str.getBytes());
		    	 out2.write(words.getString(3).getBytes());
		    	 out2.write("\n".getBytes());
		    	 
		    	 }
		    out1.close();
		    c.close();
	 }
     


}
