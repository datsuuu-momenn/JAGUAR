package com.example.task;

import com.example.util.MockFacesContext;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import com.github.database.rider.junit5.api.DBRider;
import com.github.database.rider.junit5.util.EntityManagerProvider;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TaskBean クラスの単体テスト。
 * JUnit 5 と DatabaseRider を使用。
 */
@ExtendWith({MockitoExtension.class, DBUnitExtension.class})
@DataSet(cleanBefore = true)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TaskBeanTest {

    @Mock
    private TaskController controller;

    private FacesContext facesContext;

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @InjectMocks
    private TaskBean taskBean;

    /**
     * 各テストの前にモックを初期化し、FacesContext を設定。
     */
    @BeforeEach
    void setUp() {

        // FacesContext のモックを作成
        facesContext = MockFacesContext.mock();

        // データベース接続の初期化
        entityManagerFactory = Persistence.createEntityManagerFactory("test-PU");
        entityManager = entityManagerFactory.createEntityManager();
        initializeDatabase();
    }

    /**
     * データベースの初期化メソッド。
     */
    private void initializeDatabase() {
        entityManager.getTransaction().begin();
        // テーブル作成などのスクリプトをここに追加できます
        entityManager.createNativeQuery("CREATE TABLE IF NOT EXISTS tasks (\r\n" + //
                        "    id BIGINT AUTO_INCREMENT PRIMARY KEY,\r\n" + //
                        "    task VARCHAR(255) NOT NULL\r\n" + //
                        ");").executeUpdate();
        entityManager.getTransaction().commit();
    }

    /**
     * 各テスト後に FacesContext をクリーンアップ。
     */
    @AfterEach
    void tearDown() {
        facesContext.release();
    }

    /**
     * TaskBean の add() メソッドをテストする。
     */
    @DBRider
    @DataSet("datasets/empty-tasks.yml") // テスト前に tasks テーブルを空にする
    void testAddTask() {
        // Arrange
        String taskTitle = "新しいタスク";
        taskBean.setTitle(taskTitle);

        // Act
        taskBean.add();

        // Assert
        verify(controller, times(1)).add(taskTitle);
        
        // Verify message in FacesContext
        verify(facesContext).addMessage(eq(null), argThat(message -> 
            message.getSummary().equals("Task with title " + taskTitle + " created")
        ));
    }

    /**
     * TaskBean の delete() メソッドをテストする。
     */
    @DBRider
    @DataSet("datasets/existing-tasks.yml") // テスト前に既存のタスクをロード
    void testDeleteTask() {
        // Arrange
        String taskId = "1";
        taskBean.setId(taskId);
    
        // Act
        taskBean.delete();
    
        // Assert
        verify(controller, times(1)).delete(1L);
    
        // Verify FacesContext message
        verify(facesContext).addMessage(eq(null), argThat(message -> 
            message.getSummary().equals("Task " + taskId + " deleted")
        ));
    }

    /**
     * TaskBean の update() メソッドをテストする。
     */
    @DBRider
    @DataSet("datasets/existing-tasks.yml") // テスト前に既存のタスクをロード
    void testUpdateTask() {
        // Arrange
        String taskId = "1";
        String updatedTitle = "更新されたタスクタイトル";
        taskBean.setId(taskId);
        taskBean.setTitle(updatedTitle);
    
        // Act
        taskBean.update();
    
        // Assert
        verify(controller, times(1)).update(1L, updatedTitle);
    
        // Verify FacesContext message
        verify(facesContext).addMessage(eq(null), argThat(message -> 
            message.getSummary().equals("Task " + taskId + " updated")
        ));
    }
}

