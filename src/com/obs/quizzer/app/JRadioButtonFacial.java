package com.obs.quizzer.app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.JRadioButton;

/**
 * @author vinsoncl
 * @version $Revision$
 *          Created: Oct 28, 2004 11:50:17 AM
 */
class JRadioButtonFacial extends JRadioButton implements ActionListener
{
   AbstractAction m_eval;

   JRadioButtonFacial(AbstractAction eval)
   {
      super();
      addActionListener(this);
      addMouseListener(new MouseAdapter()
      {
         public void mousePressed(MouseEvent me)
         {
            if (me.getClickCount() == 2)
            {
               System.out.println("JRadioButtonFacial.mouseClicked");
//               m_eval.
            }
         }
      });
      m_eval = eval;
   }

   public void actionPerformed(ActionEvent e)
   {
      m_eval.setEnabled(true);
   }
}

