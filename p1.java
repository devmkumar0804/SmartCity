import java.lang.Thread.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import org.w3c.dom.css.RGBColor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.IOException;

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

    public Citizen login() throws SQLException{
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Enter your citizen id");
        String id = sc1.nextLine();
        System.out.println("id = "+id);
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
      System.out.println("Hello There, "+citizenName);
      return this;
    }

    public ArrayList listLocs() throws SQLException{
        ResultSet rLoc;
        ArrayList final1 = new ArrayList<>();
        rLoc = s1.executeQuery("Select * from locations");
        int i=0;
        while(rLoc.next()){
            ArrayList a = new ArrayList<>();
            System.out.println("Landmark "+(i+1));
            i=i+1;
            System.out.println("Locality : "+rLoc.getString("address"));
            System.out.println("Name : "+rLoc.getString("LName"));
            System.out.println("Brief Description : "+rLoc.getString("Descript"));
            System.out.println("Review : "+rLoc.getFloat("review"));
            a.add(rLoc.getString("address"));
            a.add(rLoc.getString("LName"));
            a.add(rLoc.getString("Descript"));
            a.add(rLoc.getString("review"));
            final1.add(a);
        }
        return final1;
    }

    public ArrayList listDist() throws SQLException{
        ResultSet rLoc;
        ArrayList final1 = new ArrayList<>();
        rLoc = s1.executeQuery("Select * from dist");
        int i=0;
        while(rLoc.next()){
            ArrayList a = new ArrayList<>();
            System.out.println("Option "+(i+1));
            i=i+1;
            System.out.println("Source : "+rLoc.getString("sourc"));
            System.out.println("Destination : "+rLoc.getString("destination"));
            System.out.println("rate per kilometer : "+rLoc.getDouble("rateperkm"));
            System.out.println("Ride Type : "+rLoc.getString("ride_type"));
            System.out.println("Distance : "+rLoc.getDouble("distance"));
            a.add(rLoc.getString("sourc"));
            a.add(rLoc.getString("destination"));
            a.add(rLoc.getString("rateperkm"));
            a.add(rLoc.getString("ride_type"));
            a.add(rLoc.getString("distance"));
            final1.add(a);
        }
        return final1;
    }

    public double utilCalc() throws SQLException{
        ResultSet rUtil;
        double amt=0;
        
        rUtil = s1.executeQuery("SELECT * from utils where citizen_id="+"\""+citizenID+"\"");
        while(rUtil.next()){
            amt = amt + rUtil.getDouble("rate")+rUtil.getDouble("overdue");
            
        }
        return amt;
    }

    public ArrayList listBank() throws SQLException{
        ResultSet rBank;
        ArrayList final1 = new ArrayList<>();
        rBank = s1.executeQuery("select * from bank where citizen_id="+"\""+citizenID+"\"");
        while(rBank.next()){
            System.out.println("bank id = "+rBank.getString("bank_id"));
            System.out.println("bank balance = "+rBank.getDouble("amount"));
            final1.add(rBank.getString("bank_id"));
            final1.add(rBank.getString("amount"));
        }
        return final1;
    }

    public void makePayment() throws SQLException{
        ResultSet r1;
        double amt = utilCalc();
        ResultSet rBank;
        double newAmt=0;
        rBank = s1.executeQuery("select * from bank where citizen_id="+"\""+citizenID+"\"");
        while(rBank.next()){
            System.out.println("bank id = "+rBank.getString("bank_id"));
            System.out.println("bank balance = "+rBank.getDouble("amount"));
            newAmt = rBank.getDouble("amount");
        }
        newAmt=newAmt-amt;
        int r = s1.executeUpdate("update bank set amount="+newAmt+" where citizen_id="+"\""+citizenID+"\"");
        System.out.println("payment made, data updated");

    }

    public void makePaymentRide(String src,String dest) throws SQLException{
        ResultSet r1;
        double rpkm;
        double dist;
        double amt=0;
        ResultSet rBank;
        double newAmt=0;
        rBank = s1.executeQuery("select * from dist where sourc="+"\""+src+"\""+" and destination="+"\""+dest+"\"");
        while(rBank.next()){
            System.out.println("Fare = "+rBank.getString("rateperkm"));
            System.out.println("Distance = "+rBank.getDouble("distance"));
            rpkm = rBank.getDouble("rateperkm");
            dist = rBank.getDouble("distance");
            amt=rpkm*dist;
        }
        newAmt=newAmt-amt;
        int r = s1.executeUpdate("update bank set amount="+newAmt+" where citizen_id="+"\""+citizenID+"\"");
        System.out.println("payment made, data updated");

    }
}

