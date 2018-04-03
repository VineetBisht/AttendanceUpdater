


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Vineet
 */
public class DefaultTest{

    public static void main(String ar[]){
     String url="jdbc:mysql://localhost:3306/satyam";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn=DriverManager.getConnection(url,"root",null);
            System.out.println("Database connection successful.");
            PreparedStatement prep=conn.prepareStatement("insert into dates"
                            + "(Name, Registration, FirstFee, FirstDate, SecondFee, SecondDate) values"
                             + "('n',? ,FALSE,NULL,FALSE,NULL)");
            Timestamp ts = new Timestamp(new java.util.Date().getTime());
            prep.setTimestamp(1,ts);
            
            prep.execute();
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection con=DriverManager.getConnection(url,"root",null);
            System.out.println("Database connection successful.");
            Statement stmt=con.createStatement();
            ResultSet rset=stmt.executeQuery("select * from dates WHERE Name='n'");     
            rset.next();
            
            System.out.println(rset.getTimestamp("Registration"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
           
    }
    
}
