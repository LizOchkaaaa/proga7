package org.example.server.server;

import java.util.Scanner;

public class ConsoleManager implements Runnable {
    private final Scanner scanner;

    public ConsoleManager() {
        this.scanner = new Scanner(System.in);
    }

    public void run()
    {
        while(true)
        {
            if (scanner.hasNext())
            {
                String input = scanner.nextLine();
                if (input.equals("exit"))
                {
                    System.out.println("Server stopping");
                    System.exit(0);
                }
                else {
                    System.out.println("Такой команды не существует. Введите exit");
                }
            }
        }
    }
}
