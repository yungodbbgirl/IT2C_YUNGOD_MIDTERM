package com.scc.ui;

import com.scc.dao.ScenarioDao;
import com.scc.model.ScenarioEntity;

import java.util.List;
import java.util.Scanner;

public class ScenarioMenu {
    private final ScenarioDao scenarioDao = new ScenarioDao();
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        int choice = 0;
        do {
            printMenu();
            System.out.print("Enter choice: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline token
                System.out.println();

                switch (choice) {
                    case 1:
                        addRecord();
                        break;
                    case 2:
                        viewRecords();
                        break;
                    case 3:
                        System.out.println("Exiting system... Thank you!");
                        break;
                    default:
                        System.out.println("Invalid choice! Please select between 1 and 3.");
                }
            } else {
                System.out.println("Invalid input! Please enter a numeric menu option.");
                scanner.nextLine();
            }
            System.out.println();
        } while (choice != 3);
    }

    private void printMenu() {
        System.out.println("=================================================");
        System.out.println("         AUTO REPAIR SERVICE JOB SYSTEM          ");
        System.out.println("=================================================");
        System.out.println("[1] Add Record");
        System.out.println("[2] View Records");
        System.out.println("[3] Exit System");
        System.out.println("=================================================");
    }

    private void addRecord() {
        System.out.println("--- CREATE SERVICE JOB ---");

        System.out.print("Enter Customer Name: ");
        String customerName = scanner.nextLine().trim();

        System.out.print("Enter Service Type (OIL_CHANGE / BRAKE_REPAIR / TUNE_UP): ");
        String serviceType = scanner.nextLine().trim().toUpperCase();

        double estimatedCost = 0.0;
        while (true) {
            System.out.print("Enter Estimated Cost: ");
            if (scanner.hasNextDouble()) {
                estimatedCost = scanner.nextDouble();
                scanner.nextLine();
                break;
            } else {
                System.out.println("Invalid amount. Please enter a valid decimal number.");
                scanner.nextLine();
            }
        }

        System.out.print("Enter Status (RECEIVED / IN_PROGRESS / READY): ");
        String status = scanner.nextLine().trim().toUpperCase();

        ScenarioEntity newEntity = new ScenarioEntity(customerName, serviceType, estimatedCost, status);
        boolean success = scenarioDao.insertJob(newEntity);

        if (success) {
            System.out.println("\nSUCCESS: Repair service job created!");
        } else {
            System.out.println("\nERROR: Failed to create repair service job.");
        }
    }

    private void viewRecords() {
        System.out.println("=================================================");
        System.out.println("            AUTO REPAIR SERVICE JOBS             ");
        System.out.println("=================================================\n");

        List<ScenarioEntity> entities = scenarioDao.getAllJobs();

        System.out.println("+-----+--------------------+------------------+------------+-------------+");
        System.out.println("| ID  | CUSTOMER NAME      | SERVICE TYPE     | EST COST   | STATUS      |");
        System.out.println("+-----+--------------------+------------------+------------+-------------+");

        for (ScenarioEntity entity : entities) {
            System.out.printf("| %-3d | %-18s | %-16s | %-10.2f | %-11s |\n",
                    entity.getId(),
                    entity.getCustomerName(),
                    entity.getServiceType(),
                    entity.getEstimatedCost(),
                    entity.getStatus()
            );
        }

        System.out.println("+-----+--------------------+------------------+------------+-------------+");
        System.out.println("Total Records Displayed: " + entities.size());
    }
}