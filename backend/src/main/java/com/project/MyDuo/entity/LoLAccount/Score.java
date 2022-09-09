package com.project.MyDuo.entity.LoLAccount;

import lombok.*;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Score {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "score_id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lol_account_id")
    private LoLAccount loLAccount;

    @Column(length = 10)
    private String result;
    public Score(String result, LoLAccount loLAccount) {
        this.loLAccount = loLAccount;
        this.result = result;
    }
}
