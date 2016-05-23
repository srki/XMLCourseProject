package dao;


public interface IAmendmentDao extends IAbstractDao {


    void storeAmendment(String raw) throws Exception;
    String getAllAmendments(String actUri);
}
