package io.camunda.getstarted.tutorial;

import java.sql.*;

public class CreateSQLiteDB_BusinessClassification {

    public static void main(String[] args) {

        // initialization

        Connection connection = null;
        Statement statement;

        // Data

        String customer_relationship_new = "New";
        String customer_relationship_existing = "Existing";
        String customer_relationship_veryimportantbusiness = "VIB";

        String business_name_one = "Business 1";
        String business_name_two = "Business 2";
        String business_name_three = "Business 3";

        String business_size_small ="Small";
        String business_size_medium = "Medium";
        String business_size_large = "Large";

        // create connection


        
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:Database/BusinessRelationship.db");
                statement = connection.createStatement();
                statement.setQueryTimeout(30);
                // reset table
                statement.executeUpdate("drop table if exists business_relationship");
                // create Table
                String sql_statement_create_table = "CREATE TABLE " +
                        "               business_relationship (" +
                        "               business_name string, " +
                        "               relationship_status string," +
                        "               business_size string" +
                        "               )";
                statement.executeUpdate(sql_statement_create_table);
                // fill table
                statement.executeUpdate("insert into business_relationship" +
                        "                    values (" +
                        "                    '" + business_name_one + "'," +
                        "                    '" + customer_relationship_existing + "'," +
                        "                    '" + business_size_medium + "') ");
                statement.executeUpdate("insert into business_relationship" +
                        "                    values (" +
                        "                    '" + business_name_two + "'," +
                        "                    '" + customer_relationship_veryimportantbusiness + "'," +
                        "                    '" + business_size_large + "') ");
                statement.executeUpdate("insert into business_relationship" +
                        "                    values (" +
                        "                    '" + business_name_three + "'," +
                        "                    '" + customer_relationship_new + "'," +
                        "                    '" + business_size_small + "') ");

                // print to console for validation
                ResultSet rs = statement.executeQuery("select * from business_relationship");
                while (rs.next()) {
                    // read the result set
                    System.out.println("name = " + rs.getString("business_name"));
                    System.out.println("relationship = " + rs.getString("relationship_status"));
                    System.out.println("size = " + rs.getString("business_size"));
                }

            } 
            catch (SQLException e) 
            {
                e.printStackTrace();
            }
            finally 
            {
                try {
                    if (connection != null)
                    {
                        connection.close();
                    }
                } 
                catch (SQLException e)
                {
                    System.err.println(e.getMessage());
                }
            }
        }
    }
