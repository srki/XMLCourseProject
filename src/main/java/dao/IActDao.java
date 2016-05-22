package dao;


public interface IActDao extends IAbstractDao {

    void storeAct(String raw) throws Exception;

    String getAllActs(String text, String title, String country, String region,
                      String establishment, Long startDate, Long endDate, String city, String serial);

    String getArticle(String uri, String id, String format);
}
