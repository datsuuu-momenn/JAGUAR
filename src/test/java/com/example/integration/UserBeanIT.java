package com.example.integration;

import com.example.bean.UserBean;
import com.example.bean.FightBean;
import com.example.controller.UserController;
import com.example.entity.User;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpSession;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.jboss.arquillian.junit5.ArquillianExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ArquillianExtension.class)
public class UserBeanIT {

    @Inject
    private UserBean userBean;

    @Inject
    private UserController userController;

    @Inject
    private FightBean fightBean;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addClasses(UserBean.class, UserController.class, FightBean.class, User.class) // 添加相关类
                .addAsResource("META-INF/persistence.xml") // 添加 JPA 配置
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml"); // 启用 CDI
    }

    @Test
    public void testSignupSuccess() {
        // 设置测试数据
        userBean.setUsername("testUser");
        userBean.setPassword("password");

        // 执行方法
        userBean.signup();

        // 验证用户创建逻辑
        List<User> allUsers = userBean.getAllUsers();
        assertNotNull(allUsers);
        assertTrue(allUsers.stream().anyMatch(user -> "testUser".equals(user.getUsername())));

        // 验证 FacesContext 消息
        FacesMessage facesMessage = FacesContext.getCurrentInstance().getMessageList().get(0);
        assertNotNull(facesMessage);
        assertEquals("成功", facesMessage.getSummary());
        assertEquals("ユーザーが追加されました", facesMessage.getDetail());
    }

    @Test
    public void testRefresh() {
        // 执行刷新方法
        userBean.refresh();

        // 验证加载的用户
        List<User> allUsers = userBean.getAllUsers();
        assertNotNull(allUsers);
        assertFalse(allUsers.isEmpty());
    }

    @Test
    public void testRandomUserName() {
        // 验证随机用户名是否非空
        String randomName = userBean.getUsername();
        assertNotNull(randomName);
        assertFalse(randomName.trim().isEmpty());
    }

    @Test
    public void testNewAddedUsers() {
        // 验证新用户列表初始为空
        List<User> newAddedUsers = userBean.getNewAddedUsersThisTime();
        assertNotNull(newAddedUsers);
        assertTrue(newAddedUsers.isEmpty());

        // 添加新用户并验证
        userBean.setUsername("newUser");
        userBean.setPassword("newPassword");
        userBean.signup();
        newAddedUsers = userBean.getNewAddedUsersThisTime();

        assertEquals(1, newAddedUsers.size());
        assertEquals("newUser", newAddedUsers.get(0).getUsername());
    }
}