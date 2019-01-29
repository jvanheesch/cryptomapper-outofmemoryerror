package com.github.jvanheesch.wicket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

public final class MimicBrowserPageLoads {
    private static final String WEBAPP_URL = "http://localhost:8080";
    private static final int NUMBER_OF_REQUESTS = 100_000;

    public static void main(String[] args) {
        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);

        for (int i = 0; i < NUMBER_OF_REQUESTS; i++) {
            String html = getHtml();
            String css = getCssForHtml(html);
            // remove JSESSIONID cookie
            cookieManager.getCookieStore().removeAll();
        }
    }

    private static String getHtml() {
        return getWebPage(WEBAPP_URL + "/");
    }

    private static String getCssForHtml(String html) {
        int first = html.indexOf("./");

        String relativePathToCssResource = html.substring(first + 1, html.indexOf("\"", first));

        return getWebPage(WEBAPP_URL + relativePathToCssResource);
    }

    private static String getWebPage(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                return in.lines().collect(Collectors.joining());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
