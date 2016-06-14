import schema "http://ftn.uns.ac.rs/xml" at "/xml/act.xsd";
declare namespace mlt = "http://ftn.uns.ac.rs/xml";

declare variable $act_string as xs:string external;
declare variable $uri_string as xs:string external;

(: Serilize the new act :)
declare variable $act := xdmp:unquote($act_string);

(: Validate the new act :)
declare variable $errors := xdmp:validate($act, "strict");
declare variable $validation_error := if (exists($errors//error:error)) then "NOT OK" else "OK";
declare variable $is_not_act_error := if (name($act/mlt:act) eq "act") then "OK" else "NOT OK";

(: Define the uris for the original act and the purified act :)
declare variable $original_uri := "/xml/original/" || $uri_string;
declare variable $document_uri := "/xml/acts/" || $uri_string;

(: Copy the original act to the directory for the original acts. :)
declare variable $result_original := if ($validation_error eq 'OK' and $is_not_act_error eq 'OK') then
    try {
        if (empty(xdmp:document-insert($original_uri, doc($document_uri), xdmp:default-permissions(), 'xml.acts'))) then 'OK' else 'NOT OK'
    }
    catch($exception){
        'NOT OK'
    }
else 'NOT OK';

(: Replace the original act with the purified act :)
declare variable $result := if ($validation_error eq 'OK' and $is_not_act_error eq 'OK') then
    try {
        if (empty(xdmp:document-insert($document_uri, $act, xdmp:default-permissions(), 'xml.acts'))) then $document_uri else 'NOT OK'
    }
    catch($exception){
        'NOT OK'
    }
else 'NOT OK';

(: Add the properies to the purified act :)
declare variable $props := xdmp:document-set-properties($document_uri,(
    <title>{data($act/mlt:act/@title)}</title>,
    <country>{data($act/mlt:act/@country)}</country>,
    <region>{data($act/mlt:act/@region)}</region>,
    <establishment>{data($act/mlt:act/@establishment)}</establishment>,
    <serial>{data($act/mlt:act/@serial)}</serial>,
    <date>{data($act/mlt:act/@date)}</date>,
    <city>{data($act/mlt:act/@city)}</city>,
    <status>{'purified'}</status>
));

(: Return the result :)
if (empty($props) and $result_original eq 'OK') then $result else "NOT OK"
