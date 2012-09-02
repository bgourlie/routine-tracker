package com.fr.client.common;

import java.util.Iterator;
import java.util.Vector;

import com.fr.client.rpc.CallbackScreener;
import com.fr.client.rpc.ScreenedCallback;

public class Bootstrap {

	private Vector callbacks = new Vector();

	private Iterator callbacksIterator;

	private BootstrapListenerCollection listeners = null;

	private int step;

	private Bootstrap thisBootstrapper = this;

	public void addBootstrapListener(BootstrapListener listener) {
		if (listeners == null) {
			listeners = new BootstrapListenerCollection();
		}
		listeners.add(listener);

	}

	public void addStep(ScreenedCallback callback) {
		callbacks.add(callback);
	}

	public void execute() {
		step = 1;
		callbacksIterator = callbacks.iterator();
		listeners.fireOnBootstrapStep(this, step);
	}

	public int getBootstrapSteps() {
		return callbacks.size();
	}

	public CallbackScreener getCallback() {
		if (step < callbacks.size()) {
			return new CallbackScreener((ScreenedCallback) callbacksIterator
					.next()) {

				public void onSuccess(Object result) {
					super.onSuccess(result);
					listeners.fireOnBootstrapStep(thisBootstrapper, ++step);
				}
			};
		} else {
			return new CallbackScreener((ScreenedCallback) callbacksIterator
					.next()) {

				public void onSuccess(Object result) {
					super.onSuccess(result);
					listeners.fireOnBootstrapComplete();
				}
			};
		}
	}
}
