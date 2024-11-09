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