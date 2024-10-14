package com.example.task;

import com.example.util.MockFacesContext;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * TaskBean クラスの単体テスト。
 * JUnit 5 と DatabaseRider を使用。
 */
@DBRider
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class) 
class TaskBeanTest {

    @Mock
    private TaskController controller;

    private FacesContext facesContext;

    @InjectMocks
    private TaskBean taskBean;

    /**
     * 各テストの前にモックを初期化し、FacesContext を設定。
     */
    @BeforeEach
    void setUp() {
        // MockitoExtension によって @Mock と @InjectMocks が自動的に初期化される
        // FacesContext のモックを作成
        facesContext = MockFacesContext.mockFacesContext();
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
    @Test
    @DataSet("datasets/empty-tasks.yml") // テスト前に tasks テーブルを空にする
    void testAddTask() {
        // Arrange
        String taskTitle = "新しいタスク";
        taskBean.setTitle(taskTitle);

        // Act
        taskBean.add();

        // Assert
        // controller.add が正しいタイトルで呼び出されたことを確認
        verify(controller, times(1)).add(taskTitle);

        // 成功メッセージが追加されたことを確認
        ArgumentCaptor<FacesMessage> messageCaptor = ArgumentCaptor.forClass(FacesMessage.class);
        verify(facesContext).addMessage(eq(null), messageCaptor.capture());
        FacesMessage addedMessage = messageCaptor.getValue();
        assertEquals("Task with title " + taskTitle + " created", addedMessage.getSummary());

        // refresh() が呼び出され、タスクが再読み込みされたことを確認
        verify(controller, times(1)).loadAll();
    }

    /**
     * TaskBean の delete() メソッドをテストする。
     */
    @Test
    @DataSet("datasets/existing-tasks.yml") // テスト前に既存のタスクをロード
    void testDeleteTask() {
        // Arrange
        String taskId = "1";
        taskBean.setId(taskId);

        // Act
        taskBean.delete();

        // Assert
        // controller.delete が正しい ID で呼び出されたことを確認
        verify(controller, times(1)).delete(1L);

        // 成功メッセージが追加されたことを確認
        ArgumentCaptor<FacesMessage> messageCaptor = ArgumentCaptor.forClass(FacesMessage.class);
        verify(facesContext).addMessage(eq(null), messageCaptor.capture());
        FacesMessage addedMessage = messageCaptor.getValue();
        assertEquals("Task " + taskId + " deleted", addedMessage.getSummary());

        // refresh() が呼び出され、タスクが再読み込みされたことを確認
        verify(controller, times(1)).loadAll();
    }

    /**
     * TaskBean の update() メソッドをテストする。
     */
    @Test
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
        // controller.update が正しい ID とタイトルで呼び出されたことを確認
        verify(controller, times(1)).update(1L, updatedTitle);

        // 成功メッセージが追加されたことを確認
        ArgumentCaptor<FacesMessage> messageCaptor = ArgumentCaptor.forClass(FacesMessage.class);
        verify(facesContext).addMessage(eq(null), messageCaptor.capture());
        FacesMessage addedMessage = messageCaptor.getValue();
        assertEquals("Task " + taskId + " updated", addedMessage.getSummary());

        // refresh() が呼び出され、タスクが再読み込みされたことを確認
        verify(controller, times(1)).loadAll();
    }
}

