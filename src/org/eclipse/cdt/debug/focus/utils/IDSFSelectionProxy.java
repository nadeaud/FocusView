package org.eclipse.cdt.debug.focus.utils;

import org.eclipse.cdt.dsf.concurrent.RequestMonitor;
import org.eclipse.cdt.dsf.datamodel.IDMContext;
import org.eclipse.cdt.dsf.gdb.multicorevisualizer.internal.utils.DSFSessionState;

@SuppressWarnings("restriction")
public interface IDSFSelectionProxy {
	
	public void setFocusSelection(DSFSessionState sessionState, RequestMonitor rm, String[] selections);

}
