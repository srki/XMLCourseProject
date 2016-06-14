package dao;


public interface IAmendmentDao extends IAbstractDao {


    void storeAmendment(String raw, String username) throws Exception;
    String getAllAmendments(String actUri, String status, String username);
    void deleteAmendment(String uri, String username);
}
