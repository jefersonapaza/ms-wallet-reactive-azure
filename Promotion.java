package nttdata.bootcamp.quarkus.entity.api;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MongoEntity(collection="promotion")
public class Promotion extends ReactivePanacheMongoEntity {

    private ObjectId id;
    public String titulo;
    public String marketname;
    public String promotionalprice;
    public String discount;
    public String description;
    public String originalprice;
    public String validity;
    public Integer stock;
    public String  linkimages;


}