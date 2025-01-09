package src.HR.src;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class hiringProcess {
    /*
    TODO:
     - set up the hiring process
     - set up the folder access
     - set up the folder storage system
        - have txt files that will be stored into the corresponding day of the week
        - the txt files will store what current candidates are going to be interviewed that day
        - ...
    Thought process:
        Need to have counter for interview ID
        -> have this stored in a txt file somewhere, everytime the counter increments, the txt counter is
            also incremented;
        Have interviewer folders, and ability to create and remove the folders
        each folder has days of the week as folders inside of them
        each day of the week has txt files that represent time slots, when a interview is scheduled,
            it gets saved as, for example a 12:15 slot, (maybe have it be appended to the InterviewID 000_1215

            1215_1.txt
            //TODO: INITIALIZE THE CARDS EXACTLY LIKE THIS
            //
                Time: 1215
                Candidate: Someone else
                Interviewer: Someone
                Notes: blank
                                          //

        the operator can choose to see all timeslots currently created in a print out fashion, with days as headers
        they can also type in the timeslot (maybe have this changed to candidate name or something when sent to complete)
        there is a completed interviews folder where the user opens up a timeslot, and then chooses to send it to complete
            this opens up the option for them to add in any notes that they may have, and then saves the candidate in
            this format in the completed folder
            someone.txt
            //
               Candidate Name: Something
               Interviewer: Something else
               Notes: //something here idk
               Recommendation: Be sent to such and such department as this position idk
                                                                                          //
        the user should be able to get full or individual print outs of all folders available, including their contents

     */

    /*
    STEPS:
    - create interview from info
    - assign to interviewer
    - set timeslot
    - repeat until finished
    > interview is had
    > interviewer records notes about interview
    - insert notes of interviewer into the interview txt file
    - move the txt file to Completed
     */

    /*
    Necessities:
    - See all interviews arranged by interviewer
    - See only one interviewer's timeslots
    - See one timeslot's internals
    - Global counter for interviewID
     */

    private final Path folderPathSchedules = Paths.get("src", "HR", "repository", "scheduleStorage");
    private final Path folderPathIDs = Paths.get("src", "HR", "repository", "scheduleStorage", "idStorage");
    valueHandling valueHandler = new valueHandling();
    fileStorageHR storage = new fileStorageHR();
    candidateRecordManager candHandler = new candidateRecordManager();
    employeeRecordManager empHandler = new employeeRecordManager();

    /**
     *
     * @throws IOException if File is invalid
     */
    public void createInterview() throws IOException {
        /*
        TODO:
            - alter this to create a folder for an interviewer
                and upon assigning a card to them, either the folder exists and it is deposited there
                or it creates a new folder after prompting the user to confirm that the chosen interviewer is correct
         */
        String interviewTime;
        String candidateName;
        String data = "";
        String interviewer;
        String notes;
        System.out.println("Please enter Interview Time (HH:MM)");
        interviewTime = valueHandler.inputValidator(true);


        //candidate name
        System.out.println("Please enter Candidate Name (First Last):");
        candidateName = valueHandler.inputValidator(true);
        data += "Candidate Name: " + candidateName + "\n";


        //assign interviewer
        System.out.println("Please choose Interviewer to assign:");
        //File folder = folderPathSchedules.toFile();
        interviewer = valueHandler.inputValidator(true);
        data += "Interviewer: " + interviewer + "\n";

        //add notes
        System.out.println("Please enter notes: ");
        notes = valueHandler.inputValidator(true);
        data += "Notes: " + notes + "\n";

        System.out.println("generating ID...");

        //checking to make sure folder is there
        int idCounter;
        Path filePath;
        try {
            filePath = Paths.get(folderPathIDs.toString(), "idFile.txt");
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

        //Save file to scheduleStorage/<assignedInterviewer>
        String interviewerFolder = folderPathSchedules + "/" + interviewer;
        Path interviewerFolderPath = Paths.get(interviewerFolder);
        if(!interviewerFolderPath.toFile().exists()) {
            Files.createDirectory(interviewerFolderPath);
        }
        File interview = new File(interviewerFolder, interviewTime + "_" + idCounter + ".txt");
        Files.writeString(interview.toPath(), data);
        if (interview.createNewFile()) {
            System.out.println(interviewTime + "_" + idCounter + ".txt Created");
        }
        System.out.println("interview slot " + interviewTime + "_" + idCounter + ".txt created!");
    }

    /**
     *
     * @throws IOException if txt file that is being looked for doesn't exist or is invalid
     */
    public void editInterview() throws IOException {
    /* TODO
        - Trawl through scheduleStorage until file is found
        - Take user input to replace:
            1. Interview Time
            2. Candidate Name
            3. Interviewer on File
            4. Interview Notes
        - Steps:
            - read file and store to String or data structure
            - iterate through and change the marked section
            - write updated string back to file
    */

        System.out.println("Please enter interview ID to edit:");
        String interviewID = valueHandler.inputValidator(true);

        if(!Files.exists(folderPathSchedules) || !Files.isDirectory(folderPathSchedules)) {
            System.out.println("No schedule folder found");
            return;
        }

        // Find the interview file anywhere inside folderPathSchedules
        Path filePath = findInterviewFile(folderPathSchedules, interviewID);
        if (filePath == null) {
            System.out.println("No file found for interview ID: " + interviewID);
            return;
        }

        System.out.println("Found file: " + filePath.getFileName());

        // Read file into a list of [key, value] pairs
        List<String[]> data = new ArrayList<>();
        try (Scanner fileScanner = new Scanner(filePath)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(": ");
                data.add(parts);
            }
        }

        if (data.isEmpty()) {
            System.out.println("No data found in the interview file.");
            return;
        }

        while (true) {
            System.out.println("Please choose operation: [E]dit, [Q]uit:");
            String operation = valueHandler.inputValidator(false);

            if (operation.equalsIgnoreCase("E")) {
                System.out.println("Choose what to edit: \n[I]nterviewer Assigned\n[C]andidate Name\n[N]otes\n[E]dit Resume Notes");
                String userInput = valueHandler.inputValidator(false);

                switch (userInput.toUpperCase()) {
                    case "I" -> {
                        System.out.println("Please enter new Interviewer: ");
                        String newInterviewer = valueHandler.inputValidator(true);
                        updateDataField(data, "Interviewer", newInterviewer);
                        writeDataToFile(filePath, data);
                    }
                    case "C" -> {
                        System.out.println("Please enter new Candidate Name: ");
                        String newCandidate = valueHandler.inputValidator(true);
                        updateDataField(data, "Candidate Name", newCandidate);
                        writeDataToFile(filePath, data);
                    }
                    case "N" -> {
                        System.out.println("Please enter new Notes: ");
                        String newNotes = valueHandler.inputValidator(true);
                        updateDataField(data, "Notes", newNotes);
                        writeDataToFile(filePath, data);
                    }
                    case "E" -> enterResumeData(interviewID);

                    default -> System.out.println("Invalid choice.");
                }

            } else if (operation.equalsIgnoreCase("Q")) {
                break;
            } else {
                System.out.println("Incorrect input...");
            }
        }
    }

    /**
     * Recursively searches through the given directory and subdirectories to find a file
     * whose filename contains the given interviewID.
     *
     * @param root The root directory to start searching from.
     * @param interviewID The interview ID to look for.
     * @return Path to the matching file, or null if not found.
     */
    private Path findInterviewFile(Path root, String interviewID) throws IOException {
        try (Stream<Path> files = Files.walk(root)) {
            return files
                    .filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().contains(interviewID))
                    .findFirst()
                    .orElse(null);
        }
    }

    /**
     *
     * @param data
     * @param fieldName
     * @param newValue
     */
    private void updateDataField(List<String[]> data, String fieldName, String newValue) {
        boolean foundField = false;
        for (String[] datum : data) {
            if (datum.length > 0 && datum[0].equalsIgnoreCase(fieldName)) {
                if (datum.length > 1) {
                    datum[1] = newValue;
                } else {
                    // If for some reason there's no value slot, expand the array or handle accordingly
                    datum = Arrays.copyOf(datum, 2);
                    datum[1] = newValue;
                }
                foundField = true;
                break;
            }
        }
        if (!foundField) {
            System.out.println("Was not able to find " + fieldName + " section...");
        }
    }

    /**
     *
     * @param filePath
     * @param data
     * @throws IOException
     */
    private void writeDataToFile(Path filePath, List<String[]> data) throws IOException {
        StringBuilder newPayload = new StringBuilder();
        for (String[] datum : data) {
            if (datum != null && datum.length > 0) {
                newPayload.append(datum[0]);
                if (datum.length > 1) {
                    newPayload.append(": ").append(datum[1]);
                }
                newPayload.append("\n");
            }
        }

        Files.writeString(filePath, newPayload.toString());
        System.out.println("Finished writing process: " + newPayload);
    }

    private void moveInterviewToComplete(String interviewID) {
        /* TODO
            - takes interviewID from user, finds the txt file, then moves it to the completed folder after:
                - changing the name to be the candidate's name appended by interviewID
                Everything else related to the handover process will be accomplished by other methods
         */
    }


    /**
     *
     * @param interviewID String ID of the interview to print to console
     * @throws IOException if file is missing or invalid
     */
    public void printInterview(String interviewID) throws IOException {
        try(Stream<Path> stream = Files.walk(folderPathSchedules)) {
            stream.forEach(p -> {
                if (Files.isRegularFile(p) && p.toString().contains("_" + interviewID)) {
                    try {
                        Files.lines(p).forEach(System.out::println);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }

    /**
     *
     * @throws IOException
     */
    public void printAllInterviews() throws IOException {
        System.out.println("All Interviews");
        try(Stream<Path> stream = Files.walk(folderPathSchedules)) {
            stream.forEach(p -> {
                if(Files.isDirectory(p) && !p.equals(folderPathSchedules) && !p.equals(folderPathIDs)) {
                    if (p.toString().contains("Complete")) {
                        System.out.println("Complete Interviews: ");
                    }
                    else {
                        System.out.println("Interviewer: " + p.toFile().getName());
                    }
                }
                else if (Files.isRegularFile(p) && !p.equals(folderPathSchedules)
                && p.toString().toLowerCase().endsWith(".txt") && !p.toString().contains("idFile")) {
                    System.out.println("  -> " + p.getFileName());
                }
            });
        }
    }

    /**
     *
     * @param interviewerName
     * @throws IOException
     */
    public void printAllInterviewsByInterviewer(String interviewerName) throws IOException {
        // Walk through the schedule directories and try to find the one that matches the interviewerName
        try (Stream<Path> stream = Files.walk(folderPathSchedules)) {
            Optional<Path> interviewerFolder = stream
                    .filter(Files::isDirectory)
                    .filter(p -> !p.equals(folderPathSchedules) && !p.equals(folderPathIDs))
                    .filter(p -> p.getFileName().toString().contains(interviewerName))
                    .findFirst();

            if (interviewerFolder.isPresent()) {
                Path folder = interviewerFolder.get();
                System.out.println("Interviewer: " + folder.getFileName());

                // list all interview files in found directory
                try (Stream<Path> fileStream = Files.list(folder)) {
                    fileStream
                            .filter(Files::isRegularFile)
                            .filter(p -> p.toString().toLowerCase().endsWith(".txt"))
                            .filter(p -> !p.toString().contains("idFile"))
                            .forEach(p -> System.out.println("  -> " + p.getFileName()));
                }
            } else {
                System.out.println("No folder found for interviewer: " + interviewerName);
            }
        }
    }


    public void enterResumeData(String candidateID) {
        // TODO:
        //    - Take input from user for what candidate file to grab
        //    - Start taking input from user for resume data
        //    - Append or update candidate file’s resume notes

        Path candidateDir = storage.getDefault_filepath_candidateStorage();

        if (!Files.exists(candidateDir) || !Files.isDirectory(candidateDir)) {
            System.out.println("No candidate storage directory found.");
            return;
        }

        try {
            // find path to candidate
            Path candidatePath = candHandler.findCandidateFile(candidateID);

            if (candidatePath == null || !Files.exists(candidatePath)) {
                System.out.println("Candidate file not found for ID: " + candidateID);
                return;
            }

            System.out.println("Candidate file found: " + candidatePath);

            // Read the entire file content
            List<String> lines = Files.readAllLines(candidatePath, StandardCharsets.UTF_8);

            // Check if `Key Resume Notes:` line already exists
            int notesIndex = -1;
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).trim().equalsIgnoreCase("Key Resume Notes:")) {
                    notesIndex = i;
                    break;
                }
            }

            // If notes section found, remove any existing notes below it
            // until an empty line or end of file.
            if (notesIndex != -1) {
                // Remove old notes (every line after 'Key Resume Notes:' until next blank line or file end)
                int removeStart = notesIndex + 1;
                while (removeStart < lines.size() && !lines.get(removeStart).trim().isEmpty()) {
                    lines.remove(removeStart);
                }
            }

            // If not found, append the section at the end of the file
            if (notesIndex == -1) {
                lines.add("");
                lines.add("Key Resume Notes:");
                notesIndex = lines.size() - 1; // This now points to the "Key Resume Notes:" line
            }

            System.out.println("Current resume notes (if any) have been loaded. Enter new resume notes (type 'DONE' on its own line to finish):");

            // Read lines until 'DONE' is encountered
            List<String> newNotes = new ArrayList<>();
            while (true) {
                String line = valueHandler.inputValidator(false);
                if (line.equalsIgnoreCase("DONE")) {
                    break;
                }
                newNotes.add(line);
            }

            if (newNotes.isEmpty()) {
                System.out.println("No new resume notes entered. No changes made.");
                return;
            }

            // Insert the new notes right after `Key Resume Notes:` line
            lines.addAll(notesIndex + 1, newNotes);

            // Add a blank line after notes to separate from other content
            lines.add(notesIndex + 1 + newNotes.size(), "");

            // Write updated file content back
            Files.write(candidatePath, lines, StandardCharsets.UTF_8);

            System.out.println("Resume data updated successfully.");

        } catch (IOException e) {
            System.out.println("Error appending resume data: " + e.getMessage());
        }
    }

    public void readResumeData(String candidateID) {
        Path candidateDir = storage.getDefault_filepath_candidateStorage();

        if (!Files.exists(candidateDir) || !Files.isDirectory(candidateDir)) {
            System.out.println("No candidate storage directory found.");
            return;
        }

        try {
            // find path to candidate
            Path candidatePath = candHandler.findCandidateFile(candidateID);

            if (candidatePath == null || !Files.exists(candidatePath)) {
                System.out.println("Candidate file not found for ID: " + candidateID);
                return;
            }

            System.out.println("Candidate file found: " + candidatePath);

            // Read the entire file content
            List<String> lines = Files.readAllLines(candidatePath, StandardCharsets.UTF_8);

            // Locate the `Key Resume Notes:` line
            int notesIndex = -1;
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).trim().equalsIgnoreCase("Key Resume Notes:")) {
                    notesIndex = i;
                    break;
                }
            }

            // If no notes section found, inform the user
            if (notesIndex == -1) {
                System.out.println("No resume notes found for this candidate.");
                return;
            }

            // Print out all notes following the "Key Resume Notes:" line until a blank line or EOF
            System.out.println("Resume Notes:");
            int printIndex = notesIndex + 1;
            boolean notesFound = false;
            while (printIndex < lines.size() && !lines.get(printIndex).trim().isEmpty()) {
                System.out.println(" - " + lines.get(printIndex));
                notesFound = true;
                printIndex++;
            }

            if (!notesFound) {
                System.out.println("No resume notes entered yet.");
            }

        } catch (IOException e) {
            System.out.println("Error reading resume data: " + e.getMessage());
        }
    }


    public void deleteInterview(String interviewID) {
        // Check if the schedule directory exists
        if(!Files.exists(folderPathSchedules) || !Files.isDirectory(folderPathSchedules)) {
            System.out.println("No schedule folder found");
            return;
        }

        try {
            // Attempt to find the interview file
            Path interviewFile = findInterviewFile(folderPathSchedules, interviewID);
            if (interviewFile == null) {
                System.out.println("No interview file found with ID: " + interviewID);
                return;
            }

            // Delete the file
            Files.delete(interviewFile);
            System.out.println("Interview file with ID " + interviewID + " deleted successfully.");

        } catch (IOException e) {
            System.out.println("Error while deleting interview file: " + e.getMessage());
        }
    }

    public void startInterview() {
        /*
        TODO:
            - start taking input from user
            - have a while loop that breaks when keypress is escape?
            - continues to append new notes to file
            - append total time to file before saving
            - move interview card to Complete folder
            - maybe have automated process that
         */
    }


}
