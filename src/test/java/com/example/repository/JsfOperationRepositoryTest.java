package com.example.repository;

import com.example.entity.JsfOperation;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JsfOperationRepositoryTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private JsfOperationRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_WhenExists_ShouldReturnOperation() {
        // 准备测试数据
        JsfOperation operation = new JsfOperation();
        operation.setId(1L);
        when(entityManager.find(JsfOperation.class, 1L)).thenReturn(operation);

        // 执行测试
        Optional<JsfOperation> result = repository.findById(1L);

        // 验证结果
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(entityManager).find(JsfOperation.class, 1L);
    }

    @Test
    void findAll_ShouldReturnAllOperations() {
        // 准备测试数据
        List<JsfOperation> operations = Arrays.asList(
            new JsfOperation(),
            new JsfOperation()
        );
        
        var query = mock(jakarta.persistence.TypedQuery.class);
        when(entityManager.createQuery("SELECT o FROM JsfOperation o", JsfOperation.class))
            .thenReturn(query);
        when(query.getResultList()).thenReturn(operations);

        // 执行测试
        List<JsfOperation> result = repository.findAll();

        // 验证结果
        assertEquals(2, result.size());
        verify(entityManager).createQuery("SELECT o FROM JsfOperation o", JsfOperation.class);
    }

    @Test
    void save_ShouldPersistOperation() {
        // 准备测试数据
        JsfOperation operation = new JsfOperation();

        // 执行测试
        repository.save(operation);

        // 验证结果
        verify(entityManager).persist(operation);
    }

    @Test
    void update_WhenOperationExists_ShouldMergeOperation() {
        // 准备测试数据
        JsfOperation operation = new JsfOperation();
        operation.setId(1L);
        when(entityManager.find(JsfOperation.class, 1L)).thenReturn(operation);
        when(entityManager.merge(operation)).thenReturn(operation);

        // 执行测试
        JsfOperation result = repository.update(operation);

        // 验证结果
        assertNotNull(result);
        verify(entityManager).merge(operation);
    }

    @Test
    void update_WhenOperationNotExists_ShouldThrowException() {
        // 准备测试数据
        JsfOperation operation = new JsfOperation();
        operation.setId(1L);
        when(entityManager.find(JsfOperation.class, 1L)).thenReturn(null);

        // 执行测试并验证异常
        assertThrows(IllegalArgumentException.class, () -> repository.update(operation));
    }

    @Test
    void delete_WhenOperationExists_ShouldRemoveOperation() {
        // 准备测试数据
        JsfOperation operation = new JsfOperation();
        operation.setId(1L);
        when(entityManager.find(JsfOperation.class, 1L)).thenReturn(operation);

        // 执行测试
        repository.delete(1L);

        // 验证结果
        verify(entityManager).remove(operation);
    }

    @Test
    void delete_WhenOperationNotExists_ShouldThrowException() {
        // 准备测试数据
        when(entityManager.find(JsfOperation.class, 1L)).thenReturn(null);

        // 执行测试并验证异常
        assertThrows(IllegalArgumentException.class, () -> repository.delete(1L));
    }

    @Test
    void findByType_ShouldReturnOperationsOfType() {
        // 准备测试数据
        String type = "TEST_TYPE";
        List<JsfOperation> operations = Arrays.asList(new JsfOperation(), new JsfOperation());
        
        var query = mock(jakarta.persistence.TypedQuery.class);
        when(entityManager.createQuery("SELECT o FROM JsfOperation o WHERE o.type = :type", JsfOperation.class))
            .thenReturn(query);
        when(query.setParameter("type", type)).thenReturn(query);
        when(query.getResultList()).thenReturn(operations);

        // 执行测试
        List<JsfOperation> result = repository.findByType(type);

        // 验证结果
        assertEquals(2, result.size());
        verify(entityManager).createQuery("SELECT o FROM JsfOperation o WHERE o.type = :type", JsfOperation.class);
    }
}