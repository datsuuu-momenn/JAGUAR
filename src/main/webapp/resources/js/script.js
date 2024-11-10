function handleInputChange(element) {
    // 入力された値を取得
    const inputValue = element.value;
    console.log('Input value changed:', inputValue);
    
    // ここに他の処理ロジックを追加できます
    // 例：入力の検証、他の要素の更新など
}

// Seleniumテスト関連の他の関数
function ClickDropdownFuction() {
    console.log('Dropdown clicked');
    // ドロップダウンクリックイベントの処理
}

function hoverDropdownFunction() {
    console.log('Dropdown hovered');
    // ドロップダウンホバーイベントの処理
}

function ClickLink1() {
    console.log('Link 1 clicked');
    // リンク1クリックイベントの処理
}

function ClickLink2() {
    console.log('Link 2 clicked');
    // リンク2クリックイベントの処理
}

function ClickLink3() {
    console.log('Link 3 clicked');
    // リンク3クリックイベントの処理
}

// 使用RGB色のボタンクリックイベント処理関数
function buttonFuction1() {
    const button = document.getElementById('myButton');
    const colors = [
        { value: 'rgb(44, 85, 48)', name: '深緑色' },
        { value: 'rgb(230, 57, 70)', name: '鮮红色' },
        { value: 'rgb(29, 53, 87)', name: '深蓝色' },
        { value: 'rgb(255, 183, 3)', name: '明黄色' },
        { value: 'rgb(155, 93, 229)', name: '紫色' }
    ];
    
    // 現在の色インデックスを取得
    let currentIndex = parseInt(button.getAttribute('data-color-index') || '0');
    
    // 次の色に切り替え
    currentIndex = (currentIndex + 1) % colors.length;
    
    // 次の色のインデックスを取得
    const nextIndex = (currentIndex + 1) % colors.length;
    
    // 現在の色を適用して次の色の名前を表示
    button.style.color = colors[currentIndex].value;
    button.style.borderColor = colors[currentIndex].value;
    button.textContent = `クリックで${colors[nextIndex].name}に変更`;
    
    // 現在の色インデックスを保存
    button.setAttribute('data-color-index', currentIndex);
    
    // ログ出力
    console.log('Current color:', colors[currentIndex].name);
    console.log('Next color will be:', colors[nextIndex].name);
}

// SVGインタラクション関数
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
    
    // 新しいアニメーション要素を作成
    const newAnimation = document.createElementNS("http://www.w3.org/2000/svg", "animate");
    newAnimation.setAttribute("attributeName", "fill");
    newAnimation.setAttribute("from", rect.style.fill || colors[currentIndex]);
    newAnimation.setAttribute("to", colors[currentIndex]);
    newAnimation.setAttribute("dur", "0.5s");
    newAnimation.setAttribute("fill", "freeze");
    
    // アニメーションを追加して即時開始
    rect.appendChild(newAnimation);
    newAnimation.beginElement();
    
    // カラーインデックスを更新
    rect.setAttribute('data-color-index', currentIndex);
    
    console.log('SVG color changed to:', colors[currentIndex]);
}

// Slider（スライダー）関連のイベント処理関数

/**
 * スライダーの入力イベントを処理
 * ユーザーがスライダーをドラッグする時にリアルタイムで発火
 */
function sliderInputFuction() {
    const slider = document.getElementById('mySlider');
    console.log('Slider input value:', slider.value);
}

/**
 * スライダーのクリックイベントを処理
 * ユーザーがスライダーまたはトラックをクリックした時に発火
 */
function sliderClickFuction() {
    const slider = document.getElementById('mySlider');
    console.log('Slider clicked at value:', slider.value);
}

/**
 * スライダーの値変更イベントを処理
 * スライダーの値が変更され、ユーザーの操作が完了した時に発火
 */
function sliderChangeFuction() {
    const slider = document.getElementById('mySlider');
    console.log('Slider value changed to:', slider.value);
}

/**
 * スライダー上のマウス移動イベントを処理
 * 現在値のフローティングツールチップを表示
 */
