package com.example.user;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import jakarta.servlet.http.HttpSession;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
@Getter
@Setter
public class UserBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Inject
    private UserController controller;
    
    private String username;
    private String password;
    private Long id;
    private User newUser;
    private User user;
    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

    @Inject
    public UserBean(UserController controller){
        this.controller = controller;
    }
    
    public void signup() {
            // 1. 检查用户名是否为空或只包含空格
            if (username == null || username.trim().isEmpty()) {
                // 2. 获取FacesContext实例
                FacesContext.getCurrentInstance()
                    // 3. 添加错误消息
                    .addMessage(
                        null,  // null表示全局消息
                        new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,  // 错误级别
                            "エラー",                     // 错误标题
                            "ユーザー名を入力してください"  // 错误详细信息
                        )
                    );
                return;
            }
            
            // 1. 检查password是否为空或只包含空格
            if (password == null || password.trim().isEmpty()) {
                FacesContext.getCurrentInstance()
                    // 3. 添加错误消息
                    .addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "エラー", "パスワードを入力してください"));
                return;
            }
            
            // 5. 创建新用户
            try {
                // 处理提交的数据
                System.out.println("Creating user: " + username + " " + password);
                user = controller.createUser(username, password);
                session.setAttribute("user", user); 
                    // 添加成功消息
                System.out.println("User created: " + user);
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage("成功", "ユーザーが追加されました"));
                    
                 //DB確認：DBからユーザーを取得
                newUser = controller.findUserByUsername(username);
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                    // 添加错误消息
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "エラー", e.getMessage()));
                return;
            }
    }
    
    private void clearFields() {
        username = null;
        password = null;
        id = null;
    }

    public boolean isRegistrationSuccessful() {
        user = (User) session.getAttribute("user");
        newUser = controller.findUserByUsername(user.getUsername());
        return newUser != null;
    }
    
} 