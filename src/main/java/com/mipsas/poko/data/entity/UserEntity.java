package com.mipsas.poko.data.entity;

import com.mipsas.poko.common.enums.UserStatus;
import javax.persistence.*;
import static javax.persistence.EnumType.STRING;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity<Long> {

    @Column(name = "nick_name", nullable = false, unique = true)
    private String nickName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(value = STRING)
    private UserStatus status;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;
}
