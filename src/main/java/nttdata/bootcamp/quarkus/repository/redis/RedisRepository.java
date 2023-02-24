package nttdata.bootcamp.quarkus.repository.redis;

import io.smallrye.mutiny.Uni;
import nttdata.bootcamp.quarkus.entity.redis.WalletRedis;

import java.util.List;
import java.util.Map;

public interface RedisRepository {

    Uni<List<String>> findAllKeys();
    WalletRedis findByKey(String id);
    void save(String key , WalletRedis walletRedis);

    Uni<Void> delete(String id);
}
