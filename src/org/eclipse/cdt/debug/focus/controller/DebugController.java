package org.eclipse.cdt.debug.focus.controller;

import java.util.List;

import org.eclipse.cdt.debug.focus.model.Border;
import org.eclipse.cdt.debug.focus.utils.DSFSelectionProxy;
import org.eclipse.cdt.debug.focus.utils.IDSFSelectionProxy;
import org.eclipse.cdt.dsf.concurrent.ConfinedToDsfExecutor;
import org.eclipse.cdt.dsf.concurrent.DsfRunnable;
import org.eclipse.cdt.dsf.concurrent.ImmediateRequestMonitor;
import org.eclipse.cdt.dsf.gdb.launching.GDBProcess;
import org.eclipse.cdt.dsf.gdb.launching.GdbLaunch;
import org.eclipse.cdt.dsf.gdb.multicorevisualizer.internal.utils.DSFSessionState;
import org.eclipse.cdt.dsf.ui.viewmodel.datamodel.IDMVMContext;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.ui.DebugUITools;

public class DebugController {
	
	@SuppressWarnings("restriction")
	protected DSFSessionState fSessionState;
	protected IDSFSelectionProxy fSelectionProxy;
	
	public DebugController() {
		fSelectionProxy = new DSFSelectionProxy();
	}
	
	
	@SuppressWarnings("restriction")
	public void sendSelection(List<Border> borders) {
		StringBuilder sb = new StringBuilder();
		String[] array = new String[borders.size()*2];
		if (fSessionState == null) {
			if(!updateDebugContext())
				return;
		}
		
		int j = 0;
		for(int i = 0;i < borders.size(); i++) {
			Border border = borders.get(i);
			sb.append("-i");
			array[j++] = sb.toString();
			sb.setLength(0);
			sb.append(border.getAxis());
			sb.append(":");
			sb.append(border.getMin());
			sb.append(":");
			sb.append(border.getMax());
			array[j++] = sb.toString();
			sb.setLength(0);
		}		
		
		fSessionState.execute(new DsfRunnable() {

			@Override
			public void run() {
				fSelectionProxy.setFocusSelection(fSessionState, 
						new ImmediateRequestMonitor() {
							@Override
							protected void handleCompleted() {

							}
						},
						array
						);
					}
			});
	}
	
	protected boolean updateDebugContext()
	{			 
		String sessionId = null;
		IAdaptable debugContext = DebugUITools.getDebugContext();
		if (debugContext instanceof IDMVMContext) {
			sessionId = ((IDMVMContext)debugContext).getDMContext().getSessionId();
		} else if (debugContext instanceof GdbLaunch) {
			GdbLaunch gdbLaunch = (GdbLaunch)debugContext;
			if (gdbLaunch.isTerminated() == false) {
				sessionId = gdbLaunch.getSession().getId();
			}
		} else if (debugContext instanceof GDBProcess) {
			ILaunch launch = ((GDBProcess)debugContext).getLaunch();
			if (launch.isTerminated() == false &&
					launch instanceof GdbLaunch) {
				sessionId = ((GdbLaunch)launch).getSession().getId();
			}
		}
		return setDebugSession(sessionId);
	}

	protected boolean setDebugSession(String sessionId) {
		boolean changed = false;


		if (fSessionState != null &&
				! fSessionState.getSessionID().equals(sessionId))
		{			
			fSessionState.dispose();
			fSessionState = null;
			changed = true;
		}

		if (fSessionState == null &&
				sessionId != null)
		{
			fSessionState = new DSFSessionState(sessionId);
			//m_sessionState.addServiceEventListener(fEventListener);
			// start timer that updates the load meters
			changed = true;
		}
		return changed;
	}
}
