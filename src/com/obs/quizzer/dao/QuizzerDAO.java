package com.obs.quizzer.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.obs.quizzer.pojo.Quiz;


public class QuizzerDAO extends DAO
{
   public Quiz create(Quiz qm) throws Exception
   {
      log.log(Level.INFO, "cvcvcv: Entering");
      
      try
      {
         begin();
         getSession().save(qm);
         getSession().flush();
         commit();
         return qm;
      }
      catch (HibernateException ex)
      {
         rollback();
         throw new Exception("Could not create", ex);
      }
      finally
      {
         log.log(Level.INFO, "cvcvcv: Leaving");
      }
   }
   
   public List<Quiz> getAllQuizzes() throws Exception
   {
      log.log(Level.INFO, "cvcvcv: Entering getAllQuizzes()");
      
      try
      {
         Query query = getSession().createQuery("from Quiz");
         return query.list();
      }
      catch (HibernateException ex)
      {
         throw new Exception("Could not getAllQuizzes()", ex);
      }
      finally
      {
         log.log(Level.INFO, "cvcvcv: Leaving getAllQuizzes()");
      }
   }
   
   public List<Quiz> getQuizzes(String category) throws Exception
   {
      log.log(Level.INFO, "cvcvcv: Entering getQuizzes()");
      
      try
      {
         Query query = getSession().createQuery("from Quiz where category='" + category + "'");
         return query.list();
      }
      catch (HibernateException ex)
      {
         throw new Exception("Could not getQuizzes()", ex);
      }
      finally
      {
         log.log(Level.INFO, "cvcvcv: Leaving getQuizzes()");
      }
   }
   
   public List<String> getCategories() throws Exception
   {
      List<String> returnvalue = new ArrayList<String>();
      
      List<Quiz> quizzes = getAllQuizzes();
      
      for (Quiz quiz : quizzes)
      {
         String category = quiz.getCategory();
         
         if (!returnvalue.contains(category))
         {
            returnvalue.add(category);
         }
      }
      
      return returnvalue;
   }

   
}
