/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.gdtm.tagcat.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.adpf.modules.gdtm.tagcat.entity.AdpfTagCategory;
import com.adpf.modules.gdtm.tagcat.repository.custom.AdpfTagCategoryRepositoryCustom;

/**
 * 实体资源类
 * @version 1.0
 * @author
 *
 */
@Repository
public interface AdpfTagCategoryRepository extends JpaRepository<AdpfTagCategory, Long>, JpaSpecificationExecutor<AdpfTagCategory>, AdpfTagCategoryRepositoryCustom {
	
	/**
	 * 获取树列表
	 * @return
	 */
	@Query(value = "SELECT tb.* "
			+ "FROM adpf_tag_category tb "
			+ "ORDER BY PARENT_ID, DISP_ORDER, ID", nativeQuery = true)
    public List<AdpfTagCategory> getTreeList(); 
	
	
	@Query(value = "select tl.label,sum(size) as num "
			+ "from adpf_label_count lc LEFT JOIN adpf_tag_library tl on lc.label = tl.tag_id "
			+ "where  lc.date like :month and tl.id in (select tb.tag_id from adpf_tag_bindings tb where tb.cat_id = :catId) "
			+ "GROUP BY label  ORDER BY num DESC LIMIT 10;"
			,nativeQuery = true)
	public List<String> getMonth(@Param("month")String month, @Param("catId") long catId);
	
	@Query(value = "select tl.label,sum(size) as num  "
			+ "from adpf_label_count lc LEFT JOIN adpf_tag_library tl on lc.label = tl.tag_id "
			+ "where  lc.date like :month and tl.id in (select tb.tag_id from adpf_tag_bindings tb where tb.cat_id = :catId) "
			+ "GROUP BY label  ORDER BY num DESC LIMIT 10;"
			,nativeQuery = true)
	public List<String> getMonthCount(@Param("month")String month, @Param("catId") long catId);

	/**
	 * 根据父ID列表获取ID列表
	 * @param idList
	 * @return
	 */
	@Query(value = "SELECT * FROM adpf_tag_category WHERE PARENT_ID IN (:idList)", nativeQuery = true)
    public List<AdpfTagCategory> getListByParentIds(@Param("idList")List<Long> idList); 
	
	/**
	 * 根据父ID列表获取ID列表
	 * @param idList
	 * @return
	 */
	@Query(value = "SELECT * FROM adpf_tag_category WHERE PARENT_ID is null", nativeQuery = true)
    public List<AdpfTagCategory> getListByRoot();  
	

	/**
	 * 保存
	 * @param cat_id
	 * @param tag_id
	 */
	@Transactional
    @Modifying
    @Query(value = "INSERT INTO adpf_tag_bindings (tag_id, cat_id) VALUES (:tag_id, :cat_id) ", nativeQuery = true)
	public void saveBindings(@Param("tag_id")Long tagId, @Param("cat_id")Long catId);
	
	/**
	 * 删除关联表
	 * @param roleId
	 * @param idList
	 */
	@Transactional
    @Modifying
    @Query(value = "DELETE FROM adpf_tag_bindings WHERE cat_id =:cat_id AND tag_id =:tag_id", nativeQuery = true)
	public void delBindings(@Param("cat_id")Long catId, @Param("tag_id")Long tagId);
	
	
}