package src.Security.resources;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SecurityEmployeeGenerator {

    public static void main(String[] args) {
        // Define divisions and positions
        Map<String, List<String>> divisions = new LinkedHashMap<>();
        divisions.put("Access Control", Arrays.asList("Access Control Officer", "Gatekeeper", "Security Receptionist", "Badge Administrator"));
        divisions.put("Patrol", Arrays.asList("Patrol Officer", "Roving Security Guard", "Parking Lot Security Specialist", "Bicycle Patrol Officer"));
        divisions.put("Surveillance", Arrays.asList("CCTV Operator", "Surveillance Analyst", "Security System Technician", "Data Analyst"));
        divisions.put("Emergency Response", Arrays.asList("Fire Safety Officer", "Medical Response Officer", "Evacuation Coordinator", "Emergency Drill Planner"));
        divisions.put("Investigation", Arrays.asList("Investigator", "Evidence Handler", "Security Auditor", "Forensics Specialist"));

        // Define expertise per position
        Map<String, List<String>> expertise = new LinkedHashMap<>();
        expertise.put("Access Control Officer", Arrays.asList("Badge Scanning", "Monitoring", "Access Management"));
        expertise.put("Gatekeeper", Arrays.asList("Access Supervision", "Physical Security", "Gate Monitoring"));
        expertise.put("Security Receptionist", Arrays.asList("Visitor Handling", "Credential Issuance", "Front Desk Security"));
        expertise.put("Badge Administrator", Arrays.asList("Badge Issuance", "System Updates", "Entry Control Management"));
        expertise.put("Patrol Officer", Arrays.asList("Routine Patrols", "Incident Reporting", "Area Monitoring"));
        expertise.put("Roving Security Guard", Arrays.asList("Roving Patrols", "Night Surveillance", "Activity Monitoring"));
        expertise.put("Parking Lot Security Specialist", Arrays.asList("Parking Surveillance", "Anti-Theft", "Vehicle Monitoring"));
        expertise.put("Bicycle Patrol Officer", Arrays.asList("Rapid Mobility Patrol", "Area Surveillance", "Threat Detection"));
        expertise.put("CCTV Operator", Arrays.asList("Camera Monitoring", "Threat Detection", "Activity Review"));
        expertise.put("Surveillance Analyst", Arrays.asList("Activity Review", "Data Analysis", "Camera System Optimization"));
        expertise.put("Security System Technician", Arrays.asList("System Maintenance", "Equipment Troubleshooting", "Network Surveillance"));
        expertise.put("Data Analyst", Arrays.asList("Data Processing", "Trend Analysis", "Incident Reporting"));
        expertise.put("Fire Safety Officer", Arrays.asList("Fire Prevention", "Safety Inspections", "Evacuation Planning"));
        expertise.put("Medical Response Officer", Arrays.asList("First Aid", "Emergency Care", "Incident Stabilization"));
        expertise.put("Evacuation Coordinator", Arrays.asList("Evacuation Planning", "Crowd Control", "Safety Enforcement"));
        expertise.put("Emergency Drill Planner", Arrays.asList("Scenario Planning", "Staff Training", "Drill Execution"));
        expertise.put("Investigator", Arrays.asList("Incident Investigation", "Evidence Collection", "Report Writing"));
        expertise.put("Evidence Handler", Arrays.asList("Evidence Processing", "Chain of Custody", "Forensic Analysis"));
        expertise.put("Security Auditor", Arrays.asList("Policy Review", "Risk Assessment", "Compliance Checks"));
        expertise.put("Forensics Specialist", Arrays.asList("Digital Forensics", "Crime Scene Analysis", "Evidence Recovery"));

        // File path
        String filePath = "src/security/resources/demo_100_security_employees.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            int employeeCount = 1;

            // Define first and last names
            String[] firstNames = {"John", "Jane", "Alex", "Taylor", "Jordan", "Chris", "Morgan", "Casey", "Ryan", "Pat"};
            String[] lastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Martinez", "Lopez"};

            // Create a balanced distribution of employees
            int employeesPerPosition = 100 / divisions.size() / 4; // This ensures that the employees are distributed evenly across divisions and positions

            for (Map.Entry<String, List<String>> division : divisions.entrySet()) {
                String divisionName = division.getKey();
                List<String> positions = division.getValue();

                // Balance employees for each position within the division
                for (String position : positions) {
                    for (int i = 0; i < employeesPerPosition; i++) { // Balanced employee count per position
                        String employeeID = String.format("DoS_Employee_%04d", employeeCount++);

                        // Generate random name
                        String firstName = firstNames[(int) (Math.random() * firstNames.length)];
                        String lastName = lastNames[(int) (Math.random() * lastNames.length)];
                        String fullName = firstName + " " + lastName;

                        // Get expertise for the current position and replace spaces with underscores
                        List<String> positionExpertise = expertise.get(position);

                        // Select two random expertise
                        Set<String> selectedExpertise = new HashSet<>();
                        Random rand = new Random();
                        while (selectedExpertise.size() < 2) {
                            selectedExpertise.add(positionExpertise.get(rand.nextInt(positionExpertise.size())));
                        }

                        // Replace spaces with underscores in selected expertise
                        List<String> modifiedExpertise = new ArrayList<>();
                        for (String exp : selectedExpertise) {
                            modifiedExpertise.add(exp.replace(" ", "_"));
                        }
                        String expertiseList = String.join(" ", modifiedExpertise);

                        // Generate the employee data
                        String output = String.format(
                                "%s:\nname/%s\ndivision/%s\nposition/%s\ndepartment/Security\nexpertise/%s\nrating/%.1f\nyearsOfExperience/%d\npreviousAssignment/free\ncurrentAssignment/free\n\n",
                                employeeID,
                                fullName,
                                divisionName,
                                position,
                                expertiseList,
                                4.0 + (employeeCount % 10) * 0.05,
                                (employeeCount % 15) + 1
                        );
                        writer.write(output);
                    }
                }
            }

            System.out.println("File created successfully: " + filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}