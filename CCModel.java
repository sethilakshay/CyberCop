
/**
 * 95-712 Homework 3
 * Name: Lakshay Sethi
 * Andrew ID: lsethi
 */

package hw3;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;


public class CCModel {
	ObservableList<Case> caseList = FXCollections.observableArrayList(); 			//a list of case objects
	ObservableMap<String, Case> caseMap = FXCollections.observableHashMap();		//map with caseNumber as key and Case as value
	ObservableMap<String, List<Case>> yearMap = FXCollections.observableHashMap();	//map with each year as a key and a list of all cases dated in that year as value. 
	ObservableList<String> yearList = FXCollections.observableArrayList();			//list of years to populate the yearComboBox in ccView

	/** readCases() performs the following functions:
	 * It creates an instance of CaseReaderFactory, 
	 * invokes its createReader() method by passing the filename to it, 
	 * and invokes the caseReader's readCases() method. 
	 * The caseList returned by readCases() is sorted 
	 * in the order of caseDate for initial display in caseTableView. 
	 * Finally, it loads caseMap with cases in caseList. 
	 * This caseMap will be used to make sure that no duplicate cases are added to data
	 * @param filename
	 */
	void readCases(String filename) {
		//write your code here
		
	    /**
	     * Reading in the data and sorting it
	     */
		CaseReaderFactory reader = new CaseReaderFactory();
		CaseReader case_reader = reader.createReader(filename);
		caseList = FXCollections.observableArrayList(case_reader.readCases());
		Collections.sort(caseList);
		
		/**
		 * Building case Map
		 */
		for(int i=0; i<caseList.size(); i++){
		    caseMap.put(caseList.get(i).getCaseNumber(), caseList.get(i));
		}
		
	}

	/** buildYearMapAndList() performs the following functions:
	 * 1. It builds yearMap that will be used for analysis purposes in Cyber Cop 3.0
	 * 2. It creates yearList which will be used to populate yearComboBox in ccView
	 * Note that yearList can be created simply by using the keySet of yearMap.
	 */
	void buildYearMapAndList() {
		//write your code here
	    
	    /**
	     * Method to get yearList
	     */
	    for(int i=0; i<caseList.size(); i++){
	        String[] tempDate =  caseList.get(i).getCaseDate().split("-");	        
	        if(!yearList.contains(tempDate[0])) {
	            yearList.add(tempDate[0]);
	        }
	    }
	    
	    
	    
	    /**
	     * Method to Build the yearMap
	     */
	    for(int i=0; i<yearList.size(); i++){
	        ObservableList<Case> tempList = FXCollections.observableArrayList();
	        
	        for(int j=0; j<caseList.size(); j++) {
	            String[] tempDate =  caseList.get(j).getCaseDate().split("-");
	            
	            if(yearList.get(i).equals(tempDate[0])) {
	                tempList.add(caseList.get(j));
	            }
	        }
	        
	        yearMap.put(yearList.get(i), tempList);
        }
	}

	/**searchCases() takes search criteria and 
	 * iterates through the caseList to find the matching cases. 
	 * It returns a list of matching cases.
	 */
	List<Case> searchCases(String title, String caseType, String year, String caseNumber) {
		//write your code here
	    
	    ObservableList<Case> searchList = FXCollections.observableArrayList(); 

        for(int i=0; i<caseList.size(); i++){
            
            String tempYear = caseList.get(i).toString();
            String tempCaseTitle = caseList.get(i).getCaseTitle();
            String tempCaseType = caseList.get(i).getCaseType();
            String tempCaseNum = caseList.get(i).getCaseNumber();

            if((title == null || tempCaseTitle.toLowerCase().contains(title.toLowerCase())) &&
                    (year == null || tempYear.equals(year)) &&
                    (caseType == null || tempCaseType.toLowerCase().contains(caseType.toLowerCase())) &&
                    (caseNumber == null || tempCaseNum.contains(caseNumber))) {
                
                searchList.add(caseList.get(i));
            }
        }
		return searchList;
	}
	
	boolean writeCases(String filename){
	    
        try (BufferedWriter outFile = new BufferedWriter(new FileWriter(filename))) {
            for(Case c: caseList) {
                outFile.write(c.getCaseDate() + " \t" + 
                              c.getCaseTitle() + " \t" + 
                              c.getCaseType() + " \t" + 
                              c.getCaseNumber() + " \t" + 
                              c.getCaseLink() + " \t" + 
                              c.getCaseCategory() + " \t" + 
                              c.getCaseNotes() + " \t");
                outFile.newLine();
            }
            outFile.flush();
            outFile.close();
            return true;
        }
	    catch (IOException e){
	        System.out.println("Error saving the file");
	        e.printStackTrace();
	        return false;
	    }
        catch (Exception e){
            System.out.println("Error saving the file");
            e.printStackTrace();
            return false;
        }
	}
}
