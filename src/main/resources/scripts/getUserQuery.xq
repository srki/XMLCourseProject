declare namespace mlt = 'http://ftn.uns.ac.rs/xml';  
    declare variable $username as xs:token external;
    for $x in doc('/xml/users/xml-user.xml')/mlt:users/mlt:user
    where $x/mlt:username=$username return $x