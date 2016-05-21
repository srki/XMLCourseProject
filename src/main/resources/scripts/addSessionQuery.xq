import schema "http://ftn.uns.ac.rs/xml" at "/xml/session.xsd";
declare namespace mlt = "http://ftn.uns.ac.rs/xml";

declare variable $act_string as xs:string external;
declare variable $session := xdmp:unquote($act_string);
declare variable $errors := xdmp:validate($session, "strict");
declare variable $validation_error := if (exists($errors//error:error)) then "NOT OK" else "OK";
declare variable $is_not_session_error := if (name($session/mlt:session) eq "session") then "OK" else "NOT OK";

declare variable $document_uri := "/xml/sessions/" || sem:uuid-string() || ".xml";

declare variable $result := if ($validation_error eq 'OK' and $is_not_session_error eq 'OK') then
    try {
        if (empty(xdmp:document-insert($document_uri, $session, xdmp:default-permissions(), 'xml.sessions'))) then "OK" else 'NOT OK'
    }
    catch($exception){
        'NOT OK'
    }
else 'NOT OK';

$result