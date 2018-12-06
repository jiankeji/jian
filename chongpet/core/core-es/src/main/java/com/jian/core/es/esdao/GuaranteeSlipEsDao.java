package com.jian.core.es.esdao;

import com.jian.core.model.bean.GuaranteeSlip;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface GuaranteeSlipEsDao extends ElasticsearchRepository<GuaranteeSlip,Integer> {
}
