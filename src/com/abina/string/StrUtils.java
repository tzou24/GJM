package com.abina.string;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.HTMLWriter;
import org.dom4j.io.OutputFormat;

public class StrUtils {
	private static Pattern numericPattern = Pattern.compile("^[0-9\\-]+$");
	private static Pattern numericStringPattern = Pattern.compile("^[0-9\\-\\-]+$");
	private static Pattern floatNumericPattern = Pattern.compile("^[0-9\\-\\.]+$");
	private static Pattern abcPattern = Pattern.compile("^[a-z|A-Z]+$");
	public static final String splitStrPattern = ",|��|;|��|��|\\.|��|-|_|\\(|\\)|\\[|\\]|\\{|\\}|\\\\|/| |��|\"";
	private static Pattern chinesePattern = Pattern.compile("[\u4e00-\u9fa5]");

	/**
	 * �ж��Ƿ����ֱ�ʾ
	 * 
	 * @param src
	 *            Դ�ַ���
	 * @return �Ƿ����ֵı�־
	 */
	public static boolean isNumeric(String src) {
		boolean return_value = false;
		if (src != null && src.length() > 0) {
			Matcher m = numericPattern.matcher(src);
			if (m.find()) {
				return_value = true;
			}
		}
		return return_value;
	}

	/**
	 * �ж��Ƿ����ֱ�ʾ
	 * 
	 * @param src
	 *            Դ�ַ���
	 * @return �Ƿ����ֵı�־
	 */
	public static boolean isNumericString(String src) {
		boolean return_value = false;
		if (src != null && src.length() > 0) {
			Matcher m = numericStringPattern.matcher(src);
			if (m.find()) {
				return_value = true;
			}
		}
		return return_value;
	}

	/**
	 * �ж��Ƿ���ĸ���
	 * 
	 * @param src
	 *            Դ�ַ���
	 * @return �Ƿ���ĸ��ϵı�־
	 */
	public static boolean isABC(String src) {
		boolean return_value = false;
		if (src != null && src.length() > 0) {
			Matcher m = abcPattern.matcher(src);
			if (m.find()) {
				return_value = true;
			}
		}
		return return_value;
	}

	/**
	 * �ж��Ƿ񸡵����ֱ�ʾ
	 * 
	 * @param src
	 *            Դ�ַ���
	 * @return �Ƿ����ֵı�־
	 */
	public static boolean isFloatNumeric(String src) {
		boolean return_value = false;
		if (src != null && src.length() > 0) {
			Matcher m = floatNumericPattern.matcher(src);
			if (m.find()) {
				return_value = true;
			}
		}
		return return_value;
	}

