package bot.model;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;

public class EV3Bot
{
	private String botMessage;
	private int xPosition;
	private int yPosition;
	private long waitTime;
	
	private MovePilot botPilot;
	private EV3UltrasonicSensor distanceSensor;
	private EV3TouchSensor backTouch;
	private float[] ultrasonicSamples;
	private float[] touchSamples;
	
	public EV3Bot()
	{
		this.botMessage = "Colm is the best!";
		this.xPosition = 1;
		this.yPosition = 0;
		this.waitTime = 500;
		
		distanceSensor = new EV3UltrasonicSensor(LocalEV3.get().getPort("S1"));
		distanceSensor.getDistanceMode();
		backTouch = new EV3TouchSensor(LocalEV3.get().getPort("S2"));
		
		setupPilot();
		displayMessage();
	}
	
	public void setupPilot()
	{
		//Wheel wheel1 = DifferentialChassis.modelWheel(Motor.A, 43.2).offset(-72);
		//Wheel wheel2 = DifferentialChassis.modelWheel(Motor.D, 43.2).offset(72);
		//Chassis chassis = new DifferentailChassis(new Wheel[]{wheel1, wheel2});
		//MovePilot pilot = new MovePilot(chassis);
		Wheel leftWheel = WheeledChassis.modelWheel(Motor.A, 43.2).offset(-72);
		Wheel rightWheel = WheeledChassis.modelWheel(Motor.B, 43.2).offset(72);
		WheeledChassis chassis = new WheeledChassis(new Wheel[]{leftWheel, rightWheel}, WheeledChassis.TYPE_DIFFERENTIAL);
		botPilot = new MovePilot(chassis);
	}
	
	public void driveRoom()
	{
		ultrasonicSamples = new float[distanceSensor.sampleSize()];
		distanceSensor.fetchSample(ultrasonicSamples, 0);
		if(true) //Not a real number. Figure out a better number. ultrasonicSamples[0] < .5
		{
			shortPath();
		}
		else
		{
			longPath();
		}
		/*
		 * Distances 13ft by 19 ft by 12 ft by 3 ft
		 * in CM 396 by 579 by 366 by 91
		 */
		distanceSensor.fetchSample(ultrasonicSamples, 0);
		//call private helper method here.
		displayMessage("DriveRoom");
	}
	
	public void dance()
	{
		displayMessage("DANCE TIME™");
		botPilot.rotate(-10);
		botPilot.rotate(10);
		botPilot.rotate(-360);
	}
	
	private void shortPath()
	{
		//short method 
		botPilot.travel(910);
		botPilot.rotate(65);
		botPilot.travel(3660);
		botPilot.rotate(-65);
		botPilot.travel(5500);
		botPilot.rotate(65);
		botPilot.travel(3960);
	}
	
	private void longPath()
	{
		//long method
		botPilot.travel(3960);
		botPilot.rotate(-65);
		botPilot.travel(5500);
		botPilot.rotate(65);
		botPilot.travel(3660);
		botPilot.rotate(-65);
		botPilot.travel(910);
	}
	
	public void driveAround()
	{
		while(LocalEV3.get().getKeys().waitForAnyPress() != LocalEV3.get().getKeys().ID_ESCAPE)
		{
			double distance = (Math.random() * 100) % 23;
			double angle = (Math.random() * 360);
			boolean isPositive = ((int) (Math.random() * 2) % 2 == 0);
			distanceSensor.fetchSample(ultrasonicSamples, 0);
			if(ultrasonicSamples[0] < 17)
			{
				botPilot.travel(-distance);
				botPilot.travel(angle);
			}
			else
			{
				botPilot.rotate(-angle);
				botPilot.travel(distance);
			}
		}
	}
	
	private void displayMessage()
	{
		LCD.drawString(botMessage, xPosition, yPosition);
		Delay.msDelay(waitTime);
	}
	
	private void displayMessage(String message)
	{
		LCD.drawString(botMessage, xPosition, yPosition);
		Delay.msDelay(waitTime);
	}
}
