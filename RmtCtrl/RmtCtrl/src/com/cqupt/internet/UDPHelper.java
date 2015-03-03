package com.cqupt.internet;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
/**
 * ���ܣ�ʵ�ֻ��ڣգģ�Э�����Ϣ���������
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
	 * ������Ϣ
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
	 * ������Ϣ
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
	 * �ر���Դ
	 */
	public void close(){
		if(ds != null){
			ds.close();
			ds = null;
		}
	}
}
