package com.mipsas.poko.data.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "meta_data")
public class MetaDataEntity extends BaseEntity<Long> {

    private String network;
}
