package src.HR.src;
import src.TextEditor.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class fileStorageHR {

    private final Path default_filepath_employeeStorage = Paths.get("src", "HR", "repository", "employeeStorage");
    private final Path default_filepath_candidateStorage = Paths.get("src", "HR", "repository", "candidateStorage");
    private final Path default_filepath_idGeneratorStorage = Paths.get("src", "HR", "repository", "idGenerator");

    String filepath = "";
    public PoorTextEditor poorJarser = new PoorTextEditor(); //haha haha

    public fileStorageHR() {}

    public String getFilepath() {
        return filepath;
    }

    public Path getDefault_filepath_employeeStorage() {
        return default_filepath_employeeStorage;
    }

    public Path getDefault_filepath_candidateStorage() { return default_filepath_candidateStorage; }

    public Path getDefault_filepath_idGeneratorStorage() { return default_filepath_idGeneratorStorage; }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    /**
     * Deletes a given file, expects to be given the filepath however
     * @param filename name of File to be deleted, must contain file extension
     */
    public void deleteFile(String filename) throws Exception {
        try {
            Path filePath = Paths.get(getFilepath(), filename);
            File file = filePath.toFile();

            if (file.exists()) {
                if (!file.delete()) {
                    throw new Exception("Failed to delete " + filename);
                }
            } else {
                throw new FileNotFoundException("File not found: " + file.getAbsolutePath());
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    /**
     * @param folderName the name of the Department folder to be accessed
     * @return returns a String representation of the folderPath to the Department Folder
     * @throws IOException error checking
     */
    public Path getEmployeeStoragePath(String folderName) throws IOException {
        Path folderPath = Paths.get("src", "HR", "repository", "employeeStorage", folderName.toUpperCase());
        if (!Files.exists(folderPath)) {
            throw new FileNotFoundException("Folder not found: " + folderName.toUpperCase());
        }

        return folderPath.toAbsolutePath();
    }

    /**
     * Works by using the working directory saved at runtime to create the folderPath
     *  it then tries to find the absolute path of the folder, in order to work across
     *  different machines.
     * @param folderName the specific Status folder to set the path to
     * @return returns a Path object that contains the absolute path of the defined folder
     * @throws IOException error checking
     */
    public Path getCandidateStoragePath(String folderName) throws IOException {
        Path folderPath = Paths.get("src", "HR", "repository", "candidateStorage", folderName.toUpperCase());
        if (!Files.exists(folderPath)) {
            throw new FileNotFoundException("Folder not found: " + folderName.toUpperCase());
        }

        return folderPath.toAbsolutePath();
    }

    /**
     *
     * @param filePath the path to the file to be printed out to console
     * @throws Exception error checking
     */
    public void loadFileAndPrint(String filePath) throws Exception {
        try {
            System.out.println("Attempting to load: " + filePath);
            File file = new File(filePath);
            System.out.println("Loading file: " + file.getAbsolutePath());

            // Check if the file exists
            if (!file.exists()) {
                throw new FileNotFoundException("File not found: " + file.getAbsolutePath());
            }

            try (Scanner scanner = new Scanner(file)) {
                while(scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    System.out.println(line);
                }
            }
        } catch (Exception e) {
            throw new Exception("Error encountered in loadFileAndPrint with file: "
                    + filePath + "\n" + e.getMessage(), e);
        }
    }
}
