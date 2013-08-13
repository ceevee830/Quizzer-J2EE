package com.obs.quizzer.app;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.border.EtchedBorder;

import com.obs.quizzer.pojo.Quiz;

class Frame extends JFrame
{
   JToolBar m_toolBar = new JToolBar();
   static JLabel m_statusBarPercentage = new JLabel();
   static JLabel m_statusBarQuestionsLeft = new JLabel();

   private JPanel m_contentPane;
   private JPanel m_panelStatusBars = new JPanel();
   private JMenuBar m_menuBar1 = new JMenuBar();
   private JMenu m_menuFile = new JMenu();
   private JMenuItem m_menuFileExit = new JMenuItem();
   private JMenu menuHelp = new JMenu();
   private JMenuItem m_menuHelpAbout = new JMenuItem();
   private AbstractAction m_open;
   private AbstractAction m_close;
   private String m_title = "Quizzer Program";
   private String m_dir;
   private File m_quizzerfile;
   private QuestionPanel m_panel;
   private boolean m_logoption;
   private Quiz m_questionMgr = null;

   //Construct the frame
   Frame(String dir)
   {
      m_dir = dir;

      enableEvents(AWTEvent.WINDOW_EVENT_MASK);

      try
      {
         jbInit();
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
   }

   //Component initialization
   private void jbInit() throws Exception
   {
      ImageIcon image1 = new ImageIcon(Frame.class.getResource("resources/openFile.gif"));
      ImageIcon image2 = new ImageIcon(Frame.class.getResource("resources/closeFile.gif"));

      m_open = new AbstractAction("Open", image1)
      {
         public void actionPerformed(ActionEvent e)
         {
            menuFileOpen_actionPerformed(e);
         }
      };

      m_close = new AbstractAction("Close", image2)
      {
         public void actionPerformed(ActionEvent e)
         {
            menuFileClose_actionPerformed(e);
         }
      };

      m_close.setEnabled(false);
      m_contentPane = (JPanel) this.getContentPane();
      m_contentPane.setLayout(new BorderLayout());
      this.setSize(new Dimension(800, 600));
      this.setTitle(m_title);
      m_statusBarPercentage.setBorder(new EtchedBorder(EtchedBorder.RAISED, Color.white, new Color(134, 134, 134)));
      m_statusBarQuestionsLeft.setBorder(new EtchedBorder(EtchedBorder.RAISED, Color.white, new Color(134, 134, 134)));
      m_statusBarPercentage.setText(" ");
      m_statusBarQuestionsLeft.setText(" ");
      m_statusBarQuestionsLeft.setFont(new Font("Ariel", Font.PLAIN, 20));
      m_statusBarPercentage.setFont(new Font("Ariel", Font.PLAIN, 20));

      m_menuFile.setText("File");
      m_menuFileExit.setText("Exit");
      menuHelp.setText("Help");
      m_menuHelpAbout.setText("About");
      m_toolBar.add(m_open);
      m_toolBar.add(m_close);
      m_menuFile.add(m_open);
      m_menuFile.add(m_close);
      m_menuFile.add(m_menuFileExit);
      menuHelp.add(m_menuHelpAbout);
      m_menuBar1.add(m_menuFile);
      m_menuBar1.add(menuHelp);
      this.setJMenuBar(m_menuBar1);
      m_panelStatusBars.setLayout(new BorderLayout());
      m_panelStatusBars.add(m_statusBarPercentage, BorderLayout.NORTH);
      m_panelStatusBars.add(m_statusBarQuestionsLeft, BorderLayout.SOUTH);

      m_contentPane.add(m_toolBar, BorderLayout.NORTH);
      m_contentPane.add(new PanelTitle(), BorderLayout.CENTER);
      m_contentPane.add(m_panelStatusBars, BorderLayout.SOUTH);

      m_menuHelpAbout.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent e)
         {
            helpAbout_actionPerformed(e);
         }
      });

      m_menuFileExit.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent e)
         {
            fileExit_actionPerformed(e);
         }
      });
   }

   //File | Exit action performed
   private void fileExit_actionPerformed(ActionEvent e)
   {
      menuFileClose_actionPerformed(null);
      System.exit(0);
   }

   //Help | About action performed
   private void helpAbout_actionPerformed(ActionEvent e)
   {
      Frame_AboutBox dlg = new Frame_AboutBox(this);
      Dimension dlgSize = dlg.getPreferredSize();
      Dimension frmSize = getSize();
      Point loc = getLocation();
      dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
      dlg.setModal(true);
      dlg.show();
   }

   //Overridden so we can exit when window is closed
   protected void processWindowEvent(WindowEvent e)
   {
      super.processWindowEvent(e);

      if (e.getID() == WindowEvent.WINDOW_CLOSING)
         fileExit_actionPerformed(null);
   }

   private void fireFileEvent()
   {
      // Remove the panel containing question
      Component[] comps = getContentPane().getComponents();
      for (int ii = 0; ii < comps.length; ii++)
      {
         if (comps[ii] instanceof QuestionPanel || comps[ii] instanceof PanelTitle)
            getContentPane().remove(ii);
      }

      // If there are questions in memory (ie, after loading file) then....
      if (m_questionMgr == null)
      {
         m_open.setEnabled(true);
         m_close.setEnabled(false);
         getContentPane().repaint();
         getContentPane().invalidate();
         getContentPane().validate();
         setTitle(m_title);
         m_toolBar.removeAll();
         m_toolBar.add(m_open);
         m_toolBar.add(m_close);
         m_contentPane.add(new PanelTitle(), BorderLayout.CENTER);
      }
      else
      {
         m_open.setEnabled(false);
         m_close.setEnabled(true);
         // ...add panel containing question in content pane
         m_panel = new QuestionPanel(m_questionMgr, this);
         getContentPane().add(m_panel, BorderLayout.CENTER);
         m_panel.displayNextQuestion();
      }
   }

   void menuFileClose_actionPerformed(ActionEvent e)
   {
      if (m_questionMgr != null)
      {
         String strMsg = "So you closed the quiz early - Sammany crying or something?   :-)\n";
         String percent = "0 %";

         if (m_panel != null)
         {
            if (m_panel.m_questionsSeen > 0)
               percent = "" + 100.0 * (double) ((double) m_panel.m_questionsCorrect / (double) m_panel.m_questionsSeen) + " %";

            if (m_questionMgr.getNumberofUnasweredQuestionsLeft() == 0)
               strMsg = "Congratulations - you completed this quiz.\n";
         }

         strMsg += "Here are the results:\n\n";
         strMsg += "   Questions in quiz: " + m_questionMgr.getTotalNumberOfQuestions() + "\n";
         strMsg += "   Number of attempts: " + m_panel.m_questionsSeen + "\n";
         strMsg += "   Correct: " + m_panel.m_questionsCorrect + "\n";
         strMsg += "   Percentage: " + percent;

         JOptionPane.showMessageDialog(this, strMsg, "You Made It!", JOptionPane.INFORMATION_MESSAGE);

         if (m_logoption)
         {
            // Write status to log file
            try
            {
               FileWriter fw = new FileWriter(m_dir + "/QuizzerLog.txt", true);
               Date date = new Date();
               String output = "date=<" + date.toString() + ">, file=<" + m_quizzerfile.getName() + ">, total=<" + m_questionMgr.getTotalNumberOfQuestions() + ">, seen=<" + m_panel.m_questionsSeen + ">, correct=<" + m_panel.m_questionsCorrect + ">, success=<" + percent + ">\n";
               fw.write(output.toCharArray());
               fw.flush();
            }
            catch (Exception e2)
            {
               System.out.println(e2);
            }
         }
      }

      m_quizzerfile = null;
      m_panel = null;
      m_questionMgr = null;
      m_statusBarPercentage.setText(" ");
      m_statusBarQuestionsLeft.setText(" ");
      fireFileEvent();
   }

   private void menuFileOpen_actionPerformed(ActionEvent e)
   {
      JFileChooser chooser = new JFileChooser(m_dir);
      ExampleFileFilter filter = new ExampleFileFilter();
      filter.addExtension("ccc");
      filter.setDescription("CCC Quizzer Files");
      chooser.setFileFilter(filter);
      int returnVal = chooser.showOpenDialog(this);

      if (returnVal == JFileChooser.APPROVE_OPTION)
      {
         // Ask if user wants results logged
         int retval = JOptionPane.showConfirmDialog(this, "Do you want results logged (Tina answer YES)", "Logging options", JOptionPane.YES_NO_OPTION);
         m_logoption = retval == JOptionPane.YES_OPTION;

         m_quizzerfile = chooser.getSelectedFile();

         try
         {
            m_questionMgr = new Quiz(m_quizzerfile);
            fireFileEvent();
         }
         catch (IOException e1)
         {
            System.out.println("Frame.menuFileOpen_actionPerformed: " + e1);
         }
      }

      repaint();
   }
}

