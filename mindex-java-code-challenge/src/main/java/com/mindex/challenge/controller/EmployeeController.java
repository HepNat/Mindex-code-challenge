package com.mindex.challenge.controller;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.*;
import java.text.SimpleDateFormat;
import org.json.simple.JSONObject;

@RestController
public class EmployeeController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employee")
    public Employee create(@RequestBody Employee employee) {
        LOG.debug("Received employee create request for [{}]", employee);

        return employeeService.create(employee);
    }

    @GetMapping("/employee/{id}")
    public Employee read(@PathVariable String id) {
        LOG.debug("Received employee create request for id [{}]", id);

        return employeeService.read(id);
    }

    @PutMapping("/employee/{id}")
    public Employee update(@PathVariable String id, @RequestBody Employee employee) {
        LOG.debug("Received employee create request for id [{}] and employee [{}]", id, employee);

        employee.setEmployeeId(id);
        return employeeService.update(employee);
    }

    //Reporting Structure loops through each direct report and displays them to the user. Total number of reports is listed with the top employee from the search by id. Probably more confusing than it needs to be.
    @GetMapping("/employee/reportingStructure/{id}")
    public List<Employee> reportingStructure(@PathVariable String id) {
        LOG.debug("Getting reporting structure for id [{}]", id);

        int numberOfReports = 0;
        List<Employee> totalReports = new ArrayList<Employee>();
        Employee directReport = null;
        int directReportCount = 0;
        List<Employee> subReports = new ArrayList<Employee>();
        List<Employee> hasDirectReportsEmployee = new ArrayList<Employee>();
        boolean hasDirectReports = true;

        //Employee pulled from db by id
        Employee employee = employeeService.read(id);

        //All direct reports to this employee are pulled
        List<Employee> Reports = employee.getDirectReports();

        //Original employee that is searched added to list that will be outputted to the user
        totalReports.add(employee);
        if (Reports != null)
        {
          //Loop through all direct reports to find their direct reports
          for (int i = 0; i < Reports.size(); i++)
          {
            //Search by id of the direct reports
            directReport = employeeService.read(Reports.get(i).employeeId);
            totalReports.add(directReport);

            //Added report to count of reports for top employee
            numberOfReports++;

            //Helpful method created to get list of direct reports for current employee
            directReportCount = directReport.getNumberOfDirectReports();
            subReports = directReport.getDirectReports();
            if (directReportCount != 0)
            {

              //While the employee that is in the loop has direct reports under them
              while (hasDirectReports)
              {
                for (int a = 0; a < subReports.size(); a++)
                {
                  directReport = employeeService.read(subReports.get(a).employeeId);
                  totalReports.add(directReport);
                  numberOfReports++;
                  directReportCount = directReport.getNumberOfDirectReports();

                  //If the user still has direct reports under them add them to the original list of employees to be looped through
                  if (directReportCount != 0)
                  {
                    hasDirectReportsEmployee.add(directReport);
                  }
                }

                //If there are direct reports in the hasDirectReportsEmployee list then add to subReports list that is looped
                if (!hasDirectReportsEmployee.isEmpty())
                {
                  subReports = hasDirectReportsEmployee;
                }
                else
                {
                  //if there are no more direct reports then declare false and loop ends
                  hasDirectReports = false;
                }
              }
            }
          }
        }

        //Adds number of reports to employee that is printed but does not update to the persistence layer
        employee.setNumberOfReports(numberOfReports);
        return totalReports;
    }

    //Compensation read creates a json object of the ID, compensation, and effective date to return only the relevant information to the user
    @GetMapping("/employee/{id}/compensation")
    public JSONObject compensationRead(@PathVariable String id) {
        LOG.debug("Compensation Read for id [{}]", id);

        List<String> compensationData = new ArrayList<String>();
        JSONObject obj = new JSONObject();

        Employee employee = employeeService.read(id);

        obj.put("employeeId", employee.getEmployeeId());
        obj.put("compensation", String.valueOf(employee.getCompensation()));
        obj.put("effectiveDate", employee.getEffectiveDate());

        return obj;
    }

    //Compensation Update takes the ID and compensation value, searches the user, and updates the date and compensation amount
    @PutMapping("/employee/{id}/compensationUpdate/{value}")
    public Employee compensationUpdate(@PathVariable String id, @PathVariable int value) {
        LOG.debug("Received employee compensation update request for id [{}]", id);

        Employee employee = employeeService.read(id);

        //Puts current date and time into date
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        employee.setCompensation(value);
        employee.setEffectiveDate(formatter.format(date));

        return employeeService.update(employee);
    }
}
