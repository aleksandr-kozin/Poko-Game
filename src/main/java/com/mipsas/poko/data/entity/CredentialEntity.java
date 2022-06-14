package com.mipsas.poko.data.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "credentials")
public class CredentialEntity extends BaseEntity<Long> {

    private String email;
    private String password;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}
