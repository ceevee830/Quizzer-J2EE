package com.obs.quizzer.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class Question
{
   @Id
   @GeneratedValue   
   private Integer id;
   
   @ManyToOne
   @JoinColumn(name="questionmanager_fk")
   private Quiz questionManager;

   @Column(columnDefinition="TEXT")
   private String questionText;

   @Column(columnDefinition="TEXT")
   private String answerText;

   @OneToMany(cascade = CascadeType.ALL, mappedBy="question")
   private Set<Choice> choices = new HashSet<Choice>();
   
   @Transient
   private boolean isAnsweredCorrectly = false;
   
   public Question()
   {
   }

   public String getQuestionText()
   {
      return questionText;
   }

   public void setQuestionText(String questionText)
   {
      this.questionText = questionText;
   }

   public String getAnswerText()
   {
      return answerText;
   }

   public void setAnswerText(String answerText)
   {
      this.answerText = answerText;
   }

   public Set<Choice> getChoices()
   {
      return choices;
   }

   public void setChoices(Set<Choice> choices)
   {
      this.choices = choices;
   }

   public boolean isAnsweredCorrectly()
   {
      return isAnsweredCorrectly;
   }

   public void setAnsweredCorrectly(boolean isAnsweredCorrectly)
   {
      this.isAnsweredCorrectly = isAnsweredCorrectly;
   }

   public Quiz getQuestionManager()
   {
      return questionManager;
   }

   public void setQuestionManager(Quiz questionManager)
   {
      this.questionManager = questionManager;
   }

   public Integer getId()
   {
      return id;
   }

   public void setId(Integer id)
   {
      this.id = id;
   }

   @Transient
   public String toString()
   {
      String strQandA = "Question=<" + questionText + ">, Answer=<" + answerText + "> ";
      String xxx = "";

      for (Choice choice : choices)
         xxx += "Choice" + choice.getChoice() + ">, ";

      return strQandA + xxx;
   }
}
