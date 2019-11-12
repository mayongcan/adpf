package com.adpf.callwbook.util;

import com.adpf.callwbook.service.CallWbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class addCallUtil {

    @Autowired
    CallWbookService callWbookService;

    @Async
    @Scheduled(cron = "${scheduled.addCallData.cron}")
    public void timingTable() {
        System.out.println("开始更新");
        updayCallData();
    }

    public void updayCallData(){
        callWbookService.addOrUpday();
    };

}
