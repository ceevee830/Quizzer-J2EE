package com.obs.quizzer.tool;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

import com.obs.quizzer.dao.DAO;
import com.obs.quizzer.dao.QuizzerDAO;
import com.obs.quizzer.pojo.Choice;
import com.obs.quizzer.pojo.Question;
import com.obs.quizzer.pojo.Quiz;

public class UploadFileToDatabase
{
   public static void main(String[] args)
   {
      if (args == null || args.length != 1)
      {
         System.err.println("Directory containing .ccc files must be passed-in on command line.");
         System.exit(1);
      }

      UploadFileToDatabase uftd = new UploadFileToDatabase();
//      uftd.writeToDatabase(args[0]);
      uftd.readFromDatabase();
   }

   private void writeToDatabase(String folderName)
   {
      File folderFile = new File(folderName);
      FileFilter ff = new FileFilter()
      {
         public boolean accept(File pathname)
         {
            return pathname.getAbsolutePath().toLowerCase().trim().endsWith(".ccc");
         }
      };
      
      File[] files = folderFile.listFiles(ff);
      
      QuizzerDAO dao = new QuizzerDAO();
      
      if (files.length > 0)
      {
         for (File file : files)
         {
            try
            {
               Quiz qm = new Quiz(file);
               dao.create(qm);
            }
            catch (Exception e)
            {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
         }
      }
      
      DAO.close();
   }

   private void readFromDatabase()
   {
      QuizzerDAO dao = new QuizzerDAO();
      
      try
      {
         List<String> categories = dao.getCategories();
         
         for (String category : categories)
         {
            List<Quiz> qms = dao.getQuizzes(category);
            
System.out.println("read category = <" + category + "> (" + qms.size() + ")");

            for (Quiz qm : qms)
            {
               
System.out.println("   read quizname = <" + qm.getQuizName() + "> (" + qm.getQuestions().size() + ")");

               for (Question question : qm.getQuestions())
               {
System.out.println("      read question=<" + question.getQuestionText() + ">, answer=<" + question.getAnswerText() + ">");
                  
                  for (Choice choice : question.getChoices())
                  {
System.out.println("         read choice=<" + choice.getChoice() + ">");
                  }
               }
            }
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      
      DAO.close();
   }
}
