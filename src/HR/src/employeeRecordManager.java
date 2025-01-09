package src.HR.src;


import java.io.*;
import java.nio.file.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Sam Gumm
 */
public class employeeRecordManager {
    Map<String, Map<String, String>> data = new LinkedHashMap<>();
    private final fileStorageHR storageHR = new fileStorageHR();
    valueHandling valueHandler = new valueHandling();

    /*
        TODO:
     */

    private int generateID() throws IOException {
        System.out.println("generating ID...");

        //checking to make sure folder is there
        int idCounter;
        Path filePath;
        try {
            filePath = Paths.get(storageHR.getDefault_filepath_idGeneratorStorage().toString(), "idContainer.txt");
        } catch (Exception e) {
            throw new FileNotFoundException("File not found");
        }

        //read integer then increment and write to file
        try(BufferedReader br = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line = br.readLine();
            idCounter = Integer.parseInt(line);
            CharSequence chars = ++idCounter + "";
            Files.writeString(filePath, chars);
        }
        return idCounter;
    }



    /**
     * Adds an Employee to record, then persistently stores it in "empID".txt
     */
    // Add a new employee
    public void addEmployee() throws IOException {
        //TODO: have counter that increments for employeeID
        String name;
        String employeeId;
        String initialDep;
        String position;
        String employmentStatus;
        String salary;

        //Name
        System.out.println("Enter employee name: ");

        name = valueHandler.inputValidator(true);

        //ID
        employeeId = String.valueOf(generateID());

        //Department
        Department department;
        System.out.println("Enter employee department: ");
        for(int i = 0; i < Department.values().length; i++) {
            System.out.println(Department.values()[i].name());
        }
        initialDep = valueHandler.inputValidator(true);

        //handling Human Resources
        if (initialDep.contains("Human Resources")
                || initialDep.contains("human resources")) {
            department = Department.HUMAN_RESOURCES;
        } else {
            department = Department.valueOf(initialDep.toUpperCase());
        }

        //Position
        System.out.println("Enter employee position: ");
        position = valueHandler.inputValidator(true);

        //Status
        System.out.println("Enter employee employment status (i.e. onboarding): ");
        employmentStatus = valueHandler.inputValidator(true).toUpperCase();

        //Salary
        System.out.println("Enter employee salary: ");
        salary = valueHandler.inputValidator(true);

        //adding employee
        Map<String, String> employeeObject = new LinkedHashMap<>();
        employeeObject.put("name", name);
        employeeObject.put("employeeID", employeeId);
        employeeObject.put("department", department.toString());
        employeeObject.put("position", position);
        employeeObject.put("status", employmentStatus);
        employeeObject.put("salary", String.valueOf(salary));
        data.put(employeeId, employeeObject);
        storageHR.poorJarser.setRepositoryStrings(data);
        String filepathToEmployeeStorage = String.valueOf(storageHR.getEmployeeStoragePath(String.valueOf(department)));
        storageHR.poorJarser.writeToTextFile(filepathToEmployeeStorage + "/" + employeeId + ".txt");
        System.out.println("Employee " + employeeId + " has been added to the storage");
    }


    /**
     * Removes employee FILE from employeeStorage repo, uses fileStorageHR filepath to find file.
     * Also attempts to remove Employee from LinkedHashMap record if it exists there as well.
     *
     * @param employeeID the EmployeeID to be removed
     *
     */
    public void removeEmployee(String employeeID) throws Exception {
        //remove employee from data LinkedHashMap if it contains the id
        if(data.containsKey(employeeID)) {
            try {
                data.remove(employeeID);
                storageHR.poorJarser.setRepositoryStrings(data);
            } catch (Exception e) {
                throw new Exception("Error in removeEmployee when removing found ID from LinkedHashMap<>(): \n" + e.getMessage());
            }
        }

        String filepath = String.valueOf(findEmployeeFile(employeeID));
        if(filepath == null) {
            throw new NullPointerException("filepath from removeEmployee is null");
        }

        try {
            storageHR.deleteFile(filepath);
        } catch (Exception e) {
            throw new Exception("Error in removeEmployee when deleting file from repository with file path: "
                    + filepath + "\n" + e.getMessage());
        }
    }

    /**
     *
     * @param employeeID String ID of the employee to be moved
     * @param department Department object of the department to move the employee to
     * @throws IOException if file input is invalid
     */
    private void moveEmployee(String employeeID, Department department) throws IOException {
        // Find the current employee file path
        Path currentEmployeeFile = findEmployeeFile(employeeID);
        if (currentEmployeeFile == null || !Files.exists(currentEmployeeFile)) {
            throw new FileNotFoundException("Employee file not found: " + employeeID + ".txt");
        }

        // Get the path to the new Department folder
        Path newDepartmentFolder = storageHR.getEmployeeStoragePath(department.toString().toUpperCase());
        if (!Files.exists(newDepartmentFolder)) {
            throw new FileNotFoundException("Department folder not found: " + department.toString().toUpperCase());
        }

        // Construct the destination path for the candidate file
        Path destinationFile = newDepartmentFolder.resolve(employeeID + ".txt");

        // Move the candidate file to the new status folder
        Files.move(currentEmployeeFile, destinationFile, StandardCopyOption.REPLACE_EXISTING);

        System.out.println("Employee was successfully moved to " + destinationFile);
    }

