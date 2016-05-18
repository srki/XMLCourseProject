package model;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "user"
})
@XmlRootElement(name = "users", namespace = "http://ftn.uns.ac.rs/xml")
public class Users extends AbstractEntity<Users>{

    @XmlElement(required = true, namespace = "http://ftn.uns.ac.rs/xml")
    protected List<User> user;

    public List<User> getUser() {
        if (user == null) {
            user = new ArrayList<>();
        }
        return user;
    }
}
