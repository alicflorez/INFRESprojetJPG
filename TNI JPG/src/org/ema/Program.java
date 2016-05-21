package org.ema;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

		File img = new File("res/lena_gray.gif");
		
		BufferedImage in;
		try {
			in = ImageIO.read(img);
			BufferedImage newImage = ImageIO.read(img);
			List<int[]> Plateau = new ArrayList<int[]>();
			
			System.out.println(newImage.getHeight()+" "+newImage.getWidth());
			
			int dalleW=2; int dalleH=2;
			for(int dallesY=0 ; dallesY<newImage.getHeight(); dallesY+=dalleH) {
				for(int dallesX=0; dallesX<newImage.getWidth(); dallesX+=dalleW) {
					int[] dalle = new int[dalleW*dalleH];
					
					Plateau.add(newImage.getData().getPixels(dallesX, dallesY, dalleW, dalleH, dalle));
				}
			}

			System.out.println(Plateau.size());
			int nbPlateau=0;
			for (int[] dalle: Plateau) {
				int nbPixel=0;
				for (int pixel: dalle) {
					System.out.println(nbPlateau+" - "+nbPixel+") "+pixel);
					nbPixel++;
				}
				nbPlateau++;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		return;
	}
	
    // compute the FFT of x[], assuming its length is a power of 2
    public static Complex[] fft(Complex[] x) {
        int N = x.length;

        // base case
        if (N == 1) return new Complex[] { x[0] };

        // radix 2 Cooley-Tukey FFT
        if (N % 2 != 0) { throw new RuntimeException("N is not a power of 2"); }

        // fft of even terms
        Complex[] even = new Complex[N/2];
        for (int k = 0; k < N/2; k++) {
            even[k] = x[2*k];
        }
        Complex[] q = fft(even);

        // fft of odd terms
        Complex[] odd  = even;  // reuse the array
        for (int k = 0; k < N/2; k++) {
            odd[k] = x[2*k + 1];
        }
        Complex[] r = fft(odd);

        // combine
        Complex[] y = new Complex[N];
        for (int k = 0; k < N/2; k++) {
            double kth = -2 * k * Math.PI / N;
            Complex wk = new Complex(Math.cos(kth), Math.sin(kth));
            y[k]       = q[k].plus(wk.times(r[k]));
            y[k + N/2] = q[k].minus(wk.times(r[k]));
        }
        return y;
    }

}
