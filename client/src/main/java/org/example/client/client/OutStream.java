package org.example.client.client;

/**Class for data input*/
public class OutStream {
    private static OutStream instance;
    public static DataInOutStatus outputIntoCLI(String strCLI) {
        System.out.println(strCLI);
        return DataInOutStatus.SUCCESSFULLY;
    }

    public static OutStream getInstance() {
        if (instance == null) instance = new OutStream();
        return instance;
    }
}
