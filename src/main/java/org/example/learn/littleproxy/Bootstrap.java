package org.example.learn.littleproxy;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import org.example.learn.littleproxy.constant.SystemProps;
import org.littleshoot.proxy.HttpFilters;
import org.littleshoot.proxy.HttpFiltersAdapter;
import org.littleshoot.proxy.HttpFiltersSourceAdapter;
import org.littleshoot.proxy.HttpProxyServer;
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class Bootstrap {

    private static final Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    public static void main(String[] args) {
        // 本地代理监听端口
        final int listeningPort = Integer.parseInt(System.getProperty(SystemProps.LISTENING_PORT));
        // 设置目标 IP 和端口
        final String redirectIps = System.getProperty(SystemProps.REDIRECT_IPS);
        final int redirectPort = Integer.parseInt(System.getProperty(SystemProps.REDIRECT_PORT));

        HttpProxyServer server = DefaultHttpProxyServer.bootstrap()
                .withPort(listeningPort)
                .withFiltersSource(new HttpFiltersSourceAdapter() {
                    @Override
                    public HttpFilters filterRequest(HttpRequest originalRequest, ChannelHandlerContext ctx) {
                        return new HttpFiltersAdapter(originalRequest) {

                            @Override
                            public HttpResponse clientToProxyRequest(HttpObject httpObject) {
                                /*if (httpObject instanceof HttpRequest) {
                                    HttpRequest request = (HttpRequest) httpObject;
                                    // 修改 Host 头（伪装为目标服务）
                                    request.headers().set(HttpHeaderNames.HOST, redirectIps + ":" + redirectPort);
                                }*/
                                return null; // 表示继续处理请求
                            }

                            @Override
                            public InetSocketAddress proxyToServerResolutionStarted(String hostAndPort) {
                                // 强制改写目标 IP 强制改写端口
                                return new InetSocketAddress(redirectIps, redirectPort);
                            }
                        };
                    }
                })
                .start();

        InetSocketAddress listenAddress = server.getListenAddress();
        logger.info("HTTP proxy started on port {}", listenAddress.getPort());
    }

}
