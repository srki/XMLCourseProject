declare namespace mlt = 'http://ftn.uns.ac.rs/xml';
declare variable $document_uri as xs:string external;
declare variable $username as xs:string external;

declare variable $result :=
    try {
        if(data(doc("/xml/systemStatus/systemStatus.xml")/mlt:systemStatus/mlt:status) eq "amendment" and data(xdmp:document-get-properties("/xml/amendments/" || $document_uri, QName('', 'username'))) eq $username) then
            xdmp:document-delete("/xml/amendments/" || $document_uri)
        else "NOT OK"
    } catch * {
        "NOT OK"
    };

$result