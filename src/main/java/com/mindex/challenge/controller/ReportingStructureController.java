package com.mindex.challenge.controller;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.data.ReportingStructureError;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportingStructureController {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureController.class);
    @Autowired
    private ReportingStructureService reportingStructureService;

    // Task 1 : to get the reporting structure
    @GetMapping("/reporting-structure/{employeeId}")
    public ResponseEntity<Object> readReportingStructure(@PathVariable String employeeId) {
        LOG.debug("Received reporting structure request for employee id [{}]", employeeId);

        try {
            ReportingStructure reportingStructure = reportingStructureService.readReportingStructure(employeeId);
            return ResponseEntity.ok(reportingStructure);
        } catch (RuntimeException exception) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ReportingStructureError(exception.getMessage()));
        }
    }
}
