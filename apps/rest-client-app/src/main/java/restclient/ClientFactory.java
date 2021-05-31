package restclient;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

//import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.glassfish.jersey.SslConfigurator;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

public final class ClientFactory {

	private static Client client = null;
//	private static WebClient webClient = WebClient.create("http://localhost:9000/");

	public static synchronized Client createWithApacheHttpClient() {

		if (client == null) {
			client = createNewWithApacheHttpClient();
		}

		return client;
	}
	
	public static Client createNewWithApacheHttpClient() {
		ClientConfig clientConfig = createClientConfig();

		// SSL. 
		SSLContext sslContext = createSSLContext();
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.getSocketFactory())
				.register("https", new SSLConnectionSocketFactory(sslContext)).build();

		// Pooling Connection.
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(
				socketFactoryRegistry);
		connectionManager.setMaxTotal(10);
		connectionManager.setDefaultMaxPerRoute(10);

		clientConfig.property(ApacheClientProperties.CONNECTION_MANAGER, connectionManager);
		ApacheConnectorProvider connectorProvider = new ApacheConnectorProvider();
		clientConfig.connectorProvider(connectorProvider);
		
		Client client = ClientBuilder.newBuilder().sslContext(sslContext).withConfig(clientConfig).build();
		System.out.println("Used Client Impl: " + client.getClass().getName());

		return client;
	}
	
	public static synchronized Client createWithJDKUrlConnection() {
		if (client == null) {
			client = createNewWithJDKUrlConnection();
		}
		return client;
	}
	
	public static synchronized Client createNewWithJDKUrlConnection() {
		ClientConfig clientConfig = createClientConfig();
		SSLContext sslContext = createSSLContext();
		Client client = ClientBuilder.newBuilder().sslContext(sslContext).withConfig(clientConfig).build();
		System.out.println("Used Client Impl: " + client.getClass().getName());
		return client;
	}
	
//	public static WebClient createCXFClient() {
//		return webClient;
//	}
	
	private static ClientConfig createClientConfig() {
		ClientConfig clientConfig = new ClientConfig();
		// values are in milliseconds
		clientConfig.property(ClientProperties.READ_TIMEOUT, 2000);
		clientConfig.property(ClientProperties.CONNECT_TIMEOUT, 5000);
		return clientConfig;
	}

	private static SSLContext createSSLContext() {
		
		KeyStore keyStore;
		KeyStore trustStore;
		
		SSLContext sslContext = null;
		
		try {
			keyStore = KeyStoreUtil.loadInClassPath("robbyKeyStore.jks", "123456", "jks");
			trustStore = KeyStoreUtil.loadInClassPath("robbyTrustStore.jks", "123456", "jks");
			
			SslConfigurator sslConfig = SslConfigurator.newInstance().keyStore(keyStore).trustStore(trustStore);

	       sslContext = sslConfig.createSSLContext();
			
			
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sslContext;
	}

}