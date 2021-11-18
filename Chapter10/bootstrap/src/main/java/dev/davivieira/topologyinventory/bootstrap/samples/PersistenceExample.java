package dev.davivieira.topologyinventory.bootstrap.samples;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class PersistenceExample {

    @Inject
    EntityManager em;

    @Transactional
    public String createEntity(SampleObject sampleObject) {
        SampleEntity sampleEntity = new SampleEntity();
        sampleEntity.setField(sampleObject.field);
        sampleEntity.setValue(sampleObject.value);
        em.persist(sampleEntity);
        return "Entity with field "+sampleObject.field+" created!";
    }

    @Transactional
    public List<SampleEntity> getAllEntities(){
        return em.createNamedQuery("SampleEntity.findAll", SampleEntity.class)
                .getResultList();
    }
}
