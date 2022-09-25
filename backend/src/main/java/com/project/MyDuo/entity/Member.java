package com.project.MyDuo.entity;

import com.project.MyDuo.dto.AccountDto;
import com.project.MyDuo.entity.LoLAccount.LoLAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter @Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotNull
    @Embedded
    @Column(name = "user_email", length = 100)
    private String email;

    @NotNull
    @Column(name = "user_name", length = 100)
    private String name;

    @NotNull
    @Column(name = "user_pwd", length = 200)
    private String password;

    @NotNull
    @Column(name = "user_heart")
    private int heart;

    @NotNull
    @Column(name = "user_valid")
    private Boolean valid;

    private Role role;

    @Column(name = "user_memo")
    private String memo;
    @Column(name = "representative_account")
    private String loLRepPuuid;

    @Builder
    public Member(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
        heart=0;
        valid=true;
        role=Role.USER;
    }
    public Member(AccountDto accountDto){
        this.id = accountDto.getId();
        this.name = accountDto.getName();
        this.email = accountDto.getEmail();
        this.password = accountDto.getPassword();
        this.heart = accountDto.getHeart();
        this.heart = accountDto.getHeart();
        this.valid = accountDto.getValid();
        this.role = accountDto.getRole();
    }
    @OneToMany(fetch = FetchType.LAZY )
    @JoinColumn(name = "user_id",updatable = false,insertable = false)
    private List<Board> boardList = new CopyOnWriteArrayList<>();

    /* LoLAccount용 메서드
    * 작성자 : Jeong Seong Soo*/
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LoLAccount> lolAccounts = new CopyOnWriteArrayList<>();

    public void addLoLAccount(LoLAccount account) {
        this.lolAccounts.add(account);

        if (account.getUser() != this)
            account.changeUser(this);
    }

    public void removeLoLAccount(String lolPuuid) {
        for (int idx = 0; idx < lolAccounts.size(); idx++) {
            if (!lolAccounts.get(idx).getPuuid().equals(lolPuuid)) {
                lolAccounts.remove(idx);
                return;
            }
        }
    }

    public void addBoard(Board board) { this.boardList.add(board); }

    public void setHeartPlus(){
        this.heart++;
    }

    @OneToMany(fetch = FetchType.LAZY )
    @JoinColumn(name = "user_id",updatable = false,insertable = false)
    private List<Friend> friends;
}
