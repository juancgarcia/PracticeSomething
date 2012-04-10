package com.dastardlylabs.artsy.brushStrokes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class DrawingSurface extends SurfaceView implements SurfaceHolder.Callback {
	//private Boolean _run;
	protected DrawThread thread;
	private CommandManager commandManager;
	
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
		getHolder().addCallback(this);
		thread = new DrawThread(getHolder());
		commandManager = new CommandManager();
	}

	class DrawThread extends Thread{
		private SurfaceHolder mSurfaceHolder;
		private List<DrawingPath> mDrawingPaths;
		private boolean runState;
		
		public DrawThread(SurfaceHolder surfaceHolder){
			mSurfaceHolder = surfaceHolder;
			mDrawingPaths = Collections.synchronizedList(new ArrayList<DrawingPath>());
		}

		public void setRunning(boolean state){
			this.runState = state;
		}

		public void addDrawingPath(DrawingPath drawingPath){
			mDrawingPaths.add( drawingPath );
		}
		
		public void changeSurfaceHolder(SurfaceHolder sHolder){
			mSurfaceHolder = sHolder;
		}

		@Override
		public void run() {
			Canvas canvas = null;
			while (runState){
				try{
					canvas = mSurfaceHolder.lockCanvas(null);
					canvas.drawColor(0, PorterDuff.Mode.CLEAR);
					commandManager.executeAll(canvas, previewDoneHandler);
					previewPath.draw((Canvas) canvas);
					//synchronized(mDrawingPaths) {
					//	Iterator<DrawingPath> i = mDrawingPaths.iterator();
					//	while (i.hasNext()){
					//		final DrawingPath drawingPath = (DrawingPath) i.next();
					//		canvas.drawPath(drawingPath.path, drawingPath.paint);
					//	}
					//}
				}
//				catch(Exception e) {
//					;
//				}
				finally {
					mSurfaceHolder.unlockCanvasAndPost(canvas);
				}
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
	
	public void redo(){
		commandManager.redo();
	}
	
	public void undo(){
		commandManager.undo();
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		thread.changeSurfaceHolder(arg0);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		boolean retry = true;
		thread.setRunning(false);
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				//
			}
		}
	}
}//</DrawingPath></DrawingPath>