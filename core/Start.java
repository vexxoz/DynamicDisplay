package core;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Start {
	public static void main(String[] args) {
		try {
			LibraryReader read = new LibraryReader("/Users/blake/eclipse-workspace/PackageDependency/", "Test.java");
			read.searchLibrary();
		} catch (FileNotFoundException e) {
			System.out.println("Cannot find file!");
		}catch (IOException e) {
			System.out.println("Cannot read file!");
		}
	}
	
}
