curl --anyauth --user root:root -X PUT -d@'./sessionData.xml' \
    -H "Content-type: application/xml" \
    'http://localhost:8000/LATEST/documents?uri=/xml/sessions/session.xml&collection=xml.users&database=xml-project-database'
