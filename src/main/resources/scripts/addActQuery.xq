import schema 'http://ftn.uns.ac.rs/xml' at '/xml/acts.xsd';

declare namespace mlt = 'http://ftn.uns.ac.rs/xml';
declare variable $act_string as xs:string external;
declare variable $act := xdmp:unquote($act_string);
declare variable $errors := xdmp:validate($act, 'strict');
declare variable $validation_error := if (exists($errors//error:error)) then 'NOT OK' else 'OK';
declare variable $is_not_act_error := if (name($act/mlt:act) eq 'act') then 'OK' else 'NOT OK';
declare variable $less_articles := if(count($act//mlt:article) le 2) then 'NOT OK 3' else 'OK';

if ($validation_error eq 'OK' and $is_not_act_error eq 'OK' and $less_articles eq 'OK') then
  try {
    if (empty(xdmp:document-insert('/xml/acts/' || sem:uuid-string() || '.xml', $act, xdmp:default-permissions(), 'xml.acts'))) then 'OK' else 'NOT OK'
  }
  catch($exception){
    'NOT OK'
  }
else 'NOT OK';