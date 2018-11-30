package com.jian.core.server.dao;

import com.jian.core.model.bean.PetMsg;
import com.jian.core.model.bean.UserPet;
import org.apache.ibatis.annotations.*;

@Mapper
public interface PetMsgDao {
    final static String RESULT= "sid,pet_name as petNickName,pet_photo as petHeadImgUrl,pet_sex as sex,pet_breedpage_id as petBreedpageId" +
            "pet_birthday as petBirthday,pet_createTime as  petCreateTime,pet_signautre as petSignautre";
    final static String MODEL="pet_name,pet_photo,pet_sex,pet_breedpage_id,pet_birthday,pet_createTime,pet_signautre";

    final static String USER_RESULT="sid,pet_id AS petId,user_id as userId";

    @Select("select "+RESULT+" from pet where isstatus =0")
    void getPetAll();

    @Insert("<script> insert into pet ("+MODEL+") VALUES(#{petNickName,jdbcType=VARCHAR}," +
            "#{petHeadImgUrl,jdbcType=VARCHAR},#{sex,jdbcType=INTEGER},#{petBreedpageId,jdbcType=INTEGER}," +
            "#{petBirthday,jdbcType=INTEGER},#{petCreateTime,jdbcType=INTEGER},#{petSignautre,jdbcType=VARCHAR})</script>")
    @Options(useGeneratedKeys = true,keyProperty = "sid")
    int setPet(PetMsg petMsg);

    @Update("<script> update pet set pet_name=#{petNickName,jdbcType=VARCHAR},pet_photo =#{petHeadImgUrl,jdbcType=VARCHAR},pet_sex=#{sex,jdbcType=INTEGER}," +
            "pet_breedpage_id=#{petBreedpageId,jdbcType=INTEGER},pet_birthday=#{petBirthday,jdbcType=INTEGER},isstatus = #{isstatus,jdbcType=INTEGER},pet_signautre=#{petSignautre,jdbcType=VARCHAR} " +
            "where sid=#{sid,jdbcType=INTEGER}</script>")
    int updatePet(PetMsg petMsg);

    @Insert("insert into  userpet (pet_id,user_id) values(#{petId,jdbcType=INTEGER},#{userId,jdbcType=INTEGER})")
    int setUserPet(UserPet userPet);
}
