package bot.controller;

import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import bot.model.EV3Bot;

public class BotController
{
	private String message;
	private int xPosition, yPosition;
	private long waitTime;
	private EV3Bot sillyBot;
	
	public BotController()
	{
		this.xPosition = 50;
		this.yPosition = 100;
		this.waitTime = 4000;
		this.message = "I'm a robot";
		sillyBot = new EV3Bot();
	}
	
	public void start()
	{
		LCD.drawString(message, xPosition, yPosition);
		Delay.msDelay(waitTime);
		sillyBot.driveRoom();
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public int getxPostion()
	{
		return xPosition;
	}

	public void setxPostion(int xPostion)
	{
		this.xPosition = xPostion;
	}

	public int getyPosition()
	{
		return yPosition;
	}

	public void setyPosition(int yPosition)
	{
		this.yPosition = yPosition;
	}

	public long getWaitTime()
	{
		return waitTime;
	}

	public void setWaitTime(long waitTime)
	{
		this.waitTime = waitTime;
	}
}
