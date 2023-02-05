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
            System.out.println("Calculate Prize");

            // get variables for price calculation
            Map<String, Object> incomingVariables = job.getVariablesAsMap();
            int price_modification_factor = (int) incomingVariables.get("chosen_pricemodel");
            int number_developers = (int) incomingVariables.get("InputData_devcount");
            String feature_complexity = (String) incomingVariables.get("InputData_com");


            // define costs
            double developer_cost = 1000.0;
            double complexity_bonus_s = 1000.0;
            double complexity_bonus_m = 2000.0;
            double complexity_bonus_l = 3000.0;


            // get complexity bonus
            double final_complexity_bonus = 0.0;
            if(feature_complexity.equals("S")) {final_complexity_bonus = complexity_bonus_s;}
            if(feature_complexity.equals("M")) {final_complexity_bonus = complexity_bonus_m;}
            if(feature_complexity.equals("L")) {final_complexity_bonus = complexity_bonus_l;}

            // price calculation
            double price_wo_modification = ((number_developers * developer_cost) + final_complexity_bonus);
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
      System.out.println(business_name);
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
      System.out.println(return_values);
      return return_values;
  }

    @JobWorker(type = "createTicket")
    public void createTicket(final ActivatedJob job){
      PMServerCommunication pmscomm = new PMServerCommunication();
          try {
              pmscomm.createTicket();
          } catch (IOException e) {
              e.printStackTrace();
          }
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
