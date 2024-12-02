package com.example.resource;

import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import com.example.repository.UserRepository;
import com.example.entity.User;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.logging.Logger;

@Path("users")
public class UserResource {
    private final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @Inject
    private UserRepository userRepository;

    @GET
    @Path("{id}")
    @Produces("application/json")
    public User findUser(@PathParam("id") Long id) {
        logger.info("获取用户 ID: " + id);
        return userRepository.findById(id)
            .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }

    @GET
    @Produces("application/json")
    public List<User> findAll() {
        logger.info("获取所有用户");
        return userRepository.findAll();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public User create(User user) {
        logger.info("创建用户: " + user.getUsername());
        try {
            return userRepository.create(user);
        } catch (PersistenceException ex) {
            logger.info("创建用户失败: " + user.getUsername());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        logger.info("删除用户 ID: " + id);
        try {
            userRepository.delete(id);
        } catch (IllegalArgumentException e) {
            logger.info("删除用户失败 ID: " + id);
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public User update(User user) {
        logger.info("更新用户: " + user.getUsername());
        try {
            return userRepository.update(user);
        } catch (PersistenceException ex) {
            logger.info("更新用户失败: " + user.getUsername());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
}