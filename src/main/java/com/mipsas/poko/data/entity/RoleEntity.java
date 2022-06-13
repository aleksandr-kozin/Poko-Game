package com.mipsas.poko.data.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "roles")
public class RoleEntity extends BaseEntity<Byte> {
    private String name;
}
