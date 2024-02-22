package perlinNoise;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

public class NoiseImageMain {
	
	private static final int WIDTH = 800;
	private static final int HEIGHT = 800;

	public static void main(String[] args) throws Exception {

//		for (int t = 0; t < 24*10; t++) {
			final BufferedImage img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
			for (int x = 0; x < WIDTH; x += 1) {
				for (int y = 0; y < HEIGHT; y += 1) {
	//				final double noiseVal = Noise.noise(new Vector2D(x/100.0, y/100.0), WIDTH/100);
	//				final int pixVal = (int)(128 + noiseVal*128);
					final double noiseVal = 5*Math.abs(Noise.noise(new Vector2D(x/40.0, y/40.0), WIDTH/40));
//					final double noiseVal = 2*Math.abs(Noise3D.noise(x/40.0, y/40.0, t/48.0, WIDTH/40, HEIGHT/40));
//					final int pixVal = (int)(255 * (noiseVal%1));
					final int pixVal = (int)(255 * noiseVal);
//					final int pixVal = (int)(255*2 * Math.abs(noiseVal%1-0.5));
					img.setRGB(x, y, pixVal * 0x10101 + 0xff000000);
				}
			}
			ImageIO.write(img, "png", new File("noise22.png"));
//			ImageIO.write(img, "png", new File("frames/frame" + new DecimalFormat("000").format(t) + ".png"));
//			System.out.println("Frame: " + (t+1));
//		}
//		System.out.println("Finished");
	}

}
