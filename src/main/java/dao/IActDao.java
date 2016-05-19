package dao;


public interface IActDao extends IAbstractDao {

    void storeAct(String raw) throws Exception;
    public String getAllActs();
}
