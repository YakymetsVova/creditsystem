package com.yakymets.creditsystem.persistence.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class CreditProvider extends User {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "creditProvider", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Credit> credits = new ArrayList<>();

    public void addCredit(Credit credit) {
        credits.add(credit);
        credit.setCreditProvider(this);
    }

    public void removeCredit(Credit credit) {
        credits.remove(credit);
        credit.setCreditProvider(null);
    }
}
