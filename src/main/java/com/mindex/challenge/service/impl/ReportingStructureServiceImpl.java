package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ReportingStructure readReportingStructure(String id) {
        LOG.debug("Creating employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        int totalReporters = findNumOfReporters(employee);
        ReportingStructure reportingStructure = new ReportingStructure(employee,totalReporters);

        return reportingStructure;
    }

    private int findNumOfReporters(Employee employee) {
        List<Employee> directReports = employee.getDirectReports();
        if(directReports==null){
            return 0;
        }

        int indirectReports = 0;
        for(Employee emp : directReports){
            Employee empObject = employeeRepository.findByEmployeeId(emp.getEmployeeId());
            int directReporters = findNumOfReporters(empObject);
            indirectReports = indirectReports +directReporters;
        }

        return directReports.size() + indirectReports;
    }
}
