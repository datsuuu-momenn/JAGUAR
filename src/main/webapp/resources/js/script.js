function handleInputChange(element) {
    // 获取输入的值
    const inputValue = element.value;
    console.log('Input value changed:', inputValue);
    
    // 这里可以添加其他处理逻辑
    // 例如：验证输入、更新其他元素等
}

// Selenium测试相关的其他函数
function ClickDropdownFuction() {
    console.log('Dropdown clicked');
    // 处理下拉点击事件
}

function hoverDropdownFunction() {
    console.log('Dropdown hovered');
    // 处理下拉悬停事件
}

function ClickLink1() {
    console.log('Link 1 clicked');
    // 处理链接1点击事件
}

function ClickLink2() {
    console.log('Link 2 clicked');
    // 处理链接2点击事件
}

function ClickLink3() {
    console.log('Link 3 clicked');
    // 处理链接3点击事件
}

// 使用RGB颜色值的按钮点击事件处理函数
function buttonFuction1() {
    const button = document.getElementById('myButton');
    const colors = [
        { value: 'rgb(44, 85, 48)', name: '深绿色' },
        { value: 'rgb(230, 57, 70)', name: '鲜红色' },
        { value: 'rgb(29, 53, 87)', name: '深蓝色' },
        { value: 'rgb(255, 183, 3)', name: '明黄色' },
        { value: 'rgb(155, 93, 229)', name: '紫色' }
    ];
    
    // 获取当前颜色索引
    let currentIndex = parseInt(button.getAttribute('data-color-index') || '0');
    
    // 切换到下一个颜色
    currentIndex = (currentIndex + 1) % colors.length;
    
    // 获取下一个颜色的索引
    const nextIndex = (currentIndex + 1) % colors.length;
    
    // 应用当前颜色并显示下一个颜色的名称
    button.style.color = colors[currentIndex].value;
    button.style.borderColor = colors[currentIndex].value;
    button.textContent = `点击变为${colors[nextIndex].name}`;
    
    // 保存当前颜色索引
    button.setAttribute('data-color-index', currentIndex);
    
    // 输出日志
    console.log('Current color:', colors[currentIndex].name);
    console.log('Next color will be:', colors[nextIndex].name);
}

// SVG交互函数
function handleSVGClick() {
    const rect = document.getElementById('svgRect');
    const colors = [
        'rgb(44, 85, 48)',    // 深绿色
        'rgb(230, 57, 70)',   // 红色
        'rgb(29, 53, 87)',    // 深蓝色
        'rgb(255, 183, 3)',   // 黄色
        'rgb(155, 93, 229)'   // 紫色
    ];
    
    let currentIndex = parseInt(rect.getAttribute('data-color-index') || '0');
    currentIndex = (currentIndex + 1) % colors.length;
    
    // 创建新的动画元素
    const newAnimation = document.createElementNS("http://www.w3.org/2000/svg", "animate");
    newAnimation.setAttribute("attributeName", "fill");
    newAnimation.setAttribute("from", rect.style.fill || colors[currentIndex]);
    newAnimation.setAttribute("to", colors[currentIndex]);
    newAnimation.setAttribute("dur", "0.5s");
    newAnimation.setAttribute("fill", "freeze");
    
    // 添加并立即开始动画
    rect.appendChild(newAnimation);
    newAnimation.beginElement();
    
    // 更新颜色索引
    rect.setAttribute('data-color-index', currentIndex);
    
    console.log('SVG color changed to:', colors[currentIndex]);
}

// Slider（滑块）相关的事件处理函数

/**
 * 处理滑块输入事件
 * 当用户拖动滑块时实时触发
 */
function sliderInputFuction() {
    const slider = document.getElementById('mySlider');
    console.log('Slider input value:', slider.value);
}

/**
 * 处理滑块点击事件
 * 当用户点击滑块或滑道时触发
 */
function sliderClickFuction() {
    const slider = document.getElementById('mySlider');
    console.log('Slider clicked at value:', slider.value);
}

/**
 * 处理滑块值改变事件
 * 当滑块值发生变化且用户完成交互时触发
 */
function sliderChangeFuction() {
    const slider = document.getElementById('mySlider');
    console.log('Slider value changed to:', slider.value);
}

