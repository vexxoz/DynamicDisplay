package core;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class Main {

	static String[] searchItems = {"include", "require"}; // the terms used in files to denote dependency
	static Hashtable<String, ParentFile> listOfDepends;
	static int frameVert;
	static int frameHorz;
	static ArrayList<String> listOfDependents; 
	public static void main(String[] args) throws FileNotFoundException, InterruptedException {
		
		String path = "";
		String[] filesInDir;
		frameVert = 1000;
		frameHorz = 1000;
		listOfDepends = new Hashtable<String, ParentFile>();
		listOfDependents = new ArrayList<String>();		
		
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
						
						// get just the name of the file, ignore paths
						File tempFile = new File(newFile);
						addEdge(tempFile.getName(), filename);
					}

					
				}
				fileReader.close();
			}
		}
		
				
	
//		 print out results
		System.out.println("List of Parents:");
		for(ParentFile parent : listOfDepends.values()){
			System.out.println(parent.getName());
//			System.out.print("Dependents:");
//			for(String name : parent.getDepends()) {
//				System.out.print(" " + name + " ");
//			}
		}
		
		boolean isTrue = true;
		Scanner input = new Scanner(System.in);
		while(isTrue) {
			System.out.println("Type file name to see dependents");
			System.out.print("Filename: ");
			String response = input.nextLine();
			// if input is a known file
			if(listOfDepends.containsKey(response)) {
				System.out.println("Showing " + response);
				createGraph(response);
			}else if(response.equalsIgnoreCase("exit")){
				isTrue = false;
				// exit all threads
				
			}else {
				System.out.println("Unknown File!");
			}
		}
		input.close();

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
	
	/**
	 * Create a graph of dependencies for the selected file
	 * @param focusFile filename to create depenedency graph of
	 */
	private static void createGraph(String focusFile) {
		GraphDraw frame = new GraphDraw("Test Window");
		frame.setSize(frameHorz,frameVert);
		
		frame.setVisible(true);

		
		
		//add parent to screen
		frame.addNode(listOfDepends.get(focusFile).getName(), frameHorz/2, frameVert/2);
		
		int dependendSize = listOfDepends.get(focusFile).getDepends().size();
		// radius of circle
		int r = 400;
		// add child nodes
		for(int i=0;i<listOfDepends.get(focusFile).getDepends().size();i++){
			String child = listOfDepends.get(focusFile).getDepends().get(i);
			// add it to the list so it doesnt get repeated (for future use with multiple focus files
			listOfDependents.add(child);
			// add node
			int x = (int) ((frameHorz/2) + r * Math.cos(2 * Math.PI * i / dependendSize));
		    int y = (int) ((frameVert/2) + r * Math.sin(2 * Math.PI * i / dependendSize)); 
		    int childSize;
		    if(listOfDepends.containsKey(child)) {
		    	childSize = listOfDepends.get(child).getDepends().size();
		    }else {
		    	childSize = 0;
		    }
			
	    	frame.addNode(child + ", " + childSize, x, y);
			//add edge
			frame.addEdge(0,i+1);
		}
	
	}
	


}