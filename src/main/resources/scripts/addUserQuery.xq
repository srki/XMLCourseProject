declare namespace mlt = 'http://ftn.uns.ac.rs/xml'; 
declare variable $user as xs:string external;
xdmp:node-insert-child(doc('/xml/users/xml-user.xml')/mlt:users, xdmp:unquote($user)/mlt:user);