import java.sql.*;

class SqlExtract{
    
    public void initiate(){
        Connection conn = null;

        try{
            Class.forName("com.mysql.jdbc.Driver"); 
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/root","creditspace","Fllbcksql");
            Statement s1 ;
            s1 = conn.createStatement();
            ResultSet r1;
            r1 = s1.executeQuery("select * from user");
            while(r1.next()){
                System.out.println(r1.getString("name"));
            }
            r1.close();
            s1.close();
            conn.close();
        }
        catch(Exception exception){
            System.out.println(exception);
        }
    }
}


public class p1{
    public static void main(String[] args) {
        SqlExtract sql1 = new SqlExtract();
        sql1.initiate();
    }
}