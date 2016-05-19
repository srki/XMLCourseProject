declare namespace mlt = "http://ftn.uns.ac.rs/xml";
<acts>
{
  for $x in cts:uris((), (), cts:directory-query("/xml/acts/","infinity"))
  return
  <act>
    <uri>{$x}</uri>
    <title>{data(doc($x)/mlt:act/@title)}</title>
  </act>
}
</acts>