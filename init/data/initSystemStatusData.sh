curl --anyauth --user root:root -X PUT -d@'./systemStatusData.xml' \
    -H "Content-type: application/xml" \
    'http://localhost:8000/LATEST/documents?uri=/xml/systemStatus/systemStatus.xml&collection=xml.users&database=xml-project-database'