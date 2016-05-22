curl --anyauth --user root:root -X PUT -d@'./articleSchema.xsd' \
    -H "Content-type: application/xml" \
    'http://localhost:8000/LATEST/documents?uri=/xml/act.xsd&database=Schemas'
