package database;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.marklogic.client.DatabaseClientFactory.Authentication;


class DatabaseConfig {

    private static Properties props = loadProperties();

    static final String host = props.getProperty("xml.host");

    static final int port = Integer.parseInt(props.getProperty("xml.port"));

    static final String user = props.getProperty("xml.user");

    static final String password = props.getProperty("xml.password");

    static Authentication authType = Authentication.valueOf(props.getProperty("xml.authentication_type").toUpperCase());

    private static Properties loadProperties() {
        try {
            String propsName = "Config.properties";
            InputStream propsStream =
                    DatabaseConfig.class.getClassLoader().getResourceAsStream(propsName);
            if (propsStream == null)
                throw new IOException("Could not read config properties");

            Properties props = new Properties();
            props.load(propsStream);

            return props;

        } catch (final IOException exc) {
            throw new Error(exc);
        }
    }
}
