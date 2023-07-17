package OasisInfobytes;
	import java.util.HashMap;
	import java.util.Map;
	import java.util.Scanner;

	public class OnlineReservationSystem {
	    private static Map<String, String> userCredentials = new HashMap<>();
	    private static Map<String, Reservation> reservations = new HashMap<>();
	    private static int nextPNR = 1000;

	    public static void main(String[] args) {
	        populateUserCredentials(); 

	        Scanner scanner = new Scanner(System.in);

	        boolean loggedIn = false;
	        String loggedInUser = null;

	        while (true) {
	            displayMenu();

	            System.out.print("Enter your choice: ");
	            int choice = scanner.nextInt();
	            scanner.nextLine(); 

	            if (choice == 1) {
	                // Login Form
	                System.out.print("Enter login id: ");
	                String loginId = scanner.nextLine();
	                System.out.print("Enter password: ");
	                String password = scanner.nextLine();

	                loggedIn = login(loginId, password);
	                if (loggedIn) {
	                    loggedInUser = loginId;
	                    System.out.println("Login successful!");
	                } else {
	                    System.out.println("Invalid login credentials. Please try again.");
	                }
	            } else if (choice == 2) {
	                // Reservation Form
	                if (loggedIn) {
	                    Reservation reservation = captureReservationDetails(scanner);
	                    if (reservation != null) {
	                        String pnrNumber = generatePNR();
	                        reservation.setPnrNumber(pnrNumber);
	                        reservations.put(pnrNumber, reservation);
	                        System.out.println("Reservation successful!");
	                        System.out.println("PNR: " + pnrNumber);
	                    } else {
	                        System.out.println("Reservation failed. Please try again later.");
	                    }
	                } else {
	                    System.out.println("You must log in to access this feature.");
	                }
	            } else if (choice == 3) {
	                // Cancellation Form
	                if (loggedIn) {
	                    System.out.print("Enter PNR number: ");
	                    String pnrNumber = scanner.nextLine();
	                    cancelReservation(pnrNumber);
	                } else {
	                    System.out.println("You must log in to access this feature.");
	                }
	            } else if (choice == 4) {
	                // PNR Enquiry
	                if (loggedIn) {
	                    System.out.print("Enter PNR number: ");
	                    String pnrNumber = scanner.nextLine();
	                    displayReservationDetails(pnrNumber);
	                } else {
	                    System.out.println("You must log in to access this feature.");
	                }
	            } else if (choice == 5) {
	                // Exit
	                System.out.println("Thank you for using the Online Reservation System. Goodbye!");
	                break;
	            } else {
	                System.out.println("Invalid choice. Please try again.");
	            }
	        }

	        scanner.close();
	    }

	    private static void displayMenu() {
	        System.out.println();
	        System.out.println("Online Reservation System");
	        System.out.println("-------------------------");
	        System.out.println("1. Login");
	        System.out.println("2. Reservation");
	        System.out.println("3. Cancellation");
	        System.out.println("4. PNR Enquiry");
	        System.out.println("5. Exit");
	        System.out.println("-------------------------");
	    }

	    private static void populateUserCredentials() {
	        userCredentials.put("user1", "password1");
	        userCredentials.put("user2", "password2");
	    }

	    private static boolean login(String loginId, String password) {
	        String storedPassword = userCredentials.get(loginId);
	        return storedPassword != null && storedPassword.equals(password);
	    }

	    private static Reservation captureReservationDetails(Scanner scanner) {
	        System.out.println("Reservation Form");
	        System.out.println("----------------");

	        System.out.print("Passenger Name: ");
	        String passengerName = scanner.nextLine();

	        System.out.print("Source Station: ");
	        String sourceStation = scanner.nextLine();

	        System.out.print("Destination Station: ");
	        String destinationStation = scanner.nextLine();

	        String trainNumber = getTrainNumber(sourceStation, destinationStation);
	        String trainName = getTrainName(trainNumber);

	        if (trainNumber == null || trainName == null) {
	            System.out.println("Invalid route. Train not found.");
	            return null;
	        }

	        if (!validatePassengerName(passengerName) || !validateStation(sourceStation) || !validateStation(destinationStation)) {
	            System.out.println("Invalid input. Please check your details and try again.");
	            return null;
	        }

	        return new Reservation(passengerName, sourceStation, destinationStation, trainNumber, trainName);
	    }

	    private static String getTrainNumber(String sourceStation, String destinationStation) {
	        if (sourceStation.equalsIgnoreCase("Station A") && destinationStation.equalsIgnoreCase("Station B")) {
	            return "12345";
	        } else if (sourceStation.equalsIgnoreCase("Station C") && destinationStation.equalsIgnoreCase("Station D")) {
	            return "67890";
	        } else {
	            return null;
	        }
	    }

	    private static String getTrainName(String trainNumber) {
	        if (trainNumber.equals("12345")) {
	            return "Train X";
	        } else if (trainNumber.equals("67890")) {
	            return "Train Y";
	        } else {
	            return null;
	        }
	    }

	    private static boolean validatePassengerName(String passengerName) {
	        //Checks if the passenger name is not empty
	        return !passengerName.isEmpty();
	    }

	    private static boolean validateStation(String station) {
	        //Check if the station name is not empty
	        return !station.isEmpty();
	    }

	    private static String generatePNR() {
	        //Generate a unique PNR based on a counter
	        return "PNR" + nextPNR++;
	    }

	    private static void cancelReservation(String pnrNumber) {
	        Reservation reservation = reservations.get(pnrNumber);
	        if (reservation != null) {
	            reservations.remove(pnrNumber);
	            System.out.println("Reservation with PNR " + pnrNumber + " has been canceled.");
	        } else {
	            System.out.println("Reservation with PNR " + pnrNumber + " not found.");
	        }
	    }

	    private static void displayReservationDetails(String pnrNumber) {
	        Reservation reservation = reservations.get(pnrNumber);
	        if (reservation != null) {
	            System.out.println("Reservation Details");
	            System.out.println("-------------------");
	            System.out.println("PNR: " + reservation.getPnrNumber());
	            System.out.println("Passenger Name: " + reservation.getPassengerName());
	            System.out.println("Source Station: " + reservation.getSourceStation());
	            System.out.println("Destination Station: " + reservation.getDestinationStation());
	            System.out.println("Train Number: " + reservation.getTrainNumber());
	            System.out.println("Train Name: " + reservation.getTrainName());
	        } else {
	            System.out.println("Reservation with PNR " + pnrNumber + " not found.");
	        }
	    }
	}

	class Reservation {
	    private String pnrNumber;
	    private String passengerName;
	    private String sourceStation;
	    private String destinationStation;
	    private String trainNumber;
	    private String trainName;

	    public Reservation(String passengerName, String sourceStation, String destinationStation, String trainNumber, String trainName) {
	        this.passengerName = passengerName;
	        this.sourceStation = sourceStation;
	        this.destinationStation = destinationStation;
	        this.trainNumber = trainNumber;
	        this.trainName = trainName;
	    }

	    public String getPnrNumber() {
	        return pnrNumber;
	    }

	    public void setPnrNumber(String pnrNumber) {
	        this.pnrNumber = pnrNumber;
	    }

	    public String getPassengerName() {
	        return passengerName;
	    }

	    public String getSourceStation() {
	        return sourceStation;
	    }

	    public String getDestinationStation() {
	        return destinationStation;
	    }

	    public String getTrainNumber() {
	        return trainNumber;
	    }

	    public String getTrainName() {
	        return trainName;
	    }
	}
