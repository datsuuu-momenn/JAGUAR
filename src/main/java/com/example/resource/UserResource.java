package com.example.resource;

import com.example.entity.User;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;

@Path("/users")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @PersistenceContext
    EntityManager em;

    @Context
    UriInfo uriInfo;

    @GET
    public Response getAllUsers() {
        List<User> users = em.createNamedQuery(User.FIND_ALLUsers, User.class)
                .getResultList();
        return Response.ok(users).build();
    }

    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") Long id) {
        User user = em.find(User.class, id);
        if (user == null) {
            throw new NotFoundException("用户ID " + id + " 未找到");
        }
        return Response.ok(user).build();
    }

    @POST
    @Transactional
    public Response createUser(@Valid User user) {
        em.persist(user);
        
        URI location = uriInfo.getBaseUriBuilder()
                .path(UserResource.class)
                .path(user.getId().toString())
                .build();
                
        return Response.created(location)
                .entity(user)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateUser(@PathParam("id") Long id, @Valid User user) {
        User existingUser = em.find(User.class, id);
        if (existingUser == null) {
            throw new NotFoundException("用户ID " + id + " 未找到");
        }
        
        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());
        
        return Response.ok(existingUser).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteUser(@PathParam("id") Long id) {
        User user = em.find(User.class, id);
        if (user == null) {
            throw new NotFoundException("用户ID " + id + " 未找到");
        }
        em.remove(user);
        return Response.noContent().build();
    }
}