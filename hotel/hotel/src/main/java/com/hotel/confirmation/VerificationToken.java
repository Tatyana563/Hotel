package com.hotel.confirmation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hotel.model.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "token")
public class VerificationToken {
    private static final int EXPIRATION = 60 * 24;
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String token;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_fk_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;

    @Column(name = "expiration_date", nullable = false)
    private Date expiryDate;

    public Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
}