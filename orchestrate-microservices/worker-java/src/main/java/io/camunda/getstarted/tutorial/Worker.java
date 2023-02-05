package io.camunda.getstarted.tutorial;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.EnableZeebeClient;
import io.camunda.zeebe.spring.client.annotation.ZeebeWorker;
import scala.Int;

import java.sql.*;

@SpringBootApplication
@EnableZeebeClient
public class Worker {

  public static void main(String[] args) {
    SpringApplication.run(Worker.class, args);
  }

  
  @JobWorker(type = "orchestrate-something")
        public Map<String, Object> orchestrateSomething(final ActivatedJob job) {

          // Do the business logic
          System.out.println("Yeah, now you can orchestrate something :-) You could use data from the process variables: " + job.getVariables());

          // Probably add some process variables
          HashMap<String, Object> variables = new HashMap<>();
          variables.put("resultValue1", 42);
          return variables;
      }

      @JobWorker(type = "calc_prize")
      public Map<String, Object> calcPrize(final ActivatedJob job) {

            // Do the business logic
            System.out.println("Price calculation");

            // get variables for price calculation
            Map<String, Object> incomingVariables = job.getVariablesAsMap();
            int price_modification_factor = (int) incomingVariables.get("chosen_pricemodel");
            int number_developers = (int) incomingVariables.get("InputData_devcount");
            String feature_complexity = (String) incomingVariables.get("InputData_com");
            int working_days = (int) incomingVariables.get("working_days");


            // define costs
            double developer_cost = 1000.0; // per working day


            // price calculation
            double price_wo_modification = (number_developers * working_days * developer_cost);
            double price_modification = price_wo_modification * ( (double) price_modification_factor/100);
            double finale_price = price_wo_modification + price_modification;


            // Probably add some process variables
            HashMap<String, Object> variables = new HashMap<>();
            variables.put("price", finale_price);
            return variables;
      }

    @JobWorker(type = "get_business_data")
    public HashMap<String,String> get_business_relationship(final ActivatedJob job){

      HashMap<String,String> return_values = new HashMap<>();

      // get variables for price calculation
      Map<String, Object> incomingVariables = job.getVariablesAsMap();
      String business_name = (String) incomingVariables.get("business_name");

      //get relationship status relativ to business_name from database
      try
      {
          GetBusinessDataFromSQLite getBusinessData = new GetBusinessDataFromSQLite();
          return_values = getBusinessData.getBusinessSizeAndRelationship(business_name);

      }
      catch( SQLException e)
      {
          System.err.println(e);
      }

      return return_values;
  }

    @JobWorker(type = "createTicket")
    public HashMap<String, Integer> createTicket(final ActivatedJob job){
      // intialize
      HashMap<String, Integer> return_value = new HashMap<>();
      PMServerCommunication pmscomm = new PMServerCommunication();
      // get variables for price calculation
      Map<String, Object> incomingVariables = job.getVariablesAsMap();
      String title = (String) incomingVariables.get("feature_request_text");
      int ticketId = 0;
      try {
             ticketId =  pmscomm.createTicket(title);
          } catch (IOException e) {
              e.printStackTrace();
          }

      return_value.put("ticketId", ticketId);
      return return_value;
      }

    @JobWorker(type = "deleteTicket")
    public void deleteTicket(final ActivatedJob job){
        PMServerCommunication pmscomm = new PMServerCommunication();
        try {
            pmscomm.deleteTicket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
