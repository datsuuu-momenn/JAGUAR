package com.example.resource;


import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import com.example.repository.SkillTypeRepository;
import com.example.entity.SkillType;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.logging.Logger;

@Path("skilltypes")
public class SkillTypeResource {
    private final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @Inject
    private SkillTypeRepository skillTypeRepository;

    @GET
    @Produces("application/json")
    public List<SkillType> getAllSkillTypes() {
        logger.info("获取所有技能类型");
        return skillTypeRepository.findAll();
    }

    @GET
    @Path("{code}")
    @Produces("application/json")
    public SkillType getSkillTypeByCode(@PathParam("code") String code) {
        logger.info("通过代码获取技能类型: " + code);
        return skillTypeRepository.findByCode(code)
            .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }
}