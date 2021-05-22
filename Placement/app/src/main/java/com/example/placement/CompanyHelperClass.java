package com.example.placement;

public class CompanyHelperClass {
    String companyID;
    String position;
    String Description;
    int free_positions;
    Boolean status;
    String name_of_company;

    public CompanyHelperClass() {
    }

    public CompanyHelperClass(String companyID, String name_of_company, String position, String description, int free_positions, Boolean status) {
        this.companyID = companyID;
        this.position = position;
        Description = description;
        this.free_positions = free_positions;
        this.status = status;
        this.name_of_company = name_of_company;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getName_of_company() {
        return name_of_company;
    }

    public void setName_of_company(String name_of_company) { this.name_of_company = name_of_company; }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getFree_positions() {
        return free_positions;
    }

    public void setFree_positions(int free_positions) {
        this.free_positions = free_positions;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
