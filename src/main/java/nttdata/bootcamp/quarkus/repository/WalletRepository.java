package nttdata.bootcamp.quarkus.repository;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import io.smallrye.mutiny.Multi;
import jakarta.enterprise.context.ApplicationScoped;
import nttdata.bootcamp.quarkus.entity.Wallet;
import org.bson.Document;

@ApplicationScoped
public class WalletRepository implements ReactivePanacheMongoRepository<Wallet> {


    public Multi<Wallet> getAll() {
        return streamAll();
    }



}
