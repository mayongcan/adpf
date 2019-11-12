/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.tnpm.applicant.repository.custom;

import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.tnpm.applicant.entity.TnpmApplicantTrainDevelopInfo;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface TnpmApplicantTrainDevelopInfoRepositoryCustom {

	/**
	 * 获取TnpmTrainDevelopInfo列表
	 * @param tnpmTrainDevelopInfo
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(TnpmApplicantTrainDevelopInfo tnpmTrainDevelopInfo, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取TnpmTrainDevelopInfo列表总数
	 * @param tnpmTrainDevelopInfo
	 * @param params
	 * @return
	 */
	public int getListCount(TnpmApplicantTrainDevelopInfo tnpmTrainDevelopInfo, Map<String, Object> params);
}