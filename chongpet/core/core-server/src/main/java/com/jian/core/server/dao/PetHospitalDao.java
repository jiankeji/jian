package com.jian.core.server.dao;

import com.jian.core.model.bean.PetHospital;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PetHospitalDao {

    final static String RESULT="sid,shoppic as shopPic,shopaddress as shopAddress,shopname as shopName,shopposition as shopPosition," +
            "shopopen as shopOpen, shoplevel as shopLevel,shopurl as shopUrl,service as serviceNum ,ctype,regionid as regionId," +
            "locationid as locationId ,location,isstatus,paying,science,cityid as cityId,cityname as cityname,shopid as shopId";

    @Select("select "+RESULT+" from pet_hospital")
    List<PetHospital> getListPetHospital();
}
