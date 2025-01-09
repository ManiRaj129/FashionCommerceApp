package src.PublicRelations.resources;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class PRReviewEmployeeGenerator {

    public static void main(String[] args) {
        // Define divisions and positions
        Map<String, List<String>> divisions = new LinkedHashMap<>();
        divisions.put("Content Review", Arrays.asList("Content Quality Analyst", "Copy Editor", "Style Guide Reviewer"));
        divisions.put("Brand Standards", Arrays.asList("Brand Compliance Officer", "Design Standards Reviewer", "Messaging Strategist"));
        divisions.put("Event Evaluation", Arrays.asList("Event Feedback Analyst", "Logistics Quality Inspector", "Guest Experience Auditor"));

        // File path
        String filePath = "src/PublicRelations/resources/demo_100_pr_review_employees.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            int employeeCount = 1;

            // Define first and last names
            String[] firstNames = {"Olivia", "Ethan", "Sophia", "Liam", "Isabella", "Noah", "Ava", "Lucas", "Mia", "Mason"};
            String[] lastNames = {"Taylor", "Anderson", "Thomas", "White", "Harris", "Martin", "Thompson", "Garcia", "Martinez", "Robinson"};

            // Create a balanced distribution of employees
            int employeesPerPosition = 100 / divisions.size() / 3; // Balanced across divisions and positions

            for (Map.Entry<String, List<String>> division : divisions.entrySet()) {
                String divisionName = division.getKey();
                List<String> positions = division.getValue();

                // Balance employees for each position within the division
                for (String position : positions) {
                    for (int i = 0; i < employeesPerPosition; i++) { // Balanced employee count per position
                        String employeeID = String.format("DoPR_Employee_Review_%04d", employeeCount++);

                        // Generate random name
                        String firstName = firstNames[(int) (Math.random() * firstNames.length)];
                        String lastName = lastNames[(int) (Math.random() * lastNames.length)];
                        String fullName = firstName + " " + lastName;

                        // Generate random rating and years of experience
                        double rating = 4.0 + (employeeCount % 10) * 0.05;
                        int yearsOfExperience = (employeeCount % 10) + 1;

                        // Generate the employee data
                        String output = String.format(
                                "%s:\nname/%s\ndivision/%s\nposition/%s\ndepartment/Department of Public Relations\nrating/%.1f\nyearsOfExperience/%d\npreviousAssignment/free\ncurrentAssignment/free\n\n",
                                employeeID,
                                fullName,
                                divisionName,
                                position,
                                rating,
                                yearsOfExperience
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