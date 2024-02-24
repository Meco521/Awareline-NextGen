/*    */ package net.optifine.shaders.config;
/*    */ import java.util.Collection;
/*    */ import java.util.LinkedHashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class ShaderProfile {
/*  7 */   private String name = null;
/*  8 */   private final Map<String, String> mapOptionValues = new LinkedHashMap<>();
/*  9 */   private final Set<String> disabledPrograms = new LinkedHashSet<>();
/*    */ 
/*    */   
/*    */   public ShaderProfile(String name) {
/* 13 */     this.name = name;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 18 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public void addOptionValue(String option, String value) {
/* 23 */     this.mapOptionValues.put(option, value);
/*    */   }
/*    */ 
/*    */   
/*    */   public void addOptionValues(ShaderProfile prof) {
/* 28 */     if (prof != null)
/*    */     {
/* 30 */       this.mapOptionValues.putAll(prof.mapOptionValues);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void applyOptionValues(ShaderOption[] options) {
/* 36 */     for (int i = 0; i < options.length; i++) {
/*    */       
/* 38 */       ShaderOption shaderoption = options[i];
/* 39 */       String s = shaderoption.getName();
/* 40 */       String s1 = this.mapOptionValues.get(s);
/*    */       
/* 42 */       if (s1 != null)
/*    */       {
/* 44 */         shaderoption.setValue(s1);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getOptions() {
/* 51 */     Set<String> set = this.mapOptionValues.keySet();
/* 52 */     String[] astring = set.<String>toArray(new String[0]);
/* 53 */     return astring;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getValue(String key) {
/* 58 */     return this.mapOptionValues.get(key);
/*    */   }
/*    */ 
/*    */   
/*    */   public void addDisabledProgram(String program) {
/* 63 */     this.disabledPrograms.add(program);
/*    */   }
/*    */ 
/*    */   
/*    */   public void removeDisabledProgram(String program) {
/* 68 */     this.disabledPrograms.remove(program);
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<String> getDisabledPrograms() {
/* 73 */     return new LinkedHashSet<>(this.disabledPrograms);
/*    */   }
/*    */ 
/*    */   
/*    */   public void addDisabledPrograms(Collection<String> programs) {
/* 78 */     this.disabledPrograms.addAll(programs);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isProgramDisabled(String program) {
/* 83 */     return this.disabledPrograms.contains(program);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\config\ShaderProfile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */