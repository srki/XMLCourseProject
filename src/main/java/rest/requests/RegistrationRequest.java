package rest.requests;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")
public class RegistrationRequest extends AbstractRequest<RegistrationRequest>{

    public RegistrationRequest() {}

    @NotBlank(message = "Username cannot be empty")
    @Pattern(regexp = "[a-z]+[0-9]*", message = "Invalid username")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    private String password;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Lastname cannot be empty")
    private String lastname;

    @XmlElement(name="username")
    public String getUsername() {
        return username;
    }

    @XmlElement(name="password")
    public String getPassword() {
        return password;
    }

    @XmlElement(name="name")
    public String getName() {
        return name;
    }

    @XmlElement(name="lastname")
    public String getLastname() {
        return lastname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
