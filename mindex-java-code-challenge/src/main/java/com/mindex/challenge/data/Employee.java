package com.mindex.challenge.data;

import java.util.List;

public class Employee {
    public String employeeId;
    private String firstName;
    private String lastName;
    private String position;
    private String department;
    private int numberOfReports;
    private int compensation;
    private String effectiveDate;
    private List<Employee> directReports;

    public Employee() {
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<Employee> getDirectReports() {
        return directReports;
    }

    public void setDirectReports(List<Employee> directReports) {
        this.directReports = directReports;
    }

    //Lists the number of direct reports only. If null returns a 0
    public int getNumberOfDirectReports() {
      return (directReports == null) ? 0 : directReports.size();
    }

    //Defines the number of reports. Only used for printing to the api
    public void setNumberOfReports(int numberOfReports) {
      this.numberOfReports = numberOfReports;
    }

    public int getNumberOfReports() {
      return numberOfReports;
    }

    public void setCompensation(int compensation) {
      this.compensation = compensation;
    }

    public int getCompensation() {
      return compensation;
    }

    public void setEffectiveDate(String effectiveDate) {
      this.effectiveDate = effectiveDate;
    }

    public String getEffectiveDate() {
      return effectiveDate;
    }
}
