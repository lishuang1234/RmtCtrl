package com.cqupt.internet;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
/**
 * 功能：实现基于ＵＤＰ协议的信息发送与接受
 * @author LS
 *
 */
public class UDPHelper {
	public static final int PORT = 20000;
	public DatagramSocket ds;
	public UDPHelper(){
		if(ds == null){
			try {
				ds = new DatagramSocket(PORT);
			} catch (SocketException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 发送消息
	 * @param ip
	 * @param port
	 * @param info
	 */
	public void sendData(String ip, int port, String info){
		byte[] data = info.getBytes();
		try {
			DatagramPacket dp = new DatagramPacket(data, data.length, new InetSocketAddress(ip, port));
			ds.send(dp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 接收消息
	 * @return
	 */
	public String receive(){
		byte[] data = new byte[1024];
		DatagramPacket dp = new DatagramPacket(data, data.length);
		try {
			ds.receive(dp);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		String info = new String(dp.getData());
		return info.trim();
	}
	/**
	 * 关闭资源
	 */
	public void close(){
		if(ds != null){
			ds.close();
			ds = null;
		}
	}
}
