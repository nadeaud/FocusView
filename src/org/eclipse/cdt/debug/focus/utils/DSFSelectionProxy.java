package org.eclipse.cdt.debug.focus.utils;

import org.eclipse.cdt.dsf.concurrent.ConfinedToDsfExecutor;
import org.eclipse.cdt.dsf.concurrent.ImmediateRequestMonitor;
import org.eclipse.cdt.dsf.concurrent.RequestMonitor;
import org.eclipse.cdt.dsf.datamodel.DMContexts;
import org.eclipse.cdt.dsf.datamodel.IDMContext;
import org.eclipse.cdt.dsf.debug.service.IProcesses;
import org.eclipse.cdt.dsf.debug.service.IRunControl.IContainerDMContext;
import org.eclipse.cdt.dsf.debug.service.command.ICommandControlService;
import org.eclipse.cdt.dsf.gdb.multicorevisualizer.internal.utils.DSFSessionState;

@SuppressWarnings("restriction")
public class DSFSelectionProxy implements IDSFSelectionProxy {

	@Override
	@ConfinedToDsfExecutor("sessionState.getDsfSession().getExecutor()")
	public void setFocusSelection(DSFSessionState sessionState, RequestMonitor rm, String[] selections) {
		ICommandControlService controlService = sessionState.getService(ICommandControlService.class);
		final IProcesses procService = sessionState.getService(IProcesses.class);

		if (procService == null || controlService == null) {
			rm.done();
			return;
		}

		procService.setProcessSelection(controlService.getContext(),
			new ImmediateRequestMonitor() {
				@Override
				protected void handleCompleted() {
					if(!isSuccess()) {
						rm.done();
					}
					return;
				}
			},
			selections, null);
			
	}

}
