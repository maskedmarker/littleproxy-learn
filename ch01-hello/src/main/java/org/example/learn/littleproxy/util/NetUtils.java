package org.example.learn.littleproxy.util;

import org.apache.commons.lang3.StringUtils;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NetUtils {

    public static List<InetSocketAddress> parse(String[] ipPortPairs){
        List<InetSocketAddress> inetSocketAddressList = new ArrayList<>();
        Arrays.stream(ipPortPairs).forEach(ipPort -> {
            String[] ipPortPair = StringUtils.split(ipPort, ":");
            InetSocketAddress inetSocketAddress = new InetSocketAddress(ipPortPair[0], Integer.parseInt(ipPortPair[1]));
        });

        return inetSocketAddressList;
    }

}
