package com.jian.core.server.service.impl;

import com.jian.core.model.bean.PetHospital;
import com.jian.core.server.dao.PetHospitalDao;
import com.jian.core.server.service.PetHospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SuppressWarnings("ALL")
@Component
@Transactional
public class PetHospitalServiceImpl implements PetHospitalService {

    @Autowired
    private PetHospitalDao petHospitalDao;

    @Override
    public List<PetHospital> getListPetHospital() {
        return petHospitalDao.getListPetHospital();
    }
}
