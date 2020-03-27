package web.superadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.DoctorExecution;
import dto.UserExecution;
import entity.Doctor;
import entity.Hospital;
import entity.User;
import enums.HospitalStateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.DoctorService;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/superadmin")
public class SuDoctorController {
    @Autowired
    private DoctorService doctorService;

    @RequestMapping(value="listdoctor",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> listDoctor(){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Doctor doctor = new Doctor();
        DoctorExecution de = null;
        try {
            de = doctorService.suGetDoctorList();
            modelMap.put("rows",de.getDoctorList());
            modelMap.put("total",de.getCount());
        }catch (Exception e){
            e.printStackTrace();
            modelMap.put("success",false);
            modelMap.put("errM",e.toString());
        }
        return modelMap;
    }


    @RequestMapping(value = "/modifydoctor", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyHospital(String doctorStr, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        Doctor doctor = null;
        Hospital hospital = new Hospital();
        hospital.setHospitalId(17L);
        try {
            doctor = mapper.readValue(doctorStr, Doctor.class);
            doctor.setDoctorName((doctor.getDoctorName()==null)?null: URLDecoder.decode(doctor.getDoctorName(),"UTF-8"));
            doctor.setHospital(hospital);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        // 空值判断
        if ( doctor.getDoctorId()>0) {
            try {
                // 修改用户
                DoctorExecution de = doctorService.suModifyDoctor(doctor);
                if (de.getState() == HospitalStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", de.getStateInfo());
                }
            } catch (RuntimeException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入医生信息");
        }
        return modelMap;
    }
}
