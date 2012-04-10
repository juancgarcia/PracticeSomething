package com.dastardlylabs.artsy.brushStrokes;

import android.graphics.Canvas;

public class AnimationPath extends DrawingPath {
	//should generalize drawing Path a bit more and have the ICommand and IAnimationFrame be implemented in a DrawingPath extension/child
	private long[] timeStamps, xS, yS;
	
	public void start(){
//		startTime = System.currentTimeMillis();
	}
	
	public void end(){
//		endTime = System.currentTimeMillis();
	}
	
	public void animate(Canvas canvas) {
		//
	}

}
