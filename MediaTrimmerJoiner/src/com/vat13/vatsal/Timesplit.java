package com.vat13.vatsal;

import java.io.*;

public class Timesplit {

	public void trim(String a, int b, int c, int dur, String g) {

		int x = 0;
		long size;
		double bitr;
		System.out.println("path" + a);
		System.out.println("dur" + dur);

		FileInputStream fis = null;
		FileOutputStream fos = null;

		try {

			fis = new FileInputStream(a);

			File f = new File(a);
			size = f.length();
			System.out.println("size" + size);
			bitr = (double) (size * 8) / (1000 * dur);
			System.out.println("bitr" + bitr);
			byte[] buffer = new byte[512];

			fos = new FileOutputStream("/storage/sdcard0/Music/" + g + ".mp3");
			long k = 0;
			while ((x = fis.read(buffer)) != -1) {
				k = k + x;
				if ((k >= (b * bitr * 1000) / 8)
						&& (k <= (c * bitr * 1000) / 8)) {

					fos.write(buffer, 0, x);
				}
			}
		}// try
		catch (FileNotFoundException ex) {
		} catch (IOException ex) {
		} finally {

			try {
				fis.close();
				fos.close();

			} catch (Exception e) {
			}
		}

	}

}
