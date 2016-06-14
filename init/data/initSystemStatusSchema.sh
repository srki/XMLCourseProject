curl --anyauth --user root:root -X PUT -d@'./systemStatusSchema.xsd' \
    -H "Content-type: application/xml" \
    'http://localhost:8000/LATEST/documents?uri=/xml/systemStatus.xsd&database=Schemas'