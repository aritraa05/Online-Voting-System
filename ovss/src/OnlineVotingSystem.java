import java.util.ArrayList;
import java.util.Scanner;

class Voter {
    String aadhar;
    String name;
    String birthDate;

    Voter(String aadhar, String name, String birthDate) {
        this.aadhar = aadhar;
        this.name = name;
        this.birthDate = birthDate;
    }
}

public class OnlineVotingSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ArrayList<Voter> voterList = new ArrayList<>();
    private static final ArrayList<String> votedAadhar = new ArrayList<>();
    private static final int[] votes = new int[5];
    private static int attemptsLeft = 3;

    public static void main(String[] args) {
        setupVoters();

        while (true) {
            System.out.println("\n****** WELCOME TO THE ONLINE VOTING PORTAL ******");
            System.out.println("1. Vote Entry");
            System.out.println("2. Admin Panel");
            System.out.println("3. Show Winner");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> voteEntry();
                case 2 -> adminPanel();
                case 3 -> showWinner();
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void setupVoters() {
        voterList.add(new Voter("10001", "Rajat Kumar", "31-03-1999"));
        voterList.add(new Voter("10002", "Goutam Kumar Bhadani", "01-01-1999"));
        voterList.add(new Voter("10003", "Gautam Kumar", "12-10-1999"));
        voterList.add(new Voter("10004", "Kundan Kumar", "08-12-1999"));
        voterList.add(new Voter("10005", "Aniket Arora", "28-08-1999"));
    }

    private static void voteEntry() {
        if (attemptsLeft == 0) {
            System.out.println("You have been locked out due to too many incorrect attempts.");
            return;
        }

        System.out.print("Enter Aadhar ID: ");
        String aadhar = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Birth Date (dd-mm-yyyy): ");
        String birthDate = scanner.nextLine();

        Voter voter = validateVoter(aadhar, name, birthDate);
        if (voter == null) {
            attemptsLeft--;
            System.out.println("Incorrect credentials. Attempts left: " + attemptsLeft);
            return;
        }

        if (votedAadhar.contains(aadhar)) {
            System.out.println("You have already voted. You cannot vote again.");
            return;
        }

        displayCandidates();
        System.out.print("Enter your choice (1-5): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice >= 1 && choice <= 5) {
            votes[choice - 1]++;
            votedAadhar.add(aadhar);
            System.out.println("Thank you for voting!");
        } else {
            System.out.println("Invalid choice. Try again.");
        }
    }

    private static Voter validateVoter(String aadhar, String name, String birthDate) {
        for (Voter voter : voterList) {
            if (voter.aadhar.equals(aadhar) && voter.name.equals(name) && voter.birthDate.equals(birthDate)) {
                return voter;
            }
        }
        return null;
    }

    private static void displayCandidates() {
        System.out.println("\nList of Candidates:");
        System.out.println("1. Mamata Banerjee");
        System.out.println("2. Deepa Dasmunsi");
        System.out.println("3. Protima Rajak");
        System.out.println("4. Joydeb Halder");
        System.out.println("5. Kartik Chandra Ghosh");
    }

    private static void adminPanel() {
        System.out.print("Enter Admin Password: ");
        int password = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (password == 3692) {
            displayVoteCounts();
        } else {
            System.out.println("Incorrect password.");
        }
    }

    private static void displayVoteCounts() {
        System.out.println("\nCurrent Vote Counts:");
        System.out.println("1. Mamata Banerjee: " + votes[0] + " votes");
        System.out.println("2. Deepa Dasmunsi: " + votes[1] + " votes");
        System.out.println("3. Protima Rajak: " + votes[2] + " votes");
        System.out.println("4. Joydeb Halder: " + votes[3] + " votes");
        System.out.println("5. Kartik Chandra Ghosh: " + votes[4] + " votes");
    }

    private static void showWinner() {
        int maxVotes = 0;
        int winnerIndex = -1;

        for (int i = 0; i < votes.length; i++) {
            if (votes[i] > maxVotes) {
                maxVotes = votes[i];
                winnerIndex = i;
            }
        }

        if (winnerIndex != -1) {
            System.out.println("\nThe current winner is Candidate " + (winnerIndex + 1) + " with " + maxVotes + " votes.");
        } else {
            System.out.println("No votes have been cast yet.");
        }
    }
}
