package com.dastardlylabs.interfaces;

public interface ICommandItem {
	public void setContext(Object context);
	public void redo();
	public void undo();
}
