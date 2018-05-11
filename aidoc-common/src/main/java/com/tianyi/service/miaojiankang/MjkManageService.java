package com.tianyi.service.miaojiankang;

import com.tianyi.bo.miaojiankang.MjkManageResult;
import com.tianyi.dao.miaojiankang.MjkManageDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by gaozhilai on 2018/3/26.
 */
@Service("mjkManageService")
public class MjkManageService {
    @Resource
    MjkManageDao mjkManageDao;

    public List<MjkManageResult> getMjkInfo(Integer page,Integer size,Integer dataType){
        return mjkManageDao.getMjkInfo(page,size,dataType);
    }

    public int getMjkInfoTotal(Integer dataType){
        return mjkManageDao.getMjkInfoTotal(dataType);
    }
}
