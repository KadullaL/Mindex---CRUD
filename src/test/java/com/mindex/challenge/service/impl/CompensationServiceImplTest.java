package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.CompensationError;
import com.mindex.challenge.service.CompensationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {
    private String compensationUrl;
    private String compensationIdUrl;

    @Autowired
    private CompensationService compensationService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        compensationUrl = "http://localhost:" + port + "/compensation";
        compensationIdUrl = "http://localhost:" + port + "/compensation/{employeeId}";
    }

    @Test
    public void testCompensationCreateReadSuccess() {
        Compensation testCompensation = new Compensation();
        testCompensation.setEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f");
        BigDecimal bigDecimal = new BigDecimal(100000);
        testCompensation.setSalary(bigDecimal);
        Date date = new Date();
        testCompensation.setEffectiveDate(date);

        // Create Compensation checks
        Compensation createdCompensation =
                restTemplate.postForEntity(compensationUrl, testCompensation, Compensation.class).getBody();
        assertNotNull(createdCompensation.getEmployeeId());
        assertCompensationEquivalence(testCompensation, createdCompensation);

        // Read Compensation checks
        Compensation readCompensation =
                restTemplate.getForEntity(compensationIdUrl, Compensation.class,createdCompensation.getEmployeeId()).getBody();
        assertEquals(createdCompensation.getEmployeeId(), readCompensation.getEmployeeId());
        assertCompensationEquivalence(createdCompensation, readCompensation);
    }

    @Test
    public void testCreateCompensationInvalidEmployeeError() {
        Compensation testCompensation = new Compensation();
        testCompensation.setEmployeeId("b7839309-3348-463b-a7e3-5de1c168beb3123");
        BigDecimal bigDecimal = new BigDecimal(100000);
        testCompensation.setSalary(bigDecimal);
        testCompensation.setEffectiveDate(new Date());

        // Create Compensation checks for invalid employeeId
        CompensationError createdCompensation =
                restTemplate.postForEntity(compensationUrl, testCompensation, CompensationError.class).getBody();
        assertEquals("Invalid employeeId, please enter a valid employee ID to create the compensation "
                + testCompensation.getEmployeeId(),createdCompensation.getErrorMessage());
    }

    @Test
    public void testCreateCompensationExistsError() {
        Compensation testCompensation = new Compensation();
        testCompensation.setEmployeeId("b7839309-3348-463b-a7e3-5de1c168beb3");
        BigDecimal bigDecimal = new BigDecimal(100000);
        testCompensation.setSalary(bigDecimal);
        testCompensation.setEffectiveDate(new Date());

        // Create Compensation checks
        Compensation createdCompensation =
                restTemplate.postForEntity(compensationUrl, testCompensation, Compensation.class).getBody();
        assertNotNull(createdCompensation.getEmployeeId());
        assertCompensationEquivalence(testCompensation, createdCompensation);

        // Create another Compensation checks
        CompensationError createdAnotherCompensation =
                restTemplate.postForEntity(compensationUrl, testCompensation, CompensationError.class).getBody();
        assertEquals("Compensation Already Exists",createdAnotherCompensation.getErrorMessage());
    }

    @Test
    public void testCompensationNotExistsReadError() {
        Compensation testCompensation = new Compensation();
        testCompensation.setEmployeeId("b7839309-3348-463b-a7e3-5de1c168beb31");
        BigDecimal bigDecimal = new BigDecimal(100000);
        testCompensation.setSalary(bigDecimal);
        testCompensation.setEffectiveDate(new Date(1622433600));

        // Read Compensation checks for invalid employeeId
        CompensationError createdCompensation =
                restTemplate.getForEntity(compensationIdUrl, CompensationError.class,testCompensation.getEmployeeId()).getBody();
        assertEquals("No Compensation found for EmployeeId",createdCompensation.getErrorMessage());
    }
    private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
        assertEquals(expected.getEmployeeId(), actual.getEmployeeId());
        assertEquals(expected.getSalary(), actual.getSalary());
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
    }
}
