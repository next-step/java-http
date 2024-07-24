package org.apache.coyote.http11;

import java.io.OutputStream;

public class OutputStreamPrinter {
	public static void print(final OutputStream outputStream, final String response) {
		try {
			outputStream.write(response.getBytes());
			outputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
