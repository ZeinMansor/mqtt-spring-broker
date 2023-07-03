package WeatherPortal.WeatherPortal.Config;

public abstract class MqttConfig {
    protected final String broker = "127.0.0.1";
    protected final int qos = 1;
    protected Boolean hasSSL = false;
    protected final int port = 1883;
    protected final String username = "mqtt-user";
    protected final String password = "mqtt-user";
    protected final String TCP = "tcp://";
    protected final String SSL = "ssl://";


    /**
     * Custom Config
     * @param _broker
     * @param _port
     * @param _ssl
     * @param withAuth
    * */
    protected abstract void config(String _broker, Integer _port, Boolean _ssl, Boolean withAuth);

    /**
     * Default Config
     */
    protected abstract void config();

}
