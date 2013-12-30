package comp.hp.ij.common.service.pandora.api;
import org.apache.commons.codec.binary.Base64;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.ByteArrayInputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

/**
 * Xmlrpc implementation for communicating with the Pandora server
 */
public class XmlRpcProtocol extends Protocol {

	public XmlRpcProtocol(Security security, String httpUrl, String httpsUrl) throws Exception {
		super(security, httpUrl, httpsUrl);
	}

	String getContentType() {
		return "text/xml";
	}

	String getPayload(String method, HashMap<String, Object> params) {
		return XmlRpcRequest.buildRequest(method, params);
	}

	Result getResultFromResponse(String response) throws Exception {
		System.out.println("response: " + response);
		XmlRpcResult result = XmlRpcResponse.parseResponse(response);

		// check for xmlrpc errors
		if (result.containsKey("faultString")) {
			throw new Exception("xmlrpc error code: " + result.getInt("faultCode") + ", message: " + result.getString("faultString"));
		}

		return result.getResult("result");
	}

	/**
	 * Builds the RPC request from a Map of parameters.
	 */
	private static class XmlRpcRequest {
		public static String buildRequest(String method, Map<String, Object> params) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("<?xml version=\"1.0\"?>\n");
			buffer.append("<methodCall>\n");
			buffer.append("<methodName>" + method+ "</methodName>\n");
			buffer.append("<params>\n");

			buffer.append("<param>\n");
			buffer.append("<value>" + buildParamXml(params) + "</value>\n");
			buffer.append("</param>\n");

			buffer.append("</params>\n");
			buffer.append("</methodCall>");
			return buffer.toString();
		}

