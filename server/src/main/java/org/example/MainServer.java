package org.example;

import org.example.server.server.*;
import org.example.server.server.DataBase.DBConnection;
import org.example.server.server.DataBase.DBInitializer;
import org.example.server.server.DataBase.MainDB;
import org.example.server.server.commands.ExitSaver;
import org.example.xmlUtils.XmlFileHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class MainServer {
    public static final Logger logger = Logger.getLogger(MainServer.class.getCanonicalName());
    private static InetSocketAddress address;

    public static void main(String[] args) throws IOException {
        logger.info("Connect with server");
        try (Scanner scanner = new Scanner(System.in)) {
            DatagramChannel server = DatagramChannel.open();
            server.configureBlocking(false);
            int Port = 43414;
            logger.info("Server listening port " + Port);
            address = new InetSocketAddress(Port);
            server.bind(address);
            ConsoleManager consoleManager = new ConsoleManager();
            MainDB mainDB = connectToDB();
            if(mainDB == null) return;
            LocalDateBase localDateBase = new LocalDateBase();
            Receiver receiver = new Receiver(mainDB, localDateBase);

            ExecutorService executor = Executors.newCachedThreadPool();
            ExecutorService responseSender = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

            Invoker invoker = new Invoker(receiver);

            XmlFileHandler xmlFileHandler = new XmlFileHandler();
            Runtime.getRuntime().addShutdownHook(new Thread(new ExitSaver(xmlFileHandler, receiver)));

            RequestReceiver requestReceiver = new RequestReceiver(address , server , invoker , executor , responseSender);
            Thread thread = new Thread(requestReceiver);
            thread.start();
        }
    }
    private static MainDB connectToDB() {
        Connection database;
        database = new DBConnection().connect();
        if (database == null) return null;

        DBInitializer dbInitializer = new DBInitializer(database);
        try {
            dbInitializer.initialize();
        }catch (SQLException throwables) {
            logger.info("Something wrong with db");
            return null;
        }

        try {
            return new MainDB(database);
        }catch (NoSuchAlgorithmException e){
            logger.info("Hashing algorithm not found!");
            return null;
       }
    }
}
