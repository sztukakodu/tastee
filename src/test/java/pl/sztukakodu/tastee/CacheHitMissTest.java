package pl.sztukakodu.tastee;

import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;

class CacheHitMissTest {
    @SneakyThrows
    public static void main(String[] args) {
        // 21-269
        for (int i = 0; i < 4; i++) {
            makeCalls();
            Thread.sleep(1_000);
        }
    }

    @SneakyThrows
    private static void makeCalls() {
        OkHttpClient client = new OkHttpClient();
        for (int x = 21; x <= 269; x++) {
            if (x % 4 == 1) {
                Thread.sleep(100);
                var request = new Request.Builder()
                    .url("http://localhost:8080/recipes/" + x)
                    .build();
                client.newCall(request).execute();
            }
        }
    }
}
