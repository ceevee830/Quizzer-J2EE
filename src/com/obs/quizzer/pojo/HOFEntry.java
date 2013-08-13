package com.obs.quizzer.pojo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class HOFEntry
{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)   
   private Integer id;
   
   private Date dateOfEntry;
   private String quizName;
   private Integer questionsSeen;
   private Integer totalQuestionsInQuiz;
   private Integer answeredCorrectly;
   
   public HOFEntry()
   {
   }
   
   public HOFEntry(Date dateOfEntry, String quizName, Integer questionsSeen, Integer totalQuestionsInQuix, Integer answeredCorrectly)
   {
      this.dateOfEntry = dateOfEntry;
      this.quizName = quizName;
      this.questionsSeen = questionsSeen;
      this.totalQuestionsInQuiz = totalQuestionsInQuix;
      this.answeredCorrectly = answeredCorrectly;
   }
   
   public Integer getId()
   {
      return id;
   }
   public void setId(Integer id)
   {
      this.id = id;
   }
   public Date getDateOfEntry()
   {
      return dateOfEntry;
   }
   public void setDateOfEntry(Date dateOfEntry)
   {
      this.dateOfEntry = dateOfEntry;
   }
   public String getQuizName()
   {
      return quizName;
   }
   public void setQuizName(String quizName)
   {
      this.quizName = quizName;
   }
   public Integer getQuestionsSeen()
   {
      return questionsSeen;
   }
   public void setQuestionsSeen(Integer questionsSeen)
   {
      this.questionsSeen = questionsSeen;
   }
   public Integer getTotalQuestionsInQuiz()
   {
      return totalQuestionsInQuiz;
   }
   public void setTotalQuestionsInQuiz(Integer totalQuestionsInQuiz)
   {
      this.totalQuestionsInQuiz = totalQuestionsInQuiz;
   }
   public Integer getAnsweredCorrectly()
   {
      return answeredCorrectly;
   }
   public void setAnsweredCorrectly(Integer answeredCorrectly)
   {
      this.answeredCorrectly = answeredCorrectly;
   }
   
   public String toString()
   {
      String percent = "" + 100.0 * (double) ((double) answeredCorrectly / (double) questionsSeen) + " %";
      String returnvalue = "date=<" + dateOfEntry.toString() + ">, file=<" + quizName + ">, total=<" + totalQuestionsInQuiz + ">, seen=<" + questionsSeen + ">, correct=<" + answeredCorrectly + ">, success=<" + percent + ">";
      
      return returnvalue;
   }
}
