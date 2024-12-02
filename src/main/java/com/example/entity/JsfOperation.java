package com.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.sql.Timestamp;

@Entity
@Table(name = "JSF_OPERATIONS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JsfOperation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "operation_type", nullable = false)
    public OperationType operationType;
    
    @Column(name = "operation_value", nullable = false)
    public String operationValue;
    
    @Column(name = "operation_time", nullable = false, columnDefinition = "TIMESTAMP")
    public Timestamp operationTime;
    
    @PrePersist
    protected void onCreate() {
        if (operationTime == null) {
            operationTime = Timestamp.valueOf(LocalDateTime.now());
        }
    }
    
    @Override
    public String toString() {
        return "JsfOperation{" +
            "id=" + id +
            ", operationType='" + operationType + '\'' +
            ", operationValue='" + operationValue + '\'' +
            ", operationTime=" + operationTime +
            '}';
    }
} 