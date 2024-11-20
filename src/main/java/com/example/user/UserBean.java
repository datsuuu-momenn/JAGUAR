package com.example.user;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import jakarta.servlet.http.HttpSession;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import java.util.Random;

@Named
@ViewScoped
public class UserBean implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private List<User> allUsers;

    private List<User> newAddedUsersThisTime = new ArrayList<>();
    
    @Inject
    private UserController controller;
    
    // 事前入力テキストフィールド としてランダムなユーザー名を設定 
    @Getter
    @Setter
    private String username  = randomUserName();

    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private User newUser;
    @Getter
    @Setter
    private User user;
    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

    @PostConstruct
    public void postConstruct() {
        refresh();
    }

    @Inject
    public UserBean(UserController controller){
        this.controller = controller;
    }
    
    public void signup() {
        System.out.println("signup method called"); 
            // // 1. 检查用户名是否为空或只包含空格
            // if (username == null || username.trim().isEmpty()) {
            //     // 2. 获取FacesContext实例
            //     FacesContext.getCurrentInstance()
            //         // 3. 添加错误消息
            //         .addMessage(
            //             null,  // null表示全局消息
            //             new FacesMessage(
            //                 FacesMessage.SEVERITY_ERROR,  // 错误级别
            //                 "エラー",                     // 错误标题
            //                 "ユーザー名を入力してください"  // 错误详细信息
            //             )
            //         );
            //     return;
            // }
            
            // // 1. 检查password是否为空或只包含空格
            // if (password == null || password.trim().isEmpty()) {
            //     FacesContext.getCurrentInstance()
            //         // 3. 添加错误消息
            //         .addMessage(null, 
            //             new FacesMessage(FacesMessage.SEVERITY_ERROR, "エラー", "パスワードを入力してください"));
            //     return;
            // }
            
            // 5. 创建新用户
            try {
                // 处理提交的数据
                System.out.println("Creating user: " + username + " " + password);
                user = controller.createUser(username, password);
                session.setAttribute("user", user); 
                newAddedUsersThisTime.add(user);
                session.setAttribute("newAddedUsersThisTime", newAddedUsersThisTime);
                    // 添加成功消息
                System.out.println("User created: " + user);
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage("成功", "ユーザーが追加されました"));
                    
                 //DB確認：DBからユーザーを取得
                System.out.println("refresh method called in signup");
                refresh();
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

    // refreshメソッド：DBからすべてのユーザーを取得
    public void refresh() {
        System.out.println("refresh method called");
        this.allUsers = controller.loadAllUsers();
    }
    
    public List<User> getAllUsers() {
        System.out.println("getAllUsers method called in UserBean");
        return allUsers;
    }

    public List<User> getNewAddedUsersThisTime() {
        System.out.println("getNewAddedUsersThisTime method called in UserBean");
        return newAddedUsersThisTime;
    }

    private void addMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
    }

    // ランダムなユーザー名を生成
    private String randomUserName() {
        String[] names = {"モクロー", "owl", "ふくろう", "梟", "アチャモ", "ミズゴロウ","ジラーチ", 
                        "ポッチャマ", "ヒノアラシ", "ワニノコ", "ゼニガメ", "ヒトカゲ", "フシギダネ"}; 
        Random random = new Random();
        int index = random.nextInt(names.length);
        this.username = names[index];
        return username;
    }
} 