package restclient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class KeyStoreUtil {

	public static KeyStore loadInClassPath(String location, String password, String type)
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		InputStream keyStoreIn = ClientFactory.class.getClassLoader().getResourceAsStream(location);
		KeyStore keyStore = KeyStore.getInstance(type);
		keyStore.load(keyStoreIn, password.toCharArray());

		return keyStore;
	}

	public static KeyStore laodFromFile(String filePath, String password, String type)
			throws NoSuchAlgorithmException, CertificateException, IOException, KeyStoreException {
		FileInputStream instream = new FileInputStream(new File(filePath));
		KeyStore keyStore = KeyStore.getInstance(type);
		try {
			keyStore.load(instream, password.toCharArray());
		} finally {
			instream.close();
		}

		return keyStore;
	}
}
