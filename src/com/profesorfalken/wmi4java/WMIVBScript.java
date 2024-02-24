/*     */ package com.profesorfalken.wmi4java;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.Date;
/*     */ import java.util.List;
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
/*     */ class WMIVBScript
/*     */   implements WMIStub
/*     */ {
/*     */   private static final String ROOT_CIMV2 = "root/cimv2";
/*     */   private static final String IMPERSONATION_VARIABLE = "Set objWMIService=GetObject(\"winmgmts:{impersonationLevel=impersonate}!\\\\";
/*     */   private static final String CRLF = "\r\n";
/*     */   
/*     */   private static String executeScript(String scriptCode) throws WMIException {
/*  37 */     StringBuilder scriptResponse = new StringBuilder();
/*  38 */     File tmpFile = null;
/*  39 */     FileWriter writer = null;
/*  40 */     BufferedReader errorOutput = null;
/*     */     
/*     */     try {
/*  43 */       tmpFile = File.createTempFile("wmi4java" + (new Date()).getTime(), ".vbs");
/*  44 */       writer = new FileWriter(tmpFile);
/*  45 */       writer.write(scriptCode);
/*  46 */       writer.flush();
/*  47 */       writer.close();
/*     */       
/*  49 */       Process process = Runtime.getRuntime().exec(new String[] { "cmd.exe", "/C", "cscript.exe", "/NoLogo", tmpFile
/*  50 */             .getAbsolutePath() });
/*     */       
/*  52 */       BufferedReader processOutput = new BufferedReader(new InputStreamReader(process.getInputStream()));
/*     */       String line;
/*  54 */       while ((line = processOutput.readLine()) != null) {
/*  55 */         if (!line.isEmpty()) {
/*  56 */           scriptResponse.append(line).append("\r\n");
/*     */         }
/*     */       } 
/*     */       
/*  60 */       if (scriptResponse.length() == 0)
/*     */       {
/*  62 */         errorOutput = new BufferedReader(new InputStreamReader(process.getInputStream()));
/*  63 */         StringBuilder errorResponse = new StringBuilder();
/*  64 */         while ((line = errorOutput.readLine()) != null) {
/*  65 */           if (!line.isEmpty()) {
/*  66 */             errorResponse.append(line).append("\r\n");
/*     */           }
/*     */         } 
/*  69 */         if (errorResponse.length() > 0) {
/*  70 */           throw new WMIException("WMI operation finished in error: " + errorResponse);
/*     */         }
/*     */       }
/*     */     
/*     */     }
/*  75 */     catch (Exception ex) {
/*  76 */       throw new WMIException(ex.getMessage(), ex);
/*     */     } finally {
/*     */       try {
/*  79 */         if (writer != null) {
/*  80 */           writer.close();
/*     */         }
/*  82 */         if (tmpFile != null) {
/*  83 */           tmpFile.delete();
/*     */         }
/*  85 */         if (errorOutput != null) {
/*  86 */           errorOutput.close();
/*     */         }
/*  88 */       } catch (IOException ioe) {
/*  89 */         Logger.getLogger(WMI4Java.class.getName()).log(Level.SEVERE, "Exception closing in finally", ioe);
/*     */       } 
/*     */     } 
/*  92 */     return scriptResponse.toString().trim();
/*     */   }
/*     */ 
/*     */   
/*     */   public String listClasses(String namespace, String computerName) throws WMIException {
/*     */     try {
/*  98 */       StringBuilder scriptCode = new StringBuilder(200);
/*     */       
/* 100 */       String namespaceCommand = "root/cimv2";
/* 101 */       if (!"*".equals(namespace)) {
/* 102 */         namespaceCommand = namespace;
/*     */       }
/*     */       
/* 105 */       scriptCode.append("Set objWMIService=GetObject(\"winmgmts:{impersonationLevel=impersonate}!\\\\")
/* 106 */         .append(computerName).append("/").append(namespaceCommand).append("\")").append("\r\n");
/*     */       
/* 108 */       scriptCode.append("Set colClasses = objWMIService.SubclassesOf()").append("\r\n");
/*     */       
/* 110 */       scriptCode.append("For Each objClass in colClasses").append("\r\n");
/* 111 */       scriptCode.append("For Each objClassQualifier In objClass.Qualifiers_").append("\r\n");
/* 112 */       scriptCode.append("WScript.Echo objClass.Path_.Class").append("\r\n");
/* 113 */       scriptCode.append("Next").append("\r\n");
/* 114 */       scriptCode.append("Next").append("\r\n");
/*     */       
/* 116 */       return executeScript(scriptCode.toString());
/* 117 */     } catch (Exception ex) {
/* 118 */       throw new WMIException(ex.getMessage(), ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String listProperties(String wmiClass, String namespace, String computerName) throws WMIException {
/*     */     try {
/* 124 */       StringBuilder scriptCode = new StringBuilder(200);
/*     */       
/* 126 */       String namespaceCommand = "root/cimv2";
/* 127 */       if (!"*".equals(namespace)) {
/* 128 */         namespaceCommand = namespace;
/*     */       }
/*     */       
/* 131 */       scriptCode.append("Set objWMIService=GetObject(\"winmgmts:{impersonationLevel=impersonate}!\\\\")
/* 132 */         .append(computerName).append("/").append(namespaceCommand).append(":")
/* 133 */         .append(wmiClass).append("\")").append("\r\n");
/*     */       
/* 135 */       scriptCode.append("For Each objClassProperty In objWMIService.Properties_").append("\r\n");
/* 136 */       scriptCode.append("WScript.Echo objClassProperty.Name").append("\r\n");
/* 137 */       scriptCode.append("Next").append("\r\n");
/*     */       
/* 139 */       return executeScript(scriptCode.toString());
/*     */     }
/* 141 */     catch (Exception ex) {
/* 142 */       throw new WMIException(ex.getMessage(), ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String listObject(String wmiClass, String namespace, String computerName) throws WMIException {
/* 147 */     return queryObject(wmiClass, null, null, namespace, computerName);
/*     */   }
/*     */   
/*     */   public String queryObject(String wmiClass, List<String> wmiProperties, List<String> conditions, String namespace, String computerName) throws WMIException {
/*     */     List<String> usedWMIProperties;
/* 152 */     if (wmiProperties == null || wmiProperties.isEmpty()) {
/* 153 */       usedWMIProperties = WMI4Java.get().VBSEngine().computerName(computerName).namespace(namespace).listProperties(wmiClass);
/*     */     } else {
/* 155 */       usedWMIProperties = wmiProperties;
/*     */     } 
/*     */     try {
/* 158 */       StringBuilder scriptCode = new StringBuilder(200);
/*     */       
/* 160 */       String namespaceCommand = "root/cimv2";
/* 161 */       if (!"*".equals(namespace)) {
/* 162 */         namespaceCommand = namespace;
/*     */       }
/*     */       
/* 165 */       scriptCode.append("Set objWMIService=GetObject(\"winmgmts:{impersonationLevel=impersonate}!\\\\")
/* 166 */         .append(computerName).append("/").append(namespaceCommand).append("\")").append("\r\n");
/*     */       
/* 168 */       scriptCode.append("Set colClasses = objWMIService.SubclassesOf()").append("\r\n");
/*     */       
/* 170 */       scriptCode.append("Set wmiQueryData = objWMIService.ExecQuery(\"Select ").append("*").append(" from ")
/* 171 */         .append(wmiClass);
/* 172 */       if (conditions != null && !conditions.isEmpty()) {
/* 173 */         scriptCode.append(" where ").append(WMI4JavaUtil.join(" AND ", conditions));
/*     */       }
/* 175 */       scriptCode.append("\")").append("\r\n");
/* 176 */       scriptCode.append("For Each element In wmiQueryData").append("\r\n");
/* 177 */       for (String wmiProperty : usedWMIProperties) {
/* 178 */         if (!wmiProperty.equals("ConfigOptions")) {
/* 179 */           scriptCode.append("Wscript.Echo \"").append(wmiProperty)
/* 180 */             .append(": \" & ").append("element.").append(wmiProperty).append("\r\n");
/*     */           continue;
/*     */         } 
/* 183 */         scriptCode.append("Wscript.Echo \"").append(wmiProperty)
/* 184 */           .append(": \" & ").append("Join(element.").append(wmiProperty).append(", \"|\")").append("\r\n");
/*     */       } 
/*     */       
/* 187 */       scriptCode.append("Next").append("\r\n");
/*     */       
/* 189 */       return executeScript(scriptCode.toString());
/* 190 */     } catch (Exception ex) {
/* 191 */       throw new WMIException(ex.getMessage(), ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\profesorfalken\wmi4java\WMIVBScript.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */