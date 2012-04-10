package com.dastardlylabs.artsy.brushStrokes;

import com.dastardlylabs.interfaces.IAnimationFrame;

public class DrawingFrame implements IAnimationFrame {
	public long startOffset = 0, duration = 0;

	@Override
	public void start() {
		startOffset = System.currentTimeMillis();
		
	}

	@Override
	public void end() {
		duration = System.currentTimeMillis() - startOffset;
	}
	
	
}
