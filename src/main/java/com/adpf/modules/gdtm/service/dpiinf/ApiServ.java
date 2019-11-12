package com.adpf.modules.gdtm.service.dpiinf;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class ApiServ {

	private final static Logger logger = Logger.getLogger(ApiServ.class);

	// 统计写入电话号码总数
	private String mDntoken = "";

	// 统计写入电话号码总数
	private String authCode = "";

	public static Properties getProperties(String url, Properties urlsProp) {
		String relativelyPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		urlsProp = getPropertiesSelf(relativelyPath + url, urlsProp);
		// #是否自助配置 1：是 0：否
		String isSelfConf = urlsProp.getProperty("isSelfConf");
		if (isSelfConf.equals("1")) {
			String isSelfConfPath = urlsProp.getProperty("isSelfConfPath");
			logger.info("自定义路径isSelfConfPath:" + isSelfConfPath + "/mdnbase.properties");
			urlsProp = getPropertiesSelf(isSelfConfPath + "/mdnbase.properties", urlsProp);
		}

		return urlsProp;
	}

	/**
	 * 主函数,获取自定义配置路径
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static Properties getPropertiesSelf(String url, Properties urlsProp) {
		try {
			InputStream in2 = new BufferedInputStream(new FileInputStream(url));
			urlsProp = new Properties();
			urlsProp.load(in2);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return urlsProp;
	}

	/**
	 * 主函数
	 * 
	 * @param args
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws Exception
	 */
	public static void main(String[] args) throws InterruptedException, IOException {
		Logger logger = Logger.getLogger(ApiServ.class);

		Map dictMap = new HashedMap();
		dictMap.put("811", "BJ");
		dictMap.put("812", "TJ");
		dictMap.put("813", "HB");
		dictMap.put("814", "SX");
		dictMap.put("815", "NMG");
		dictMap.put("821", "LN");
		dictMap.put("822", "JL");
		dictMap.put("823", "HLJ");
		dictMap.put("831", "SH");
		dictMap.put("832", "JS");
		dictMap.put("833", "ZJ");
		dictMap.put("834", "AH");
		dictMap.put("835", "FJ");
		dictMap.put("836", "JX");
		dictMap.put("837", "SD");
		dictMap.put("841", "HB");
		dictMap.put("842", "HB");
		dictMap.put("843", "HN");
		dictMap.put("844", "GD");
		dictMap.put("845", "GX");
		dictMap.put("846", "HA");
		dictMap.put("850", "CQ");
		dictMap.put("851", "SC");
		dictMap.put("852", "GZ");
		dictMap.put("853", "YN");
		dictMap.put("854", "XZ");
		dictMap.put("861", "SX");
		dictMap.put("862", "GS");
		dictMap.put("863", "QH");
		dictMap.put("864", "NX");
		dictMap.put("865", "XJ");
		StringBuffer sb = new StringBuffer();

		Properties urlsProp = null;
		urlsProp = getProperties("/config/conf_hdfs/mdnbase.properties", urlsProp);

		String kvCompanyApikey = urlsProp.getProperty("kvCompanyApikey");
		String kvCompanySecretkey = urlsProp.getProperty("kvCompanySecretkey");
		String httpTagUrl = urlsProp.getProperty("httpTagUrl");
		String testMdn = urlsProp.getProperty("testMdn");
		String city_id = urlsProp.getProperty("city_id");

		// String filePath =
		// "/Users/zhijianzhang/develop/gdyt-bigdata/gdytbdmk-internet/src/main/resources/conf_hdfs/rule/test/name.txt";
		// List<String> lists = FileUtils.readLines(new File(filePath), "utf-8");
		//
		// String ret1;
		// Map<String, String> nameMaps = new HashMap<String, String>();
		// // 将文件所有的行转换成为字符串，行与行之间使用逗号区分
		// for (String list : lists) {
		// sb.append(list).append(",");
		// }
		// if (StringUtils.isNotBlank(sb.toString())) {
		// ret1 = sb.toString().substring(0, sb.toString().lastIndexOf(","));
		// if (StringUtils.isNotBlank(ret1)) {
		// String[] words = StringUtils.split(ret1, ",");
		// for (int i = 0; i < words.length; i++) {
		// nameMaps.put(words[i], String.valueOf(i));
		// }
		// }
		// }
		// System.out.println(nameMaps.get("李"));

		Business busi = new Business();
		busi.setCompanyApikey(kvCompanyApikey);
		busi.setCompanySecretkey(kvCompanySecretkey);
		busi.setKvmdnurl(httpTagUrl);

		// 访问服务接口
		// busi.setKvmdnurl("10.161.1.15:18080");

		// String mDntoken = getToken(busi);
		// String mDntoken = "VT5eiXeANB6C82IlwXc3Q8URQUnZOksS";
		// System.out.println(mDntoken);
		String v[] = StringUtils.split(testMdn, ",");
		ApiServ apiServ = new ApiServ();

		for (String vs : v) {
			String mdn = vs;
			String province = (String) dictMap.get(city_id);
			String month = "Jan_2017";

			String ret = apiServ.getFirstNameChar(busi, mdn, province, month);
			logger.info("获取首姓名ret:" + ret);
			ret = apiServ.getGender(busi, mdn, province, month);
			logger.info("获取性别ret:" + ret);
			ret = apiServ.getAge(busi, mdn, province, month);
			logger.info("获取年龄ret:" + ret);
		}

		// Map<String, Object> resultMap = HttpCLientUtil.get(
		// "http://10.36.0.132:18080/restful/basicdata/ctuserinfomaskm/_getAge/CD1BC28CAF762A86D75BBC072D8B4F08/musCAzoanHuIVZikENVMWgpWYyGxfiYW.json?mdn=6BC1DDAB6F106AFE02B6FF4FE9362409&type=MD5&province=BJ&month=Jan_2017");
		// // return resultMap.toString();
		// logger.info("setMdnUrl：" + resultMap.toString());

		// logger.info("######################################getBirthDate");
		// ret = getBirthDate(busi, mdn, province, month);
		// logger.info("######################################getAge");
		// ret = getAge(busi, mdn, province, month);
		// logger.info("######################################getGroupCustFlag");
		// ret = getGroupCustFlag(busi, mdn, province, month);
		// logger.info("######################################getNoCall");
		// ret = getNoCall(busi, mdn, province, month);
		// logger.info("######################################getCalling400Times");
		// ret = getCalling400Times(busi, mdn, province, month);
		// logger.info("######################################getCalling400Ducation");
		// ret = getCalling400Ducation(busi, mdn, province, month);
		// logger.info("#####################################getCalled400Duration#");
		// ret = getCalled400Duration(busi, mdn, province, month);
		// logger.info("######################################getNoMdr");
		// ret = getNoMdr(busi, mdn, province, month);
		// logger.info("######################################getPayCharge");
		// ret = getPayCharge(busi, mdn, province, month);
		// logger.info("######################################getLimitMonCnt");
		// ret = getLimitMonCnt(busi, mdn, province, month);
		// logger.info("######################################getPhoneHjTime");
		// ret = getPhoneHjTime(busi, mdn, province, month);
		// logger.info("######################################getPhonePinci");
		// ret = getPhonePinci(busi, mdn, province, month);
		// logger.info("######################################getPhoneZdPriceYear");
		// ret = getPhoneZdPriceYear(busi, mdn, province, month);
		// logger.info("######################################getPhoneBrand");
		// ret = getPhoneBrand(busi, mdn, province, month);
		// logger.info("######################################getPhoneXh");
		// ret = getPhoneXh(busi, mdn, province, month);
		// logger.info("######################################getPhoneZdPrice");
		// ret = getPhoneZdPrice(busi, mdn, province, month);
		// logger.info("######################################getFundContact");
		// ret = getFundContact(busi, mdn, province, month);
		// logger.info("######################################getAllStopTimes");
		// ret = getAllStopTimes(busi, mdn, province, month);

	}

	// 手机号码获取月份欠费停机次数（月）
	public String getAllStopTimes(Business busi, String mdn, String province, String month) {

		String setMdnUrl = "http://" + busi.getKvmdnurl() + "/restful/basicdata/ctuserbizstatmaskm/_getAllStopTimes/"
				+ busi.getCompanyApikey() + "/";
		String param = ".json?mdn=" + mdn + "&type=MD5&province=" + province + "&month=" + month;
		String ret = SendKv.sendMdnKvH(setMdnUrl, param, busi);
		logger.info("ret：" + ret);
		return ret;

	}

	// 手机号码获取资金需求指数得分（月）
	public String getFundContact(Business busi, String mdn, String province, String month) {

		String setMdnUrl = "http://" + busi.getKvmdnurl() + "/restful/basicdata/ctuserbizstatmaskm/_getFundContact/"
				+ busi.getCompanyApikey() + "/";
		String param = ".json?mdn=" + mdn + "&type=MD5&province=" + province + "&month=" + month;
		String ret = SendKv.sendMdnKvH(setMdnUrl, param, busi);
		logger.info("ret：" + ret);
		return ret;

	}

	// 手机号码获取终端当前消费（终端上市价格）
	public String getPhoneZdPrice(Business busi, String mdn, String province, String month) {

		String setMdnUrl = "http://" + busi.getKvmdnurl() + "/restful/basicdata/ctuserinfoextmaskm/_getPhoneZdPrice/"
				+ busi.getCompanyApikey() + "/";
		String param = ".json?mdn=" + mdn + "&type=MD5&province=" + province + "&month=" + month;
		String ret = SendKv.sendMdnKvH(setMdnUrl, param, busi);
		logger.info("ret：" + ret);
		return ret;

	}

	// 手机号码获取型号
	public String getPhoneXh(Business busi, String mdn, String province, String month) {

		String setMdnUrl = "http://" + busi.getKvmdnurl() + "/restful/basicdata/ctuserinfoextmaskm/_getPhoneXh/"
				+ busi.getCompanyApikey() + "/";
		String param = ".json?mdn=" + mdn + "&type=MD5&province=" + province + "&month=" + month;
		String ret = SendKv.sendMdnKvH(setMdnUrl, param, busi);
		logger.info("ret：" + ret);
		return ret;

	}

	// 手机号码获取品牌
	public String getPhoneBrand(Business busi, String mdn, String province, String month) {

		String setMdnUrl = "http://" + busi.getKvmdnurl() + "/restful/basicdata/ctuserinfoextmaskm/_getPhoneBrand/"
				+ busi.getCompanyApikey() + "/";
		String param = ".json?mdn=" + mdn + "&type=MD5&province=" + province + "&month=" + month;
		String ret = SendKv.sendMdnKvH(setMdnUrl, param, busi);
		logger.info("ret：" + ret);
		return ret;

	}

	// 手机号码获取终端年化消费（上市价格/更换频次）
	public String getPhoneZdPriceYear(Business busi, String mdn, String province, String month) {

		String setMdnUrl = "http://" + busi.getKvmdnurl()
				+ "/restful/basicdata/ctuserinfoextmaskm/_getPhoneZdPriceYear/" + busi.getCompanyApikey() + "/";
		String param = ".json?mdn=" + mdn + "&type=MD5&province=" + province + "&month=" + month;
		String ret = SendKv.sendMdnKvH(setMdnUrl, param, busi);
		logger.info("ret：" + ret);
		return ret;

	}

	// 手机号码获取终端更换次数
	public String getPhonePinci(Business busi, String mdn, String province, String month) {
		String setMdnUrl = "http://" + busi.getKvmdnurl() + "/restful/basicdata/ctuserinfoextmaskm/_getPhonePinci/"
				+ busi.getCompanyApikey() + "/";
		String param = ".json?mdn=" + mdn + "&type=MD5&province=" + province + "&month=" + month;
		String ret = SendKv.sendMdnKvH(setMdnUrl, param, busi);
		logger.info("ret：" + ret);
		return ret;

	}

	// 手机号码获取最近一次终端换机时间
	public String getPhoneHjTime(Business busi, String mdn, String province, String month) {

		String setMdnUrl = "http://" + busi.getKvmdnurl() + "/restful/basicdata/ctuserinfoextmaskm/_getPhoneHjTime/"
				+ busi.getCompanyApikey() + "/";
		String param = ".json?mdn=" + mdn + "&type=MD5&province=" + province + "&month=" + month;
		String ret = SendKv.sendMdnKvH(setMdnUrl, param, busi);
		logger.info("ret：" + ret);
		return ret;

	}

	// 根据手机号获取包期月数 --宽带包月数量
	public String getLimitMonCnt(Business busi, String mdn, String province, String month) {

		String setMdnUrl = "http://" + busi.getKvmdnurl() + "/restful/basicdata/ctuseracctmaskm/_getLimitMonCnt/"
				+ busi.getCompanyApikey() + "/";
		String param = ".json?mdn=" + mdn + "&type=MD5&province=" + province + "&month=" + month;
		String ret = SendKv.sendMdnKvH(setMdnUrl, param, busi);
		logger.info("ret：" + ret);
		return ret;

	}

	// 手机号码-月缴费金额
	public String getPayCharge(Business busi, String mdn, String province, String month) {

		String setMdnUrl = "http://" + busi.getKvmdnurl() + "/restful/basicdata/ctuseracctmaskm/_getPayCharge/"
				+ busi.getCompanyApikey() + "/";
		String param = ".json?mdn=" + mdn + "&type=MD5&province=" + province + "&month=" + month;
		String ret = SendKv.sendMdnKvH(setMdnUrl, param, busi);
		logger.info("ret：" + ret);
		return ret;

	}

	// 根据手机号码查询指定月份与指定对端号码短信次数
	public String getNoMdr(Business busi, String mdn, String province, String month) {

		String setMdnUrl = "http://" + busi.getKvmdnurl() + "/restful/basicdata/ctdoublenumstatmaskm/_getNoMdr/"
				+ busi.getCompanyApikey() + "/";
		String param = ".json?mdn=" + mdn + "&type=MD5&province=" + province + "&month=" + month;
		String ret = SendKv.sendMdnKvH(setMdnUrl, param, busi);
		logger.info("ret：" + ret);
		return ret;

	}

	// 手机号码-接听固定开头电话（400/800）的时长（月）
	public String getCalled400Duration(Business busi, String mdn, String province, String month) {

		String setMdnUrl = "http://" + busi.getKvmdnurl()
				+ "/restful/basicdata/ctuserbizstatmaskm/_getCalled400Duration/" + busi.getCompanyApikey() + "/";
		String param = ".json?mdn=" + mdn + "&type=MD5&province=" + province + "&month=" + month;
		String ret = SendKv.sendMdnKvH(setMdnUrl, param, busi);
		logger.info("ret：" + ret);
		return ret;

	}

	// 手机号码-拨打固定开头电话（400/800）的时长（月）
	public String getCalling400Ducation(Business busi, String mdn, String province, String month) {
		if (StringUtils.isBlank(mDntoken)) {
			mDntoken = SendKv.getToken(busi);
		}
		String setMdnUrl = "http://" + busi.getKvmdnurl()
				+ "/restful/basicdata/ctuserbizstatmaskm/_getCalling400Ducation/" + busi.getCompanyApikey() + "/";
		String param = ".json?mdn=" + mdn + "&type=MD5&province=" + province + "&month=" + month;
		String ret = SendKv.sendMdnKvH(setMdnUrl, param, busi);
		logger.info("ret：" + ret);
		return ret;

	}

	// 手机号码-接听固定开头电话（400/800）的次数（月）
	public String getCalled400Times(Business busi, String mdn, String province, String month) {

		String setMdnUrl = "http://" + busi.getKvmdnurl() + "/restful/basicdata/ctuserbizstatmaskm/_getCalled400Times/"
				+ busi.getCompanyApikey() + "/";
		String param = ".json?mdn=" + mdn + "&type=MD5&province=" + province + "&month=" + month;
		String ret = SendKv.sendMdnKvH(setMdnUrl, param, busi);
		logger.info("ret：" + ret);
		return ret;

	}

	// 根据手机号查询与指定对端号的月度通话次数
	public String getCalling400Times(Business busi, String mdn, String province, String month) {

		String setMdnUrl = "http://" + busi.getKvmdnurl() + "/restful/basicdata/ctuserbizstatmaskm/_getCalling400Times/"
				+ busi.getCompanyApikey() + "/";
		String param = ".json?mdn=" + mdn + "&type=MD5&province=" + province + "&month=" + month;
		String ret = SendKv.sendMdnKvH(setMdnUrl, param, busi);
		logger.info("ret：" + ret);
		return ret;

	}

	// 根据手机号查询与指定对端号的月度通话次数
	public String getNoCall(Business busi, String mdn, String province, String month) {

		String setMdnUrl = "http://" + busi.getKvmdnurl() + "/restful/basicdata/ctdoublenumstatmaskm/_getNoCall/"
				+ busi.getCompanyApikey() + "/";
		String param = ".json?mdn=" + mdn + "&type=MD5&province=" + province + "&month=" + month;
		String ret = SendKv.sendMdnKvH(setMdnUrl, param, busi);
		logger.info("ret：" + ret);
		return ret;

	}

	// 根据手机号获取是否是集团客户
	public String getGroupCustFlag(Business busi, String mdn, String province, String month) {

		String setMdnUrl = "http://" + busi.getKvmdnurl() + "/restful/basicdata/ctuserinfomaskm/_getGroupCustFlag/"
				+ busi.getCompanyApikey() + "/";
		String param = ".json?mdn=" + mdn + "&type=MD5&province=" + province + "&month=" + month;
		String ret = SendKv.sendMdnKvH(setMdnUrl, param, busi);
		logger.info("ret：" + ret);
		return ret;

	}

	// 手机号码-客户生日
	public String getBirthDate(Business busi, String mdn, String province, String month) {

		String setMdnUrl = "http://" + busi.getKvmdnurl() + "/restful/basicdata/ctuserinfomaskm/_getBirthDate/"
				+ busi.getCompanyApikey() + "/";
		String param = ".json?mdn=" + mdn + "&type=MD5&province=" + province + "&month=" + month;
		String ret = SendKv.sendMdnKvH(setMdnUrl, param, busi);
		logger.info("ret：" + ret);
		return ret;

	}

	// 手机号码-姓氏
	public String getFirstNameChar(Business busi, String mdn, String province, String month) {

		String setMdnUrl = "http://" + busi.getKvmdnurl() + "/restful/basicdata/ctuserinfomaskm/_getFirstNameChar/"
				+ busi.getCompanyApikey() + "/";
		String param = ".json?mdn=" + mdn + "&type=MD5&province=" + province + "&month=" + month;
		String ret = SendKv.sendMdnKvH(setMdnUrl, param, busi);
		// logger.info("ret：" + ret);
		return ret;

	}

	// 手机号码-信用度等级
	public String getCreditLevel(Business busi, String mdn, String province, String month) {

		String setMdnUrl = "http://" + busi.getKvmdnurl() + "/restful/basicdata/ctuserinfomaskm/_getCreditLevel/"
				+ busi.getCompanyApikey() + "/";
		String param = ".json?mdn=" + mdn + "&type=MD5&province=" + province + "&month=" + month;
		String ret = SendKv.sendMdnKvH(setMdnUrl, param, busi);
		logger.info("ret：" + ret);
		return ret;

	}

	// 获取性别
	public String getTag(Business busi, String mdn, String province, String month) {

		String setMdnUrl = "http://" + busi.getKvmdnurl() + "/restful/basicdata/ctusernbrtagd/_getTag/"
				+ busi.getCompanyApikey() + "/";
		String param = ".json?mdn=" + mdn + "&type=MD5&province=" + province + "&month=" + month;
		String ret = SendKv.sendMdnKvH(setMdnUrl, param, busi);
		logger.info("ret：" + ret);
		return ret;

	}

	// 获取性别
	public String getGender(Business busi, String mdn, String province, String month) {

		String setMdnUrl = "http://" + busi.getKvmdnurl() + "/restful/basicdata/ctuserinfomaskm/_getGender/"
				+ busi.getCompanyApikey() + "/";
		String param = ".json?mdn=" + mdn + "&type=MD5&province=" + province + "&month=" + month;
		String ret = SendKv.sendMdnKvH(setMdnUrl, param, busi);
		// logger.info("ret：" + ret);
		return ret;

	}

	// 获取年龄
	public String getAge(Business busi, String mdn, String province, String month) {

		String setMdnUrl = "http://" + busi.getKvmdnurl() + "/restful/basicdata/ctuserinfomaskm/_getAge/"
				+ busi.getCompanyApikey() + "/";
		String param = ".json?mdn=" + mdn + "&type=MD5&province=" + province + "&month=" + month;
		String ret = SendKv.sendMdnKvH(setMdnUrl, param, busi);
		// logger.info("ret：" + ret);
		return ret;

	}

	// 手机号码获取上网总时长
	public String getSumSurfDuration(Business busi, String mdn, String province, String month) {

		String setMdnUrl = "http://" + busi.getKvmdnurl() + "/restful/basicdata/ctuserbizstatmaskm/_getSumSurfDuration/"
				+ busi.getCompanyApikey() + "/";
		String param = ".json?mdn=" + mdn + "&type=MD5&&province=" + province + "&month=" + month;
		String ret = SendKv.sendMdnKvH(setMdnUrl, param, busi);
		logger.info("ret：" + ret);
		return ret;

	}

	// 手机号码获取上网活跃天数
	public String getFlowDownActiveDays(Business busi, String mdn, String province, String month) {

		String setMdnUrl = "http://" + busi.getKvmdnurl()
				+ "/restful/basicdata/ctuserbizstatmaskm/_getFlowDownActiveDays/" + busi.getCompanyApikey() + "/";
		String param = ".json?mdn=" + mdn + "&type=MD5&month=" + month;
		String ret = SendKv.sendMdnKvH(setMdnUrl, param, busi);
		logger.info("ret：" + ret);
		return ret;

	}

	public String getProvCode(Business busi, String mdn, String province) {

		String setMdnUrl = "http://" + busi.getKvmdnurl() + "/restful/basicdata/ctuserinfomaskm/_getProvCode/"
				+ busi.getCompanyApikey() + "/";
		String param = ".json?mdn=" + mdn + "&type=MD5";
		String ret = SendKv.sendMdnKvH(setMdnUrl, param, busi);
		logger.info("ret：" + ret);
		return ret;

	}

}
