package com.jian.core.server.dao;

import com.jian.core.model.bean.PetMsg;
import com.jian.core.model.bean.UserPet;
import org.apache.ibatis.annotations.*;

@Mapper
public interface PetMsgDao {
    final static String RESULT= "sid,pet_name as petNickName,pet_photo as petHeadImgUrl,pet_sex as sex,pet_breedpage_id as petBreedpageId" +
            "pet_birthday as petBirthday,pet_createTime as  petCreateTime";
    final static String MODEL="pet_name,pet_photo,pet_photo,pet_sex,pet_breedpage_id,pet_birthday,pet_createTime";

    final static String USER_RESULT="sid,pet_id AS petId,user_id as userId";

    @Select("select "+RESULT+" from pet ")
    void getPetAll();

    @Insert("<script> insert into pet ("+MODEL+") VALUES(#{petNickName,jdbcType=VARCHAR}," +
            "#{petHeadImgUrl,jdbcType=VARCHAR},#{sex,jdbcType=INTEGER},#{petBreedpageId,jdbcType=INTEGER}," +
            "#{petBirthday,jdbcType=INTEGER},#{petCreateTime,jdbcType=INTEGER})</script>")
    @Options(useGeneratedKeys = true,keyProperty = "sid")
    int setPet(PetMsg petMsg);

    @Update("<script> update pet set pet_name=#{pet_name,jdbcType=VARCHAR},pet_photo =#{petHeadImgUrl,jdbcType=VARCHAR},pet_sex=#{sex,jdbcType=INTEGER}," +
            "pet_breedpage_id=#{petBreedpageId,jdbcType=INTEGER},pet_birthday=#{petBirthday,jdbcType=INTEGER},isstatus = #{isstatus,jdbcType=INTEGER} </script>")
    int updatePet(PetMsg petMsg);

    @Insert("insert into to userpet ('pet_id,user_id') values(#{petId,jdbcType=INTEGER},#{userId,jdbcType=INTEGER})")
    int setUserPet(UserPet userPet);
}
