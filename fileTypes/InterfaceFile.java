package fileTypes;

public class InterfaceFile extends ClassFile {
	
	private String type;
	
	/**
	 * Constructor for File of type Interface
	 * @param nameIn name of the file
	 */
	public InterfaceFile(String nameIn, String path){
		super(nameIn, path);
		type = "Interface";
	}
}
