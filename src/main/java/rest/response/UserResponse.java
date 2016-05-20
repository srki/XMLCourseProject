package rest.response;

import model.User;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
public class UserResponse {

    private String username;
    private String type;
    private String name;
    private String lastname;

    public UserResponse() {}

    public UserResponse(User user) {
        username = user.getUsername();
        type = user.getType();
        name = user.getName();
        lastname = user.getLastname();
    }

    @XmlElement(name="username")
    public String getUsername() {
        return username;
    }

    @XmlElement(name="name")
    public String getName() {
        return name;
    }

    @XmlElement(name="lastname")
    public String getLastname() {
        return lastname;
    }

    @XmlElement(name="type")
    public String getType() {
        return type;
    }
}
