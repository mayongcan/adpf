package com.adpf.modules.gdtm.service.hive.restful;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.adpf.modules.gdtm.service.hive.MatchedDataService;

@RestController
@RequestMapping("/api/adpf/MatchedData")
public class MatchedDataRestful {

	@Autowired
	MatchedDataService matchedDataService;
	
	@RequestMapping(value = "/getData", method = RequestMethod.POST)
	public String getData(HttpServletRequest request, @RequestParam Map<String,Object> parms)  {
		String path="C:\\Users\\user\\Desktop\\数据.txt";
		String ss=matchedDataService.MatchedData(path);
		return ss;
	}
}
