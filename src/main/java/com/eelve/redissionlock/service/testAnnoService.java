package com.eelve.redissionlock.service;

import com.eelve.redissionlock.distributedlock.annotation.Lock;
import com.eelve.redissionlock.entity.TestLockVo;
import com.eelve.redissionlock.mapper.TestLockDAO;
import org.assertj.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Description Created by zeng.yubo on 2019/8/12.
 */
@Service
public class testAnnoService {

    @Autowired
    TestLockDAO dao;

    @Lock(value = "wang", leaseTime = 5)
    public String testAnno(){
        TestLockVo vo = dao.getVoById(1);
        int count = vo.getCountnum();
        if (count == 0) {
            System.out.println("===================错误");
            return "faild";
        } else {
            --count;
            System.out.println("yes");
        }
        vo.setCountnum(count);
        dao.updateVoById(vo);

        //模拟耗时操作
        Date start = new Date();
        while(true) {
            if (DateUtil.timeDifference(new Date(), start) > 600 * 1000) {
                break;
            }
        }
        return "success";
    }
}
