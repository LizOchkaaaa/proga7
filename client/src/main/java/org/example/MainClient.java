package org.example;

import org.example.client.RequestHandler;
import org.example.client.client.InputClireader;
import org.example.client.client.ObjectReading;
import org.example.client.client.OutStream;
import org.example.client.client.SessionWorker;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MainClient {
        public static void main(String[] args) {
            try (Scanner scanner = new Scanner(System.in)) {
                System.out.println(getEntryInformation());
                String sessionStatus = null;

                while (true) {
                    try {
                        getRequestHandlerProperties(scanner, InetAddress.getLocalHost());
                    }catch (UnknownHostException e){
                        System.out.println("Your computer has problems with the network, " +
                                "run the application again!");
                        return;
                    }

                    RequestHandler.getInstance().setSocketStatus(true);
                    try {
                        sessionStatus = getSession();
                    } catch (IOException e) {
                        System.out.println("Client can't get authorization on server, try again");
                        return;
                    }
                    if(!sessionStatus.equals("\n\tAction processed successful!\n")){
                        System.out.println(sessionStatus);
                        continue;

                    }

                    System.out.println(RequestHandler.getInstance().getInformation());

                    ObjectReading objectReading = new ObjectReading();
                    InputClireader.openStream();
                    break;
//                if (objectReading.objread(AbstractCommand )) return;
                }
            }
        }

        private static String getEntryInformation() {
            return "Hi";
        }

        private static boolean requestTypeOfAddress(Scanner scanner) {
            String answer;
            do {
                System.out.println("Do you want to specify the address of the remote host?" + "[\"y\", \"n\"]:");
                answer = scanner.nextLine();
            } while (!answer.equals("y") && !answer.equals("n"));
            return answer.equals("y");
        }

        private static int getPort(Scanner scanner) {
            String arg;
            Pattern remoteHostPortPattern = Pattern.compile("^\\s*(\\d{1,5})\\s*");

            do {
                System.out.print("\nPlease, enter remote host port(1-65535): ");
                arg = scanner.nextLine();
            } while (!(remoteHostPortPattern.matcher(arg).find() && (Integer.parseInt(arg.trim()) < 65536)
                    && (Integer.parseInt(arg.trim()) > 0)));

            return Integer.parseInt(arg.trim());
        }

        private static void getRequestHandlerProperties(Scanner scanner, InetAddress localHost) {
            InetAddress remoteHostAddress = localHost;
            if (requestTypeOfAddress(scanner)) {
                String arg;
                Pattern hostAddress = Pattern.compile("^\\s*([\\w.]+)\\s*");

                do {
                    System.out.print("Please, enter remote host address! (Example: 5.18.215.199): ");
                    arg = scanner.nextLine();
                } while (!hostAddress.matcher(arg).find());
                try {
                    remoteHostAddress = InetAddress.getByName(arg.trim());
                } catch (UnknownHostException e) {
                    System.out.println(
                            "\nThe program could not find the server by the specified address!\n " +
                                    "The default address(localhost) will be used!");
                    ;
                }

            }
            RequestHandler.getInstance().setRemoteHostSocketAddress(
                    new InetSocketAddress(remoteHostAddress, getPort(scanner)));
        }


        private static String getSession() throws IOException {
            Session session = new SessionWorker(OutStream.getInstance()).getSession();
            if (session.getTypeOfSession().equals(TypeOfSession.Register)){
                if (!session.getPassword().isEmpty()) {
                    return RequestHandler.getInstance().register(session);
                }
            }

                return RequestHandler.getInstance().login(session);

        }

    }