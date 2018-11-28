package com.jian.core.es.esdao;

import com.jian.core.model.bean.PetHospital;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PetHospitalEsDao extends ElasticsearchRepository<PetHospital,Integer>{
}
