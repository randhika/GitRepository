package br.livroandroid.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XMLUtils {

    private DomDriver domDriver;
    protected XStream xstream;
    
    public DomDriver getDomDriver() {
	return domDriver;
    }

    public void setDomDriver(DomDriver domDriver) {
	this.domDriver = domDriver;
    }

    public XMLUtils(String encoding) {
	domDriver = new DomDriver(encoding);
    }

    public XMLUtils() {
    };

    public static Element getRoot(String xml, String charset) {
	try {
	    InputStream in = null;
	    DocumentBuilderFactory factoring = DocumentBuilderFactory
		    .newInstance();
	    factoring.setValidating(false);
	    byte[] bytes = xml.getBytes();
	    in = new ByteArrayInputStream(bytes);
	    DocumentBuilder builder = factoring.newDocumentBuilder();
	    Document dom = builder.parse(in);
	    Element root = dom.getDocumentElement();
	    if (root == null) {
		throw new RuntimeException("XML invalido");
	    }
	    return root;
	} catch (Exception e) {
	    throw new RuntimeException(e.getMessage(), e);
	}
    }

    public static String getText(Node node, String tag) {
	Node n = getChild(node, tag);
	if (n != null) {
	    Node text = n.getFirstChild();
	    if (text != null) {
		String s = text.getNodeValue();
		return s.trim();
	    }
	}
	return "";
    }

    public static List<Node> getChildren(Node node, String name) {
	List<Node> children = new ArrayList<Node>();
	NodeList nodes = node.getChildNodes();
	if (nodes != null && nodes.getLength() >= 1) {
	    for (int i = 0; i < nodes.getLength(); i++) {
		Node n = nodes.item(i);
		if (name.equals(n.getNodeName())) {
		    children.add(n);
		}
	    }
	}
	return children;
    }

    public static Node getChild(Node node, String tag) {
	if (node == null) {
	    return null;
	}
	NodeList nodes = node.getChildNodes();
	if (nodes == null) {
	    return null;
	}
	for (int i = 0; i < nodes.getLength(); i++) {
	    Node item = nodes.item(i);
	    if (item != null) {
		String name = item.getNodeName();
		if (tag.equals(name)) {
		    return item;
		}
	    }
	}

	return null;
    }

    public void toXml(String charset, Object obj, Writer writer,
	    Class<?>[] classes, String... alias) {
	xstream = new XStream(getDomDriver());
	for (int i = 0; i < alias.length; i++) {
	    xstream.alias(alias[i], classes[i]);
	}
	xstream.toXML(obj, writer);
    }

    public Object fromXml(String xml) {
	xstream = new XStream(getDomDriver());
	return xstream.fromXML(xml);
    }
    public Object fromXml(File xml) {
	xstream = new XStream(getDomDriver());
	return xstream.fromXML(xml);
    }

}
