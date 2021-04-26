package com.rsystems.noriel.userrecomandations;


import com.rsystems.com.utils.Constants;
import com.rsystems.noriel.parsing.Parser;
import com.rsystems.noriel.parsing.ParsingEvents;
import com.rsystems.noriel.parsing.StringAction;
import com.rsystems.noriel.parsing.StringActionMod;
import com.rsystems.noriel.jsoncsvconverter.CsvSearch;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class UserRecommendationsDAOService implements ParsingEvents{

    public static final int INDEX_PRODUCT_ID = 1;
    public static final int ACTION_TYPE = 0;
    private static List<String[]> recommendations;
    private static List<String[]> userActions;

    
    
    
    public String getUserProdIDs (String userId, String fileLocalPath) {
    	String userprodIDs = new String();
    	 
    	recommendations = CsvSearch.searchCsv(new File(fileLocalPath), userId, "PartnerId", false);
        AtomicReference<List<String[]>> similarProductIds = new AtomicReference<>();
        List firstElem = Arrays.asList(recommendations.get(0));
        
        System.out.println("FE" + firstElem.toString());
        
        userprodIDs = (String) firstElem.get(INDEX_PRODUCT_ID);	
        
        Parser parser = new Parser();
        
        userprodIDs = parser.extract("[", "]", userprodIDs, actionFirst.SKIP, actionLast.SKIP);
        userprodIDs = ", " + userprodIDs + ", ";
        
        System.out.println("USERPROD ID " + userprodIDs);
        
        CircularFifoQueue<String> userProductIDs = new CircularFifoQueue<>(12);
        
        parser.first = -1;
        
        while (parser.seek(", ", userprodIDs, direction.FORWARD, actionLast.NOTHING) != -1) {
        	
        	String s = parser.extract(" ", ",", userprodIDs, actionFirst.SKIP, actionLast.SKIP);
        	System.out.println("ELEMENT " + s);
        	userProductIDs.offer(s);
        	
        }
        
        
       
        System.out.println("QUEUE " + userProductIDs.toString());
        
        userProductIDs.offer("0000");
        userProductIDs.offer("1111");
        userProductIDs.offer("2222");
        
        System.out.println("QUEUE " + userProductIDs.toString());
        
    	
    	return userprodIDs;
    }
    
    public String getRecommendationsByUser(String emailHash) {
        recommendations = CsvSearch.searchCsv(new File(Constants.USER_RECOM), emailHash, "PartnerId", false);
        // recommendations.forEach(s -> System.out.println(Arrays.toString((String[]) s)));
        final List<String[]> similarProductIds = new ArrayList<>();
        List firstElem = Arrays.asList(recommendations.get(0));
        String recommendIds = (String) firstElem.get(INDEX_PRODUCT_ID);
        //System.out.println("lista = " + recommendIds);
        userActions = CsvSearch.searchCsv(new File(Constants.ACTION_PATH), emailHash, "emailHash", true);
//        List<String[]> userActionsFiltered = userActions.stream().filter(c -> {
//            try {
//                Date xxx = new SimpleDateFormat("yyyy-MM-dd").parse(c[3]);
//                long currenttime = System.currentTimeMillis() - 15 * 60 * 1000;
//                return xxx.getTime() > currenttime;
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            return false;
//        }).collect(Collectors.toList());

        userActions.forEach(s -> {
            Date actionTimestamp = null;
            try {
                actionTimestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(s[3]);

                long lastPeriodMinTimestamp = System.currentTimeMillis() - 360 * 60 * 1000;
                if (actionTimestamp.getTime() > lastPeriodMinTimestamp) {
                    switch (s[ACTION_TYPE]) {
                        case "addToCart":
                        case "viewProduct": {
                            similarProductIds.addAll(CsvSearch.searchCsv(new File(Constants.PROD_RECOM), s[2], "ItemId", false));
                            break;
                        }
                        case "removeFromCart": {
                            Iterator i = similarProductIds.iterator();
                            String[] str;
                            while (i.hasNext()) {
                                str = (String[]) i.next();
                                if (str[0].equals(s[2])) {
                                    i.remove();
                                    break;
                                }
                            }
//                            boolean isInTheList = similarProductIds.contains(CsvSearch.searchCsv(new File("d:/item_based_recommendation.csv"), s[2], "ItemId", false));
//                            similarProductIds.removeAll(CsvSearch.searchCsv(new File("d:/item_based_recommendation.csv"), s[2], "ItemId", false));
                            break;

                        }


                    }
                }
                System.out.println(Arrays.toString((String[]) s));


            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        similarProductIds.forEach(a -> System.out.println(Arrays.toString((String[]) a)));
        for (int i=0;i< similarProductIds.size();i++){
            List newLine = Arrays.asList(similarProductIds.get(i));
            String newIds = (String) newLine.get(1);
            recommendIds += ',' + newIds;
            recommendIds.replaceAll("],",",");


        }
       // List firstLine = Arrays.asList(similarProductIds.get(0));


        return recommendIds;
    }

}
