package core;
import java.util.ArrayList;

public class ParentFile {

	private String name;
	private ArrayList<String> dependants;
	
	public ParentFile(String nameIn) {
		name = nameIn;
		dependants = new ArrayList<String>();
	}
	
	/**
	 * Add a filename that depends on the parent file
	 * @param nameIn name of file
	 */
	public void add(String nameIn) {
		dependants.add(nameIn);
	}
	
	/**
	 * Get the list of files that depend on the parent file
	 * @return An ArrayList of file names
	 */
	public ArrayList<String> getDepends(){
		return dependants;
	}
	
	/**
	 * Get the name of the parent file
	 * @return Name of parent file
	 */
	public String getName() {
		return name;
	}
}
