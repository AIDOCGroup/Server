package me.aidoc.appserver.admin.controller;

import me.aidoc.appserver.admin.controller.sys.AbstractController;
import me.aidoc.appserver.admin.controller.vo.AidocStatisticsVO;
import me.aidoc.appserver.admin.controller.vo.AppInfoStatisticsVO;
import me.aidoc.appserver.admin.controller.vo.TransferStatisticsVO;
import me.aidoc.appserver.common.PageData;
import me.aidoc.appserver.service.UserAppInformationService;
import me.aidoc.appserver.service.user.AccountStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * 统计相关的接口
 *
 * @author vliu
 * @create 2018-10-09 13:51
 **/
@Controller
@RequestMapping(value = "/admin/statistics")
public class AdminStatisticsController extends AbstractController{

    @Autowired
    private AccountStatisticsService accountStatisticsService;

    @Autowired
    private UserAppInformationService userAppInformationService;

    /**
     * aidoc统计
     * @param response
     * @param aidocStatisticsVO
     */
    @RequestMapping("/aidoc")
    public void aidoc(HttpServletResponse response,@RequestBody AidocStatisticsVO aidocStatisticsVO){
        PageData pageData = accountStatisticsService.aidocStatistics(aidocStatisticsVO.getUser_id(),aidocStatisticsVO.getTime_sort(),
                aidocStatisticsVO.getStep_sort(),aidocStatisticsVO.getReward_sort(),
                aidocStatisticsVO.getLike_param(),aidocStatisticsVO.getPage_index(),aidocStatisticsVO.getPage_size());
        renderSuccessJson(response,pageData);
    }

    /**
     * 设备信息统计
     * @param response
     * @param appInfoStatisticsVO
     */
    @RequestMapping("/app_info")
    public void appInfo(HttpServletResponse response,@RequestBody AppInfoStatisticsVO appInfoStatisticsVO){
        PageData pageData = userAppInformationService.selectUserAppInfo(appInfoStatisticsVO.getUser_id(),
                appInfoStatisticsVO.getMobile(),appInfoStatisticsVO.getStart_time(),appInfoStatisticsVO.getStop_time(),
                appInfoStatisticsVO.getPage_index(),appInfoStatisticsVO.getPage_size());
        renderSuccessJson(response,pageData);
    }

    /**
     * 转入转出记录统计
     * @param response
     * @param transferStatisticsVO
     */
    @RequestMapping("/transfer")
    public void transfer(HttpServletResponse response,@RequestBody  TransferStatisticsVO transferStatisticsVO){
        PageData pageData = null;
        if (transferStatisticsVO.getType() == 0){//冲币
            pageData = accountStatisticsService.rechargeRecord(transferStatisticsVO.getUser_id(),transferStatisticsVO.getStart_time(),
                    transferStatisticsVO.getStop_time(),transferStatisticsVO.getMobile(),transferStatisticsVO.getPage_index(),
                    transferStatisticsVO.getPage_size());
        }else{//提币
            pageData = accountStatisticsService.drawingsRecord(transferStatisticsVO.getUser_id(),transferStatisticsVO.getStart_time(),
                    transferStatisticsVO.getStop_time(),transferStatisticsVO.getMobile(),transferStatisticsVO.getPage_index(),
                    transferStatisticsVO.getPage_size());
        }
        renderSuccessJson(response,pageData);
    }
}
