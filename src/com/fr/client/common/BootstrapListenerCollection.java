package com.fr.client.common;

import java.util.Iterator;
import java.util.Vector;

public class BootstrapListenerCollection extends Vector {

	private static final long serialVersionUID = 1L;

	public void fireOnBootstrapStep(Bootstrap bootstrapper, int step) {
		for (Iterator it = iterator(); it.hasNext();) {
			BootstrapListener listener = (BootstrapListener) it.next();
			listener.onBootstrapStep(bootstrapper, step);
		}
	}

	public void fireOnBootstrapComplete() {
		for (Iterator it = iterator(); it.hasNext();) {
			BootstrapListener listener = (BootstrapListener) it.next();
			listener.onBootstrapComplete();
		}
	}
}
