curl --anyauth --user root:root -X PUT -d@'./userSchemaData.xsd' \
    -H "Content-type: application/xml" \
    'http://localhost:8000/LATEST/documents?uri=/xml/users.xsd&database=Schemas'
