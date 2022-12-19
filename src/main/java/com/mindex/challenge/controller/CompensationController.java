package com.mindex.challenge.controller;

import com.mindex.challenge.data.CompensationError;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;

    @PostMapping("/compensation")
    public ResponseEntity<Object> createCompensation(@RequestBody Compensation compensation) {
        LOG.debug("Received compensation create request for [{}]", compensation);

        try {
            Compensation compensationObject = compensationService.createCompensation(compensation);
            return ResponseEntity.ok(compensationObject);
        } catch (RuntimeException exception) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new CompensationError(exception.getMessage()));
        }
    }

    @GetMapping("/compensation/{employeeId}")
    public ResponseEntity<Object> readCompensation(@PathVariable String employeeId) {
        LOG.debug("Received compensation read request for employee id [{}]", employeeId);

        Compensation compensation = compensationService.readCompensation(employeeId);

        if (compensation != null) {
            return ResponseEntity.ok(compensation);
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new CompensationError("No Compensation found for EmployeeId"));
    }
}
