package com.obs.quizzer.pojo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 * @author vinsoncl
 * @version $Revision$
 *          Created: Oct 28, 2004 12:03:31 PM
 */

@Entity
public class Quiz implements Comparator<Quiz>
{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "quiz_id")
   private Integer id;
   private Integer answersPerQuestion = -1;            // only used when mode = OneAnswerPerLine
   private Boolean oneAnswerPerLine = false;
   private String quizName;
   private String category;

   @OneToMany(cascade = CascadeType.ALL)
   @JoinColumn
   private Set<Question> questions = new HashSet<Question>();       // Will contain nothing but Question objects
   
   public Quiz()
   {
   }

   public Quiz(File quizzerfileselected) throws IOException
   {
      setQuizName(quizzerfileselected.getName());
      final FileInputStream in = new FileInputStream(quizzerfileselected);
      Properties props = new Properties();
      props.load(in);
      in.close();

      final String strMode = (String) props.get("mode");     // OneAnswer

      if (strMode != null)
      {
         oneAnswerPerLine = strMode.compareToIgnoreCase("OneAnswerPerLine") == 0;

         final String strPossibleAnswers = (String) props.get("PossibleAnswersPerQuestion");
         if (oneAnswerPerLine && strPossibleAnswers != null)
            answersPerQuestion = Integer.parseInt(strPossibleAnswers);

         if (answersPerQuestion < 1)
            throw new RuntimeException("The value for 'PossibleAnswersPerQuestion' in '" + quizzerfileselected + " must be greater than zero.");
      }
      
      category = (String)props.getProperty("category");
      
      if (category == null)
      {
         category = "No category";
      }

      try
      {
         final BufferedReader reader = new BufferedReader(new FileReader(quizzerfileselected));

         while (true)
         {
            try
            {
               String strLine = reader.readLine();

               if (strLine == null)
                  break;

               strLine = strLine.trim();

               if (strLine.startsWith("#"))
                  continue;
               else if (strLine.length() == 0)
                  continue;

               final Question ques = addLine(strLine);

               if (ques != null)
                  questions.add(ques);
            }
            catch (IOException ex)
            {
               break;
            }
         }

         reader.close();
      }
      catch (IOException evt)
      {
         System.out.println(evt);
      }
   }

   public Question getRandomUnasweredQuestion()
   {
      Question ques;

      do
      {
         ques = getRandomQuestion();

      }
      while (ques.isAnsweredCorrectly() == true);

      return ques;
   }

   public int getNumberofUnasweredQuestionsLeft()
   {
      int total = questions.size();

      for (Question question : questions)
      {
         if (question.isAnsweredCorrectly())
            total--;
      }

      return total;
   }

   public int getTotalNumberOfQuestions()
   {
      return questions.size();
   }

   private void fillInChoices()
   {
      if (!oneAnswerPerLine)
         throw new RuntimeException("This method should not be called in AllAnswersPerLine mode");

      for (Question question : questions)
      {
         question.setChoices(getIncorrectChoices(question));
      }
   }

   private Set<Choice> getIncorrectChoices(Question ques)
   {
      if (!oneAnswerPerLine)
         throw new RuntimeException("This method should not be called in AllAnswersPerLine mode");

      Set<Choice> listOfChoices = new HashSet<Choice>();

      for (int ii = 0; ii < answersPerQuestion - 1; ii++)
      {
         Choice candidate = null;

         do
         {
            Question temp = this.getRandomQuestion();
            candidate = new Choice(temp.getAnswerText());
         }
         while (candidate.getChoice().compareTo(ques.getAnswerText()) == 0 || listOfChoices.contains(candidate));

         listOfChoices.add(candidate);
      }

      return listOfChoices;
   }

   private Question getRandomQuestion()
   {
      Question returnvalue = null;
      final double d = Math.random() * questions.size();
      final int randomint = (int) d;

      Iterator<Question> iter = questions.iterator();
      returnvalue = iter.next();
      for (int ii = 0 ; ii < randomint ; ii++)
      {
         returnvalue = iter.next();
      }
      
      return returnvalue;
   }

   private void removeThisQuestionFromList(Question question)
   {
      question.setAnsweredCorrectly(true);
   }

   private Question addLine(String strLine)
   {
      final int nOffset = 2;    // question and answer make up two tokens
      final StringTokenizer tok = new StringTokenizer(strLine, "|");
      final int nTotalTokens = tok.countTokens();
      Question ques = null;

      if (nTotalTokens - nOffset >= 0)
      {
         ques = new Question();

         for (int ii = 0; ii < nTotalTokens; ii++)
         {
            final String strToken = tok.nextToken().trim();

            if (ii == 0)
               ques.setQuestionText(strToken);
            else if (ii == 1)
               ques.setAnswerText(strToken);
            else
            {
               Choice choice = new Choice(strToken);
               ques.getChoices().add(choice);
            }
         }
      }

      return ques;
   }

   public static void main(String[] args)
   {
      try
      {
         final Quiz fileio = new Quiz(new File(args[0]));
System.out.println("QuestionManager.main: unasnswered questions=" + fileio.getNumberofUnasweredQuestionsLeft());

         for (int ii = 0; fileio.getNumberofUnasweredQuestionsLeft() > 0; ii++)
         {
            final Question question = fileio.getRandomUnasweredQuestion();
System.out.println("   " + ii + ": " + question);

            fileio.removeThisQuestionFromList(question);
         }
      }
      catch (IOException e)
      {
         System.out.println("QuestionManager.main: " + e);
      }

      System.exit(0);
   }

   public Integer getAnswersPerQuestion()
   {
      return answersPerQuestion;
   }

   public void setAnswersPerQuestion(Integer questionsPerAnswer)
   {
      this.answersPerQuestion = questionsPerAnswer;
   }

   public Boolean isOneAnswerPerLine()
   {
      return oneAnswerPerLine;
   }

   public void setOneAnswerPerLine(Boolean oneAnswerPerLine)
   {
      this.oneAnswerPerLine = oneAnswerPerLine;
   }

   @javax.persistence.OneToMany
   @org.hibernate.annotations.Sort(type=org.hibernate.annotations.SortType.COMPARATOR, comparator=Quiz.class)
   public Set<Question> getQuestions()
   {
      return questions;
   }

   public void setQuestions(Set<Question> questions)
   {
      this.questions = questions;
   }

   public Integer getId()
   {
      return id;
   }

   public void setId(Integer id)
   {
      this.id = id;
   }

   public String getQuizName()
   {
      return quizName;
   }

   public void setQuizName(String quizName)
   {
      this.quizName = quizName;
   }

   public String getCategory()
   {
      return category;
   }

   public void setCategory(String category)
   {
      this.category = category;
   }

   @Override
   public int compare(Quiz o1, Quiz o2)
   {
      return o1.getQuizName().compareTo(o2.getQuizName());
   }
}
