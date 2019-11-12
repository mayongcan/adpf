/*
 * Copyright(c) 2018 gimplatform(通用信息管理平台) All rights reserved.
 */
package com.adpf.modules.gdtm.tagcat.service.impl;

import java.util.HashMap;
import com.alibaba.fastjson.JSONArray;
import com.gimplatform.core.common.Constants;
import com.gimplatform.core.tree.Tree;
import com.gimplatform.core.tree.TreeNode;
import com.gimplatform.core.tree.TreeNodeExtend;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.alibaba.fastjson.JSONObject;
import com.gimplatform.core.common.Constants;
import com.gimplatform.core.entity.UserInfo;
import com.gimplatform.core.utils.BeanUtils;
import com.gimplatform.core.utils.RestfulRetUtils;
import com.gimplatform.core.utils.StringUtils;

import com.adpf.modules.gdtm.tagcat.service.AdpfTagCategoryService;
import com.adpf.modules.gdtm.tagLibrary.entity.AdpfTagLibrary;
import com.adpf.modules.gdtm.tagLibrary.repository.AdpfTagLibraryRepository;
import com.adpf.modules.gdtm.tagcat.entity.AdpfTagCategory;
import com.adpf.modules.gdtm.tagcat.repository.AdpfTagCategoryRepository;

@Service
public class AdpfTagCategoryServiceImpl implements AdpfTagCategoryService {
	
    @Autowired
    private AdpfTagCategoryRepository adpfTagCategoryRepository;
    
    @Autowired
    private AdpfTagLibraryRepository adpfTagLibraryRepository;

	@Override
	public JSONObject getList(Pageable page, AdpfTagCategory adpfTagCategory, Map<String, Object> params) {
		List<Map<String, Object>> list = adpfTagCategoryRepository.getList(adpfTagCategory, params, page.getPageNumber(), page.getPageSize());
		int count = adpfTagCategoryRepository.getListCount(adpfTagCategory, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);	
	}
	
	@Override
	public JSONObject getMonth(Map<String, Object> params) {
		JSONObject json = new JSONObject();
		String month = (String) params.get("month")+"%";
		long catId = Long.valueOf(String.valueOf(params.get("catId"))).longValue();
		System.out.println(month);
		System.out.println(catId);
//		List<Map<String, Object>> list = adpfTagCategoryRepository.getMonth(adpfTagCategory, params);
//		int count = adpfTagCategoryRepository.getListCount(adpfTagCategory, params);
		json.put("count", adpfTagCategoryRepository.getMonth(month,catId)) ;
		return json;	
	}
	
	@Override
	public JSONObject getListTag(Pageable page, AdpfTagCategory adpfTagCategory, Map<String, Object> params) {
		List<Map<String, Object>> list = adpfTagCategoryRepository.getListTag(adpfTagCategory, params, page.getPageNumber(), page.getPageSize());
		int count = adpfTagCategoryRepository.getListCountTag(adpfTagCategory, params);
		return RestfulRetUtils.getRetSuccessWithPage(list, count);
	}

