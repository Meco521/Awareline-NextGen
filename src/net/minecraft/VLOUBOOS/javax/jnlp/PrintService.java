package net.minecraft.VLOUBOOS.javax.jnlp;

import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Printable;

public interface PrintService {
  PageFormat getDefaultPage();
  
  PageFormat showPageFormatDialog(PageFormat paramPageFormat);
  
  boolean print(Pageable paramPageable);
  
  boolean print(Printable paramPrintable);
}


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\VLOUBOOS\javax\jnlp\PrintService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */