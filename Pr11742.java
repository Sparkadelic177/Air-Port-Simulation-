import java.util.Scanner;
import java.util.ArrayList;

//Plane class tells us about what specific planes are doing 
class Plane{
    int flightNumber;
    int clockStart;
    int status; //null = 0, arriving = 1, departing = 2

    Plane(){
        flightNumber = 0;
        clockStart = 0;
        status = 0;
    }

    Plane(int fltNumber, int time, int status){
        flightNumber = fltNumber;
        clockStart = time;
        this.status = status;
    }

    //if the landing arraylist reaches a predetermined size the plane gets redirected to a different airport 
    void refuse(){
        System.out.println("Flight Number " + flightNumber + " going to another airport's runway");

    }   

    //Arriving flight lands and once it land it will be removed from the list 
    void land(int time){
        status = 0;
        System.err.println("Flight number " + flightNumber + " has landed at the " + time + " time interval, was accepted at " + clockStart + " interval");
    }

    //departing flight takes off and its deleted from the takeout list
    void fly(int time){
        status = 0;
        System.out.println("Flight number " + flightNumber + " took off at the " + time + " time interval, requested takeoff at " + clockStart + " interval");
    }

    //returns the time at which a plane joineed the appropriate runway
    int started(){
        return clockStart;
    }

}

//Runway class that tells us how many planes and if it can land or takeoff
class Runway{
    ArrayList<Plane> landing;
    ArrayList<Plane> takeoff = new ArrayList<Plane>();
    int listLimit; //the limit of the landing planes

    //METHODS
    public Runway(int limit){
        landing = new ArrayList<Plane>(limit);
        listLimit = limit;
    }

    //returns true if it can enter the landing list otherwise false
    //enters the first plane in the list , but its only suppose to return true or false? 
    //what do I do with whats entered in this method
    boolean canLand(Plane current){
        boolean flag = false;
        if(landing.size() == listLimit){
            return flag;
        }else{
            flag = true;
            addPlane(current);
            return flag;
        }
    }

    //this method is usless, no need for such method to perform a simple task
    void addPlane(Plane current){
        landing.add(current);
    }

    //add the current plane to the takeoff list
    void addTakeOff(Plane current){
        takeoff.add(current);
    }

    //let the first plane in the list land and then removed after the plane lands
    void activityLand(int time){
        //if the landing list is not empty
        if(!landing.isEmpty()){
            landing.get(0).land(time);
            landing.remove(0);
        }
    }

    //let the first plane in the takeoff list takeoff
    //is there is no planes in the landing list and takeoff list is not empty then let a plane takeoff
    void activityTakeOff(int time){
        if(landing.isEmpty()){
            if(!takeoff.isEmpty()){
                takeoff.get(0).fly(time);
                takeoff.remove(0);
            }
        }
    }

    //If there is no plane to land or takeoff, let the runway idle
    void runIdle(int time){
        if(landing.size() == 0 && takeoff.size() == 0){
            System.out.println("The runway is idle at the " + time + " time interval");
        }
    }
}


//Main public class that simulates the runway and planes
//this class doesnt need a validate method, can be checked on the run
public class Pr11742{

    //this method gives you the number of arriving planes     
    static int poisson(double rate){
        double limit = Math.exp(-rate);
        double product =  Math.random();
        int count = 0;
        while(product > limit){
            count++;
            product *= Math.random();
        }
        return count;
    }


