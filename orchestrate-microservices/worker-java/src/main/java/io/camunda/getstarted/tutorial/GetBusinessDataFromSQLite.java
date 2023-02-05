package io.camunda.getstarted.tutorial;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;

public class GetBusinessDataFromSQLite implements GetBusinessDataFromDB {

    @Override
    public HashMap<String, String> getBusinessSizeAndRelationship(String business_name) throws  SQLException {

        HashMap<String,String> businessClassification = new HashMap<>();
        Connection connection =null;
        Statement statement;
        String value_relationship = null;
        String value_size = null;

        // establish connection
        connection = DriverManager.getConnection("jdbc:sqlite:Database/BusinessRelationship.db");
        statement = connection.createStatement();
        statement.setQueryTimeout(30);
        // build query
        String sql_query = "SELECT * " +
                "           FROM business_relationship" +
                "           WHERE business_name = '"+business_name+"'";

        //handle result
        ResultSet result = statement.executeQuery(sql_query);
        value_relationship = result.getString("relationship_status");
        value_size = result.getString("business_size");

        businessClassification.put("relationship_status" , value_relationship);
        businessClassification.put("business_size" , value_size);

        if (connection != null) {
            connection.close();
        }

        return businessClassification;
    }
}
