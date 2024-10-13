package com.example.resource;

import com.example.model.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/persons")
public class PersonResource {

    @PersistenceContext(unitName = "my-persistence-unit")
    private EntityManager em;

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPerson(Person person) {
        em.persist(person);
        return Response.ok(person).build();
    }
}