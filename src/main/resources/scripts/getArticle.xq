import schema "http://ftn.uns.ac.rs/xml" at "/xml/act.xsd";
declare namespace mlt = "http://ftn.uns.ac.rs/xml";

declare variable $act_uri as xs:string external;
declare variable $article_id as xs:string external;
declare variable $format as xs:string external;

if ($format eq "amendment") then
    (<amendment date="" articleId="{$article_id}" uri="{$act_uri}" operation="" xmlns="http://ftn.uns.ac.rs/xml" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
    <description>asdasdasd</description>
        {doc("/xml/acts/" || $act_uri)//mlt:article[@id=$article_id]}
    </amendment>)
else
    doc("/xml/acts/" || $act_uri)//mlt:article[@id=$article_id]
