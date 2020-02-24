package service;

import dto.HospitalExecution;
import dto.ImageHolder;
import entity.Hospital;
import exceptions.HospitalOperationException;

public interface HospitalService {
    HospitalExecution addHospital(Hospital hospital, ImageHolder thumbnail);
    Hospital getByHospitalId(long HospitalId);
    HospitalExecution modifyHospital(Hospital hospital,ImageHolder thumbnail)throws HospitalOperationException;
    public HospitalExecution getHospitalList(Hospital hospitalCondition,int pageIndex,int pageSize);
}
