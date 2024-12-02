package com.example.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.example.bean.JsfBean;
import com.example.entity.JsfOperation;

@ExtendWith(MockitoExtension.class)
public class JsfControllerTest {

    // @Mock
    // private EntityManager entityManager;

    // @Mock
    // private JsfBean jsfBean;

    // @InjectMocks
    // private JsfController jsfController;

    // @Mock
    // private TypedQuery<JsfOperation> typedQuery;


    // @BeforeEach
    // void setUp() {
    //     when(jsfBean.getSliderValue()).thenReturn(75);
    //     when(jsfBean.getDropdownValue()).thenReturn("50%");
    // }

    // @Test
    // void testSaveSliderOperation() {
    //     jsfController.saveSliderOperation();

    //     verify(entityManager).persist(any(JsfOperation.class));
    //     verify(entityManager).flush();
    // }

    // @Test
    // void testSaveDropdownOperation() {
    //     jsfController.saveDropdownOperation();

    //     verify(entityManager).persist(any(JsfOperation.class));
    //     verify(entityManager).flush();
    // }

    // @Test
    // void testFindAllOperations() {
    //     List<JsfOperation> expectedOperations = Arrays.asList(
    //         createTestOperation("SLIDER_CHANGE", "75"),
    //         createTestOperation("DROPDOWN_CHANGE", "50%")
    //     );

    //     when(entityManager.createQuery("SELECT o FROM JsfOperation o", JsfOperation.class))
    //         .thenReturn(typedQuery);
    //     when(typedQuery.getResultList()).thenReturn(expectedOperations);

    //     List<JsfOperation> actualOperations = jsfController.findAllOperations();

    //     assertNotNull(actualOperations);
    //     assertEquals(2, actualOperations.size());
    //     assertEquals("SLIDER_CHANGE", actualOperations.get(0).getOperationType());
    //     assertEquals("75", actualOperations.get(0).getValue());
    //     assertEquals("DROPDOWN_CHANGE", actualOperations.get(1).getOperationType());
    //     assertEquals("50%", actualOperations.get(1).getValue());
    // }

    // private JsfOperation createTestOperation(String operationType, String value) {
    //     JsfOperation operation = new JsfOperation();
    //     operation.setOperationType(operationType);
    //     operation.setValue(value);
    //     operation.setOperationTime(LocalDateTime.now());
    //     return operation;
    // }
} 