class ImagePanel extends JComponent{
    private Image image;
    public ImagePanel(Image image){
        this.image=image;
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}

class CitizenView{
    public void mainFrame(Citizen c1) throws IOException{
        

        JFrame frame1 = new JFrame("Smart City Manager");
        ImageIcon img = new ImageIcon("C:\\Users\\gauta\\SmartCity\\background.jpg");
        frame1.setIconImage(img.getImage());
        frame1.setSize(1000, 1000);
        frame1.setLayout(new BorderLayout(20, 30));
        frame1.setResizable(true);
        JLabel background = new JLabel(new ImageIcon("./background.jpg"));
        frame1.add(background);
        frame1.setBackground(new Color(5, 10, 15));
        frame1.setSize(1000, 1000);




        JPanel headingPanel = new JPanel(new BorderLayout(20, 30));
        JLabel headingLabel = new JLabel("Hello There, "+c1.citizenName,0);
        //headingLabel.setBounds(150, 20, 200, 20);
        // headingLabel.setAlignmentX(0.5f);
        // headingLabel.setAlignmentY(0.5f);
        //frame1.add(headingLabel);
        headingPanel.add(headingLabel,BorderLayout.NORTH);
        JLabel l1 = new JLabel("Welcome to the Smart City Manager",0);
        //l1.setBounds(100, 40, 300, 20);
        headingPanel.add(l1,BorderLayout.SOUTH);
        //headingPanel.set
        //frame1.add(l1);
        JMenu m1 = new JMenu("Available Options", true);

        JButton b1 = new JButton("List Locations");
        JButton  b2 = new JButton("List Available Transports");
        JButton b3 = new JButton("List Your Bank Details");
        JButton b4 = new JButton("Book A Ride");
        JButton b5 = new JButton("Make A Payment");
        JButton b6 = new JButton("List Your Utility Dues");
        JPanel buttonPanel = new JPanel();
       //buttonPanel.setBounds(300,  300, 500, 500);
        buttonPanel.add(b1);
        buttonPanel.add(b2);
        buttonPanel.add(b3);
        buttonPanel.add(b4);
        buttonPanel.add(b5);
        buttonPanel.add(b6);
        // b1.setBounds(50, 200, 50, 20);
        // b1.setBounds(110, 200, 50, 20);
        // b1.setBounds(170, 200, 50, 20);
        // b1.setBounds(230, 200, 50, 20);
        // b1.setBounds(290, 200, 50, 20);
        // b1.setBounds(350, 200, 50, 20);
        // frame1.add(b1);
        // frame1.add(b2);
        // frame1.add(b3);
        // frame1.add(b4);
        // frame1.add(b5);
        // frame1.add(b6);
        JTabbedPane tResult = new JTabbedPane();
        //m1.setLayout(new GridLayout(3, 2));
        //locs.setBounds(100, 70, 100, 20);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    tResult.removeAll();
                    ArrayList locs1 = c1.listLocs();
                    
                    //t1.setBounds(50, 150, 400, 300);
                    for(int i=0;i<locs1.size();i++){
                        ArrayList a = (ArrayList) locs1.get(i);
                        JPanel p1 = new JPanel();
                        JLabel s1 = new JLabel("locality : "+a.get(0));
                        JLabel s2 = new JLabel("Name : "+a.get(1));
                        JLabel s3 = new JLabel("brief description : "+a.get(2));
                        JLabel s4 = new JLabel("review : "+a.get(3));
                        String f = s1+"\n"+s2+"\n"+s3+"\n"+s4+"\n";
                        JTextField tf1 = new JTextField(f);
                        p1.setLayout(new GridLayout(4, 1));
                        p1.setBounds(100, 600, 600, 600);
                        tf1.setSize(600,600);
                        p1.add(s1, 0);
                        p1.add(s2,1);
                        p1.add(s3,2);
                        p1.add(s4,3);
                        tResult.add("location "+(i+1),p1);

                    }
                    frame1.add(tResult,BorderLayout.PAGE_END);
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    tResult.removeAll();
                    ArrayList locs1 = c1.listDist();
                    //t1.setBounds(50, 150, 400, 300);
                    for(int i=0;i<locs1.size();i++){
                        ArrayList a = (ArrayList) locs1.get(i);
                        JPanel p1 = new JPanel();
                        JLabel s1 = new JLabel("Source : "+a.get(0));
                        JLabel s2 = new JLabel("Destination : "+a.get(1));
                        JLabel s3 = new JLabel("Fare : "+a.get(2));
                        JLabel s4 = new JLabel("Ride Type : "+a.get(3));
                        JLabel s5 = new JLabel("Distance : "+a.get(4));
                        String f = s1+"\n"+s2+"\n"+s3+"\n"+s4+"\n";
                        JTextField tf1 = new JTextField(f);
                        p1.setLayout(new GridLayout(4, 1));
                        tf1.setSize(600,500);
                        p1.add(s1);
                        p1.add(s2);
                        p1.add(s3);
                        p1.add(s4);
                        p1.add(s5);
                        tResult.add("Ride  "+(i+1),p1);
                        

                    }
                    //frame1.add(tResult,BorderLayout.PAGE_END);
                    frame1.add(tResult,BorderLayout.PAGE_END);
                    
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        b4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    tResult.removeAll();
                    ArrayList locs1 = c1.listBank();
                    //t1.setBounds(50, 150, 400, 300);
                    for(int i=0;i<locs1.size();i++){
                    
                        JPanel p1 = new JPanel();
                        JLabel s1 = new JLabel("Bank ID : "+locs1.get(0));
                        JLabel s2 = new JLabel("Balance : "+locs1.get(1));

                        p1.setLayout(new GridLayout(4, 1));
                        p1.add(s1);
                        p1.add(s2);
                        tResult.add("Your Balance "+(i+1),p1);

                    }
                    frame1.add(tResult,BorderLayout.PAGE_END);
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });


        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    tResult.removeAll();
                    ArrayList locs1 = c1.listBank();
                    //t1.setBounds(50, 150, 400, 300);
                    for(int i=0;i<locs1.size();i++){
                    
                        JPanel p1 = new JPanel();
                        JLabel s1 = new JLabel("Bank ID : "+locs1.get(0));
                        JLabel s2 = new JLabel("Balance : "+locs1.get(1));

                        p1.setLayout(new GridLayout(4, 1));
                        p1.add(s1);
                        p1.add(s2);
                        tResult.add("Your Balance "+(i+1),p1);

                    }
                    frame1.add(tResult,BorderLayout.PAGE_END);
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        b6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    tResult.removeAll();
                    Double amt = c1.utilCalc();
                    JPanel p1 = new JPanel();
                        JLabel s1 = new JLabel("Amount Due : "+amt);
                    p1.add(s1);
                    tResult.add(p1);
                    //t1.setBounds(50, 150, 400, 300);
                    frame1.add(tResult,BorderLayout.PAGE_END);
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        b5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    tResult.removeAll();
                    c1.makePayment();
                    JPanel p1 = new JPanel();
                        JLabel s1 = new JLabel("Utility bills payed");
                    p1.add(s1);
                    tResult.add(p1);
                    //t1.setBounds(50, 150, 400, 300);
                    frame1.add(tResult,BorderLayout.PAGE_END);
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        // frame1.add(b1);
        // frame1.add(b2);
        // frame1.add(b3);
        // frame1.add(b4);
        // frame1.add(b5);
        // frame1.add(b6);
        headingPanel.setVisible(true);
        frame1.add(headingPanel,BorderLayout.PAGE_START);
        buttonPanel.setVisible(true);
        frame1.add(buttonPanel,BorderLayout.CENTER);
        frame1.setVisible(true);

        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    
}

public class p1{
    public static void main(String[] args) throws IOException {
        ConnectionManager cnm1 = new ConnectionManager();
        Citizen c1 = new Citizen();
        CitizenView cv = new CitizenView();
        try {
            //cnm1.initiate();
            c1.initiate();
            c1.login();
            //c1.listLocs();
            //c1.utilCalc();
            //c1.listBank();
            //c1.makePayment();
            //c1.listBank();
            //c1 = cv.loginFrame();
            cv.mainFrame(c1);
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //cnm1.insertInitialValues("citizen");
        //cnm1.insertInitialValues("locations");
        //cnm1.insertInitialValues("bank");

    }
}