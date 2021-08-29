package dev.davivieira.topologyinventory.bootstrap.samples;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/app")
public class RestExample {

    @Inject
    BeanExample beanExample;

    @Inject
    PersistenceExample persistenceExample;

    @GET
    @Path("/simple-rest")
    @Produces(MediaType.TEXT_PLAIN)
    public String simpleRest() {
        return "This REST endpoint is provided by Quarkus";
    }

    @GET
    @Path("/simple-bean")
    @Produces(MediaType.TEXT_PLAIN)
    public String simpleBean() {
        return beanExample.simpleBean();
    }

    @POST
    @Path("/request-validation")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Result validation(@Valid SampleObject sampleObject) {
        try {
            return new Result("The request data is valid!");
        } catch (ConstraintViolationException e) {
            return new Result(e.getConstraintViolations());
        }
    }

    @POST
    @Path("/create-entity")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String persistData(@Valid SampleObject sampleObject) {
        return persistenceExample.createEntity(sampleObject);
    }

    @GET
    @Path("/get-all-entities")
    public List<SampleEntity> retrieveAllEntities() {
        return persistenceExample.getAllEntities();
    }

    public static class Result {

        private String message;
        private boolean success;

        Result(String message) {
            this.success = true;
            this.message = message;
        }

        Result(Set<? extends ConstraintViolation<?>> violations) {
            this.success = false;
            this.message = violations.stream()
                    .map(cv -> cv.getMessage())
                    .collect(Collectors.joining(", "));
        }

        public String getMessage() {
            return message;
        }

        public boolean isSuccess() {
            return success;
        }

    }
}