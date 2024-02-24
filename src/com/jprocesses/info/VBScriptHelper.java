/*     */ package com.jprocesses.info;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.Date;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class VBScriptHelper
/*     */ {
/*     */   private static final String CRLF = "\r\n";
/*     */   
/*     */   private static String executeScript(String scriptCode) {
/*  37 */     StringBuilder scriptResponse = new StringBuilder();
/*  38 */     File tmpFile = null;
/*  39 */     FileWriter writer = null;
/*  40 */     BufferedReader processOutput = null;
/*  41 */     BufferedReader errorOutput = null;
/*     */     
/*     */     try {
/*  44 */       tmpFile = File.createTempFile("wmi4java" + (new Date()).getTime(), ".vbs");
/*  45 */       writer = new FileWriter(tmpFile);
/*  46 */       writer.write(scriptCode);
/*  47 */       writer.flush();
/*  48 */       writer.close();
/*     */       
/*  50 */       Process process = Runtime.getRuntime().exec(new String[] { "cmd.exe", "/C", "cscript.exe", "/NoLogo", tmpFile
/*  51 */             .getAbsolutePath() });
/*     */       
/*  53 */       processOutput = new BufferedReader(new InputStreamReader(process.getInputStream()));
/*     */       String line;
/*  55 */       while ((line = processOutput.readLine()) != null) {
/*  56 */         if (!line.isEmpty()) {
/*  57 */           scriptResponse.append(line).append("\r\n");
/*     */         }
/*     */       } 
/*     */       
/*  61 */       if (scriptResponse.length() == 0)
/*     */       {
/*  63 */         errorOutput = new BufferedReader(new InputStreamReader(process.getInputStream()));
/*  64 */         StringBuilder errorResponse = new StringBuilder();
/*  65 */         while ((line = errorOutput.readLine()) != null) {
/*  66 */           if (!line.isEmpty()) {
/*  67 */             errorResponse.append(line).append("\r\n");
/*     */           }
/*     */         } 
/*  70 */         if (errorResponse.length() > 0) {
/*  71 */           Logger.getLogger(VBScriptHelper.class.getName()).log(Level.SEVERE, "WMI operation finished in error: ");
/*     */         }
/*  73 */         errorOutput.close();
/*     */       }
/*     */     
/*  76 */     } catch (Exception ex) {
/*  77 */       Logger.getLogger(VBScriptHelper.class.getName()).log(Level.SEVERE, (String)null, ex);
/*     */     } finally {
/*     */       try {
/*  80 */         if (processOutput != null) {
/*  81 */           processOutput.close();
/*     */         }
/*  83 */         if (errorOutput != null) {
/*  84 */           errorOutput.close();
/*     */         }
/*  86 */         if (writer != null) {
/*  87 */           writer.close();
/*     */         }
/*  89 */         if (tmpFile != null) {
/*  90 */           tmpFile.delete();
/*     */         }
/*  92 */       } catch (IOException ioe) {
/*  93 */         Logger.getLogger(VBScriptHelper.class.getName()).log(Level.SEVERE, (String)null, ioe);
/*     */       } 
/*     */     } 
/*  96 */     return scriptResponse.toString().trim();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getProcessesOwner() {
/*     */     try {
/* 108 */       String scriptCode = "Set objWMIService=GetObject(\"winmgmts:{impersonationLevel=impersonate}!\\\\./root/cimv2\")\r\nSet colProcessList = objWMIService.ExecQuery(\"Select * from Win32_Process\")\r\nFor Each objProcess in colProcessList\r\ncolProperties = objProcess.GetOwner(strNameOfUser,strUserDomain)\r\nWscript.Echo objProcess.ProcessId & \":\" & strNameOfUser\r\nNext\r\n";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 116 */       return executeScript(scriptCode);
/* 117 */     } catch (Exception ex) {
/* 118 */       Logger.getLogger(VBScriptHelper.class.getName()).log(Level.SEVERE, (String)null, ex);
/*     */ 
/*     */       
/* 121 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String changePriority(int pid, int newPriority) {
/*     */     try {
/* 135 */       String scriptCode = "Set objWMIService=GetObject(\"winmgmts:{impersonationLevel=impersonate}!\\\\./root/cimv2\")\r\nSet colProcesses = objWMIService.ExecQuery(\"Select * from Win32_Process Where ProcessId = " + pid + "\")" + "\r\n" + "For Each objProcess in colProcesses" + "\r\n" + "objProcess.SetPriority(" + newPriority + ")" + "\r\n" + "Next" + "\r\n";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 143 */       return executeScript(scriptCode);
/* 144 */     } catch (Exception ex) {
/* 145 */       Logger.getLogger(VBScriptHelper.class.getName()).log(Level.SEVERE, (String)null, ex);
/*     */ 
/*     */       
/* 148 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\jprocesses\info\VBScriptHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */