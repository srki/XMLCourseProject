curl --anyauth --user root:root -X PUT -d@'./amendmentSchema.xsd' \
    -H "Content-type: application/xml" \
    'http://localhost:8000/LATEST/documents?uri=/xml/amendment.xsd&database=Schemas'
