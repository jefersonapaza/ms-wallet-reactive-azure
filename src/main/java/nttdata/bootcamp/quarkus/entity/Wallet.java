package nttdata.bootcamp.quarkus.entity;


import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MongoEntity(collection="wallet")
public class Wallet {

    private Object _id;

    private String idClient;
    private String phone;
    private String password;
    private String idDebitCard;

    private String validationCode;

    private String status;

}
