package database;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;

/**
 * @author - Srđan Milaković
 */
@Stateless
@Local(ISchemaDatabaseManager.class)
public class SchemaDatabaseManager implements ISchemaDatabaseManager {

    private DatabaseClient client;

    @PostConstruct
    public void init() {
        client = DatabaseClientFactory.newClient(DatabaseConfig.host,
                DatabaseConfig.port,
                "Schemas",
                DatabaseConfig.user,
                DatabaseConfig.password,
                DatabaseConfig.authType);
    }

    @PreDestroy
    public void remove() {
        client.release();
    }

    @Override
    public DatabaseClient getSchemaDatabaseClient() {
        return client;
    }
}
