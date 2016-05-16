package model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user", namespace = "http://ftn.uns.ac.rs/xml")
public class User {

    private String username;
    private String password;
    private String type;


    @XmlElement(name="username", namespace = "http://ftn.uns.ac.rs/xml")
    public String getUsername() {
        return username;
    }

    @XmlElement(name="password", namespace = "http://ftn.uns.ac.rs/xml")
    public String getPassword() {
        return password;
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
}
