package com.example.bean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import jakarta.faces.event.ValueChangeEvent;
import jakarta.faces.component.UIInput;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

import com.example.entity.JsfOperation;
import com.example.controller.JsfController;
import com.example.bean.JsfBean;



@ExtendWith(MockitoExtension.class)
class JsfBeanTest {
    
    // @Mock
    // private JsfController jsfController;
    
    // @Mock
    // private ValueChangeEvent event;
    
    // @InjectMocks
    // private JsfBean jsfBean;
    
    // private UIInput component;
    
    // @BeforeEach
    // void setUp() {
    //     component = new UIInput();
    //     when(event.getComponent()).thenReturn(component);
    // }
    
    // @Test
    // void testHandleSliderChange() {
    //     component.setValue(75);
    //     when(event.getNewValue()).thenReturn(75);
    //     when(event.getOldValue()).thenReturn(50);
        
    //     jsfBean.handleSliderChange(event);
        
    //     verify(jsfController).saveSliderOperation();
    // }
    
    // @Test
    // void testHandleMeterChange() {
    //     component.setValue("50%");
    //     when(event.getNewValue()).thenReturn("50%");
    //     when(event.getOldValue()).thenReturn("25%");
        
    //     jsfBean.handleMeterChange(event);
        
    //     verify(jsfController).saveDropdownOperation();
    // }
    
    // @Test
    // void testGetOperationHistory() {
    //     List<JsfOperation> expectedHistory = Arrays.asList(
    //         new JsfOperation(), new JsfOperation()
    //     );
    //     when(jsfController.findAllOperations()).thenReturn(expectedHistory);
        
    //     List<JsfOperation> actualHistory = jsfBean.getOperationHistory();
        
    //     assertEquals(expectedHistory, actualHistory);
    //     verify(jsfController).findAllOperations();
    // }
    
    // @Test
    // void testGetterAndSetter() {
    //     jsfBean.setSliderValue(75);
    //     assertEquals(75, jsfBean.getSliderValue());
        
    //     jsfBean.setDropdownValue("50%");
    //     assertEquals("50%", jsfBean.getDropdownValue());
    // }
} 