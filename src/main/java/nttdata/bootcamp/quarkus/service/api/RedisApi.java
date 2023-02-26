package nttdata.bootcamp.quarkus.service.api;

import com.azure.core.annotation.Get;
import io.smallrye.mutiny.Uni;


import nttdata.bootcamp.quarkus.entity.redis.WalletRedis;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@RegisterRestClient
@Path("/increments")
public interface RedisApi {

   // @POST
    //public void add(WalletRedis walletRedis);

    @GET
    public Uni<List<String>> getAll();

}
