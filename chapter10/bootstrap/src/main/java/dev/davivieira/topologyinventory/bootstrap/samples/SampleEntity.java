package dev.davivieira.topologyinventory.bootstrap.samples;

import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;

@Entity
@ApplicationScoped
@NamedQuery(name = "SampleEntity.findAll",
        query = "SELECT f FROM SampleEntity f ORDER BY f.field",
        hints = @QueryHint(name = "org.hibernate.cacheable", value = "true") )
public class SampleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Getter
    @Setter
    private String field;
    @Getter
    @Setter
    private int value;
}