import java.sql.Connection;
import java.sql.DriverManager;
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
                        
                }

            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}