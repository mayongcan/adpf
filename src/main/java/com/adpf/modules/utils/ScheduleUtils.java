package com.adpf.modules.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.adpf.modules.crowd.entity.DmpCrowdInfo;
import com.adpf.modules.crowd.entity.DmpCrowdNumberInfo;
import com.adpf.modules.crowd.repository.DmpCrowdInfoRepository;
import com.adpf.modules.crowd.repository.DmpCrowdNumberInfoRepository;
import com.adpf.qtn.service.QtnTakeNumberInfoService;
import com.adpf.qtn.service.QtnWindowInfoService;
import com.gimplatform.core.utils.BeanUtils;

@Component
public class ScheduleUtils {
	protected static final Logger logger = LogManager.getLogger(ScheduleUtils.class);

	@Autowired
	private DmpCrowdInfoRepository dmpCrowdInfoRepository;

	@Autowired
	private DmpCrowdNumberInfoRepository dmpCrowdNumberInfoRepository;

	@Autowired
	private QtnTakeNumberInfoService qtnTakeNumberInfoService;

	@Autowired
	private QtnWindowInfoService qtnWindowInfoService;

	/**
	 *	每30分钟定时检查没有上传的人群
	 */

	//@Scheduled(cron="0 0/30 * * * ?")
	public void scheduledAddCrowdNumberInfo() {
		System.out.println("------------------------------------------定时任务开始：");
		try {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("isJoin", "0");
			//查出所有没上传的人群	
			List<Map<String,Object>> list =dmpCrowdInfoRepository.getNotJoinList(new DmpCrowdInfo(), params);
			List<DmpCrowdNumberInfo> numberlist = new ArrayList<>();
			for(Map<String,Object>map:list) {
				DmpCrowdInfo info =(DmpCrowdInfo) BeanUtils.mapToBean(map, DmpCrowdInfo.class);
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = formatter.parse(formatter.format(map.get("createDate")));
				info.setCreateDate(date); 
				//				File  file =new File("E:\\work_space\\project\\adpf\\"+info.getTxtPath());
				File  file =new File("/Users/zzd/Work/ProjectRun/Resources/"+info.getTxtPath());
				DmpCrowdNumberInfo numberInfo =new DmpCrowdNumberInfo();
				numberInfo.setCrowdId(info.getCrowdId());
				if(file.exists()) {
					//读取每个文件
					BufferedReader bis = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
					String sb="";
					while((sb=bis.readLine())!=null){
						//手机号 11位
						if("1".equals(info.getTxtType()) && sb.trim().length() ==  11) {
							numberInfo.setNumber(sb.trim());
						}
						//IDFA和IMEI 15位
						else if("3".equals(info.getTxtType()) || "5".equals(info.getTxtType())) {
							if(sb.trim().length() ==15) {
								numberInfo.setNumber(sb.trim());
							}
						}
						//md5加密都是 32位
						else if("2".equals(info.getTxtType())||"4".equals(info.getTxtType())||"6".equals(info.getTxtType())) {
							if(sb.trim().length() == 32) {
								numberInfo.setNumber(sb.trim());
							}
						}
						if(numberInfo.getNumber() != null && !"".equals(numberInfo.getNumber())) {
							numberlist.add(numberInfo);
						}
					}
					dmpCrowdNumberInfoRepository.save(numberlist);
					DmpCrowdInfo newInfo = new DmpCrowdInfo();
					newInfo.setCrowdId(info.getCrowdId());
					newInfo.setIsJoin("1");
					newInfo.setStatus("2");
					String length = "";
					if(file.length() < 1024) {
						length = file.length() + " B";
					}else {
						double len =(double)file.length()/1024/100;
						length = String.valueOf(len).substring(0, String.valueOf(len).indexOf(".")+3)  + " KB";
					}
					newInfo.setTxtSize(length);
					newInfo.setTxtSum((long) numberlist.size());
					BeanUtils.mergeBean(newInfo, info);
					dmpCrowdInfoRepository.save(info);
				}else {
					logger.error("文件不存在");
				}
			}
			System.out.println("------------------------------------------定时任务结束：");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch(Exception e) {
			logger.error(e.getStackTrace(), e);
		}
	}


	/**
	 *每天0点，将取号系统每个营业厅的取号改为0000 
	 */
	//@Scheduled(cron="0 0 0 * * ?")
	public void scheduledChangeNumber() {
		try {
			System.out.println("-----------取号系统定时任务开始:");
			//将每个营业厅的取号改成000
			qtnTakeNumberInfoService.changeNumber();
			//将每个窗口的当前状态改为默认，当前号码改为空
			qtnWindowInfoService.changeWindowStatusAndNumber();
			//将redis中的所有营业厅的取码队列清空
			qtnTakeNumberInfoService.initRedis();

			System.out.println("-----------取号系统定时任务开始");
		}catch(Exception e) {
			logger.error(e.getMessage(),e);
		}
	}


}
