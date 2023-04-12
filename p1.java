import java.lang.Thread.State;
import java.sql.*;
import java.util.Scanner;

import com.mysql.cj.protocol.Resultset;

class ConnectionManager{
    public Connection conn=null;
    Statement s1;
    int r1;
    public void initiate() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver"); 
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartcity","root","Fllbcksql");
        s1 = conn.createStatement();
    }
    
    
    public void insertInitialValues(String tableName){
        if(tableName.equalsIgnoreCase("citizen")){
            
            try {
                
                
                String[][] citizenValues = {{"Dev M Kumar","dev@gmail.com","123456789","1","Resident","BT1"},
                                            {"Gokul Krishnan","gokul@gmail.com","234567891","2","Resident","BT2"},
                                            {"KV Dhruthi Rao","dhruthi@gmail.com","234567231","3","Resident","BT3"},
                                            {"Gayatri Prabhala","gayatri@gmail.com","234521891","4","Resident","BT4"},
                                            {"Darth Vader","dvader@gmail.com","345666123","5","Tourist","BT5"}};
                for(String[] a : citizenValues){
                    String cname = a[0];
                    String email = a[1];
                    String phno = a[2];
                    String citizen_id = a[3];
                    String citizen_type = a[4];
                    String bank_id = a[5];
                    r1=s1.executeUpdate("insert into citizen values("+"\""+cname+"\""+","+"\""+email+"\""+","+"\""+phno+"\""+","+"\""+citizen_id+"\""+","+"\""+citizen_type+"\""+","+"\""+bank_id+"\""+")");
                }
                System.out.println("inserted succesfully into citizen table");
                s1.close();
            } 
            catch (SQLException e) {
                // TODO Auto-generated catch block
                System.out.println("error");
                System.out.println(e);
                e.printStackTrace();
            }
            
        }
        else if(tableName.equalsIgnoreCase("locations")){
            try {
                Statement s1;
                s1 = conn.createStatement();
                String[][] locationValues = {{"kanakpura","FORUM Mall","A new, luxury mall with premium brands opening their stores. Shop now","4.5"},
                                            {"rajajinagar","Sheraton Grand Hotel","This hotel is within the Brigade Gateway complex and is a good choice for a night out","4.3"},
                                            {"bellandur","Ecospace","This is a highly developed IT tech park","4.6"},
                                            {"rrnagar","Glen's Bakehouse","This is a cute cake shop","4.4"}};
                for(String[] a : locationValues){
                    String address = a[0];
                    String LName = a[1];
                    String Descript = a[2];
                    String review = a[3];
                    r1=s1.executeUpdate("insert into locations values("+"\""+address+"\""+","+"\""+LName+"\""+","+"\""+Descript+"\""+","+"\""+review+"\""+")");
                }
                System.out.println("inserted succesfully into location table");
                s1.close();
            } 
            catch (SQLException e) {
                // TODO Auto-generated catch block
                System.out.println(e);
                e.printStackTrace();
            }
        }
        else if(tableName.equalsIgnoreCase("bank")){
            try {
                Statement s1;
                s1 = conn.createStatement();
                String[][] bankValues = {{"BT1","1","20000"},
                                        {"BT2","2","10000"},
                                        {"BT3","3","30000"},
                                        {"BT4","4","40000"},
                                        {"BT5","5","50000"}};
                                                    
                for(String[] a : bankValues){
                    String bank_id = a[0];
                    String citizen_id = a[1];
                    String amount = a[2];
                    r1=s1.executeUpdate("insert into bank values("+"\""+bank_id+"\""+","+"\""+citizen_id+"\""+","+amount+")");
                }
                System.out.println("inserted succesfully into bank table");
                s1.close();
            } 
            catch (SQLException e) {
                // TODO Auto-generated catch block
                System.out.println(e);
                e.printStackTrace();
            }
        }
    }
}

class Citizen{
    public Connection connCitizen = null;
    Statement s1;
    Resultset r1;
    public String citizenName;
    public String citizenID;
    public String bankID;
    public String citizenEmail;
    public String citizenType;
    public String phno;
    public void initiate() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver"); 
        connCitizen = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartcity","root","Fllbcksql");
        s1 = connCitizen.createStatement();
    }

    public void closer() throws SQLException{
        connCitizen.close();
        s1.close();
    }

    public void login() throws SQLException{
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Enter your citizen id");
        String id = sc1.nextLine();
        //System.out.println("id = "+id);
        ResultSet rciti;
        rciti = s1.executeQuery("SELECT * FROM citizen where citizen_id="+"\""+id+"\"");
      while(rciti.next()){
        citizenName = rciti.getString("CName");
        citizenEmail = rciti.getString("email");
        phno = rciti.getString("phno");
        citizenID = rciti.getString("citizen_id");
        citizenType = rciti.getString("citizen_type");
        bankID = rciti.getString("bank_id");
      }
      System.out.println("Hllo There, "+citizenName);
    }

    public void listLocs() throws SQLException{
        ResultSet rLoc;
        rLoc = s1.executeQuery("Select * from locations");
        int i=0;
        while(rLoc.next()){
            System.out.println("Landmark "+i);
            i=i+1;
            System.out.println("City : "+rLoc.getString("address"));
            System.out.println("Name : "+rLoc.getString("LName"));
            System.out.println("Brief Description : "+rLoc.getString("Descript"));
            System.out.println("Review : "+rLoc.getFloat("review"));
        }
    }

    public void utilCalc() throws SQLException{
        ResultSet rUtil;
        double amt=0;
        rUtil = s1.executeQuery("SELECT * from utils where citizen_id="+"\""+citizenID+"\"");
        while(rUtil.next()){
            amt = amt + rUtil.getDouble("rate")+rUtil.getDouble("overdue");
            
        }
        System.out.println("Your total util bill is = "+amt);
    }
}
public class p1{
    public static void main(String[] args) {
        ConnectionManager cnm1 = new ConnectionManager();
        Citizen c1 = new Citizen();
        try {
            //cnm1.initiate();
            c1.initiate();
            c1.login();
            c1.listLocs();
            c1.utilCalc();
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //cnm1.insertInitialValues("citizen");
        //cnm1.insertInitialValues("locations");
        //cnm1.insertInitialValues("bank");

    }
}