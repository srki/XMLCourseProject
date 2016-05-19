package database;

import com.marklogic.client.DatabaseClient;

/**
 * @author - Srđan Milaković
 */
public interface ISchemaDatabaseManager {
    DatabaseClient getSchemaDatabaseClient();

}
