ListDirectoryContentInGitFormat
```
package test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ListDirectoryContentInGitFormat {

	public static final String DIRECTORY_WINDOWS = "C:\\Users\\rrkaranam\\workspace\\webapp\\";

	public static void main(String[] args) throws FileNotFoundException, IOException {
		ListDirectoryContentInGitFormat listFilesUtil = new ListDirectoryContentInGitFormat();

		listFilesUtil.listFilesAndFilesSubDirectories(DIRECTORY_WINDOWS);

	}

	/**
	 * List all files from a directory and its subdirectories
	 * 
	 * @param rootDirectory
	 *            to be listed
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void listFilesAndFilesSubDirectories(String rootDirectory) throws FileNotFoundException, IOException {
		File directory = new File(rootDirectory);
		// get all the files from a directory
		File[] fList = directory.listFiles();
		for (File file : fList) {
			if (file.getName().startsWith(".") || file.getName().startsWith("target"))
				continue;

			if (file.isFile()) {
				printContent(file.getAbsolutePath());
			} else if (file.isDirectory()) {
				listFilesAndFilesSubDirectories(file.getAbsolutePath());
			}
		}
	}

	private void printContent(String absolutePath) throws FileNotFoundException, IOException {
		System.out.println(absolutePath.replace(DIRECTORY_WINDOWS, ""));
		System.out.println("```");
		try (BufferedReader br = new BufferedReader(new FileReader(absolutePath))) {
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		}
		System.out.println("```");
	}

}
```
