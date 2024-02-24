/*     */ package com.jprocesses.util;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProcessesUtils
/*     */ {
/*     */   private static final String CRLF = "\r\n";
/*     */   private static String customDateFormat;
/*     */   private static Locale customLocale;
/*     */   
/*     */   public static String executeCommand(String... command) {
/*  48 */     String commandOutput = null;
/*     */     
/*     */     try {
/*  51 */       ProcessBuilder processBuilder = new ProcessBuilder(command);
/*  52 */       processBuilder.redirectErrorStream(true);
/*     */       
/*  54 */       commandOutput = readData(processBuilder.start());
/*  55 */     } catch (IOException ex) {
/*  56 */       commandOutput = "";
/*  57 */       Logger.getLogger(ProcessesUtils.class.getName()).log(Level.SEVERE, "Error executing command", ex);
/*  58 */       ex.printStackTrace();
/*     */     } 
/*     */     
/*  61 */     return commandOutput;
/*     */   }
/*     */   
/*     */   private static String readData(Process process) {
/*  65 */     StringBuilder commandOutput = new StringBuilder();
/*  66 */     BufferedReader processOutput = null;
/*     */     
/*     */     try {
/*  69 */       processOutput = new BufferedReader(new InputStreamReader(process.getInputStream()));
/*     */       
/*     */       String line;
/*  72 */       while ((line = processOutput.readLine()) != null) {
/*  73 */         if (!line.isEmpty()) {
/*  74 */           commandOutput.append(line).append("\r\n");
/*     */         }
/*     */       } 
/*  77 */     } catch (IOException ex) {
/*  78 */       Logger.getLogger(ProcessesUtils.class.getName()).log(Level.SEVERE, "Error reading data", ex);
/*     */     } finally {
/*     */       try {
/*  81 */         if (processOutput != null) {
/*  82 */           processOutput.close();
/*     */         }
/*  84 */       } catch (IOException ioe) {
/*  85 */         Logger.getLogger(ProcessesUtils.class.getName()).log(Level.SEVERE, "Error closing reader", ioe);
/*     */       } 
/*     */     } 
/*     */     
/*  89 */     return commandOutput.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static int executeCommandAndGetCode(String... command) {
/*     */     Process process;
/*     */     try {
/*  96 */       process = Runtime.getRuntime().exec(command);
/*  97 */       process.waitFor();
/*  98 */     } catch (IOException ex) {
/*  99 */       Logger.getLogger(ProcessesUtils.class.getName()).log(Level.SEVERE, (String)null, ex);
/* 100 */       return -1;
/* 101 */     } catch (InterruptedException ex) {
/* 102 */       Logger.getLogger(ProcessesUtils.class.getName()).log(Level.SEVERE, (String)null, ex);
/* 103 */       return -1;
/*     */     } 
/*     */     
/* 106 */     return process.exitValue();
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
/*     */   public static String parseWindowsDateTimeToSimpleTime(String dateTime) {
/* 118 */     String returnedDate = dateTime;
/* 119 */     if (dateTime != null && !dateTime.isEmpty()) {
/* 120 */       String hour = dateTime.substring(8, 10);
/* 121 */       String minutes = dateTime.substring(10, 12);
/* 122 */       String seconds = dateTime.substring(12, 14);
/*     */       
/* 124 */       returnedDate = hour + ":" + minutes + ":" + seconds;
/*     */     } 
/* 126 */     return returnedDate;
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
/*     */   public static String parseWindowsDateTimeToFullDate(String dateTime) {
/* 138 */     String returnedDate = dateTime;
/* 139 */     if (dateTime != null && !dateTime.isEmpty()) {
/* 140 */       String year = dateTime.substring(0, 4);
/* 141 */       String month = dateTime.substring(4, 6);
/* 142 */       String day = dateTime.substring(6, 8);
/* 143 */       String hour = dateTime.substring(8, 10);
/* 144 */       String minutes = dateTime.substring(10, 12);
/* 145 */       String seconds = dateTime.substring(12, 14);
/*     */       
/* 147 */       returnedDate = month + "/" + day + "/" + year + " " + hour + ":" + minutes + ":" + seconds;
/*     */     } 
/*     */     
/* 150 */     return returnedDate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String parseUnixLongTimeToFullDate(String longFormatDate) throws ParseException {
/* 161 */     DateFormat targetFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
/* 162 */     List<String> formatsToTry = new ArrayList<>(Arrays.asList(new String[] { "MMM dd HH:mm:ss yyyy", "dd MMM HH:mm:ss yyyy" }));
/* 163 */     List<Locale> localesToTry = new ArrayList<>(Arrays.asList(new Locale[] { Locale.getDefault(), 
/* 164 */             Locale.getDefault(Locale.Category.FORMAT), Locale.ENGLISH }));
/*     */     
/* 166 */     if (customDateFormat != null) {
/* 167 */       formatsToTry.add(0, customDateFormat);
/*     */     }
/* 169 */     if (customLocale != null) {
/* 170 */       localesToTry.add(0, customLocale);
/*     */     }
/*     */     
/* 173 */     ParseException lastException = null;
/* 174 */     for (Locale locale : localesToTry) {
/* 175 */       for (String format : formatsToTry) {
/* 176 */         DateFormat originalFormat = new SimpleDateFormat(format, locale);
/*     */         try {
/* 178 */           return targetFormat.format(originalFormat.parse(longFormatDate));
/* 179 */         } catch (ParseException ex) {
/* 180 */           lastException = ex;
/*     */         } 
/*     */       } 
/*     */     } 
/* 184 */     throw lastException;
/*     */   }
/*     */   
/*     */   public static String getCustomDateFormat() {
/* 188 */     return customDateFormat;
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
/*     */   public static void setCustomDateFormat(String dateFormat) {
/* 200 */     customDateFormat = dateFormat;
/*     */   }
/*     */   
/*     */   public static Locale getCustomLocale() {
/* 204 */     return customLocale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setCustomLocale(Locale customLocale) {
/* 213 */     ProcessesUtils.customLocale = customLocale;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\jprocesse\\util\ProcessesUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */