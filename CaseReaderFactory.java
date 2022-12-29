
/**
 * 95-712 Homework 3
 * Name: Lakshay Sethi
 * Andrew ID: lsethi
 */


package hw3;

public class CaseReaderFactory {
	
	CaseReader createReader(String filename) {
		if (filename.contains(".tsv")) {
			TSVCaseReader reader = new TSVCaseReader(filename);
			return reader;
		}
		else {
			CSVCaseReader reader = new CSVCaseReader(filename);
			return reader;
		}
	}

}
