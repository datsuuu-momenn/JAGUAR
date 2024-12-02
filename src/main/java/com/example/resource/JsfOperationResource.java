package com.example.resource;

import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import com.example.repository.JsfOperationRepository;
import com.example.entity.JsfOperation;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.logging.Logger;




@Path("operations")
public class JsfOperationResource {
    private final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @Inject
    private JsfOperationRepository operationRepository;

    @GET
    @Path("{id}")
    @Produces("application/json")
    public JsfOperation findOperation(@PathParam("id") Long id) {
        logger.info("获取操作 ID: " + id);
        return operationRepository.findById(id)
            .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }

    @GET
    @Produces("application/json")
    public List<JsfOperation> findAll() {
        logger.info("获取所有操作");
        return operationRepository.findAll();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public JsfOperation create(JsfOperation operation) {
        logger.info("创建操作: " + operation.getOperationType());
        try {
            return operationRepository.save(operation);
        } catch (PersistenceException ex) {
            logger.info("创建操作失败: " + operation.getOperationType());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        logger.info("删除操作 ID: " + id);
        try {
            operationRepository.delete(id);
        } catch (IllegalArgumentException e) {
            logger.info("删除操作失败 ID: " + id);
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public JsfOperation update(JsfOperation operation) {
        logger.info("更新操作: " + operation.getOperationType());
        try {
            return operationRepository.update(operation);
        } catch (PersistenceException ex) {
            logger.info("更新操作失败: " + operation.getOperationType());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
}