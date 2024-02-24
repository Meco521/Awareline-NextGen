/*     */ package net.optifine.shaders;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class Programs
/*     */ {
/*   8 */   private final List<Program> programs = new ArrayList<>();
/*   9 */   private final Program programNone = make("", ProgramStage.NONE, true);
/*     */ 
/*     */   
/*     */   public Program make(String name, ProgramStage programStage, Program backupProgram) {
/*  13 */     int i = this.programs.size();
/*  14 */     Program program = new Program(i, name, programStage, backupProgram);
/*  15 */     this.programs.add(program);
/*  16 */     return program;
/*     */   }
/*     */ 
/*     */   
/*     */   private Program make(String name, ProgramStage programStage, boolean ownBackup) {
/*  21 */     int i = this.programs.size();
/*  22 */     Program program = new Program(i, name, programStage, ownBackup);
/*  23 */     this.programs.add(program);
/*  24 */     return program;
/*     */   }
/*     */ 
/*     */   
/*     */   public Program makeGbuffers(String name, Program backupProgram) {
/*  29 */     return make(name, ProgramStage.GBUFFERS, backupProgram);
/*     */   }
/*     */ 
/*     */   
/*     */   public Program makeComposite(String name) {
/*  34 */     return make(name, ProgramStage.COMPOSITE, this.programNone);
/*     */   }
/*     */ 
/*     */   
/*     */   public Program makeDeferred(String name) {
/*  39 */     return make(name, ProgramStage.DEFERRED, this.programNone);
/*     */   }
/*     */ 
/*     */   
/*     */   public Program makeShadow(String name, Program backupProgram) {
/*  44 */     return make(name, ProgramStage.SHADOW, backupProgram);
/*     */   }
/*     */ 
/*     */   
/*     */   public Program makeVirtual(String name) {
/*  49 */     return make(name, ProgramStage.NONE, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public Program[] makeComposites(String prefix, int count) {
/*  54 */     Program[] aprogram = new Program[count];
/*     */     
/*  56 */     for (int i = 0; i < count; i++) {
/*     */       
/*  58 */       String s = (i == 0) ? prefix : (prefix + i);
/*  59 */       aprogram[i] = makeComposite(s);
/*     */     } 
/*     */     
/*  62 */     return aprogram;
/*     */   }
/*     */ 
/*     */   
/*     */   public Program[] makeDeferreds(String prefix, int count) {
/*  67 */     Program[] aprogram = new Program[count];
/*     */     
/*  69 */     for (int i = 0; i < count; i++) {
/*     */       
/*  71 */       String s = (i == 0) ? prefix : (prefix + i);
/*  72 */       aprogram[i] = makeDeferred(s);
/*     */     } 
/*     */     
/*  75 */     return aprogram;
/*     */   }
/*     */ 
/*     */   
/*     */   public Program getProgramNone() {
/*  80 */     return this.programNone;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCount() {
/*  85 */     return this.programs.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public Program getProgram(String name) {
/*  90 */     if (name == null)
/*     */     {
/*  92 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  96 */     for (int i = 0; i < this.programs.size(); i++) {
/*     */       
/*  98 */       Program program = this.programs.get(i);
/*  99 */       String s = program.getName();
/*     */       
/* 101 */       if (s.equals(name))
/*     */       {
/* 103 */         return program;
/*     */       }
/*     */     } 
/*     */     
/* 107 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getProgramNames() {
/* 113 */     String[] astring = new String[this.programs.size()];
/*     */     
/* 115 */     for (int i = 0; i < astring.length; i++)
/*     */     {
/* 117 */       astring[i] = ((Program)this.programs.get(i)).getName();
/*     */     }
/*     */     
/* 120 */     return astring;
/*     */   }
/*     */ 
/*     */   
/*     */   public Program[] getPrograms() {
/* 125 */     Program[] aprogram = this.programs.<Program>toArray(new Program[0]);
/* 126 */     return aprogram;
/*     */   }
/*     */ 
/*     */   
/*     */   public Program[] getPrograms(Program programFrom, Program programTo) {
/* 131 */     int i = programFrom.getIndex();
/* 132 */     int j = programTo.getIndex();
/*     */     
/* 134 */     if (i > j) {
/*     */       
/* 136 */       int k = i;
/* 137 */       i = j;
/* 138 */       j = k;
/*     */     } 
/*     */     
/* 141 */     Program[] aprogram = new Program[j - i + 1];
/*     */     
/* 143 */     for (int l = 0; l < aprogram.length; l++)
/*     */     {
/* 145 */       aprogram[l] = this.programs.get(i + l);
/*     */     }
/*     */     
/* 148 */     return aprogram;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 153 */     return this.programs.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\Programs.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */