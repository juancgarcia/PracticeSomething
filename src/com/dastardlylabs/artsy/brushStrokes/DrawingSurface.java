package com.dastardlylabs.artsy.brushStrokes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class DrawingSurface extends SurfaceView implements SurfaceHolder.Callback, Runnable {
	public CommandManager commandManager;
	
	Thread drawingThread = null;
	SurfaceHolder mSurfaceHolder = null;
	private boolean runState;
	
	public boolean isDrawing = true;
	public DrawingPath previewPath;
	
	private Handler previewDoneHandler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			isDrawing = false;
		}
	};

	public DrawingSurface(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		commandManager = new CanvasCommandManager();

		mSurfaceHolder = getHolder();
		mSurfaceHolder.addCallback(this);
		
		resume();
	}
	
	public void resume(){
		runState = true;
		drawingThread = new Thread(this);
		drawingThread.start();	
	}
	
	public void pause(){
		runState = false;
		
		while (true) {
			try {
				drawingThread.join();
			} catch (InterruptedException e) {
				//
			}
			break;
		}
		drawingThread = null;
	}

	@Override
	public void run() {
		Canvas canvas = null;
		while (runState){
			if(!mSurfaceHolder.getSurface().isValid())
				continue;
			try{
				canvas = mSurfaceHolder.lockCanvas(null);
				canvas.drawColor(0, PorterDuff.Mode.CLEAR);
				commandManager.executeAll(canvas, previewDoneHandler);
				previewPath.draw((Canvas) canvas);
			}
			catch(Exception e) {
				;
			}
			finally {
				mSurfaceHolder.unlockCanvasAndPost(canvas);
			}
		}		
	}

	public void addDrawingPath (DrawingPath drawingPath){
		//thread.addDrawingPath(drawingPath);
		commandManager.addCommand(drawingPath);
	}
	
	public boolean hasMoreRedo(){
		return commandManager.hasMoreRedo();
	}
	
	public boolean hasMoreUndo(){
		return commandManager.hasMoreUndo();
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		//thread.changeSurfaceHolder(arg0);
		mSurfaceHolder = arg0;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		//thread.setRunning(true);
		//thread.start();		
		resume();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		//thread.setRunning(false);
		pause();
	}
}