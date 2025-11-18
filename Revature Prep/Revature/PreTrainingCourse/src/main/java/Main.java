package main.java;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        int port = 3000;

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new StaticHtmlHandler());
        server.setExecutor(null);
        server.start();

        System.out.println("Server Running At: http://localhost:" + port);
        Desktop.getDesktop().browse(new URI("http://localhost:" + port));
    }

    private static class StaticHtmlHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            URI uri = exchange.getRequestURI();
            String path = uri.getPath();

            if (path.equals("/")) {
                path = "/HomePage.html";
            }

            File file = new File("public" + path);

            if (!file.exists()) {
                file = new File("src/main/resources/" + path);
            }

            if (!file.exists()) {
                String error = "404 Not Found: " + path;

                exchange.sendResponseHeaders(404, error.length());
                exchange.getResponseBody().write(error.getBytes());
                exchange.close();

                return;
            }

            String contentType = guessContentType(path);
            byte[] response = Files.readAllBytes(file.toPath());

            exchange.getResponseHeaders().add("Content-Type", contentType);
            exchange.sendResponseHeaders(200, response.length);
            exchange.getResponseBody().write(response);
            exchange.close();
        }

        private String guessContentType(String path) {
            if (path.endsWith(".html")) {
                return "text/html";
            } else if (path.endsWith(".css")) {
                return "text/css";
            }  else if (path.endsWith(".js")) {
                return "application/javascript";
            }  else if (path.endsWith(".json")) {
                return "application/json";
            } else if  (path.endsWith(".java")) {
                return "text/plain";
            }

            return "application/octet-stream";
        }
    }
}
