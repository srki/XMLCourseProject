import schema "http://ftn.uns.ac.rs/xml" at "/xml/amendment.xsd";
declare namespace mlt = "http://ftn.uns.ac.rs/xml";

declare variable $username as xs:string external;

declare variable $amendment_string as xs:string external;
declare variable $amendment := xdmp:unquote($amendment_string);

declare variable $errors := xdmp:validate($amendment, "strict");

declare variable $validation_error := if (exists($errors//error:error)) then "NOT OK" else "OK";

declare variable $article_exists := if (
empty(doc("/xml/acts/" || $amendment/mlt:amendment/@uri)//mlt:article[@id=$amendment/mlt:amendment/@articleId]) and not(
$amendment/mlt:amendment/@operation eq "delete")) then "NOT OK" else "OK";

declare variable $same_article_id := if($amendment/mlt:amendment/@articleId eq $amendment/mlt:amendment/mlt:article/@id
        or $amendment/mlt:amendment/@operation eq "delete") then "OK" else "NOT OK";

declare variable $document_uri := "/xml/amendments/" || sem:uuid-string() || ".xml";

declare variable $result := if ($validation_error eq 'OK' and $article_exists eq 'OK' and $same_article_id eq 'OK') then
  try {
    if (empty(xdmp:document-insert($document_uri, $amendment, xdmp:default-permissions(), 'xml.amendments'))) then $document_uri else 'NOT OK'
  }
  catch($exception){
    'NOT OK'
  }
else 'NOT OK';

declare variable $props := xdmp:document-add-properties($document_uri,(
  <status>{'proposed'}</status>,
  <username>{$username}</username>,
  <title>{$amendment/mlt:amendment/@name}</title>
));

if (empty($props)) then $result else "NOT OK"