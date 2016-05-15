package database;

import com.marklogic.client.DatabaseClient;

public interface IDatabaseManager {

    DatabaseClient getDatabaseClient();

}
