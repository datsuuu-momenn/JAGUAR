package com.example.task;

import com.example.util.MockFacesContext;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import com.github.database.rider.junit5.api.DBRider;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;

import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.jboss.weld.junit5.WeldSetup;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * TaskBean の単体テストクラス.
 */
@ExtendWith({WeldJunit5Extension.class, DBUnitExtension.class})
@DataSet(cleanBefore = true)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TaskBeanWithRealControllerTest {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private TaskController taskController;

    private FacesContext facesContext;

    private TaskBean taskBean;

    // WeldInitiatorを使ってCDIコンテナを初期化
    @WeldSetup
    public WeldInitiator weld = WeldInitiator.from(TaskController.class, TaskBean.class).activate(RequestScoped.class).setPersistenceContextFactory(getPCFactory()).build();

    @BeforeEach
    void setUp() {

        // Mock FacesContext
        facesContext = MockFacesContext.mock();

        taskBean = new TaskBean(taskController);

    }

    @AfterEach
    void tearDown() {
        facesContext.release();
    }

    /**
     * TaskBean の add() メソッドのテスト.
     */
    @DBRider
    @DataSet("datasets/empty-tasks.yml") // テスト前に tasks テーブルを空にする
    void testAddTask() {

        // Arrange
        String taskTitle = "新しいタスク";
        taskBean.setTitle(taskTitle);

        // Act
        taskBean.add();

        // Assert: Check if the task was correctly added to the database
        List<Task> allTasks = taskBean.getAllTasks();
        assertNotNull(allTasks);
        assertEquals(1, allTasks.size());
        assertEquals(taskTitle, allTasks.get(0).getTitle());

        // Verify FacesContext message
        verify(facesContext).addMessage(eq(null), argThat(message ->
                message.getSummary().equals("Task with title " + taskTitle + " created")));
    }

    /**
     * TaskBean の delete() メソッドのテスト.
     */
    @DBRider
    @DataSet("datasets/existing-tasks.yml") // テスト前に既存のタスクをロード
    void testDeleteTask() {
        // Arrange
        String taskId = "1";
        taskBean.setId(taskId);

        // Act
        taskBean.delete();

        // Assert: Check if the task was correctly deleted from the database
        List<Task> allTasks = taskBean.getAllTasks();
        
        assertNotNull(allTasks);
        assertEquals(1, allTasks.size());
        assertEquals("初期タスク2", allTasks.get(0).getTitle());

        // Verify FacesContext message
        verify(facesContext).addMessage(eq(null), argThat(message ->
                message.getSummary().equals("Task " + taskId + " deleted")));
    }

    /**
     * TaskBean の update() メソッドのテスト.
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

        // Assert: Check if the task was correctly updated in the database
        List<Task> allTasks = taskBean.getAllTasks();
        assertNotNull(allTasks);
        assertEquals(2, allTasks.size());
        assertEquals(updatedTitle, allTasks.get(0).getTitle());

        // Verify FacesContext message
        verify(facesContext).addMessage(eq(null), argThat(message ->
                message.getSummary().equals("Task " + taskId + " updated")));
    }

    /**
     * @return EntityManager を生成するファクトリ関数.
     */
    static Function<InjectionPoint, Object> getPCFactory() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test-PU");
        
        return ip -> {
            EntityManager em = emf.createEntityManager();
            return em; 
        };
    }
}
