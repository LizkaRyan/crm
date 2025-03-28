package site.easy.to.build.crm.entity.budget;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
public class TokenApi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTokenApi;

    private String token;

    private LocalDateTime dateExpiration;

    public TokenApi(){
        this.token= UUID.randomUUID().toString();
        this.dateExpiration=LocalDateTime.now().plusDays(2);
    }
}
