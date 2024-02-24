/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.profiler.Profiler;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class EntityAITasks
/*     */ {
/*  13 */   private static final Logger logger = LogManager.getLogger();
/*  14 */   private final List<EntityAITaskEntry> taskEntries = Lists.newArrayList();
/*  15 */   private final List<EntityAITaskEntry> executingTaskEntries = Lists.newArrayList();
/*     */   
/*     */   private final Profiler theProfiler;
/*     */   
/*     */   private int tickCount;
/*  20 */   private final int tickRate = 3;
/*     */ 
/*     */   
/*     */   public EntityAITasks(Profiler profilerIn) {
/*  24 */     this.theProfiler = profilerIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addTask(int priority, EntityAIBase task) {
/*  32 */     this.taskEntries.add(new EntityAITaskEntry(priority, task));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeTask(EntityAIBase task) {
/*  40 */     Iterator<EntityAITaskEntry> iterator = this.taskEntries.iterator();
/*     */     
/*  42 */     while (iterator.hasNext()) {
/*     */       
/*  44 */       EntityAITaskEntry entityaitasks$entityaitaskentry = iterator.next();
/*  45 */       EntityAIBase entityaibase = entityaitasks$entityaitaskentry.action;
/*     */       
/*  47 */       if (entityaibase == task) {
/*     */         
/*  49 */         if (this.executingTaskEntries.contains(entityaitasks$entityaitaskentry)) {
/*     */           
/*  51 */           entityaibase.resetTask();
/*  52 */           this.executingTaskEntries.remove(entityaitasks$entityaitaskentry);
/*     */         } 
/*     */         
/*  55 */         iterator.remove();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdateTasks() {
/*  62 */     this.theProfiler.startSection("goalSetup");
/*     */     
/*  64 */     getClass(); if (this.tickCount++ % 3 == 0) {
/*     */       
/*  66 */       Iterator<EntityAITaskEntry> iterator = this.taskEntries.iterator();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  75 */       while (iterator.hasNext()) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  80 */         EntityAITaskEntry entityaitasks$entityaitaskentry = iterator.next();
/*  81 */         boolean flag = this.executingTaskEntries.contains(entityaitasks$entityaitaskentry);
/*     */         
/*  83 */         if (flag)
/*     */         {
/*     */ 
/*     */ 
/*     */           
/*  88 */           if (!canUse(entityaitasks$entityaitaskentry) || !canContinue(entityaitasks$entityaitaskentry)) {
/*     */             
/*  90 */             entityaitasks$entityaitaskentry.action.resetTask();
/*  91 */             this.executingTaskEntries.remove(entityaitasks$entityaitaskentry);
/*     */           } else {
/*     */             continue;
/*     */           } 
/*     */         }
/*  96 */         if (canUse(entityaitasks$entityaitaskentry) && entityaitasks$entityaitaskentry.action.shouldExecute())
/*     */         {
/*  98 */           entityaitasks$entityaitaskentry.action.startExecuting();
/*  99 */           this.executingTaskEntries.add(entityaitasks$entityaitaskentry);
/*     */         }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/* 105 */       Iterator<EntityAITaskEntry> iterator1 = this.executingTaskEntries.iterator();
/*     */       
/* 107 */       while (iterator1.hasNext()) {
/*     */         
/* 109 */         EntityAITaskEntry entityaitasks$entityaitaskentry1 = iterator1.next();
/*     */         
/* 111 */         if (!canContinue(entityaitasks$entityaitaskentry1)) {
/*     */           
/* 113 */           entityaitasks$entityaitaskentry1.action.resetTask();
/* 114 */           iterator1.remove();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 119 */     this.theProfiler.endSection();
/* 120 */     this.theProfiler.startSection("goalTick");
/*     */     
/* 122 */     for (EntityAITaskEntry entityaitasks$entityaitaskentry2 : this.executingTaskEntries)
/*     */     {
/* 124 */       entityaitasks$entityaitaskentry2.action.updateTask();
/*     */     }
/*     */     
/* 127 */     this.theProfiler.endSection();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canContinue(EntityAITaskEntry taskEntry) {
/* 135 */     boolean flag = taskEntry.action.continueExecuting();
/* 136 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canUse(EntityAITaskEntry taskEntry) {
/* 145 */     for (EntityAITaskEntry entityaitasks$entityaitaskentry : this.taskEntries) {
/*     */       
/* 147 */       if (entityaitasks$entityaitaskentry != taskEntry) {
/*     */         
/* 149 */         if (taskEntry.priority >= entityaitasks$entityaitaskentry.priority) {
/*     */           
/* 151 */           if (!areTasksCompatible(taskEntry, entityaitasks$entityaitaskentry) && this.executingTaskEntries.contains(entityaitasks$entityaitaskentry))
/*     */           {
/* 153 */             return false; } 
/*     */           continue;
/*     */         } 
/* 156 */         if (!entityaitasks$entityaitaskentry.action.isInterruptible() && this.executingTaskEntries.contains(entityaitasks$entityaitaskentry))
/*     */         {
/* 158 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 163 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean areTasksCompatible(EntityAITaskEntry taskEntry1, EntityAITaskEntry taskEntry2) {
/* 171 */     return ((taskEntry1.action.getMutexBits() & taskEntry2.action.getMutexBits()) == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   static class EntityAITaskEntry
/*     */   {
/*     */     public EntityAIBase action;
/*     */     public int priority;
/*     */     
/*     */     public EntityAITaskEntry(int priorityIn, EntityAIBase task) {
/* 181 */       this.priority = priorityIn;
/* 182 */       this.action = task;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\ai\EntityAITasks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */