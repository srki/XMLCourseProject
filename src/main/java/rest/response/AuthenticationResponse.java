package rest.response;


import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
public class AuthenticationResponse extends AbstractResponse<AuthenticationResponse> {

    @NotBlank(message = "Email cannot be empty")
    @Pattern(regexp = "[a-z]+[0-9]*", message = "Invalid username")
    private String username;

    @NotBlank(message = "Type can either be citizen, representative or president!")
    @Pattern(regexp = "(citizen|representative|president)", message = "Invalid type")
    private String type;

    public AuthenticationResponse() {
    }

    public AuthenticationResponse(String username, String type) {
        this.username = username;
        this.type = type;
    }

    @XmlElement(name="username")
    public String getUsername() {
        return username;
    }

    @XmlElement(name="type")
    public String getType() {
        return type;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setType(String type) {
        this.type = type;
    }
}
