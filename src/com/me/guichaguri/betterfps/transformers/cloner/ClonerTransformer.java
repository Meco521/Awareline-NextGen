/*     */ package com.me.guichaguri.betterfps.transformers.cloner;
/*     */ import com.me.guichaguri.betterfps.ASMUtils;
/*     */ import com.me.guichaguri.betterfps.BetterFpsConfig;
/*     */ import com.me.guichaguri.betterfps.BetterFpsHelper;
/*     */ import com.me.guichaguri.betterfps.tweaker.Naming;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.jar.JarFile;
/*     */ import java.util.zip.ZipEntry;
/*     */ import jdk.internal.org.objectweb.asm.ClassReader;
/*     */ import jdk.internal.org.objectweb.asm.ClassWriter;
/*     */ import jdk.internal.org.objectweb.asm.Type;
/*     */ import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.AnnotationNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.ClassNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.FieldInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.FieldNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.LocalVariableNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.MethodInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.MethodNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.TypeInsnNode;
/*     */ 
/*     */ public class ClonerTransformer implements IClassTransformer {
/*  25 */   private static final List<Clone> clones = new ArrayList<>();
/*     */   
/*     */   public static void add(String clazz, Naming target) {
/*  28 */     clones.add(new Clone(clazz.replaceAll("\\.", "/"), target));
/*     */   }
/*     */   
/*     */   static {
/*  32 */     BetterFpsConfig config = BetterFpsConfig.getConfig();
/*     */     
/*  34 */     if (config.fastBeacon) {
/*  35 */       add("com.com.me.guichaguri.betterfps.clones.tileentity.BeaconLogic", Naming.C_TileEntityBeacon);
/*     */     }
/*     */     
/*  38 */     if (config.fastHopper) {
/*  39 */       add("com.com.me.guichaguri.betterfps.clones.tileentity.HopperLogic", Naming.C_TileEntityHopper);
/*  40 */       add("com.com.me.guichaguri.betterfps.clones.block.HopperBlock", Naming.C_BlockHopper);
/*     */     } 
/*     */     
/*  43 */     add("com.com.me.guichaguri.betterfps.clones.client.ModelBoxLogic", Naming.C_ModelBox);
/*  44 */     add("com.com.me.guichaguri.betterfps.clones.client.EntityRenderLogic", Naming.C_EntityRenderer);
/*  45 */     add("com.com.me.guichaguri.betterfps.clones.client.GuiOptionsLogic", Naming.C_GuiOptions);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] transform(String name, String transformedName, byte[] bytes) {
/*  50 */     if (bytes == null) return bytes;
/*     */     
/*  52 */     List<Clone> foundClones = null;
/*  53 */     for (Clone c : clones) {
/*  54 */       if (c.target.is(name)) {
/*  55 */         if (foundClones == null) foundClones = new ArrayList<>(); 
/*  56 */         foundClones.add(c);
/*     */       } 
/*     */     } 
/*     */     
/*  60 */     if (foundClones != null) {
/*  61 */       BetterFps.log.info("Found " + foundClones.size() + " class patches for " + name);
/*  62 */       return patchClones(foundClones, bytes);
/*     */     } 
/*     */     
/*  65 */     return bytes;
/*     */   }
/*     */   
/*     */   public byte[] patchClones(List<Clone> clones, byte[] bytes) {
/*  69 */     ClassNode classNode = new ClassNode();
/*  70 */     ClassReader classReader = new ClassReader(bytes);
/*  71 */     classReader.accept(classNode, 0);
/*     */     
/*  73 */     boolean patched = false;
/*     */ 
/*     */     
/*  76 */     for (Clone c : clones) {
/*     */       try {
/*     */         ClassReader reader;
/*  79 */         if (BetterFpsHelper.LOC == null) {
/*  80 */           reader = new ClassReader(c.clonePath);
/*     */         } else {
/*  82 */           JarFile jar = new JarFile(BetterFpsHelper.LOC);
/*  83 */           ZipEntry e = jar.getEntry(c.clonePath + ".class");
/*  84 */           reader = new ClassReader(jar.getInputStream(e));
/*  85 */           jar.close();
/*     */         } 
/*     */         
/*  88 */         ClassNode cloneClass = new ClassNode();
/*  89 */         reader.accept(cloneClass, 0);
/*     */         
/*  91 */         if (cloneClass.visibleAnnotations != null) {
/*  92 */           boolean canCopy = canCopy(cloneClass.visibleAnnotations);
/*  93 */           if (!canCopy) {
/*     */             continue;
/*     */           }
/*     */         } 
/*  97 */         for (FieldNode field : cloneClass.fields) {
/*  98 */           CopyMode.Mode mode = CopyMode.Mode.REPLACE;
/*  99 */           Naming name = null;
/* 100 */           if (field.visibleAnnotations != null) {
/* 101 */             boolean canCopy = canCopy(field.visibleAnnotations);
/* 102 */             if (!canCopy)
/* 103 */               continue;  mode = getCopyMode(field.visibleAnnotations);
/* 104 */             name = getNaming(field.visibleAnnotations);
/*     */           } 
/* 106 */           cloneField(field, classNode, mode, name);
/* 107 */           patched = true;
/*     */         } 
/*     */ 
/*     */         
/* 111 */         for (MethodNode method : cloneClass.methods) {
/* 112 */           CopyMode.Mode mode = CopyMode.Mode.REPLACE;
/* 113 */           Naming name = null;
/* 114 */           if (method.visibleAnnotations != null) {
/* 115 */             boolean canCopy = canCopy(method.visibleAnnotations);
/* 116 */             if (!canCopy)
/* 117 */               continue;  mode = getCopyMode(method.visibleAnnotations);
/* 118 */             name = getNaming(method.visibleAnnotations);
/*     */           } 
/* 120 */           cloneMethod(method, classNode, cloneClass, mode, name);
/* 121 */           patched = true;
/*     */         }
/*     */       
/* 124 */       } catch (Exception ex) {
/* 125 */         BetterFps.log.error("Could not patch with " + c.clonePath + ": " + ex);
/* 126 */         ex.printStackTrace();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 131 */     if (!patched) return bytes;
/*     */     
/* 133 */     ClassWriter writer = new ClassWriter(3);
/* 134 */     classNode.accept(writer);
/* 135 */     return writer.toByteArray();
/*     */   }
/*     */   
/*     */   private CopyMode.Mode getCopyMode(List<AnnotationNode> annotations) {
/* 139 */     for (AnnotationNode node : annotations) {
/* 140 */       if (node.desc.equals(Type.getDescriptor(CopyMode.class))) {
/* 141 */         CopyMode.Mode n = (CopyMode.Mode)ASMUtils.getAnnotationValue(node, "value", CopyMode.Mode.class);
/* 142 */         if (n != null) return n; 
/*     */       } 
/*     */     } 
/* 145 */     return CopyMode.Mode.REPLACE;
/*     */   }
/*     */   
/*     */   private Naming getNaming(List<AnnotationNode> annotations) {
/* 149 */     for (AnnotationNode node : annotations) {
/* 150 */       if (node.desc.equals(Type.getDescriptor(Named.class))) {
/* 151 */         Naming n = (Naming)ASMUtils.getAnnotationValue(node, "value", Naming.class);
/* 152 */         if (n != null) return n; 
/*     */       } 
/*     */     } 
/* 155 */     return null;
/*     */   }
/*     */   
/*     */   private boolean canCopy(List<AnnotationNode> annotations) {
/* 159 */     boolean canCopy = false;
/* 160 */     int conditions = 0;
/* 161 */     for (AnnotationNode node : annotations) {
/* 162 */       if (node.desc.equals(Type.getDescriptor(CopyCondition.class))) {
/* 163 */         String key = ASMUtils.getAnnotationValue(node, "key");
/* 164 */         String value = ASMUtils.getAnnotationValue(node, "value");
/* 165 */         canCopy = (canCopy || BetterFpsConfig.getValue(key).equals(value));
/* 166 */         conditions++; continue;
/* 167 */       }  if (node.desc.equals(Type.getDescriptor(CopyBoolCondition.class))) {
/* 168 */         String key = ASMUtils.getAnnotationValue(node, "key");
/* 169 */         Boolean value = (Boolean)ASMUtils.getAnnotationValue(node, "value", Boolean.class);
/* 170 */         if (value == null) value = Boolean.valueOf(true); 
/* 171 */         canCopy = (canCopy || ((Boolean)BetterFpsConfig.getValue(key)).booleanValue() == value.booleanValue());
/* 172 */         conditions++;
/*     */       } 
/*     */     } 
/* 175 */     return (conditions > 0) ? canCopy : true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void cloneField(FieldNode e, ClassNode node, CopyMode.Mode mode, Naming name) {
/* 180 */     if (mode == CopyMode.Mode.IGNORE)
/* 181 */       return;  for (int i = 0; i < node.fields.size(); i++) {
/* 182 */       FieldNode field = node.fields.get(i);
/* 183 */       boolean b = false;
/* 184 */       if (name != null && name.is(field.name, field.desc)) {
/* 185 */         b = true;
/* 186 */         e.name = field.name;
/* 187 */         e.desc = field.desc;
/* 188 */       } else if (field.name.equals(e.name) && field.desc.equals(e.desc)) {
/* 189 */         b = true;
/*     */       } 
/* 191 */       if (b) {
/* 192 */         if (mode == CopyMode.Mode.ADD_IF_NOT_EXISTS)
/* 193 */           return;  node.fields.remove(field);
/*     */         break;
/*     */       } 
/*     */     } 
/* 197 */     node.fields.add(e);
/*     */   }
/*     */   
/*     */   private boolean cloneMethod(MethodNode e, ClassNode node, ClassNode original, CopyMode.Mode mode, Naming name) {
/* 201 */     if (mode == CopyMode.Mode.IGNORE) return false; 
/* 202 */     MethodNode originalMethod = null;
/* 203 */     for (int i = 0; i < node.methods.size(); i++) {
/* 204 */       MethodNode method = node.methods.get(i);
/* 205 */       boolean b = false;
/* 206 */       if (name != null && name.is(method.name) && method.desc.equals(e.desc)) {
/* 207 */         b = true;
/* 208 */         e.name = method.name;
/* 209 */         e.desc = method.desc;
/* 210 */       } else if (method.name.equals(e.name) && method.desc.equals(e.desc)) {
/* 211 */         b = true;
/*     */       } 
/* 213 */       if (b) {
/* 214 */         if (mode == CopyMode.Mode.ADD_IF_NOT_EXISTS) return false; 
/* 215 */         if (mode == CopyMode.Mode.PREPEND) {
/* 216 */           replaceOcurrences(e, node, original, null);
/* 217 */           method.instructions = ASMUtils.prependNodeList(method.instructions, e.instructions);
/* 218 */           return true;
/*     */         } 
/* 220 */         if (mode == CopyMode.Mode.APPEND) {
/* 221 */           replaceOcurrences(e, node, original, null);
/* 222 */           method.instructions = ASMUtils.appendNodeList(method.instructions, e.instructions);
/* 223 */           return true;
/*     */         } 
/* 225 */         originalMethod = method;
/* 226 */         node.methods.remove(method);
/*     */         break;
/*     */       } 
/*     */     } 
/* 230 */     replaceOcurrences(e, node, original, originalMethod);
/* 231 */     node.methods.add(e);
/* 232 */     return true;
/*     */   }
/*     */   
/*     */   private void replaceOcurrences(MethodNode method, ClassNode classNode, ClassNode original, MethodNode originalMethod) {
/* 236 */     String originalDesc = "L" + original.name + ";";
/* 237 */     String classDesc = "L" + classNode.name + ";";
/* 238 */     Iterator<AbstractInsnNode> nodes = method.instructions.iterator();
/* 239 */     TypeInsnNode lastType = null;
/* 240 */     boolean hasSuper = false;
/* 241 */     String superName = (originalMethod == null) ? null : originalMethod.name;
/*     */     
/* 243 */     while (nodes.hasNext()) {
/* 244 */       AbstractInsnNode node = nodes.next();
/*     */       
/* 246 */       if (node instanceof FieldInsnNode) {
/* 247 */         FieldInsnNode f = (FieldInsnNode)node;
/* 248 */         if (f.owner.equals(original.name)) {
/* 249 */           f.owner = classNode.name; continue;
/*     */         } 
/* 251 */         for (Clone c : clones) {
/* 252 */           if (f.owner.equals(c.clonePath)) {
/* 253 */             f.owner = lastType.desc;
/*     */           }
/*     */         } 
/*     */         continue;
/*     */       } 
/* 258 */       if (node instanceof MethodInsnNode) {
/* 259 */         MethodInsnNode m = (MethodInsnNode)node;
/*     */         
/* 261 */         if (originalMethod != null && m.getOpcode() == 183 && m.owner.equals(classNode.name) && m.name
/* 262 */           .equals(superName) && m.desc.equals(originalMethod.desc)) {
/* 263 */           if (!hasSuper) {
/* 264 */             originalMethod.name += "_BF_repl";
/* 265 */             classNode.methods.add(originalMethod);
/* 266 */             hasSuper = true;
/*     */           } 
/* 268 */           m.setOpcode(182);
/* 269 */           m.name = originalMethod.name;
/*     */         } 
/*     */         
/* 272 */         if (m.owner.equals(original.name)) {
/* 273 */           m.owner = classNode.name; continue;
/*     */         } 
/* 275 */         for (Clone c : clones) {
/* 276 */           if (m.owner.equals(c.clonePath)) {
/* 277 */             m.owner = lastType.desc;
/*     */           }
/*     */         } 
/*     */         continue;
/*     */       } 
/* 282 */       if (node instanceof TypeInsnNode) {
/* 283 */         TypeInsnNode t = (TypeInsnNode)node;
/* 284 */         if (t.desc.equals(original.name)) {
/* 285 */           t.desc = classNode.name; continue;
/*     */         } 
/* 287 */         for (Clone c : clones) {
/* 288 */           if (t.desc.equals(c.clonePath)) {
/* 289 */             nodes.remove();
/*     */           }
/*     */         } 
/*     */         
/* 293 */         lastType = t;
/*     */       } 
/*     */     } 
/*     */     
/* 297 */     for (LocalVariableNode var : method.localVariables) {
/* 298 */       if (var.desc == originalDesc)
/* 299 */         var.desc = classDesc; 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static class Clone
/*     */   {
/*     */     public final String clonePath;
/*     */     public final Naming target;
/*     */     
/*     */     public Clone(String clonePath, Naming target) {
/* 309 */       this.clonePath = clonePath;
/* 310 */       this.target = target;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\guichaguri\betterfps\transformers\cloner\ClonerTransformer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */