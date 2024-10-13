package com.example.task;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.faces.context.FacesContext;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, DBUnitExtension.class})
@DataSet("datasets/tasks.yml") // データセットファイルのパス
class TaskBeanTest {

    @Mock
    private TaskController controller;

    @Mock
    private FacesContext facesContext;

    @InjectMocks
    private TaskBean taskBean;

    @BeforeEach
    public void setUp() {
        // FacesContext のモックを設定
        FacesContextSetter.setFacesContext(facesContext);
    }

    @Test
    void testPostConstruct() {
        // テスト前に setUp が実行され、refresh() が呼ばれていることを確認
        List<Task> mockTasks = Arrays.asList(
                new Task() {{ setId(1L); setTitle("Task 1"); }},
                new Task() {{ setId(2L); setTitle("Task 2"); }}
        );
        when(controller.loadAll()).thenReturn(mockTasks);

        taskBean.postConstruct();

        assertEquals(2, taskBean.getAllTasks().size());
        verify(controller, times(1)).loadAll();
    }

    @Test
    void testAddSuccess() {
        String title = "New Task";
        taskBean.setTitle(title);

        doNothing().when(controller).add(title);
        List<Task> mockTasks = Arrays.asList(
                new Task() {{ setId(1L); setTitle("Task 1"); }},
                new Task() {{ setId(2L); setTitle("Task 2"); }},
                new Task() {{ setId(3L); setTitle(title); }}
        );
        when(controller.loadAll()).thenReturn(mockTasks);

        taskBean.add();

        verify(controller, times(1)).add(title);
        verify(controller, times(1)).loadAll();
        verify(facesContext, times(1)).addMessage(
                eq(null),
                argThat(message -> message.getSummary().equals("Task with title " + title + " created"))
        );

        assertEquals(3, taskBean.getAllTasks().size());
    }

    @Test
    void testAddFailure() {
        String title = "New Task";
        taskBean.setTitle(title);
        String errorMessage = "Database error";

        doThrow(new RuntimeException(errorMessage)).when(controller).add(title);

        taskBean.add();

        verify(controller, times(1)).add(title);
        verify(controller, never()).loadAll();
        verify(facesContext, times(1)).addMessage(
                eq(null),
                argThat(message -> message.getSummary().equals("Error adding a new Task. " + errorMessage))
        );
    }

    @Test
    void testDeleteSuccess() {
        Long id = 1L;
        taskBean.setId(id.toString());

        doNothing().when(controller).delete(id);
        List<Task> mockTasks = Arrays.asList(
                new Task() {{ setId(2L); setTitle("Task 2"); }}
        );
        when(controller.loadAll()).thenReturn(mockTasks);

        taskBean.delete();

        verify(controller, times(1)).delete(id);
        verify(controller, times(1)).loadAll();
        verify(facesContext, times(1)).addMessage(
                eq(null),
                argThat(message -> message.getSummary().equals("Task " + id + " deleted"))
        );

        assertEquals(1, taskBean.getAllTasks().size());
    }

    @Test
    void testDeleteFailure() {
        Long id = 1L;
        taskBean.setId(id.toString());
        String errorMessage = "Task not found";

        doThrow(new RuntimeException(errorMessage)).when(controller).delete(id);

        taskBean.delete();

        verify(controller, times(1)).delete(id);
        verify(controller, never()).loadAll();
        verify(facesContext, times(1)).addMessage(
                eq(null),
                argThat(message -> message.getSummary().equals("Error deleting the Task by Id. " + errorMessage))
        );
    }

    @Test
    void testUpdateSuccess() {
        Long id = 1L;
        String newTitle = "Updated Task";
        taskBean.setId(id.toString());
        taskBean.setTitle(newTitle);

        doNothing().when(controller).update(id, newTitle);
        List<Task> mockTasks = Arrays.asList(
                new Task() {{ setId(1L); setTitle(newTitle); }},
                new Task() {{ setId(2L); setTitle("Task 2"); }}
        );
        when(controller.loadAll()).thenReturn(mockTasks);

        taskBean.update();

        verify(controller, times(1)).update(id, newTitle);
        verify(controller, times(1)).loadAll();
        verify(facesContext, times(1)).addMessage(
                eq(null),
                argThat(message -> message.getSummary().equals("Task " + id + " updated"))
        );

        assertEquals(newTitle, taskBean.getAllTasks().get(0).getTitle());
    }

    @Test
    void testUpdateFailure() {
        Long id = 1L;
        String newTitle = "Updated Task";
        taskBean.setId(id.toString());
        taskBean.setTitle(newTitle);
        String errorMessage = "Update failed";

        doThrow(new RuntimeException(errorMessage)).when(controller).update(id, newTitle);

        taskBean.update();

        verify(controller, times(1)).update(id, newTitle);
        verify(controller, never()).loadAll();
        verify(facesContext, times(1)).addMessage(
                eq(null),
                argThat(message -> message.getSummary().equals("Error updating the Task by Id. " + errorMessage))
        );
    }

    @Test
    void testGetAllTasks() {
        List<Task> mockTasks = Arrays.asList(
                new Task() {{ setId(1L); setTitle("Task 1"); }},
                new Task() {{ setId(2L); setTitle("Task 2"); }}
        );
        when(controller.loadAll()).thenReturn(mockTasks);

        taskBean.refresh();

        List<Task> tasks = taskBean.getAllTasks();
        assertNotNull(tasks);
        assertEquals(2, tasks.size());
        assertEquals("Task 1", tasks.get(0).getTitle());
        assertEquals("Task 2", tasks.get(1).getTitle());
    }

    // Utilityクラスを使用してFacesContextをモックに設定
    static class FacesContextSetter {
        static void setFacesContext(FacesContext context) {
            // Reflectionを使用してFacesContextのインスタンスを設定
            try {
                java.lang.reflect.Field field = FacesContext.class.getDeclaredField("currentInstance");
                field.setAccessible(true);
                field.set(null, context);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}