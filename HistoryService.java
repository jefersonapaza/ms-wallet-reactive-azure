package nttdata.bootcamp.quarkus.service;


import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import org.bson.Document;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


import java.util.List;
import java.lang.String;
import nttdata.bootcamp.quarkus.entity.History;


@ApplicationScoped
public class HistoryService {
    @Inject
    ReactiveMongoClient mongoClient;

    public Uni<List<History>> list() {
        return getCollection().find()
                .map(doc -> {
                    
                    History history = new History();
                    history.setPhone(doc.getString("phone"));
                    history.setDate( doc.getString("date")   );
                    history.setAmount(doc.getString("amount"));
                    history.setDescription(doc.getString("description"));
                    return history;
                }).collect().asList();
    }

    public Uni<Void> add(History history) {


                 Document document = new Document()
                .append("phone",history.getPhone())
                .append("date", history.getDate())
                .append("amount",history.getAmount())
                .append("description",history.getDescription());

        return getCollection().insertOne(document)
                .onItem().ignore().andContinueWithNull();
    }

    private ReactiveMongoCollection<Document> getCollection() {
        return mongoClient.getDatabase("BOOTCAMP").getCollection("history");
    }
}