	@Override
	public JSONObject add(Map<String, Object> params, UserInfo userInfo) {
	    AdpfTagCategory adpfTagCategory = (AdpfTagCategory) BeanUtils.mapToBean(params, AdpfTagCategory.class);
		adpfTagCategoryRepository.save(adpfTagCategory);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject edit(Map<String, Object> params, UserInfo userInfo) {
        AdpfTagCategory adpfTagCategory = (AdpfTagCategory) BeanUtils.mapToBean(params, AdpfTagCategory.class);
		AdpfTagCategory adpfTagCategoryInDb = adpfTagCategoryRepository.findOne(adpfTagCategory.getId());
		if(adpfTagCategoryInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(adpfTagCategory, adpfTagCategoryInDb);
		adpfTagCategoryRepository.save(adpfTagCategoryInDb);
		return RestfulRetUtils.getRetSuccess();
	}

	@Override
	public JSONObject del(String idsList, UserInfo userInfo) {
		Long id = StringUtils.toLong(idsList);
		List<Long> pids = new ArrayList<Long>();
		List<Long> allIds = new ArrayList<Long>();
		pids.add(id);
		allIds.add(id);
		int deep = Constants.DEFAULT_TREE_DEEP;
		while (!pids.isEmpty() && pids.size() > 0 && deep > 0) {
			List<AdpfTagCategory> list = adpfTagCategoryRepository.getListByParentIds(pids);
			pids.clear();
			for (AdpfTagCategory obj : list) {
				pids.add(obj.getId());
				allIds.add(obj.getId());
			}
			deep--;
		}
		for (int i = 0; i < allIds.size(); i++) {
			adpfTagCategoryRepository.delete(allIds.get(i));
		}
		return RestfulRetUtils.getRetSuccess();
	}

	public JSONObject getTreeList(){
		List<AdpfTagCategory> list = adpfTagCategoryRepository.getTreeList();
		return getJsonTree(list);
	}

	public List<AdpfTagCategory> getListByParentIds(AdpfTagCategory adpfTagCategory){
		List<Long> idList = new ArrayList<>();
		Long parentId = (long) 0;
		if (adpfTagCategory != null) {
			parentId = adpfTagCategory.getParentId();

		}
		List<AdpfTagCategory> treeList = new ArrayList<>();
		if (parentId == 0) {
			treeList = adpfTagCategoryRepository.getListByRoot();
		} else {
			idList.add(parentId);
			treeList = adpfTagCategoryRepository.getListByParentIds(idList);
		}
		return treeList;
	}
	
	/**
	 * 获取json格式的树
	 * @param list
	 * @return
	 */
	private JSONObject getJsonTree(List<AdpfTagCategory> list) {
		TreeNode root = new TreeNode("root", "all", null, false);
		Map<String, String> mapAttr = null;
		TreeNodeExtend treeNode = null;
		String id = "", text = "", parent = "";
		Tree tree = new Tree(true);
		// 添加一个自定义的根节点
		if (list == null || list.isEmpty()) {
			treeNode = new TreeNodeExtend("-1", "虚拟节点", "", false, null);
			tree.addNode(treeNode);
		}else{
			for (AdpfTagCategory obj : list) {
				if (obj == null || obj.getId() == null)
					continue;
				id = obj.getId().toString();
				text = obj.getName();
				parent = obj.getParentId() == null ? "" : obj.getParentId().toString();

                mapAttr = BeanUtils.beanToMapStr(obj);
//				mapAttr = new HashMap<String, String>();
//				
//				
//				mapAttr.put("id", obj.getId() == null ? "" : obj.getId().toString());
//				
//				
//				
//				mapAttr.put("name", obj.getName());
//				
//				
//				
//				mapAttr.put("parentId", obj.getParentId() == null ? "" : obj.getParentId().toString());
//				
//				
//				
//				mapAttr.put("dispOrder", obj.getDispOrder() == null ? "" : obj.getDispOrder().toString());
//				
//				

				treeNode = new TreeNodeExtend(id, text, parent, false, mapAttr);
				tree.addNode(treeNode);
			}
		}
		String strTree = tree.getTreeJson(tree, root);
		return RestfulRetUtils.getRetSuccess(JSONArray.parseArray(strTree));
	}

	@Override
	public JSONObject addBindings(UserInfo userInfo, Long catId, List<Long> tagIdList) {
		Map<String,Object> params = new HashMap<>();
	    for (Long tagId : tagIdList) { 
	    	adpfTagCategoryRepository.saveBindings(tagId, catId);
	    	params.put("id", tagId);
			params.put("isValid", "Y");
	    	AdpfTagLibrary adpfTagLibrary = (AdpfTagLibrary) BeanUtils.mapToBean(params, AdpfTagLibrary.class);
			AdpfTagLibrary adpfTagLibraryInDb = adpfTagLibraryRepository.findOne(adpfTagLibrary.getId());
			if(adpfTagLibraryInDb == null){
				return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
			}
			//合并两个javabean
			BeanUtils.mergeBean(adpfTagLibrary, adpfTagLibraryInDb);
			adpfTagLibraryRepository.save(adpfTagLibraryInDb);
	    }
	    
	    return RestfulRetUtils.getRetSuccess(); 
	}

	@Override
	public JSONObject delBindings(UserInfo userInfo, Long catId, Long tagId) {
		Map<String,Object> params = new HashMap<>();
		adpfTagCategoryRepository.delBindings(catId, tagId);
		params.put("id", tagId);
		params.put("isValid", "N");
    	AdpfTagLibrary adpfTagLibrary = (AdpfTagLibrary) BeanUtils.mapToBean(params, AdpfTagLibrary.class);
		AdpfTagLibrary adpfTagLibraryInDb = adpfTagLibraryRepository.findOne(adpfTagLibrary.getId());
		if(adpfTagLibraryInDb == null){
			return RestfulRetUtils.getErrorMsg("51006","当前编辑的对象不存在");
		}
		//合并两个javabean
		BeanUtils.mergeBean(adpfTagLibrary, adpfTagLibraryInDb);
		adpfTagLibraryRepository.save(adpfTagLibraryInDb);
        return RestfulRetUtils.getRetSuccess();
	}
}
