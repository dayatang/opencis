/**
 * 
 */
package org.opencis.template.internal;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.junit.Test;

/**
 * @author chencao
 * 
 *         2011-5-25
 */
public class PomTest {

	@Test
	public void test() throws Exception {

		// 构建SAXBuilder对象
		SAXBuilder builder = new SAXBuilder();
		// 构建Document对象
		Document document = builder.build(PomTest.class
				.getResourceAsStream("/test/apps.xml"));

		// Namespace ns = Namespace.getNamespace("xmlns",
		// "http://maven.apache.org/POM/4.0.0");
		// 获取根元素
		Element root = document.getRootElement();
		// System.out.println(root.getValue());
		// Element messagesElement = root.getChild("version",
		// Namespace.getNamespace("http://maven.apache.org/POM/4.0.0"));
		Element messagesElement = root.getChild("messages");
		messagesElement.addContent(new Element("message")
				.addContent(new Element("start-date").setText("sdf")));
		messagesElement.removeChild("messaage");

		// System.out.println(messagesElement.getValue());

		/**
		 * Print the modified xml document
		 */
		String xmlFileData = new XMLOutputter().outputString(document);
		System.out.println("Modified XML file is : " + xmlFileData);

		/**
		 * Modify the orginal document using FileWritter
		 */
		// FileUtils.write(file, xmlFileData, encoding)
	}
}
