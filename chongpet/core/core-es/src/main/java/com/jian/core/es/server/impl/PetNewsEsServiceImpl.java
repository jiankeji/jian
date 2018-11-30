package com.jian.core.es.server.impl;

import com.jian.core.es.esdao.PetNewsEsDao;
import com.jian.core.es.server.PetNewsEsService;
import com.jian.core.model.bean.PetNews;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class PetNewsEsServiceImpl implements PetNewsEsService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private PetNewsEsDao petNewsEsDao;

    @Override
    public void saveNews(PetNews petNews) {
        petNewsEsDao.save(petNews);
    }

    @Override
    public PetNews selectOneNews(int sid,String classification) {
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        builder.must(QueryBuilders.termQuery("sid",sid));
        builder.must(QueryBuilders.termQuery("classification",classification));

        NativeSearchQueryBuilder builder1 =
                new NativeSearchQueryBuilder();
        builder1.withQuery(builder);

        SearchQuery searchQuery = builder1.build();
        List<PetNews> petNewsList = elasticsearchTemplate.queryForList(searchQuery,PetNews.class);
        if (petNewsList.isEmpty())return null;
        return petNewsList.get(0);
    }

    @Override
    public PetNews getNews(int sid) {
        QueryBuilder qb = QueryBuilders.idsQuery().addIds(sid+"");
        NativeSearchQueryBuilder builder1 =
                new NativeSearchQueryBuilder();
        builder1.withFilter(qb);
        SearchQuery searchQuery = builder1.build();
        List<PetNews> petNewsList =  elasticsearchTemplate.queryForList(searchQuery,PetNews.class);
        if(petNewsList.isEmpty())return null;
        return petNewsList.get(0);
    }
}
