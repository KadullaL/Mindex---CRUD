package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CompensationRepository compensationRepository;

    @Override
    public Compensation createCompensation(Compensation compensation) {
        String employeeId = compensation.getEmployeeId();

        LOG.debug("Creating compensation [{}] ", compensation,
                " for employee Id [{}] ", employeeId);

        Employee employeeObject = employeeRepository.findByEmployeeId(employeeId);

        if (employeeObject == null) {
            throw new RuntimeException("Invalid employeeId, " +
                    "please enter a valid employee ID to create the compensation " + employeeId);
        }

        Compensation compensationObject = compensationRepository.findCompensationByEmployeeId(employeeId);

        if(compensationObject==null){
            compensationRepository.save(compensation);
        }else {
            throw new RuntimeException("Compensation Already Exists");
        }

        return compensation;
    }

    @Override
    public Compensation readCompensation(String employeeId) {
        LOG.debug("Getting compensation for employee Id [{}]", employeeId);

        return compensationRepository.findCompensationByEmployeeId(employeeId);

////        }catch (Exception exception){
////            throw new RuntimeException("Exception thrown: " + exception.getMessage());
////        }
//
//        return compensation;
    }
}