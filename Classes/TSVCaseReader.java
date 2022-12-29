
/**
 * 95-712 Homework 3
 * Name: Lakshay Sethi
 * Andrew ID: lsethi
 */

package hw3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TSVCaseReader extends CaseReader {

	TSVCaseReader(String filename) {
		super(filename);
	}

	@Override
	List<Case> readCases() {
	    int cntExceptions = 0;
	    
		//Reading the input as an array
		Scanner input = null;	//Initializing input to null
		try {
			input = new Scanner(new File(filename));
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		StringBuilder stringData = new StringBuilder();
		while (input.hasNextLine()) {
			stringData.append(input.nextLine() + "\n");
		}
		String[] fileData = stringData.toString().split("\n");
		
		List<Case> caseList = new ArrayList<>();
		//Checking to see how many fields are there
		for(int i =0; i<fileData.length; i++) {
		    
			String[] temp = fileData[i].split("\t"); //Assigning the array temp by splitting fileData lines on the basis of Tabs

			//Exception checking for date
			String date = "";
			if(temp[0] == null || temp[0].isBlank()) {
			    cntExceptions++;
			    continue;
			}else {
			    date = temp[0].trim();
			}
			
			//Exception checking for title
			String title = "";
			if(temp[1] == null || temp[1].isBlank()) {
			    cntExceptions++;
                continue;
            }else {
                title = temp[1].trim();
            }
			
			//Exception checking for type
			String type = "";
			if(temp[2] == null || temp[2].isBlank()) {
			    cntExceptions++;
                continue;
            }else {
                type = temp[2].trim();
            }
			
			//Exception checking for case number
			String case_num = "";
			if(temp[3] == null || temp[3].isBlank()) {
			    cntExceptions++;
                continue;
            }else {
                case_num = temp[3].trim();
            }
			
			String case_link = "";
			String category = "";
			String notes = "";
			

			if(temp[4] != null) {
				case_link = temp[4].trim();
			}
			
			if(temp[5] != null) {
				category = temp[5].trim();
			}
			
			if(temp[6] != null) {
				notes = temp[6].trim();
			}
			
			Case c = new Case(date, title, type, case_num, case_link, category, notes);
			caseList.add(c);
		}
		
		
		//HW3 new addition
		try {
		    String message = Integer.toString(cntExceptions) + " cases rejected.\n" + "The file must have cases with\n " + "tab separated date, title, type, and case number!";
		    if(cntExceptions > 0) {
		        throw new DataException(message);
		    }
		}
		catch (DataException e){
		    System.out.println("Caught improper data in TSVCaseReader");
		    e.showAlert();
		}
		return caseList;
	}
}