/**
 * 处理鼠标在滑块上移动的事件
 * 实时显示当前值的浮动提示框
 */
function sliderMouseMoveFuction() {
    const slider = document.getElementById('mySlider');
    const value = slider.value;
    
    // 创建或获取用于显示值的浮动div元素
    let valueDisplay = document.getElementById('sliderValueDisplay');
    if (!valueDisplay) {
        // 如果显示元素不存在，则创建一个新的
        valueDisplay = document.createElement('div');
        valueDisplay.id = 'sliderValueDisplay';
        // 设置显示元素的样式
        valueDisplay.style.position = 'fixed';            // 使用fixed定位
        valueDisplay.style.backgroundColor = 'rgba(44, 85, 48, 0.9)';  // 使用主题深绿色
        valueDisplay.style.color = 'white';              // 白色文字
        valueDisplay.style.padding = '4px 8px';          // 内边距
        valueDisplay.style.borderRadius = '4px';         // 圆角边框
        valueDisplay.style.boxShadow = '0 2px 4px rgba(0,0,0,0.2)';  // 阴影效果
        valueDisplay.style.fontSize = '14px';            // 字体大小
        valueDisplay.style.zIndex = '1000';             // 确保显示在最上层
        valueDisplay.style.pointerEvents = 'none';      // 防止干扰鼠标事件
        // 将显示元素添加到body中
        document.body.appendChild(valueDisplay);
    }
    
    // 获取鼠标事件
    const mouseEvent = window.event;
    
    // 更新显示值的位置和内容
    valueDisplay.style.left = `${mouseEvent.clientX + 10}px`;  // 鼠标位置右侧10px
    valueDisplay.style.top = `${mouseEvent.clientY - 35}px`;   // 鼠标位置上方35px
    valueDisplay.textContent = `当前输入值: ${value}`;             // 显示当前值
    
    console.log('Mouse moved over slider at value:', value);
}

/**
 * 处理鼠标进入滑块区域的事件
 * 增加滑块的不透明度
 */
function sliderMouseOverFuction() {
    const slider = document.getElementById('mySlider');
    console.log('Mouse entered slider area');
    slider.style.opacity = '1';  // 设置完全不透明
}

/**
 * 处理鼠标离开滑块区域的事件
 * 移除值显示元素并降低滑块不透明度
 */
function sliderMouseOutFuction() {
    const slider = document.getElementById('mySlider');
    // 移除值显示元素
    const valueDisplay = document.getElementById('sliderValueDisplay');
    if (valueDisplay) {
        valueDisplay.remove();
    }
    console.log('Mouse left slider area');
    slider.style.opacity = '0.7';  // 降低不透明度
}

/**
 * 处理滑块获得焦点的事件
 * 将滑块圆点变为主题色
 */
function sliderFocusFuction() {
    const slider = document.getElementById('mySlider');
    console.log('Slider gained focus');
    
    // 添加自定义样式类来改变滑块圆点的颜色
    slider.style.accentColor = 'var(--forest-dark)';  // 使用主题深绿色
    slider.style.outline = 'none';  // 移除默认的焦点轮廓
}

/**
 * 处理滑块失去焦点的事件
 * 恢复滑块圆点的默认颜色
 */
function sliderBlurFuction() {
    const slider = document.getElementById('mySlider');
    console.log('Slider lost focus');
    
    // 移除自定义样式，恢复默认颜色
    slider.style.accentColor = '';
}

/**
 * 处理下拉菜单值改变事件
 * 根据选择的选项更新进度条的值和标签文本
 */
function selectChangeFuction1() {
    // 获取相关元素
    const select = document.getElementById('mySelect');
    const progressBar = document.getElementById('progressBar');
    const progressLabel = document.getElementById('progressLabel');
    
    // 获取选中的值（去掉百分号）
    const selectedValue = parseInt(select.value.replace('%', ''));
    
    // 更新进度条的值
    progressBar.value = selectedValue;
    
    // 更新进度条标签文本
    progressLabel.textContent = `Progress Bar: (${selectedValue}%)`;
    
    // 输出日志
    console.log('Select changed to:', selectedValue + '%');
} 