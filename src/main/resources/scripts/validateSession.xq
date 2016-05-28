import schema "http://ftn.uns.ac.rs/xml" at "/xml/session.xsd";
declare namespace mlt = "http://ftn.uns.ac.rs/xml";

declare function mlt:is_act_status_legal( $src as xs:string, $dst as xs:string ) as xs:boolean {

    if ($src eq "proposed") then ($dst eq "canceled") or ($dst eq "denied") or ($dst eq "approved_in_principle") or ($dst eq "approved_as_whole")
    else if ($src eq "canceled") then $dst eq "proposed"
    else if ($src eq "denied") then $dst eq "proposed"
    else if ($src eq "approved_as_whole") then $dst eq "retired"
    else if ($src eq "approved_in_principle") then ($dst eq "denied") or ($dst eq "approved_as_whole")
    else false()
};

declare function mlt:is_amendment_status_legal( $src as xs:string, $dst as xs:string ) as xs:boolean {

    if ($src eq "proposed") then ($dst eq "canceled") or ($dst eq "denied") or ($dst eq "approved")
    else if ($src eq "canceled") then $dst eq "proposed"
    else if ($src eq "denied") then $dst eq "proposed"
    else false()
};

declare function mlt:are-distinct-values( $seq as xs:anyAtomicType* ) as xs:boolean {
    count(distinct-values($seq)) = count($seq)
};

declare variable $session := xdmp:document-get("/home/dimitrijejankov/session.xml");
declare variable $users := doc("/xml/users/xml-user.xml");

declare variable $errors := xdmp:validate($session, "strict");

declare variable $alderman := $session//mlt:alderman;
declare variable $amendments := $session//mlt:amendment;
declare variable $acts := $session//mlt:act;

declare variable $validation_error := if (exists($errors//error:error))
then "NOT OK"
else "OK";

declare variable $aldermanCheck :=
    if ((for $i in $alderman return exists($users//mlt:user[mlt:username=$i/@username and mlt:type!="citizen"])) = false())
    then "NOT OK"
    else "OK";

declare variable $amendmentProperies := (for $i in $amendments return
    mlt:is_amendment_status_legal(
            data(xdmp:document-get-properties("/xml/amendments/" || $i/@ref, QName("", "status"))),
            $i/@status));

declare variable $amendmentCheck := if ($amendmentProperies = false() or empty($amendmentProperies))
then "NOT OK"
else "OK";

declare variable $actProperties := (for $i in $acts return mlt:is_act_status_legal(data(xdmp:document-get-properties("/xml/acts/"|| $i/@ref, QName("", "status"))),
        $i/@status));

declare variable $actCheck := if ($actProperties = false() or empty($actProperties))
then "NOT OK"
else "OK";

declare variable $articlesInActs :=
    for $act in $acts return
        mlt:are-distinct-values(for $amendment in $act/mlt:amendment[@status="accepted"] return doc("/xml/amendments/" || $amendment//@ref)/mlt:amendment/@articleId);

declare variable $articlesInConfilct := if ($articlesInActs = false()) then "NOT OK" else "OK";

if ($actCheck eq "OK" and
        $validation_error eq "OK" and
        $aldermanCheck eq "OK" and
        $amendmentCheck eq "OK" and
        $actCheck eq "OK" and
        $articlesInConfilct eq "OK") then "OK" else "NOT OK"