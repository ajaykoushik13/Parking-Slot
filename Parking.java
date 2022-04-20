import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
class Vehicle
{
	ArrayList arr = new ArrayList();
	String vehicleNum;
	double time;
	void getData()
	{
		int ticketNum;
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Vehicle Number:");
		vehicleNum = sc.nextLine();
		System.out.println("Enter time:");
		time = sc.nextDouble();
		CheckAvailability ca = new CheckAvailability();
		ticketNum = ca.isAvailable();
		if(ticketNum == 0)
			System.out.println("Please Wait!!! Slots are full");
		else
		{
			System.out.println("Parking Slot is alloted");
			System.out.println("Go to " + (ticketNum / 10) + " floor " + (ticketNum%10) + " Slot");
			System.out.println("Your ticket number is " + ticketNum);
			arr.add(vehicleNum);
			arr.add(time);
			AssignTicket at = new AssignTicket();
			at.assignticket(ticketNum,arr);
		}		
	}
}

class CheckAvailability
{
	static final int floor = 2;
	static final int space = 2;
	private static int slot[][]= new int [floor][space];
	public int isAvailable()
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

class AssignTicket extends CheckAvailability
{
	static HashMap<Integer,ArrayList> ticket = new HashMap();
	void assignticket(Integer ticketNum,ArrayList arr)
	{
		ticket.put(ticketNum,arr);
	}
}

class Exit extends AssignTicket
{
	void getFair()
	{
		int ticketNum;
		double endTime;
		double startTime;
		double fair = 10;
		int intpart ;
		double decipartStart;
		double decipartEnd;
		double hoursParked;
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the ticket number:");
        ticketNum = sc.nextInt();
        System.out.println("Enter the Exit time:");
        endTime = sc.nextDouble();
        intpart = (int) endTime;
        decipartEnd =  endTime - intpart;
        startTime = (Double) ticket.get(ticketNum).get(1);
        intpart = (int) startTime;
        decipartStart = startTime - intpart;
        if(decipartEnd >= decipartStart)
        {
        	hoursParked = endTime - startTime;
        	fair *= hoursParked;
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
        	fair *= hoursParked; 
        }
        ticket.remove(ticketNum);
        int floor = ticketNum / 10;
        int space = ticketNum % 10;
        set(floor,space-1);
        System.out.println("Amount to be paid : " + Math.round(fair));
        System.out.println("Thank You!!! Visit Again");
	}
}

public class Parking {
	public static void main(String[] args) {
        int num;
        do{
        	System.out.println("********************************");
        	System.out.println("Welcome");
        	System.out.println("Enter 1 for entry\nEnter 2 for exit\nEnter 0 to stop the program");
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
            	Exit ex = new Exit();
            	ex.getFair();
            }
            else if(num == 0)
            	System.out.println("Program has stopped.Thank You");
            else
            System.out.println("Enter the correct number");
        }while(num != 0);
	}
}
