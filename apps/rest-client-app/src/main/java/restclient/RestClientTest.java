package restclient;

import java.net.MalformedURLException;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

//import org.apache.cxf.jaxrs.client.WebClient;

public class RestClientTest {

	private static ExecutorService threadPool = Executors.newFixedThreadPool(100);
	private static int TRHEAD_NUM = 100;
	private static final AtomicLong counter = new AtomicLong();
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Properties props=System.getProperties();
//		System.setProperty("http.maxConnections", "6");
//		System.setProperty("http.keepAlive", "false");
		System.out.println("http.keepAlive: " + props.getProperty("http.keepAlive"));
		System.out.println("http.keepAlive: " + System.getProperty("http.keepAlive"));
		System.out.println("http.maxConnections: " + props.getProperty("http.maxConnections"));
		System.out.println("Java的运行环境版本："+props.getProperty("java.version"));
		System.out.println("Press any key to start, q to quit.");
        Scanner in = new Scanner(System.in);
        String n = in.nextLine();
        
        while (!n.equals("q")) {
			switch (n) {
			case "j":
				getJDK();
				break;
			case "jn":
				getJDKNew();
				break;
			case "mj":
				mgetJDK();
				break;
			case "mjn":
				mgetJDKNew();
				break;
			case "a":
				getApache();
				break;
			case "an":
				getApacheNew();
				break;
			case "ma":
				mgetApache();
				break;
			case "mx":
				mgetCXF();
				break;
			default:
				System.out.println("Unknown action: " + n);
			}
        	n = in.nextLine();
        }
	}

	private static void getJDK() {
		Client client = ClientFactory.createWithJDKUrlConnection();
		send(client);
	}

	private static void getJDKNew() {
		Client client = ClientFactory.createNewWithJDKUrlConnection();
		send(client);
	}
	
	private static void getApache() {
		Client client = ClientFactory.createWithApacheHttpClient();
		send1(client);
	}
	
	private static void getApacheNew() {
		Client client = ClientFactory.createNewWithApacheHttpClient();
		send(client);
	}
	
	private static void mgetJDK() {
		for (int i=0; i<TRHEAD_NUM; i++) {
			Runnable r = new Runnable() {
				public void run() {
					getJDK1();
					getJDK2();
				}
			};
			threadPool.submit(r);
		}
	}
	
	private static void mgetJDKNew() {
		for (int i=0; i<TRHEAD_NUM; i++) {
			Runnable r = new Runnable() {
				public void run() {
					getJDKNew1();
					getJDKNew2();
				}
			};
			threadPool.submit(r);
		}
	}
	
	private static void mgetApache() {
		for (int i=0; i<TRHEAD_NUM; i++) {
			Runnable r = new Runnable() {
				public void run() {
					getApache1();
					getApache2();
				}
			};
			threadPool.submit(r);
		}
	}
	
	private static void mgetCXF() {
		for (int i=0; i<TRHEAD_NUM; i++) {
			Runnable r = new Runnable() {
				public void run() {
					sendXCF1();
					sendXCF2();
				}
			};
			threadPool.submit(r);
		}
	}
	
	
	private static void getApache1() {
		Client client = ClientFactory.createWithApacheHttpClient();
		send1(client);
	}	
	
	private static void getApache2() {
		Client client = ClientFactory.createWithApacheHttpClient();
		send2(client);
	}
	
	private static void getJDK1() {
		Client client = ClientFactory.createWithJDKUrlConnection();
		send1(client);
	}
	
	private static void getJDK2() {
		Client client = ClientFactory.createWithJDKUrlConnection();
		send2(client);
	}
	
	private static void getJDKNew1() {
		Client client = ClientFactory.createNewWithJDKUrlConnection();
		send1(client);
	}
	
	private static void getJDKNew2() {
		Client client = ClientFactory.createNewWithJDKUrlConnection();
		send2(client);
	}
	
	private static void send(Client client) {
		WebTarget target = client.target("https://localhost:9001/greeting");
		Builder builder = target.request();
		Response response = builder.method("GET");
		System.out.println(String.format("%s sending...", counter.incrementAndGet()));
		String responseJson = response.readEntity(String.class);
		System.out.println(responseJson);
	}
	
	private static void sendXCF1() {
//		WebClient client = WebClient.create("http://localhost:9000/");
//		WebClient client = WebClient.fromClient(ClientFactory.createCXFClient());
//		try {
//			System.out.println(client.getCurrentURI().toURL().toString());
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		String responseJson = client.path("test1").query("name", "xcfClient1").get(String.class);
//		System.out.println(responseJson);
	}

	private static void sendXCF2() {
//		WebClient client = WebClient.create("http://localhost:9000/");
//		WebClient client = WebClient.fromClient(ClientFactory.createCXFClient());
//		String responseJson = client.path("test2").query("name", "xcfClient2").get(String.class);
//		System.out.println(responseJson);
	}
	
	private static void send1(Client client) {
		WebTarget target = client.target("http://stormlidembp.cn.ibm.com:9000/test1").queryParam("name", "client1");
		Builder builder = target.request();
		Response response = builder.method("GET");
		System.out.println(String.format("%s sending...", counter.incrementAndGet()));
		String responseJson = response.readEntity(String.class);
		System.out.println(responseJson);
	}
	
	private static void send2(Client client) {
//		WebTarget target = client.target("https://localhost:9001/test2").queryParam("name", "client2");
		WebTarget target = client.target("http://localhost:9000/test2").queryParam("name", "client2");
		Builder builder = target.request();
		Response response = builder.method("GET");
		System.out.println(String.format("%s sending...", counter.incrementAndGet()));
		String responseJson = response.readEntity(String.class);
		System.out.println(responseJson);
	}
}
