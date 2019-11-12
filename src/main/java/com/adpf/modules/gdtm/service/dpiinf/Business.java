package com.adpf.modules.gdtm.service.dpiinf;

import java.io.Serializable;
import java.util.Set;

/**
 * 参数
 * 
 * @author zzj
 * @createTime 2017年4月20日下午4:54:15
 * @version
 */
public class Business implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 厂商名字
	 */
	private String companyName;
	/**
	 * apikey
	 */
	private String companyApikey;
	/**
	 * 密钥
	 */
	private String companySecretkey;
	/**
	 * 商家编号
	 */
	private String companyCode;
	/**
	 * 商家标签
	 */
	private String companyLabel;
	/**
	 * 省份
	 */
	private String companyProvince;
	/**
	 * 状态 默认：0 删除时：1
	 */
	private String del_flag;
	/**
	 * 手机号码
	 */
	private Set<String> mdnSet;

	/**
	 * mad手机url
	 */
	private String kvmdnurl;

	public String getKvmdnurl() {
		return kvmdnurl;
	}

	public void setKvmdnurl(String kvmdnurl) {
		this.kvmdnurl = kvmdnurl;
	}

	public Business() {

	}

	public Business(Set<String> mdnSet) {
		this.mdnSet = mdnSet;
	}

	public Business(Set<String> mdnSet, String... str) {
		this.mdnSet = mdnSet;
		this.companyName = str[0];
		this.companyApikey = str[1];
		this.companySecretkey = str[2];
		this.companyCode = str[3];
		this.companyLabel = str[4];
		this.companyProvince = str[5];
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyApikey() {
		return companyApikey;
	}

	public void setCompanyApikey(String companyApikey) {
		this.companyApikey = companyApikey;
	}

	public String getCompanySecretkey() {
		return companySecretkey;
	}

	public void setCompanySecretkey(String companySecretkey) {
		this.companySecretkey = companySecretkey;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyLabel() {
		return companyLabel;
	}

	public void setCompanyLabel(String companyLabel) {
		this.companyLabel = companyLabel;
	}

	public String getCompanyProvince() {
		return companyProvince;
	}

	public void setCompanyProvince(String companyProvince) {
		this.companyProvince = companyProvince;
	}

	public String getDel_flag() {
		return del_flag;
	}

	public void setDel_flag(String del_flag) {
		this.del_flag = del_flag;
	}

	public Set<String> getMdnSet() {
		return mdnSet;
	}

	public void setMdnSet(Set<String> mdnSet) {
		this.mdnSet = mdnSet;
	}

}
