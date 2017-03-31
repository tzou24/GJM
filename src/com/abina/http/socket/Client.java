package com.abina.http.socket;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Client extends Thread {

	private static String WEBAPP_ROOT;
	private Socket socket;
	private InputStream in;
	private PrintStream out;

	/**
	 * 构造方法
	 * 
	 * @param socket
	 */
	public Client(Socket socket) {
		this.WEBAPP_ROOT = this.getClass().getResource("/").getFile() + "webroot/";
		this.socket = socket;
		try {
			this.in = socket.getInputStream();
			this.out = new PrintStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解析http 输入流
	 */
	public void run() {
		// 线程运行后 直接解析 流
		BufferedReader inReader = new BufferedReader(new InputStreamReader(in));
		try {
			String httpUrlMessage = inReader.readLine();
			String[] contents = httpUrlMessage.split(" ");
			if (contents.length == 3) {
				System.out.println("接受到请求 : method = " + contents[0] + " filename = " + contents[1] + "http version="
						+ contents[2]);
				String filename = contents[1];
				sendFile(filename);
			} else {
				sendError(400, "解析错误！");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送报错信息 TODO 还有问题，中文编码，各个浏览器显示的结果不一样。 =~=
	 * 
	 * @param errorCode
	 *            错误代码
	 * @param errorMessage
	 *            错误消息
	 */

	private void sendError(int errorCode, String errorMessage) {
		try {
			String html = "<html><head><title>" + errorCode + "</title></head><body>" + errorCode + ":" + errorMessage
					+ "</body></html>";
			byte[] bytes = html.getBytes();
			out.print("HTTP/1.0 " + errorCode + " errorpage");
			out.print("content-tyle: text/html");
			out.print("content-length:" + bytes.length);
			out.println();
			out.write(bytes);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sendFile(String filename) {
		String filepath = WEBAPP_ROOT + filename;
		System.out.println("实际访问的文件路径:" + filepath);
		File file = new File(filepath);
		if (!file.exists()) {
			sendError(404, "文件不存在");
			return;
		}
		// 发送文件正文
		try {
			InputStream in = new FileInputStream(file);
			byte[] bytes = new byte[(int) file.length()];
			in.read(bytes);
			out.print("HTTP/1.0 200 CXCHTTP-FILE");
			out.print("content-length:" + bytes.length);
			out.println(); // 一定要输出 表示下面是正文
			out.write(bytes); // html内容
			// 清理
			out.flush();
			out.close();
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
