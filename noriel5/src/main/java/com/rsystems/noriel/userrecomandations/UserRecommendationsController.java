package com.rsystems.noriel.userrecomandations;

import com.rsystems.com.utils.Constants;
import com.rsystems.noriel.jsoncsvconverter.JsonCsvConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
public class UserRecommendationsController {
    @Autowired
    private UserRecommendationsDAOService service;

    @RequestMapping("/saveDoc")
    String saveDoc() {
    	
    	
    	AzureConnServices azServices = new AzureConnServices();
    	
//============================= AZURE SAVE ============================ 
    	
    	try {
			azServices.saveAZFile("aaa33.csv");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
 
    	
        return "saved!";
        
    }
    
    @RequestMapping("/testVC")
    String hello() {
    	
    	
    	AzureConnServices azServices = new AzureConnServices();
    	
//============================= AZURE SAVE ============================ 
    	
    	try {
			azServices.saveAZFile("aaa11.csv");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
//============================= AZURE DOWNLOAD ============================
    	String fileContentStr = new String();
    	try {
    		
			fileContentStr = azServices.downloadAZFile("aaa.csv");
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
//============================= LOCAL ============================
    	try {
    		
			// azServices.downloadLocalFile("aaa.csv", "/Users/marius.stancalie/Downloads/aaa4.csv");
    	    azServices.downloadLocalFile("user_based_item_recommendation.csv", "/home/user_based_item_recommendation4.csv");
    	    azServices.downloadLocalFile("item_based_recommendation.csv", "/home/item_based_recommendation4.csv");
    	    azServices.downloadLocalFile("aaa.csv", "/home/aaa4.csv");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//    	
//    	BasicExample bs = new BasicExample();
//    	String fileContentStr = new String();
//    	try {
//			fileContentStr = bs.azureConn();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    	
    	
        return "Hello World! spring is here ULTIMATE: " + fileContentStr;
        
    }
    
    @RequestMapping("/findUser")
    public String findUser(@RequestParam(value = "userId", defaultValue = "101378654") String userId) {
    	
    	
    	
    	String s = new String();
    	
    	s = service.getUserProdIDs(userId, Constants.USER_RECOM);
    	
    	return s;
        
    }
    
    @PostMapping("/userRecommendations")
    public String createUser(@RequestBody User user) {
       // System.out.printf("user id = " + user.getEmailHash());
        return service.getRecommendationsByUser(user.getEmailHash());
    }

    @PostMapping("/userActionTypes")
    public void saveUserActionTypes(@RequestBody String jsonString) {
        try {
            JsonCsvConverter.JsonToCsv(jsonString, new File("d:/aaa.csv"));
//            AzureServices az = new AzureServices();
//            az.azureConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
