curl --anyauth --user root:root -X PUT -d@'./actData.xml' \
    -H "Content-type: application/xml" \
    'http://localhost:8000/LATEST/documents?uri=/xml/acts/act1.xml&collection=xml.acts&database=xml-project-database'
