package com.example.hello;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloResourceTest {

    private static Process wildflyProcess;

    @Disabled
    @BeforeAll
    static void startWildFly() throws Exception {
        // WildFly Bootable JARのパスを指定
        String jarPath = "path/to/your/bootable.jar"; 
        wildflyProcess = new ProcessBuilder("java", "-jar", jarPath).start();

        // WildFlyが起動するまで少し待機
        Thread.sleep(10000); // 必要に応じて調整
    }

    @Disabled
    @AfterAll
    static void stopWildFly() {
        if (wildflyProcess != null) {
            wildflyProcess.destroy();
        }
    }

    @Disabled
    @Test
    void testHello() throws Exception {
        URL url = new URL("http://localhost:8080/jaguar/api/hello");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        assertEquals(200, responseCode);

        // 応答の内容を取得する場合は、InputStreamを使用して読み取ります。
        // 省略していますが、必要に応じて実装してください。
    }
}


