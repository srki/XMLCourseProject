package database;

import com.marklogic.client.DatabaseClientFactory.Authentication;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


class DatabaseConfig {

    private static final String PROPERTIES_PATH = "Config.properties";
    private static final String LOCAL_PROPERTIES_PATH = "Config-local.properties";

    private static Properties props = loadProperties();
    static final String host = props.getProperty("xml.host");
    static final int port = Integer.parseInt(props.getProperty("xml.port"));
    static final String user = props.getProperty("xml.user");
    static final String password = props.getProperty("xml.password");
    static Authentication authType = Authentication.valueOf(props.getProperty("xml.authentication_type").toUpperCase());

    private static Properties loadProperties() {
        try {
            InputStream propsStream = loadPropertiesStream(LOCAL_PROPERTIES_PATH);
            if (propsStream == null) {
                propsStream = loadPropertiesStream(PROPERTIES_PATH);
            }

            if (propsStream == null) {
                throw new IOException("Could not read config properties");
            }

            Properties props = new Properties();
            props.load(propsStream);

            return props;

        } catch (final IOException exc) {
            throw new Error(exc);
        }
    }

    private static InputStream loadPropertiesStream(String path) throws IOException {
        return DatabaseConfig.class.getClassLoader().getResourceAsStream(path);
    }
}
