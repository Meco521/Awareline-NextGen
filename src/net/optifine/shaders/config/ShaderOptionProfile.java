/*     */ package net.optifine.shaders.config;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.optifine.Lang;
/*     */ import net.optifine.shaders.ShaderUtils;
/*     */ import net.optifine.shaders.Shaders;
/*     */ 
/*     */ public class ShaderOptionProfile
/*     */   extends ShaderOption
/*     */ {
/*  12 */   private ShaderProfile[] profiles = null;
/*  13 */   private ShaderOption[] options = null;
/*     */   
/*     */   private static final String NAME_PROFILE = "<profile>";
/*     */   private static final String VALUE_CUSTOM = "<custom>";
/*     */   
/*     */   public ShaderOptionProfile(ShaderProfile[] profiles, ShaderOption[] options) {
/*  19 */     super("<profile>", "", detectProfileName(profiles, options), getProfileNames(profiles), detectProfileName(profiles, options, true), (String)null);
/*  20 */     this.profiles = profiles;
/*  21 */     this.options = options;
/*     */   }
/*     */ 
/*     */   
/*     */   public void nextValue() {
/*  26 */     super.nextValue();
/*     */     
/*  28 */     if (getValue().equals("<custom>"))
/*     */     {
/*  30 */       super.nextValue();
/*     */     }
/*     */     
/*  33 */     applyProfileOptions();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateProfile() {
/*  38 */     ShaderProfile shaderprofile = getProfile(getValue());
/*     */     
/*  40 */     if (shaderprofile == null || !ShaderUtils.matchProfile(shaderprofile, this.options, false)) {
/*     */       
/*  42 */       String s = detectProfileName(this.profiles, this.options);
/*  43 */       setValue(s);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void applyProfileOptions() {
/*  49 */     ShaderProfile shaderprofile = getProfile(getValue());
/*     */     
/*  51 */     if (shaderprofile != null) {
/*     */       
/*  53 */       String[] astring = shaderprofile.getOptions();
/*     */       
/*  55 */       for (int i = 0; i < astring.length; i++) {
/*     */         
/*  57 */         String s = astring[i];
/*  58 */         ShaderOption shaderoption = getOption(s);
/*     */         
/*  60 */         if (shaderoption != null) {
/*     */           
/*  62 */           String s1 = shaderprofile.getValue(s);
/*  63 */           shaderoption.setValue(s1);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ShaderOption getOption(String name) {
/*  71 */     for (int i = 0; i < this.options.length; i++) {
/*     */       
/*  73 */       ShaderOption shaderoption = this.options[i];
/*     */       
/*  75 */       if (shaderoption.getName().equals(name))
/*     */       {
/*  77 */         return shaderoption;
/*     */       }
/*     */     } 
/*     */     
/*  81 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private ShaderProfile getProfile(String name) {
/*  86 */     for (int i = 0; i < this.profiles.length; i++) {
/*     */       
/*  88 */       ShaderProfile shaderprofile = this.profiles[i];
/*     */       
/*  90 */       if (shaderprofile.getName().equals(name))
/*     */       {
/*  92 */         return shaderprofile;
/*     */       }
/*     */     } 
/*     */     
/*  96 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNameText() {
/* 101 */     return Lang.get("of.shaders.profile");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValueText(String val) {
/* 106 */     return val.equals("<custom>") ? Lang.get("of.general.custom", "<custom>") : Shaders.translate("profile." + val, val);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValueColor(String val) {
/* 111 */     return val.equals("<custom>") ? "§c" : "§a";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescriptionText() {
/* 116 */     String s = Shaders.translate("profile.comment", (String)null);
/*     */     
/* 118 */     if (s != null)
/*     */     {
/* 120 */       return s;
/*     */     }
/*     */ 
/*     */     
/* 124 */     StringBuffer stringbuffer = new StringBuffer();
/*     */     
/* 126 */     for (int i = 0; i < this.profiles.length; i++) {
/*     */       
/* 128 */       String s1 = this.profiles[i].getName();
/*     */       
/* 130 */       if (s1 != null) {
/*     */         
/* 132 */         String s2 = Shaders.translate("profile." + s1 + ".comment", (String)null);
/*     */         
/* 134 */         if (s2 != null) {
/*     */           
/* 136 */           stringbuffer.append(s2);
/*     */           
/* 138 */           if (!s2.endsWith(". "))
/*     */           {
/* 140 */             stringbuffer.append(". ");
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 146 */     return stringbuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String detectProfileName(ShaderProfile[] profs, ShaderOption[] opts) {
/* 152 */     return detectProfileName(profs, opts, false);
/*     */   }
/*     */ 
/*     */   
/*     */   private static String detectProfileName(ShaderProfile[] profs, ShaderOption[] opts, boolean def) {
/* 157 */     ShaderProfile shaderprofile = ShaderUtils.detectProfile(profs, opts, def);
/* 158 */     return (shaderprofile == null) ? "<custom>" : shaderprofile.getName();
/*     */   }
/*     */ 
/*     */   
/*     */   private static String[] getProfileNames(ShaderProfile[] profs) {
/* 163 */     List<String> list = new ArrayList<>();
/*     */     
/* 165 */     for (int i = 0; i < profs.length; i++) {
/*     */       
/* 167 */       ShaderProfile shaderprofile = profs[i];
/* 168 */       list.add(shaderprofile.getName());
/*     */     } 
/*     */     
/* 171 */     list.add("<custom>");
/* 172 */     String[] astring = list.<String>toArray(new String[0]);
/* 173 */     return astring;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\config\ShaderOptionProfile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */