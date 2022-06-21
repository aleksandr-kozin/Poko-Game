package com.mipsas.poko.data.entity;

import com.mipsas.poko.common.enums.UserAuthority;
import com.mipsas.poko.common.enums.UserStatus;
import java.util.Set;
import static javax.persistence.CascadeType.REMOVE;
import javax.persistence.*;
import static javax.persistence.EnumType.STRING;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
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

    @Enumerated(value = STRING)
    private UserAuthority role;

    @OneToMany(mappedBy = "user", cascade = REMOVE)
    private Set<UserLocationEntity> userLocations;

    @OneToMany(mappedBy = "user", cascade = REMOVE)
    private Set<MetaDataEntity> userMetaData;

    @OneToMany(mappedBy = "user", cascade = REMOVE)
    private Set<NetworkInfoEntity> networks;
}
