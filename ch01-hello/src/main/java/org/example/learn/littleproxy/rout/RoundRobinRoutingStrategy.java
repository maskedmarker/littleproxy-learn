package org.example.learn.littleproxy.rout;

import io.netty.handler.codec.http.HttpRequest;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinRoutingStrategy implements RoutingStrategy {

    private List<InetSocketAddress> addressList = new ArrayList<>();

    private AtomicInteger index = new AtomicInteger(0);

    public RoundRobinRoutingStrategy() {
    }

    public RoundRobinRoutingStrategy(List<InetSocketAddress> addressList) {
        this.addressList = addressList;
    }

    @Override
    public boolean support(String strategyName) {
        return RoutingStrategy.NAME_ROUND_ROBIN.equalsIgnoreCase(strategyName);
    }

    @Override
    public InetSocketAddress routTo(HttpRequest originalRequest) {
        int index = this.index.getAndAdd(1) % addressList.size();
        return addressList.get(index);
    }

    public List<InetSocketAddress> getAddressList() {
        return addressList;
    }

    @Override
    public void setAddressList(List<InetSocketAddress> addressList) {
        this.addressList = addressList;
    }
}
