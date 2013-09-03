package org.opencis.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;

public class HttpUtil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6236843003056619519L;
	private static int HTTP_CONNECTION_TIMEOUT = 2000;
	private static int SOCKET_TIMEOUT = 4000;

	public static HttpURLConnection openHttpURLConnection(String httpUrl)
			throws MalformedURLException, IOException {
		URL url = new URL(httpUrl);
		URLConnection conn = url.openConnection();

		conn.setConnectTimeout(HTTP_CONNECTION_TIMEOUT);
		return (HttpURLConnection) conn;
	}

	public static DefaultHttpClient getHttpClient() throws MalformedURLException,
			IOException {
		return new DefaultHttpClient(createHttpParams());
	}

	private static HttpParams createHttpParams() {
		HttpParams httpParameters = new BasicHttpParams();
		// httpParameters.setParameter(HttpMethod, value)
		// Set the timeout in milliseconds until a connection is established.
		HttpConnectionParams.setConnectionTimeout(httpParameters,
				HTTP_CONNECTION_TIMEOUT);
		// Set the default socket timeout (SO_TIMEOUT)
		// in milliseconds which is the timeout for waiting for data.
		HttpConnectionParams.setSoTimeout(httpParameters, SOCKET_TIMEOUT);
		return httpParameters;
	}

	// 辅助方法，用于把流转换为字符串
	public static String convertStreamToString(InputStream is)
			throws IOException {
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(is);
			ByteArrayBuffer bab = new ByteArrayBuffer(32);
			int current = 0;
			while ((current = bis.read()) != -1) {
				bab.append((byte) current);
			}
			String result = EncodingUtils.getString(bab.toByteArray(),
					HTTP.UTF_8);
			return result;
		} catch (IOException e) {
			throw e;
		} finally {
			bis.close();
			is.close();
		}

	}

}
