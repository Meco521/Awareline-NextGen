/*     */ package com.jprocesses.info;
/*     */ 
/*     */ import com.jprocesses.model.JProcessesResponse;
/*     */ import com.jprocesses.model.ProcessInfo;
/*     */ import com.jprocesses.util.ProcessesUtils;
/*     */ import com.profesorfalken.wmi4java.WMI4Java;
/*     */ import com.profesorfalken.wmi4java.WMIClass;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
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
/*     */ class WindowsProcessesService
/*     */   extends AbstractProcessesService
/*     */ {
/*  37 */   private final Map<String, String> userData = new HashMap<>();
/*  38 */   private final Map<String, String> cpuData = new HashMap<>();
/*     */   
/*     */   private static final String LINE_BREAK_REGEX = "\\r?\\n";
/*     */   
/*     */   private static final Map<String, String> keyMap;
/*     */   
/*     */   private Map<String, String> processMap;
/*     */   
/*     */   private static final String NAME_PROPNAME = "Name";
/*     */   
/*     */   private static final String PROCESSID_PROPNAME = "ProcessId";
/*     */   private static final String USERMODETIME_PROPNAME = "UserModeTime";
/*     */   private static final String PRIORITY_PROPNAME = "Priority";
/*     */   private static final String VIRTUALSIZE_PROPNAME = "VirtualSize";
/*     */   private static final String WORKINGSETSIZE_PROPNAME = "WorkingSetSize";
/*     */   private static final String COMMANDLINE_PROPNAME = "CommandLine";
/*     */   private static final String CREATIONDATE_PROPNAME = "CreationDate";
/*     */   private static final String CAPTION_PROPNAME = "Caption";
/*     */   private final WMI4Java wmi4Java;
/*     */   
/*     */   static {
/*  59 */     Map<String, String> tmpMap = new HashMap<>();
/*  60 */     tmpMap.put("Name", "proc_name");
/*  61 */     tmpMap.put("ProcessId", "pid");
/*  62 */     tmpMap.put("UserModeTime", "proc_time");
/*  63 */     tmpMap.put("Priority", "priority");
/*  64 */     tmpMap.put("VirtualSize", "virtual_memory");
/*  65 */     tmpMap.put("WorkingSetSize", "physical_memory");
/*  66 */     tmpMap.put("CommandLine", "command");
/*  67 */     tmpMap.put("CreationDate", "start_time");
/*     */     
/*  69 */     keyMap = Collections.unmodifiableMap(tmpMap);
/*     */   }
/*     */   
/*     */   public WindowsProcessesService() {
/*  73 */     this(null);
/*     */   }
/*     */   
/*     */   WindowsProcessesService(WMI4Java wmi4Java) {
/*  77 */     this.wmi4Java = wmi4Java;
/*     */   }
/*     */   
/*     */   public WMI4Java getWmi4Java() {
/*  81 */     if (this.wmi4Java == null) {
/*  82 */       return WMI4Java.get();
/*     */     }
/*  84 */     return this.wmi4Java;
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<Map<String, String>> parseList(String rawData) {
/*  89 */     List<Map<String, String>> processesDataList = new ArrayList<>();
/*     */     
/*  91 */     String[] dataStringLines = rawData.split("\\r?\\n");
/*     */     
/*  93 */     for (String dataLine : dataStringLines) {
/*  94 */       if (!dataLine.trim().isEmpty()) {
/*  95 */         processLine(dataLine, processesDataList);
/*     */       }
/*     */     } 
/*     */     
/*  99 */     return processesDataList;
/*     */   }
/*     */   
/*     */   private void processLine(String dataLine, List<? super Map<String, String>> processesDataList) {
/* 103 */     if (dataLine.startsWith("Caption")) {
/* 104 */       this.processMap = new HashMap<>();
/* 105 */       processesDataList.add(this.processMap);
/*     */     } 
/*     */     
/* 108 */     if (this.processMap != null) {
/* 109 */       String[] dataStringInfo = dataLine.split(":", 2);
/* 110 */       this.processMap.put(normalizeKey(dataStringInfo[0].trim()), 
/* 111 */           normalizeValue(dataStringInfo[0].trim(), dataStringInfo[1].trim()));
/*     */       
/* 113 */       if ("ProcessId".equals(dataStringInfo[0].trim())) {
/* 114 */         this.processMap.put("user", this.userData.get(dataStringInfo[1].trim()));
/* 115 */         this.processMap.put("cpu_usage", this.cpuData.get(dataStringInfo[1].trim()));
/*     */       } 
/*     */       
/* 118 */       if ("CreationDate".equals(dataStringInfo[0].trim())) {
/* 119 */         this.processMap.put("start_datetime", 
/* 120 */             ProcessesUtils.parseWindowsDateTimeToFullDate(dataStringInfo[1].trim()));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getProcessesData(String name) {
/* 127 */     if (!this.fastMode) {
/* 128 */       fillExtraProcessData();
/*     */     }
/*     */     
/* 131 */     if (name != null) {
/* 132 */       return getWmi4Java().VBSEngine()
/* 133 */         .properties(Arrays.asList(new String[] {
/*     */               
/*     */               "Caption", "ProcessId", "Name", "UserModeTime", "CommandLine", "WorkingSetSize", "CreationDate", "VirtualSize", "Priority"
/*     */             
/* 137 */             })).filters(Collections.singletonList("Name like '%" + name + "%'"))
/* 138 */         .getRawWMIObjectOutput(WMIClass.WIN32_PROCESS);
/*     */     }
/*     */     
/* 141 */     return getWmi4Java().VBSEngine().getRawWMIObjectOutput(WMIClass.WIN32_PROCESS);
/*     */   }
/*     */ 
/*     */   
/*     */   protected JProcessesResponse kill(int pid) {
/* 146 */     JProcessesResponse response = new JProcessesResponse();
/* 147 */     if (ProcessesUtils.executeCommandAndGetCode(new String[] { "taskkill", "/PID", String.valueOf(pid), "/F" }) == 0) {
/* 148 */       response.setSuccess(true);
/*     */     }
/*     */     
/* 151 */     return response;
/*     */   }
/*     */ 
/*     */   
/*     */   protected JProcessesResponse killGracefully(int pid) {
/* 156 */     JProcessesResponse response = new JProcessesResponse();
/* 157 */     if (ProcessesUtils.executeCommandAndGetCode(new String[] { "taskkill", "/PID", String.valueOf(pid) }) == 0) {
/* 158 */       response.setSuccess(true);
/*     */     }
/*     */     
/* 161 */     return response;
/*     */   }
/*     */   
/*     */   private static String normalizeKey(String origKey) {
/* 165 */     return keyMap.get(origKey);
/*     */   }
/*     */   
/*     */   private static String normalizeValue(String origKey, String origValue) {
/* 169 */     if ("UserModeTime".equals(origKey)) {
/*     */       
/* 171 */       long seconds = Long.parseLong(origValue) * 100L / 1000000L / 1000L;
/* 172 */       return nomalizeTime(seconds);
/*     */     } 
/* 174 */     if (("VirtualSize".equals(origKey) || "WorkingSetSize".equals(origKey)) && 
/* 175 */       !origValue.isEmpty()) {
/* 176 */       return String.valueOf(Long.parseLong(origValue) / 1024L);
/*     */     }
/*     */     
/* 179 */     if ("CreationDate".equals(origKey)) {
/* 180 */       return ProcessesUtils.parseWindowsDateTimeToSimpleTime(origValue);
/*     */     }
/*     */     
/* 183 */     return origValue;
/*     */   }
/*     */   
/*     */   private static String nomalizeTime(long seconds) {
/* 187 */     long hours = seconds / 3600L;
/* 188 */     long minutes = seconds % 3600L / 60L;
/*     */     
/* 190 */     return String.format("%02d:%02d:%02d", new Object[] { Long.valueOf(hours), Long.valueOf(minutes), Long.valueOf(seconds) });
/*     */   }
/*     */   
/*     */   private void fillExtraProcessData() {
/* 194 */     String perfData = getWmi4Java().VBSEngine().getRawWMIObjectOutput(WMIClass.WIN32_PERFFORMATTEDDATA_PERFPROC_PROCESS);
/*     */     
/* 196 */     String[] dataStringLines = perfData.split("\\r?\\n");
/* 197 */     String pid = null;
/* 198 */     String cpuUsage = null;
/* 199 */     for (String dataLine : dataStringLines) {
/*     */       
/* 201 */       if (!dataLine.trim().isEmpty()) {
/* 202 */         if (dataLine.startsWith("Caption")) {
/* 203 */           if (pid != null && cpuUsage != null) {
/* 204 */             this.cpuData.put(pid, cpuUsage);
/* 205 */             pid = null;
/* 206 */             cpuUsage = null;
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 211 */           if (pid == null) {
/* 212 */             pid = checkAndGetDataInLine("IDProcess", dataLine);
/*     */           }
/* 214 */           if (cpuUsage == null) {
/* 215 */             cpuUsage = checkAndGetDataInLine("PercentProcessorTime", dataLine);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/* 220 */     String processesData = VBScriptHelper.getProcessesOwner();
/*     */     
/* 222 */     if (processesData != null) {
/* 223 */       dataStringLines = processesData.split("\\r?\\n");
/* 224 */       for (String dataLine : dataStringLines) {
/* 225 */         String[] dataStringInfo = dataLine.split(":", 2);
/* 226 */         if (dataStringInfo.length == 2) {
/* 227 */           this.userData.put(dataStringInfo[0].trim(), dataStringInfo[1].trim());
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String checkAndGetDataInLine(String dataName, String dataLine) {
/* 234 */     if (dataLine.startsWith(dataName)) {
/* 235 */       String[] dataStringInfo = dataLine.split(":");
/* 236 */       if (dataStringInfo.length == 2) {
/* 237 */         return dataStringInfo[1].trim();
/*     */       }
/*     */     } 
/* 240 */     return null;
/*     */   }
/*     */   
/*     */   public JProcessesResponse changePriority(int pid, int priority) {
/* 244 */     JProcessesResponse response = new JProcessesResponse();
/* 245 */     String message = VBScriptHelper.changePriority(pid, priority);
/* 246 */     if (message == null || message.isEmpty()) {
/* 247 */       response.setSuccess(true);
/*     */     } else {
/* 249 */       response.setMessage(message);
/*     */     } 
/* 251 */     return response;
/*     */   }
/*     */   
/*     */   public ProcessInfo getProcess(int pid) {
/* 255 */     return getProcess(pid, false);
/*     */   }
/*     */   
/*     */   public ProcessInfo getProcess(int pid, boolean fastMode) {
/* 259 */     this.fastMode = fastMode;
/* 260 */     List<Map<String, String>> allProcesses = parseList(getProcessesData(null));
/*     */     
/* 262 */     for (Map<String, String> process : allProcesses) {
/* 263 */       if (String.valueOf(pid).equals(process.get("pid"))) {
/* 264 */         ProcessInfo info = new ProcessInfo();
/* 265 */         info.setPid(process.get("pid"));
/* 266 */         info.setName(process.get("proc_name"));
/* 267 */         info.setTime(process.get("proc_time"));
/* 268 */         info.setCommand(process.get("command"));
/* 269 */         info.setCpuUsage(process.get("cpu_usage"));
/* 270 */         info.setPhysicalMemory(process.get("physical_memory"));
/* 271 */         info.setStartTime(process.get("start_time"));
/* 272 */         info.setUser(process.get("user"));
/* 273 */         info.setVirtualMemory(process.get("virtual_memory"));
/* 274 */         info.setPriority(process.get("priority"));
/*     */         
/* 276 */         return info;
/*     */       } 
/*     */     } 
/* 279 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\jprocesses\info\WindowsProcessesService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */