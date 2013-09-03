/**
 * 
 */
package org.opencis.ci.hudson;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.opencis.core.CiServer;
import org.opencis.core.Project;
import org.opencis.utils.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import com.dayatang.utils.Assert;

/**
 * @author chencao
 * 
 *         2011-5-14
 */
@Entity
@DiscriminatorValue("1")
public class HudsonCiServer extends CiServer<HudsonConfiguration> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2386402217609339253L;

	private static final Long ID = 1L;

	private static final Logger logger = LoggerFactory
			.getLogger(HudsonCiServer.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencis.ci.CIManager#createJob(java.lang.String,
	 * org.opencis.ci.CIConfiguration)
	 */
	@Override
	public void createJob(HudsonConfiguration configuration) {

		Assert.notNull(configuration);
		DefaultHttpClient hc = null;
		/** 通过apache httpclient 开始 */
		try {
			hc = HttpUtil.getHttpClient();

			HttpPost post = new HttpPost(configuration.getCreateJobUrl());

			StringEntity entity = new StringEntity(
					configuration.getConfigXML(), "UTF-8");
			post.addHeader("Content-Type", "application/xml");

			post.setEntity(entity);
			HttpResponse rp = hc.execute(post);
			// HttpResponse rp = hc.execute(targetHost, post, localcontext);

			if (logger.isInfoEnabled()) {
				if (rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					// 获得响应
					logger.info("=============Hudson Job创建成功。=============");
				} else {
					// 获得响应
					logger.info(
							"=============Hudson Job创建失败，响应：{}=============",
							HttpUtil.convertStreamToString(rp.getEntity()
									.getContent()));
				}
			}

		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("创建Hudson Job失败。" + ex.getMessage());
			}
			ex.printStackTrace();
			throw new RuntimeException("创建Hudson Job失败。");
		} finally {
			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			if (hc != null)
				hc.getConnectionManager().shutdown();
		}
		/** 通过apache httpclient 结束 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opencis.core.domain.CIManager#newConfiguration(org.opencis.core.domain
	 * .Project)
	 */
	@Override
	public HudsonConfiguration buildConfiguration(Project project) {

		Assert.notNull(project);

		HudsonConfiguration config = HudsonConfiguration.getByProject(project);

		if (config == null) {

			config = new HudsonConfiguration(project.getName(),
					project.getCiUrl());

			String configXML = config.getConfigXML();

			// 更新configXML
			config.setConfigXML(updateConfigXML(configXML, project));
			config.save();
		}
		return config;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opencis.core.domain.CIManager#performBuild(org.opencis.core.domain
	 * .CIConfiguration)
	 */
	@Override
	public void performBuild(HudsonConfiguration configuration) {
		Assert.notNull(configuration);

		/** 通过apache httpclient 开始 */
		try {
			HttpClient hc = HttpUtil.getHttpClient();

			HttpGet get = new HttpGet(configuration.getBuildUrl());

			HttpResponse rp = hc.execute(get);

			if (logger.isInfoEnabled()) {
				if (rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					// 获得响应
					logger.info("=============Hudson Job构建指令发送成功。=============");
				} else {
					// 获得响应
					logger.info(
							"=============Hudson Job构建指令发送失败，响应：{}=============",
							HttpUtil.convertStreamToString(rp.getEntity()
									.getContent()));
				}
			}

		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("Hudson Job构建指令发送失败。" + ex.getMessage());
			}
			ex.printStackTrace();
			throw new RuntimeException("Hudson Job构建指令发送失败。");
		}
		/** 通过apache httpclient 结束 */

	}

	/**
	 * @param project
	 *            TODO
	 * @return
	 */
	public String updateConfigXML(String oldConfigXML, Project project) {

		String scmUrl = project.getScm().getDeveloperConnection();
		String newConfigXML = oldConfigXML;

		StringReader sr = new StringReader(oldConfigXML);
		InputSource is = new InputSource(sr);

		try {
			// 构建SAXBuilder对象
			SAXBuilder builder = new SAXBuilder();
			// 构建Document对象
			Document document = builder.build(is);

			// 获取根元素
			Element root = document.getRootElement();

			Element remoteElement = root.getChild("scm").getChild("locations")
					.getChild("hudson.scm.SubversionSCM_-ModuleLocation")
					.getChild("remote");
			remoteElement.setText(scmUrl);

			newConfigXML = new XMLOutputter().outputString(document);
			if (logger.isDebugEnabled()) {
				logger.debug("修改后的configXML为：{}", newConfigXML);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// TODO 为ci添加通知的email
		return newConfigXML;
	}

	public static HudsonCiServer getInstance() {
		return get(HudsonCiServer.class, ID);
	}

	@SuppressWarnings("unused")
	private HttpClient authenticate(String hudsonBaseURL) throws IOException,
			HttpException {
		HttpClient client = HttpUtil.getHttpClient();
		HttpPost httpost = new HttpPost(hudsonBaseURL
				+ "/j_acegi_security_check");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("j_username", "cchen"));
		nvps.add(new BasicNameValuePair("j_password", "123456"));
		nvps.add(new BasicNameValuePair("Login", "login"));
		httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		HttpResponse rp = client.execute(httpost);
		System.out.println("status" + "\n" + rp.getStatusLine() + " rep:"
				+ HttpUtil.convertStreamToString(rp.getEntity().getContent()));
		return client;
	}

	@SuppressWarnings("unused")
	private void put(HttpClient client, String hudsonBaseURL, String jobName,
			File configFile) throws IOException, HttpException {
		// HttpPost post = new HttpPost(hudsonBaseURL+ "/createItem?name=" +
		// jobName);
		// client.getParams().setAuthenticationPreemptive(true);
		// StringEntity entity = new StringEntity(
		// configuration.getConfigXML(), "UTF-8");
		// post.addHeader("Content-Type", "application/xml");
		// postMethod.setRequestHeader("Content-type","application/xml; charset=UTF-8");
		// postMethod.setRequestBody();
		// postMethod.setDoAuthentication(true);
		// try {
		// int status = client.executeMethod(postMethod);
		// System.out.println(status + "\n"+
		// postMethod.getResponseBodyAsString());
		// } finally {
		// postMethod.releaseConnection();
		// }
	}

}
