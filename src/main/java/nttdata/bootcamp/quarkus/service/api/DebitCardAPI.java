package nttdata.bootcamp.quarkus.service.api;


import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import nttdata.bootcamp.quarkus.entity.api.Client;
import nttdata.bootcamp.quarkus.entity.api.DebitCard;
import org.bson.types.ObjectId;

@ApplicationScoped
public class DebitCardAPI {

    public Uni<DebitCard> findDebitCardById(String id){
        return DebitCard.findById(new ObjectId(id));
    }
}
