package readFile;

import java.io.IOException;

public class OpenFile {
	public static void readFile(String filepath) {
		Runtime runtime = Runtime.getRuntime(); // getting Runtime object
		try {
			String[] s = new String[] {"C:\\Program Files\\Windows NT\\Accessories\\wordpad.exe"
					, filepath + "\\part-r-00000"};
	        Process process = runtime.exec(s);
		} catch (IOException e) {
			System.out.println("Lỗi tại class openFile");
			//e.printStackTrace();
		}
	}
}
