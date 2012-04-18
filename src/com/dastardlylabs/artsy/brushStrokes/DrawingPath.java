package com.dastardlylabs.artsy.brushStrokes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.SystemClock;

import com.dastardlylabs.interfaces.ICommandItem;

public class DrawingPath implements ICommandItem{
	public Path path;
	public Paint paint;
	private Canvas canvas;
	private boolean finalized;
	private long[] xS, yS, timeCodes;
	private int[] actionS;

	private static final int MOVE_TO = 0;
	private static final int LINE_TO = 1;
	private static final int MOUSE_DOWN = 2;
	private static final int MOUSE_MOVE = 3;
	private static final int MOUSE_UP = 4;
	
	
	/**
	 * per path/command uses 0 as starting time reference
	 * only the parent (command queue, timeline, etc)
	 * knows its starting time in the outer scope
	 */
	//private long timeCode;
	
	private long duration;
	
	public DrawingPath(){
		path = new Path();
		paint = new Paint();
		finalized = false;
	}
	
	public void addAction(){
		
	}
	
	/**
	 * Similar to the executeAll of CommandManager
	 * Plays back the recorded path instructions in real-ish time
	 * @param canvas
	 */
	public void playBack(Canvas canvas /*, Callback*/){
		Path tempPath = new Path();
		long startTime = SystemClock.elapsedRealtime(),
			 nextActionTime = 0;
		
		for( int index : actionS){
			nextActionTime = timeCodes[index];
			while(startTime + nextActionTime > SystemClock.elapsedRealtime()){
				;//wait until the action time before triggering the next animation
			}
			doAction(index, tempPath);
		}
	}
	
	/**
	 * Animate Path drawing from previous state to the current action
	 * @param index index of the action
	 * @param playbackPath temporary Path for iterative display of the stroke
	 */
	private void doAction(int index, Path playbackPath){
		switch(actionS[index]){
		case MOVE_TO:
			playbackPath.moveTo(xS[index], yS[index]);
			break;
		case LINE_TO:
			break;
		case MOUSE_DOWN:
			break;
		case MOUSE_MOVE:
			break;
		case MOUSE_UP:
			break;			
		}
	}
	
	public void finish(){
		finalized = true;
		calculateDuration();
	}
	
	public long getDuration(){
		if(!finalized)
			calculateDuration();
		return duration;
	}
	
	private void calculateDuration(){
		//TODO complete calculation
		//Get timeCode from last pathCommand item
		duration = 0;
	}
	
	/**
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
