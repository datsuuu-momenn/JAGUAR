/**
 * テキスト入力の変更イベントを処理する関数
 * @param {HTMLElement} element - 入力要素
 */
function handleInputChange(element) {
    const inputValue = element.value;
    console.log('Input value changed:', inputValue);
}

/**
 * ドロップダウンボタンのクリックイベントを処理する関数
 */
function ClickDropdownFuction() {
    console.log('Dropdown clicked');
}

/**
 * ドロップダウンのホバーイベントを処理する関数
 */
function hoverDropdownFunction() {
    console.log('Dropdown hovered');
}

/**
 * リンク1のクリックイベントを処理する関数
 */
function ClickLink1() {
    console.log('Link 1 clicked');
}

/**
 * リンク2のクリックイベントを処理する関数
 */
function ClickLink2() {
    console.log('Link 2 clicked');
}

/**
 * リンク3のクリックイベントを処理する関数
 */
function ClickLink3() {
    console.log('Link 3 clicked');
}

/**
 * ボタンクリックで色を変更する関数
 * ボタン、読み取り専用入力欄、テキストの色を同時に変更
 */
function buttonFuction1() {
    // 関連する要素を取得
    const button = document.getElementById('myButton');
    const readOnlyInput = document.getElementById('readOnlyInput');
    const pText = document.getElementById('pText');
    
    // 色の定義配列（各色に値と名前を設定）
    const colors = [
        { value: 'rgb(76, 175, 80)', name: '明るい緑' },  // デフォルトの色
        { value: 'rgb(230, 57, 70)', name: '赤色' },      // 赤色に変更
        { value: 'rgb(33, 150, 243)', name: '明るい青' }, // 青色に変更
        { value: 'rgb(255, 183, 3)', name: '黄色' },      // 黄色に変更
        { value: 'rgb(155, 93, 229)', name: '紫色' }      // 紫色に変更
    ];
    
    // 現在の色のインデックスを取得（データ属性から）
    let currentIndex = parseInt(button.getAttribute('data-color-index') || '0');
    // 次の色のインデックスを計算（配列の最後まで行ったら最初に戻る）
    currentIndex = (currentIndex + 1) % colors.length;
    // その次の色のインデックスを計算（ボタンのテキスト用）
    const nextIndex = (currentIndex + 1) % colors.length;
    
    // 全ての要素の色を更新
    button.style.color = colors[currentIndex].value;
    button.style.borderColor = colors[currentIndex].value;
    readOnlyInput.style.color = colors[currentIndex].value;
    pText.style.color = colors[currentIndex].value;
    
    // ボタンのテキストを次の色の名前に更新
    button.textContent = `クリックで${colors[nextIndex].name}に変更`;
    
    // 現在の色のインデックスを保存
    button.setAttribute('data-color-index', currentIndex);
    
    // デバッグ用のログ出力
    console.log('Current color:', colors[currentIndex].name);
    console.log('Next color will be:', colors[nextIndex].name);
}

/**
 * SVGの色を変更するアニメーション関数
 * クリックごとに色が循環して変化
 */
function handleSVGClick() {
    // SVG矩形要素を取得
    const rect = document.getElementById('svgRect');
    
    // 色の定義配列
    const colors = [
        'rgb(44, 85, 48)',    // 深緑色
        'rgb(230, 57, 70)',   // 赤色
        'rgb(29, 53, 87)',    // 紺色
        'rgb(255, 183, 3)',   // 黄色
        'rgb(155, 93, 229)'   // 紫色
    ];
    
    // 現在の色のインデックスを取得（データ属性から）
    let currentIndex = parseInt(rect.getAttribute('data-color-index') || '0');
    // 次の色のインデックスを計算（配列の最後まで行ったら最初に戻る）
    currentIndex = (currentIndex + 1) % colors.length;
    
    // SVGアニメーション要素を作成
    const newAnimation = document.createElementNS("http://www.w3.org/2000/svg", "animate");
    // アニメーション属性を設定
    newAnimation.setAttribute("attributeName", "fill");           // 変更する属性は塗りつぶし色
    newAnimation.setAttribute("from", rect.style.fill || colors[currentIndex]); // 開始色
    newAnimation.setAttribute("to", colors[currentIndex]);        // 終了色
    newAnimation.setAttribute("dur", "0.5s");                    // アニメーション時間
    newAnimation.setAttribute("fill", "freeze");                 // アニメーション終了後の状態を保持
    
    // アニメーションを矩形要素に追加して開始
    rect.appendChild(newAnimation);
    newAnimation.beginElement();
    
    // 現在の色のインデックスを保存
    rect.setAttribute('data-color-index', currentIndex);
    
    // デバッグ用ログ出力
    console.log('SVG color changed to:', colors[currentIndex]);
}

/**
 * スライダーの入力イベントを処理
 * ユーザーがスライダーをドラッグする時にリアルタイムで発火
 */
function sliderInputFuction() {
    // スライダー要素を取得
    const slider = document.getElementById('mySlider');
    const value = slider.value;

    // プログレスバーを取得して値を更新
    const progressBar = document.getElementById('progressBar');
    progressBar.value = value;

    // プログレスバーのラベルを更新
    const progressLabel = document.getElementById('progressLabel');
    progressLabel.textContent = `Progress Bar: (${value}%)`;

    // デバッグ用ログ
    console.log('Slider input value:', value);
}

/**
 * スライダーのクリックイベントを処理
 * ユーザーがスライダーまたはトラックをクリックした時に発火
 */
