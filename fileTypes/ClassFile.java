package fileTypes;

import java.util.ArrayList;

public class ClassFile {
	

	private ArrayList<ClassFile> reference;
	private ArrayList<ClassFile> referenced;
	private String name;
	private String type;
	private String path;
	
	/**
	 * Constructor for File of type Class
	 * @param nameIn name of the file
	 */
	public ClassFile(String pathIn, String nameIn){
		name = nameIn;
		path = pathIn;
		type = "Class";
		reference = new ArrayList<ClassFile>();
		referenced = new ArrayList<ClassFile>();
	}
	
	/**
	 * Get the list of files that this file references
	 * @return An ArrayList of files
	 */
	public ArrayList<ClassFile> getReferences() {
		return reference;
	}

	/**
	 * Get the list of files that reference this file
	 * @return An ArrayList of files
	 */
	public ArrayList<ClassFile> getReferenced() {
		return referenced;
	}

	/**
	 * Add a file that this file references
	 * @param File to be added to list
	 */
	public void addReference(ClassFile newFile) {
		reference.add(newFile);
	}

	/**
	 * Add a file that references this file
	 * @param File to be added to list
	 */
	public void addReferenced(ClassFile newFile) {
		referenced.add(newFile);

	}
	
	/**
	 * Get the name of the Class
	 * @return the name of the class
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get the type of the Class
	 * @return the type of the class
	 */
	public String getType() {
		return type;
	}

	public String getAbsolutePath() {
		return path;
	}
}
