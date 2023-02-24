package nttdata.bootcamp.quarkus.service.api;


import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import nttdata.bootcamp.quarkus.entity.api.Client;
import org.bson.types.ObjectId;

@ApplicationScoped
public class ClientAPI {
    
    public Uni<Client> findClientById(String id){
        return Client.findById(new ObjectId(id));
    }

}
