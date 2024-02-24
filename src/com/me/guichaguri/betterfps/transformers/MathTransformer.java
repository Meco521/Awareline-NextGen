/*     */ package com.me.guichaguri.betterfps.transformers;
/*     */ 
/*     */ import com.me.guichaguri.betterfps.BetterFps;
/*     */ import com.me.guichaguri.betterfps.BetterFpsConfig;
/*     */ import com.me.guichaguri.betterfps.BetterFpsHelper;
/*     */ import com.me.guichaguri.betterfps.IClassTransformer;
/*     */ import com.me.guichaguri.betterfps.tweaker.Naming;
/*     */ import java.util.Iterator;
/*     */ import java.util.jar.JarFile;
/*     */ import java.util.zip.ZipEntry;
/*     */ import jdk.internal.org.objectweb.asm.ClassReader;
/*     */ import jdk.internal.org.objectweb.asm.ClassWriter;
/*     */ import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.ClassNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.FieldInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.FieldNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.InsnList;
/*     */ import jdk.internal.org.objectweb.asm.tree.MethodNode;
/*     */ 
/*     */ public class MathTransformer
/*     */   implements IClassTransformer
/*     */ {
/*     */   public byte[] transform(String name, String name2, byte[] bytes) {
/*  24 */     if (bytes == null) return new byte[0];
/*     */     
/*  26 */     if (Naming.C_MathHelper.is(name)) {
/*     */       try {
/*  28 */         return patchMath(bytes);
/*  29 */       } catch (Exception ex) {
/*  30 */         ex.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/*  34 */     return bytes;
/*     */   }
/*     */   
/*     */   private byte[] patchMath(byte[] bytes) throws Exception {
/*     */     ClassReader reader;
/*  39 */     BetterFpsConfig config = BetterFpsConfig.getConfig();
/*  40 */     if (config == null) {
/*  41 */       BetterFpsHelper.loadConfig();
/*  42 */       config = BetterFpsConfig.getConfig();
/*     */     } 
/*     */     
/*  45 */     String algorithmClass = (String)BetterFpsHelper.helpers.get(config.algorithm);
/*  46 */     if (algorithmClass == null) {
/*  47 */       BetterFps.log.error("The algorithm is invalid. We're going to use Vanilla Algorithm instead.");
/*  48 */       config.algorithm = "vanilla";
/*     */     } 
/*     */     
/*  51 */     if (config.algorithm.equals("vanilla")) {
/*  52 */       BetterFps.log.info("Letting Minecraft use " + (String)BetterFpsHelper.displayHelpers.get(config.algorithm));
/*  53 */       return bytes;
/*     */     } 
/*  55 */     BetterFps.log.info("Patching Minecraft using " + (String)BetterFpsHelper.displayHelpers.get(config.algorithm));
/*     */ 
/*     */ 
/*     */     
/*  59 */     if (BetterFpsHelper.LOC == null) {
/*  60 */       reader = new ClassReader("com.com.me.guichaguri.betterfps.math." + algorithmClass);
/*     */     } else {
/*  62 */       JarFile jar = new JarFile(BetterFpsHelper.LOC);
/*  63 */       ZipEntry e = jar.getEntry("me/guichaguri/betterfps/math/" + algorithmClass + ".class");
/*  64 */       reader = new ClassReader(jar.getInputStream(e));
/*  65 */       jar.close();
/*     */     } 
/*     */     
/*  68 */     ClassNode mathnode = new ClassNode();
/*  69 */     reader.accept(mathnode, 0);
/*     */     
/*  71 */     ClassNode classNode = new ClassNode();
/*  72 */     ClassReader classReader = new ClassReader(bytes);
/*  73 */     classReader.accept(classNode, 0);
/*     */     
/*  75 */     String className = classNode.name;
/*  76 */     String mathClass = mathnode.name;
/*     */     
/*  78 */     patchInit(classNode, mathnode, className, mathClass);
/*     */     
/*  80 */     Iterator<MethodNode> methods = classNode.methods.iterator();
/*  81 */     boolean patched = false;
/*     */     
/*  83 */     while (methods.hasNext()) {
/*  84 */       MethodNode method = methods.next();
/*     */       
/*  86 */       if (Naming.M_sin.is(method.name, method.desc)) {
/*     */         
/*  88 */         patchSin(method, mathnode, className, mathClass);
/*  89 */         patched = true; continue;
/*  90 */       }  if (Naming.M_cos.is(method.name, method.desc)) {
/*     */         
/*  92 */         patchCos(method, mathnode, className, mathClass);
/*  93 */         patched = true;
/*     */       } 
/*     */     } 
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
/* 111 */     if (patched) {
/*     */       
/* 113 */       Iterator<FieldNode> fields = classNode.fields.iterator();
/* 114 */       while (fields.hasNext()) {
/* 115 */         FieldNode field = fields.next();
/* 116 */         if (Naming.F_SIN_TABLE.is(field.name, field.desc)) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 123 */       ClassWriter writer = new ClassWriter(3);
/* 124 */       classNode.accept(writer);
/* 125 */       return writer.toByteArray();
/*     */     } 
/*     */ 
/*     */     
/* 129 */     return bytes;
/*     */   }
/*     */ 
/*     */   
/*     */   private void patchInit(ClassNode classNode, ClassNode math, String name, String oldName) {
/* 134 */     classNode.fields.addAll(math.fields);
/*     */ 
/*     */     
/* 137 */     MethodNode mathClinit = null;
/* 138 */     for (MethodNode m : math.methods) {
/* 139 */       if (m.name.equals("<clinit>")) {
/* 140 */         mathClinit = m;
/*     */         break;
/*     */       } 
/*     */     } 
/* 144 */     if (mathClinit != null) {
/* 145 */       MethodNode clinit = null;
/* 146 */       for (MethodNode m : classNode.methods) {
/* 147 */         if (m.name.equals("<clinit>")) {
/* 148 */           clinit = m;
/*     */           break;
/*     */         } 
/*     */       } 
/* 152 */       if (clinit == null) {
/* 153 */         clinit = new MethodNode(8, "<clinit>", "()V", null, null);
/*     */       }
/* 155 */       InsnList list = new InsnList();
/* 156 */       for (AbstractInsnNode node : mathClinit.instructions.toArray()) {
/* 157 */         if (node instanceof FieldInsnNode) {
/* 158 */           FieldInsnNode field = (FieldInsnNode)node;
/* 159 */           if (field.owner.equals(oldName)) field.owner = name; 
/* 160 */         } else if (node.getOpcode() == 177) {
/*     */           continue;
/*     */         } 
/* 163 */         list.add(node); continue;
/*     */       } 
/* 165 */       list.add(clinit.instructions);
/* 166 */       clinit.instructions.clear();
/* 167 */       clinit.instructions.add(list);
/*     */       
/* 169 */       classNode.methods.remove(clinit);
/* 170 */       classNode.methods.add(clinit);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void patchSin(MethodNode method, ClassNode math, String name, String oldName) {
/* 175 */     method.instructions.clear();
/*     */     
/* 177 */     for (MethodNode original : math.methods) {
/* 178 */       if (original.name.equals("sin")) {
/* 179 */         method.instructions.add(original.instructions);
/* 180 */         for (AbstractInsnNode node : method.instructions.toArray()) {
/* 181 */           if (node instanceof FieldInsnNode) {
/* 182 */             FieldInsnNode field = (FieldInsnNode)node;
/* 183 */             if (field.owner.equals(oldName)) field.owner = name; 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void patchCos(MethodNode method, ClassNode math, String name, String oldName) {
/* 191 */     method.instructions.clear();
/*     */     
/* 193 */     for (MethodNode original : math.methods) {
/* 194 */       if (original.name.equals("cos")) {
/* 195 */         method.instructions.add(original.instructions);
/* 196 */         for (AbstractInsnNode node : method.instructions.toArray()) {
/* 197 */           if (node instanceof FieldInsnNode) {
/* 198 */             FieldInsnNode field = (FieldInsnNode)node;
/* 199 */             if (field.owner.equals(oldName)) field.owner = name; 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\guichaguri\betterfps\transformers\MathTransformer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */