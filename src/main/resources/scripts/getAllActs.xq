declare variable $text as xs:string external;
declare variable $title as xs:string external;
declare variable $region as xs:string external;
declare variable $establishment as xs:string external;
declare variable $city as xs:string external;
declare variable $serial as xs:string external;
declare variable $status as xs:string external;

declare variable $start_date as xs:string external;
declare variable $end_date as xs:string external;


declare variable $q1 := cts:directory-query("/xml/acts/", "infinity");

declare variable $q2 := if ($text eq "") then $q1 else
    cts:and-query(($q1,
    cts:word-query(
            tokenize($text, '\s'),
            "case-insensitive")
    ));

declare variable $q3 := if ($title eq "") then $q2 else
    cts:and-query(($q2,
    cts:properties-query(
            cts:element-word-query(
                    QName('', 'title'),
                    tokenize($title, '\s')
            )
    )));

declare variable $q4 := if ($region eq "") then $q3 else
    cts:and-query(($q3,
    cts:properties-query(
            cts:element-word-query(
                    QName('', 'region'),
                    tokenize($region, '\s')
            )
    )));

declare variable $q5 := if ($establishment eq "") then $q4 else
    cts:and-query(($q4, cts:properties-query(
            cts:element-word-query(
                    QName('', 'establishment'),
                    tokenize($establishment, '\s')
            )
    )));

declare variable $q6 := if ($city eq "") then $q5 else
    cts:and-query(($q5,
    cts:properties-query(
            cts:element-word-query(
                    QName('', 'city'),
                    tokenize($city, '\s')
            )
    )));

declare variable $q7 := if ($serial eq "") then $q6 else
    cts:and-query(($q6,
    cts:properties-query(
            cts:element-word-query(
                    QName('', 'serial'),
                    tokenize($serial, '\s')
            )
    )));

declare variable $q8 := if ($start_date eq "" or $end_date eq "") then $q7 else
    cts:and-query((
        $q7,
        cts:and-query((
            cts:properties-query(
                    cts:element-range-query(QName('', 'date'), ">", xs:date($start_date))),
            cts:properties-query(
                    cts:element-range-query(QName('', 'date'), "<", xs:date($end_date)))
        ))
    ));

declare variable $q9 := if ($status eq "") then $q8 else
    cts:and-query(($q8, cts:properties-query(
            cts:element-word-query(
                    QName('', 'status'),
                    tokenize($status, '\s')
            )
    )));


<acts>
    {
        for $x in cts:uris((), (), $q9)
        return
            <act>
                <uri>{$x}</uri>
                <title>{data(xdmp:document-get-properties($x, QName('', 'title')))}</title>
                <status>{data(xdmp:document-get-properties($x, QName('', 'status')))}</status>
            </act>
    }
</acts>