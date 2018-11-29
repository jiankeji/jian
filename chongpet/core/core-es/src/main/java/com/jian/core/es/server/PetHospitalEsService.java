package com.jian.core.es.server;

import com.jian.core.model.bean.PetHospital;
import com.jian.core.model.bo.HomePetHospitalBo;

import java.util.List;

public interface PetHospitalEsService {

    /**
     * TODO 保存宠物医院信息到es中
     * @param petHospital
     */
    void  PetHospitalEsSave(PetHospital petHospital);

    /**
     * TODO 查询首页宠物医院分页(首页和搜索通用)
     * @return list
     */
    List<HomePetHospitalBo> getHomePetHopsptialAll( int pageNum,int pageSize, double lat, double lon,String context);

}
