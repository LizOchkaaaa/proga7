package org.example.server.server;



/**Class for data input*/
public class OutStream {
    public static DataInOutStatus outputIntoCLI(String strCLI) {
        System.out.println(strCLI);
        return DataInOutStatus.SUCCESSFULLY;
    }
}
