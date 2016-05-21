curl --anyauth --user root:root -X PUT -d@'./sessionSchema.xsd' \
    -H "Content-type: application/xml" \
    'http://localhost:8000/LATEST/documents?uri=/xml/session.xsd&database=Schemas'