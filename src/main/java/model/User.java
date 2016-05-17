package model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user", namespace = "http://ftn.uns.ac.rs/xml")
public class User extends AbstractEntity<User> {

    private String username;
    private String password;
    private String type;
    private String name;
    private String lastname;

    @XmlElement(name="username", namespace = "http://ftn.uns.ac.rs/xml")
    public String getUsername() {
        return username;
    }

    @XmlElement(name="password", namespace = "http://ftn.uns.ac.rs/xml")
    public String getPassword() {
        return password;
    }

    @XmlElement(name="name", namespace = "http://ftn.uns.ac.rs/xml")
    public String getName() {
        return name;
    }

    @XmlElement(name="lastname", namespace = "http://ftn.uns.ac.rs/xml")
    public String getLastname() {
        return lastname;
    }

    @XmlElement(name="type", namespace = "http://ftn.uns.ac.rs/xml")
    public String getType() {
        return type;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
