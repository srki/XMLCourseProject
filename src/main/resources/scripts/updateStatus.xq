import schema "http://ftn.uns.ac.rs/xml" at "/xml/systemStatus.xsd";
declare namespace mlt = "http://ftn.uns.ac.rs/xml";

declare variable $systemStatus_string as xs:string external;
declare variable $systemStatus := xdmp:unquote($systemStatus_string);
declare variable $errors := xdmp:validate($systemStatus, "strict");
declare variable $validation_error := if (exists($errors//error:error)) then "NOT OK" else "OK";
declare variable $is_not_systemStatus_error := if (name($systemStatus/mlt:systemStatus) eq "systemStatus") then "OK" else "NOT OK";

declare variable $document_uri := "/xml/systemStatus/systemStatus.xml";

declare variable $result := if ($validation_error eq 'OK' and $is_not_systemStatus_error eq 'OK') then
    try {
        if (empty(xdmp:document-insert($document_uri, $systemStatus, xdmp:default-permissions(), 'xml.systemStatus'))) then "OK" else 'NOT OK'
    }
    catch($exception){
        'NOT OK'
    }
else 'NOT OK';

$result
