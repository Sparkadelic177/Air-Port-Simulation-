# Airport-Simulation

# Project Details 
Simulation is the use of one system to imitate the behavior of another system.
Computer simulations use the steps of a program to imitate the behavior of
a system under study.
In a computer simulation, the objects being studied are usually repre-
sented as data, often as classes whose methods describe behavior of the ob-
jects and whose instance variables describe the properties of the objects.
Most importantly, actions being studied are represented as methods of the
classes, and the rules describing these actions are translated into computer
algorithms. By changing the values of the data or by modifying these algo-
rithms we can observe changes in the computer simulation, then we can draw
worthwhile conclusions concerning the behavior of the actual system.
This project is a simulation of an airport. As a specific example, we
consider a small but a busy airport with only one runway. The simulation
will run for a certain number of units of time. In each time unit, one plane
can land or one plane can take off, but not both. Planes arrive ready to land
or take off at random times, so at any given moment of time, the runway
may be idle or a plane may be landing or taking off, and there may be
several planes waiting either to land or take off. Since there is only a single
runway, we will not use a separate ATC (air traffc control) class which
might normally register arriving and departing 
ights for a large airport with
multiple terminals and runways. The runway class controls all air traffic in
this project.

# Airport Simulation Class

* Only one plane can be served in any unit of time. So either a plane
can land or a plane can takeoff not both.
*  Since waiting in the air might lead to disasters, arrivals have higher
priority than the departing planes.
* Only when the landing list is empty, is a departing plane allowed to
takeoff.
* Planes can't be queued for too long while waiting to land. This means
there is a capacity limit on the landing list. When this list is full
arriving planes must be directed to another airport.
* The simulation of the airport should keep track of how long a landing
or departing plane waits in the line before landing or departing.
* It keeps track of how many arriving flights are directed to another airport.
* The simulation keeps track of the number of planes that requested landing and the number that are accepted, and the number that are landed.
* The departing list doesn't have a limit. All requests for departures will be accepted and served in the order in which they requested the service.
* At the end of simulation, we find the total number of planes served, the average rate of planes wanting to land per unit time, the average rate of planes wanting to depart per unit time, the average wait in the landing line, and the average wait in the departing line in time units.

# Input Data required from a user of the simulation
- This will be read as input in the main method before the simulation begins.
1. endTime in time units.
2. landing list size. Keep this size small.
3. arrivalRate, departureRate. These two variables are doubles. The sum of these two rates shouldn't exceed 1.0 or this will saturate the runway.