function sliderClickFuction() {
    // スライダー要素を取得
    const slider = document.getElementById('mySlider');
    const value = slider.value;

    // プログレスバーを取得して値を更新
    const progressBar = document.getElementById('progressBar');
    progressBar.value = value;

    // プログレスバーのラベルを更新
    const progressLabel = document.getElementById('progressLabel');
    progressLabel.textContent = `Progress Bar: (${value}%)`;

    // デバッグ用ログ
    console.log('Slider clicked at value:', value);
}

/**
 * スライダーの値変更イベントを処理
 * スライダーの値が変更され、ユーザーの操作が完了した時に発火
 */
function sliderChangeFuction() {
    // スライダー要素を取得
    const slider = document.getElementById('mySlider');
    // デバッグ用ログ
    console.log('Slider value changed to:', slider.value);
}

/**
 * スライダー上のマウス移動イベントを処理
 * 現在値のフローティングツールチップを表示
 */
function sliderMouseMoveFuction() {
    // スライダー要素を取得
    const slider = document.getElementById('mySlider');
    // 現在の値を取得
    const value = slider.value;
    
    // 値表示用のdiv要素を取得または作成
    let valueDisplay = document.getElementById('sliderValueDisplay');
    if (!valueDisplay) {
        // 値表示要素が存在しない場合、新規作成
        valueDisplay = document.createElement('div');
        valueDisplay.id = 'sliderValueDisplay';
        
        // スタイルの設定
        valueDisplay.style.position = 'fixed';            // 画面に固定
        valueDisplay.style.backgroundColor = 'rgba(44, 85, 48, 0.9)';  // 半透明の深緑色
        valueDisplay.style.color = 'white';              // 文字色を白に
        valueDisplay.style.padding = '4px 8px';          // 内部の余白
        valueDisplay.style.borderRadius = '4px';         // 角を丸く
        valueDisplay.style.boxShadow = '0 2px 4px rgba(0,0,0,0.2)';  // 影を追加
        valueDisplay.style.fontSize = '14px';            // フォントサイズ
        valueDisplay.style.zIndex = '1000';             // 最前面に表示
        valueDisplay.style.pointerEvents = 'none';      // マウスイベントを無視
        
        // body要素に追加
        document.body.appendChild(valueDisplay);
    }
    
    // マウスイベントを取得
    const mouseEvent = window.event;
    
    // 表示位置を設定（マウスカーソルの右上）
    valueDisplay.style.left = `${mouseEvent.clientX + 10}px`;  // マウスの右10pxの位置
    valueDisplay.style.top = `${mouseEvent.clientY - 35}px`;   // マウスの上35pxの位置
    
    // 現在値を表示
    valueDisplay.textContent = `現在の入力値: ${value}`;
    
    // デバッグ用ログ
    console.log('Mouse moved over slider at value:', value);
}

/**
 * スライダーにマウスが重なった時のイベントを処理
 * スライダーの不透明度を上げる
 */
function sliderMouseOverFuction() {
    // スライダー要素を取得
    const slider = document.getElementById('mySlider');
    
    // デバッグ用ログ
    console.log('Mouse entered slider area');
    
    // スライダーを完全に不透明に設定
    slider.style.opacity = '1';
}

/**
 * スライダーからマウスが離れた時のイベントを処理
 * 値表示要素を削除し、スライダーの不透明度を下げる
 */
function sliderMouseOutFuction() {
    // スライダー要素を取得
    const slider = document.getElementById('mySlider');
    
    // 値表示要素を取得して削除
    const valueDisplay = document.getElementById('sliderValueDisplay');
    if (valueDisplay) {
        valueDisplay.remove();
    }
    
    // デバッグ用ログ
    console.log('Mouse left slider area');
    
    // スライダーの不透明度を下げる
    slider.style.opacity = '0.7';
}

/**
 * スライダーがフォーカスを得た時のイベントを処理
 * スライダーのつまみの色を深緑色に変更
 */
function sliderFocusFuction() {
    // スライダー要素を取得
    const slider = document.getElementById('mySlider');
    
    // デバッグ用ログ
    console.log('Slider gained focus');
    
    // つまみの色を深緑色に変更
    slider.style.accentColor = 'var(--forest-dark)';
    // デフォルトのフォーカス枠を削除
    slider.style.outline = 'none';
}

/**
 * スライダーがフォーカスを失った時のイベントを処理
 * スライダーのつまみの色をデフォルトに戻す
 */
function sliderBlurFuction() {
    // スライダー要素を取得
    const slider = document.getElementById('mySlider');
    
    // デバッグ用ログ
    console.log('Slider lost focus');
    
    // つまみの色をデフォルトに戻す
    slider.style.accentColor = '';
}

/**
 * セレクトメニューの値変更イベントを処理
 * 選択された値に基づいてメーターの値とラベルを更新
 */
function selectChangeFuction1() {
    // 関連要素を取得
    const select = document.getElementById('mySelect');
    const meterBar = document.getElementById('meterBar');
    const meterLabel = document.getElementById('meterLabel');
    
    // パーセント記号を除去して数値に変換
    const selectedValue = parseInt(select.value.replace('%', ''));
    
    // メーターの値を0-1の範囲に変換して設定
    meterBar.value = selectedValue / 100;
    
    // メーターのラベルを更新
    meterLabel.textContent = `HTML Meter: (${selectedValue}%)`;
    
    // デバッグ用ログ
    console.log('Select changed to:', selectedValue + '%');
} 