package com.jian.core.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.lang.reflect.Method;

@Configuration
@PropertySource(value = "classpath:/redis.properties")
public class RedisConfig extends CachingConfigurerSupport {
    //private static Log log = LogFactory.getLog ( RedisConfig.class );

    private volatile JedisConnectionFactory mJedisConnectionFactory;
    private volatile RedisTemplate<String, String> mRedisTemplate;
    //private volatile RedisCacheManager mRedisCacheManager;

    public RedisConfig() {
        super();
    }

    public RedisConfig(JedisConnectionFactory mJedisConnectionFactory, RedisTemplate<String, String> mRedisTemplate, RedisCacheManager mRedisCacheManager) {
        super();
        this.mJedisConnectionFactory = mJedisConnectionFactory;
        this.mRedisTemplate = mRedisTemplate;
        //this.mRedisCacheManager = mRedisCacheManager;
    }

    public JedisConnectionFactory redisConnectionFactory() {
        return mJedisConnectionFactory;
    }

    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory cf) {
        return mRedisTemplate;
    }

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private  int port;
    @Value("${spring.redis.timeout}")
    private  int timeout;

    @Value ( "${spring.redis.password}" )
    private String password;

//    @Autowired
//    private JedisConnectionFactory jedisConnectionFactory;

//    @Bean
//    public JedisConnectionFactory redisConnectionFactory() {
//        jedisConnectionFactory = new JedisConnectionFactory();
//        jedisConnectionFactory.setHostName(host);
//        jedisConnectionFactory.setPort(port);
//        jedisConnectionFactory.setTimeout(timeout); //设置连接超时时间
//        jedisConnectionFactory.setPassword(password);
//        //System.out.println("redis开始启动--------------------");
//        log.info("redis开始启动--------------------");
//        return jedisConnectionFactory;
//    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate0(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate (factory);
        setSerializer(template); //设置序列化工具，这样ReportBean不需要实现Serializable接口
        template.afterPropertiesSet();
        System.out.println("------redis开始启动------");
        template.setConnectionFactory ( factory );
        return template;
    }

    /**
     * 切库
     * @param factory
     * @return
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate1(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate (factory);
        setSerializer(template); //设置序列化工具，这样ReportBean不需要实现Serializable接口
        template.afterPropertiesSet();
        //template.setConnectionFactory ( factory );
        return redisChange ( 1,template );
    }


    /**
     * 切库
     * @param factory
     * @return
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate2(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate (factory);
        setSerializer(template); //设置序列化工具，这样ReportBean不需要实现Serializable接口
        template.afterPropertiesSet();
        System.out.println("------redis2开始启动------");
        //template.setConnectionFactory ( factory );
        return redisChange ( 2,template );
    }

    /**
     * 切库
     * @param factory
     * @return
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate3(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate (factory);
        setSerializer(template); //设置序列化工具，这样ReportBean不需要实现Serializable接口
        template.afterPropertiesSet();
        //template.setConnectionFactory ( factory );
        return redisChange ( 3,template );
    }


    public RedisTemplate redisChange(Integer dbNum, StringRedisTemplate template){
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory (  );
        jedisConnectionFactory.setHostName ( host );
        jedisConnectionFactory.setPort (port);
        jedisConnectionFactory.setDatabase (dbNum  );
        jedisConnectionFactory.setPassword(password);
        jedisConnectionFactory.afterPropertiesSet ();
        template.setConnectionFactory ( jedisConnectionFactory );
        return  template;
    }
    /**
     * 序列化reids
     * @param template
     */
    private void setSerializer(StringRedisTemplate template) {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility( PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        // template.setValueSerializer(jackson2JsonRedisSerializer);
    }
}
