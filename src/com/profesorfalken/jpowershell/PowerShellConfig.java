/*    */ package com.profesorfalken.jpowershell;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.Properties;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class PowerShellConfig
/*    */ {
/*    */   private static final String CONFIG_FILENAME = "jpowershell.properties";
/*    */   private static Properties config;
/*    */   
/*    */   public static Properties getConfig() {
/* 34 */     if (config == null) {
/* 35 */       config = new Properties();
/*    */       
/*    */       try {
/* 38 */         config.load(PowerShellConfig.class.getClassLoader().getResourceAsStream("jpowershell.properties"));
/* 39 */       } catch (IOException ex) {
/* 40 */         Logger.getLogger(PowerShell.class.getName())
/* 41 */           .log(Level.SEVERE, "Cannot read config values from file:jpowershell.properties", ex);
/*    */       } 
/*    */     } 
/*    */     
/* 45 */     return config;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\profesorfalken\jpowershell\PowerShellConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */