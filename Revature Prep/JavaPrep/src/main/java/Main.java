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
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        int port = 3000;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/", new StaticHtmlHandler());
        server.createContext("/Shared", new StaticHtmlHandler());
        server.createContext("/Code", new JavaCodeHandler());

        server.setExecutor(null);
        server.start();

        System.out.println("Server Running At http://localhost:" + port);
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

            File file = new File("src/main/resources" + path);

            if (!file.exists()) {
                String error = "404 Not Found: " + path;

                exchange.sendResponseHeaders(404, error.length());
                exchange.getResponseBody().write(error.getBytes());
                exchange.close();

                return;
            }

            byte[] response = Files.readAllBytes(file.toPath());

            exchange.getResponseHeaders().add("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, response.length);
            exchange.getResponseBody().write(response);
            exchange.close();
        }
    }

    private static class JavaCodeHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            Map<String, String> params = queryToMap(exchange.getRequestURI().getQuery());
            String fileName = params.get("file");

            if (fileName == null || !fileName.endsWith(".java")) {
                String error = "Missing or Invalid 'file' Parameter";

                exchange.sendResponseHeaders(400, error.length());
                exchange.getResponseBody().write(error.getBytes());
                exchange.close();

                return;
            }

            File file = new File("src/main/java/" + fileName);

            if (!file.exists()) {
                String error = "404 Not Found: " + fileName;

                exchange.sendResponseHeaders(404, error.length());
                exchange.getResponseBody().write(error.getBytes());
                exchange.close();

                return;
            }

            String code = Files.readString(file.toPath())
                    .replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;");

            exchange.getResponseHeaders().add("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, code.length());
            exchange.getResponseBody().write(code.getBytes());
            exchange.close();
        }

        /**
         * Converts a query string into a map where keys and values are derived from the query parameters.
         * This method splits the query string by "&" to separate individual parameters,
         * then splits each parameter by "=" to extract the key and value.
         * Only parameters with both a key and a value are included in the resulting map.
         *
         * @param query the query string to be parsed, typically in the format "key1=value1&key2=value2".
         * @return a map containing the parsed query parameters as key-value pairs.
         */
        private Map<String, String> queryToMap(String query) {
            return Arrays.stream(query.split("&"))
                    .map(s -> s.split("="))
                    .filter(arr -> arr.length == 2)
                    .collect(Collectors.toMap(arr -> arr[0], arr -> arr[1]));
        }
    }
}
