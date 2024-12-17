package com.example.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.quality.Strictness;
import org.mockito.junit.jupiter.MockitoSettings;
import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import com.example.bean.JsfBean;
import com.example.entity.JsfOperation;
import com.example.repository.JsfOperationRepository;
import com.example.entity.OperationType;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class JsfControllerTest {

    @Mock
    private JsfOperationRepository jsfOperationRepository;

    @Mock
    private JsfBean jsfBean;

    @InjectMocks
    private JsfController jsfController;

    @BeforeEach
    void setUp() {
        // 如果没有通用的 mock 设置，可以删除整个方法
    }

    @Test
    void testSaveSliderOperation() {
        // 実行
        jsfController.saveSliderOperation("75");

        // 検証
        verify(jsfOperationRepository).save(argThat(operation -> {
            return operation.getOperationType() == OperationType.SLIDER_CHANGE
                && operation.getOperationValue().equals("75")
                && operation.getOperationTime() != null;
        }));
    }

    @Test
    void testSaveDropdownOperation() {
        // 実行
        jsfController.saveDropdownOperation("50%");

        // 検証
        verify(jsfOperationRepository).save(argThat(operation -> {
            return operation.getOperationType() == OperationType.DROPDOWN_CHANGE
                && operation.getOperationValue().equals("50%")
                && operation.getOperationTime() != null;
        }));
    }

    @Test
    void testFindAllOperations() {
        // テストデータの準備
        List<JsfOperation> expectedOperations = Arrays.asList(
            createTestOperation(OperationType.SLIDER_CHANGE, "75"),
            createTestOperation(OperationType.DROPDOWN_CHANGE, "50%")
        );

        // モックの設定
        when(jsfOperationRepository.findAll()).thenReturn(expectedOperations);

        // 実行
        List<JsfOperation> actualOperations = jsfController.findAllOperations();

        // 検証
        assertNotNull(actualOperations);
        assertEquals(2, actualOperations.size());
        assertEquals(OperationType.SLIDER_CHANGE, actualOperations.get(0).getOperationType());
        assertEquals("75", actualOperations.get(0).getOperationValue());
        assertEquals(OperationType.DROPDOWN_CHANGE, actualOperations.get(1).getOperationType());
        assertEquals("50%", actualOperations.get(1).getOperationValue());
    }

    @Test
    void testLoadAllOperations() {
        // テストデータの準備
        List<JsfOperation> expectedOperations = Arrays.asList(
            createTestOperation(OperationType.SLIDER_CHANGE, "75"),
            createTestOperation(OperationType.DROPDOWN_CHANGE, "50%")
        );

        // モックの設定
        when(jsfOperationRepository.findAll()).thenReturn(expectedOperations);

        // 実行
        List<JsfOperation> actualOperations = jsfController.loadAllOperations();

        // 検証
        assertNotNull(actualOperations);
        assertEquals(2, actualOperations.size());
        verify(jsfOperationRepository).findAll();
    }

    private JsfOperation createTestOperation(OperationType type, String value) {
        JsfOperation operation = new JsfOperation();
        operation.setOperationType(type);
        operation.setOperationValue(value);
        operation.setOperationTime(Timestamp.valueOf(LocalDateTime.now()));
        return operation;
    }
} 