function sliderMouseMoveFuction() {
    const slider = document.getElementById('mySlider');
    const value = slider.value;
    
    // 値表示用のフローティングdiv要素を作成または取得
    let valueDisplay = document.getElementById('sliderValueDisplay');
    if (!valueDisplay) {
        // 表示要素が存在しない場合、新規作成
        valueDisplay = document.createElement('div');
        valueDisplay.id = 'sliderValueDisplay';
        // 表示要素のスタイルを設定
        valueDisplay.style.position = 'fixed';            // 固定位置
        valueDisplay.style.backgroundColor = 'rgba(44, 85, 48, 0.9)';  // テーマの深緑色
        valueDisplay.style.color = 'white';              // 白文字
        valueDisplay.style.padding = '4px 8px';          // 内部余白
        valueDisplay.style.borderRadius = '4px';         // 角丸
        valueDisplay.style.boxShadow = '0 2px 4px rgba(0,0,0,0.2)';  // 影効果
        valueDisplay.style.fontSize = '14px';            // フォントサイズ
        valueDisplay.style.zIndex = '1000';             // 最前面に表示
        valueDisplay.style.pointerEvents = 'none';      // マウスイベントを妨げない
        // 表示要素をbodyに追加
        document.body.appendChild(valueDisplay);
    }
    
    // マウスイベントを取得
    const mouseEvent = window.event;
    
    // 値表示の位置とコンテンツを更新
    valueDisplay.style.left = `${mouseEvent.clientX + 10}px`;  // マウス位置の右10px
    valueDisplay.style.top = `${mouseEvent.clientY - 35}px`;   // マウス位置の上35px
    valueDisplay.textContent = `現在値: ${value}`;             // 現在値を表示
    
    console.log('Mouse moved over slider at value:', value);
}

/**
 * スライダーにマウスが入った時のイベントを処理
 * スライダーの不透明度を上げる
 */
function sliderMouseOverFuction() {
    const slider = document.getElementById('mySlider');
    console.log('Mouse entered slider area');
    slider.style.opacity = '1';  // 完全な不透明度に設定
}

/**
 * スライダーからマウスが出た時のイベントを処理
 * 値表示要素を削除しスライダーの不透明度を下げる
 */
function sliderMouseOutFuction() {
    const slider = document.getElementById('mySlider');
    // 値表示要素を削除
    const valueDisplay = document.getElementById('sliderValueDisplay');
    if (valueDisplay) {
        valueDisplay.remove();
    }
    console.log('Mouse left slider area');
    slider.style.opacity = '0.7';  // 不透明度を下げる
}

/**
 * スライダーがフォーカスを得た時のイベントを処理
 * スライダーの丸いつまみをテーマカラーに変更
 */
function sliderFocusFuction() {
    const slider = document.getElementById('mySlider');
    console.log('Slider gained focus');
    slider.style.accentColor = 'var(--forest-dark)';  // テーマの深緑色を使用
    slider.style.outline = 'none';  // デフォルトのフォーカス枠を削除
}

/**
 * スライダーがフォーカスを失った時のイベントを処理
 * スライダーの丸いつまみをデフォルト色に戻す
 */
function sliderBlurFuction() {
    const slider = document.getElementById('mySlider');
    console.log('Slider lost focus');
    slider.style.accentColor = '';  // カスタムスタイルを削除
}

/**
 * セレクトメニューの値変更イベントを処理
 * 選択されたオプションに基づいてプログレスバーの値とラベルテキストを更新
 */
function selectChangeFuction1() {
    // 関連要素を取得
    const select = document.getElementById('mySelect');
    const progressBar = document.getElementById('progressBar');
    const progressLabel = document.getElementById('progressLabel');
    
    // 選択された値からパーセント記号を除去
    const selectedValue = parseInt(select.value.replace('%', ''));
    
    // プログレスバーの値を更新
    progressBar.value = selectedValue;
    
    // プログレスバーのラベルテキストを更新
    progressLabel.textContent = `Progress Bar: (${selectedValue}%)`;
    
    // ログ出力
    console.log('Select changed to:', selectedValue + '%');
} 