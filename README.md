dropwizard-mongo
================


Simple dropwizard-mongo demo. Test adding a patient with:

curl -X PUT localhost:8080/patient/ -v --header "Content-Type: application/json" -d' {
    "data" : {
        "patientId" : "1",
        "firstName": "John",
        "lastName": "Smith",
        "dob": "1983-08-03"
    }
}'
