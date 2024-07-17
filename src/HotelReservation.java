import java.sql.*;
import java.util.Scanner;

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
            System.out.println("Enter guest name");
            String guestname=scanner.next();
            scanner.nextLine();
            System.out.println("Enter room number");
            int roomnumber=scanner.nextInt();
            System.out.println("Enter contact number");
            String contactnumber=scanner.next();

            String sql="INSERT into reservation (guest_name, room_number, contact_number)" + "VALUES" +
                    " ('" + guestname + "', "+roomnumber+", '" +contactnumber +"')";

            try (Statement statement=connection.createStatement())
            {
                int affecteeedroews=statement.executeUpdate(sql);

                if(affecteeedroews>0)
                {
                    System.out.println("Reservation Successful");
                }
                else
                {
                    System.out.println("Reservation Failed");
                }
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

    public static void deleteReservation(Scanner scanner, Connection connection)
    {
        try {
            System.out.println("Enter reservation id to delete");
            int reservationId=scanner.nextInt();

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}