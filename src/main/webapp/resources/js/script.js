function handleInputChange(element) {
    const inputValue = element.value;
    console.log('Input value changed:', inputValue);
}

function ClickDropdownFuction() {
    console.log('Dropdown clicked');
}

function hoverDropdownFunction() {
    console.log('Dropdown hovered');
}

function ClickLink1() {
    console.log('Link 1 clicked');
}

function ClickLink2() {
    console.log('Link 2 clicked');
}

function ClickLink3() {
    console.log('Link 3 clicked');
}

function buttonFuction1() {
    const button = document.getElementById('myButton');
    const readOnlyInput = document.getElementById('readOnlyInput');
    const pText = document.getElementById('pText');
    
    const colors = [
        { value: 'rgb(76, 175, 80)', name: '明るい緑' },
        { value: 'rgb(230, 57, 70)', name: '赤色' },   
        { value: 'rgb(33, 150, 243)', name: '明るい青' },
        { value: 'rgb(255, 183, 3)', name: '黄色' },   
        { value: 'rgb(155, 93, 229)', name: '紫色' }   
    ];
    
    let currentIndex = parseInt(button.getAttribute('data-color-index') || '0');
    currentIndex = (currentIndex + 1) % colors.length;
    const nextIndex = (currentIndex + 1) % colors.length;
    
    button.style.color = colors[currentIndex].value;
    button.style.borderColor = colors[currentIndex].value;
    readOnlyInput.style.color = colors[currentIndex].value;
    pText.style.color = colors[currentIndex].value;
    
    button.textContent = `クリックで${colors[nextIndex].name}に変更`;
    
    button.setAttribute('data-color-index', currentIndex);
    
    console.log('Current color:', colors[currentIndex].name);
    console.log('Next color will be:', colors[nextIndex].name);
}

function handleSVGClick() {
    const rect = document.getElementById('svgRect');
    const colors = [
        'rgb(44, 85, 48)',    // 深緑色
        'rgb(230, 57, 70)',   // 赤色
        'rgb(29, 53, 87)',    // 紺色
        'rgb(255, 183, 3)',   // 黄色
        'rgb(155, 93, 229)'   // 紫色
    ];
    
    let currentIndex = parseInt(rect.getAttribute('data-color-index') || '0');
    currentIndex = (currentIndex + 1) % colors.length;
    
    const newAnimation = document.createElementNS("http://www.w3.org/2000/svg", "animate");
    newAnimation.setAttribute("attributeName", "fill");
    newAnimation.setAttribute("from", rect.style.fill || colors[currentIndex]);
    newAnimation.setAttribute("to", colors[currentIndex]);
    newAnimation.setAttribute("dur", "0.5s");
    newAnimation.setAttribute("fill", "freeze");
    
    rect.appendChild(newAnimation);
    newAnimation.beginElement();
    
    rect.setAttribute('data-color-index', currentIndex);
    
    console.log('SVG color changed to:', colors[currentIndex]);
}

function sliderInputFuction() {
    const slider = document.getElementById('mySlider');
    const value = slider.value;

    const progressBar = document.getElementById('progressBar');
    progressBar.value = value;

    const progressLabel = document.getElementById('progressLabel');
    progressLabel.textContent = `Progress Bar: (${value}%)`;

    console.log('Slider input value:', value);
}

function sliderClickFuction() {
    const slider = document.getElementById('mySlider');
    const value = slider.value;

    const progressBar = document.getElementById('progressBar');
    progressBar.value = value;

    const progressLabel = document.getElementById('progressLabel');
    progressLabel.textContent = `Progress Bar: (${value}%)`;

    console.log('Slider clicked at value:', value);
}

function sliderChangeFuction() {
    const slider = document.getElementById('mySlider');
    console.log('Slider value changed to:', slider.value);
}

function sliderMouseMoveFuction() {
    const slider = document.getElementById('mySlider');
    const value = slider.value;
    
    let valueDisplay = document.getElementById('sliderValueDisplay');
    if (!valueDisplay) {
        valueDisplay = document.createElement('div');
        valueDisplay.id = 'sliderValueDisplay';
        valueDisplay.style.position = 'fixed';
        valueDisplay.style.backgroundColor = 'rgba(44, 85, 48, 0.9)';
        valueDisplay.style.color = 'white';
        valueDisplay.style.padding = '4px 8px';
        valueDisplay.style.borderRadius = '4px';
        valueDisplay.style.boxShadow = '0 2px 4px rgba(0,0,0,0.2)';
        valueDisplay.style.fontSize = '14px';
        valueDisplay.style.zIndex = '1000';
        valueDisplay.style.pointerEvents = 'none';
        document.body.appendChild(valueDisplay);
    }
    
    const mouseEvent = window.event;
    
    valueDisplay.style.left = `${mouseEvent.clientX + 10}px`;
    valueDisplay.style.top = `${mouseEvent.clientY - 35}px`;
    valueDisplay.textContent = `現在の入力値: ${value}`;
    
    console.log('Mouse moved over slider at value:', value);
}

function sliderMouseOverFuction() {
    const slider = document.getElementById('mySlider');
    console.log('Mouse entered slider area');
    slider.style.opacity = '1';
}

function sliderMouseOutFuction() {
    const slider = document.getElementById('mySlider');
    const valueDisplay = document.getElementById('sliderValueDisplay');
    if (valueDisplay) {
        valueDisplay.remove();
    }
    console.log('Mouse left slider area');
    slider.style.opacity = '0.7';
}

function sliderFocusFuction() {
    const slider = document.getElementById('mySlider');
    console.log('Slider gained focus');
    
    slider.style.accentColor = 'var(--forest-dark)';
    slider.style.outline = 'none';
}

function sliderBlurFuction() {
    const slider = document.getElementById('mySlider');
    console.log('Slider lost focus');
    
    slider.style.accentColor = '';
}

function selectChangeFuction1() {
    const select = document.getElementById('mySelect');
    const meterBar = document.getElementById('meterBar');
    const meterLabel = document.getElementById('meterLabel');
    
    const selectedValue = parseInt(select.value.replace('%', ''));
    
    meterBar.value = selectedValue / 100;
    
    meterLabel.textContent = `HTML Meter: (${selectedValue}%)`;
    
    console.log('Select changed to:', selectedValue + '%');
} 