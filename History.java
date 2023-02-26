package nttdata.bootcamp.quarkus.entity;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MongoEntity(collection="history")
public class History {

    private Object _id;

    private String phone;
    private String date;
    private String amount;
    private String description;

    History( String phone,String date, String amount,String description)
    {
      this.phone = phone;
      this.date = date;
      this.amount =amount;
      this.description= description;

    }

    
}
