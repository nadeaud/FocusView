package org.eclipse.cdt.debug.focus.model;

import org.eclipse.jface.viewers.TableViewer;

public class BorderMinEditingSupport extends BorderEditingSupport {

	public BorderMinEditingSupport(TableViewer viewer) {
		super(viewer);
	}
	
	@Override
	protected Object getValue(Object element) {
		return Integer.toString(((Border)element).getMin());
	}
	
	@Override
	protected void setValue(Object element, Object userInputValue) {
		((Border)element).setMin(Integer.parseInt((String)userInputValue));
		viewer.update(element, null);
	}

}
