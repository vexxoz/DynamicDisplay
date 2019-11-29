package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import fileTypes.ClassFile;
import fileTypes.ClassFile;

public class LibraryReader {
	
	// read stream to open and read the files
	private Scanner readStream;
	// Queue of files that need to be searched
	private Queue<ClassFile> list;
	// A hashtable of all files that have been searched
	private Hashtable<String, ClassFile> seenElements;
	//file being read
	private File file;
	// the current path of the file worked on
	private String path;
	
	/**
	 * Constructor for a LibraryReader object used to find dependencies between files of 
	 * a library in java only
	 * @param path The absolute path of a file
	 * @param fileName the name of the file
	 * @throws FileNotFoundException Throws exception if file is not found
	 */
	public LibraryReader(String pathIn, String fileName) throws FileNotFoundException {
		// instantiate a new queue
		list = new LinkedList<ClassFile>();
		// instantiate hash table
		seenElements = new Hashtable<String, ClassFile>();

		// add the first file to the queue to be searched
		list.add(new ClassFile(pathIn, fileName));
		
		//sets the path global variable
		path = pathIn;
		
	}
	/**
	 * searchLibrary() is used to read files in a queue find their dependencies
	 * @return A hashtable of search files with their dependencies
	 * @throws IOException If file cannot be read
	 */
	public Hashtable<String,ClassFile> searchLibrary() throws IOException {
		// while there are files to be searched
		while(list.size() > 0) {
			
			// gets new file from list
			file = new File(list.peek().getName());
			// gets the path being worked in
			path = list.peek().getAbsolutePath();
			// read new file
			readStream = new Scanner(file);
			
			// line being read declaration
			String line;
			// while EOF has not been reached
			while(readStream.hasNext()) {
				//read the next line of the file
				line = readStream.nextLine();
				// if the line contains an import statement
				if(line.contains("import")) {
					// split the line into string chunks broken up by spaces
					String[] seq = line.split(" ");
					// if the include is a default package ignore it
					if(!seq[1].contains("java")) {
						// find and return the file that this import statement relates to
						list.add(findFromImport(seq[1]));
					}
				}
			}
			
			//close stream of file
			readStream.close();
			//adds file searched to list of seen elements
			seenElements.put(file.getName(), list.poll());
			
		}
		
		// return the list of elements 
		return seenElements;
	}
	/**
	 * Private function to find a file from an import line
	 * @param importLine String which is from a java file used to import a package
	 * @return
	 */
	private ClassFile findFromImport(String importLine) {
		// break the import line into sections 
		String[] importSections = importLine.split(".");
		// last section of import line is the name of the file assuming code is only java files
		String fileName = importSections[importSections.length-1] + ".java";
		path = path.substring(0, path.lastIndexOf("/")) + "/";
		System.out.println(path);
		
		return null;
	}
}

