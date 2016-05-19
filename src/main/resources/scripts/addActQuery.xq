import schema "http://ftn.uns.ac.rs/xml" at "/xml/act.xsd";
declare namespace mlt = "http://ftn.uns.ac.rs/xml";

declare variable $act := fn:doc("/xml/acts/act1.xml");
declare variable $errors := xdmp:validate($act, "strict");
declare variable $validation_error := if (exists($errors//error:error)) then "NOT OK" else "OK";
declare variable $is_not_act_error := if (name($act/mlt:act) eq "act") then "OK" else "NOT OK";
declare variable $less_articles := if(count($act//mlt:article) le 2) then "NOT OK" else "OK";
declare variable $document_uri := "/xml/acts/" || sem:uuid-string() || ".xml";

declare variable $document_created :=
  if ($validation_error eq 'OK' and $is_not_act_error eq 'OK' and $less_articles eq 'OK') then
    try {
      if (empty(xdmp:document-insert($document_uri, $act, xdmp:default-permissions(), 'xml.acts'))) then 'OK' else 'NOT OK'
    }
    catch($exception){
      'NOT OK'
    }
  else 'NOT OK';

declare variable $properties_created := xdmp:document-add-properties($document_uri,(
  <title>{data($act/mlt:act/@title)}</title>,
  <country>{data($act/mlt:act/@country)}</country>,
  <region>{data($act/mlt:act/@region)}</region>,
  <establishment>{data($act/mlt:act/@establishment)}</establishment>,
  <serial>{data($act/mlt:act/@serial)}</serial>,
  <date>{data($act/mlt:act/@date)}</date>,
  <city>{data($act/mlt:act/@city)}</city>
));

$document_created