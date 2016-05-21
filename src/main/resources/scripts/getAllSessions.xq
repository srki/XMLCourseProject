declare namespace mlt = "http://ftn.uns.ac.rs/xml";

<sessions>
    {
        for $x in cts:uris((), (), cts:directory-query("/xml/sessions/", "infinity"))
        return
            <session>
                <uri>{$x}</uri>
                <beginDate>{data(doc($x)/mlt:session/@beginDate)}</beginDate>
                <endDate>{data(doc($x)/mlt:session/@beginDate)}</endDate>
                <place>{data(doc($x)/mlt:session/mlt:place)}</place>
            </session>
    }
</sessions>