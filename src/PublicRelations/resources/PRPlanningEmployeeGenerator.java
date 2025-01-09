package src.PublicRelations.resources;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class PRPlanningEmployeeGenerator {

    public static void main(String[] args) {
        // Define divisions and positions
        Map<String, List<String>> divisions = new LinkedHashMap<>();
        divisions.put("Social Media Management", Arrays.asList("Social Media Strategist", "Content Planner", "Campaign Coordinator"));
        divisions.put("Publicity Event Planning", Arrays.asList("Event Logistics Coordinator", "Event Scheduler", "Vendor Liaison"));
        divisions.put("Brand Development", Arrays.asList("Brand Strategist", "Creative Planner", "Engagement Coordinator"));

        // File path
        String filePath = "src/PublicRelations/resources/demo_100_pr_planning_employees.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            int employeeCount = 1;

            // Define first and last names
            String[] firstNames = {"Emily", "Michael", "Sarah", "David", "Jessica", "Daniel", "Ashley", "Matthew", "Amanda", "Andrew"};
            String[] lastNames = {"Clark", "Lewis", "Walker", "Allen", "Young", "Hill", "King", "Scott", "Green", "Adams"};

            // Create a balanced distribution of employees
            int employeesPerPosition = 100 / divisions.size() / 3; // Balanced across divisions and positions

            for (Map.Entry<String, List<String>> division : divisions.entrySet()) {
                String divisionName = division.getKey();
                List<String> positions = division.getValue();

                // Balance employees for each position within the division
                for (String position : positions) {
                    for (int i = 0; i < employeesPerPosition; i++) { // Balanced employee count per position
                        String employeeID = String.format("DoPR_Employee_Planning_%04d", employeeCount++);

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