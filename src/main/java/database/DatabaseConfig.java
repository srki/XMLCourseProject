package database;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.marklogic.client.DatabaseClientFactory.Authentication;


class DatabaseConfig {

    private static Properties props = loadProperties();

    static String host = props.getProperty("xml.host");

    static int port = Integer.parseInt(props.getProperty("xml.port"));

    static String user = props.getProperty("xml.writer_user");

    static String password = props.getProperty("xml.writer_password");

    public static String admin_user = props.getProperty("xml.admin_user");

    public static String admin_password = props.getProperty("xml.admin_password");

    static Authentication authType = Authentication.valueOf(
            props.getProperty("xml.authentication_type").toUpperCase()
    );

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