		@SuppressWarnings("unchecked")
        private static String buildMapXml(Map<String, Object> params) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("<struct>\n");
			for (Iterator iterator = params.entrySet().iterator(); iterator.hasNext();) {
				Map.Entry entry = (Map.Entry) iterator.next();
				buffer.append("<member>\n");
				buffer.append("<name>" + entry.getKey() + "</name>\n");
				buffer.append("<value>" + buildParamXml(entry.getValue()) + "</value>\n");
				buffer.append("</member>\n");
			}
			buffer.append("</struct>\n");
			return buffer.toString();
		}

		private static String buildStringXml(String value) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("<string>" + value + "</string>\n");
			return buffer.toString();
		}

		private static String buildBooleanXml(boolean value) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("<boolean>" + value + "</boolean>\n");
			return buffer.toString();
		}

		private static String buildIntegerXml(Long value) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("<i4>" + value.toString() + "</i4>\n");
			return buffer.toString();
		}

		@SuppressWarnings("unchecked")
        private static String buildParamXml(Object obj) {
			if (obj instanceof String) {
				return buildStringXml((String)obj);
			}
			else if (obj instanceof Map) {
				return buildMapXml((Map)obj);
			}
			else if (obj instanceof Boolean) {
				return buildBooleanXml((Boolean)obj);
			}
			else if (obj instanceof Long) {
				return buildIntegerXml((Long)obj);
			}
			else {
				throw new RuntimeException("xmlrpc object not supported: " + obj.getClass().getName());
			}
		}
	}

	/**
	 * Parses the xmlrpc response using a sax parser.
	 */
	@SuppressWarnings("unchecked")
	private static class XmlRpcResponse extends DefaultHandler {
        private Stack values;
		private Value currentValue;
		private StringBuffer cdata;
		private boolean readCdata;

		private XmlRpcResult returnValue;

		public XmlRpcResponse() {
			values = new Stack();
			cdata = new StringBuffer();
			currentValue = null;
			readCdata = false;
		}

		public static XmlRpcResult parseResponse(String response) throws Exception {
			XmlRpcResponse handler = new XmlRpcResponse();
			XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.crimson.parser.XMLReaderImpl");
			parser.setContentHandler(handler);
			parser.setErrorHandler(handler);
			parser.parse(new InputSource(new ByteArrayInputStream(response.getBytes("UTF-8"))));
			return handler.getReturnValue();
		}

		XmlRpcResult getReturnValue() {
			return returnValue;
		}

		@Override
		public void characters(char[] chars, int start, int length) throws SAXException {
			if (readCdata) {
				cdata.append(chars, start, length);
			}
		}

		@Override
		public void startElement(String nsUri, String strippedName, String name, Attributes attributes) throws SAXException {
			if ("value".equals(name))
			{
				Value v = new Value();
				values.push(v);
				currentValue = v;
				// cdata object is reused
				cdata.setLength(0);
				readCdata = true;
			}
			else if ("methodName".equals(name))
			{
				cdata.setLength(0);
				readCdata = true;
			}
			else if ("name".equals(name))
			{
				cdata.setLength(0);
				readCdata = true;
			}
			else if ("string".equals(name))
			{
				// currentValue.setType (STRING);
				cdata.setLength(0);
				readCdata = true;
			}
			else if ("i4".equals(name) || "int".equals(name))
			{
				currentValue.setType(Value.INTEGER);
				cdata.setLength(0);
				readCdata = true;
			}
			else if ("boolean".equals(name))
			{
				currentValue.setType(Value.BOOLEAN);
				cdata.setLength(0);
				readCdata = true;
			}
			else if ("double".equals(name))
			{
				currentValue.setType(Value.DOUBLE);
				cdata.setLength(0);
				readCdata = true;
			}
			else if ("dateTime.iso8601".equals(name))
			{
				currentValue.setType(Value.DATE);
				cdata.setLength(0);
				readCdata = true;
			}
			else if ("base64".equals(name))
			{
				currentValue.setType(Value.BASE64);
				cdata.setLength(0);
				readCdata = true;
			}
			else if ("struct".equals(name))
			{
				currentValue.setType(Value.STRUCT);
			}
			else if ("array".equals(name))
			{
				currentValue.setType(Value.ARRAY);
			}
		}

		@Override
		public void endElement(String nsUri, String strippedName, String name) throws SAXException {
			// finalize character data, if appropriate
			if (currentValue != null && readCdata)
			{
				currentValue.characterData(cdata.toString());
				cdata.setLength(0);
				readCdata = false;
			}

			if ("value".equals(name))
			{
				// Only handle top level objects or objects contained in
				// arrays here.  For objects contained in structs, wait
				// for </member> (see code below).
				int depth = values.size ();
				if (depth < 2 || values.elementAt(depth - 2).hashCode() != Value.STRUCT)
				{
					Value v = currentValue;
					values.pop();
					if (depth < 2)
					{
						// done parsing, save off the final value
						returnValue = (XmlRpcResult) v.value;
						currentValue = null;
					}
					else
					{
						// Add object to sub-array; if current container
						// is a struct, add later (at </member>).
						currentValue = (Value) values.peek();
						currentValue.endElement(v);
					}
				}
			}

			// Handle objects contained in structs.
			if ("member".equals(name))
			{
				Value v = currentValue;
				values.pop();
				currentValue = (Value) values.peek();
				currentValue.endElement(v);
			}
			else if ("methodName".equals(name))
			{
				cdata.setLength(0);
				readCdata = false;
			}
		}

		@Override
		public void error(SAXParseException e) throws SAXException {
			System.err.println("Error parsing XML: " + e);
		}

		@Override
		public void fatalError(SAXParseException e) throws SAXException {
			System.err.println("Fatal error parsing XML: " + e);
		}
	}

	/**
	 * Container class for an xmlrpc value
	 */
	@SuppressWarnings("unchecked")
    static class Value {
		static final DateParser dateParser = new DateParser();

		static final int STRING = 0;
		static final int INTEGER = 1;
		static final int BOOLEAN = 2;
		static final int DOUBLE = 3;
		static final int DATE = 4;
		static final int BASE64 = 5;
		static final int STRUCT = 6;
		static final int ARRAY = 7;

        int type;
        Object value;
        String nextMemberName;

        XmlRpcResult struct;
        ArrayList array;

		/**
         * Constructor.
         */
        public Value()
        {
            this.type = STRING;
        }

        /**
         * Notification that a new child element has been parsed.
         */
        public void endElement(Value child)
        {
            switch (type)
            {
                case ARRAY:
                    array.add(child.value);
                    break;
                case STRUCT:
                    struct.put(nextMemberName, child.value);
            }
        }

        /**
         * Set the type of this value. If it's a container, create the
         * corresponding java container.
         */
        public void setType(int type)
        {
            this.type = type;
            switch (type)
            {
                case ARRAY:
                    value = array = new ArrayList ();
                    break;
                case STRUCT:
                    value = struct = new XmlRpcResult (new HashMap());
                    break;
            }
        }

        /**
         * Set the character data for the element and interpret it
         * according to the element type.
         */
        public void characterData(String cdata) {
            switch (type)
            {
                case INTEGER:
                    value = new Integer(cdata.trim());
                    break;
                case BOOLEAN:
                    value = ("1".equals(cdata.trim ()) ? Boolean.TRUE : Boolean.FALSE);
                    break;
                case DOUBLE:
                    value = new Double(cdata.trim());
                    break;
                case DATE:
					try {
						value = dateParser.parse(cdata.trim());
					}
					catch (ParseException p) {
						throw new RuntimeException(p.getMessage());
					}
                    break;
                case BASE64:
                    value = new Base64().decode(cdata.getBytes());
                    break;
                case STRING:
                    value = cdata;
                    break;
                case STRUCT:
                    // this is the name to use for the next member of this struct
                    nextMemberName = cdata;
                    break;
            }
        }

		/**
		 * This is a performance hack to get the type of a value
		 * without casting the Object.  It breaks the contract of
		 * method hashCode, but it doesn't matter since Value objects
		 * are never used as keys in Hashtables.
		 */
		public int hashCode()
		{
			return type;
		}
    }

	/**
	 * Parses xmlrpc dates and protects against mutilple threads.
	 */
	static class DateParser {
		protected static final String FORMAT = "yyyyMMdd'T'HH:mm:ss";
		private DateFormat df;

		public DateParser() {
			df = new SimpleDateFormat(FORMAT);
		}

		public synchronized String format(Date d) {
			return df.format(d);
		}

		public synchronized Date parse(String s) throws ParseException {
			return df.parse(s);
		}
	}
}