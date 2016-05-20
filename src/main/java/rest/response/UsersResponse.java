package rest.response;

import model.User;
import model.Users;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "users")
public class UsersResponse {

    @XmlElement(required = true, name = "user")
    protected List<UserResponse> users;

    public UsersResponse() {}

    public UsersResponse(Users users) {
        this.users = new ArrayList<>();
        for(User u : users.getUsers()) {
            this.users.add(new UserResponse(u));
        }
    }

    public List<UserResponse> getUsers() {
        if (users == null) {
            users = new ArrayList<>();
        }
        return users;
    }
}
