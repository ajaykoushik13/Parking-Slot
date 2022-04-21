import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
class Vehicle
{
	ArrayList arr = new ArrayList();
	String vehicleNum;
	double time;
	void getData() //Gets the Vehicle Number & In time
	{
		int ticketNum;
		Scanner sc = new Scanner(System.in);
		
		Space ca = new Space();
		ticketNum = ca.isAvailable();  //Checks for slot availability
		if(ticketNum == 0)
			System.out.println("Please Wait!!! Slots are full");
		else
		{
			System.out.println("Enter Vehicle Number:");
			vehicleNum = sc.nextLine();
			System.out.println("Enter time:");
			time = sc.nextDouble();
			System.out.println("Parking Slot is alloted");
			System.out.println("Go to " + (ticketNum / 10) + " floor " + (ticketNum%10) + " Slot");
			System.out.println("Your ticket number is " + ticketNum);
			arr.add(vehicleNum); //adding vehicle number & in time  to array list
			arr.add(time);
			AssignTicket at = new AssignTicket();//assigning the ticket number and in time to an array
			at.assignticket(ticketNum,arr); 
		}		
	}
}

class Space
{
	static final int floor = 4;
	static final int space = 10;
	private static int slot[][]= new int [floor][space]; //Encapsulating the data
	public int isAvailable() //if available returns the ticket number
	{
		for(int i = 0; i < floor;i+=1)
		{
			for(int j = 0 ; j < space;j+=1)
			{
				if(slot[i][j] == 0)
				{
					slot[i][j] = 1;
					return ((i*10)+(j+1));
				}
			}
		}
		return 0;
	}
	public void set(int floor,int space)
	{
		slot[floor][space] = 0;
	}
} 

class AssignTicket extends Space
{
	static HashMap<Integer,ArrayList> ticket = new HashMap();
	void assignticket(Integer ticketNum,ArrayList arr)
	{
		ticket.put(ticketNum,arr);// making ticket number as key and the array as value
	}
}

class Payment extends AssignTicket
{
	void getFare()
	{
		int ticketNum;
		double endTime;
		double startTime;
		double fare = 10;
		int intpart ;
		double decipartStart;
		double decipartEnd;
		double hoursParked;
		Scanner sc = new Scanner(System.in);
		// getting ticket number and out time to calculate fare
		System.out.println("Enter the ticket number:");
        ticketNum = sc.nextInt(); 
        System.out.println("Enter the Exit time:");
        endTime = sc.nextDouble();
        //logic to get hours parked
        intpart = (int) endTime;
        decipartEnd =  endTime - intpart;
        startTime = (Double) ticket.get(ticketNum).get(1);
        intpart = (int) startTime;
        decipartStart = startTime - intpart;
        
        if(decipartEnd >= decipartStart)
        {
        	hoursParked = endTime - startTime;
        	fare *= hoursParked;
        }
        else
        {
        	decipartEnd += 0.60;
        	decipartEnd /= 10;
        	intpart = (int) endTime;
        	intpart -= 1;
        	int intpartStart = (int) startTime;
        	int deciEnd;
        	int deciStart;
        	intpart -= intpartStart;
        	deciEnd = (int) (decipartEnd * 1000);
        	deciStart = (int) (decipartStart * 100);
        	decipartEnd = (deciEnd - deciStart);
        	decipartEnd /= 100;
        	hoursParked = intpart + decipartEnd;
        	fare *= hoursParked; 
        }
        ticket.remove(ticketNum); //remove the exit vehicle from the records
        int floor = ticketNum / 10;
        int space = ticketNum % 10;
        set(floor,space-1); //Setting the space availability for next vehicle
        System.out.println("Amount to be paid : " + Math.round(fare)); // displaying the fare
        System.out.println("Thank You!!! Visit Again");
	}
}

public class Parking
{
	public static void main(String[] args) {
        int num;
        do{
        	System.out.println("********************************");
        	System.out.println("Welcome");
        	System.out.println("Enter 1 for entry\nEnter 2 for Payment\nEnter 0 to stop the program");
        	System.out.println("********************************");
        	Scanner sc = new Scanner(System.in);
            num = sc.nextInt();
            if(num == 1)
            {
            	Vehicle v = new Vehicle();
            	v.getData();
            }
            else if (num == 2)
            {
            	Payment ex = new Payment();
            	ex.getFare();
            }
            else if(num == 0)
            	System.out.println("Program has stopped.Thank You");
            else
            System.out.println("Enter the correct number");
        }while(num != 0);
	}
}
