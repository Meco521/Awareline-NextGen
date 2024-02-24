/*    */ package com.jprocesses.info;
/*    */ 
/*    */ import com.jprocesses.model.JProcessesResponse;
/*    */ import com.jprocesses.model.ProcessInfo;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
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
/*    */ abstract class AbstractProcessesService
/*    */   implements ProcessesService
/*    */ {
/*    */   protected boolean fastMode = true;
/*    */   
/*    */   public List<ProcessInfo> getList() {
/* 35 */     return getList((String)null);
/*    */   }
/*    */   
/*    */   public List<ProcessInfo> getList(boolean fastMode) {
/* 39 */     return getList(null, fastMode);
/*    */   }
/*    */   
/*    */   public List<ProcessInfo> getList(String name) {
/* 43 */     return getList(name, false);
/*    */   }
/*    */   
/*    */   public List<ProcessInfo> getList(String name, boolean fastMode) {
/* 47 */     this.fastMode = fastMode;
/* 48 */     String rawData = getProcessesData(name);
/*    */     
/* 50 */     List<Map<String, String>> mapList = parseList(rawData);
/*    */     
/* 52 */     return buildInfoFromMap(mapList);
/*    */   }
/*    */   
/*    */   public JProcessesResponse killProcess(int pid) {
/* 56 */     return kill(pid);
/*    */   }
/*    */   
/*    */   public JProcessesResponse killProcessGracefully(int pid) {
/* 60 */     return killGracefully(pid);
/*    */   }
/*    */   
/*    */   protected abstract List<Map<String, String>> parseList(String paramString);
/*    */   
/*    */   protected abstract String getProcessesData(String paramString);
/*    */   
/*    */   protected abstract JProcessesResponse kill(int paramInt);
/*    */   
/*    */   protected abstract JProcessesResponse killGracefully(int paramInt);
/*    */   
/*    */   private List<ProcessInfo> buildInfoFromMap(List<? extends Map<String, String>> mapList) {
/* 72 */     List<ProcessInfo> infoList = new ArrayList<>();
/*    */     
/* 74 */     for (Map<String, String> map : mapList) {
/* 75 */       ProcessInfo info = new ProcessInfo();
/* 76 */       info.setPid(map.get("pid"));
/* 77 */       info.setName(map.get("proc_name"));
/* 78 */       info.setTime(map.get("proc_time"));
/* 79 */       info.setCommand((map.get("command") != null) ? map.get("command") : "");
/* 80 */       info.setCpuUsage(map.get("cpu_usage"));
/* 81 */       info.setPhysicalMemory(map.get("physical_memory"));
/* 82 */       info.setStartTime(map.get("start_time"));
/* 83 */       info.setUser(map.get("user"));
/* 84 */       info.setVirtualMemory(map.get("virtual_memory"));
/* 85 */       info.setPriority(map.get("priority"));
/*    */ 
/*    */       
/* 88 */       info.setExtraData(map);
/*    */       
/* 90 */       infoList.add(info);
/*    */     } 
/*    */     
/* 93 */     return infoList;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\jprocesses\info\AbstractProcessesService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */