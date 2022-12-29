
/**
 * 95-712 Homework 3
 * Name: Lakshay Sethi
 * Andrew ID: lsethi
 */

package hw3;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Case implements Comparable<Case> {
	
	private StringProperty caseDate;
	private StringProperty caseTitle;
	private StringProperty caseType;
	private StringProperty caseNumber;
	private StringProperty caseLink;
	private StringProperty caseCategory;
	private StringProperty caseNotes;
	
	Case(String caseDate, String caseTitle, String caseType, String caseNumber, String caseLink, String caseCategory, String caseNotes){
		this.caseDate = new SimpleStringProperty(caseDate);
		this.caseTitle = new SimpleStringProperty(caseTitle);
		this.caseType = new SimpleStringProperty(caseType);
		this.caseNumber = new SimpleStringProperty(caseNumber);
		this.caseLink = new SimpleStringProperty(caseLink);
		this.caseCategory = new SimpleStringProperty(caseCategory);
		this.caseNotes = new SimpleStringProperty(caseNotes);
	}
	
	//Getters and Setters for caseDate
	public final String getCaseDate() {
		return caseDate.get();
	}
	
	public final void setCaseDate(String date) {
		caseDate.set(date);
	}
	
	public final StringProperty caseDateProperty() {
	    return caseDate;
	}
	
	
	//Getters and Setters for caseTitle
	public final String getCaseTitle() {
		return caseTitle.get();
	}
	
	public final void setCaseTitle(String title) {
		caseTitle.set(title);
	}
	
	public final StringProperty caseTitleProperty() {
        return caseTitle;
    }
	
	
	//Getters and Setters for caseType
	public final String getCaseType() {
		return caseType.get();
	}

	public final void setCaseType(String type) {
		caseType.set(type);
	}
	
	public final StringProperty caseTypeProperty() {
        return caseType;
    }
	
	
	//Getters and Setters for caseType
	public final String getCaseNumber() {
		return caseNumber.get();
	}

	public final void setCaseNumber(String number) {
		caseNumber.set(number);
	}
	
	public final StringProperty caseNumberProperty() {
        return caseNumber;
    }
	
	
	//Getters and Setters for caseType
	public final String getCaseLink() {
		return caseLink.get();
	}

	public final void setCaseLink(String link) {
		caseLink.set(link);
	}
	
	public final StringProperty caseLinkProperty() {
        return caseLink;
    }
	
	
	//Getters and Setters for caseCategory
	public final String getCaseCategory() {
		return caseCategory.get();
	}

	public final void setCaseCategory(String category) {
		caseCategory.set(category);
	}
	
	public final StringProperty caseCategoryProperty() {
        return caseCategory;
    }
	
	
	//Getters and Setters for caseNotes
	public final String getCaseNotes() {
		return caseNotes.get();
	}

	public final void setCaseNotes(String notes) {
		caseNotes.set(notes);
	}
	
	public final StringProperty caseNotesProperty() {
        return caseNotes;
    }
	
	@Override
    public int compareTo(Case o) {
        // TODO Auto-generated method stub
        return o.caseDate.toString().compareTo(this.caseDate.toString());
    }
	
	@Override
	//Special for the combobox
	public String toString() {
	    return caseDate.get().split("-")[0];
	}
}
