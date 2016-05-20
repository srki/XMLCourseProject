package model;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "users"
})
@XmlRootElement(name = "users", namespace = "http://ftn.uns.ac.rs/xml")
public class Users extends AbstractEntity<Users>{

    @XmlElement(required = true, namespace = "http://ftn.uns.ac.rs/xml", name = "user")
    protected List<User> users;

    public List<User> getUsers() {
        if (users == null) {
            users = new ArrayList<>();
        }
        return users;
    }

    public void addUser(User u) {
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(u);
    }
}
