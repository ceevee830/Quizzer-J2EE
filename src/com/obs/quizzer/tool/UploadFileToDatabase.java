package com.obs.quizzer.tool;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

import com.obs.quizzer.dao.DAO;
import com.obs.quizzer.dao.QuizzerDAO;
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

      String folderName = args[0];
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
      
      try
      {
         List<Quiz> qms = dao.getAllQuizzes();
         for (Quiz qm : qms)
         {
            System.out.println("quizname = <" + qm.getQuizName() + "> (" + qm.getQuestions().size() + ")");
            for (Question question : qm.getQuestions())
            {
               System.out.println("   question=<" + question.getQuestionText() + ">, answer=<" + question.getAnswerText() + ">");
            }
         }
      }
      catch (Exception e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      
      DAO.close();
   }
}
