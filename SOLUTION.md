Coding Challenge Solution

### TASK 1

### Implementation
ReportingStructure, has two properties: employee and numberOfReports.

It has one REST API below:
1. To read by employeeId
   * This new endpoint accepts an employeeId and return the fully filled out ReportingStructure
     for the specified employeeId. This would throw en error message for an invalid employeeId.
   * I haven't added DAO for the reporting structure as it was mentioned in the readme that 
     the values are computed on the fly and will not be persisted.

### How to Use
The following endpoints are available to use:
```
* READ
    * HTTP Method: GET
    * URL: localhost:8080/reporting-structure/{employeeId}
    * RESPONSE: ReportingStructure
```
The ReportingStructure has a JSON schema of:
```json
{
  "type":"ReportingStructure",
  "properties": {
    "employee": {
      "type": "Employee"
    },
    "numberOfReports": {
      "type": "integer"
    }
  }
}
```

### TASK 2

### Implementation
A Compensation has the following fields: employee, salary, and effectiveDate. 
As it was mentioned in the readme file that these should persist and to query the Compensation from the
persistence layer, I have added a compensation DAO. After a compensation is created, it is added into the
compensation entity which can be read later using the read REST API by employeeId.

It has two new Compensation REST endpoints:
1. To create by employeeId
   * This is an idempotent operation. As it wasn't mentioned in the readme file if we should allow to 
   create compensation multiple times or just once, I have made it idempotent so that compensation should
   only be allowed to create once. An error is thrown if compensation is created more than once.
   * An error is thrown if an invalid employeeId is provided.
   * In the future, if its required to create multiple compensation, the existing compensation CREATE REST API
   could be updated to allow multiple compensations by adding additional filed "CompensationId" in the Compensation 
   class.
   * Additionally, if there is a requirement to update an existing compensation, a new REST API UPDATE could
   be added to update an existing compensation.
   
2. To read by employeeId
   This REST API would take an employeeId and will return the Compensation for the provided Id.
   If employeeId is not valid, this would return an error message to provide a valid employeeId.


### How to Use
The following endpoints are available to use:
```
* CREATE
    * HTTP Method: POST
    * URL: localhost:8080/compensation
    * PAYLOAD: Compensation
    * RESPONSE: Compensation
* READ
    * HTTP Method: GET
    * URL: localhost:8080/compensation/{employeeId}
    * RESPONSE: Compensation
```

The Compensation has a JSON schema of:
```json
{
  "type":"Compensation",
  "properties": {
    "employeeId": {
      "type": "string"
    },
    "salary": {
      "type": "number"
    },
    "effectiveDate": {
      "type": "date"
    }
  }
}
```
Sample Input Payload for create Compensation
```
{
	"employeeId" : "16a596ae-edd3-4847-99fe-c4518e82c86f",
	"salary" : "100000",
	"effectiveDate" : "2023-10-03"
}
```

### Test Cases
I have added the test cases inside the test folder. Test cases include test for both success and failure scenarios.
Reporting Structure tests are written in the ReportingStructureImplTest class and Compensation tests are written
in the CompensationServiceImplTest class.