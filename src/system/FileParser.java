package system;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileParser{
	public static String parseFile(String filename) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		StringBuilder builder = new StringBuilder();
		String line = reader.readLine();
		while(line!=null){
			builder.append(line);
			builder.append('\n');
			line = reader.readLine();
		}
		return builder.toString();
	}
}
