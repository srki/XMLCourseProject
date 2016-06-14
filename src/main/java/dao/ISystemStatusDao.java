package dao;

import javax.xml.transform.TransformerException;
import java.io.IOException;

/**
 * Created by ragnar on 14.6.16..
 */
public interface ISystemStatusDao extends IAbstractDao {
    String documentName = "systemStatus.xml";

    void updateStatus(String status) throws IOException, TransformerException, Exception;
}
