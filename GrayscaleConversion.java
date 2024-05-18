// t1: take input
// t2: import images
// t3: function for RGB to grayscale
// t4: divide portion of image and make threads to run 
// t5: perform normal conversion and compare time

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

public class GrayscaleConversion {
	public static void main(String args[]) {
		String inputImagePath = args[0];
		String outputImagePath = args[1];

		try {
			BufferedImage inputImage = ImageIO.read(new File(inputImagePath));
			int width = inputImage.getWidth();
			int height = inputImage.getHeight();
			long startTime, endTime;
			BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

			startTime = System.currentTimeMillis();
			convertToGrayscaleNormal(inputImage, outputImage);
			endTime = System.currentTimeMillis();
			System.out.println("Normal execution time = " + (endTime - startTime) + " ms");

			startTime = System.currentTimeMillis();
			convertToGrayscaleParallel(inputImage, outputImage);
			endTime = System.currentTimeMillis();
			System.out.println("Parallel execution time = " + (endTime - startTime) + " ms");

			ImageIO.write(outputImage, "jpg", new File(outputImagePath));
			System.out.println("Conversion Done...");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void convertToGrayscaleParallel(BufferedImage inputImage, BufferedImage outputImage) {
		int height = inputImage.getHeight();
		int noOfThreads = Runtime.getRuntime().availableProcessors();
		Thread[] threads = new Thread[noOfThreads];
		int segment = height / noOfThreads;

		for (int i = 0; i < noOfThreads; i++) {
			int start = i * segment;
			int end = i == noOfThreads - 1 ? height : start + segment;
			threads[i] = new Thread(new makeGrayscale(inputImage, outputImage, start, end));
			threads[i].start();
		}
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	private static void convertToGrayscaleNormal(BufferedImage inputImage, BufferedImage outputImage) {
		int height = inputImage.getHeight();
		new makeGrayscale(inputImage, outputImage, 0, height).run();;

	}
}

class makeGrayscale implements Runnable {
	private final BufferedImage inputImage;
	private final BufferedImage outputImage;
	private final int start;
	private final int end;

	public makeGrayscale(BufferedImage inputImage, BufferedImage outputImage, int start, int end) {
		this.inputImage = inputImage;
		this.outputImage = outputImage;
		this.start = start;
		this.end = end;
	}

	@Override
	public void run() {
		for (int y = start; y < end; y++) {
			for (int x = 0; x < inputImage.getWidth(); x++) {
				Color rgb = new Color(inputImage.getRGB(x, y));
				int grayLevel = (int) (0.299 * rgb.getRed() + 0.587 * rgb.getGreen() + 0.114 * rgb.getBlue());
				int gray = new Color(grayLevel, grayLevel, grayLevel).getRGB();
				outputImage.setRGB(x, y, gray);
			}
		}
	}
}
