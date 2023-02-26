package nttdata.bootcamp.quarkus.repository.redis;

import com.azure.core.http.rest.Response;
import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.keys.ReactiveKeyCommands;
import io.quarkus.redis.datasource.string.StringCommands;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.mutiny.Uni;
import jakarta.ejb.Asynchronous;
import jakarta.ejb.Singleton;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import nttdata.bootcamp.quarkus.entity.redis.WalletRedis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import io.quarkus.redis.datasource.ReactiveRedisDataSource;

@ApplicationScoped
public class WalletRedisRepository implements RedisRepository {


    private ReactiveKeyCommands<String> keys;
    private StringCommands<String, WalletRedis> redisTemplate;


    @Inject
    ReactiveRedisDataSource reactiveRedisDataSource2;


    public WalletRedisRepository(RedisDataSource redisDS, ReactiveRedisDataSource reactiveRedisDS) {
        keys = reactiveRedisDS.key();
        redisTemplate = redisDS.string(WalletRedis.class);
    }

    @Override
    @Blocking
    public Uni<List<String>> findAllKeys() {
       // return keys.keys("*");
       // return reactiveRedisDataSource2.
        return keys.keys("*").map(response -> {
            List<String> result = new ArrayList<>();
            for (String r : response) {
                result.add(r);
            }
            return result;
        });
    }





    @Override
    public WalletRedis findByKey(String key) {
        return redisTemplate.get(key);
    }

    @Override
    public void save(String key ,WalletRedis walletRedis) {
        walletRedis.setExpirationDate(new Date());
        redisTemplate.set(key,walletRedis);
       // return Uni.createFrom().item(walletRedis);

    }

    @Override
    public Uni<Void> delete(String key) {
        return keys.del(key)
                .replaceWithVoid();
    }
}
