import schema "http://ftn.uns.ac.rs/xml" at "/xml/act.xsd";
declare namespace mlt = "http://ftn.uns.ac.rs/xml";

declare variable $act_uri as xs:string external;
declare variable $article_id as xs:string external;

doc("/xml/acts/" || $act_uri)//mlt:article[@id=$article_id]