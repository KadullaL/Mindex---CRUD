Coding Challenge Solution

TASK 1

### Implementation
This new endpoint accepts an employeeId and return the fully filled out ReportingStructure
for the specified employeeId. DAO has not been defined for reporting structure as mentioned 
in the readme that the values are computed on the fly and will not be persisted.

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

TASK2

### Implementation
A Compensation has the following fields: employee, salary, and effectiveDate.
A compensation DAO has been created as mentioned in the readme that these should persist
and query the Compensation from the persistence layer.

It has two new Compensation REST endpoints:
1. To create by employeeId. This is an idempotent operation. As it wasn't mentioned in the readme file
   if we should allow to create compensation multiple times or just once, I have made it idempotent so that compensation should
   only be allowed to create once. In future another API could be updated to create multiple compensation if it is required
   or additionally a new api update could be added to update an existing compensation.
   
2. To read by employeeId


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
      "type": "integer"
    },
    "effectiveDate": {
      "type": "date"
    }
  }
}
```
Sample Payload for create Compensation
```
{
	"employeeId" : "16a596ae-edd3-4847-99fe-c4518e82c86f",
	"salary" : "100000",
	"effectiveDate" : "2023/10/03"
}
```