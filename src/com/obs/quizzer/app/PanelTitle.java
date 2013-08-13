package com.obs.quizzer.app;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * @author vinsoncl
 * @version $Revision$
 *          Created: Oct 28, 2004 11:51:02 AM
 */
class PanelTitle extends JPanel
{
   PanelTitle()
   {
      super();
      ImageIcon icon = new ImageIcon("teevee.jpg");
      setLayout(new BorderLayout());
      JLabel label = new JLabel("Esthetician Quizzer");
      label.setFont(new Font("Ariel", Font.PLAIN, 50));
      label.setIcon(icon);
      label.setHorizontalAlignment(SwingConstants.CENTER);
      label.setVerticalTextPosition(SwingConstants.TOP);
      label.setHorizontalTextPosition(SwingConstants.CENTER);
      add(label, BorderLayout.CENTER);
   }
}

