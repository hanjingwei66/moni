package com.shuojie.nettyService.Handler;

import com.alibaba.fastjson.JSONObject;
import com.shuojie.domain.sensorModle.BaseSensor;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/*传感器handler*/
@Component
@ChannelHandler.Sharable
public class SensorHandler  extends SimpleChannelInboundHandler<TextWebSocketFrame> {
//    private static RedisService redisService;
//    static {
//        redisService = SpringUtil.getBean(RedisService.class);
//    }
    private boolean flag;
//    @Autowired
//    private RedisService redisService;
    @Value("${redis.key.prefix.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;
    //过期时间60秒
    @Value("${redis.key.expire.authCode}")
    private Long AUTH_CODE_EXPIRE_SECONDS;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {

//        InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
//        String ip = inetSocketAddress.getHostName();
//        System.out.println("收到id为"+ctx.channel().id().asLongText()+"ip为"+ip+"发来的消息1："+msg.text());
        JSONObject json = JSONObject.parseObject(msg.text().toString());//json字符串转json对象
        String command = json.getString("command");
        switch (command){
            case "sensor_check"://检测数量
                List<BaseSensor> list =new ArrayList();
                for(int i=0; i<17;i++){
                    Integer id=(int)(Math.random()*100);
                    Integer sesorType=((int) (Math.random()*4));
                    Integer power =80+((int)(Math.random()*20));
                    Integer status=0;
                    Integer signal =2+(int)(Math.random()*3);
                    String angle=(int)(Math.random()*15)+"";
                    Double distance =Math.random()*100;
                    BaseSensor s= new BaseSensor();
                    s.setId(id);
                    s.setPower(power);
                    s.setSesorName("Sesor"+id);
                    s.setSesorType(sesorType);
                    s.setStatus(status);
                    s.setSignal(signal);
//                    s.setAngle(angle);
//                    s.setDistance(distance);
                    list.add(s);
                }
                Map map=new HashMap();
                map.put("list",list);
                map.put("command", "sensor_check");
                String sesorList = JSONObject.toJSONString(map);
                ctx.channel().writeAndFlush(new TextWebSocketFrame(sesorList));
                ByteBuf buf = ctx.alloc().directBuffer();
                System.out.println("buf.refCnt()"+buf.refCnt());
                return;
            case "sensor_allow"://检测
                if(flag){

                }
//                String redisauthcode= redisService.get("portal:authCode:"+"demo/topics");//REDIS_KEY_PREFIX_AUTH_CODE
//                ctx.channel().writeAndFlush(new TextWebSocketFrame(redisauthcode));
//                System.out.println("value"+redisauthcode);
                break;
            case "sensor_start":
                //REDIS_KEY_PREFIX_AUTH_CODE
//                redisService.set( "portal:authCode:"+"123", ctx);

                new Timer("testTimer").schedule(new TimerTask() {
                    @Override
                    public void run() {
//                        String rediscode= redisService.get("portal:authCode:"+"demo/topics");
//                        ctx.channel().writeAndFlush(new TextWebSocketFrame(rediscode));
                        System.out.println("123");
                    }
                }, 100,100);


                break;
        }

    }



    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
