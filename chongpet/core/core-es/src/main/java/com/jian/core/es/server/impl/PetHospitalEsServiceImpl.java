package com.jian.core.es.server.impl;

import com.alibaba.fastjson.JSON;
import com.jian.core.es.esdao.PetHospitalEsDao;
import com.jian.core.es.server.PetHospitalEsService;
import com.jian.core.model.bean.PetHospital;
import com.jian.core.model.bo.HomePetHospitalBo;
import com.jian.core.model.util.DistanceUtil;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Component;

import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;

@Component
public class PetHospitalEsServiceImpl implements PetHospitalEsService {

    @Autowired
    PetHospitalEsDao petHospitalEsDao;

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public void PetHospitalEsSave(PetHospital petHospital) {
        petHospitalEsDao.save(petHospital);
    }

    @Override
    public List<HomePetHospitalBo> getHomePetHopsptialAll(int pageSize, int pageNum, double lat, double lon) {
        List<HomePetHospitalBo> homePetHospitalBos = new ArrayList<>();

        //搜索在100KM范围内的数据
        GeoDistanceQueryBuilder builder = QueryBuilders.geoDistanceQuery("shopPosition").point(lat, lon)
                .distance(100, DistanceUnit.KILOMETERS);

        //给距离排序
        GeoDistanceSortBuilder sortBuilder = SortBuilders.geoDistanceSort("shopPosition", lat, lon)
                .unit(DistanceUnit.KILOMETERS)
                .order(SortOrder.ASC).geoDistance(GeoDistance.ARC);


        //queryForList默认是分页，走的是queryForPage，默认10个
        Pageable pageable = new PageRequest(pageSize, pageNum);

        NativeSearchQueryBuilder builder1 =
                new NativeSearchQueryBuilder();
        builder1.withSort(sortBuilder);
        builder1.withPageable((org.springframework.data.domain.Pageable) pageable);
        SearchQuery searchQuery = builder1.build();
        List<PetHospital> petHospitalList = elasticsearchTemplate.queryForList(searchQuery, PetHospital.class);
        //System.out.println(JSON.toJSONString(petHospitalList));
        for (PetHospital petHospital : petHospitalList) {
            double lat2 = 0;
            boolean flaglat = false;
            if (petHospital.getShopPosition() != null && !petHospital.getShopPosition().equals("")) {
                if (StringUtils.isNotEmpty(petHospital.getShopPosition().split(",")[0])) {
                    lat2 = Double.parseDouble(petHospital.getShopPosition().split(",")[0]);
                    flaglat = true;
                }
                double lon2 = 0;
                boolean flaglon = false;
                if (StringUtils.isNotEmpty(petHospital.getShopPosition().split(",")[1])) {
                    lon2 = Double.parseDouble(petHospital.getShopPosition().split(",")[1]);
                    flaglon = true;
                }
                if (flaglat && flaglon) {
                    petHospital.setDistance(DistanceUtil.getDistance(lat, lon, lat2, lon2));
                }
                String hospital = JSON.toJSONString(petHospital);
                HomePetHospitalBo homePetHospitalBo = JSON.parseObject(hospital, HomePetHospitalBo.class);
                homePetHospitalBos.add(homePetHospitalBo);
            }
        }
        return homePetHospitalBos;
    }
}
