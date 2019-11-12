package com.adpf.modules.gdtm.util;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;

import com.adpf.modules.gdtm.service.dpiinf.Business;

/**
 * KV操作
 * 
 * @author zzj
 * @createTime 2017年4月19日上午10:48:19
 * @version
 */
public class KvUtil {

	private static final Logger logger = Logger.getLogger(KvUtil.class);

	private static final String FLAG = "_";
	// 时间
	private static final String DATE = DateUtil.DATEKEY_FORMAT.format(new Date());

	// 限频500次
	private static final int LIMIT_NO = 3;

	// 统计写入电话号码总数
	private static int WRITE_STAT = 0;
	private static String BUSICODE = null;
	private static String projectName = "RTB项目：";

	static final String FLAG_03 = "\\s*";

	// 统计写入电话号码总数
	private static String mDntoken = "";

	private static boolean fc[] = { true, true, true, true, true, true, true, true, true, true, true, true, true, true,
			true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true };

	public static String getProjectName() {
		return projectName;
	}

	public static void setProjectName(String projectName) {
		KvUtil.projectName = projectName;
	}

	/**
	 * 根据厂商信息处理key
	 * 
	 * @param business 厂商信息
	 * @param mdnCode  编号
	 * @return
	 */
	// Key格式为：厂商编码_省份缩写编码_标签类型编码_日期_编码
	public static String setKey(Business business, int mdnCode, String city_id) {
		StringBuffer key = new StringBuffer();
		key.append(business.getCompanyCode()).append(FLAG)
				// 省份缩写编码
				.append(city_id).append(FLAG)
				// 标签编号
				.append(business.getCompanyLabel()).append(FLAG)
				// 日期
				.append(DATE).append(FLAG)
				// 顺序
				.append(mdnCode);
		return key.toString();
	}

	/**
	 * 根据厂商信息处理key
	 * 
	 * @param business 厂商信息
	 * @param mdnCode  编号
	 * @return
	 */
	// Key格式为：厂商编码_标签类型编码_日期_省份_编码
	public static String setKeyMdn(Business business, int mdnCode, String city_id) {
		StringBuffer key = new StringBuffer();
		key.append(business.getCompanyCode()).append(FLAG)
				// 标签编号
				.append(business.getCompanyLabel()).append(FLAG)
				// 日期
				.append(DATE).append(FLAG)
				// 省份缩写编码
				.append(city_id).append(FLAG)
				// 顺序
				.append(mdnCode);
		return key.toString();
	}

	/**
	 * 处理value
	 * 
	 * @param mdn 手机号码
	 * @return
	 */
	// public static String setValue(String mdn) {
	// String transitMdn = null;
	// if (StringUtils.isNotBlank(mdn)) {
	// if (mdn.length() == 32)
	// transitMdn = mdn;
	// else
	// transitMdn = MD5Util.MD5(mdn).toUpperCase();
	// }
	// return transitMdn;
	// }

	/**
	 * 将号码写入K/V
	 * 
	 * @return
	 */
	public static String setMdnToKv(Business business, String url, String ref, String pwd, String key,
			String resultType) {

		// 错误编码1：token不能为空或null
		String status = "-1";
		// 批量数据时 需要循环调用接口，遍历set到kv
		if (null != business) {
			Set<String> mdnSets = business.getMdnSet();
			if (null != mdnSets && mdnSets.size() > 0) {
				// 将三网明文手机号码KV列表成功后写入触点
				String isMdn = null;
				for (String mdn : mdnSets) {
					isMdn = mdn;
					break;
				}

				if (resultType.equals("2")) {
					try {
						String seed = DateFormatUtils.format(new Date(), "yyyyMMdd");
						String token = DigestUtils
								.md5Hex("ref=" + ref + "&password=" + DigestUtils.md5Hex(pwd) + "&seed=" + seed);
						// logger.info(projectName+"手机为加密号，直接写入kv库");
						return setKv(business, token, url, key);
					} catch (Exception e) {
						logger.info(projectName + "RTB入库失败");
						// e.printStackTrace();
					} finally {

					}
				} else {
					try {
						return BdcscUtil.addMdnToKV(business, key);
					} catch (Exception e) {
						logger.info(projectName + "外呼号码未能正确写入库2");
						// e.printStackTrace();
					} finally {

					}
				}

			}
		}
		return status;
	}

	/**
	 * 写入KV，供外呼平台提取
	 * 
	 * @param business 厂商信息
	 * @param token    token
	 */
	private static String setKv(Business business, String token, String url, String key) {
		String status = "-1";
		Set<String> mdnSets = business.getMdnSet();
		// 写入KV时计数为1
		for (String mdn : mdnSets) {
			String setKvUrl = String.format(url + "%s.json?key=%s&value=%s",
					// "http://10.161.0.60:8020/bdapi/restful/fog/gdyttx/setItems/ctyun_bdcsc_gdyttx/%s.json?key=%s&value=%s",
					token, key, mdn);
			// logger.info(projectName+setKvUrl);
			Map<String, Object> resultMap = HttpCLientUtil.get(setKvUrl.replaceAll(FLAG_03, ""));
			logger.info(projectName + resultMap.toString());
			if (null != resultMap && resultMap.get("status").toString().equals("1")) {
				status = "0";
				return status;
			}
			return resultMap.toString();

		}
		return status;
	}

