package nttdata.bootcamp.quarkus.entity.api;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MongoEntity(collection="client")
public class Client extends ReactivePanacheMongoEntity {


    public ObjectId id;

    //public Object _id;
    public String personType;
    public String documentType;
    public String documentNumber;
    public String names;
    public String surnames;
    public String sex;
    public String email;
    public String cellPhone;



}
