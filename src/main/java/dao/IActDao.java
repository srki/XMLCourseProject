package dao;


public interface IActDao extends IAbstractDao {

    void storeAct(String raw) throws Exception;

    String getAllActs(String text, String title, String country, String region,
                      String establishment, String startDate, String endDate, String city, String serial);
}
