package main.java.Server;

import com.sun.net.httpserver.HttpServer;
import main.java.Controller.CookieController;
import main.java.Controller.Home.HomeController;
import main.java.Controller.Login.LoginController;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
    private static final int DEFAULT_PORT = 8000;
    private static int PORT = DEFAULT_PORT;

    public static void main(String[] args) throws IOException {
        parseArgs(args);

        HttpServer httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/cookies", new CookieController());
        httpServer.createContext("/", new HomeController());
        httpServer.createContext("/login/", new LoginController());
        httpServer.setExecutor(null);
        httpServer.start();

        System.out.println("Server started at port " + PORT);
    }

    private static void parseArgs(String[] args) {
        if (args.length > 0) {
            try {
                PORT = Integer.parseInt(args[0]);
            } catch (NumberFormatException exception) {
                System.out.println("Incorrect port number, using port " + PORT);
            }
        }
    }
}
