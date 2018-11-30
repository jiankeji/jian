package com.jian.core.es.esdao;

import com.jian.core.model.bean.PetNews;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PetNewsEsDao extends ElasticsearchRepository<PetNews,Integer> {
}
