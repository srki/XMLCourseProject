package rest.response;


import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
public class AuthenticationResponse extends AbstractResponse<AuthenticationResponse> {

    public AuthenticationResponse() {
    }

    public AuthenticationResponse(String username, String type, String name, String lastname) {
        this.username = username;
        this.type = type;
        this.name = name;
        this.lastname = lastname;
    }

    @NotBlank(message = "Email cannot be empty")
    @Pattern(regexp = "[a-z]+[0-9]*", message = "Invalid username")
    private String username;

    @NotBlank(message = "Type can either be citizen, representative or president!")
    @Pattern(regexp = "(citizen|representative|president)", message = "Invalid type")
    private String type;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Lastname cannot be empty")
    private String lastname;

    @XmlElement(name="username")
    public String getUsername() {
        return username;
    }

    @XmlElement(name="type")
    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setUsername(String username) {
        this.username = username;
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
