package src.Security.resources;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class AuditEmployeeGenerator {

    public static void main(String[] args) {
        // Define divisions and positions for audit
        Map<String, List<String>> divisions = new LinkedHashMap<>();
        divisions.put("Financial Audit", Arrays.asList("Financial Auditor", "Budget Analyst", "Account Reconciliation Specialist", "Tax Compliance Auditor"));
        divisions.put("Operational Audit", Arrays.asList("Operations Auditor", "Risk Management Analyst", "Process Optimization Specialist", "Workflow Efficiency Consultant"));
        divisions.put("IT Audit", Arrays.asList("IT Auditor", "Cybersecurity Analyst", "System Compliance Specialist", "Data Privacy Officer"));
        divisions.put("Compliance Audit", Arrays.asList("Regulatory Compliance Officer", "Policy Enforcement Specialist", "Internal Control Analyst", "Standards Adherence Auditor"));
        divisions.put("Environmental Audit", Arrays.asList("Sustainability Auditor", "Environmental Impact Analyst", "Waste Management Reviewer", "Energy Efficiency Consultant"));

        // Define expertise per position
        Map<String, List<String>> expertise = new LinkedHashMap<>();
        expertise.put("Financial Auditor", Arrays.asList("Balance Sheet Review", "Financial Statement Analysis", "Fraud Detection"));
        expertise.put("Budget Analyst", Arrays.asList("Cost Analysis", "Forecasting", "Budget Optimization"));
        expertise.put("Account Reconciliation Specialist", Arrays.asList("Account Matching", "Discrepancy Resolution", "Audit Trail Creation"));
        expertise.put("Tax Compliance Auditor", Arrays.asList("Tax Code Expertise", "Regulatory Filings", "Audit Adjustments"));
        expertise.put("Operations Auditor", Arrays.asList("Process Audits", "Procedure Analysis", "Risk Assessment"));
        expertise.put("Risk Management Analyst", Arrays.asList("Risk Identification", "Control Evaluation", "Mitigation Strategies"));
        expertise.put("Process Optimization Specialist", Arrays.asList("Efficiency Analysis", "Bottleneck Identification", "Workflow Enhancement"));
        expertise.put("Workflow Efficiency Consultant", Arrays.asList("Process Streamlining", "Resource Allocation", "Productivity Boosting"));
        expertise.put("IT Auditor", Arrays.asList("System Security Audits", "Data Integrity Checks", "Access Control Review"));
        expertise.put("Cybersecurity Analyst", Arrays.asList("Vulnerability Assessments", "Incident Response Planning", "Threat Modeling"));
        expertise.put("System Compliance Specialist", Arrays.asList("Compliance Checklists", "Policy Review", "System Hardening"));
        expertise.put("Data Privacy Officer", Arrays.asList("GDPR Compliance", "Data Protection Audits", "Privacy Risk Analysis"));
        expertise.put("Regulatory Compliance Officer", Arrays.asList("Legal Standards Review", "Policy Enforcement", "Regulatory Documentation"));
        expertise.put("Policy Enforcement Specialist", Arrays.asList("Guideline Implementation", "Regulation Monitoring", "Non-Compliance Reporting"));
        expertise.put("Internal Control Analyst", Arrays.asList("Control Framework Design", "Effectiveness Testing", "Fraud Prevention"));
        expertise.put("Standards Adherence Auditor", Arrays.asList("Certification Checks", "ISO Compliance", "Operational Consistency"));
        expertise.put("Sustainability Auditor", Arrays.asList("Carbon Footprint Assessment", "Sustainability Metrics", "Green Certification"));
        expertise.put("Environmental Impact Analyst", Arrays.asList("Impact Studies", "Resource Conservation", "Policy Recommendations"));
        expertise.put("Waste Management Reviewer", Arrays.asList("Waste Stream Audits", "Recycling Program Evaluation", "Hazardous Material Review"));
        expertise.put("Energy Efficiency Consultant", Arrays.asList("Energy Usage Analysis", "Efficiency Improvements", "Sustainable Design"));

        // File path
        String filePath = "src/security/resources/demo_100_audit_employees.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            int employeeCount = 1;

            // Define first and last names
            String[] firstNames = {"Emily", "Daniel", "Sophia", "Ethan", "Grace", "Michael", "Ella", "Benjamin", "Liam", "Emma"};
            String[] lastNames = {"Taylor", "White", "Harris", "Walker", "Clark", "Lewis", "Robinson", "Hill", "Scott", "Adams"};

            // Create a balanced distribution of employees
            int employeesPerPosition = 100 / divisions.size() / 4; // Balanced across divisions and positions

            for (Map.Entry<String, List<String>> division : divisions.entrySet()) {
                String divisionName = division.getKey();
                List<String> positions = division.getValue();

                // Balance employees for each position
                for (String position : positions) {
                    for (int i = 0; i < employeesPerPosition; i++) {
                        String employeeID = String.format("DoS_Employee_Audit_%04d", employeeCount++);

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
                                "%s:\nname/%s\ndivision/%s\nposition/%s\ndepartment/Audit\nexpertise/%s\nrating/%.1f\nyearsOfExperience/%d\npreviousAssignment/free\ncurrentAssignment/free\n\n",
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
