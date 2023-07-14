package com.example.vetau.helpers;


import java.sql.*;

public class Database {
    public static Connection connectionDB (){
        try{
            //Class.forName("com.mysql.jdbc.Driver");

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/TicketBookingManagement", "root", "");
            return  connection;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static ResultSet SELECT_ALL_FROM_TABLE(Connection connection,String TableName, String ColumnName)
    {
        String sql = "SELECT * "+ " FROM " + TableName + " WHERE " + ColumnName ;

        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);
            return resultSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }


    }
    public static ResultSet SELECT_String_FROM_TABLE(Connection connection,String TableName, String ColumnName,String Data)
    {
        String sql = "SELECT * "+ " FROM " + TableName + " WHERE " + ColumnName + " = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,Data);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }


    }
    public static ResultSet SELECT_INT_FROM_TABLE(Connection connection,String TableName, String ColumnName,int Data)
    {
        String sql = "SELECT *" + ColumnName + " FROM " + TableName + " WHERE " + ColumnName + " = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,Data);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }


    }
    public static ResultSet DELETE_String_FROM_TABLE(Connection connection,String TableName, String ColumnName,String Data)
    {
        String sql = "DELETE" + " FROM " + TableName + " WHERE " + ColumnName + " = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,Data);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }


    }
    public static ResultSet DELETE_INT_FROM_TABLE(Connection connection,String TableName, String ColumnName,int Data)
    {
        String sql = "DELETE" + " FROM " + TableName + " WHERE " + ColumnName + " = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,Data);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }


    }
    static void Insert() throws SQLException {
        Connection connection = Database.connectionDB();
        int count = 0;
        for (int i =1; i <= 5;i++) {
            String query1 = "SELECT Soluongtoa from tau WHERE ID_Tau = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query1);
                preparedStatement.setString(1,"TAU"+i);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    count = resultSet.getInt("Soluongtoa");
                    System.out.println(count);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        String query = "INSERT INTO toa_tau (ID_Toatau, ID_Tau, Soluongghe) VALUES (?, ?, ?)\n";
        for (int j = 6; j<=count; j++)
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,"TOA" + j + "TAU" + i);
            preparedStatement.setString(2,"TAU"+i);
            preparedStatement.setInt(3,20);
            preparedStatement.executeUpdate();
        }
        }
    }
    static void Update() throws SQLException {
        Connection connection = Database.connectionDB();
        int count = 0;
        String loaitoa = null;
        for (int i =1; i <= 5;i++) {
            String query1 = "SELECT Soluongtoa from tau WHERE ID_Tau = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query1);
                preparedStatement.setString(1,"TAU"+i);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    count = resultSet.getInt("Soluongtoa");
                    System.out.println(count);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            String query = "UPDATE toa_tau SET Loaitoa = ?\n" +
                          "WHERE (ID_Toatau = ? ) and (ID_Tau = ?)";
            for (int j = 1; j<=count; j++)
            {
                if (j <= 5)
                {
                    loaitoa = "Vip";
                }
                else {
                    loaitoa = "Thuong";
                }
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1,loaitoa);
                    preparedStatement.setString(2,"TOA"+j+"TAU"+i);
                    preparedStatement.setString(3,"TAU"+i);
                    System.out.println("TOA"+i+"TAU"+j + " " + loaitoa);
                    preparedStatement.executeUpdate();
            }
        }
    }
    public static void main(String[] args) {
//        Connection connection = Database.connectionDB();
//        String loaitoa = null;
//        PreparedStatement preparedStatement = null;
//        String query = "UPDATE toa_tau SET Loaitoa = ? " +
//                " WHERE (ID_Toatau = ? ) and (ID_Tau = ?)";
//
//        for (int i = 1; i<= 5; i++)
//        {
//
//            for (int j = 1; j<=5;j++)
//            {
//                if (j <= 3)
//                {
//                    loaitoa = "Vip";
//                }
//                else {
//                    loaitoa = "Thuong";
//                }
//                try {
//                    preparedStatement = connection.prepareStatement(query);
//                    preparedStatement.setString(1,loaitoa);
//                    preparedStatement.setString(2,"TOA"+j+"TAU"+i);
//                    preparedStatement.setString(3,"TAU"+i);
//                    System.out.println("TOA"+i+"TAU"+j + " " + loaitoa);
//                    preparedStatement.executeUpdate();
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
        try {
            Update();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
