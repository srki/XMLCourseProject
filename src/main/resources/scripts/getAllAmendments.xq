declare namespace mlt = "http://ftn.uns.ac.rs/xml";

declare variable $act_uri as xs:string external;
declare variable $status as xs:string external;

declare variable $q1 := cts:directory-query("/xml/amendments/", "infinity");

declare variable $q2 := if ($act_uri eq "") then $q1 else
cts:and-query((
  $q1,
  cts:element-attribute-value-query(QName("http://ftn.uns.ac.rs/xml","amendment"), QName("","uri"), $act_uri)
));

declare variable $q3 := if ($status eq "") then $q3 else
    cts:and-query(($q3, cts:properties-query(
            cts:element-word-query(
                    QName('', 'status'),
                    tokenize($status, '\s')
            )
    )));

<amendments>
    {
        for $x in cts:uris((), (), $q3)
        return
            <amendment>
                <uri>{$x}</uri>
                <date>{doc($x)/mlt:amendment/@date}</date>
            </amendment>
    }
</amendments>