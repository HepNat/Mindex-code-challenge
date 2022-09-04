package com.mindex.challenge.dao;

import com.mindex.challenge.data.Employee;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {

    //Modified search to get the first employee by their effective date of compensation update. it's the only date in the table but I would prefer some kind of update date.
    Employee findFirstByEmployeeIdOrderByCompensationDesc(String employeeId);
}
