package com.platform.util;

import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;

/**
 * @author Muhil
 *
 */
public class ImageUtil {

	public static final int Thumbnail_AspectWidth = 200;
	public static final int Thumbnail_AspectHeight = 200;
	public static final String Thumbnail_Exension = "png";

	public static byte[] compressImage(byte[] data) {
		return compressImage(data);
	}

	public static byte[] compressImage(byte[] data, boolean useHighSpeedConversion) {
		Deflater deflater = new Deflater();
		deflater.setLevel(useHighSpeedConversion ? Deflater.BEST_SPEED : Deflater.BEST_COMPRESSION);
		deflater.setInput(data);
		deflater.finish();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] tmp = new byte[4 * 1024];
		while (!deflater.finished()) {
			int size = deflater.deflate(tmp);
			outputStream.write(tmp, 0, size);
		}
		try {
			outputStream.close();
		} catch (Exception ignored) {
		}
		return outputStream.toByteArray();
	}

	public static byte[] decompressImage(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] tmp = new byte[4 * 1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(tmp);
				outputStream.write(tmp, 0, count);
			}
			outputStream.close();
		} catch (Exception ignored) {
		}
		return outputStream.toByteArray();
	}

	/**
	 * @param in           input stream
	 * @param aspectWidth
	 * @param aspectHeight
	 * @return
	 * @throws IllegalArgumentException
	 * @throws ImagingOpException
	 * @throws IOException
	 */
	public static BufferedImage resizeImage(InputStream in, int aspectWidth, int aspectHeight)
			throws IllegalArgumentException, ImagingOpException, IOException {
		return Scalr.resize(ImageIO.read(in), Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, aspectWidth, aspectHeight,
				Scalr.OP_ANTIALIAS);
	}

	public static byte[] getThumbnailImage(byte[] image) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			InputStream in = new ByteArrayInputStream(image);
			BufferedImage bImage = resizeImage(in, Thumbnail_AspectWidth, Thumbnail_AspectHeight);
			ImageIO.write(bImage, Thumbnail_Exension, baos);
			return baos.toByteArray();
		}
		finally {
			baos.close();
		}
	}

}