	/**
	 * 将号码写入K/V
	 * 
	 * @return
	 */
	public static String getMdnToKv(Business business, String url, String ref, String pwd, String kvSeq,
			String resultType) {

		String seed = DateFormatUtils.format(new Date(), "yyyyMMdd");
		String token = DigestUtils.md5Hex("ref=" + ref + "&password=" + DigestUtils.md5Hex(pwd) + "&seed=" + seed);
		// logger.info(seed);
		if (null == token || "".equals(token)) {
			throw new RuntimeException("token不能为空或Null");
		}
		// logger.info(projectName+"手机为加密号，直接写入kv库");
		return getKv(business, token, url, kvSeq);
	}

	/**
	 * 写入KV，供外呼平台提取
	 * 
	 * @param business 厂商信息
	 * @param token    token
	 */
	private static String getKv(Business business, String token, String url, String kvSeq) {
		String setKvUrl = String.format(url + "%s.json?key=%s", token, kvSeq);
		// logger.info(projectName + setKvUrl);
//		String resultMap = HttpCLientUtil.getStr(setKvUrl.replaceAll(FLAG_03, ""));
		String resultMap = HttpUtil.getStr(setKvUrl.replaceAll(FLAG_03, ""));
		// logger.info(projectName + resultMap.toString());
		return resultMap;

	}

	public static void putlogs(int percent) {
		int temp[] = { 0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100 };
		int count = 0;
		for (int i = 0; i < 20; i++) {
			if (temp[i] <= percent && percent <= temp[i + 1]) {
				count = i;
				break;
			}
		}

		if (fc[count]) {
			fc[count] = false;
			logger.info(projectName + "当前加载文件进度：" + percent);
		}

	}

	public static void main(String[] args) throws Exception {

		int currentNum = 0;
		int totalNum = 100;
		for (; currentNum < totalNum; currentNum++) {
			double percent = ((double) currentNum / (double) totalNum) * 100;
			int percentInt = (new Double(percent)).intValue();
			putlogs(percentInt);
		}

		// System.out.print(((double) 4 / (double) 10) * 100);
		// Business busi = new Business();
		// busi.setCompanyApikey("CD1BC28CAF762A86D75BBC072D8B4F08");
		// busi.setCompanySecretkey("3DE2C39E5B06F33336A5DC21E1A3370F");
		// busi.setKvmdnurl("42.123.106.17:18080");
		// // 必填，由gdyt（前缀），区域：guangdong，业务类型：yx（游戏），时间标识：20171031：序号：1组成
		// busi.setCompanyName("广东翼天");
		// busi.setCompanyCode("gdyt");
		// busi.setCompanyProvince("BJ");
		// busi.setCompanyLabel("JR");
		//
		// Set<String> mdnSet = Sets.newHashSet();
		// mdnSet.add("3DE28DA127577432527221FC3D4B6DBF");
		//
		// busi.setMdnSet(mdnSet);
		// busi.setUserId("1");
		//
		// try {
		// if (null != busi) {
		// // KvUtil.addMdnToKV(busi);
		// // KvUtil.getMdnToKv(busi,
		// //
		// "http://42.123.72.93:8081/bdapi/restful/fog/gdyttx/getItems/ctyun_bdcsc_gdyttx/",
		// // "ctyun_bdcsc_gdyttx", "gdyttx20171020", 3, "2");
		// KvUtil.setMdnToKv(busi,
		// "http://42.123.72.93:8081/bdapi/restful/fog/gdyttx/setItems/ctyun_bdcsc_gdyttx/",
		// "ctyun_bdcsc_gdyttx", "gdyttx20171020", "gdyt_BJ_JR_20171108_1", "1");
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	/**
	 * 创建单个线程 调用set方法
	 * 
	 * @author zzj
	 * @createTime 2017年5月19日下午3:25:37
	 * @version
	 */
	// static class SetKvThread implements Runnable {
	//
	// private Business business;
	// private String token;
	//
	// public Business getBusiness() {
	// return business;
	// }
	//
	// public void setBusiness(Business business) {
	// this.business = business;
	// }
	//
	// public String getToken() {
	// return token;
	// }
	//
	// public void setToken(String token) {
	// this.token = token;
	// }
	//
	// public SetKvThread() {
	//
	// }
	//
	// public SetKvThread(Business business, String token) {
	// this.business = business;
	// this.token = token;
	// }
	//
	// @Override
	// public void run() {
	// Set<String> mdnSets = business.getMdnSet();
	// synchronized (SetKvThread.class) {
	// try {
	// //加密后写入到KV供触点平台调用
	// int mdnCode = 1;
	// for(String mdn : mdnSets) {
	// String setKvUrl = String.format(
	// "http://%s/restful/pm-label/info/receiveData/%s/%s.json?key=%s&value=%s",
	// BdcscUtil.getAuthor(),
	// BdcscUtil.getApiKey(business),
	// getToken(),
	// setKey(mdnCode),
	// setValue(mdn));
	// logger.info(projectName+"------KvUrl------:" + setKvUrl);
	// Map<String, Object> resultMap =
	// HttpCLientUtil.get(setKvUrl.replaceAll(WodeConstans.FLAG_03, ""));
	// logger.info(projectName+"----resultMap----:" + resultMap);
	// JSONObject jsonObj = null;
	// if(null != resultMap && resultMap.get("status").toString().equals("1")) {
	// jsonObj = JSONObject.fromObject(resultMap.get("result"));
	// if(jsonObj.getInt("code") == 200) {
	// String value = jsonObj.getJSONObject("data").getString("value");
	// if(!value.toUpperCase().equals("SUCCESS")) {
	// throw new RuntimeException("号码写入触点时发生错误,号码为["+ mdn +"]");
	// } else {
	// mdnCode ++;
	// Thread.sleep(1000);
	// logger.info(projectName+"休息1秒...");
	// }
	// }
	// }
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	// //
	// }
	// }
	// }
	// }
}
