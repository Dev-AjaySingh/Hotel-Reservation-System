import java.sql.*;
import java.util.Scanner;
import java.util.SortedMap;

public class HotelReservation
{

    private static final String url="jdbc:mysql://127.0.0.1:3306/hotel_db";
    private static final String username="root";
    private static final String password="Kanchan1@";

    public static void main(String[] args)
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded Successfully");
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        try
        {
            Connection connection= DriverManager.getConnection(url,username,password);
            System.out.println("Connection successfully");
            while(true)
            {
                System.out.println();
                System.out.println("Hotel Management System");
                Scanner scanner=new Scanner(System.in);
                System.out.println("1. Hotel Reservation");
                System.out.println("2. View Reservation");
                System.out.println("3. Get Room Number");
                System.out.println("4. Update Reservation");
                System.out.println("5. Delete Reservation");
                System.out.println("0. Exit");
                System.out.println("Choose an option: ");
                int choise=scanner.nextInt();
                switch (choise)
                {
                    case 1:
                        reserveRoom(connection,scanner);
                        break;
                    case 2:
                        viewReservation(connection);
                        break;
                    case 3:
                        getroomnumber(scanner,connection);
                        break;
                    case 4:
                        updateReservation(scanner,connection);
                        break;
                    case 5:
                        deleteReservation(connection,scanner);
                        break;
                    case 0:
                        exit();
                        scanner.close();
                        return;
                    default:
                        System.out.println("invalid choice");
                }

            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }


    public static void reserveRoom(Connection connection, Scanner scanner)
    {
        try
        {
//            System.out.println("Enter guest name");
//            String guestname=scanner.next();
//            scanner.nextLine();
//            System.out.println("Enter room number");
//            int roomnumber=scanner.nextInt();
//            System.out.println("Enter contact number");
//            String contactnumber=scanner.next();

//            String sql="INSERT into reservation (guest_name, room_number, contact_number)" + "VALUES" +
//                    " ('" + guestname + "', "+roomnumber+", '" +contactnumber +"')";
            String sql= "select * from reservation where reservation_id=? and guest_name = ? ";

            try (Statement statement=connection.createStatement())
            {
                //int affecteeedroews=statement.executeUpdate(sql);

                PreparedStatement preparedStatement=connection.prepareStatement(sql);
                preparedStatement.setInt(1,1);
                preparedStatement.setString(2,"tom");
                ResultSet resultSet=preparedStatement.executeQuery();


//                if(affecteeedroews>0)
//                {
//                    System.out.println("Reservation Successful");
//                }
//                else
//                {
//                    System.out.println("Reservation Failed");
//                }
                while (resultSet.next())
                {
                    int id=resultSet.getInt("reservation_id");
                    String name=resultSet.getString("guest_name");
                    String number=resultSet.getString("contact_number");
                    System.out.println("ID:"+id);
                    System.out.println("Name:"+name);
                    System.out.println("Contact"+number);

                }
                connection.close();
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void viewReservation(Connection connection) throws SQLException
    {
        String sql="SELECT reservation_id, guest_name, room_number, contact_number, reservation_date FROM reservation";
        try(Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(sql))
        {
            System.out.println("Current Reservation");
            while (resultSet.next())
            {
                int reservationId=resultSet.getInt("reservation_id");
                String guestName=resultSet.getString("guest_name");
                int roomNumber=resultSet.getInt("room_number");
                String contactNumber= resultSet.getString("contact_number");
                String reservationDate=resultSet.getTimestamp("reservation_date").toString();
                System.out.println(reservationId+" "+guestName+" "+roomNumber+" "+contactNumber+" "+reservationDate);

            }
        }

    }

    public static void getroomnumber(Scanner scanner, Connection connection)
    {
        try {


            System.out.println("Enter the ID");
            int reservationId = scanner.nextInt();

            String sql = "SELECT room_number FROM reservation " +
                    "WHERE reservation_id = " + reservationId;

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                if (resultSet.next()) {
                    int roomNumber = resultSet.getInt("room_number");
                    System.out.println("the room number is " + roomNumber);
                }
                else
                {
                    System.out.println("No reservation found");
                }

            }

        }
        catch (SQLException e)
        {
           e.printStackTrace();
        }
    }

    public static void updateReservation(Scanner scanner, Connection connection)
    {
        try {
            System.out.println("Enter reservation id to update");
            int reservationId = scanner.nextInt();
            scanner.nextLine();

            if (!reservationExist(connection, reservationId)) {
                System.out.println("Reservation not found for the given ID");
                return;
            }

            System.out.println("Enter new guest name: ");
            String newGuestName = scanner.nextLine();
            System.out.println("Enter room number");
            int newRoomNumber = scanner.nextInt();
            System.out.println("Enter new contact number");
            String newContactNumber = scanner.next();

            String sql = "UPDATE reservation SET guest_name = '" + newGuestName + "', " +
                    "room_number = " + newRoomNumber + ", " +
                    "contact_number = '" + newContactNumber + "' " +
                    "WHERE reservation_id = " + reservationId;

            try (Statement statement = connection.createStatement()) {
                int affectedRows = statement.executeUpdate(sql);

                if (affectedRows > 0) {
                    System.out.println("Reservation updated successfully");
                } else {
                    System.out.println("Reservation update failed.");
                }
            }
        }

        catch (SQLException e)
            {
                e.printStackTrace();
            }
    }

    private static void deleteReservation(Connection connection,Scanner scanner)
    {
        try {
            System.out.println("Enter reservation id to delete");
            int reservationId=scanner.nextInt();

            if(reservationExist(connection, reservationId))
            {
                System.out.println("Reservation not found for the given id");
                return;
            }

            String sql="DELETE from reservation WHERE reservation_id="+reservationId;

            try (Statement statement=connection.createStatement()){
                int affectedRows=statement.executeUpdate(sql);

                if(affectedRows>0)
                {
                    System.out.println("Deleted Succesfully");
                }
                else
                {
                    System.out.println("deletion failed!");
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    private static boolean reservationExist(Connection connection, int reservationId)
    {
        try {
            String sql="SELECT reservation_id FROM reservation WHERE reservation_id = " +reservationId;

            try (
                Statement statement=connection.createStatement();
                ResultSet resultSet=statement.executeQuery(sql)) {
                return resultSet.next();
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    private static void exit() throws InterruptedException
    {
        System.out.println("Exiting System");
        int i=5;
        while(i!=0)
        {
            System.out.print(".");
            Thread.sleep(450);
            i--;
        }
        System.out.println();
        System.out.println("Thank-you for Using Hotel Reservation System.");
    }

}