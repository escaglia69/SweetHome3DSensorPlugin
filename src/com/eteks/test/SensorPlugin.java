package com.eteks.test;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import com.eteks.sweethome3d.model.RecorderException;
import com.eteks.sweethome3d.plugin.Plugin;
import com.eteks.sweethome3d.plugin.PluginAction;

public class SensorPlugin extends Plugin {

	public class SensorAction extends PluginAction {
		
		String url = null;
		Timer checkTimer = null;
		public SensorAction() {
			 if (!getUserPreferences().getAutoCompletionStrings("jsonUrl").isEmpty()) {
				 url = getUserPreferences().getAutoCompletionStrings("jsonUrl").get(0);
			 }
			 putPropertyValue(Property.NAME, "Enable Sensors");
	         putPropertyValue(Property.MENU, "3D view");
	         //putPropertyValue(Property.TOOL_BAR, true);
	         setEnabled(true);
	         checkTimer = new Timer(20000,new SensorFeedActionListener(getHome(), url));
	         checkTimer.start();
		}

		@Override
		public void execute() {
	         url = JOptionPane.showInputDialog("URL JSON service:",url);
	         getUserPreferences().addAutoCompletionString("jsonUrl", url);
	         try {
	        	 getUserPreferences().write();
		         checkTimer.stop();
		         checkTimer.addActionListener(new SensorFeedActionListener(getHome(), url));
		         checkTimer.start();
	         } catch (RecorderException re) {
	        	 System.err.println("Prefs writing error");
	         }
		}
	}

	@Override
	public PluginAction[] getActions() {
		return new PluginAction [] {new SensorAction()};
	}

}
