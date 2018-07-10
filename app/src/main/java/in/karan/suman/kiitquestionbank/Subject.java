package in.karan.suman.kiitquestionbank;

/**
 * Created by Suman on 12-Oct-17.
 */

public class Subject {

    private String branch,year,subject;


    public Subject(){}
    public Subject(String branch, String year, String subject) {
        this.branch = branch;
        this.year = year;
        this.subject = subject;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
