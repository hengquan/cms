package com.hj.utils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;

public class XmlConverUtils {

	public static String maptoXml(Map<String, Object> map) {
		Document document = DocumentHelper.createDocument();
		Element nodeElement = document.addElement("xml");
		for (Object obj : map.keySet()) {
			Element keyElement = nodeElement.addElement(obj.toString());
			// keyElement.addAttribute("label", String.valueOf(obj));
			keyElement.setText(String.valueOf(map.get(obj)));
		}
		return doc2String(document);
	}

	/**
	 * list to xml xml <nodes><node><key label="key1">value1</key>
	 * <key label="key2">value2</key>......</node>
	 * <node><key label="key1">value1</key><key label="key2">value2</key>
	 * ......</node></nodes>
	 * 
	 * @param list
	 * @return
	 */
	public static String listtoXml(List<Object> list) throws Exception {
		Document document = DocumentHelper.createDocument();
		Element nodesElement = document.addElement("nodes");
		int i = 0;
		for (Object o : list) {
			Element nodeElement = nodesElement.addElement("node");
			if (o instanceof Map) {
				for (Object obj : ((Map) o).keySet()) {
					Element keyElement = nodeElement.addElement("key");
					keyElement.addAttribute("label", String.valueOf(obj));
					keyElement.setText(String.valueOf(((Map) o).get(obj)));
				}
			} else {
				Element keyElement = nodeElement.addElement("key");
				keyElement.addAttribute("label", String.valueOf(i));
				keyElement.setText(String.valueOf(o));
			}
			i++;
		}
		return doc2String(document);
	}

	/**
	 * json to xml {"node":{"key":{"@label":"key1","#text":"value1"}}} conver
	 * <o><node class="object"><key class="object" label="key1">value1</key>
	 * </node></o>
	 * 
	 * @param json
	 * @return
	 */
	public static String jsontoXml(String json) {
		try {
			XMLSerializer serializer = new XMLSerializer();
			JSON jsonObject = JSONSerializer.toJSON(json);
			return serializer.write(jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * xml to map xml <node><key label="key1">value1</key>
	 * <key label="key2">value2</key>......</node>
	 * 
	 * @param xml
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String, Object> xml2Map(String xml) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			Document document = DocumentHelper.parseText(xml);
			Element nodeElement = document.getRootElement();
			List<Object> node = nodeElement.elements();
			for (Iterator it = node.iterator(); it.hasNext();) {
				Element elm = (Element) it.next();
				// map.put(elm.attributeValue("label"), elm.getText());
				map.put(elm.getName(), elm.getText());
				elm = null;
			}
			node = null;
			nodeElement = null;
			document = null;
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * xml to list xml <nodes><node><key label="key1">value1</key>
	 * <key label="key2">value2</key>......</node>
	 * <node><key label="key1">value1</key><key label="key2">value2</key>
	 * ......</node></nodes>
	 * 
	 * @param xml
	 * @return
	 */
	public static List xmltoList(String xml) {
		try {
			List<Map> list = new ArrayList<Map>();
			Document document = DocumentHelper.parseText(xml);
			Element nodesElement = document.getRootElement();
			List nodes = nodesElement.elements();
			for (Iterator its = nodes.iterator(); its.hasNext();) {
				Element nodeElement = (Element) its.next();
				Map map = xml2Map(nodeElement.asXML());
				list.add(map);
				map = null;
			}
			nodes = null;
			nodesElement = null;
			document = null;
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * xml to json <node><key label="key1">value1</key></node> 转化为
	 * {"key":{"@label":"key1","#text":"value1"}}
	 * 
	 * @param xml
	 * @return
	 */
	public static String xmltoJson(String xml) {
		XMLSerializer xmlSerializer = new XMLSerializer();
		return xmlSerializer.read(xml).toString();
	}

	/**
	 * 
	 * @param document
	 * @return
	 */
	public static String doc2String(Document document) {
		String s = "";
		try {
			// 使用输出流来进行转化
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			// 使用UTF-8编码
			OutputFormat format = new OutputFormat("   ", true, "UTF-8");
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(document);
			s = out.toString("UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return s;
	}

	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("abc", "12312321");
		map.put("name", "大家");
		map.put("node_sr", "ddasfdfdafasfa");
		map.put("sign", "<![CDATA[3CA89B5870F944736C657979192E1CF4]]>");

		String xml = "<xml><return_code><![CDATA[FAIL ]]></return_code> <return_msg><![CDATA[SYSERR]]></return_msg> </xml>";
		xml = "<xml><appid><![CDATA[wx1d4dd53cdbfed0b2]]></appid><attach><![CDATA[78A809C7FFA8016A2B2CBC8EDB14FAF1]]></attach><bank_type><![CDATA[CCB_DEBIT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[1323949601]]></mch_id><nonce_str><![CDATA[18C160B17F0000010849328AF276074A]]></nonce_str><openid><![CDATA[oi_zvs4WLMR9EjpXXr-bN15zk2vg]]></openid><out_trade_no><![CDATA[2016041515100645FC9431]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[3750BD4F588CC375405A01B47BCCD75D]]></sign><time_end><![CDATA[20160415154446]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[4000732001201604154866893214]]></transaction_id></xml>";

		Map<String, Object> mp = xml2Map(xml);
	}
}
