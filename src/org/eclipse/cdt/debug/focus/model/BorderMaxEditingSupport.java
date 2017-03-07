package org.eclipse.cdt.debug.focus.model;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TableViewer;

public class BorderMaxEditingSupport extends BorderEditingSupport {

	public BorderMaxEditingSupport(TableViewer viewer) {
		super(viewer);
	}
	
	@Override
	protected Object getValue(Object element) {
		return Integer.toString(((Border)element).getMax());
	}
	
	@Override
	protected void setValue(Object element, Object userInputValue) {
		((Border)element).setMax(Integer.parseInt((String)userInputValue));
		viewer.update(element, null);
	}
}
