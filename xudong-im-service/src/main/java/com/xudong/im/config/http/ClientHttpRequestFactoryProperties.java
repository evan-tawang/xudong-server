package com.xudong.im.config.http;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author evan.shen
 * @since 2017/11/1
 */
@ConfigurationProperties("httpclient")
public class ClientHttpRequestFactoryProperties {
    private int readTimeout = 10000;
    private int connectTimeout = 3000;
    private int maxTotalConnections = 128;
    private int maxConnectionsPerRoute = 8;

    /** */
    public int getReadTimeout() {
        return readTimeout;
    }

    /***/
    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    /** */
    public int getConnectTimeout() {
        return connectTimeout;
    }

    /***/
    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    /** */
    public int getMaxTotalConnections() {
        return maxTotalConnections;
    }

    /***/
    public void setMaxTotalConnections(int maxTotalConnections) {
        this.maxTotalConnections = maxTotalConnections;
    }

    /** */
    public int getMaxConnectionsPerRoute() {
        return maxConnectionsPerRoute;
    }

    /***/
    public void setMaxConnectionsPerRoute(int maxConnectionsPerRoute) {
        this.maxConnectionsPerRoute = maxConnectionsPerRoute;
    }

    @Override
    public String toString() {
        return "ClientHttpRequestFactoryProperties{" +
                "readTimeout=" + readTimeout +
                ", connectTimeout=" + connectTimeout +
                ", maxTotalConnections=" + maxTotalConnections +
                ", maxConnectionsPerRoute=" + maxConnectionsPerRoute +
                '}';
    }
}
