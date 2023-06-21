package com.example.vetau.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public static Connection connectionDB (){
        try{
            //Class.forName("com.mysql.jdbc.Driver");

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/datvetau", "root", "trung1601");
            return  connection;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
