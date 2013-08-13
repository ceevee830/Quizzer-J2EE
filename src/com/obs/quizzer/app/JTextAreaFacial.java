package com.obs.quizzer.app;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

/**
 * @author vinsoncl
 * @version $Revision$
 *          Created: Oct 28, 2004 11:50:42 AM
 */
class JTextAreaFacial extends JTextArea
{
   JTextAreaFacial(String s, Font font, Color bg, final JRadioButtonFacial button, Border border)
   {
      super(s);
      addCaretListener(new CaretListenerFacial(button));
      setLineWrap(true);
      setWrapStyleWord(true);
      setEditable(false);
      setFont(font);
      setBorder(border);
      setBackground(bg);
      addMouseListener(new MouseAdapter()
      {
         public void mouseClicked(MouseEvent me)
         {
            if (me.getClickCount() == 2)
            {
//               button.add
            }
         }
      });
   }

   class CaretListenerFacial implements CaretListener
   {
      JRadioButtonFacial m_button;

      public CaretListenerFacial(JRadioButtonFacial button)
      {
         m_button = button;
      }

      public void caretUpdate(CaretEvent e)
      {
         m_button.doClick();
      }
   }
}
