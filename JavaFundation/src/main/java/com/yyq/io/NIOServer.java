package com.yyq.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class NIOServer {
    public static void main(String[] args) throws IOException {
        //打开服务端SOCKET
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress("127.0.0.1", 8000));
        //设置非阻塞
        serverSocketChannel.configureBlocking(Boolean.FALSE);
        //绑定监听端口号
        Selector selector = Selector.open();
        //注册感兴趣的时间
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while(true){
            //如果没有查到感兴趣的事件，则阻塞
            selector.select();
            Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
            //迭代Selection中的事件
            while (selectionKeyIterator.hasNext()){
                SelectionKey key = selectionKeyIterator.next();
                selectionKeyIterator.remove();
                if(key.isAcceptable()){
                    handleAcceptable(serverSocketChannel,selector,key);
                }
                if(key.isReadable()){
                    handleRead(key);
                }
                if(key.isWritable()){
                    handleWrite(key);
                }
            }
        }
    }
    /*
     * 功能：处理read的数据
     * @author zhangdaquan
     * @date 2018/11/23 下午3:12
     * @param [readKey]
     * @return void
     * @exception
     */
    static void handleRead(SelectionKey readKey) throws IOException {
        SocketChannel channel = (SocketChannel) readKey.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1<<5);
        channel.read(buffer);
        System.out.println("我收到了："+(new String(buffer.array())));
        readKey.interestOps(SelectionKey.OP_WRITE);
    }
    /*
     * 功能：处理写回的数据
     * @author zhangdaquan
     * @date 2018/11/23 下午3:13
     * @param [writeKey]
     * @return void
     * @exception
     */
    static void handleWrite(SelectionKey writeKey) throws IOException {
        byte [] bytes = "我写回了:".getBytes();
        SocketChannel channel = (SocketChannel)writeKey.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1<<5);
        buffer.put(bytes);
        System.out.println(new String(buffer.array()));
        channel.write(buffer);
    }
    /*
     * 功能：接受请求
     * @author zhangdaquan
     * @date 2018/11/23 下午3:26
     * @param [serverSocketChannel, selector, acceptableKey]
     * @return void
     * @exception
     */
    static void handleAcceptable(ServerSocketChannel serverSocketChannel, Selector selector, SelectionKey acceptableKey)
            throws IOException {
        SelectableChannel channel = serverSocketChannel.accept();
        //设置信道为非阻塞
        channel.configureBlocking(Boolean.FALSE);
        //把选择器注册到连接到的客户端信道并设置感兴趣的事件
        channel.register(selector,SelectionKey.OP_READ);
    }
}
