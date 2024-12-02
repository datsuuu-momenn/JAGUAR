
INSERT INTO tasks (title) VALUES ('This is the task-1');
INSERT INTO tasks (title) VALUES ('This is the task-2');
INSERT INTO tasks (title) VALUES ('This is the task-3');
INSERT INTO tasks (title) VALUES ('This is the task-4');
INSERT INTO tasks (title) VALUES ('This is the task-5');


INSERT INTO users (username, password) VALUES ('admin', 'admin123');

-- スキルデータの初期化
INSERT INTO skills (skill_id, skill_name, damage, accuracy, critical_rate, skill_type, description) VALUES (1, 'リーフブレード', 70, 95, 10, 'GRASS', '');
INSERT INTO skills (skill_id, skill_name, damage, accuracy, critical_rate, skill_type, description) VALUES (2, 'ブレイブバード', 120, 85, 15, 'FLYING', '');
INSERT INTO skills (skill_id, skill_name, damage, accuracy, critical_rate, skill_type, description) VALUES (3, 'はっぱカッター', 55, 100, 5, 'GRASS', '');
INSERT INTO skills (skill_id, skill_name, damage, accuracy, critical_rate, skill_type, description) VALUES (4, 'マヒルノツメ', 65, 95, 10, 'NORMAL', '');


-- 初期データの挿入（必要な場合）
INSERT INTO JSF_OPERATIONS (operation_time, operation_type, operation_value) VALUES (CURRENT_TIMESTAMP, 'DROPDOWN_CHANGE', '50%');
INSERT INTO JSF_OPERATIONS (operation_time, operation_type, operation_value) VALUES (CURRENT_TIMESTAMP, 'SLIDER_CHANGE', '75');


-- 插入咖啡推荐数据
INSERT INTO coffee_recommendation (coffee_type, recommended_bean, bean_description) VALUES ('Latte', 'カフェ ベロナ（Caffè Verona）', '黒ココアのような濃厚な風味が特徴で、ミルクと相性抜群。ラテのクリーミーさと調和し、甘く滑らかな味わいを引き立てます。');  
INSERT INTO coffee_recommendation (coffee_type, recommended_bean, bean_description) VALUES ('Cappuccino', 'エスプレッソ ロースト（Espresso Roast）', '焦がしキャラメルの甘みと濃厚な風味が特徴。フォームミルクと合わせると、コーヒーのアロマが際立ちます。');
INSERT INTO coffee_recommendation (coffee_type, recommended_bean, bean_description) VALUES ('Americano', 'ケニア（Kenya）', 'フルーティーで酸味が強いケニア豆は、シンプルでクリアな味わいのアメリカーノにぴったりです。');
INSERT INTO coffee_recommendation (coffee_type, recommended_bean, bean_description) VALUES ('Mocha', 'エスプレッソ ロースト（Espresso Roast）', '濃厚で香ばしいコーヒーの風味がチョコレートの甘さを引き立て、バランスの良いモカを楽しめます。');
INSERT INTO coffee_recommendation (coffee_type, recommended_bean, bean_description) VALUES ('Caramel_Macchiato', 'パイクプレイス ロースト（Pike Place Roast）', 'バランスの良い風味で、キャラメルの甘さを邪魔せず、やさしいコーヒー感を提供します。');
INSERT INTO coffee_recommendation (coffee_type, recommended_bean, bean_description) VALUES ('Frappuccino', 'ブロンド ロースト（Blonde Roast）', '軽やかで飲みやすいフラペチーノにぴったりの優しい味わいのコーヒー豆です。');
INSERT INTO coffee_recommendation (coffee_type, recommended_bean, bean_description) VALUES ('Cold_Brew', 'スマトラ（Sumatra）', '低酸でコクのあるスマトラ豆は、長時間抽出するコールドブリューに適しており、スパイシーで濃厚な風味を楽しめます。');
INSERT INTO coffee_recommendation (coffee_type, recommended_bean, bean_description) VALUES ('Flat_White', 'エスプレッソ ロースト（Espresso Roast）', '濃厚なエスプレッソに微細なスチームミルクが加わるフラットホワイトには、深い焙煎の豆がベストです。');
INSERT INTO coffee_recommendation (coffee_type, recommended_bean, bean_description) VALUES ('Brewed_Coffee', 'グアテマラ アンティグア（Guatemala Antigua）', 'チョコレートのような口当たりとスパイスの風味があり、シンプルなドリップコーヒーとして楽しめます。');
INSERT INTO coffee_recommendation (coffee_type, recommended_bean, bean_description) VALUES ('Matcha_Latte', 'エチオピア（Ethiopia）', 'フローラルでやわらかな風味が抹茶のほろ苦さを引き立て、絶妙なバランスを保ちます。');
