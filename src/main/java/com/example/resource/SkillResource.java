package com.example.resource;


import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import com.example.repository.SkillRepository;
import com.example.entity.Skill;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.logging.Logger;


@Path("skills")
public class SkillResource {
    private final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @Inject
    private SkillRepository skillRepository;

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Skill findSkill(@PathParam("id") int id) {
        logger.info("获取技能 ID: " + id);
        return skillRepository.findById(id)
            .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }

    @GET
    @Produces("application/json")
    public List<Skill> findAll() {
        logger.info("获取所有技能");
        return skillRepository.findAll();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Skill create(Skill skill) {
        logger.info("创建技能: " + skill.getSkillName());
        try {
            return skillRepository.save(skill);
        } catch (PersistenceException ex) {
            logger.info("创建技能失败: " + skill.getSkillName());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") int id) {
        logger.info("删除技能 ID: " + id);
        try {
            skillRepository.deleteById(id);
        } catch (IllegalArgumentException e) {
            logger.info("删除技能失败 ID: " + id);
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Skill update(Skill skill) {
        logger.info("更新技能: " + skill.getSkillName());
        try {
            return skillRepository.update(skill);
        } catch (PersistenceException ex) {
            logger.info("更新技能失败: " + skill.getSkillName());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
}