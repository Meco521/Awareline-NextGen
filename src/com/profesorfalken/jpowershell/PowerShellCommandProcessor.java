/*     */ package com.profesorfalken.jpowershell;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.concurrent.Callable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PowerShellCommandProcessor
/*     */   implements Callable<String>
/*     */ {
/*     */   private static final String CRLF = "\r\n";
/*     */   private final BufferedReader reader;
/*     */   private boolean closed = false;
/*     */   private final boolean scriptMode;
/*     */   private final int waitPause;
/*     */   
/*     */   public PowerShellCommandProcessor(String name, InputStream inputStream, int waitPause, boolean scriptMode) {
/*  53 */     this.reader = new BufferedReader(new InputStreamReader(inputStream));
/*     */     
/*  55 */     this.waitPause = waitPause;
/*  56 */     this.scriptMode = scriptMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String call() throws InterruptedException {
/*  66 */     StringBuilder powerShellOutput = new StringBuilder();
/*     */     
/*     */     try {
/*  69 */       if (startReading()) {
/*  70 */         readData(powerShellOutput);
/*     */       }
/*  72 */     } catch (IOException ioe) {
/*  73 */       Logger.getLogger(PowerShell.class.getName()).log(Level.SEVERE, "Unexpected error reading PowerShell output", ioe);
/*  74 */       return ioe.getMessage();
/*     */     } 
/*     */ 
/*     */     
/*  78 */     return powerShellOutput.toString().replaceAll("\\s+$", "");
/*     */   }
/*     */ 
/*     */   
/*     */   private void readData(StringBuilder powerShellOutput) throws IOException {
/*     */     String line;
/*  84 */     while (null != (line = this.reader.readLine())) {
/*     */ 
/*     */       
/*  87 */       if (this.scriptMode && 
/*  88 */         line.equals("--END-JPOWERSHELL-SCRIPT--")) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/*  93 */       powerShellOutput.append(line).append("\r\n");
/*     */ 
/*     */       
/*  96 */       if (!this.scriptMode) {
/*     */         try {
/*  98 */           if (this.closed || !canContinueReading()) {
/*     */             break;
/*     */           }
/* 101 */         } catch (InterruptedException ex) {
/* 102 */           Logger.getLogger(PowerShellCommandProcessor.class.getName()).log(Level.SEVERE, "Error executing command and reading result", ex);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean startReading() throws IOException, InterruptedException {
/* 111 */     while (!this.reader.ready()) {
/* 112 */       Thread.sleep(this.waitPause);
/* 113 */       if (this.closed) {
/* 114 */         return false;
/*     */       }
/*     */     } 
/* 117 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canContinueReading() throws IOException, InterruptedException {
/* 124 */     if (!this.reader.ready()) {
/* 125 */       Thread.sleep(this.waitPause);
/*     */     }
/*     */ 
/*     */     
/* 129 */     if (!this.reader.ready()) {
/* 130 */       Thread.sleep(50L);
/*     */     }
/*     */     
/* 133 */     return this.reader.ready();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/* 140 */     this.closed = true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\profesorfalken\jpowershell\PowerShellCommandProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */