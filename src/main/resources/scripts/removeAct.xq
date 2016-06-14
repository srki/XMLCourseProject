declare namespace mlt = 'http://ftn.uns.ac.rs/xml';
declare variable $uri as xs:string external;
declare variable $username as xs:string external;

declare variable $result :=
try {
    if(data(doc("/xml/systemStatus/systemStatus.xml")/mlt:systemStatus/mlt:status) eq "act" and data(xdmp:document-get-properties("/xml/acts/" || $uri, QName('', 'username'))) eq $username) then
        xdmp:document-delete("/xml/acts/" || $uri)
    else "NOT OK"
} catch * {
    "NOT OK"
};

$result