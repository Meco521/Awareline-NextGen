/*     */ package com.profesorfalken.jpowershell;
/*     */ 
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.TimeoutException;
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
/*     */ public class PowerShell
/*     */   implements AutoCloseable
/*     */ {
/*  40 */   private static final Logger logger = Logger.getLogger(PowerShell.class.getName());
/*     */ 
/*     */   
/*     */   private Process p;
/*     */   
/*  45 */   private long pid = -1L;
/*     */ 
/*     */   
/*     */   private PrintWriter commandWriter;
/*     */   
/*     */   private boolean closed = false;
/*     */   
/*     */   private ExecutorService threadpool;
/*     */   
/*     */   private static final String DEFAULT_WIN_EXECUTABLE = "powershell.exe";
/*     */   
/*     */   private static final String DEFAULT_LINUX_EXECUTABLE = "powershell";
/*     */   
/*  58 */   private int waitPause = 5;
/*  59 */   private long maxWait = 10000L;
/*  60 */   private File tempFolder = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean scriptMode = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String END_SCRIPT_STRING = "--END-JPOWERSHELL-SCRIPT--";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PowerShell configuration(Map<String, String> config) {
/*     */     try {
/*  88 */       this.waitPause = 
/*  89 */         Integer.valueOf((config != null && config.get("waitPause") != null) ? config.get("waitPause") : 
/*  90 */           PowerShellConfig.getConfig().getProperty("waitPause")).intValue();
/*  91 */       this.maxWait = Long.valueOf((config != null && config.get("maxWait") != null) ? config.get("maxWait") : 
/*  92 */           PowerShellConfig.getConfig().getProperty("maxWait")).longValue();
/*  93 */       this
/*  94 */         .tempFolder = (config != null && config.get("tempFolder") != null) ? getTempFolder(config.get("tempFolder")) : getTempFolder(PowerShellConfig.getConfig().getProperty("tempFolder"));
/*  95 */     } catch (NumberFormatException nfe) {
/*  96 */       logger.log(Level.SEVERE, "Could not read configuration. Using default values.", nfe);
/*     */     } 
/*     */     
/*  99 */     return this;
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
/*     */   public static PowerShell openSession() throws PowerShellNotAvailableException {
/* 111 */     return openSession(null);
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
/*     */ 
/*     */   
/*     */   public static PowerShell openSession(String customPowerShellExecutablePath) throws PowerShellNotAvailableException {
/* 125 */     PowerShell powerShell = new PowerShell();
/*     */ 
/*     */     
/* 128 */     powerShell.configuration(null);
/*     */     
/* 130 */     String powerShellExecutablePath = (customPowerShellExecutablePath == null) ? (OSDetector.isWindows() ? "powershell.exe" : "powershell") : customPowerShellExecutablePath;
/*     */     
/* 132 */     return powerShell.initalize(powerShellExecutablePath);
/*     */   }
/*     */   
/*     */   private PowerShell initalize(String powerShellExecutablePath) throws PowerShellNotAvailableException {
/*     */     ProcessBuilder pb;
/* 137 */     String codePage = PowerShellCodepage.getIdentifierByCodePageName(Charset.defaultCharset().name());
/*     */ 
/*     */ 
/*     */     
/* 141 */     if (OSDetector.isWindows()) {
/* 142 */       pb = new ProcessBuilder(new String[] { "cmd.exe", "/c", "chcp", codePage, ">", "NUL", "&", powerShellExecutablePath, "-ExecutionPolicy", "Bypass", "-NoExit", "-NoProfile", "-Command", "-" });
/*     */     } else {
/*     */       
/* 145 */       pb = new ProcessBuilder(new String[] { powerShellExecutablePath, "-nologo", "-noexit", "-Command", "-" });
/*     */     } 
/*     */ 
/*     */     
/* 149 */     pb.redirectErrorStream(true);
/*     */ 
/*     */     
/*     */     try {
/* 153 */       this.p = pb.start();
/*     */       
/* 155 */       if (this.p.waitFor(5L, TimeUnit.SECONDS) && !this.p.isAlive()) {
/* 156 */         throw new PowerShellNotAvailableException("Cannot execute PowerShell. Please make sure that it is installed in your system. Errorcode:" + this.p
/* 157 */             .exitValue());
/*     */       }
/* 159 */     } catch (IOException|InterruptedException ex) {
/* 160 */       throw new PowerShellNotAvailableException("Cannot execute PowerShell. Please make sure that it is installed in your system", ex);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 165 */     this.commandWriter = new PrintWriter(new OutputStreamWriter(new BufferedOutputStream(this.p.getOutputStream())), true);
/*     */ 
/*     */     
/* 168 */     this.threadpool = Executors.newFixedThreadPool(2);
/*     */ 
/*     */     
/* 171 */     this.pid = getPID();
/*     */     
/* 173 */     return this;
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
/*     */ 
/*     */   
/*     */   public PowerShellResponse executeCommand(String command) {
/* 187 */     String commandOutput = "";
/* 188 */     boolean isError = false;
/* 189 */     boolean timeout = false;
/*     */     
/* 191 */     checkState();
/*     */     
/* 193 */     PowerShellCommandProcessor commandProcessor = new PowerShellCommandProcessor("standard", this.p.getInputStream(), this.waitPause, this.scriptMode);
/*     */     
/* 195 */     Future<String> result = this.threadpool.submit(commandProcessor);
/*     */ 
/*     */     
/* 198 */     this.commandWriter.println(command);
/*     */     
/*     */     try {
/* 201 */       if (!result.isDone()) {
/*     */         try {
/* 203 */           commandOutput = result.get(this.maxWait, TimeUnit.MILLISECONDS);
/* 204 */         } catch (TimeoutException timeoutEx) {
/* 205 */           timeout = true;
/* 206 */           isError = true;
/*     */           
/* 208 */           result.cancel(true);
/*     */         } 
/*     */       }
/* 211 */     } catch (InterruptedException|ExecutionException ex) {
/* 212 */       logger.log(Level.SEVERE, "Unexpected error when processing PowerShell command", ex);
/*     */       
/* 214 */       isError = true;
/*     */     }
/*     */     finally {
/*     */       
/* 218 */       commandProcessor.close();
/*     */     } 
/*     */     
/* 221 */     return new PowerShellResponse(isError, commandOutput, timeout);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PowerShellResponse executeSingleCommand(String command) {
/* 231 */     PowerShellResponse response = null;
/*     */     
/* 233 */     try (PowerShell session = openSession()) {
/* 234 */       response = session.executeCommand(command);
/* 235 */     } catch (PowerShellNotAvailableException ex) {
/* 236 */       logger.log(Level.SEVERE, "PowerShell not available", ex);
/*     */     } 
/*     */     
/* 239 */     return response;
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
/*     */   
/*     */   public PowerShell executeCommandAndChain(String command, PowerShellResponseHandler... response) {
/* 252 */     PowerShellResponse powerShellResponse = executeCommand(command);
/*     */     
/* 254 */     if (response.length > 0) {
/* 255 */       handleResponse(response[0], powerShellResponse);
/*     */     }
/*     */     
/* 258 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   private void handleResponse(PowerShellResponseHandler response, PowerShellResponse powerShellResponse) {
/*     */     try {
/* 264 */       response.handle(powerShellResponse);
/* 265 */     } catch (Exception ex) {
/* 266 */       logger.log(Level.SEVERE, "PowerShell not available", ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLastCommandInError() {
/* 276 */     return !Boolean.valueOf(executeCommand("$?").getCommandOutput()).booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PowerShellResponse executeScript(String scriptPath) {
/* 287 */     return executeScript(scriptPath, "");
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
/*     */   
/*     */   public PowerShellResponse executeScript(String scriptPath, String params) {
/* 300 */     try (BufferedReader srcReader = new BufferedReader(new FileReader(new File(scriptPath)))) {
/* 301 */       return executeScript(srcReader, params);
/* 302 */     } catch (FileNotFoundException fnfex) {
/* 303 */       logger.log(Level.SEVERE, "Unexpected error when processing PowerShell script: file not found", fnfex);
/*     */       
/* 305 */       return new PowerShellResponse(true, "Wrong script path: " + scriptPath, false);
/* 306 */     } catch (IOException ioe) {
/* 307 */       logger.log(Level.SEVERE, "Unexpected error when processing PowerShell script", ioe);
/*     */       
/* 309 */       return new PowerShellResponse(true, "IO error reading: " + scriptPath, false);
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
/*     */   public PowerShellResponse executeScript(BufferedReader srcReader) {
/* 321 */     return executeScript(srcReader, "");
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
/*     */   public PowerShellResponse executeScript(BufferedReader srcReader, String params) {
/*     */     PowerShellResponse response;
/* 334 */     if (srcReader != null) {
/* 335 */       File tmpFile = createWriteTempFile(srcReader);
/* 336 */       if (tmpFile != null) {
/* 337 */         this.scriptMode = true;
/* 338 */         response = executeCommand(tmpFile.getAbsolutePath() + " " + params);
/* 339 */         this.scriptMode = false;
/* 340 */         tmpFile.delete();
/*     */       } else {
/* 342 */         response = new PowerShellResponse(true, "Cannot create temp script file!", false);
/*     */       } 
/*     */     } else {
/* 345 */       logger.log(Level.SEVERE, "Script buffered reader is null!");
/* 346 */       response = new PowerShellResponse(true, "Script buffered reader is null!", false);
/*     */     } 
/*     */     
/* 349 */     return response;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private File createWriteTempFile(BufferedReader srcReader) {
/* 355 */     BufferedWriter tmpWriter = null;
/* 356 */     File tmpFile = null;
/*     */     
/*     */     try {
/* 359 */       tmpFile = File.createTempFile("psscript_" + (new Date()).getTime(), ".ps1", this.tempFolder);
/* 360 */       if (!tmpFile.exists()) {
/* 361 */         return null;
/*     */       }
/*     */       
/* 364 */       tmpWriter = new BufferedWriter(new FileWriter(tmpFile));
/*     */       String line;
/* 366 */       while (srcReader != null && (line = srcReader.readLine()) != null) {
/* 367 */         tmpWriter.write(line);
/* 368 */         tmpWriter.newLine();
/*     */       } 
/*     */ 
/*     */       
/* 372 */       tmpWriter.write("Write-Output \"--END-JPOWERSHELL-SCRIPT--\"");
/* 373 */     } catch (IOException ioex) {
/* 374 */       logger.log(Level.SEVERE, "Unexpected error while writing temporary PowerShell script", ioex);
/*     */     } finally {
/*     */       
/*     */       try {
/* 378 */         if (tmpWriter != null) {
/* 379 */           tmpWriter.close();
/*     */         }
/* 381 */       } catch (IOException ex) {
/* 382 */         logger.log(Level.SEVERE, "Unexpected error when processing temporary PowerShell script", ex);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 387 */     return tmpFile;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/* 395 */     if (!this.closed) {
/*     */       try {
/* 397 */         Future<String> closeTask = this.threadpool.submit(() -> {
/*     */               this.commandWriter.println("exit");
/*     */               this.p.waitFor();
/*     */               return "OK";
/*     */             });
/* 402 */         if (!closeAndWait(closeTask) && this.pid > 0L) {
/*     */           
/* 404 */           Logger.getLogger(PowerShell.class.getName()).log(Level.INFO, "Forcing PowerShell to close. PID: " + this.pid);
/*     */           
/*     */           try {
/* 407 */             Runtime.getRuntime().exec("taskkill.exe /PID " + this.pid + " /F /T");
/* 408 */             this.closed = true;
/* 409 */           } catch (IOException e) {
/* 410 */             Logger.getLogger(PowerShell.class.getName()).log(Level.SEVERE, "Unexpected error while killing powershell process", e);
/*     */           }
/*     */         
/*     */         } 
/* 414 */       } catch (InterruptedException|ExecutionException ex) {
/* 415 */         logger.log(Level.SEVERE, "Unexpected error when when closing PowerShell", ex);
/*     */       } finally {
/*     */         
/* 418 */         this.commandWriter.close();
/*     */         try {
/* 420 */           if (this.p.isAlive()) {
/* 421 */             this.p.getInputStream().close();
/*     */           }
/* 423 */         } catch (IOException ex) {
/* 424 */           logger.log(Level.SEVERE, "Unexpected error when when closing streams", ex);
/*     */         } 
/*     */         
/* 427 */         if (this.threadpool != null) {
/*     */           try {
/* 429 */             this.threadpool.shutdownNow();
/* 430 */             this.threadpool.awaitTermination(5L, TimeUnit.SECONDS);
/* 431 */           } catch (InterruptedException ex) {
/* 432 */             logger.log(Level.SEVERE, "Unexpected error when when shutting down thread pool", ex);
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/* 437 */         this.closed = true;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean closeAndWait(Future<String> task) throws InterruptedException, ExecutionException {
/* 443 */     boolean closed = true;
/* 444 */     if (!task.isDone()) {
/*     */       try {
/* 446 */         task.get(this.maxWait, TimeUnit.MILLISECONDS);
/* 447 */       } catch (TimeoutException timeoutEx) {
/* 448 */         logger.log(Level.WARNING, "Powershell process cannot be closed. Session seems to be blocked");
/*     */ 
/*     */         
/* 451 */         task.cancel(true);
/* 452 */         closed = false;
/*     */       } 
/*     */     }
/* 455 */     return closed;
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkState() {
/* 460 */     if (this.closed) {
/* 461 */       throw new IllegalStateException("PowerShell is already closed. Please open a new session.");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private long getPID() {
/* 467 */     String commandOutput = executeCommand("$pid").getCommandOutput();
/*     */ 
/*     */     
/* 470 */     commandOutput = commandOutput.replaceAll("\\D", "");
/*     */     
/* 472 */     if (!commandOutput.isEmpty()) {
/* 473 */       return Long.valueOf(commandOutput).longValue();
/*     */     }
/*     */     
/* 476 */     return -1L;
/*     */   }
/*     */ 
/*     */   
/*     */   private File getTempFolder(String tempPath) {
/* 481 */     if (tempPath != null) {
/* 482 */       File folder = new File(tempPath);
/* 483 */       if (folder.exists()) {
/* 484 */         return folder;
/*     */       }
/*     */     } 
/* 487 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\profesorfalken\jpowershell\PowerShell.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */