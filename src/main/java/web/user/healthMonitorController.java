package web.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.EchartSeries;
import entity.HealthMonitor;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.HealthMonitorService;
import util.CodeUtil;
import util.HttpServletRequestUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/user")
public class healthMonitorController {
    @Autowired
    private HealthMonitorService healthMonitorService;

    @RequestMapping(value="/addhealthinfo",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> addHealthInfo(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误的验证码");
            return modelMap;
        }
        String healthInfoStr = HttpServletRequestUtil.getString(request,"healthInfoStr");
        ObjectMapper mapper = new ObjectMapper();
        HealthMonitor healthMonitor = null;
        try{
            healthMonitor = mapper.readValue(healthInfoStr,HealthMonitor.class);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
        if(healthMonitor!=null){
            Long userId = (Long) request.getSession().getAttribute("userId");
            User user = new User();
            user.setUserId(userId);
            healthMonitor.setUser(user);
            healthMonitor.setCreateTime(new Date());
            healthMonitor.setLastEditTime(new Date());
            int effectedNum = healthMonitorService.insertHealthMonitor(healthMonitor);
            if(effectedNum>0){
                modelMap.put("success",true);
            }else{
                modelMap.put("success",false);
                modelMap.put("errMsg","提交出现错误");
            }
        }
        return modelMap;
    }

    @RequestMapping(value="/gethealthinfobysession",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getHealthInfo(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        Long userId = (Long) request.getSession().getAttribute("userId");
        List<HealthMonitor> healthMonitorList = healthMonitorService.getHealthMonitorByUserId(userId);
        List<EchartSeries> series = new ArrayList<EchartSeries>();
        List<Date> dateList = new ArrayList<Date>();
        List<Float> bloodPressureHighList = new ArrayList<Float>();
        List<Float> bloodPressureLowList = new ArrayList<Float>();
        List<Float> bloodGlucoseList = new ArrayList<Float>();
        List<Float> heartRateList = new ArrayList<Float>();
        EchartSeries es1 = new EchartSeries();
        EchartSeries es2 = new EchartSeries();
        EchartSeries es3 = new EchartSeries();
        EchartSeries es4 = new EchartSeries();
        if(healthMonitorList.size()>0) {
            for (int i = 0; i <healthMonitorList.size(); i++) {
                heartRateList.add(healthMonitorList.get(i).getHeartRate());
                bloodGlucoseList.add(healthMonitorList.get(i).getBloodGlucose());
                bloodPressureHighList.add(healthMonitorList.get(i).getBloodPressureHigh());
                bloodPressureLowList.add(healthMonitorList.get(i).getBloodPressureLow());
            }
            es1.setName("上血压");
            es1.setType("line");
            es1.setData(bloodPressureHighList);
            es2.setName("下血压");
            es2.setType("line");
            es2.setData(bloodPressureLowList);
            es3.setName("血糖");
            es3.setType("bar");
            es3.setData(bloodGlucoseList);
            es4.setName("心率");
            es4.setType("bar");
            es4.setData(heartRateList);
            series.add(es1);
            series.add(es2);
            series.add(es3);
            series.add(es4);
            modelMap.put("series",series);
            modelMap.put("success",true);
            modelMap.put("xAxis",dateList);
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty userId");
        }
        return modelMap;
    }

    @RequestMapping(value="/deleteinfobysession",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> deleteHealthInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Long userId = (Long) request.getSession().getAttribute("userId");
        int effectedNum = healthMonitorService.deleteInfoByUser(userId);
        if(effectedNum>0){
            modelMap.put("success",true);
        }else{
            modelMap.put("success",false);
        }
        return modelMap;
    }
}
