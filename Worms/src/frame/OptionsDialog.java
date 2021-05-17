package frame;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;

import colors.Colors;
import options.Options;

/**
 * Displays the options menu where all the game settings can be changed
 */
public class OptionsDialog extends JDialog {
	
	/**Stores all the setting components*/
	ArrayList<JComponent> elements;
	/**An option with all the set values*/
	Options opt;
	
	/**
	 * Creates the option menu.
	 */
	public OptionsDialog() {
		elements = new ArrayList<JComponent>();
		opt = new Options(3,3,70,Colors.IntToColor(1),Colors.IntToColor(0),false,false);
		
		if(new File(System.getProperty("user.dir")+"/options/current.ser").exists()) {
			opt = opt.readOption(System.getProperty("user.dir")+"/options/current.ser");
		}
		
		setSize(500,300);
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		setResizable(false);
		setTitle("Options");
		
		SpinnerNumberModel model1 = new SpinnerNumberModel(opt.teamSize,1,5,1);
		SpinnerNumberModel model2 = new SpinnerNumberModel(opt.maptype,1,5,1);
		SpinnerNumberModel model3 = new SpinnerNumberModel(opt.maxHP,10,500,10);
		
		String[] colors = {"Blue", "Red", "Green", "Yellow"};
		String[] files = new File(System.getProperty("user.dir")+"/options").list();
		
		setLayout(new GridLayout(9,2));		
		
		elements.add(new JLabel(" Team Size:"));       //0
		elements.add(new JSpinner(model1));
		elements.add(new JLabel(" Mountains size:"));
		elements.add(new JSpinner(model2));
		elements.add(new JLabel(" Max. Health:"));
		elements.add(new JSpinner(model3));  //5
		elements.add(new JLabel(" Color 1:"));
		elements.add(new JComboBox(colors)); 
		((JComboBox)elements.get(7)).setSelectedIndex(Colors.ColorToInt(opt.color1));
		elements.add(new JLabel(" Color 2:"));
		elements.add(new JComboBox(colors));
		((JComboBox)elements.get(9)).setSelectedIndex(Colors.ColorToInt(opt.color2));
		elements.add(new JLabel(" Caves:"));   //10
		elements.add(new JCheckBox("", opt.caves));
		elements.add(new JLabel(" Symmetric:"));			
		elements.add(new JCheckBox("", opt.symmetric));
		elements.add(new JComboBox(files));
		elements.add(new JButton("Load"));//15	
		elements.add(new JButton("Save"));
		elements.add(new JButton("Ok")); //17
			
		
		for (int i = 0; i< elements.size(); i++) {
			add(elements.get(i));
		}
				
		((JButton)elements.get(17)).addActionListener(new okClick());
		((JButton)elements.get(16)).addActionListener(new saveClick());
		((JButton)elements.get(15)).addActionListener(new loadClick());
	}
	
	
	/**
	 *actionPerformed() is called, when the Ok button is clicked.
	 *Saves the current values for the current game
	 */
	final class okClick implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(((JComboBox)elements.get(7)).getSelectedIndex()==((JComboBox)elements.get(9)).getSelectedIndex()) {
				JOptionPane.showMessageDialog(new JFrame(), "Please select different colors!",
			    "Error", JOptionPane.ERROR_MESSAGE);
			}
			else {
				opt = createOpt();
				opt.writeOption(System.getProperty("user.dir")+"/options/current.ser");
				dispose();
			}
		}
	}
	
	/**
	 *actionPerformed() is called, when the Save button is clicked.
	 *Saves the values for the current game
	 *and also the option can be loaded later
	 */
	final class saveClick implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(((JComboBox)elements.get(7)).getSelectedIndex()==((JComboBox)elements.get(9)).getSelectedIndex()) {
				JOptionPane.showMessageDialog(new JFrame(), "Please select different colors!",
			    "Error", JOptionPane.ERROR_MESSAGE);
			}
			else {
				opt = createOpt();
				opt.writeOption(System.getProperty("user.dir")+"/options/"
						+"T_"+opt.teamSize+" m_"+opt.maptype+" hp_"+opt.maxHP+" _"+
						Colors.ColorToString(opt.color1)+" _"+Colors.ColorToString(opt.color2)+" c_"+(opt.caves?"Y":"N")+" s_"+
						(opt.symmetric?"Y":"N")+".ser");
				opt.writeOption(System.getProperty("user.dir")+"/options/current.ser");
				dispose();
			}	        
		}
	}
	
	/**
	 *actionPerformed() is called, when the Load button is clicked.
	 *Loads a saved option
	 */
	final class loadClick implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {				
			opt = opt.readOption(System.getProperty("user.dir")+"/options/"+((JComboBox)elements.get(14)).getSelectedItem().toString());
			opt.writeOption(System.getProperty("user.dir")+"/options/current.ser");
			dispose();					
		}
	}
	
	/**
	 * Gets values from the GUI and creates options object
	 * @return the created options object
	 */
	public Options createOpt() {
		return new Options((Integer)(((JSpinner)(elements.get(1))).getValue()),
     			(Integer)(((JSpinner)elements.get(3)).getValue()),
     			(Integer)(((JSpinner)elements.get(5)).getValue()),
     			Colors.IntToColor(((JComboBox)elements.get(7)).getSelectedIndex()),
     			Colors.IntToColor(((JComboBox)elements.get(9)).getSelectedIndex()),
     			((JCheckBox)elements.get(11)).isSelected(),
     			((JCheckBox)elements.get(13)).isSelected());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
