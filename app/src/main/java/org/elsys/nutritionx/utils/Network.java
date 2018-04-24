package org.elsys.nutritionx.utils;

import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Network {
    public static boolean isPortOpen(final String ip, final int port, final int timeout) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(ip, port), timeout);
            socket.close();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
