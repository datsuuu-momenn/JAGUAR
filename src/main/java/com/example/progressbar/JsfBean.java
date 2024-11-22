package com.example.progressbar;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.Random;

@Named
@SessionScoped
public class JsfBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private final Random random = new Random();
    
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

    public void handleDropdownChange() {
        // ドロップダウンの値から%を除去して数値に変換
        String numericValue = dropdownValue.replace("%", "");
        this.progressValue = Integer.parseInt(numericValue);
        System.out.println("progressValue: " + this.progressValue);
        System.out.println("dropdownValue: " + dropdownValue);

        this.sliderValue = this.progressValue;
    }

    public void handleMeterChange() {
        System.out.println("meterValue: " + this.meterValue);
        System.out.println("get meterBar same to dropdownValue: " + Double.parseDouble(dropdownValue) / 100.0);
        this.meterValue = Double.parseDouble(dropdownValue.replace("%", "")) / 100.0;
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
} 