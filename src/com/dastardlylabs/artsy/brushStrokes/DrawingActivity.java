package com.dastardlylabs.artsy.brushStrokes;

import android.app.Activity;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
//import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.dastardlylabs.artsy.tools.Brush;
import com.dastardlylabs.artsy.tools.PenBrush;

public class DrawingActivity extends Activity implements View.OnTouchListener, View.OnClickListener{
	private DrawingSurface drawingSurface;
	private DrawingPath currentDrawingPath;
	private Paint currentPaint;
	private Brush currentBrush;
//	private static final String TAG = "DrawingActivity";
	private static final float thick = 9;
	private static final float thin = 3;
	private static final int colorRed = 0xFFFF0000, colorGreen = 0xFF00FF00, colorBlue = 0xFF0000FF;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drawing_activity);
		
		drawingSurface = (DrawingSurface) findViewById(R.id.drawingSurface);
		drawingSurface.setOnTouchListener(this);		

	    drawingSurface.previewPath = getPreviewPath();
	    
	    currentBrush = new PenBrush();
		
		setButtonListeners();
	}

	@Override
	protected void onPause() {
		super.onPause();
		drawingSurface.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		drawingSurface.resume();
	}
	
	private void setButtonListeners(){
		((Button) findViewById(R.id.colorRedBtn)).setOnClickListener(this);
		((Button) findViewById(R.id.colorBlueBtn)).setOnClickListener(this);
		((Button) findViewById(R.id.colorGreenBtn)).setOnClickListener(this);
		((Button) findViewById(R.id.undoBtn)).setOnClickListener(this);
		((Button) findViewById(R.id.redoBtn)).setOnClickListener(this);
	}

	private Paint getCurrentPaint(){
		if(currentPaint == null){
			currentPaint = new Paint();
			currentPaint.setDither(true);
			currentPaint.setColor(colorGreen);
			currentPaint.setStyle(Paint.Style.STROKE);
			currentPaint.setStrokeJoin(Paint.Join.ROUND);
			currentPaint.setStrokeCap(Paint.Cap.ROUND);
			currentPaint.setStrokeWidth(thin);
		}
		return currentPaint;
	}
	
	private DrawingPath getPreviewPath(){
		return getCurrentDrawingPath(null, true);
	}
	
	private DrawingPath getCurrentDrawingPath(Path path){
		return getCurrentDrawingPath(path, false);
	}
	
	private DrawingPath getCurrentDrawingPath(Path path, boolean isPreview){
		DrawingPath dPath = new DrawingPath();
		dPath.path = (path != null)? path: new Path();
		dPath.paint = (isPreview)? getCurrentPaint(): new Paint(getCurrentPaint());
		return dPath;		
	}

	public boolean onTouch(View view, MotionEvent motionEvent) {
		switch(motionEvent.getAction()){
			case MotionEvent.ACTION_DOWN:
				drawingSurface.previewPath.addAction();
				currentBrush.mouseDown(drawingSurface.previewPath.path, motionEvent.getX(), motionEvent.getY());				
				break;
				
			case MotionEvent.ACTION_MOVE:
				drawingSurface.previewPath.addAction();
				currentBrush.mouseMove(drawingSurface.previewPath.path, motionEvent.getX(), motionEvent.getY());				
				break;
				
			case MotionEvent.ACTION_UP:				
				currentBrush.mouseUp(drawingSurface.previewPath.path, motionEvent.getX(), motionEvent.getY());
				drawingSurface.previewPath.finish();
				
				currentDrawingPath = getCurrentDrawingPath(drawingSurface.previewPath.path);
				drawingSurface.addDrawingPath(currentDrawingPath);				
				
				//reset previewPath
				drawingSurface.previewPath = getPreviewPath();				
				break;
				
			default:
		}		
		return true; //allows onTouch to continue looping and reporting x,y on drag events
	}

	public void onClick(View view){
		switch (view.getId()){
		case R.id.colorRedBtn:
			currentPaint = getCurrentPaint();
			currentPaint.setColor(colorRed);
			break;
		case R.id.colorGreenBtn:
			currentPaint = getCurrentPaint();
			currentPaint.setColor(colorGreen);
			break;
		case R.id.colorBlueBtn:
			currentPaint = getCurrentPaint();
			currentPaint.setColor(colorBlue);
			break;
		case R.id.circleBtn:
			currentPaint = getCurrentPaint();
			currentPaint.setStrokeWidth(thick);
			break;
		case R.id.pathBtn:
			currentPaint = getCurrentPaint();
			currentPaint.setStrokeWidth(thin);
			break;
		case R.id.undoBtn:
			drawingSurface.commandManager.undo();
			//disable undo
			view.setEnabled(drawingSurface.hasMoreUndo());	
			((Button) findViewById(R.id.redoBtn)).setEnabled(true);
			break;
		case R.id.redoBtn:
			drawingSurface.commandManager.redo();
			view.setEnabled(drawingSurface.hasMoreRedo());			
			((Button) findViewById(R.id.undoBtn)).setEnabled(true);
			break;
		}
	}
}
