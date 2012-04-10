package com.dastardlylabs.artsy.brushStrokes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import android.graphics.Canvas;
import android.os.Handler;

import com.dastardlylabs.interfaces.ICanvasCommand;

/**
 * 
 * @author juancgarcia
 * unused, until interface differences get resolved.
 *
 */

public class CanvasCommandManager extends CommandManager {
	protected List<ICanvasCommand> currentStack;
	protected List<ICanvasCommand> redoStack;
	
	public CanvasCommandManager(){
		super();
		currentStack = Collections.synchronizedList(new ArrayList<ICanvasCommand>());
		redoStack = Collections.synchronizedList(new ArrayList<ICanvasCommand>());
	}

	public void executeAll( Canvas canvas, Handler completeHandler){
		if( currentStack != null ){
			synchronized( currentStack ) {
				final Iterator<ICanvasCommand> i = currentStack.iterator();
				while ( i.hasNext() ){
					final ICanvasCommand command = (ICanvasCommand) i.next();
					command.draw(canvas); //command.setContext(canvas); command.redo();
                    completeHandler.sendEmptyMessage(1);
				}
			}
		}
	}
}
