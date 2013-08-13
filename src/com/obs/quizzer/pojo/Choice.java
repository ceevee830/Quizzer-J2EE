package com.obs.quizzer.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Choice
{
   @Id
   @GeneratedValue   
   private Integer id;
   
   @ManyToOne
   @JoinColumn(name="question_fk")
   private Question question;
   
   @Column(columnDefinition="TEXT")
   private String choice;
   
   public Choice()
   {
   }

   public Choice(String strToken)
   {
      setChoice(strToken);
   }

   public Integer getId()
   {
      return id;
   }

   public void setId(Integer id)
   {
      this.id = id;
   }

   public String getChoice()
   {
      return choice;
   }

   public void setChoice(String choice)
   {
      this.choice = choice;
   }

   public Question getQuestion()
   {
      return question;
   }

   public void setQuestion(Question question)
   {
      this.question = question;
   }
}
