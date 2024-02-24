/*     */ package com.jprocesses.info;
/*     */ 
/*     */ import com.jprocesses.model.JProcessesResponse;
/*     */ import com.jprocesses.model.ProcessInfo;
/*     */ import com.jprocesses.util.OSDetector;
/*     */ import com.jprocesses.util.ProcessesUtils;
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
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
/*     */ class UnixProcessesService
/*     */   extends AbstractProcessesService
/*     */ {
/*     */   private static final String PS_COLUMNS = "pid,ruser,vsize,rss,%cpu,lstart,cputime,nice,ucomm";
/*     */   private static final String PS_FULL_COMMAND = "pid,command";
/*  37 */   private static final int PS_COLUMNS_SIZE = ("pid,ruser,vsize,rss,%cpu,lstart,cputime,nice,ucomm".split(",")).length;
/*  38 */   private static final int PS_FULL_COMMAND_SIZE = ("pid,command".split(",")).length;
/*     */   
/*  40 */   private String nameFilter = null;
/*     */ 
/*     */   
/*     */   protected List<Map<String, String>> parseList(String rawData) {
/*  44 */     List<Map<String, String>> processesDataList = new ArrayList<>();
/*  45 */     String[] dataStringLines = rawData.split("\\r?\\n");
/*     */ 
/*     */     
/*  48 */     for (String dataLine : dataStringLines) {
/*  49 */       String line = dataLine.trim();
/*  50 */       if (!line.startsWith("PID")) {
/*     */ 
/*     */ 
/*     */         
/*  54 */         Map<String, String> element = new LinkedHashMap<>();
/*  55 */         String[] elements = line.split("\\s+", PS_COLUMNS_SIZE + 5);
/*  56 */         int index = 0;
/*  57 */         element.put("pid", elements[index++]);
/*  58 */         element.put("user", elements[index++]);
/*  59 */         element.put("virtual_memory", elements[index++]);
/*  60 */         element.put("physical_memory", elements[index++]);
/*  61 */         element.put("cpu_usage", elements[index++]);
/*  62 */         index++;
/*  63 */         String longDate = elements[index++] + " " + elements[index++] + " " + elements[index++] + " " + elements[index++];
/*     */ 
/*     */ 
/*     */         
/*  67 */         element.put("start_time", elements[index - 2]);
/*     */         try {
/*  69 */           element.put("start_datetime", 
/*  70 */               ProcessesUtils.parseUnixLongTimeToFullDate(longDate));
/*  71 */         } catch (ParseException e) {
/*  72 */           element.put("start_datetime", "01/01/2000 00:00:00");
/*  73 */           System.err.println("Failed formatting date from ps: " + longDate + ", using \"01/01/2000 00:00:00\"");
/*     */         } 
/*  75 */         element.put("proc_time", elements[index++]);
/*  76 */         element.put("priority", elements[index++]);
/*  77 */         element.put("proc_name", elements[index++]);
/*     */         
/*  79 */         element.put("command", elements[index - 1]);
/*     */         
/*  81 */         processesDataList.add(element);
/*     */       } 
/*     */     } 
/*  84 */     loadFullCommandData(processesDataList);
/*     */     
/*  86 */     if (this.nameFilter != null) {
/*  87 */       filterByName(processesDataList);
/*     */     }
/*     */     
/*  90 */     return processesDataList;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getProcessesData(String name) {
/*  95 */     if (name != null) {
/*  96 */       if (OSDetector.isLinux()) {
/*  97 */         return ProcessesUtils.executeCommand(new String[] { "ps", "-o", "pid,ruser,vsize,rss,%cpu,lstart,cputime,nice,ucomm", "-C", name });
/*     */       }
/*     */       
/* 100 */       this.nameFilter = name;
/*     */     } 
/*     */     
/* 103 */     return ProcessesUtils.executeCommand(new String[] { "ps", "-e", "-o", "pid,ruser,vsize,rss,%cpu,lstart,cputime,nice,ucomm" });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected JProcessesResponse kill(int pid) {
/* 109 */     JProcessesResponse response = new JProcessesResponse();
/* 110 */     if (ProcessesUtils.executeCommandAndGetCode(new String[] { "kill", "-9", String.valueOf(pid) }) == 0) {
/* 111 */       response.setSuccess(true);
/*     */     }
/* 113 */     return response;
/*     */   }
/*     */ 
/*     */   
/*     */   protected JProcessesResponse killGracefully(int pid) {
/* 118 */     JProcessesResponse response = new JProcessesResponse();
/* 119 */     if (ProcessesUtils.executeCommandAndGetCode(new String[] { "kill", "-15", String.valueOf(pid) }) == 0) {
/* 120 */       response.setSuccess(true);
/*     */     }
/* 122 */     return response;
/*     */   }
/*     */   
/*     */   public JProcessesResponse changePriority(int pid, int priority) {
/* 126 */     JProcessesResponse response = new JProcessesResponse();
/* 127 */     if (ProcessesUtils.executeCommandAndGetCode(new String[] { "renice", String.valueOf(priority), "-p", 
/* 128 */           String.valueOf(pid) }) == 0) {
/* 129 */       response.setSuccess(true);
/*     */     }
/* 131 */     return response;
/*     */   }
/*     */   
/*     */   public ProcessInfo getProcess(int pid) {
/* 135 */     return getProcess(pid, false);
/*     */   }
/*     */   
/*     */   public ProcessInfo getProcess(int pid, boolean fastMode) {
/* 139 */     this.fastMode = fastMode;
/*     */     
/* 141 */     List<Map<String, String>> processList = parseList(ProcessesUtils.executeCommand(new String[] {
/* 142 */             "ps", "-o", "pid,ruser,vsize,rss,%cpu,lstart,cputime,nice,ucomm", "-p", String.valueOf(pid)
/*     */           }));
/* 144 */     if (processList != null && !processList.isEmpty()) {
/* 145 */       Map<String, String> processData = processList.get(0);
/* 146 */       ProcessInfo info = new ProcessInfo();
/* 147 */       info.setPid(processData.get("pid"));
/* 148 */       info.setName(processData.get("proc_name"));
/* 149 */       info.setTime(processData.get("proc_time"));
/* 150 */       info.setCommand(processData.get("command"));
/* 151 */       info.setCpuUsage(processData.get("cpu_usage"));
/* 152 */       info.setPhysicalMemory(processData.get("physical_memory"));
/* 153 */       info.setStartTime(processData.get("start_time"));
/* 154 */       info.setUser(processData.get("user"));
/* 155 */       info.setVirtualMemory(processData.get("virtual_memory"));
/* 156 */       info.setPriority(processData.get("priority"));
/*     */       
/* 158 */       return info;
/*     */     } 
/* 160 */     return null;
/*     */   }
/*     */   
/*     */   private static void loadFullCommandData(List<? extends Map<String, String>> processesDataList) {
/* 164 */     Map<String, String> commandsMap = new HashMap<>();
/* 165 */     String data = ProcessesUtils.executeCommand(new String[] { "ps", "-e", "-o", "pid,command" });
/*     */     
/* 167 */     String[] dataStringLines = data.split("\\r?\\n");
/*     */     
/* 169 */     for (String dataLine : dataStringLines) {
/* 170 */       if (!dataLine.trim().startsWith("PID")) {
/* 171 */         String[] elements = dataLine.trim().split("\\s+", PS_FULL_COMMAND_SIZE);
/* 172 */         if (elements.length == PS_FULL_COMMAND_SIZE) {
/* 173 */           commandsMap.put(elements[0], elements[1]);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 178 */     for (Map<String, String> process : processesDataList) {
/* 179 */       if (commandsMap.containsKey(process.get("pid"))) {
/* 180 */         process.put("command", commandsMap.get(process.get("pid")));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void filterByName(List<? extends Map<String, String>> processesDataList) {
/* 187 */     List<Map<String, String>> processesToRemove = new ArrayList<>();
/* 188 */     for (Map<String, String> process : processesDataList) {
/* 189 */       if (!this.nameFilter.equals(process.get("proc_name"))) {
/* 190 */         processesToRemove.add(process);
/*     */       }
/*     */     } 
/* 193 */     processesDataList.removeAll(processesToRemove);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\jprocesses\info\UnixProcessesService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */