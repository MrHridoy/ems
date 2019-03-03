import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseClass{
      Connection conn=null;
      public static Connection ConnectDB(){
    	  try {
    		  Class.forName("com.mysql.cj.jdbc.Driver");
        	  Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/db_office","root","");
        	  System.out.println("Database Connected");
        	  
        	  return conn;
    	  }
    	  catch(ClassNotFoundException | SQLException e){
    		  System.out.println(e);
    		  
    		  return null; 
    		 
    	  }
    	  
    	  
      }
}
