import schema "http://ftn.uns.ac.rs/xml" at "/xml/session.xsd";
declare namespace mlt = "http://ftn.uns.ac.rs/xml";

declare variable $session_uri as xs:string external;
declare variable $session := doc("/xml/sessions/" || $session_uri);


declare variable $beginDate := data($session/mlt:session/@beginDate);
declare variable $endDate := data($session/mlt:session/@beginDate);

<session beginDate="{$beginDate}" endDate="{$endDate}" xmlns="http://ftn.uns.ac.rs/xml" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">

    <place>{data($session/mlt:session/mlt:place)}</place>

    {
        for $act in $session/mlt:session/mlt:act return

            <act title="{data(xdmp:document-get-properties('/xml/acts/' || $act/@ref, QName('', 'title')))}">
                {$act/@status}
                {$act/@ref}
                {$act/@votedFor}
                {$act/@votedAgainst}
                {$act/@notVoted}
                {
                    for $amendment in $act//mlt:amendment return
                        <amendment title="{xdmp:document-get-properties('/xml/amendments/' || $amendment/@ref, QName('', 'title'))/@name}">
                            {$amendment/@status}
                            {$amendment/@ref}
                            {$amendment/@votedFor}
                            {$amendment/@votedAgainst}
                            {$amendment/@notVoted}
                        </amendment>
                }
            </act>

    }

    {$session/mlt:session/mlt:alderman}

</session>