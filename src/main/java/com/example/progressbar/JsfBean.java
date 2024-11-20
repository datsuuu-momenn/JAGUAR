package com.example.progressbar;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class JsfBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private int sliderValue = 50;
    private int progressValue = 50;
    private String dropdownValue = "50%";

    public void handleSliderChange() {
        this.progressValue = this.sliderValue;
    }

    public void handleDropdownChange() {
        // ドロップダウンの値から%を除去して数値に変換
        String numericValue = dropdownValue.replace("%", "");
        this.progressValue = Integer.parseInt(numericValue);
        this.sliderValue = this.progressValue;
    }

    // Getters and Setters
    public int getSliderValue() {
        return sliderValue;
    }

    public void setSliderValue(int sliderValue) {
        this.sliderValue = sliderValue;
    }

    public int getProgressValue() {
        return progressValue;
    }

    public void setProgressValue(int progressValue) {
        this.progressValue = progressValue;
    }

    public String getDropdownValue() {
        return dropdownValue;
    }

    public void setDropdownValue(String dropdownValue) {
        this.dropdownValue = dropdownValue;
    }
} 