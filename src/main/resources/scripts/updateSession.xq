import schema "http://ftn.uns.ac.rs/xml" at "/xml/session.xsd";
declare namespace mlt = "http://ftn.uns.ac.rs/xml";

declare variable $session_url as xs:string external;

declare variable $session_string as xs:string external;
declare variable $session := xdmp:unquote($session_string);

declare variable $document_uri := "/xml/sessions/" || $session_url;

declare variable $result :=
try {
    if (empty(xdmp:document-insert($document_uri, $session, xdmp:default-permissions(), 'xml.sessions'))) then "OK" else 'NOT OK'
}
catch($exception){
    'NOT OK'
};

declare variable $amendments := $session//mlt:amendment;
declare variable $amendmentProperies := (for $i in $amendments return xdmp:document-set-property("/xml/amendments/" || $i/@ref, <status>{$i/@status}</status>));

if (empty($amendmentProperies)) then $result else "NOT OK"