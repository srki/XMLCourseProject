declare namespace mlt = "http://ftn.uns.ac.rs/xml";

declare variable $act_uri as xs:string external;
declare variable $status as xs:string external;
declare variable $username as xs:string external;

declare variable $q1 := cts:directory-query("/xml/amendments/", "infinity");

declare variable $q2 := if ($act_uri eq "") then $q1 else
cts:and-query((
  $q1,
  cts:element-attribute-value-query(QName("http://ftn.uns.ac.rs/xml","amendment"), QName("","uri"), $act_uri)
));

declare variable $q3 := if ($status eq "") then $q2 else
    cts:and-query(($q2, cts:properties-query(
            cts:element-word-query(
                    QName('', 'status'),
                    tokenize($status, '\s')
            )
    )));

declare variable $q4 := if ($username eq "") then $q3 else
    cts:and-query(($q3, cts:properties-query(
            cts:element-word-query(
                    QName('', 'username'),
                    tokenize($username, '\s')
            )
    )));

<amendments>
    {
        for $x in cts:uris((), (), $q4)
        return
            <amendment>
                <uri>{$x}</uri>
                <act>{data(doc($x)/mlt:amendment/@uri)}</act>
                <status>{data(xdmp:document-get-properties($x, QName('', 'status')))}</status>
                <date>{data(doc($x)/mlt:amendment/@date)}</date>
                <name>{data(doc($x)/mlt:amendment/@name)}</name>
            </amendment>
    }
</amendments>