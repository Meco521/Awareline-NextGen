/*     */ package awareline.main.mod.manager;
/*     */ 
/*     */ import awareline.antileak.VerifyData;
/*     */ import awareline.main.Client;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.implement.visual.ctype.ClickGui;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.utility.chat.Helper;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConfigManager
/*     */ {
/*     */   public void saveConfig(String dirs) {
/*  22 */     Helper.sendMessage("Trying to save the configuration:" + dirs);
/*  23 */     File dir = new File(FileManager.dir, dirs);
/*  24 */     if (!dir.exists()) {
/*  25 */       dir.mkdir();
/*     */     }
/*  27 */     StringBuilder values = new StringBuilder();
/*  28 */     for (Module m : Client.instance.getModuleManager().getModules()) {
/*  29 */       for (Value v : m.getValues()) {
/*  30 */         values.append(String.format("%s:%s:%s%s", new Object[] { m.getName(), v.getName(), v.getValue(), System.lineSeparator() }));
/*     */       } 
/*     */     } 
/*  33 */     FileManager.save(dir, "Values.txt", values.toString(), false);
/*  34 */     String content = "";
/*     */     
/*  36 */     for (Iterator<Module> var4 = Client.instance.getModuleManager().getModules().iterator(); var4.hasNext(); content = content + String.format("%s:%s%s", new Object[] { m
/*  37 */           .getName(), Keyboard.getKeyName(m.getKey()), System.lineSeparator() })) {
/*  38 */       Module m = var4.next();
/*     */     }
/*     */     
/*  41 */     FileManager.save(dir, "Binds.txt", content, false);
/*  42 */     StringBuilder enabled = new StringBuilder();
/*  43 */     for (Module mod : Client.instance.getModuleManager().getModules()) {
/*  44 */       if (mod.isEnabled())
/*  45 */         enabled.append(String.format("%s%s", new Object[] { mod.getName(), System.lineSeparator() })); 
/*     */     } 
/*  47 */     FileManager.save(dir, "Enabled.txt", enabled.toString(), false);
/*     */     
/*  49 */     String ConfigInfo = String.format("%s:%s%s", new Object[] { dirs, VerifyData.instance.UserName, System.lineSeparator() });
/*  50 */     FileManager.save(dir, "Config.info", ConfigInfo, false);
/*  51 */     Helper.sendMessage("Save the configuration:" + dirs + " Successfully");
/*     */   }
/*     */ 
/*     */   
/*     */   public void LoadConfig(String dirs) {
/*  56 */     File dir = new File(FileManager.dir, dirs);
/*  57 */     if (!dir.exists()) {
/*  58 */       Helper.sendMessage("The configuration you loaded does not exist!");
/*     */       return;
/*     */     } 
/*     */     try {
/*  62 */       File info = new File(dir, "Config.info");
/*  63 */       BufferedReader bufferedReader = new BufferedReader(new FileReader(info));
/*  64 */       String s = bufferedReader.readLine();
/*  65 */       String[] s2 = s.split(":");
/*  66 */       Helper.sendMessage("Configuration:" + s2[0] + " Author of the configuration:" + s2[1]);
/*  67 */     } catch (Exception e) {
/*  68 */       e.printStackTrace();
/*     */     } 
/*  70 */     List<String> binds = FileManager.read(dir, "Binds.txt");
/*  71 */     assert binds != null;
/*  72 */     for (String v : binds) {
/*  73 */       String name = v.split(":")[0];
/*  74 */       String bind = v.split(":")[1];
/*  75 */       Module m = Client.instance.getModuleManager().getModuleByName(name);
/*  76 */       if (m == null)
/*     */         continue; 
/*  78 */       m.setKey(Keyboard.getKeyIndex(bind.toUpperCase()));
/*     */     } 
/*  80 */     if (Client.instance.getModuleManager().getModuleByClass((Class)ClickGui.class).getKey() == 0)
/*  81 */       Client.instance.getModuleManager().getModuleByClass((Class)ClickGui.class).setKey(Keyboard.getKeyIndex("RSHIFT")); 
/*  82 */     List<String> enabled = FileManager.read(dir, "Enabled.txt");
/*  83 */     for (Module m : Client.instance.getModuleManager().getModules()) {
/*  84 */       if (m.isEnabled()) {
/*  85 */         m.setEnabled(false);
/*     */       }
/*     */     } 
/*  88 */     assert enabled != null;
/*  89 */     for (String v : enabled) {
/*  90 */       Module m = Client.instance.getModuleManager().getModuleByName(v);
/*  91 */       if (m == null)
/*     */         continue; 
/*  93 */       if (!m.isEnabled()) {
/*  94 */         m.setEnabled(true);
/*     */       }
/*     */     } 
/*  97 */     List<String> vals = FileManager.read(dir, "Values.txt");
/*  98 */     assert vals != null;
/*  99 */     for (String v : vals) {
/* 100 */       String name = v.split(":")[0];
/* 101 */       String values = v.split(":")[1];
/* 102 */       Module m = Client.instance.getModuleManager().getModuleByName(name);
/* 103 */       if (m == null) {
/*     */         continue;
/*     */       }
/* 106 */       for (Value value : m.getValues()) {
/* 107 */         if (!value.getName().equalsIgnoreCase(values))
/*     */           continue; 
/* 109 */         if (value instanceof awareline.main.mod.values.Option) {
/* 110 */           value.setValue(Boolean.valueOf(Boolean.parseBoolean(v.split(":")[2])));
/*     */           continue;
/*     */         } 
/* 113 */         if (value instanceof awareline.main.mod.values.Numbers) {
/* 114 */           value.setValue(Double.valueOf(Double.parseDouble(v.split(":")[2])));
/*     */           
/*     */           continue;
/*     */         } 
/* 118 */         ((Mode)value).setMode(v.split(":")[2]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void RemoveConfig(String dirs) {
/* 124 */     File dir = new File(FileManager.dir, dirs);
/* 125 */     if (!dir.exists()) {
/* 126 */       Helper.sendMessage("The configuration you are trying to delete does not exist!");
/*     */     } else {
/* 128 */       deleteFile(dir);
/* 129 */       Helper.sendMessage("Deleted configuration" + dirs + "Successfully!");
/*     */     } 
/*     */   }
/*     */   
/*     */   public void deleteFile(File file) {
/* 134 */     if (file.exists()) {
/* 135 */       if (file.isFile()) {
/* 136 */         file.delete();
/*     */       } else {
/* 138 */         File[] listFiles = file.listFiles();
/* 139 */         assert listFiles != null;
/* 140 */         for (File file2 : listFiles) {
/* 141 */           deleteFile(file2);
/*     */         }
/*     */       } 
/* 144 */       file.delete();
/*     */     } else {
/* 146 */       System.err.println("Path does not exist?");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\manager\ConfigManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */