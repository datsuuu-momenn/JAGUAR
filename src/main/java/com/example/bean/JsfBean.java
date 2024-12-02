package com.example.bean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.inject.Inject;
import java.io.Serializable;
import java.io.IOException;
import java.util.List;
import com.example.controller.JsfController;
import com.example.entity.JsfOperation;


import lombok.Getter;
import lombok.Setter;
import jakarta.faces.event.ValueChangeEvent;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.ExternalContext;

@Named
@SessionScoped
public class JsfBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Inject
    private JsfController jsfController;


    private List<JsfOperation> operationHistory;
    
    @Getter
    @Setter
    private int sliderValue = 50;

    @Getter 
    @Setter
    private int progressValue = 50;

    @Getter
    @Setter
    private String dropdownValue = "50%";

    @Getter
    @Setter
    private String selectedRadio = "1";

    @Getter
    @Setter
    private String[] selectedCheckboxes;

    @Getter
    @Setter
    private String prefilledText = "モクロー.....";

    @Getter
    @Setter
    private double meterValue = 0.5;

    public void handleSliderChange(AjaxBehaviorEvent event) {
        try {
            // 从 AjaxBehaviorEvent 中获取值
            UIInput source = (UIInput) event.getSource();
            Integer newValue = (Integer) source.getValue();
            this.sliderValue = newValue;
            jsfController.saveSliderOperation(String.valueOf(this.sliderValue));

            this.progressValue = newValue;
            jsfController.saveProgressOperation(String.valueOf(this.progressValue));
            refresh();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "エラーが発生しました", "スライダーの値の保存に失敗しました。"));
        }
    }

 

    public void handleMeterChange(ValueChangeEvent event) {
        String newValue = event.getNewValue().toString();
        System.out.println("New value selected: " + newValue);
        this.dropdownValue = newValue;
        jsfController.saveDropdownOperation(newValue);
 
        // 移除百分比符号并解析为数字
        String numberOnly = newValue.replaceAll("[^0-9]", "");
        try {
            this.meterValue = Double.parseDouble(numberOnly) / 100.0;
            System.out.println("Meter value set to: " + this.meterValue);
            jsfController.saveMeterOperation(String.valueOf(this.meterValue));
            refresh();
        } catch (NumberFormatException e) {
            System.err.println("Error parsing value: " + newValue);
            this.meterValue = 0.5; // 设置默认值
        }
        
    }



    public void handleRadioChange() {
        System.out.println("Selected radio: " + selectedRadio);
    }

    public void handleCheckboxChange() {
        if (selectedCheckboxes != null) {
            for (String value : selectedCheckboxes) {
                System.out.println("Selected checkbox: " + value);
            }
        }
    }



    public List<JsfOperation> getOperationHistory() {
        return jsfController.findAllOperations();
    }

    // refreshメソッド：DBからすべての操作履歴を取得
    public void refresh() {
        System.out.println("refresh method called");
        this.operationHistory = jsfController.loadAllOperations();
    }

    /**
    //  * チェックボックスの変更を処理し、サーブレットにリダイレクト
    //  */
    // public String submitCheckboxChange() {
    //     try {
    //         // 現在のコンテキストを取得
    //         FacesContext context = FacesContext.getCurrentInstance();
    //         ExternalContext externalContext = context.getExternalContext();
            
    //         // チェックボックスの値をリクエストパラメータとして設定
    //         String[] selectedValues = this.selectedCheckboxes;
            
    //         // サーブレットにリダイレクト
    //         String contextPath = externalContext.getRequestContextPath();
    //         externalContext.redirect(contextPath + "/storePreferences?checkboxGroup=" + 
    //             String.join(",", selectedValues));
            
    //         return null; // リダイレクトするのでnullを返す
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //         FacesContext.getCurrentInstance().addMessage(null,
    //             new FacesMessage(FacesMessage.SEVERITY_ERROR, 
    //                 "エラーが発生しました", "選択の保存に失敗しました。"));
    //         return null;
    //     }
    // }

} 