	/**
	 * ������ת�������
	 * @param str
	 * @return
	 */
	public static String toChinese(String str){
		String chineseChar = "";
		if(!isContainChinese(str)){
			chineseChar = changCoding(str, "ISO-8859-1", "utf-8");
		}else{
			chineseChar = str;
		}
		return chineseChar;
	}
	
	
	/**
	 * ������֤�ַ����Ƿ���������ַ�
	 * @param str
	 * @return true:����; false:������
	 */
	public static boolean isContainChinese(String str) {
		if(str == null){
			return false;
		}
        Matcher m = chinesePattern.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
	
	/**
	 * ��string array or list�ø����ķ���symbol���ӳ�һ���ַ���
	 * 
	 * @param array
	 * @param symbol
	 * @return
	 */
	public static String joinString(List array, String symbol) {
		String result = "";
		if (array != null) {
			for (int i = 0; i < array.size(); i++) {
				String temp = array.get(i).toString();
				if (temp != null && temp.trim().length() > 0)
					result += (temp + symbol);
			}
			if (result.length() > 1)
				result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	public static String subStringNotEncode(String subject, int size) {
		if (subject != null && subject.length() > size) {
			subject = subject.substring(0, size) + "...";
		}
		return subject;
	}

	/**
	 * ��ȡ�ַ������������ַ���symbol���� ����
	 * 
	 * @param len
	 *            ���ַ������ȡ����ȼ�����λΪһ��GBK���֡�������Ӣ����ĸ����Ϊһ����λ����
	 * @param str
	 * @param symbol
	 * @return
	 */
	public static String getLimitLengthString(String str, int len, String symbol) {
		int iLen = len * 2;
		int counterOfDoubleByte = 0;
		String strRet = "";
		try {
			if (str != null) {
				byte[] b = str.getBytes("GBK");
				if (b.length <= iLen) {
					return str;
				}
				for (int i = 0; i < iLen; i++) {
					if (b[i] < 0) {
						counterOfDoubleByte++;
					}
				}
				if (counterOfDoubleByte % 2 == 0) {
					strRet = new String(b, 0, iLen, "GBK") + symbol;
					return strRet;
				} else {
					strRet = new String(b, 0, iLen - 1, "GBK") + symbol;
					return strRet;
				}
			} else {
				return "";
			}
		} catch (Exception ex) {
			return str.substring(0, len);
		} finally {
			strRet = null;
		}
	}

	/**
	 * ��ȡ�ַ������������ַ���symbol���� ����
	 * 
	 * @param len
	 *            ���ַ������ȡ����ȼ�����λΪһ��GBK���֡�������Ӣ����ĸ����Ϊһ����λ����
	 * @param str
	 * @param symbol
	 * @return12
	 */
	public static String getLimitLengthString(String str, int len) {
		return getLimitLengthString(str, len, "...");
	}

	/**
	 * 
	 * ��ȡ�ַ�����ת��
	 * 
	 * @param subject
	 * @param size
	 * @return
	 */
	public static String subStrNotEncode(String subject, int size) {
		if (subject.length() > size) {
			subject = subject.substring(0, size);
		}
		return subject;
	}

	/**
	 * ��string array or list�ø����ķ���symbol���ӳ�һ���ַ���
	 * 
	 * @param array
	 * @param symbol
	 * @return
	 */
	public static String joinString(String[] array, String symbol) {
		String result = "";
		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				String temp = array[i];
				if (temp != null && temp.trim().length() > 0)
					result += (temp + symbol);
			}
			if (result.length() > 1)
				result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	/**
	 * ȡ���ַ�����ʵ�ʳ��ȣ������˺��ֵ������
	 * 
	 * @param SrcStr
	 *            Դ�ַ���
	 * @return �ַ�����ʵ�ʳ���
	 */
	public static int getStringLen(String SrcStr) {
		int return_value = 0;
		if (SrcStr != null) {
			char[] theChars = SrcStr.toCharArray();
			for (int i = 0; i < theChars.length; i++) {
				return_value += (theChars[i] <= 255) ? 1 : 2;
			}
		}
		return return_value;
	}

	/**
	 * ������ݴ����Ƿ�����Ƿ��ַ���
	 * 
	 * @param str
	 * @return [true]|[false] ����|������
	 */
	public static boolean check(String str) {
		String sIllegal = "'\"";
		int len = sIllegal.length();
		if (null == str)
			return false;
		for (int i = 0; i < len; i++) {
			if (str.indexOf(sIllegal.charAt(i)) != -1)
				return true;
		}

		return false;
	}

	/***************************************************************************
	 * getHideEmailPrefix - �����ʼ���ַǰ׺��
	 * 
	 * @param email
	 *            - EMail�����ַ ����: linwenguo@koubei.com �ȵ�...
	 * @return ����������ǰ׺�ʼ���ַ, �� *********@koubei.com.
	 * @version 1.0 (2006.11.27) Wilson Lin
	 **************************************************************************/
	public static String getHideEmailPrefix(String email) {
		if (null != email) {
			int index = email.lastIndexOf('@');
			if (index > 0) {
				email = repeat("*", index).concat(email.substring(index));
			}
		}
		return email;
	}

	/***************************************************************************
	 * repeat - ͨ��Դ�ַ����ظ�����N������µ��ַ�����
	 * 
	 * @param src
	 *            - Դ�ַ��� ����: �ո�(" "), �Ǻ�("*"), "�㽭" �ȵ�...
	 * @param num
	 *            - �ظ����ɴ���
	 * @return ���������ɵ��ظ��ַ���
	 * @version 1.0 (2006.10.10) Wilson Lin
	 **************************************************************************/
	public static String repeat(String src, int num) {
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < num; i++)
			s.append(src);
		return s.toString();
	}

	/**
	 * ����ָ�����ַ���Դ�ַ����ָ��һ������
	 * 
	 * @param src
	 * @return
	 */
	public static List<String> parseString2ListByCustomerPattern(String pattern, String src) {

		if (src == null)
			return null;
		List<String> list = new ArrayList<String>();
		String[] result = src.split(pattern);
		for (int i = 0; i < result.length; i++) {
			list.add(result[i]);
		}
		return list;
	}

	/**
	 * ����ָ�����ַ���Դ�ַ����ָ��һ������
	 * 
	 * @param src
	 * @return
	 */
	public static List<String> parseString2ListByPattern(String src) {
		String pattern = "��|,|��|��";
		return parseString2ListByCustomerPattern(pattern, src);
	}

	/**
	 * ��ʽ��һ��float
	 * 
	 * @param format
	 *            Ҫ��ʽ���ɵĸ�ʽ such as #.00, #.#
	 */

	public static String formatFloat(float f, String format) {
		DecimalFormat df = new DecimalFormat(format);
		return df.format(f);
	}

	/**
	 * �ж��Ƿ��ǿ��ַ��� null��"" ������ true
	 * 
	 * @author Robin Chang
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		if (s != null && !s.equals("")) {
			return false;
		}
		return true;
	}

	/**
	 * �Զ���ķָ��ַ������� ����: 1,2,3 =>[1,2,3] 3��Ԫ�� ,2,3=>[,2,3] 3��Ԫ�� ,2,3,=>[,2,3,] 4��Ԫ�� ,,,=>[,,,] 4��Ԫ��
	 * 
	 * 5.22�㷨�޸ģ�Ϊ����ٶȲ���������ʽ ���������,,����""Ԫ��
	 * 
	 * @param split
	 *            �ָ��ַ� Ĭ��,
	 * @param src
	 *            �����ַ���
	 * @return �ָ����list
	 * @author Robin
	 */
	public static List<String> splitToList(String split, String src) {
		// Ĭ��,
		String sp = ",";
		if (split != null && split.length() == 1) {
			sp = split;
		}
		List<String> r = new ArrayList<String>();
		int lastIndex = -1;
		int index = src.indexOf(sp);
		if (-1 == index && src != null) {
			r.add(src);
			return r;
		}
		while (index >= 0) {
			if (index > lastIndex) {
				r.add(src.substring(lastIndex + 1, index));
			} else {
				r.add("");
			}

			lastIndex = index;
			index = src.indexOf(sp, index + 1);
			if (index == -1) {
				r.add(src.substring(lastIndex + 1, src.length()));
			}
		}
		return r;
	}

	/**
	 * �� ��=ֵ ������ת�����ַ��� (a=1,b=2 =>a=1&b=2)
	 * 
	 * @param map
	 * @return
	 */
	public static String linkedHashMapToString(LinkedHashMap<String, String> map) {
		if (map != null && map.size() > 0) {
			String result = "";
			Iterator it = map.keySet().iterator();
			while (it.hasNext()) {
				String name = (String) it.next();
				String value = (String) map.get(name);
				result += (result.equals("")) ? "" : "&";
				result += String.format("%s=%s", name, value);
			}
			return result;
		}
		return null;
	}

	/**
	 * �����ַ������� ����=ֵ�Ĳ����� (a=1&b=2 => a=1,b=2)
	 * 
	 * @see test.koubei.util.StringUtilTest#testParseStr()
	 * @param str
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> toLinkedHashMap(String str) {
		if (str != null && !str.equals("") && str.indexOf("=") > 0) {
			LinkedHashMap result = new LinkedHashMap();

			String name = null;
			String value = null;
			int i = 0;
			while (i < str.length()) {
				char c = str.charAt(i);
				switch (c) {
				case 61: // =
					value = "";
					break;
				case 38: // &
					if (name != null && value != null && !name.equals("")) {
						result.put(name, value);
					}
					name = null;
					value = null;
					break;
				default:
					if (value != null) {
						value = (value != null) ? (value + c) : "" + c;
					} else {
						name = (name != null) ? (name + c) : "" + c;
					}
				}
				i++;

			}

			if (name != null && value != null && !name.equals("")) {
				result.put(name, value);
			}

			return result;

		}
		return null;
	}

	/**
	 * ��������Ķ�����ͺ��±귵��һ��ֵ
	 * 
	 * @param captions
	 *            ����:"��,���ɾ�,һ��,�Ƚ���"
	 * @param index
	 *            1
	 * @return һ��
	 */
	public static String getCaption(String captions, int index) {
		if (index > 0 && captions != null && !captions.equals("")) {
			String[] ss = captions.split(",");
			if (ss != null && ss.length > 0 && index < ss.length) {
				return ss[index];
			}
		}
		return null;
	}

	/**
	 * ����ת�ַ���,���num<=0 �����"";
	 * 
	 * @param num
	 * @return
	 */
	public static String numberToString(Object num) {
		if (num == null) {
			return null;
		} else if (num instanceof Integer && (Integer) num > 0) {
			return Integer.toString((Integer) num);
		} else if (num instanceof Long && (Long) num > 0) {
			return Long.toString((Long) num);
		} else if (num instanceof Float && (Float) num > 0) {
			return Float.toString((Float) num);
		} else if (num instanceof Double && (Double) num > 0) {
			return Double.toString((Double) num);
		} else {
			return "";
		}
	}

	/**
	 * ����ת�ַ���
	 * 
	 * @param money
	 * @param style
	 *            ��ʽ [default]Ҫ��ʽ���ɵĸ�ʽ such as #.00, #.#
	 * @return
	 */

	public static String moneyToString(Object money, String style) {
		if (money != null && style != null && (money instanceof Double || money instanceof Float)) {
			Double num = (Double) money;

			if (style.equalsIgnoreCase("default")) {
				// ȱʡ��ʽ 0 ����� ,���û�����С��λ�����.0
				if (num == 0) {
					// �����0
					return "";
				} else if ((num * 10 % 10) == 0) {
					// û��С��
					return Integer.toString((int) num.intValue());
				} else {
					// ��С��
					return num.toString();
				}

			} else {
				DecimalFormat df = new DecimalFormat(style);
				return df.format(num);
			}
		}
		return null;
	}

	/**
	 * ��sou���Ƿ����finds ���ָ����finds�ַ�����һ����sou���ҵ�,����true;
	 * 
	 * @param sou
	 * @param find
	 * @return
	 */
	public static boolean strPos(String sou, String... finds) {
		if (sou != null && finds != null && finds.length > 0) {
			for (int i = 0; i < finds.length; i++) {
				if (sou.indexOf(finds[i]) > -1)
					return true;
			}
		}
		return false;
	}

	public static boolean strPos(String sou, List<String> finds) {
		if (sou != null && finds != null && finds.size() > 0) {
			for (String s : finds) {
				if (sou.indexOf(s) > -1)
					return true;
			}
		}
		return false;
	}

	public static boolean strPos(String sou, String finds) {
		List<String> t = splitToList(",", finds);
		return strPos(sou, t);
	}

	/**
	 * �ж������ַ����Ƿ���� �����Ϊnull���ж�Ϊ���,һ��Ϊnull��һ��not null���жϲ���� �������s1=s2�����
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static boolean equals(String s1, String s2) {
		if (StrUtils.isEmpty(s1) && StrUtils.isEmpty(s2)) {
			return true;
		} else if (!StrUtils.isEmpty(s1) && !StrUtils.isEmpty(s2)) {
			return s1.equals(s2);
		}
		return false;
	}

	public static int toInt(String s) {
		if (s != null && !"".equals(s.trim())) {
			try {
				return Integer.parseInt(s);
			} catch (Exception e) {
				return 0;
			}
		}
		return 0;
	}

	public static double toDouble(String s) {
		if (s != null && !"".equals(s.trim())) {
			return Double.parseDouble(s);
		}
		return 0;
	}

	/**
	 * ��xml תΪobject
	 * 
	 * @param xml
	 * @return
	 */
	public static Object xmlToObject(String xml) {
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(xml.getBytes("UTF8"));
			XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(in));
			return decoder.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static long toLong(String s) {
		try {
			if (s != null && !"".equals(s.trim()))
				return Long.parseLong(s);
		} catch (Exception exception) {
		}
		return 0L;
	}

	public static String simpleEncrypt(String str) {
		if (str != null && str.length() > 0) {
			// str = str.replaceAll("0","a");
			str = str.replaceAll("1", "b");
			// str = str.replaceAll("2","c");
			str = str.replaceAll("3", "d");
			// str = str.replaceAll("4","e");
			str = str.replaceAll("5", "f");
			str = str.replaceAll("6", "g");
			str = str.replaceAll("7", "h");
			str = str.replaceAll("8", "i");
			str = str.replaceAll("9", "j");
		}
		return str;

	}

	/**
	 * �����û������URL��ַ�������û���棩 Ŀǰֻ�����http��www��ͷ��URL��ַ ���������õ�������ʽ�����������ڶ������ϸ�ĵط�����:ѭ����listҳ���
	 * 
	 * @author fengliang
	 * @param str
	 *            ��Ҫ������ַ���
	 * @return ���ش������ַ���
	 */
	public static String removeURL(String str) {
		if (str != null)
			str = str.toLowerCase().replaceAll("(http|www|com|cn|org|\\.)+", "");
		return str;
	}

	/**
	 * �漴����ָ��λ���ĺ�������֤���ַ���
	 * 
	 * @author Peltason
	 * @date 2007-5-9
	 * @param bit
	 *            ָ��������֤��λ��
	 * @return String
	 */
	public static String numRandom(int bit) {
		if (bit == 0)
			bit = 6; // Ĭ��6λ
		String str = "";
		str = "0123456789";// ��ʼ������
		return RandomStringUtils.random(bit, str);// ����6λ���ַ���
	}

	/**
	 * �漴����ָ��λ���ĺ���֤���ַ���
	 * 
	 * @author Peltason
	 * 
	 * @date 2007-5-9
	 * @param bit
	 *            ָ��������֤��λ��
	 * @return String
	 */
	public static String random(int bit) {
		if (bit == 0)
			bit = 6; // Ĭ��6λ
		// ��Ϊo��0,l��1��������,����,ȥ����Сд��o��l
		String str = "";
		str = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz";// ��ʼ������
		return RandomStringUtils.random(bit, str);// ����6λ���ַ���
	}

	/**
	 * Wapҳ��ķǷ��ַ����
	 * 
	 * @author hugh115
	 * @date 2007-06-29
	 * @param str
	 * @return
	 */
	public static String replaceWapStr(String str) {
		if (str != null) {
			str = str.replaceAll("<span class=\"keyword\">", "");
			str = str.replaceAll("</span>", "");
			str = str.replaceAll("<strong class=\"keyword\">", "");
			str = str.replaceAll("<strong>", "");
			str = str.replaceAll("</strong>", "");

			str = str.replace('$', '��');

			str = str.replaceAll("&amp;", "��");
			str = str.replace('&', '��');

			str = str.replace('<', '��');

			str = str.replace('>', '��');

		}
		return str;
	}

	/**
	 * �ַ���תfloat ����쳣����0.00
	 * 
	 * @param s
	 *            ������ַ���
	 * @return ת�����float
	 */
	public static Float toFloat(String s) {
		try {
			return Float.parseFloat(s);
		} catch (NumberFormatException e) {
			return new Float(0);
		}
	}

	/**
	 * ҳ����ȥ���ַ����еĿո񡢻س������з����Ʊ��
	 * 
	 * @author shazao
	 * @date 2007-08-17
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			str = m.replaceAll("");
		}
		return str;
	}

	/**
	 * ȫ�����ɰ��
	 * 
	 * @author bailong
	 * @date 2007-08-29
	 * @param str
	 * @return
	 */
	public static String Q2B(String QJstr) {
		String outStr = "";
		String Tstr = "";
		byte[] b = null;
		for (int i = 0; i < QJstr.length(); i++) {
			try {
				Tstr = QJstr.substring(i, i + 1);
				b = Tstr.getBytes("unicode");
			} catch (java.io.UnsupportedEncodingException e) {
			}
			if (b[3] == -1) {
				b[2] = (byte) (b[2] + 32);
				b[3] = 0;
				try {
					outStr = outStr + new String(b, "unicode");
				} catch (java.io.UnsupportedEncodingException ex) {
				}
			} else {
				outStr = outStr + Tstr;
			}
		}
		return outStr;
	}

	/**
	 * 
	 * ת������
	 * 
	 * @param s
	 *            Դ�ַ���
	 * @param fencode
	 *            Դ�����ʽ
	 * @param bencode
	 *            Ŀ������ʽ
	 * @return Ŀ�����
	 */
	public static String changCoding(String s, String fencode, String bencode) {
		String str;
		try {
			if (StrUtils.isNotEmpty(s)) {
				str = new String(s.getBytes(fencode), bencode);
			} else {
				str = "";
			}
			return str;
		} catch (UnsupportedEncodingException e) {
			return s;
		}
	}

	/**
	 * @param str
	 * @return
	 ************************************************************************* 
	 */
	public static String removeHTMLLableExe(String str) {
		str = stringReplace(str, ">\\s*<", "><");
		str = stringReplace(str, "&nbsp;", " ");// �滻�ո�
		str = stringReplace(str, "<br ?/?>", "\n");// ȥ<br><br />
		str = stringReplace(str, "<([^<>]+)>", "");// ȥ��<>�ڵ��ַ�
		str = stringReplace(str, "\\s\\s\\s*", " ");// ������հױ��һ���ո�
		str = stringReplace(str, "^\\s*", "");// ȥ��ͷ�Ŀհ�
		str = stringReplace(str, "\\s*$", "");// ȥ��β�Ŀհ�
		str = stringReplace(str, " +", " ");
		return str;
	}

	/**
	 * ��ȥhtml��ǩ
	 * 
	 * @param str
	 *            Դ�ַ���
	 * @return Ŀ���ַ���
	 */
	public static String removeHTMLLable(String str) {
		str = stringReplace(str, "\\s", "");// ȥ��ҳ���Ͽ��������ַ�
		str = stringReplace(str, "<br ?/?>", "\n");// ȥ<br><br />
		str = stringReplace(str, "<([^<>]+)>", "");// ȥ��<>�ڵ��ַ�
		str = stringReplace(str, "&nbsp;", " ");// �滻�ո�
		str = stringReplace(str, "&(\\S)(\\S?)(\\S?)(\\S?);", "");// ȥ<br><br />
		return str;
	}

	/**
	 * ȥ��HTML��ǩ֮����ַ���
	 * 
	 * @param str
	 *            Դ�ַ���
	 * @return Ŀ���ַ���
	 */
	public static String removeOutHTMLLable(String str) {
		str = stringReplace(str, ">([^<>]+)<", "><");
		str = stringReplace(str, "^([^<>]+)<", "<");
		str = stringReplace(str, ">([^<>]+)$", ">");
		return str;
	}

	/**
	 * 
	 * �ַ����滻
	 * 
	 * @param str
	 *            Դ�ַ���
	 * @param sr
	 *            ������ʽ��ʽ
	 * @param sd
	 *            �滻�ı�
	 * @return �����
	 */
	public static String stringReplace(String str, String sr, String sd) {
		String regEx = sr;
		Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(str);
		str = m.replaceAll(sd);
		return str;
	}

	/**
	 * 
	 * ��html��ʡ��д���滻�ɷ�ʡ��д��
	 * 
	 * @param str
	 *            html�ַ���
	 * @param pt
	 *            ��ǩ��table
	 * @return �����
	 */
	public static String fomateToFullForm(String str, String pt) {
		String regEx = "<" + pt + "\\s+([\\S&&[^<>]]*)/>";
		Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(str);
		String[] sa = null;
		String sf = "";
		String sf2 = "";
		String sf3 = "";
		for (; m.find();) {
			sa = p.split(str);
			if (sa == null) {
				break;
			}
			sf = str.substring(sa[0].length(), str.indexOf("/>", sa[0].length()));
			sf2 = sf + "></" + pt + ">";
			sf3 = str.substring(sa[0].length() + sf.length() + 2);
			str = sa[0] + sf2 + sf3;
			sa = null;
		}
		return str;
	}

	/**
	 * 
	 * �õ��ַ������Ӵ�λ������
	 * 
	 * @param str
	 *            �ַ���
	 * @param sub
	 *            �Ӵ�
	 * @param b
	 *            true�Ӵ�ǰ��,false�Ӵ����
	 * @return �ַ������Ӵ�λ������
	 */
	public static int[] getSubStringPos(String str, String sub, boolean b) {
		// int[] i = new int[(new Integer((str.length()-stringReplace( str , sub
		// , "" ).length())/sub.length())).intValue()] ;
		String[] sp = null;
		int l = sub.length();
		sp = splitString(str, sub);
		if (sp == null) {
			return null;
		}
		int[] ip = new int[sp.length - 1];
		for (int i = 0; i < sp.length - 1; i++) {
			ip[i] = sp[i].length() + l;
			if (i != 0) {
				ip[i] += ip[i - 1];
			}
		}
		if (b) {
			for (int j = 0; j < ip.length; j++) {
				ip[j] = ip[j] - l;
			}
		}
		return ip;
	}

	/**
	 * 
	 * ����������ʽ�ָ��ַ���
	 * 
	 * @param str
	 *            Դ�ַ���
	 * @param ms
	 *            ������ʽ
	 * @return Ŀ���ַ�����
	 */
	public static String[] splitString(String str, String ms) {
		String regEx = ms;
		Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		String[] sp = p.split(str);
		return sp;
	}

	/**
	 * ����������ʽ��ȡ�ַ���,��ͬ���ַ���ֻ����һ��
	 * 
	 * @param strԴ�ַ���
	 * @param pattern
	 *            ������ʽ
	 * @return Ŀ���ַ���������
	 ************************************************************************* 
	 */

	// �ﴫ��һ���ַ������ѷ���pattern��ʽ���ַ��������ַ�������
	// java.util.regex��һ����������ʽ�����Ƶ�ģʽ�����ַ�������ƥ�乤��������
	public static String[] getStringArrayByPattern(String str, String pattern) {
		Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(str);
		// ����
		Set<String> result = new HashSet<String>();// Ŀ���ǣ���ͬ���ַ���ֻ����һ�������� ���ظ�Ԫ��
		// boolean find() ������Ŀ���ַ����������һ��ƥ���Ӵ���
		while (matcher.find()) {
			for (int i = 0; i < matcher.groupCount(); i++) { // int groupCount()
																// ���ص�ǰ��������õ�ƥ�����������
				// org.jeecgframework.core.util.LogUtil.info(matcher.group(i));
				result.add(matcher.group(i));

			}
		}
		String[] resultStr = null;
		if (result.size() > 0) {
			resultStr = new String[result.size()];
			return result.toArray(resultStr);// ��Set resultת��ΪString[] resultStr
		}
		return resultStr;

	}

	/**
	 * �õ���һ��b,e֮����ַ���,������e����Ӵ�
	 * 
	 * @param s
	 *            Դ�ַ���
	 * @param b
	 *            ��־��ʼ
	 * @param e
	 *            ��־����
	 * @return b,e֮����ַ���
	 */

	/*
	 * String aaa="abcdefghijklmn"; String[] bbb=StringProcessor.midString(aaa, "b","l"); org.jeecgframework.core.util.LogUtil.info("bbb[0]:"+bbb[0]);//cdefghijk org.jeecgframework.core.util.LogUtil.info("bbb[1]:"+bbb[1]);//lmn ����������ǵõ��ڶ��������͵���������֮����ַ���,����Ԫ��0;Ȼ���Ԫ��0������ַ���֮���,����Ԫ��1
	 */

	/*
	 * String aaa="abcdefgllhijklmn5465"; String[] bbb=StringProcessor.midString(aaa, "b","l"); //ab cdefg llhijklmn5465 // Ԫ��0 Ԫ��1
	 */
	public static String[] midString(String s, String b, String e) {
		int i = s.indexOf(b) + b.length();
		int j = s.indexOf(e, i);
		String[] sa = new String[2];
		if (i < b.length() || j < i + 1 || i > j) {
			sa[1] = s;
			sa[0] = null;
			return sa;
		} else {
			sa[0] = s.substring(i, j);
			sa[1] = s.substring(j);
			return sa;
		}
	}

	/**
	 * ����ǰһ��������е�������ʽ���
	 * 
	 * @param s
	 * @param pf
	 * @param pb
	 * @param start
	 * @return
	 */
	public static String stringReplace(String s, String pf, String pb, int start) {
		Pattern pattern_hand = Pattern.compile(pf);
		Matcher matcher_hand = pattern_hand.matcher(s);
		int gc = matcher_hand.groupCount();
		int pos = start;
		String sf1 = "";
		String sf2 = "";
		String sf3 = "";
		int if1 = 0;
		String strr = "";
		while (matcher_hand.find(pos)) {
			sf1 = matcher_hand.group();
			if1 = s.indexOf(sf1, pos);
			if (if1 >= pos) {
				strr += s.substring(pos, if1);
				pos = if1 + sf1.length();
				sf2 = pb;
				for (int i = 1; i <= gc; i++) {
					sf3 = "\\" + i;
					sf2 = replaceAll(sf2, sf3, matcher_hand.group(i));
				}
				strr += sf2;
			} else {
				return s;
			}
		}
		strr = s.substring(0, start) + strr;
		return strr;
	}

	/**
	 * ���ı��滻
	 * 
	 * @param s
	 *            Դ�ַ���
	 * @param sf
	 *            ���ַ���
	 * @param sb
	 *            �滻�ַ���
	 * @return �滻����ַ���
	 */
	public static String replaceAll(String s, String sf, String sb) {
		int i = 0, j = 0;
		int l = sf.length();
		boolean b = true;
		boolean o = true;
		String str = "";
		do {
			j = i;
			i = s.indexOf(sf, j);
			if (i > j) {
				str += s.substring(j, i);
				str += sb;
				i += l;
				o = false;
			} else {
				str += s.substring(j);
				b = false;
			}
		} while (b);
		if (o) {
			str = s;
		}
		return str;
	}
	
	// �ַ������滻
	public static String replace(String strSource, String strOld, String strNew) {
		if (strSource == null) {
			return null;
		}
		int i = 0;
		if ((i = strSource.indexOf(strOld, i)) >= 0) {
			char[] cSrc = strSource.toCharArray();
			char[] cTo = strNew.toCharArray();
			int len = strOld.length();
			StringBuffer buf = new StringBuffer(cSrc.length);
			buf.append(cSrc, 0, i).append(cTo);
			i += len;
			int j = i;
			while ((i = strSource.indexOf(strOld, i)) > 0) {
				buf.append(cSrc, j, i - j).append(cTo);
				i += len;
				j = i;
			}
			buf.append(cSrc, j, cSrc.length - j);
			return buf.toString();
		}
		return strSource;
	}

	/**
	 * �ж��Ƿ�������ַ�����ʽƥ��
	 * 
	 * @param str
	 *            �ַ���
	 * @param pattern
	 *            ������ʽ��ʽ
	 * @return �Ƿ�ƥ����true,��false
	 */
	public static boolean isMatch(String str, String pattern) {
		Pattern pattern_hand = Pattern.compile(pattern);
		Matcher matcher_hand = pattern_hand.matcher(str);
		boolean b = matcher_hand.matches();
		return b;
	}

	/**
	 * ��ȡ�ַ���
	 * 
	 * @param s
	 *            Դ�ַ���
	 * @param jmp
	 *            ����jmp
	 * @param sb
	 *            ȡ��sb
	 * @param se
	 *            ��se
	 * @return ֮����ַ���
	 */
	public static String subStringExe(String s, String jmp, String sb, String se) {
		if (isEmpty(s)) {
			return "";
		}
		int i = s.indexOf(jmp);
		if (i >= 0 && i < s.length()) {
			s = s.substring(i + 1);
		}
		i = s.indexOf(sb);
		if (i >= 0 && i < s.length()) {
			s = s.substring(i + 1);
		}
		if (se == "") {
			return s;
		} else {
			i = s.indexOf(se);
			if (i >= 0 && i < s.length()) {
				s = s.substring(i + 1);
			}
			return s;
		}
	}

	/**
	 * ************************************************************************* ��Ҫͨ��URL��������ݽ��б���
	 * 
	 * @param Դ�ַ���
	 * @return �������������
	 ************************************************************************* 
	 */
	public static String URLEncode(String src) {
		String return_value = "";
		try {
			if (src != null) {
				return_value = URLEncoder.encode(src, "GBK");

			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return_value = src;
		}

		return return_value;
	}

	/**
	 * *************************************************************************
	 * 
	 * @author ��� 2007.4.18
	 * @param ����
	 *            &#31119;test&#29031;&#27004;&#65288;&#21271;&#22823;&#38376;&# 24635 ;&#24215;&#65289;&#31119;
	 * @return �������������
	 ************************************************************************* 
	 */
	public static String getGBK(String str) {

		return transfer(str);
	}

	public static String transfer(String str) {
		Pattern p = Pattern.compile("&#\\d+;");
		Matcher m = p.matcher(str);
		while (m.find()) {
			String old = m.group();
			str = str.replaceAll(old, getChar(old));
		}
		return str;
	}

	public static String getChar(String str) {
		String dest = str.substring(2, str.length() - 1);
		char ch = (char) Integer.parseInt(dest);
		return "" + ch;
	}

	/**
	 * yahoo��ҳ���и��ַ���.
	 * 
	 * @author yxg
	 * @date 2007-09-17
	 * @param str
	 * @return
	 */
	public static String subYhooString(String subject, int size) {
		subject = subject.substring(1, size);
		return subject;
	}

	public static String subYhooStringDot(String subject, int size) {
		subject = subject.substring(1, size) + "...";
		return subject;
	}

	/**
	 * ���ͷ���(ͨ��)����listת�����ԡ�,��������ַ��� ����ʱע�����ͳ�ʼ�����������ͣ� �磺List<Integer> intList = new ArrayList<Integer>(); ���÷�����StringUtil.listTtoString(intList); Ч�ʣ�list��4����Ϣ��1000000�ε���ʱ��Ϊ850ms����
	 * 
	 * @author fengliang
	 * @serialData 2008-01-09
	 * @param <T>
	 *            ����
	 * @param list
	 *            list�б�
	 * @return �ԡ�,��������ַ���
	 */
	public static <T> String listTtoString(List<T> list) {
		if (list == null || list.size() < 1)
			return "";
		Iterator<T> i = list.iterator();
		if (!i.hasNext())
			return "";
		StringBuilder sb = new StringBuilder();
		for (;;) {
			T e = i.next();
			sb.append(e);
			if (!i.hasNext())
				return sb.toString();
			sb.append(",");
		}
	}

	/**
	 * ����������ת�����ԡ�,��������ַ���
	 * 
	 * @author fengliang
	 * @serialData 2008-01-08
	 * @param a
	 *            ����a
	 * @return �ԡ�,��������ַ���
	 */
	public static String intArraytoString(int[] a) {
		if (a == null)
			return "";
		int iMax = a.length - 1;
		if (iMax == -1)
			return "";
		StringBuilder b = new StringBuilder();
		for (int i = 0;; i++) {
			b.append(a[i]);
			if (i == iMax)
				return b.toString();
			b.append(",");
		}
	}

	/**
	 * �ж����������ظ�
	 * 
	 * @author ɳ��
	 * @Date 2008-04-17
	 */
	public static boolean isContentRepeat(String content) {
		int similarNum = 0;
		int forNum = 0;
		int subNum = 0;
		int thousandNum = 0;
		String startStr = "";
		String nextStr = "";
		boolean result = false;
		float endNum = (float) 0.0;
		if (content != null && content.length() > 0) {
			if (content.length() % 1000 > 0)
				thousandNum = (int) Math.floor(content.length() / 1000) + 1;
			else
				thousandNum = (int) Math.floor(content.length() / 1000);
			if (thousandNum < 3)
				subNum = 100 * thousandNum;
			else if (thousandNum < 6)
				subNum = 200 * thousandNum;
			else if (thousandNum < 9)
				subNum = 300 * thousandNum;
			else
				subNum = 3000;
			for (int j = 1; j < subNum; j++) {
				if (content.length() % j > 0)
					forNum = (int) Math.floor(content.length() / j) + 1;
				else
					forNum = (int) Math.floor(content.length() / j);
				if (result || j >= content.length())
					break;
				else {
					for (int m = 0; m < forNum; m++) {
						if (m * j > content.length() || (m + 1) * j > content.length() || (m + 2) * j > content.length())
							break;
						startStr = content.substring(m * j, (m + 1) * j);
						nextStr = content.substring((m + 1) * j, (m + 2) * j);
						if (startStr.equals(nextStr)) {
							similarNum = similarNum + 1;
							endNum = (float) similarNum / forNum;
							if (endNum > 0.4) {
								result = true;
								break;
							}
						} else
							similarNum = 0;
					}
				}
			}
		}
		return result;
	}

	/**
	 * �ж��Ƿ��ǿ��ַ��� null��"" null����result,���򷵻��ַ���
	 * 
	 * @param s
	 * @return
	 */
	public static String isEmpty(String s, String result) {
		if (s != null && !s.equals("")) {
			return s;
		}
		return result;
	}

	/**
	 * �ж϶����Ƿ�Ϊ��
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(Object str) {
		boolean flag = true;
		if (str != null && !str.equals("")) {
			if (str.toString().length() > 0) {
				flag = true;
			}
		} else {
			flag = false;
		}
		return flag;
	}

	/**
	 * ȫ���ַ������ַ�
	 * 
	 * @author shazao
	 * @date 2008-04-03
	 * @param str
	 * @return
	 */
	public static String full2Half(String str) {
		if (str == null || "".equals(str))
			return "";
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);

			if (c >= 65281 && c < 65373)
				sb.append((char) (c - 65248));
			else
				sb.append(str.charAt(i));
		}

		return sb.toString();

	}

	/**
	 * ȫ������תΪ���
	 * 
	 * @author shazao
	 * @date 2007-11-29
	 * @param str
	 * @return
	 */
	public static String replaceBracketStr(String str) {
		if (str != null && str.length() > 0) {
			str = str.replaceAll("��", "(");
			str = str.replaceAll("��", ")");
		}
		return str;
	}

	/**
	 * �����ַ�������map��ֵ��(����a=1&b=2 => a=1,b=2)
	 * 
	 * @param query
	 *            Դ�����ַ���
	 * @param split1
	 *            ��ֵ��֮��ķָ���������&��
	 * @param split2
	 *            key��value֮��ķָ���������=��
	 * @param dupLink
	 *            �ظ��������Ĳ���ֵ֮������ӷ������Ӻ���ַ�����Ϊ�ò����Ĳ���ֵ����Ϊnull null���������ظ����������֣��򿿺�Ĳ���ֵ�Ḳ�ǵ���ǰ�Ĳ���ֵ��
	 * @return map
	 * @author sky
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseQuery(String query, char split1, char split2, String dupLink) {
		if (!isEmpty(query) && query.indexOf(split2) > 0) {
			Map<String, String> result = new HashMap();

			String name = null;
			String value = null;
			String tempValue = "";
			int len = query.length();
			for (int i = 0; i < len; i++) {
				char c = query.charAt(i);
				if (c == split2) {
					value = "";
				} else if (c == split1) {
					if (!isEmpty(name) && value != null) {
						if (dupLink != null) {
							tempValue = result.get(name);
							if (tempValue != null) {
								value += dupLink + tempValue;
							}
						}
						result.put(name, value);
					}
					name = null;
					value = null;
				} else if (value != null) {
					value += c;
				} else {
					name = (name != null) ? (name + c) : "" + c;
				}
			}

			if (!isEmpty(name) && value != null) {
				if (dupLink != null) {
					tempValue = result.get(name);
					if (tempValue != null) {
						value += dupLink + tempValue;
					}
				}
				result.put(name, value);
			}

			return result;
		}
		return null;
	}

	/**
	 * ��list �ô���ķָ�����װΪString
	 * 
	 * @param list
	 * @param slipStr
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public static String listToStringSlipStr(List list, String slipStr) {
		StringBuffer returnStr = new StringBuffer();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				returnStr.append(list.get(i)).append(slipStr);
			}
		}
		if (returnStr.toString().length() > 0)
			return returnStr.toString().substring(0, returnStr.toString().lastIndexOf(slipStr));
		else
			return "";
	}

	/**
	 * ��ȡ��start��ʼ��*�滻len�����Ⱥ���ַ���
	 * 
	 * @param str
	 *            Ҫ�滻���ַ���
	 * @param start
	 *            ��ʼλ��
	 * @param len
	 *            ����
	 * @return �滻����ַ���
	 */
	public static String getMaskStr(String str, int start, int len) {
		if (StrUtils.isEmpty(str)) {
			return str;
		}
		if (str.length() < start) {
			return str;
		}

		// ��ȡ*֮ǰ���ַ���
		String ret = str.substring(0, start);

		// ��ȡ����ܴ��*����
		int strLen = str.length();
		if (strLen < start + len) {
			len = strLen - start;
		}

		// �滻��*
		for (int i = 0; i < len; i++) {
			ret += "*";
		}

		// ����*֮����ַ���
		if (strLen > start + len) {
			ret += str.substring(start + len);
		}

		return ret;
	}

	/**
	 * ���ݴ���ķָ����,�Ѵ�����ַ����ָ�ΪList�ַ���
	 * 
	 * @param slipStr
	 *            �ָ����ַ���
	 * @param src
	 *            �ַ���
	 * @return �б�
	 */
	public static List<String> stringToStringListBySlipStr(String slipStr, String src) {

		if (src == null)
			return null;
		List<String> list = new ArrayList<String>();
		String[] result = src.split(slipStr);
		for (int i = 0; i < result.length; i++) {
			list.add(result[i]);
		}
		return list;
	}

	/**
	 * ��ȡ�ַ���
	 * 
	 * @param str
	 *            ԭʼ�ַ���
	 * @param len
	 *            Ҫ��ȡ�ĳ���
	 * @param tail
	 *            �������ϵĺ�׺
	 * @return ��ȡ����ַ���
	 */
	public static String getHtmlSubString(String str, int len, String tail) {
		if (str == null || str.length() <= len) {
			return str;
		}
		int length = str.length();
		char c = ' ';
		String tag = null;
		String name = null;
		int size = 0;
		String result = "";
		boolean isTag = false;
		List<String> tags = new ArrayList<String>();
		int i = 0;
		for (int end = 0, spanEnd = 0; i < length && len > 0; i++) {
			c = str.charAt(i);
			if (c == '<') {
				end = str.indexOf('>', i);
			}

			if (end > 0) {
				// ��ȡ��ǩ
				tag = str.substring(i, end + 1);
				int n = tag.length();
				if (tag.endsWith("/>")) {
					isTag = true;
				} else if (tag.startsWith("</")) { // ������
					name = tag.substring(2, end - i);
					size = tags.size() - 1;
					// ��ջȡ��html��ʼ��ǩ
					if (size >= 0 && name.equals(tags.get(size))) {
						isTag = true;
						tags.remove(size);
					}
				} else { // ��ʼ��
					spanEnd = tag.indexOf(' ', 0);
					spanEnd = spanEnd > 0 ? spanEnd : n;
					name = tag.substring(1, spanEnd);
					if (name.trim().length() > 0) {
						// ����н�������Ϊhtml��ǩ
						spanEnd = str.indexOf("</" + name + ">", end);
						if (spanEnd > 0) {
							isTag = true;
							tags.add(name);
						}
					}
				}
				// ��html��ǩ�ַ�
				if (!isTag) {
					if (n >= len) {
						result += tag.substring(0, len);
						break;
					} else {
						len -= n;
					}
				}

				result += tag;
				isTag = false;
				i = end;
				end = 0;
			} else { // ��html��ǩ�ַ�
				len--;
				result += c;
			}
		}
		// ���δ������html��ǩ
		for (String endTag : tags) {
			result += "</" + endTag + ">";
		}
		if (i < length) {
			result += tail;
		}
		return result;
	}

	public static String getProperty(String property) {
		if (property.contains("_")) {
			return property.replaceAll("_", "\\.");
		}
		return property;
	}

	/**
	 * ����ǰ̨encodeURIComponent�����Ĳ���
	 * 
	 * @param encodeURIComponent
	 *            (encodeURIComponent(no))
	 * @return
	 */
	public static String getEncodePra(String property) {
		String trem = "";
		if (isNotEmpty(property)) {
			try {
				trem = URLDecoder.decode(property, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return trem;
	}

	// �ж�һ���ַ����Ƿ�Ϊ����
	public boolean isDigit(String strNum) {
		Pattern pattern = Pattern.compile("[0-9]{1,}");
		Matcher matcher = pattern.matcher((CharSequence) strNum);
		return matcher.matches();
	}

	// ��ȡ����
	public String getNumbers(String content) {
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			return matcher.group(0);
		}
		return "";
	}

	// ��ȡ������
	public String splitNotNumber(String content) {
		Pattern pattern = Pattern.compile("\\D+");
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			return matcher.group(0);
		}
		return "";
	}

	/**
	 * �ж�ĳ���ַ����Ƿ������������
	 * 
	 * @param stringArray
	 *            ԭ����
	 * @param source
	 *            ���ҵ��ַ���
	 * @return �Ƿ��ҵ�
	 */
	public static boolean contains(String[] stringArray, String source) {
		// ת��Ϊlist
		List<String> tempList = Arrays.asList(stringArray);

		// ����list�İ�������,�����ж�
		if (tempList.contains(source)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * html �����Ǹ�ʽ���õ�
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String formatHtml(String str) throws Exception {
		Document document = null;
		document = DocumentHelper.parseText(str);

		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("utf-8");
		StringWriter writer = new StringWriter();

		HTMLWriter htmlWriter = new HTMLWriter(writer, format);

		htmlWriter.write(document);
		htmlWriter.close();
		return writer.toString();
	}
	
	/**
	 * ����ĸ��д
	 * @param realName
	 * @return
	 */
	public static String firstUpperCase(String realName) {
		return StringUtils.replaceChars(realName, realName.substring(0, 1),realName.substring(0, 1).toUpperCase());
	}

	/**
	 * ����ĸСд
	 * @param realName
	 * @return
	 */
	public static String firstLowerCase(String realName) {
		return StringUtils.replaceChars(realName, realName.substring(0, 1),realName.substring(0, 1).toLowerCase());
	}
	
	/**
	 * �ж�������ǲ���java�Դ�����
	 * @param clazz
	 * @return
	 */
	public static boolean isJavaClass(Class<?> clazz) {
		boolean isBaseClass = false;
		if(clazz.isArray()){
			isBaseClass = false;
		}else if (clazz.isPrimitive()||clazz.getPackage()==null
				|| clazz.getPackage().getName().equals("java.lang")
				|| clazz.getPackage().getName().equals("java.math")
				|| clazz.getPackage().getName().equals("java.util")) {
			isBaseClass =  true;
		}
		return isBaseClass;
	}
	
	/**
	 * �ж�������ǲ���java�Դ�����
	 * @param clazz
	 * @return
	 */
	public static String getEmptyString() {
		return "";
	}
}
