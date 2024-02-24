/*     */ package com.jprocesses.model;
/*     */ 
/*     */ import java.util.HashMap;
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
/*     */ 
/*     */ 
/*     */ public class ProcessInfo
/*     */ {
/*     */   private String pid;
/*     */   private String time;
/*     */   private String name;
/*     */   private String user;
/*     */   private String virtualMemory;
/*     */   private String physicalMemory;
/*     */   private String cpuUsage;
/*     */   private String startTime;
/*     */   private String priority;
/*     */   private String command;
/*  40 */   private Map<String, String> extraData = new HashMap<>();
/*     */ 
/*     */   
/*     */   public ProcessInfo() {}
/*     */   
/*     */   public ProcessInfo(String pid, String time, String name, String user, String virtualMemory, String physicalMemory, String cpuUsage, String startTime, String priority, String command) {
/*  46 */     this.pid = pid;
/*  47 */     this.time = time;
/*  48 */     this.name = name;
/*  49 */     this.user = user;
/*  50 */     this.virtualMemory = virtualMemory;
/*  51 */     this.physicalMemory = physicalMemory;
/*  52 */     this.cpuUsage = cpuUsage;
/*  53 */     this.startTime = startTime;
/*  54 */     this.priority = priority;
/*  55 */     this.command = command;
/*     */   }
/*     */   
/*     */   public String getPid() {
/*  59 */     return this.pid;
/*     */   }
/*     */   
/*     */   public void setPid(String pid) {
/*  63 */     this.pid = pid;
/*     */   }
/*     */   
/*     */   public String getTime() {
/*  67 */     return this.time;
/*     */   }
/*     */   
/*     */   public void setTime(String time) {
/*  71 */     this.time = time;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  75 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/*  79 */     this.name = name;
/*     */   }
/*     */   
/*     */   public String getUser() {
/*  83 */     return this.user;
/*     */   }
/*     */   
/*     */   public void setUser(String user) {
/*  87 */     this.user = user;
/*     */   }
/*     */   
/*     */   public String getVirtualMemory() {
/*  91 */     return this.virtualMemory;
/*     */   }
/*     */   
/*     */   public void setVirtualMemory(String virtualMemory) {
/*  95 */     this.virtualMemory = virtualMemory;
/*     */   }
/*     */   
/*     */   public String getPhysicalMemory() {
/*  99 */     return this.physicalMemory;
/*     */   }
/*     */   
/*     */   public void setPhysicalMemory(String physicalMemory) {
/* 103 */     this.physicalMemory = physicalMemory;
/*     */   }
/*     */   
/*     */   public String getCpuUsage() {
/* 107 */     return this.cpuUsage;
/*     */   }
/*     */   
/*     */   public void setCpuUsage(String cpuUsage) {
/* 111 */     this.cpuUsage = cpuUsage;
/*     */   }
/*     */   
/*     */   public String getStartTime() {
/* 115 */     return this.startTime;
/*     */   }
/*     */   
/*     */   public void setStartTime(String startTime) {
/* 119 */     this.startTime = startTime;
/*     */   }
/*     */   
/*     */   public String getCommand() {
/* 123 */     return this.command;
/*     */   }
/*     */   
/*     */   public void setCommand(String command) {
/* 127 */     this.command = command;
/*     */   }
/*     */   
/*     */   public String getPriority() {
/* 131 */     return this.priority;
/*     */   }
/*     */   
/*     */   public void setPriority(String priority) {
/* 135 */     this.priority = priority;
/*     */   }
/*     */   
/*     */   public Map<String, String> getExtraData() {
/* 139 */     return this.extraData;
/*     */   }
/*     */   
/*     */   public void setExtraData(Map<String, String> extraData) {
/* 143 */     this.extraData = extraData;
/*     */   }
/*     */   
/*     */   public void addExtraData(String key, String value) {
/* 147 */     this.extraData.put(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 152 */     if (this == o) return true; 
/* 153 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 155 */     ProcessInfo that = (ProcessInfo)o;
/*     */     
/* 157 */     if ((this.pid != null) ? !this.pid.equals(that.pid) : (that.pid != null)) return false; 
/* 158 */     if ((this.time != null) ? !this.time.equals(that.time) : (that.time != null)) return false; 
/* 159 */     if ((this.name != null) ? !this.name.equals(that.name) : (that.name != null)) return false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 166 */     if ((this.startTime != null) ? !this.startTime.equals(that.startTime) : (that.startTime != null)) return false; 
/* 167 */     if ((this.priority != null) ? !this.priority.equals(that.priority) : (that.priority != null)) return false; 
/* 168 */     return (this.command != null) ? this.command.equals(that.command) : ((that.command == null));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 174 */     int result = (this.pid != null) ? this.pid.hashCode() : 0;
/* 175 */     result = 31 * result + ((this.time != null) ? this.time.hashCode() : 0);
/* 176 */     result = 31 * result + ((this.name != null) ? this.name.hashCode() : 0);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 181 */     result = 31 * result + ((this.startTime != null) ? this.startTime.hashCode() : 0);
/* 182 */     result = 31 * result + ((this.priority != null) ? this.priority.hashCode() : 0);
/* 183 */     result = 31 * result + ((this.command != null) ? this.command.hashCode() : 0);
/* 184 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 189 */     return "PID:" + this.pid + "\tCPU:" + this.cpuUsage + "\tMEM:" + this.physicalMemory + "\tPRIORITY:" + this.priority + "\tCMD:" + this.command;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\jprocesses\model\ProcessInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */