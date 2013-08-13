package com.obs.quizzer.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import com.obs.quizzer.pojo.Choice;
import com.obs.quizzer.pojo.Question;
import com.obs.quizzer.pojo.Quiz;

class QuestionPanel extends JPanel
{
   private JPanel m_panelQandA = new JPanel();
   private JPanel m_panelAnswers = new JPanel();
   private JTextArea m_txtAreaQuestion = new JTextArea();
   int m_questionsSeen = 0;
   int m_questionsCorrect = 0;
   private Quiz m_questionMgr;
   private Question m_question;
   private JRadioButtonFacial[] m_radiobuttons;
   private JTextAreaFacial[] m_textAreas;
   private Font m_font = new Font("Ariel", Font.PLAIN, 20);
   private Frame m_frame;
   private AbstractAction m_evaluate;
   private AbstractAction m_skip;
   private Border m_border = BorderFactory.createCompoundBorder(new EtchedBorder(EtchedBorder.RAISED, Color.white, new Color(134, 134, 134)), BorderFactory.createEmptyBorder(12, 12, 12, 12));


   public QuestionPanel(Quiz mgr, Frame frame)
   {
      m_questionMgr = mgr;
      m_frame = frame;

      try
      {
         jbInit();
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
   }

   private void jbInit() throws Exception
   {
      this.setLayout(new BorderLayout());
      m_panelQandA.setLayout(new BorderLayout());
      m_txtAreaQuestion.setRows(2);
      m_txtAreaQuestion.setLineWrap(true);
      m_txtAreaQuestion.setWrapStyleWord(true);
      m_txtAreaQuestion.setFont(m_font);
      m_txtAreaQuestion.setEditable(false);
      m_txtAreaQuestion.setBackground(getBackground());
      m_panelAnswers.setBorder(m_border);
      final GridLayout gridLayout = new GridLayout();
      gridLayout.setColumns(1);
      gridLayout.setRows(4);
      m_panelAnswers.setLayout(gridLayout);
      this.add(m_panelQandA, BorderLayout.CENTER);
      m_panelQandA.add(m_txtAreaQuestion, BorderLayout.NORTH);
      m_panelQandA.add(m_panelAnswers, BorderLayout.CENTER);

      m_evaluate = new AbstractAction("Evaluate")
      {
         public void actionPerformed(ActionEvent e)
         {
            m_evaluate_actionPerformed(e);
         }
      };

      m_frame.m_toolBar.add(m_evaluate);

      m_skip = new AbstractAction("Skip")
      {
         public void actionPerformed(ActionEvent e)
         {
            m_skip_actionPerformed(e);
         }
      };

      m_frame.m_toolBar.add(m_skip);
   }

   public void displayNextQuestion()
   {
      m_evaluate.setEnabled(false);

      if (m_questionMgr.getNumberofUnasweredQuestionsLeft() == 0)
      {
         m_frame.menuFileClose_actionPerformed(null);
      }
      else
      {
         m_evaluate.setEnabled(true);
         m_question = (Question) m_questionMgr.getRandomUnasweredQuestion();

         // Update the status bar
         Frame.m_statusBarQuestionsLeft.setText("" + m_questionMgr.getNumberofUnasweredQuestionsLeft() + " Questions Left to be answered");

         placeAnswersOnPanel(m_question);
      }
   }

   private int getRandomInt(final int max)
   {
      final double d = Math.random() * max;
      return (int) d;
   }

//   int getNumberOfQuestionsAnsweredCorrectly()
//   {
//      int correct = 0;
//      final int len = m_questionMgr.size();
//
//      for (int ii = 0 ; ii < len ; ii++)
//      {
//         Question ques = (Question)m_questionMgr.elementAt(ii);
//         if (ques.bAnsweredCorrectly)
//            correct++;
//      }
//
//      return correct;
//   }

   private void placeAnswersOnPanel(Question q)
   {
      ButtonGroup bg = new ButtonGroup();
      m_question = q;
      m_panelAnswers.removeAll();

      m_txtAreaQuestion.setText(q.getQuestionText());
      m_evaluate.setEnabled(false);

      // Determine the number of answers to display to student
      final int answerCount = q.getChoices().size() + 1;

      // Allocate one radio button for each possible answer
      m_radiobuttons = new JRadioButtonFacial[answerCount];
      m_textAreas = new JTextAreaFacial[answerCount];

      // Place the correct answer in a random location
      final int val = getRandomInt(answerCount);
      m_radiobuttons[val] = new JRadioButtonFacial(m_evaluate);
      m_textAreas[val] = new JTextAreaFacial(q.getAnswerText(), m_font, getBackground(), m_radiobuttons[val], m_border);

      // Now place the incorrect answers elsewhere randomly
      for (Choice choice : q.getChoices())
      {
         // Loop until an unfilled position is found
         while (true)
         {
            final int spot = getRandomInt(answerCount);

            if (m_radiobuttons[spot] == null)
            {
               // Place this incorrect answer in the array of radio buttons
               m_radiobuttons[spot] = new JRadioButtonFacial(m_evaluate);
               m_textAreas[spot] = new JTextAreaFacial(choice.getChoice(), m_font, getBackground(), m_radiobuttons[spot], m_border);
               break;
            }
         }
      }

      // Place each of the answers in the panel and buttongroup
      for (int ii = 0; ii < answerCount; ii++)
      {
         bg.add(m_radiobuttons[ii]);
         JPanel panelRow = new JPanel();
         panelRow.setLayout(new BorderLayout());
         panelRow.add(m_radiobuttons[ii], BorderLayout.WEST);
         panelRow.add(m_textAreas[ii], BorderLayout.CENTER);
         m_panelAnswers.add(panelRow);
      }

      repaint();
      invalidate();
      validate();
   }

   private void m_skip_actionPerformed(ActionEvent e)
   {
      displayNextQuestion();
   }

   private void m_evaluate_actionPerformed(ActionEvent e)
   {
      m_questionsSeen++;

      String strEval = "";

      // Loop through all the answers on panel
      for (int ii = 0; ii < m_radiobuttons.length; ii++)
      {
         // If this answer was selected by student
         if (m_radiobuttons[ii].isSelected())
         {
            // Determine if student selected correct answer
            final boolean bIsCorrect = m_question.getAnswerText().compareTo(m_textAreas[ii].getText()) == 0;

            // Get the evaluation string based on student's answer
            strEval = getEvalString(bIsCorrect);

            //
            if (bIsCorrect)
               m_questionsCorrect++;
            else
            {
               m_textAreas[ii].setBackground(Color.red);
            }

            m_question.setAnsweredCorrectly(bIsCorrect);
         }

         // Highlight the correct answer
         if (m_question.getAnswerText().compareTo(m_textAreas[ii].getText()) == 0)
            m_textAreas[ii].setBackground(Color.green);
      }

      // Display the evaluation text
      JOptionPane.showMessageDialog(m_frame, strEval, "Evaluation", JOptionPane.INFORMATION_MESSAGE);
      Frame.m_statusBarPercentage.setText("Correct / Total = percentage ( " + m_questionsCorrect + " / " + m_questionsSeen + " = " + 100.0 * (double) ((double) m_questionsCorrect / (double) m_questionsSeen) + " % )");
      Frame.m_statusBarQuestionsLeft.setText("" + m_questionMgr.getNumberofUnasweredQuestionsLeft() + " Questions Left to be answered");

      displayNextQuestion();
   }

   private String getEvalString(boolean eval)
   {
      String strEvalString;

      if (eval)
      {
         String[] yes =
            {
               "Yess!!!!",
               "You are Correct!",
               "You got it!",
               "Correcta Mundo!",
               "Oh yeahhh",
               "Right on!",
               "Traveesamo!",
            };

         double d = Math.random() * yes.length;
         strEvalString = yes[(int) d];
      }
      else
      {
         String[] no =
            {
               "Sorry...",
               "No dice",
               "Try again",
               "Ixnay on the answeray",
               "No, but thanks for playing our game",
               "Carol Marril will show you your departing gifts"
            };

         double d = Math.random() * no.length;
         strEvalString = no[(int) d];
      }

      return strEvalString;
   }
}

//class JRadioButtonFacial extends JRadioButton implements ActionListener
//{
//   AbstractAction m_eval;
//
//   public JRadioButtonFacial(AbstractAction eval)
//   {
//      super();
//      addActionListener(this);
//      m_eval = eval;
//   }
//
//   public void actionPerformed(ActionEvent e)
//   {
//      m_eval.setEnabled(true);
//   }
//}

//class JTextAreaFacial extends JTextArea
//{
//   public JTextAreaFacial(String s, Font font, Color bg, JRadioButtonFacial button, Border border)
//   {
//      super(s);
//      addCaretListener(new CaretListenerFacial(button));
//      setLineWrap(true);
//      setWrapStyleWord(true);
//      setEditable(false);
//      setFont(font);
//      setBorder(border);
//      setBackground(bg);
//   }
//
//   class CaretListenerFacial implements CaretListener
//   {
//      JRadioButtonFacial m_button;
//
//      public CaretListenerFacial(JRadioButtonFacial button)
//      {
//         m_button = button;
//      }
//
//      public void caretUpdate(CaretEvent e)
//      {
//         m_button.doClick();
//      }
//   }
//}
