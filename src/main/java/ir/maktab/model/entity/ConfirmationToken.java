package ir.maktab.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="token_id")
    private long tokenid;

    @Column(name="confirmation_token")
    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @OneToOne(targetEntity = Account.class, fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "user_id")
    private Account account;

    public ConfirmationToken(Account account) {
        this.account = account;
        createdDate = new Date();
        confirmationToken = UUID.randomUUID().toString();
    }


}