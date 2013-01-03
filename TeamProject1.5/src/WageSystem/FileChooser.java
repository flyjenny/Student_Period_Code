package WageSystem;

import java.awt.Component; 
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.*; 

public class FileChooser { 
    public static  JLabel findLabel(JComponent comp, String s){ 
        JLabel label = null; 
        if (comp instanceof JLabel) { 
            if (((JLabel)comp).getText().equals(s)){ 
                label = (JLabel)comp; 
            }             
        } else if (comp instanceof JComponent) { 
            Component[] comps = comp.getComponents();             
            for (int i=0; i<comps.length; i++) { 
                if (comps[i] instanceof JComponent) { 
                    label = findLabel((JComponent)comps[i], s); 
                    if (label != null) { 
                        break; 
                    } 
                } 
            }             
        } 
        return label; 
    } 

    public static   Component getLabelForInChooser(JFileChooser chooser, String key){ 
        java.util.Locale l = chooser.getLocale(); 
        String s = UIManager.getString(key, l); 
         
        javax.swing.plaf.FileChooserUI ui = chooser.getUI(); 
        int count = ui.getAccessibleChildrenCount(chooser); 
        for (int i=0; i<count; i++) { 
            javax.accessibility.Accessible a =  
                ui.getAccessibleChild(chooser, i); 
            JLabel label = findLabel((JComponent)a, s); 
            if (label != null) { 
                return label.getLabelFor(); 
            } 
        } 
        return null; 
    } 
    
    
     
    public static void main(String[] args) { 
        JFileChooser chooser = new JFileChooser(""); 
        Component comp =  
            getLabelForInChooser(chooser, "FileChooser.fileNameLabelText"); 
        if (comp instanceof JTextField) { 
            JTextField field = ((JTextField)comp);
            field.setText("hello.exe");
            field.setEditable(true); 

        } 
        chooser.showOpenDialog(null); 
        System.out.println(chooser.getSelectedFile().getPath());
    } 
}
