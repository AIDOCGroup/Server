package com.tianyi.web.controller.miaojiankang;

import com.tianyi.bo.miaojiankang.MjkManageResult;
import com.tianyi.service.miaojiankang.MjkManageService;
import com.tianyi.web.model.PagedListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by gaozhilai on 2018/3/26.
 */
@RestController
@RequestMapping(value = "/mjk")
public class MjkManageController {
    @Autowired
    MjkManageService mjkManageService;

    @RequestMapping("getMjkInfo")
    public PagedListModel getMjkInfo(@RequestParam(value = "p", required = false) Integer page,
                                            @RequestParam(value = "p_size", required = false) Integer pageSize,
                                            @RequestParam(value = "datatype", required = false) Integer dataType){
        page = page == null ? 1 : page;
        pageSize = pageSize == null ? 20 : pageSize;
        List<MjkManageResult> result=mjkManageService.getMjkInfo(page,pageSize,dataType);
        int total=mjkManageService.getMjkInfoTotal(dataType);
        return new PagedListModel(result,total,page,pageSize);
    }
}
