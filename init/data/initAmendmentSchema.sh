curl --anyauth --user root:root -X PUT -d@'./amandmentSchema.xsd' \
    -H "Content-type: application/xml" \
    'http://localhost:8000/LATEST/documents?uri=/xml/amandment.xsd&database=Schemas'
