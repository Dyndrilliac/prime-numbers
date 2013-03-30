/*
	Title:  Prime Number Generator
	Author: Matthew Boyette
	Date:   2/21/2012
	
	This application generates the first 'n' prime numbers and displays them to the user. The method used to test for primality is fairly
	efficient but will naturally pale in comparison to industry standard libraries optimized for this task. This is merely proof of concept
	code. It will also approximate the amount of time in seconds that it took to generate the list.
*/

import api.gui.*;
import api.util.*;

import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;

public final class PrimeNumbers
{
	private static final void handleInput(final Object... arguments)
	{
		ApplicationWindow window = (ApplicationWindow)arguments[1];
		RichTextPane      output = null;
		
		for (int i = 0; i < window.getElements().size(); i++)
		{
			if (window.getElements().get(i) instanceof RichTextPane)
			{
				output = (RichTextPane)window.getElements().get(i);
			}
		}
		
		printPrimes(500, output);
	}
	
	public static final void main(final String[] args)
	{
		ApplicationWindow mainWindow = null;
		int choice = Support.promptDebugMode(mainWindow);
		
		// Define a self-contained ActionListener event handler.
		EventHandler myActionPerformed = new EventHandler()
		{
			public final void run(final Object... arguments) throws IllegalArgumentException
			{
				if ((arguments.length <= 1) || (arguments.length > 2))
				{
					throw new IllegalArgumentException("myActionPerformed Error : incorrect number of arguments.");
				}
				else if (!(arguments[0] instanceof ActionEvent))
				{
					throw new IllegalArgumentException("myActionPerformed Error : argument[0] is of incorrect type.");
				}
				else if (!(arguments[1] instanceof ApplicationWindow))
				{
					throw new IllegalArgumentException("myActionPerformed Error : argument[1] is of incorrect type.");
				}
				
				ActionEvent       event  = (ActionEvent)arguments[0];
				ApplicationWindow window = (ApplicationWindow)arguments[1];
				RichTextPane      output = null;
				
				for (int i = 0; i < window.getElements().size(); i++)
				{
					if (window.getElements().get(i) instanceof RichTextPane)
					{
						output = (RichTextPane)window.getElements().get(i);
					}
				}
				
				/*
					JDK 7 allows string objects as the expression in a switch statement.
					This generally produces more efficient byte code compared to a chain of if statements.
					http://docs.oracle.com/javase/7/docs/technotes/guides/language/strings-switch.html
				*/
				switch (event.getActionCommand())
				{
					case "Clear":
						
						output.clear();
						break;
						
					case "Open":
						
						output.openFile();
						break;
						
					case "Save":
						
						output.saveFile();
						break;
						
					case "Print Prime Numbers":
						
						handleInput(arguments);
						break;
						
					default:
						
						break;
				}
			}
		};
		
		// Define a self-contained interface construction event handler.
		EventHandler myDrawGUI = new EventHandler()
		{
			public final void run(final Object... arguments) throws IllegalArgumentException
			{
				if (arguments.length <= 0)
				{
					throw new IllegalArgumentException("myDrawGUI Error : incorrect number of arguments.");
				}
				else if (!(arguments[0] instanceof ApplicationWindow))
				{
					throw new IllegalArgumentException("myDrawGUI Error : argument[0] is of incorrect type.");
				}
				
				ApplicationWindow window      = (ApplicationWindow)arguments[0];
				Container         contentPane = window.getContentPane();
				JMenuBar          menuBar     = new JMenuBar();
				JMenu             fileMenu    = new JMenu("File");
				JMenuItem         clearOption = new JMenuItem("Clear");
				JMenuItem         openOption  = new JMenuItem("Open");
				JMenuItem         saveOption  = new JMenuItem("Save");
				Font              outputFont  = new Font("Lucida Console", Font.PLAIN, 14);
				RichTextPane      outputBox   = new RichTextPane((Component)window, true, window.isDebugging(), outputFont);
				JButton           button      = new JButton("Print Prime Numbers");
				
				contentPane.setLayout(new BorderLayout());
				clearOption.addActionListener(window);
				fileMenu.add(clearOption);
				openOption.addActionListener(window);
				fileMenu.add(openOption);
				saveOption.addActionListener(window);
				fileMenu.add(saveOption);
				menuBar.add(fileMenu);
				window.setJMenuBar(menuBar);
				
				JScrollPane outputPanel = new JScrollPane(outputBox);
				JPanel      inputPanel  = new JPanel();
				
				inputPanel.setLayout(new FlowLayout());
				button.setPreferredSize(new Dimension(175, 20));
				button.addActionListener(window);
				inputPanel.add(button);
				contentPane.add(outputPanel, BorderLayout.CENTER);
				contentPane.add(inputPanel, BorderLayout.SOUTH);
				window.getElements().add(outputBox);
			}
		};
		
		if (choice == JOptionPane.YES_OPTION)
		{
			mainWindow = new ApplicationWindow(null, "Prime Numbers Application", new Dimension(800, 600), true, false, 
				true, myActionPerformed, myDrawGUI);
		}
		else if (choice == JOptionPane.NO_OPTION)
		{
			mainWindow = new ApplicationWindow(null, "Prime Numbers Application", new Dimension(800, 600), false, false, 
				true, myActionPerformed, myDrawGUI);
		}
		else
		{
			return;
		}
		
		mainWindow.drawGUI();
		mainWindow.setVisible(true);
	}
	
	private static final void printPrimes(final long numberOfPrimes, final RichTextPane output)
	{
		long startTime = System.currentTimeMillis();

		for (long i = 0, j = 1; j <= numberOfPrimes; i++)
		{
			if (Support.isPrime(i))
			{
				output.append(Color.BLACK, Color.WHITE, "[" + Support.getDateTimeStamp() + "]: ", 
					Color.BLUE, Color.WHITE, "Prime #" + (j) + ": " + i + "\n");
				j++;
			}
		}

		long stopTime = System.currentTimeMillis();
		output.append(Color.BLACK, Color.WHITE, "\n[" + Support.getDateTimeStamp() + "]: ", 
			Color.BLUE, Color.WHITE, "Execution time (in seconds): " + ((double)(stopTime - startTime) / 1000.0) + ".\n\n");
	}
}