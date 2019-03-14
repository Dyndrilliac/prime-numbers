/*
 * Title: Prime Number Generator
 * Author: Matthew Boyette
 * Date: 2/21/2012
 * 
 * This application generates the first 'n' prime numbers and displays them to the user. The method used to test for primality is fairly efficient but
 * will naturally pale in comparison to industry standard libraries optimized for this task. This is merely proof of concept code. It will also
 * approximate the amount of time in seconds that it took to generate the list.
 */

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import api.gui.swing.ApplicationWindow;
import api.gui.swing.RichTextPane;
import api.util.EventHandler;
import api.util.Mathematics;
import api.util.Support;
import edu.princeton.cs.introcs.Stopwatch;

public class PrimeNumbers
{
    public final static void main(final String[] args)
    {
        new PrimeNumbers(args);
    }
    
    public final static void printPrimes(final long numberOfPrimes, final RichTextPane output)
    {
        Stopwatch stopwatch = new Stopwatch();
        
        for (long i = 0, j = 1; j <= numberOfPrimes; i++)
        {
            if (Mathematics.isPrime(i))
            {
                output.append(Color.BLACK, Color.WHITE, "[" + Support.getDateTimeStamp() + "]: ", Color.BLUE, Color.WHITE, "Prime #" + (j) +
                    ": " +
                    i +
                    "\n");
                j++;
            }
        }
        
        output.append(Color.BLACK,
            Color.WHITE,
            "\n[" + Support.getDateTimeStamp() + "]: ",
            Color.BLUE,
            Color.WHITE,
            "Execution time (in seconds): " + stopwatch.elapsedTime() + ".\n\n");
    }
    
    private boolean           isDebugging = false;
    private RichTextPane      output      = null;
    private ApplicationWindow window      = null;
    
    public PrimeNumbers(final String[] args)
    {
        this.setDebugging(Support.promptDebugMode(this.getWindow()));
        
        // Define a self-contained ActionListener event handler.
        // @formatter:off
        EventHandler<PrimeNumbers> myActionPerformed = new EventHandler<PrimeNumbers>(this)
        {
            private final static long serialVersionUID = 1L;
            
            @Override
            public final void run(final AWTEvent event)
            {
                ActionEvent actionEvent = (ActionEvent)event;
                PrimeNumbers parent = this.getParent();
                
                if (parent.getOutput() != null)
                {
                    /*
                     * JDK 7 allows string objects as the expression in a switch statement. This generally produces more efficient byte code compared
                     * to a chain of if statements. http://docs.oracle.com/javase/7/docs/technotes/guides/language/strings-switch.html
                     */
                    switch (actionEvent.getActionCommand())
                    {
                        case "Clear":
                            
                            parent.getOutput().clear();
                            break;
                        
                        case "Open":
                            
                            parent.getOutput().openOrSaveFile(true);
                            break;
                        
                        case "Save":
                            
                            parent.getOutput().openOrSaveFile(false);
                            break;
                        
                        case "Print Prime Numbers":
                            
                            PrimeNumbers.printPrimes(Support.getIntegerInputString(parent.getWindow(),
                                "What value to use for 'n'?\n\n" + "Note: 'n' represents the upper bound for the reporting of prime numbers.\n"
                                    + "Therefore, if 'n' is 5 for example, then the program will report the first 5 prime numbers.",
                                "Set 'n'"), parent.getOutput());
                            break;
                        
                        default:
                            
                            break;
                    }
                }
            }
        };
        
        // Define a self-contained interface construction event handler.
        EventHandler<PrimeNumbers> myDrawGUI = new EventHandler<PrimeNumbers>(this)
        {
            private final static long serialVersionUID = 1L;
            
            @Override
            public final void run(final ApplicationWindow window)
            {
                PrimeNumbers parent = this.getParent();
                Container contentPane = window.getContentPane();
                JMenuBar menuBar = new JMenuBar();
                JMenu fileMenu = new JMenu("File");
                JMenuItem clearOption = new JMenuItem("Clear");
                JMenuItem openOption = new JMenuItem("Open");
                JMenuItem saveOption = new JMenuItem("Save");
                RichTextPane outputBox = new RichTextPane(window, true, window.isDebugging());
                JButton button = new JButton("Print Prime Numbers");
                JScrollPane outputPanel = new JScrollPane(outputBox);
                JPanel inputPanel = new JPanel();
                
                menuBar.setFont(Support.DEFAULT_TEXT_FONT);
                fileMenu.setFont(Support.DEFAULT_TEXT_FONT);
                clearOption.setFont(Support.DEFAULT_TEXT_FONT);
                openOption.setFont(Support.DEFAULT_TEXT_FONT);
                saveOption.setFont(Support.DEFAULT_TEXT_FONT);
                contentPane.setLayout(new BorderLayout());
                clearOption.addActionListener(window);
                fileMenu.add(clearOption);
                openOption.addActionListener(window);
                fileMenu.add(openOption);
                saveOption.addActionListener(window);
                fileMenu.add(saveOption);
                menuBar.add(fileMenu);
                window.setJMenuBar(menuBar);
                fileMenu.setMnemonic('F');
                openOption.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.KeyEvent.CTRL_DOWN_MASK));
                openOption.setMnemonic('O');
                saveOption.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.KeyEvent.CTRL_DOWN_MASK));
                saveOption.setMnemonic('S');
                clearOption.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.KeyEvent.CTRL_DOWN_MASK));
                clearOption.setMnemonic('C');
                inputPanel.setLayout(new FlowLayout());
                button.setFont(Support.DEFAULT_TEXT_FONT);
                button.addActionListener(window);
                inputPanel.add(button);
                contentPane.add(outputPanel, BorderLayout.CENTER);
                contentPane.add(inputPanel, BorderLayout.SOUTH);
                parent.setOutput(outputBox);
            }
        };
        
        this.setWindow(new ApplicationWindow(null,
            "Prime Numbers Application",
            new Dimension(800, 600),
            this.isDebugging(),
            false,
            myActionPerformed,
            myDrawGUI));
        this.getWindow().setIconImageByResourceName("icon.png");
        // @formatter:on
    }
    
    public final RichTextPane getOutput()
    {
        return this.output;
    }
    
    public final ApplicationWindow getWindow()
    {
        return this.window;
    }
    
    public final boolean isDebugging()
    {
        return this.isDebugging;
    }
    
    protected final void setDebugging(final boolean isDebugging)
    {
        this.isDebugging = isDebugging;
    }
    
    protected final void setOutput(final RichTextPane output)
    {
        this.output = output;
    }
    
    protected final void setWindow(final ApplicationWindow window)
    {
        this.window = window;
    }
}