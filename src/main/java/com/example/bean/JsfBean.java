package com.example.bean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import jakarta.faces.event.ValueChangeEvent;

@Named
@SessionScoped
public class JsfBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    
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

    public void handleSliderChange() {
        this.progressValue = this.sliderValue;
    }

 

    public void handleMeterChange(ValueChangeEvent event) {
        String newValue = event.getNewValue().toString();
        System.out.println("New value selected: " + newValue);
        this.dropdownValue = newValue;

        // 移除百分比符号并解析为数字
        String numberOnly = newValue.replaceAll("[^0-9]", "");
        try {
            this.meterValue = Double.parseDouble(numberOnly) / 100.0;
            System.out.println("Meter value set to: " + this.meterValue);
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

    public String getDynamicSrc() {
        return "data:text/html,&lt;body style='background-color:%23F2F6F8;'&gt;&lt;h4&gt;iFrame Text&lt;/h4&gt;&lt;/body&gt;";
    }
} 