package restclient;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.ssl.SSLContexts;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

public class TrustAnyCertHttpClientTest {

	private static CloseableHttpClient client;

	public static HttpClient getHttpsClient() throws Exception {

		if (client != null) {
			return client;
		}
		// SSLContext sslcontext = SSLContexts.custom().useSSL().build();
		SSLContext sslcontext = SSLContexts.createDefault();

		TrustManager[] trustAllCerts = new X509TrustManager[] { new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}
		} };

		sslcontext.init(null, trustAllCerts, new SecureRandom());
		SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(sslcontext);
		client = HttpClients.custom().setSSLSocketFactory(factory).build();

		return client;
	}

	public static void releaseInstance() {
		client = null;
	}
	
	public static void main(String[] args) {
		try {
			getHttpsClient();
			HttpGet httpget = new HttpGet("https://localhost:9001/greeting"); 
			CloseableHttpResponse response = client.execute(httpget);
			HttpEntity entity = response.getEntity();
		    System.out.println(entity.getContentType());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
