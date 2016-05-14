package rest.response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "TestXML")
public class TestXML {

    private String testString;
    private Integer testInt;

    @XmlElement
    public String getTestString() {
        return testString;
    }

    @XmlElement
    public Integer getTestInt() {
        return testInt;
    }

    public void setTestString(String testString) {
        this.testString = testString;
    }

    public void setTestInt(Integer testInt) {
        this.testInt = testInt;
    }
}
