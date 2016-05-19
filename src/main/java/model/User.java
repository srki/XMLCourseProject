package model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user", namespace = "http://ftn.uns.ac.rs/xml")
public class User extends AbstractEntity<User> {

    public static final String CITIZEN = "citizen";
    public static final String REPRESENTATIVE = "representative";
    public static final String PRESIDENT = "president";

    private String username;
    private String password;
    private String type;
    private String name;
    private String lastname;

    @XmlElement(name="username", namespace = "http://ftn.uns.ac.rs/xml")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @XmlElement(name="password", namespace = "http://ftn.uns.ac.rs/xml")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @XmlElement(name="name", namespace = "http://ftn.uns.ac.rs/xml")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "lastname", namespace = "http://ftn.uns.ac.rs/xml")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @XmlElement(name = "type", namespace = "http://ftn.uns.ac.rs/xml")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
