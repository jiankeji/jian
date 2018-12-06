package com.jian.core.server.service;

import com.jian.core.model.bo.PlanAndGuaranteeBo;

import java.util.List;

public interface PlanAndGuaranteeService {

    List<PlanAndGuaranteeBo> getBoAll(int planId);
}
