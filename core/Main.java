package core;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;

public class Main {

	static String[] searchItems = {"include", "require"}; // the terms used in files to denote dependency
	static Hashtable<String, ParentFile> listOfDepends;
	
	public static void main(String[] args) throws FileNotFoundException {
		String path = "";
		String[] filesInDir;
		listOfDepends = new Hashtable<String, ParentFile>();
		
		
		if(args.length > 0) {
			path = args[0];
		}else {
			System.out.println("No path given!");
			System.exit(0);
		}
		
		// create path as a file
		File dir = new File(path);
		// get list of files in dir
		filesInDir = dir.list();

		// for each file in directory
		for(String filename : filesInDir) {
			// get files of certain extension
			if(checkExtension(filename)) {
					
				Scanner fileReader = new Scanner(new File(path + filename));
				
				// while there are lines to read
				while(fileReader.hasNextLine()) {
					String line = fileReader.nextLine();

					// check if import exists in line
					String newFile = getImport(line);

					// if the line contained a dependency
					if(newFile != null) {
						// add dependency edge
						addEdge(newFile, filename);
					}

					
				}
			}
		}
	
		// print out results
		for(ParentFile parent : listOfDepends.values()){
			System.out.println("Parent: " + parent.getName());
			System.out.print("Dependents:");
			for(String name : parent.getDepends()) {
				System.out.print(" " + name + " ");
			}
			System.out.println("\n");
		}

	}
	
	private static boolean checkExtension(String nameIn) {
		if(nameIn.contains(".php")) {
			return true;
		}
		return false;
	}
	
	/**
	 * Get the name of the imported file if it exists in the line
	 * @param lineIn line of code to parse for import statements
	 * @return the name of the file being imported
	 */
	private static String getImport(String lineIn) {
		for(int i=0;i<searchItems.length;i++) {
			// if line has certain terms showing dependency
			if(lineIn.contains(searchItems[i]) && lineIn.contains(".php")) {
				// if line is using "
				if(lineIn.contains("\"")) {
					return lineIn.split("\"")[1]; // get the file name since it is always index 1 in array
				}else {
					// line is using '
					return  lineIn.split("'")[1]; // get the file name since it is always index 1 in array
				}
	
			}
		}
		return null;
	}
	/**
	 * Add edge showing dependency
	 * @param parentIn parent file
	 * @param child child file
	 */
	private static void addEdge(String parentIn, String child) {
		// check if the depended file is listed in dependency already if not create it
		if(!(listOfDepends.containsKey(parentIn))) {
			listOfDepends.put(parentIn, new ParentFile(parentIn));

		}
		// get the parent file object
		ParentFile parent = listOfDepends.get(parentIn);
		// add the name of the file that this dependency is in
		parent.add(child);
	}

}