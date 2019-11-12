/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.ImportNumber.repository.custom;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.springframework.data.repository.NoRepositoryBean;
import com.adpf.modules.ImportNumber.entity.AdpfImportNumberFile;
import com.mysql.jdbc.Connection;

/**
 * 自定义实体资源类接口
 * @version 1.0
 * @author
 *
 */
@NoRepositoryBean
public interface AdpfImportNumberFileRepositoryCustom {

	/**
	 * 获取AdpfImportNumberFile列表
	 * @param adpfImportNumberFile
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<Map<String, Object>> getList(AdpfImportNumberFile adpfImportNumberFile, Map<String, Object> params, int pageIndex, int pageSize);
	
	/**
	 * 获取AdpfImportNumberFile列表总数
	 * @param adpfImportNumberFile
	 * @param params
	 * @return
	 */
	public int getListCount(AdpfImportNumberFile adpfImportNumberFile, Map<String, Object> params);
	
}