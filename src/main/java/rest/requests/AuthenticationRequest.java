package rest.requests;


import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
public class AuthenticationRequest extends AbstractRequest<AuthenticationRequest>{

    @NotBlank(message = "Username cannot be empty")
    @Pattern(regexp = "[a-z]+[0-9]*", message = "Invalid username")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    private String password;

    public AuthenticationRequest() {
    }

    @XmlElement(name="username")
    public String getUsername() {
        return username;
    }

    @XmlElement(name="password")
    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

