package web.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import dto.FriendExecution;
import dto.UserExecution;
import entity.Doctor;
import entity.Friend;
import entity.Hospital;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.FriendService;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class FriendController {
    @Autowired
    private FriendService friendService;
    @Autowired
    private UserService userService;

    /**
     * 处理用户通过hospitalId发送的签约请求
     */
    @RequestMapping(value="/signbyhospitalid",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> sign(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<String, Object>();
        Friend friend = new Friend();
        Long userId = (Long)request.getSession().getAttribute("userId");
        friend.setUserId(userId);
        Long hospitalId = Long.parseLong(request.getParameter("hospitalId"));
        Date createTime = new Date();
        friend.setCreateTime(createTime);
        friend.setHospitalId(hospitalId);
        FriendExecution fe1 = friendService.getByUserIdAndHospitalId(userId,hospitalId);
        if(fe1.getFriend()!=null){
                modelMap.put("success",false);
                return modelMap;
        }
        FriendExecution fe2 = friendService.getByUserId(userId);
        if(fe2.getFriendList().size()>0){
                modelMap.put("success",false);
                return modelMap;
        }
        if (friend.getUserId() > -1 && friend.getHospitalId() > -1) {
            FriendExecution fe = friendService.insertFriendShip(friend);
            modelMap.put("success", true);
            modelMap.put("friend", fe.getFriend());
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "提交信息为空");
        }
        return modelMap;
    }

    /**
     * 医院审核通过向自己发送签约请求的用户（也可以审核不通过，这里只要通过更改hospitalFollow的状态就可以代表是否审核通过）
     */
    @RequestMapping(value="/verifyuser",method=RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> verifyUser(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        Hospital currentHospital = (Hospital)request.getSession().getAttribute("currentHospital");
        Long hospitalId = currentHospital.getHospitalId();
        Friend friend = new Friend();
        friend.setHospitalId(hospitalId);
        Long userId = Long.parseLong(request.getParameter("userId"));
        friend.setUserId(userId);
        if(friend.getUserId()>-1&&friend.getHospitalId()>-1){
            FriendExecution fe = friendService.updateFriendShip(friend);
            modelMap.put("success",true);
            modelMap.put("friend",fe.getFriend());
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","提交信息为空");
        }
        return modelMap;
    }

    /**
     * 用户通过此URL 可以与自己签约的医院进行单方面的解约
     */
    @RequestMapping(value="/deletefriend",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> deleteFriend(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        Friend friend = new Friend();
        Long userId = (Long)request.getSession().getAttribute("userId");
        friend.setUserId(userId);
        Long hospitalId = Long.parseLong(request.getParameter("hospitalId"));
        friend.setHospitalId(hospitalId);
        User user = new User();
        Doctor doctor = new Doctor();
        doctor.setDoctorId(0L);
        user.setDoctor(doctor);
        user.setUserId(userId);
        try{
            if(friend.getUserId()>-1&&friend.getHospitalId()>-1){
                FriendExecution fe = friendService.deleteFriendShip(friend);
                UserExecution ue = userService.suModify(user);
                modelMap.put("success",true);
                modelMap.put("friend",fe.getFriend());
                modelMap.put("user",ue.getUser());
            }else{
                modelMap.put("success",false);
                modelMap.put("errMsg","提交信息为空");
            }}catch (Exception e){
            modelMap.put("success",false);
        }
        return modelMap;
    }

    /**
     * 医院通过这个URL与用户解约（删除用户）
     * @param request
     * @return
     */
    @RequestMapping(value="/deleteUser",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> deleteUser(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        Friend friend = new Friend();
        Long userId = Long.parseLong(request.getParameter("userId"));
        friend.setUserId(userId);
        User user = new User();
        Doctor doctor = new Doctor();
        doctor.setDoctorId(0L);
        user.setDoctor(doctor);
        user.setUserId(userId);
        Hospital currentHospital = (Hospital)request.getSession().getAttribute("currentHospital");
        friend.setHospitalId(currentHospital.getHospitalId());
        try{
            if(friend.getUserId()>-1&&friend.getHospitalId()>-1){
                FriendExecution fe = friendService.deleteFriendShip(friend);
                UserExecution ue = userService.suModify(user);
                modelMap.put("success",true);
                modelMap.put("friend",fe.getFriend());
                modelMap.put("user",ue.getUser());
            }else{
                modelMap.put("success",false);
                modelMap.put("errMsg","提交信息为空");
            }}catch (Exception e){
            modelMap.put("success",false);
        }
        return modelMap;
    }

    /**
     * 用户通过此url 查看自己的签约情况
     */
    @RequestMapping(value="/getfriendbyuser",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getFriendShipByUserId(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        //int userId = Integer.parseInt(request.getParameter("userId"));
        Long userId = (Long) request.getSession().getAttribute("userId");
        if(userId>-1){
            FriendExecution fe= friendService.getByUserId(userId);
            Date createTime = fe.getFriendList().get(0).getCreateTime();
            if(fe.getFriendList().size()>0){
                modelMap.put("success",true);
                modelMap.put("friendList",fe.getFriendList());
            }else {
                modelMap.put("success",false);
            }
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","用户ID不存在");
        }
        return modelMap;
    }

    /**
     * 医院通过此url获取有意向与自己签约的用户列表
     */
    @RequestMapping(value="/getfriendbyhospital",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getFriendShipByHospitalId(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        Hospital currentHospital = (Hospital)request.getSession().getAttribute("currentHospital");
        long HospitalId = Long.parseLong(String.valueOf(currentHospital.getHospitalId()));
        if(HospitalId>-1){
            FriendExecution fe= friendService.getByHospitalId(HospitalId);
            modelMap.put("success",true);
            modelMap.put("friendList",fe.getFriendList());
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","医院ID不存在");
        }
        return modelMap;
    }

    /**
     * 医院通过此url获取自己已经审核通过的用户信息
     */
    @RequestMapping(value="/getfriendsigned",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getFriendWhoHasSigned(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        Hospital currentHospital = (Hospital)request.getSession().getAttribute("currentHospital");
        long HospitalId = Long.parseLong(String.valueOf(currentHospital.getHospitalId()));
        if(HospitalId>-1){
            FriendExecution fe = friendService.getFriendByHospitalId(HospitalId);
            modelMap.put("success",true);
            modelMap.put("friendList",fe.getFriendList());
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","医院ID不存在");
        }
        return modelMap;
    }
}
