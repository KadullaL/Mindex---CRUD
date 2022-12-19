package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.data.ReportingStructureError;
import com.mindex.challenge.service.ReportingStructureService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureImplTest {
    private String employeeUrl;
    private String reportingStructureIdUrl;

    @Autowired
    private ReportingStructureService reportingStructureService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        reportingStructureIdUrl = "http://localhost:" + port + "/reporting-structure/{id}";
    }

    @Test
    public void testReadReportingStructure() {
        Employee testEmployee3 = new Employee();
        Employee createdTestEmployee3 = restTemplate.postForEntity(employeeUrl, testEmployee3, Employee.class).getBody();

        Employee testEmployee2 = new Employee();
        List<Employee> directReportsForTestEmployee2 = new ArrayList<>();
        directReportsForTestEmployee2.add(createdTestEmployee3);
        testEmployee2.setDirectReports(directReportsForTestEmployee2);

        Employee createdTestEmployee2 = restTemplate.postForEntity(employeeUrl, testEmployee2, Employee.class).getBody();

        Employee testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(createdTestEmployee2);
        testEmployee.setDirectReports(employeeList);

        // create employee
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();

        // Read ReportingStructure checks
        ReportingStructure readReportingStructure =
                restTemplate.getForEntity(reportingStructureIdUrl,ReportingStructure.class,
                        createdEmployee.getEmployeeId()).getBody();

        assertEquals(createdEmployee.getEmployeeId(), readReportingStructure.getEmployee().getEmployeeId());
        assertEquals(2, readReportingStructure.getNumberOfReports());
    }

    @Test
    public void testReadReportingStructureInvalidEmployeeError() {
        Employee testEmployee = new Employee();
        testEmployee.setEmployeeId("b7839309-3348-463b-a7e3-5de1c168beb31");
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");

        // Read ReportingStructure for invalid employee
        ReportingStructureError reportingStructureErrorMessage =
                restTemplate.getForEntity(reportingStructureIdUrl, ReportingStructureError.class,
                        testEmployee.getEmployeeId()).getBody();
        assertEquals("Invalid employeeId: " + testEmployee.getEmployeeId()
                , reportingStructureErrorMessage.getErrorMessage());
    }

}
