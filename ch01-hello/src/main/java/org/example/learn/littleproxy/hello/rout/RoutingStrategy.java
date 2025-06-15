package org.example.learn.littleproxy.hello.rout;

import io.netty.handler.codec.http.HttpRequest;

import java.net.InetSocketAddress;
import java.util.List;

public interface RoutingStrategy {

    String NAME_ROUND_ROBIN = "roundrobin";

    boolean support(String strategyName);

    InetSocketAddress routTo(HttpRequest originalRequest);

    void setAddressList(List<InetSocketAddress> addressList);
}
