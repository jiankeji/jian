package com.jian.core.server.dao;

import com.jian.core.model.bean.GuaranteeSlip;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface GuaranteeSlipDao {

    final static String MODEL="u_id,p_id,begin_time,end_time,pet_type,lineage,dog_num,pet_id,birthdate,diseases_history,if_sterilization,pet_img_url," +
            "user_name,papers_type,papers_num,phone_num,email,price,isstatus";

    @Insert("insert into t_guarantee_slip("+MODEL+") values(u_id = #{uid},p_id= #{pid},begin_time= #{beginTime},end_time= #{endTime},pet_type= #{petType},lineage= #{lineage},dog_num= #{dogNum},pet_id= #{petId}," +
            "birthdate= #{birthdate},diseases_history= #{diseasesHistory},if_sterilization= #{ifSterilization},pet_img_url= #{petImgUrl},"+
            "user_name= #{UserName},papers_type= #{papersType},papers_num= #{papersNum},phone_num= #{phoneNum},email= #{email},price= #{price},isstatus= #{isstatus})")
    @Options(useGeneratedKeys = true,keyProperty = "sid")
    int setGuaranteeSilp(GuaranteeSlip guaranteeSlip);
}
