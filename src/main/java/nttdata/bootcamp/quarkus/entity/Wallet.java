package nttdata.bootcamp.quarkus.entity;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {

    private Object id;

    private String idClient;
    private String phone;
    private String password;
    private String idDebitCard;



}
