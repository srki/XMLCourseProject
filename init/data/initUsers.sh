curl --anyauth --user rest-writer:writer -X PUT -d@'./userData.xml' \
    -H "Content-type: application/xml" \
    'http://localhost:8000/LATEST/documents?uri=/xml/users/xml-user.xml&collection=xml.users'