    /**
     * Takes the EmployeeID of the Employee object to be changed, then checks the employeeStorage
     * folder for the given ID. If it finds one, logic executes for updating the employee file, otherwise
     * it calls itself again to receive a valid ID.
     *
     * @param employeeID the associated ID of the Employee
     */
    public void updateEmployee(String employeeID) throws Exception {
        String filepath = null;

        try {
            filepath = (findEmployeeFile(employeeID)).toString();
        } catch (IOException e) {
            System.out.println("Employee file not found: " + employeeID + ".txt");
            System.out.println("Enter new employee id: ");
            employeeID = valueHandler.inputValidator(true);
            updateEmployee(employeeID);
        }

        //read from Employee file
        storageHR.poorJarser.processTextFile(filepath);

        //initialize data from current repo
        Map<String, Map<String, String>> data = storageHR.poorJarser.getRepositoryStringMap();

        //make sure data is not initialized with nothing
        if(data.isEmpty()) {
            throw new Exception("data was not initialized properly: \n");
        }

        //initialize candidateObject to modify
        Map<String, String> employeeObject = data.get(employeeID);
        if(employeeObject == null) {
            employeeObject = new LinkedHashMap<>();
        }
        removeEmployee(employeeID);

        //Get input
        System.out.println("Enter new employee Department: ");
        for(int i = 0; i < Department.values().length; i++) {
            System.out.println(Department.values()[i].name());
        }
        Department department = Department.valueOf(valueHandler.inputValidator(true).toUpperCase());

        System.out.println("Enter in new employee position: ");
        String position = valueHandler.inputValidator(true);

        System.out.println("Enter in new employee Status: ");
        String status = valueHandler.inputValidator(true);

        System.out.println("Enter in new employee salary: ");
        String salary = valueHandler.inputValidator(true);

        employeeObject.put("employeeID", employeeID);
        employeeObject.put("name", employeeObject.get("name"));
        employeeObject.put("department", department.toString());
        employeeObject.put("position", position);
        employeeObject.put("status", status);
        employeeObject.put("salary", salary);

        data.put(employeeID, employeeObject);

        storageHR.poorJarser.setRepositoryStrings(data);
        storageHR.poorJarser.writeToTextFile(filepath);

        moveEmployee(employeeID, department);
    }

    /**
     * @param departmentName the Department to iterate through.
     */
    public void displayEmployeesByDepartment(Department departmentName) throws Exception {
        try {
            String statusDirName = String.valueOf(departmentName).toUpperCase();

            Path statusDir = storageHR.getEmployeeStoragePath(statusDirName);

            if(statusDir == null) {
                throw new NullPointerException("statusDir is null");
            }

            if(!Files.exists(statusDir) || !Files.isDirectory(statusDir)) {
                System.out.println("No employees found in " + departmentName);
                return;
            }

            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(statusDir, "*.txt")) {
                boolean employeesFound = false;
                for (Path path : directoryStream) {
                    employeesFound = true;
                    storageHR.loadFileAndPrint(path.toString()); // Pass full path as string
                }
                if(!employeesFound) {
                    System.out.println("No candidates found with status: " + departmentName);
                }
            }
        } catch (Exception e) {
            throw new Exception("displayEmployeesByDepartment failed: \n" + e.getMessage(), e);
        }
    }

    /**
     * TODO: add description
     * @param employeeID String ID of the employee to be found
     * @return a Path object containing the path to the employee File
     * @throws IOException if file encountered is invalid
     */
    public Path findEmployeeFile(String employeeID) throws IOException {
        Path base = storageHR.getDefault_filepath_employeeStorage();
        String employeeFileName = employeeID + ".txt";

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(base)) {
            for (Path departmentDir : directoryStream) {
                if (Files.isDirectory(departmentDir)) { // Ensure it's a directory
                    Path employeeFile = departmentDir.resolve(employeeFileName);
                    System.out.println(employeeFile);
                    if (Files.exists(employeeFile)) {
                        return employeeFile;
                    }
                }
            }
        }
        System.out.println("No employee found with id: " + employeeID + "\nPlease try again: ");
        employeeID = valueHandler.inputValidator(true);
        return findEmployeeFile(employeeID);
    }



    /**
     *
     * @param employeeID String ID of the employee to be returned
     * @return complete Employee object created from employee File
     * @throws Exception if text file is invalid
     */
    public Employee getEmployee(String employeeID) throws Exception {
        String filepath;

        filepath = Objects.requireNonNull(findEmployeeFile(employeeID)).toString();
        System.out.println(filepath);
        //read from Employee file
        storageHR.poorJarser.processTextFile(filepath);

        Map<String, Map<String, String>> data = storageHR.poorJarser.getRepositoryStringMap();

        if(data.isEmpty()) {
            throw new Exception("data was not initialized properly: \n");
        }


        //initialize data from current repo
        Map<String, String> employeeObject = data.get(employeeID);

        assert employeeObject != null;

        String name = employeeObject.get("name");
        String empID = employeeObject.get("employeeID");
        Department department = Department.valueOf(employeeObject.get("department").toUpperCase());
        String position = employeeObject.get("position");
        String status = employeeObject.get("status");
        int salary = Integer.parseInt(employeeObject.get("salary"));

        return new Employee(empID, name, department, position, status, salary);
    }
}
