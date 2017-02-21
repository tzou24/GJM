package com.abina.generate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

import jp.sourceforge.qrcode.QRCodeDecoder;
import jp.sourceforge.qrcode.data.QRCodeImage;

/**
 * 生成二维码工具类
 * 
 * @author yaobin
 *
 */
public class GenerateQRCode {

	/** 限制生成二维码的数据字节最大 */
	private static final int MAX_DATA_SIZE_LARGE = 500;// 500字节的原始数据, 生成二维码时,
														// 是89宽度
	private static final int MAX_DATA_SIZE_SMALL = 84;

	// 2、编写生成二维码的Java代码，main方法如下：
	public static void main(String[] args) {
		String data = "http://www.zoubp.com";
		/**
		 * 生成二维码
		 */
		GenerateQRCode.encode_large(data, "D:/test/123.png");
		/**
		 * 解析二维码
		 */
		GenerateQRCode.decode("D:/test/123.png");
	}

	/**
	 * Encoding the information to a QRCode, size of the information must be
	 * less than 84 byte.
	 * 
	 * @param srcValue
	 * @param qrcodePicfilePath
	 * @return
	 */
	public static boolean encode_small(String srcValue, String qrcodePicfilePath) {
		byte[] d = srcValue.getBytes();
		int dataLength = d.length;
		/* 113是预先计算出来的.113*113像素 注意：图像宽度必须比生成的二维码图像宽度大,至少相等,否则,二维码识别不出来 */
		int imageWidth = 113; // 37字节, (37-1)*3+2+3-1+1 = 113
		int imageHeight = imageWidth;
		BufferedImage bi = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bi.createGraphics();
		g.setBackground(Color.WHITE);
		g.clearRect(0, 0, imageWidth, imageHeight);
		g.setColor(Color.BLACK);
		if (dataLength > 0 && dataLength <= MAX_DATA_SIZE_SMALL) {
			/* 生成二维码 */
			Qrcode qrcode = new Qrcode();
			qrcode.setQrcodeErrorCorrect('M');
			qrcode.setQrcodeEncodeMode('B');
			qrcode.setQrcodeVersion(5);
			boolean[][] b = qrcode.calQrcode(d);
			int qrcodeDataLen = b.length;
			/*
			 * 画二维码图形, 画出的图形宽度是 ((qrcodeDataLen-1)*3+2) + 3 -1 ;
			 * 生成的image的宽度大小必须>=该值,外围的1个像素用来标识此块区域为二维码
			 * 
			 * fillRect(int x, int y, int width, int height) 函数作用：
			 * 填充指定的矩形。该矩形左边和右边位于 x 和 x + width - 1。顶边和底边位于 y 和 y + height - 1。
			 * 得到的矩形覆盖的区域宽度为 width 像素，高度为 height 像素。 使用图形上下文的当前颜色填充该矩形。 参数： x -
			 * 要填充矩形的 x 坐标。 y - 要填充矩形的 y 坐标。 width - 要填充矩形的宽度。 height -
			 * 要填充矩形的高度。
			 * 
			 */
			for (int i = 0; i < qrcodeDataLen; i++) {
				for (int j = 0; j < qrcodeDataLen; j++) {
					if (b[j][i]) {
						g.fillRect(j * 3 + 2, i * 3 + 2, 3, 3);
					}
				}
			}
		} else {
			return false;
		}
		g.dispose();
		bi.flush();
		File f = new File(qrcodePicfilePath);
		if (!f.exists()) {
			f.mkdirs();
		}
		String suffix = f.getName().substring(f.getName().indexOf(".") + 1, f.getName().length());
		try {
			ImageIO.write(bi, suffix, f);
		} catch (IOException ioe) {
			return false;
		}
		return true;
	}

