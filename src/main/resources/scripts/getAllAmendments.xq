declare namespace mlt = "http://ftn.uns.ac.rs/xml";

declare variable $act_uri as xs:string external;

declare variable $q1 := cts:directory-query("/xml/amendments/", "infinity");

declare variable $q2 := if ($act_uri eq "") then $q1 else
cts:and-query((
  $q1,
  cts:element-attribute-value-query(QName("http://ftn.uns.ac.rs/xml","amendment"), QName("","uri"), $act_uri)
));

<amendments>
    {
        for $x in cts:uris((), (), $q2)
        return
            <amendment>
                <uri>{$x}</uri>
                <date>{doc($x)/mlt:amendment/@date}</date>
            </amendment>
    }
</amendments>