package com.abina.poi;

/**
 * @author abina
 * @Data 2018-03-08
 */
public class WordUtil {


	/**
	 * @方法名称 exportCtrAtch
	 * @功能描述 <pre>生成合同附件</pre>
	 * @作者 zouyaobin@tansun.com.cn
	 * @创建时间 2018年3月7日 上午9:32:47
	 * @param params
	 * @param response
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@RequestMapping(value="/exportCtrAtch")
	public void exportCtrAtch(QueryCtrVO queryVo, HttpServletResponse response) {
		
		//1.查询数据，拼装替换 Map,
		Map<String, Object> ctrInfMap = ctrApi.getCtrInfMap(queryVo);
		if (CollectionUtil.isEmpty(ctrInfMap)) {
			return;
		}
		Map<String, String> mappingMap = buildMappingMap(ctrInfMap);
		String templateFileName = templateFileName(ctrInfMap);
		//2.获取文档路径
		StringBuffer sbPath = new StringBuffer();
		sbPath.append(this.getRequest().getSession().getServletContext().getRealPath("/"));
		sbPath.append(File.separator).append("static").append(File.separator).append("template").append(File.separator).append("ctr").append(File.separator);
		
		File tempFile = null;
		try {
			//3.替换文档内容，并创建临时文件写入
			XWPFDocument document = replaceWord(sbPath.toString() + templateFileName, mappingMap);
			tempFile = FileUtil.createTempFile(new File(sbPath.toString()));
			OutputStream os = new FileOutputStream(tempFile);  
			document.write(os);  
			os.close();
				
			//4.读取临时文件，并生成新文档
			InputStream tempIs = new FileInputStream(tempFile);  
			String agent = getRequest().getHeader("User-Agent");
			boolean isMSIE = (agent != null && agent.indexOf("MSIE") != -1);
			if (isMSIE) {
				templateFileName = URLEncoder.encode(templateFileName, "UTF-8");
			} else {
				templateFileName = new String(templateFileName.getBytes("UTF-8"), "ISO-8859-1");
			}
			response.reset();  
			response.setContentType("application/vnd.ms-word;charset=UTF-8");  
			response.setHeader("Content-Disposition","attachment; filename=" + templateFileName); 
			response.setHeader("Content-Length", tempFile.length() + ""); 
			ServletOutputStream out = response.getOutputStream();  
			byte[] b=new byte[1024];  
			int len;  
			while((len = tempIs.read(b))!=-1){  
			    out.write(b, 0, len);  
			}  
			tempIs.close();  
			out.close();
		} catch (IORuntimeException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(tempFile != null) {
				tempFile.delete();
			}
		}
	}
	
	/**
	 * @方法名称 buildMappingMap
	 * @功能描述 <pre>构建映射替换集合对象，格式：${ctrId} => ctrId </pre>
	 * @作者 zouyaobin@tansun.com.cn
	 * @创建时间 2018年3月8日 上午10:01:57
	 * @param ctrInf
	 * @return
	 */
	public Map<String, String> buildMappingMap(Map<String, Object> ctrInf) {
		Map<String, String> replaceMap = new HashMap<String, String>();
		//合同基础信息
		replaceMap.put("${ctrId}", MapUtils.getString(ctrInf, "ctrId", ""));
		replaceMap.put("${cstNm}", MapUtils.getString(ctrInf, "cstNm", ""));
		
		//合同客户信息 = 债权人
		Map<String, Object> cstQueryParams = new HashMap<String, Object>();
		cstQueryParams.put("cstId", MapUtils.getString(ctrInf, "cstId"));
		Map<String, Object> cstBscInf = customerApi.getBizCstBscInfByParams(cstQueryParams);
		replaceMap.put("${rgstAdr}", MapUtils.getString(cstBscInf, "rgstAdr", ""));
		replaceMap.put("${ctcPsn}", MapUtils.getString(ctrInf, "ctcPsn", ""));
		replaceMap.put("${ctcPsnTel}", MapUtils.getString(ctrInf, "ctcPsnTel", ""));
		replaceMap.put("${email}", MapUtils.getString(ctrInf, "email", ""));
		
		//债务人信息
		QueryCtrVO oblgVo = new QueryCtrVO();
		oblgVo.setCtrSn(MapUtil.getString(ctrInf, "ctrSn"));
		Map<String, Object> rltvOblgMap = ctrApi.getRltvOblgMap(oblgVo);
		replaceMap.put("${oblgCstNm}", MapUtils.getString(rltvOblgMap, "cstNm", ""));
//		replaceMap.put("XX商业", ""); TODO
		return replaceMap;
	}
	
	/**
	 * @方法名称 templateFileName
	 * @功能描述 <pre>获取模板文档名称</pre>
	 * @作者 zouyaobin@tansun.com.cn
	 * @创建时间 2018年3月8日 上午9:56:00
	 * @param ctrInfMap
	 * @return
	 */
	private static String templateFileName(Map<String, Object> ctrInfMap) {
		String tempFileName = "应收账款转让合同.docx";
		String pdId = MapUtil.getString(ctrInfMap, "pdId");
		if (DictUtil.getDictValue("ProNm", "ProNm_0").equals(pdId)) { //应收账款
			tempFileName = "应收账款转让合同.docx"; //TODO
		}
		if (DictUtil.getDictValue("ProNm", "ProNm_1").equals(pdId)) { //销售回购
			tempFileName = "应收账款转让合同.docx";
		}
		if (DictUtil.getDictValue("ProNm", "ProNm_2").equals(pdId)) { //代理采购
			tempFileName = "应收账款转让合同.docx"; //TODO
		}
		return tempFileName;
	}
	
	/**
	 * @方法名称 replaceWord
	 * @功能描述 <pre>替换 word 文档中的指定字符</pre>
	 * @作者 zouyaobin@tansun.com.cn
	 * @创建时间 2018年3月8日 上午9:38:22
	 * @param filePath 读取模板文件地址(含文件名)
	 * @param replaceMap 替换的新旧值 Map
	 * @return word 文档对象
	 * @throws IOException
	 */
	public static XWPFDocument replaceWord(String filePath, Map<String, String> replaceMap) throws IOException {
		XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(filePath));
		Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();
		int idx = 0;
		while (itPara.hasNext()) {
			XWPFParagraph paragraph = (XWPFParagraph) itPara.next();
			int size = paragraph.getRuns().size();
			if (size == 0) {
				continue;
			}
			String paragraphText = StringUtils.join(paragraph.getRuns().toArray());
			//存在标识，才替换
			if (paragraphText.contains("$")) {
				for (Map.Entry<String, String> entry : replaceMap.entrySet()) {
					paragraphText = paragraphText.replace(entry.getKey(), entry.getValue());
				}
				for (int i = (size - 1); i >= 0; i--) {
					paragraph.removeRun(i);
				}
				XWPFRun newRun = paragraph.insertNewRun(0);
				newRun.setText(paragraphText, 0);
				newRun.setFontFamily("宋体");
				if (idx == 0) {
					newRun.setFontSize(12);
				} else {
					newRun.setFontSize(14);
				}
				idx ++ ;
			}
		}
		return document;
	}
}