	/**
	 * Encoding the information to a QRCode, size of the information must be
	 * less than 500 byte.
	 * 
	 * @param srcValue
	 * @param qrcodePicfilePath
	 * @return
	 */
	public static boolean encode_large(String srcValue, String qrcodePicfilePath) {
		byte[] d = srcValue.getBytes();
		int dataLength = d.length;
		// 269是预先计算出来的.269*269像素 注意：图像宽度必须比生成的二维码图像宽度大,至少相等,否则,二维码识别不出来
		int imageWidth = 269;
		int imageHeight = imageWidth;
		BufferedImage bi = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bi.createGraphics();
		g.setBackground(Color.WHITE);
		g.clearRect(0, 0, imageWidth, imageHeight);
		g.setColor(Color.BLACK);
		if (dataLength > 0 && dataLength <= MAX_DATA_SIZE_LARGE) {
			/* 生成二维码 */
			Qrcode qrcode = new Qrcode();
			qrcode.setQrcodeErrorCorrect('M'); // L, Q, H, 默认 //纠错等级（四种等级）
			qrcode.setQrcodeEncodeMode('B'); // A, N, 默认 //N代表数字，A代表a-Z,B代表其他字符
			qrcode.setQrcodeVersion(18); // 0<= version <=40; 89字节, //
											// (89-1)*3+2+3-1+1 = 269
			boolean[][] b = qrcode.calQrcode(d);
			int qrcodeDataLen = b.length;
			for (int i = 0; i < qrcodeDataLen; i++) {
				for (int j = 0; j < qrcodeDataLen; j++) {
					if (b[j][i]) {
						g.fillRect(j * 3 + 2, i * 3 + 2, 3, 3);
						/*
						 * 画二维码图形, 画出的图形宽度是 ((qrcodeDataLen-1)*3+2) + 3 -1 =
						 * 136; 生成的image的宽度大小必须>=(136+1), 外围的1个像素用来标识此块区域为二维码
						 * fillRect(int x, int y, int width, int height) 函数作用：
						 * 填充指定的矩形。该矩形左边和右边位于 x 和 x + width - 1。顶边和底边位于 y 和 y +
						 * height - 1。 得到的矩形覆盖的区域宽度为 width 像素，高度为 height 像素。
						 * 使用图形上下文的当前颜色填充该矩形。 参数： x - 要填充矩形的 x 坐标。 y - 要填充矩形的 y
						 * 坐标。 width - 要填充矩形的宽度。 height - 要填充矩形的高度。
						 */
					}
				}
			}
		} else {
			return false;
		}
		g.dispose();
		bi.flush();
		/* generate image */
		File f = new File(qrcodePicfilePath);
		String suffix = f.getName().substring(f.getName().indexOf(".") + 1, f.getName().length());
		try {
			ImageIO.write(bi, suffix, f); // "png"
		} catch (IOException ioe) {
			return false;
		}
		return true;
	}

	/**
	 * 解析二维码
	 * 
	 * @param qrcodePicfilePath
	 *            二维码图片地址
	 * @return 二维码内容
	 */
	public static String decode(String qrcodePicfilePath) {
		/* 读取二维码图像数据 */
		File imageFile = new File(qrcodePicfilePath);
		if (!imageFile.exists()) {// 文件不存在返回null
			return null;
		}
		BufferedImage image = null;
		try {
			image = ImageIO.read(imageFile);
		} catch (IOException e) {
			return null;
		}
		/* 解析开始二维码 */
		QRCodeDecoder decoder = new QRCodeDecoder();
		String decodedData = new String(decoder.decode(new J2SEImageGucas(image)));
		return decodedData;
	}
}

class J2SEImageGucas implements QRCodeImage {
	BufferedImage image;

	public J2SEImageGucas(BufferedImage image) {
		this.image = image;
	}

	public int getWidth() {
		return image.getWidth();
	}

	public int getHeight() {
		return image.getHeight();
	}

	public int getPixel(int x, int y) {
		return image.getRGB(x, y);
	}
}