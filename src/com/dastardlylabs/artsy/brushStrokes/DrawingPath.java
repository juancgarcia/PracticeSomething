package com.dastardlylabs.artsy.brushStrokes;

import com.dastardlylabs.interfaces.ICanvasCommand;
import com.dastardlylabs.interfaces.ICommandItem;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class DrawingPath implements ICommandItem, ICanvasCommand{
	public Path path;
	public Paint paint;
	private Canvas canvas;
	private long timeCode;
	private long startTime;
	private long endTime;
	
	public DrawingPath(){
		path = new Path();
		paint = new Paint();
		timeCode = System.currentTimeMillis();
		startTime = timeCode;
		endTime = timeCode;
	}
	
	public String getCode(){
		return String.valueOf(timeCode);
	}
	
	public void start(){
		startTime = System.currentTimeMillis();
	}
	
	public void end(){
		endTime = System.currentTimeMillis();
	}
	
	/**
	 * ICanvasCommand
	 * shortcut of ICommandItem:setContext + ICommandItem:redo
	 */
	public void draw(Canvas canvas) {
		canvas.drawPath( path, paint );
	}
	
	/**
	 * ICommandItem
	 */
	public void setContext(Object context){
		canvas = (Canvas) context;
	}
	
	/**
	 * ICommandItem
	 */
	public void redo() {
		draw( canvas );
	}

	/**
	 * ICommandItem
	 */
	public void undo() {
		//Todo this would be changed later
	}
}
