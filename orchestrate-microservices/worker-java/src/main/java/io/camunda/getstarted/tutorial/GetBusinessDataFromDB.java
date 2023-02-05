package io.camunda.getstarted.tutorial;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public interface GetBusinessDataFromDB {

    public HashMap<String, String> getBusinessSizeAndRelationship(String business_name) throws SQLException;

}
