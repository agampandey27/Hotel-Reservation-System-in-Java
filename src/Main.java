import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
    private static final String username = "root";
    private static final String password = "Mdag1902@@";
    public static void main(String[] args) throws ClassNotFoundException , SQLException{

        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch(ClassNotFoundException e){
            System.err.println("Couldn't load the class " + e.getMessage());
        }

        try{
            Connection connection = DriverManager.getConnection(url,username,password);
            while(true){
                System.out.println();
                System.out.println("HOTEL MANAGEMENT SYSTEM");
                Scanner scan = new Scanner(System.in);
                System.out.println("1. Reserve a room");
                System.out.println("2. View Reservations");
                System.out.println("3. Get Room Number");
                System.out.println("4. Update Reservations");
                System.out.println("5. Delete Reservations");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");
                int choice = scan.nextInt();

                switch (choice){
                    case 1:
                        reserveRoom(connection,scan);
                        break;
                    case 2:
                        viewReservations(connection);
                        break;
                    case 3:
                        getRoomNumber(connection , scan);
                        break;
                    case 4:
                        updateReservation(connection , scan);
                        break;
                    case 5:
                        deleteReservation(connection ,scan);
                        break;
                    case 0:
                        exit();
                        scan.close();
                        return;
                    default:
                        System.out.println("Invalid Input Please Enter Valid Response.");
                }
            }
        } catch (SQLException e){
            System.err.println("Connection Failed" + e.getMessage());
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    private static void reserveRoom(Connection connection , Scanner scan) throws InterruptedException {
        try {
        System.out.print("Enter Guest Name: ");
        scan.nextLine();
        String guest_name = scan.nextLine();
        System.out.print("Enter Room Number Provided to "+guest_name+": ");
        int guest_room_no = scan.nextInt();
        System.out.print("Enter the Contact Number of "+guest_name+": ");
        scan.nextLine();
        String guest_contact = scan.nextLine();
        System.out.println();

        String sql = "INSERT INTO reservations(guest_name, room_number, contact_number) VALUES ('"+guest_name+"',"+guest_room_no+
                ",'"+guest_contact+"');";

//        System.out.println(sql);

            Statement stmt = connection.createStatement();
            int rowsAffected = stmt.executeUpdate(sql);
            if(rowsAffected>0){
                System.out.println("User Register Successfully");
            } else{
                System.err.println("Error Occurred");
            }
        } catch (SQLException e){
            System.err.println("Entries failed: " + e.getMessage());
        } catch (Exception e) {
            System.err.print("Invalid Entry: " + e.getMessage());
            Thread.sleep(2000);
            System.out.println();
        }
    }

    private static void viewReservations(Connection connection) {
        try {
            String sql = "SELECT * FROM reservations;";
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);

            System.out.println("=".repeat(87));
            System.out.printf("| %-4s | %-20s | %-12s | %-15s | %-20s |\n",
                    "ID", "Guest Name", "Room No.", "Contact No.", "Date");
            System.out.println("=".repeat(87));

            while (resultSet.next()) {
                int id = resultSet.getInt("reservation_id");
                String name = resultSet.getString("guest_name");
                int room_number = resultSet.getInt("room_number");
                String contact = resultSet.getString("contact_number");
                String reservationDate = resultSet.getTimestamp("reservation_date").toString();

                System.out.printf("| %-4d | %-20s | %-12d | %-15s | %-20s |\n",
                        id, name, room_number, contact, reservationDate);
            }

            System.out.println("=".repeat(87));

        } catch (Exception e) {
            System.err.println("Error viewing reservations: " + e.getMessage());
        }
    }


    private static void getRoomNumber(Connection connection, Scanner scan) throws InterruptedException{
        try{

            System.out.print("Enter the guest's reservation id: ");
            int guest_id = scan.nextInt();
            System.out.print("Enter the guest's name: ");
            scan.nextLine();
            String guest_name = scan.nextLine();

            String sql =
                    "SELECT room_number FROM reservations WHERE(reservation_id="+guest_id+" AND guest_name='"+guest_name+
                    "');";

            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);

            resultSet.next();
            int room_number = resultSet.getInt("room_number");

            System.out.println("The Room "+'"'+room_number+'"'+" is allotted to reservation id "+guest_id+" on guest " +
                    "name "+guest_name);

        }catch (InputMismatchException e){
            Thread.sleep(1000);
            System.err.println("False Input Provided");
        } catch (SQLException e){
            Thread.sleep(1000);
            System.err.println("Wrong Inputs provided NO SUCH GUEST EXISTS");
        } catch (Exception e){
            Thread.sleep(2000);
            System.err.println("Error Occurred: " + e.getMessage());
        }
    }

    private static void updateReservation(Connection connection , Scanner scan) throws InterruptedException{
        try {

            System.out.print("Enter the reservation id you want to update: ");
            int res_id = scan.nextInt();

            System.out.print("Enter the new Guest Name: ");
            scan.nextLine();
            String new_guest_name = scan.nextLine();
            System.out.print("Enter the new Room Number: ");
            int new_room_number = scan.nextInt();
            System.out.print("Enter the new Contact Number: ");
            scan.nextLine();
            String new_contact_number = scan.nextLine();

            String sql = "UPDATE reservations SET guest_name='"+new_guest_name+"', room_number = "+new_room_number+"," +
                    "contact_number='"+new_contact_number+"' WHERE reservation_id="+res_id+";";

//            System.out.println(sql);
            Statement stmt = connection.createStatement();
            int rowsAffected = stmt.executeUpdate(sql);
            if(rowsAffected>0){
                System.out.println("Details Updated Successfully");
            } else {
                System.err.println("Failed to Update details");
            }

        }catch (InputMismatchException e){
            Thread.sleep(1000);
            System.err.println("False Input Provided");
        } catch (SQLException e){
            Thread.sleep(1000);
            System.err.println("Reservation Doesn't Exist Please Go to 'View Reservations' and Check");
        } catch (Exception e){
            Thread.sleep(1000);
            System.err.println(e.getMessage());
        }

    }

    private static void deleteReservation(Connection connection , Scanner scan) throws InterruptedException{
        try{

            System.out.print("Enter the reservation id you want to delete: ");
            int del_res_id = scan.nextInt();

            String sql = "DELETE FROM reservations WHERE reservation_id = "+del_res_id+";";
            Statement stmt = connection.createStatement();
            int rowsAffected = stmt.executeUpdate(sql);
            if(rowsAffected>0){
                System.out.println("Details Deleted Successfully");
            } else {
                System.out.println("Failed to Delete");
            }

        }catch (InputMismatchException e){
            Thread.sleep(1000);
            System.err.println("False Input Provided");
        } catch (SQLException e){
            Thread.sleep(1000);
            System.err.println("Reservation id doesn't exist.");
        } catch (Exception e) {
            Thread.sleep(1000);
            System.err.println("Error Occurred: " + e.getMessage());
        }
    }

    public static void exit() throws InterruptedException{
        System.out.print("Exiting System");
        int i=5;
        while(i!=0){
            System.out.print(".");
            Thread.sleep(1000);
            i--;
        }
        System.out.println();
        System.out.println("Thank You for using Hotel Management System");
    }
}