    public static void main(String[] args){
        //find the total number of plans served, the average rate of plans wanting to land and depart per unit time 
        //The average wait in the landing line and average wait in the landing line in time units

        int endTime, //end of the simulation 
            size, //size of the the landing list
            arriving, //amount of arrving planes per time unit
            departing = 0, //amountof departing planes per time unit 
            directed = 0, //amount of directed planes
            requestedLanding = 0, //amount of requested for landing
            requestedDeparting = 0, //amount of requsted for departing
            landed = 0, //amount of landed planes
            tookoff = 0, //amount of planes that tookoff
            accepted = 0, //ammount of accepted requests
            planesServed = 0; //departuring and arriving 
            

        double arrivalRate = 0;
        double departureRate = 0;
        double 
            averageDepartingRate = 0, //planes wanting to depart per unit time 
            averageArrivingRate = 0,  //planes wanting to land per unit time
            arrivingWaitAverage = 0, //time spent waiting -> landing time - time start
            departingWaitAverage = 0; //time spending waiting -> takeoff time - time start
        Runway runway;
        boolean valid = false;
        Scanner input = new Scanner(System.in);


        System.out.print("\nPlease enter the end time: ");
        endTime = input.nextInt();

        System.out.print("\nNow enter the size of the landing list: ");
        size = input.nextInt();
        runway = new Runway(size);

        while(valid == false){ 
            System.out.print("\nNow enter the arrivalRate: ");
            arrivalRate = input.nextDouble();

            System.out.print("\nNow enter the departureRate: ");
            departureRate = input.nextDouble();

            if((departureRate + arrivalRate ) <= 1.0){
                valid = true;
                
            }else{
                System.out.print("\nTHE ARRIVAL AND DEPARTURE RATE SUM CAN NOT BE GREATER THAN 1.0, try again\n");
            }

        }

        //Main loop
        for (int i = 0; i < endTime; i++) {

            //adding the arriving planes
            arriving = poisson(departureRate + arrivalRate); 
            requestedLanding += arriving;
            for(int j = 0; j < arriving; j++ ){
                int flightNumber = (int)Math.floor((Math.random() * 1000) + 1);;
                Plane arrivingPlane = new Plane(flightNumber, i,  1);
                if(runway.canLand(arrivingPlane)){
                    accepted += 1;                 
                }else{
                    //the flight has to be redirected and recorded 
                    arrivingPlane.refuse();
                    directed += 1;
                }
            }

            //getting the departing planes
            departing = poisson(departureRate); 
            requestedDeparting += departing;
            for (int j = 0; j < departing; j++) {
                int flightNumber = (int)Math.floor((Math.random() * 1000) + 1);
                Plane departingPlane = new Plane(flightNumber, i, 2);
                runway.addTakeOff(departingPlane);
            }


            //Deciding what the runway is going to do.
            if(!runway.landing.isEmpty()){
                arrivingWaitAverage += i - runway.landing.get(0).started(); 
                runway.activityLand(i); //removing plane
                landed += 1; //recording data
            }else if(!runway.takeoff.isEmpty()){
                departingWaitAverage += i - runway.takeoff.get(0).started(); 
                runway.activityTakeOff(i); //removing plane
                tookoff += 1; //recording data
            }else{
                runway.runIdle(i);
            }        

        }

        
        //statistic calculations
        planesServed = landed + tookoff;
        averageDepartingRate = (double)requestedDeparting / (double)endTime;
        averageArrivingRate = (double)requestedLanding / (double)endTime;
        arrivingWaitAverage = (double)arrivingWaitAverage / (double)landed;

        //if none tookoff, departingwaitAverage can't be divided by zero
        if(tookoff == 0 ){
            departingWaitAverage = departingWaitAverage / 1;
        }else{
            departingWaitAverage = (double)departingWaitAverage / (double)tookoff;
        }

        //printing out the stats
        System.out.println("\nThese are the Stats:\n\tPlanes Served:"+ planesServed + 
            "\n\tAverage Departure Rate:" +averageDepartingRate + "\n\tAverage Arriving Rate: " + averageArrivingRate +
            "\n\tAverage Departure Wait: " +departingWaitAverage + "\n\tAverage Arriving Wait: " + arrivingWaitAverage +
            "\n\nExtra data calculated: " +
            "\n\tRequested Landing: " + requestedLanding + "\n\tRequested Departure: " +requestedDeparting+ 
            "\n\tDirected Planes: " + directed + "\n\tAccepted Planes: " +accepted+
            "\n\tLanded Planes: " + landed + "\n\ttookoff Planes: " + tookoff +
            "\n\tLanding list leftovers: " + runway.landing.size() + "\n\tTakeoff list leftovers: "  + runway.takeoff.size());
    }  
}


