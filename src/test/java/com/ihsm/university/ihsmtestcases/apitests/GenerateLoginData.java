package com.ihsm.university.ihsmtestcases.apitests;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GenerateLoginData {
    
    public static void main(String[] args) {
        
        String fileName =  "C://Automation//com.ihsm.university.project//src//test//resources//login_data.csv";
        int totalUsers = 40000;
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            
          
            writer.println("strEmail,strPassword,strFirebaseDeviceId,intSource");
            
            // Generate 40,000 rows here for the bulk upload test
            for (int i = 1; i <= totalUsers; i++) {
                String strEmail          = "user" + i ;
                String strPassword       = "123456";
                String strFirebaseDeviceId = "firebase_token_" + i;
                int    intSource         = 2;
                
                writer.println(strEmail + "," + 
                               strPassword + "," + 
                               strFirebaseDeviceId + "," + 
                               intSource);
            }
            
            System.out.println("Done! login_data.csv created with " + totalUsers + " rows.");
            
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}