package com.obs.quizzer.app;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class App
{
   //Construct the application
   public App(String[] args)
   {
      if (args == null || args.length != 1)
      {
         JOptionPane.showMessageDialog(null, "Directory containing .ccc files must be passed-in on command line", "Quizzer Initialization", JOptionPane.ERROR_MESSAGE);
         System.exit(1);
      }

      final Frame frame = new Frame(args[0]);
      frame.pack();
      frame.setSize(800, 600);
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   }

   //Main method
   public static void main(String[] args)
   {
      try
      {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }

      new App(args);
   }
}
