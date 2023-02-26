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
@MongoEntity(collection="debitcard")
public class DebitCard extends ReactivePanacheMongoEntity {

    public ObjectId id;


    public String cardnumber;
  //  public Integer pin;
  //  public Date expirationdate;
    public String validationcode;

    public String dniClient;

    public String estadotarjeta;



}