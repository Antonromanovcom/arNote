package com.antonromanov.arnote.model;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class WishWithPicture {

	private String decodedBase64String;

	public String getDecodedBase64String() {
		return decodedBase64String;
	}

	public void setDecodedBase64String(String decodedBase64String) {
		this.decodedBase64String = decodedBase64String;
	}

	public WishWithPicture(String decodedBase64String) {
		this.decodedBase64String = decodedBase64String;
	}

	public WishWithPicture() {
	}

	public void base64StringToPng() {


		String[] strings = decodedBase64String.split(","); // отрезаем ненужное
		byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
		InputStream in = new ByteArrayInputStream(data);
		try {
			BufferedImage bImageFromConvert = ImageIO.read(in); // пишем байты в BufferedImage
			cutCircle(bImageFromConvert); // обрезаем
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void cutCircle(BufferedImage image) {

		try {
			int radius;
			if (image.getHeight() > image.getWidth()) {
				radius = image.getWidth() / 2;
			} else {
				radius = image.getHeight() / 2;
			}

			int x = image.getWidth() / 2;
			int y = image.getHeight() / 2;
			int margin = ((image.getWidth() / 100) * 10);

			BufferedImage bi = new BufferedImage(2 * radius + (2 * margin), 2 * radius + (2 * margin), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = bi.createGraphics();
			g2.translate(bi.getWidth() / 2, bi.getHeight() / 2);
			Arc2D myArea = new Arc2D.Float(0 - radius, 0 - radius, 2 * radius, 2 * radius, 0, -360, Arc2D.OPEN);

			AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f);
			g2.setComposite(composite);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

			g2.setClip(myArea);
			g2.drawImage(image.getSubimage(x - radius, y - radius, x + radius, y + radius), -radius, -radius, null);
			String path = "C:\\elexir\\test_image_1.png";

			ImageIO.write(bi, "png", new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
