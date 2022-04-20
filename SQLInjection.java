package cz.vsb.ekf.koj0010;

import java.util.Scanner;
import java.sql.*;

public class SQLInjection {
    public static final Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("Zadejte osobní číslo: ");
        String osCislo = sc.nextLine();
        System.out.println("Zadejte heslo: ");
        String heslo = sc.nextLine();
        
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            try (Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:64376;databaseName=ZkouskyData", "sa", "student");
                 Statement stat = con.createStatement();
                 ResultSet rs = stat.executeQuery(
                         "select OsCislo, Prijmeni, Jmeno "
                       + "from tblStudenti "
                       + "where OsCislo='" + osCislo + "' and Heslo='" + heslo + "'")
                    ) {
            
                if (rs.next()) {
                    System.out.println(rs.getString("OsCislo") + ", " + rs.getString("Prijmeni") + " " + rs.getString("Jmeno"));
                } else {
                    System.out.println("Nesprávné osobní číslo nebo heslo");
                }
                
            } catch (SQLException ex) {
                System.out.println("Nastal problém s databází: " + ex.getMessage());
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("Nebyl nalezen ovladač.");
        }
    }
    
}
