package com.dastardlylabs.artsy.brushStrokes;

import java.util.Iterator;

import android.graphics.Canvas;
import android.os.Handler;

import com.dastardlylabs.interfaces.ICommandItem;

/**
 * 
 * @author juancgarcia
 * unused, until interface differences get resolved.
 *
 */

public class CanvasCommandManager extends CommandManager {
	
	public CanvasCommandManager(){
		super();
	}

	@Override
	public void executeAll( Canvas canvas, Handler completeHandler){
		if( currentStack != null ){
			synchronized( currentStack ) {
				final Iterator<ICommandItem> i = super.currentStack.iterator();
				while ( i.hasNext() ){
					final DrawingPath command = (DrawingPath) i.next();
					command.draw(canvas); //command.setContext(canvas); command.redo();
                    completeHandler.sendEmptyMessage(1);
				}
			}
		}
	}

	public void playBack( Canvas canvas, Handler completeHandler){
		if( currentStack != null ){
			synchronized( currentStack ) {
				final Iterator<ICommandItem> i = super.currentStack.iterator();
				while ( i.hasNext() ){
					final DrawingPath command = (DrawingPath) i.next();
					command.draw(canvas); //command.setContext(canvas); command.redo();
                    completeHandler.sendEmptyMessage(1);
				}
			}
		}
	}
}
