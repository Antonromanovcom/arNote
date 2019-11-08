package com.antonromanov.arnote.model;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

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

	public void base64StringToJpg() {

		// System.out.println(this.decodedBase64String);

		String[] strings = decodedBase64String.split(",");
		String extension;

		switch (strings[0]) {//check image's extension
			case "data:image/jpeg;base64":
				extension = "jpeg";
				break;
			case "data:image/png;base64":
				extension = "png";
				break;
			default://should write cases for more images types
				extension = "jpg";
				break;
		}

		//convert base64 string to binary data
		byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
		byte[] imageInByte = null;
		InputStream in = new ByteArrayInputStream(data);

		try {
			BufferedImage bImageFromConvert = ImageIO.read(in);

			URL urlFG = new URL("https://pbs.twimg.com/media/DuLkkh_XgAE7Juj.jpg");
			final BufferedImage biFG = ImageIO.read(urlFG);
			cutCircle(bImageFromConvert, biFG);
			cutCircle2(bImageFromConvert, "png");

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bImageFromConvert, "jpg", baos);
			baos.flush();
			imageInByte = baos.toByteArray();
			baos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		String path = "C:\\elexir\\test_image." + extension;
		File file = new File(path);

		try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
			//outputStream.write(data);
			outputStream.write(imageInByte);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void cutCircle2(BufferedImage image, String extension) {

		try {
			int radius;
			if (image.getHeight() > image.getWidth()) {
				radius = image.getWidth() / 2;
			} else {
				radius = image.getHeight() / 2;
			}

			int x = image.getWidth() / 2;
			int y = image.getHeight() / 2;
//			int margin = 10;
			int margin = ((image.getWidth()/100)*10);

			BufferedImage bi = new BufferedImage(2 * radius + (2 * margin), 2 * radius + (2 * margin), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = bi.createGraphics();
			g2.translate(bi.getWidth() / 2, bi.getHeight() / 2);
			Arc2D myArea = new Arc2D.Float(0 - radius, 0 - radius, 2 * radius, 2 * radius, 0, -360, Arc2D.OPEN);

			AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f);
			g2.setComposite(composite);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

			g2.setClip(myArea);
			g2.drawImage(image.getSubimage(x - radius, y - radius, x + radius, y + radius), -radius, -radius, null);
			String path = "C:\\elexir\\test_image_1." + extension;

			ImageIO.write(bi, "png", new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void cutCircle(BufferedImage imageBG, BufferedImage imageFG) {

		int w = imageBG.getWidth();
		int h = imageBG.getHeight();
		int x = 0;
		int y = 0;
		int wNew = 0;
		int hNew = 0;

		if (w == h) {
			x = w / 2;
			y = x;
			wNew = w * (1 - (10 / 100));
			hNew = wNew;
		} else if (h > w) {
			x = w / 2;
			y = h / 2;
			wNew = w * (1 - (10 / 100));
			hNew = wNew;
		} else {
			x = w / 2;
			y = h / 2;
			hNew = h * (1 - (10 / 100));
			wNew = hNew;
		}

		Ellipse2D.Double ellipse1 = new Ellipse2D.Double(200, 400, 800, 800);

		BufferedImage img = new BufferedImage((int) ellipse1.width, (int) ellipse1.height, BufferedImage.TYPE_INT_ARGB);
//		BufferedImage img = new BufferedImage(800, 1600, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g = img.createGraphics();
		g.setClip(ellipse1);
		g.drawImage(imageBG, 0, 0, null);
		try {
			ImageIO.write(img, "png", new File("Clipped.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		g.setClip(null);
		Stroke s = new BasicStroke(2);
		g.setStroke(s);
		g.setColor(Color.BLACK);
		//g.draw(circle);
		g.dispose();

	}


}
