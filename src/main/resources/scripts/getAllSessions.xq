declare namespace mlt = "http://ftn.uns.ac.rs/xml";

declare variable $status as xs:string external;

declare variable $q1 := cts:directory-query("/xml/sessions/", "infinity");

declare variable $q2 := if ($status eq "upcoming") then
    cts:and-query((
        $q1,
        cts:element-attribute-range-query(
                QName('http://ftn.uns.ac.rs/xml', 'session'),
                QName('', 'beginDate'),
                ">=",
                fn:current-dateTime())
    )) else $q1;

declare variable $q3 := if ($status eq "finished") then
    cts:and-query((
        $q2,
        cts:element-attribute-range-query(
                QName('http://ftn.uns.ac.rs/xml', 'session'),
                QName('', 'endDate'),
                "<=",
                fn:current-dateTime())
    )) else $q2;

<sessions>
    {
        for $x in cts:uris((), (), $q3)
        return
            <session>
                <uri>{$x}</uri>
                <beginDate>{data(doc($x)/mlt:session/@beginDate)}</beginDate>
                <endDate>{data(doc($x)/mlt:session/@beginDate)}</endDate>
                <place>{data(doc($x)/mlt:session/mlt:place)}</place>
            </session>
    }
</sessions>