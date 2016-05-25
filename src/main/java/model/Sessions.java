package model;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "sessions"
})
@XmlRootElement(name = "sessions")
public class Sessions {

    @XmlElement(required = true, name = "session")
    protected List<String> sessions;

    public List<String> getSessions() {
        if (sessions == null) {
            sessions = new ArrayList<>();
        }
        return sessions;
    }

    public void addSession(String u) {
        if (sessions == null) {
            sessions = new ArrayList<>();
        }
        sessions.add(u);
    }
}
