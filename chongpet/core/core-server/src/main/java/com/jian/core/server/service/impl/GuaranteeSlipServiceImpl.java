package com.jian.core.server.service.impl;

import com.jian.core.es.esdao.GuaranteeSlipEsDao;
import com.jian.core.model.bean.GuaranteeSlip;
import com.jian.core.redis.util.RedisUtil;
import com.jian.core.server.dao.GuaranteeSlipDao;
import com.jian.core.server.service.GuaranteeSlipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@SuppressWarnings("ALL")
public class GuaranteeSlipServiceImpl implements GuaranteeSlipService {
    @Autowired
    private GuaranteeSlipDao guaranteeSlipDao;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private GuaranteeSlipEsDao guaranteeSlipEsDao;

    @Override
    public int setGuaranteeSilp(GuaranteeSlip guaranteeSlip) {
        int num = guaranteeSlipDao.setGuaranteeSilp(guaranteeSlip);
        if (num > 0){
            guaranteeSlipEsDao.save(guaranteeSlip);
        }
        return num;
    }
}
