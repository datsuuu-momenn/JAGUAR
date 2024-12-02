package com.example.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.sql.Timestamp;

import com.example.bean.JsfBean;
import com.example.entity.JsfOperation;
import com.example.entity.User;
import com.example.repository.JsfOperationRepository;
import com.example.entity.OperationType;

@Named
@ApplicationScoped
@Transactional
public class JsfController implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Inject
    private JsfBean jsfBean;

    @Inject
    private JsfOperationRepository jsfOperationRepository;
    
    
    /**
     * Sliderの値を保存
     */
    public void saveSliderOperation(String newValue) {
        try {
            JsfOperation operation = new JsfOperation();
            operation.setOperationType(OperationType.SLIDER_CHANGE);
            operation.setOperationValue(newValue);
            operation.setOperationTime(Timestamp.valueOf(LocalDateTime.now()));
            
            System.out.println("Saving operation: " + operation);
            jsfOperationRepository.save(operation);
            System.out.println("Operation saved successfully");
        } catch (Exception e) {
            System.err.println("Error saving operation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void saveProgressOperation(String newValue) {
        try {
            JsfOperation operation = new JsfOperation();
            operation.setOperationType(OperationType.PROGRESS_CHANGE);
            operation.setOperationValue(newValue);
            operation.setOperationTime(Timestamp.valueOf(LocalDateTime.now()));
            
            jsfOperationRepository.save(operation);
        } catch (Exception e) {
            System.err.println("Error saving operation: " + e.getMessage());
            e.printStackTrace();
        }
    }   
    
    /**
     * ドロップダウンの値を保存
     */
    public void saveDropdownOperation(String newValue) {
        try {
            JsfOperation operation = new JsfOperation();
            operation.setOperationType(OperationType.DROPDOWN_CHANGE);
            operation.setOperationValue(newValue);
            operation.setOperationTime(Timestamp.valueOf(LocalDateTime.now()));
            
            jsfOperationRepository.save(operation);
        } catch (Exception e) {
            System.err.println("Error saving operation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void saveMeterOperation(String newValue) {
        try {
            JsfOperation operation = new JsfOperation();
            operation.setOperationType(OperationType.METER_CHANGE);
            operation.setOperationValue(newValue);
            operation.setOperationTime(Timestamp.valueOf(LocalDateTime.now()));
            
            jsfOperationRepository.save(operation);
        } catch (Exception e) {
            System.err.println("Error saving operation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<JsfOperation> findAllOperations() {
        return jsfOperationRepository.findAll();
    }

    public List<JsfOperation> loadAllOperations() {
        System.out.println("loadAllOperations method called in controller");
        return jsfOperationRepository.findAll();
    }
} 