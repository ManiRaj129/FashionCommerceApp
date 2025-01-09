//package src.HR.src;
///**
// * @author Sam Gumm
// */
//public class employeeHandlingSystem {
//
//    /**
//     * @param args
//     */
//    public static void main(String[] args) {
//        // Add a new employee
//        Employee newEmployee1 = new Employee("E123", "Alice Johnson", Department.ENGINEERING, "Software Engineer", "Active", 80000);
//        Employee newEmployee2 = new Employee("E124", "John Smith", Department.MARKETING, "Marketing Specialist", "Active", 90000);
//        Employee newEmployee3 = new Employee("E125", "Emma Brown", Department.HUMAN_RESOURCES, "HR Coordinator", "Active", 100000);
//        Employee newEmployee4 = new Employee("E126", "Michael Davis", Department.FINANCE, "Financial Analyst", "Active", 110000);
//        Employee newEmployee5 = new Employee("E127", "Sophia Wilson", Department.ENGINEERING, "DevOps Engineer", "Active", 70000);
//
//        System.out.println("Employee '" + newEmployee1.name + "' in department: " + newEmployee1.department);
//
//        // Add a new candidate
//        Candidate newCandidate = new Candidate("C456", "Bob Smith", "Data Analyst");
//        System.out.println("Candidate '" + newCandidate.candidateId + "' with name: " + newCandidate.name);
//
//        // Employee management
//        employeeRecordManager handler = new employeeRecordManager();
//        handler.addEmployee(newEmployee1);
//        handler.addEmployee(newEmployee2);
//        handler.addEmployee(newEmployee3);
//        handler.addEmployee(newEmployee4);
//        handler.addEmployee(newEmployee5);
//        handler.displayRecords();
//        // Switching employee to new department
//        handler.updateEmployee("E123", Department.DESIGN, "Software Engineer", "Active", 100000);
//        handler.displayRecords();
//        // print out just a department -> DESIGN
//        handler.displayDepartment(Department.DESIGN);
//        System.out.println(handler.collateSalariesByDepartment(Department.DESIGN));
//    }
//}