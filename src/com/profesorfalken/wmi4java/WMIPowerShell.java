/*     */ package com.profesorfalken.wmi4java;
/*     */ 
/*     */ import com.profesorfalken.jpowershell.PowerShell;
/*     */ import com.profesorfalken.jpowershell.PowerShellNotAvailableException;
/*     */ import com.profesorfalken.jpowershell.PowerShellResponse;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ class WMIPowerShell
/*     */   implements WMIStub
/*     */ {
/*     */   private static final String NAMESPACE_PARAM = "-Namespace ";
/*     */   private static final String COMPUTERNAME_PARAM = "-ComputerName ";
/*     */   private static final String GETWMIOBJECT_COMMAND = "Get-WMIObject ";
/*     */   
/*     */   private static String executeCommand(String command) throws WMIException {
/*  39 */     String commandResponse = null;
/*  40 */     PowerShell powerShell = null;
/*     */     try {
/*  42 */       powerShell = PowerShell.openSession();
/*  43 */       Map<String, String> config = new HashMap<>();
/*  44 */       config.put("maxWait", "20000");
/*  45 */       PowerShellResponse psResponse = powerShell.configuration(config).executeCommand(command);
/*     */       
/*  47 */       if (psResponse.isError()) {
/*  48 */         throw new WMIException("WMI operation finished in error: " + psResponse
/*  49 */             .getCommandOutput());
/*     */       }
/*     */       
/*  52 */       commandResponse = psResponse.getCommandOutput().trim();
/*     */       
/*  54 */       powerShell.close();
/*  55 */     } catch (PowerShellNotAvailableException ex) {
/*  56 */       throw new WMIException(ex.getMessage(), ex);
/*     */     } finally {
/*  58 */       if (powerShell != null) {
/*  59 */         powerShell.close();
/*     */       }
/*     */     } 
/*     */     
/*  63 */     return commandResponse;
/*     */   }
/*     */   
/*     */   public String listClasses(String namespace, String computerName) throws WMIException {
/*  67 */     String namespaceString = "";
/*  68 */     if (!"*".equals(namespace)) {
/*  69 */       namespaceString = namespaceString + "-Namespace " + namespace;
/*     */     }
/*     */     
/*  72 */     return executeCommand("Get-WMIObject " + namespaceString + " -List | Sort Name");
/*     */   }
/*     */ 
/*     */   
/*     */   public String listProperties(String wmiClass, String namespace, String computerName) throws WMIException {
/*  77 */     String command = initCommand(wmiClass, namespace, computerName);
/*     */     
/*  79 */     command = command + " | ";
/*     */     
/*  81 */     command = command + "Select-Object * -excludeproperty \"_*\" | ";
/*     */     
/*  83 */     command = command + "Get-Member | select name | format-table -hidetableheader";
/*     */     
/*  85 */     return executeCommand(command);
/*     */   }
/*     */   
/*     */   public String listObject(String wmiClass, String namespace, String computerName) throws WMIException {
/*  89 */     String command = initCommand(wmiClass, namespace, computerName);
/*     */     
/*  91 */     command = command + " | ";
/*     */     
/*  93 */     command = command + "Select-Object * -excludeproperty \"_*\" | ";
/*     */     
/*  95 */     command = command + "Format-List *";
/*     */     
/*  97 */     return executeCommand(command);
/*     */   }
/*     */   public String queryObject(String wmiClass, List<String> wmiProperties, List<String> conditions, String namespace, String computerName) throws WMIException {
/*     */     List<String> usedWMIProperties;
/* 101 */     StringBuilder command = new StringBuilder(initCommand(wmiClass, namespace, computerName));
/*     */ 
/*     */     
/* 104 */     if (wmiProperties == null || wmiProperties.isEmpty()) {
/* 105 */       usedWMIProperties = Collections.singletonList("*");
/*     */     } else {
/* 107 */       usedWMIProperties = wmiProperties;
/*     */     } 
/*     */     
/* 110 */     command.append(" | ");
/*     */     
/* 112 */     command.append("Select-Object ").append(WMI4JavaUtil.join(", ", usedWMIProperties)).append(" -excludeproperty \"_*\" | ");
/*     */     
/* 114 */     if (conditions != null && !conditions.isEmpty()) {
/* 115 */       for (String condition : conditions) {
/* 116 */         command.append("Where-Object -FilterScript {").append(condition).append("} | ");
/*     */       }
/*     */     }
/*     */     
/* 120 */     command.append("Format-List *");
/*     */     
/* 122 */     return executeCommand(command.toString());
/*     */   }
/*     */   
/*     */   private String initCommand(String wmiClass, String namespace, String computerName) {
/* 126 */     String command = "Get-WMIObject " + wmiClass + " ";
/*     */     
/* 128 */     if (!"*".equals(namespace)) {
/* 129 */       command = command + "-Namespace " + namespace + " ";
/*     */     }
/* 131 */     if (!computerName.isEmpty()) {
/* 132 */       command = command + "-ComputerName " + computerName + " ";
/*     */     }
/*     */     
/* 135 */     return command;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\profesorfalken\wmi4java\WMIPowerShell.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */