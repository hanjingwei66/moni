package com.shuojie.nettyService;

import com.shuojie.nettyService.Handler.MapsWebSocketFrameHandle;
import com.shuojie.nettyService.Handler.SensorHandler;
import com.shuojie.nettyService.Handler.TextWebSocketFrameHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketFrameAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Autowired
    private TextWebSocketFrameHandler textWebSocketFrameHandler;
    @Autowired
    private SensorHandler sensorHandler;
    @Autowired
    private MapsWebSocketFrameHandle mapsWebSocketFrameHandle;
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //websocket协议本身是基于http协议的，所以这边也要使用http解编码器
        pipeline.addLast(new HttpServerCodec());
        //mqtt 协议的编码解码器
//        pipeline.addLast(new MqttDecoder());
//        pipeline.addLast(MqttEncoder.INSTANCE);
        //以块的方式来写的处理器
        pipeline.addLast(new ChunkedWriteHandler());
        //netty是基于分段请求的，HttpObjectAggregator的作用是将请求分段再聚合,参数是聚合字节的最大长度
        pipeline.addLast(new HttpObjectAggregator(8192));
        // webSocket 数据压缩扩展，当添加这个的时候WebSocketServerProtocolHandler
        pipeline.addLast(new WebSocketServerCompressionHandler());
        pipeline.addLast(new WebSocketFrameAggregator(10 * 1024 * 1024));
        //ws://server:port/context_path
        //ws://localhost:9999/ws
        //参数指的是contex_path
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws", null, true, 10485760));

        //websocket定义了传递数据的6中frame类型
//        pipeline.addLast(new com.shuojie.nettyService.Handler.SensorHandler());


        pipeline.addLast("TextWebSocketFrameHandler",textWebSocketFrameHandler);
        pipeline.addLast("SensorHandler",sensorHandler);
        pipeline.addLast("mapsWebSocketFrameHandle",mapsWebSocketFrameHandle);
//        pipeline.addLast(new BinaryWebSocketFrameHandler());
//        new StringDecoder();
        //测试git

    }

}
