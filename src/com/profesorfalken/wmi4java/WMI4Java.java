/*     */ package com.profesorfalken.wmi4java;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WMI4Java
/*     */ {
/*     */   private static final String NEWLINE_REGEX = "\\r?\\n";
/*     */   private static final String SPACE_REGEX = "\\s+";
/*     */   private static final String GENERIC_ERROR_MSG = "Error calling WMI4Java";
/*  58 */   private String namespace = "*";
/*  59 */   private String computerName = ".";
/*     */   
/*     */   private boolean forceVBEngine = false;
/*  62 */   List<String> properties = null;
/*  63 */   List<String> filters = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private WMIStub getWMIStub() {
/*  71 */     if (this.forceVBEngine) {
/*  72 */       return new WMIVBScript();
/*     */     }
/*  74 */     return new WMIPowerShell();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static WMI4Java get() {
/*  84 */     return new WMI4Java();
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
/*     */   public WMI4Java namespace(String namespace) {
/*  96 */     this.namespace = namespace;
/*  97 */     return this;
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
/*     */   public WMI4Java computerName(String computerName) {
/* 109 */     this.computerName = computerName;
/* 110 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WMI4Java PowerShellEngine() {
/* 119 */     this.forceVBEngine = false;
/* 120 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WMI4Java VBSEngine() {
/* 129 */     this.forceVBEngine = true;
/* 130 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WMI4Java properties(List<String> properties) {
/* 140 */     this.properties = properties;
/* 141 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WMI4Java filters(List<String> filters) {
/* 151 */     this.filters = filters;
/* 152 */     return this;
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
/*     */   public List<String> listClasses() throws WMIException {
/* 164 */     List<String> wmiClasses = new ArrayList<>();
/*     */     
/*     */     try {
/* 167 */       String rawData = getWMIStub().listClasses(this.namespace, this.computerName);
/*     */       
/* 169 */       String[] dataStringLines = rawData.split("\\r?\\n");
/*     */       
/* 171 */       for (String line : dataStringLines) {
/* 172 */         if (!line.isEmpty() && (line.isEmpty() || line.charAt(0) != '_')) {
/* 173 */           String[] infos = line.split("\\s+");
/* 174 */           wmiClasses.addAll(Arrays.asList(infos));
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 179 */       Set<String> hs = new HashSet<>(wmiClasses);
/* 180 */       wmiClasses.clear();
/* 181 */       wmiClasses.addAll(hs);
/*     */     }
/* 183 */     catch (Exception ex) {
/* 184 */       Logger.getLogger(WMI4Java.class.getName()).log(Level.SEVERE, "Error calling WMI4Java", ex);
/* 185 */       throw new WMIException(ex);
/*     */     } 
/*     */     
/* 188 */     return wmiClasses;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> listProperties(String wmiClass) throws WMIException {
/* 198 */     List<String> foundPropertiesList = new ArrayList<>();
/*     */     try {
/* 200 */       String rawData = getWMIStub().listProperties(wmiClass, this.namespace, this.computerName);
/*     */       
/* 202 */       String[] dataStringLines = rawData.split("\\r?\\n");
/*     */       
/* 204 */       for (String line : dataStringLines) {
/* 205 */         if (!line.isEmpty()) {
/* 206 */           foundPropertiesList.add(line.trim());
/*     */         }
/*     */       } 
/*     */       
/* 210 */       List<String> notAllowed = Arrays.asList(new String[] { "Equals", "GetHashCode", "GetType", "ToString" });
/* 211 */       foundPropertiesList.removeAll(notAllowed);
/*     */     }
/* 213 */     catch (Exception ex) {
/* 214 */       Logger.getLogger(WMI4Java.class.getName()).log(Level.SEVERE, "Error calling WMI4Java", ex);
/* 215 */       throw new WMIException(ex);
/*     */     } 
/* 217 */     return foundPropertiesList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, String> getWMIObject(WMIClass wmiClass) {
/* 236 */     return getWMIObject(wmiClass.getName());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, String> getWMIObject(String wmiClass) throws WMIException {
/* 255 */     Map<String, String> foundWMIClassProperties = new HashMap<>();
/*     */     try {
/*     */       String rawData;
/* 258 */       if (this.properties != null || this.filters != null) {
/* 259 */         rawData = getWMIStub().queryObject(wmiClass, this.properties, this.filters, this.namespace, this.computerName);
/*     */       } else {
/*     */         
/* 262 */         rawData = getWMIStub().listObject(wmiClass, this.namespace, this.computerName);
/*     */       } 
/*     */       
/* 265 */       String[] dataStringLines = rawData.split("\\r?\\n");
/*     */       
/* 267 */       for (String line : dataStringLines) {
/* 268 */         if (!line.isEmpty()) {
/* 269 */           String[] entry = line.split(":");
/* 270 */           if (entry != null && entry.length == 2) {
/* 271 */             foundWMIClassProperties.put(entry[0].trim(), entry[1].trim());
/*     */           }
/*     */         } 
/*     */       } 
/* 275 */     } catch (WMIException ex) {
/* 276 */       Logger.getLogger(WMI4Java.class.getName()).log(Level.SEVERE, "Error calling WMI4Java", ex);
/* 277 */       throw new WMIException(ex);
/*     */     } 
/* 279 */     return foundWMIClassProperties;
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
/*     */ 
/*     */   
/*     */   public List<Map<String, String>> getWMIObjectList(WMIClass wmiClass) {
/* 295 */     return getWMIObjectList(wmiClass.getName());
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
/*     */ 
/*     */   
/*     */   public List<Map<String, String>> getWMIObjectList(String wmiClass) throws WMIException {
/* 311 */     List<Map<String, String>> foundWMIClassProperties = new ArrayList<>();
/*     */     try {
/*     */       String rawData;
/* 314 */       if (this.properties != null || this.filters != null) {
/* 315 */         rawData = getWMIStub().queryObject(wmiClass, this.properties, this.filters, this.namespace, this.computerName);
/*     */       } else {
/*     */         
/* 318 */         rawData = getWMIStub().listObject(wmiClass, this.namespace, this.computerName);
/*     */       } 
/*     */       
/* 321 */       String[] dataStringObjects = rawData.split("\\r?\\n\\r?\\n");
/* 322 */       for (String dataStringObject : dataStringObjects) {
/* 323 */         String[] dataStringLines = dataStringObject.split("\\r?\\n");
/* 324 */         Map<String, String> objectProperties = new HashMap<>();
/* 325 */         for (String line : dataStringLines) {
/* 326 */           if (!line.isEmpty()) {
/* 327 */             String[] entry = line.split(":");
/* 328 */             if (entry.length == 2) {
/* 329 */               objectProperties.put(entry[0].trim(), entry[1].trim());
/*     */             }
/*     */           } 
/*     */         } 
/* 333 */         foundWMIClassProperties.add(objectProperties);
/*     */       } 
/* 335 */     } catch (WMIException ex) {
/* 336 */       Logger.getLogger(WMI4Java.class.getName()).log(Level.SEVERE, "Error calling WMI4Java", ex);
/* 337 */       throw new WMIException(ex);
/*     */     } 
/*     */     
/* 340 */     return foundWMIClassProperties;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRawWMIObjectOutput(WMIClass wmiClass) {
/* 350 */     return getRawWMIObjectOutput(wmiClass.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRawWMIObjectOutput(String wmiClass) throws WMIException {
/*     */     String rawData;
/*     */     try {
/* 362 */       if (this.properties != null || this.filters != null) {
/* 363 */         rawData = getWMIStub().queryObject(wmiClass, this.properties, this.filters, this.namespace, this.computerName);
/*     */       } else {
/*     */         
/* 366 */         rawData = getWMIStub().listObject(wmiClass, this.namespace, this.computerName);
/*     */       } 
/* 368 */     } catch (WMIException ex) {
/* 369 */       Logger.getLogger(WMI4Java.class.getName()).log(Level.SEVERE, "Error calling WMI4Java", ex);
/* 370 */       throw new WMIException(ex);
/*     */     } 
/* 372 */     return rawData;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\profesorfalken\wmi4java\WMI4Java.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */