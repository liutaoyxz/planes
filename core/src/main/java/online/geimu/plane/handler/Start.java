package online.geimu.plane.handler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.apache.log4j.Logger;

/**
 * Created by ltlxy on 2016/11/27.
 */
public class Start {

    private static final Logger log = Logger.getLogger(Start.class);

    private static final int DEFAULT_PORT = 11171;

    public static void main(String[] args) throws InterruptedException {
        int port = DEFAULT_PORT;
        if (args != null && args.length>0){
            try {
                port = Integer.parseInt(args[0]);
            }catch (Exception e){
                log.error("error port");
            }
        }
        ServerBootstrap b = new ServerBootstrap();
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        b.group(boss,worker);
        b.channel(NioServerSocketChannel.class);
        b.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel c) throws Exception {
                ChannelPipeline pipeline = c.pipeline();
                pipeline.addLast("http-codec",new HttpServerCodec());
                pipeline.addLast("aggregator",new HttpObjectAggregator(65536));
                pipeline.addLast("http-chunked",new ChunkedWriteHandler());
                pipeline.addLast("wchandler",new WCServerHandler());
            }
        });
        log.info("服务器端启动....... 监听端口 :"+port);
        ChannelFuture f = b.bind(port).sync();
        f.channel().closeFuture().sync();
    }



}
