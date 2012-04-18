package com.dastardlylabs.artsy.brushStrokes;


public class BrushStrokeAnimation {
	
	/** offset used as reference for timing frames, can be updated when */
	public long startTimeCodeRef;
	
	/** date-time the animation was opened/created */
	public long creationDate;
	
	/** if the animation was edited this would reflect a different date than creationDate */
	public long modifiedDate;				
	
	/** Ordered List of frames composing this animation */
	//public List<IAnimationFrame> drawingSequence;
	
	//public LinkedHashMap<Integer,IAnimationFrame> keyFrames;
	
	//public IAnimationFrame currentFrame;
	
	/**
	 * Constructor, sets the
	 */
	public BrushStrokeAnimation(){
		creationDate = System.currentTimeMillis();
		modifiedDate = creationDate;
		
		//keyFrames = new LinkedHashMap<Integer, IAnimationFrame>();
		
		//currentFrame = new PlaceholderFrame();
		//currentFrame = new DrawingFrame();
		//currentFrame.recordStart();
		
		
		reload();
	}
	
	
	/**
	 * resets the global time code reference for this animation
	 */
	public void reload(){
		//startTimeCodeRef = SystemClock.elapsedRealtime();
		startTimeCodeRef = System.nanoTime();		
	}
	
	public void addFrame(){
		
	}
	
	//public void addFrame(IAnimationFrame frame){
		//currentFrame.recordEnd();
		//keyFrames.put(0, currentFrame);
		
		//currentFrame = frame;
	//}
	
	/**
	 * triggers animation of the currentFrame
	 */
	//private void animateFrame(){
		
	//}
}
