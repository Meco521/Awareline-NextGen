/*     */ package com.me.guichaguri.betterfps.transformers;
/*     */ 
/*     */ import com.me.guichaguri.betterfps.BetterFps;
/*     */ import com.me.guichaguri.betterfps.IClassTransformer;
/*     */ import com.me.guichaguri.betterfps.tweaker.Naming;
/*     */ import java.util.Iterator;
/*     */ import jdk.internal.org.objectweb.asm.ClassReader;
/*     */ import jdk.internal.org.objectweb.asm.ClassWriter;
/*     */ import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.ClassNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.InsnList;
/*     */ import jdk.internal.org.objectweb.asm.tree.LdcInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.MethodInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.MethodNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.VarInsnNode;
/*     */ 
/*     */ public class EventTransformer
/*     */   implements IClassTransformer
/*     */ {
/*     */   public byte[] transform(String name, String transformedName, byte[] bytes) {
/*  21 */     if (bytes == null) return new byte[0];
/*     */     
/*     */     try {
/*  24 */       if (Naming.C_Minecraft.is(name))
/*  25 */         return patchClientStart(bytes); 
/*  26 */       if (!Naming.C_KeyBinding.is(name)) {
/*     */         
/*  28 */         if (Naming.C_World.is(name))
/*  29 */           return patchWorldTick(bytes); 
/*  30 */         if (Naming.C_ClientBrandRetriever.is(name))
/*  31 */           return patchClientBrand(bytes); 
/*  32 */         if (Naming.C_WorldClient.is(name))
/*  33 */           return patchClientWorldLoad(bytes); 
/*  34 */         if (Naming.C_DedicatedServer.is(name))
/*  35 */           return patchServerStart(bytes); 
/*  36 */         if (Naming.C_IntegratedServer.is(name))
/*  37 */           return patchServerStart(bytes); 
/*     */       } 
/*  39 */     } catch (Exception ex) {
/*  40 */       ex.printStackTrace();
/*     */     } 
/*     */     
/*  43 */     return bytes;
/*     */   }
/*     */   
/*     */   private byte[] patchClientStart(byte[] bytes) {
/*  47 */     ClassNode classNode = new ClassNode();
/*  48 */     ClassReader classReader = new ClassReader(bytes);
/*  49 */     classReader.accept(classNode, 4);
/*     */     
/*  51 */     Iterator<MethodNode> methods = classNode.methods.iterator();
/*  52 */     boolean patch = false;
/*     */     
/*  54 */     while (methods.hasNext()) {
/*  55 */       MethodNode method = methods.next();
/*     */       
/*  57 */       if (Naming.M_startGame.is(method.name, method.desc)) {
/*  58 */         BetterFps.log.info("Patching Game Start...");
/*  59 */         InsnList list = new InsnList();
/*     */         
/*  61 */         for (AbstractInsnNode node : method.instructions.toArray()) {
/*  62 */           if (node.getOpcode() == 177) {
/*     */             
/*  64 */             list.add(new VarInsnNode(25, 0));
/*     */             
/*  66 */             list.add(new MethodInsnNode(184, "me/guichaguri/betterfps/BetterFpsClient", "start", "(L" + classNode.name + ";)V", false));
/*     */           } 
/*     */ 
/*     */           
/*  70 */           list.add(node);
/*     */         } 
/*     */         
/*  73 */         method.instructions.clear();
/*  74 */         method.instructions.add(list);
/*  75 */         patch = true;
/*     */       } 
/*     */     } 
/*     */     
/*  79 */     if (!patch) return bytes;
/*     */     
/*  81 */     ClassWriter writer = new ClassWriter(1);
/*  82 */     classNode.accept(writer);
/*  83 */     return writer.toByteArray();
/*     */   }
/*     */   
/*     */   private byte[] patchKeyTick(byte[] bytes) {
/*  87 */     ClassNode classNode = new ClassNode();
/*  88 */     ClassReader classReader = new ClassReader(bytes);
/*  89 */     classReader.accept(classNode, 4);
/*     */     
/*  91 */     Iterator<MethodNode> methods = classNode.methods.iterator();
/*  92 */     boolean patch = false;
/*     */     
/*  94 */     while (methods.hasNext()) {
/*  95 */       MethodNode method = methods.next();
/*     */       
/*  97 */       if (Naming.M_onTick.is(method.name, method.desc)) {
/*  98 */         BetterFps.log.info("Patching SimpleWhiteKey Event...");
/*  99 */         InsnList list = new InsnList();
/* 100 */         for (AbstractInsnNode node : method.instructions.toArray()) {
/* 101 */           if (node.getOpcode() == 177) {
/*     */             
/* 103 */             list.add(new VarInsnNode(21, 0));
/* 104 */             list.add(new MethodInsnNode(184, "me/guichaguri/betterfps/BetterFpsClient", "keyEvent", "(I)V", false));
/*     */           } 
/*     */ 
/*     */           
/* 108 */           list.add(node);
/*     */         } 
/*     */         
/* 111 */         method.instructions.clear();
/* 112 */         method.instructions.add(list);
/* 113 */         patch = true;
/*     */       } 
/*     */     } 
/*     */     
/* 117 */     if (!patch) return bytes;
/*     */     
/* 119 */     ClassWriter writer = new ClassWriter(3);
/* 120 */     classNode.accept(writer);
/* 121 */     return writer.toByteArray();
/*     */   }
/*     */ 
/*     */   
/*     */   private byte[] patchWorldTick(byte[] bytes) {
/* 126 */     ClassNode classNode = new ClassNode();
/* 127 */     ClassReader classReader = new ClassReader(bytes);
/* 128 */     classReader.accept(classNode, 4);
/*     */     
/* 130 */     Iterator<MethodNode> methods = classNode.methods.iterator();
/* 131 */     boolean patch = false;
/*     */     
/* 133 */     while (methods.hasNext()) {
/* 134 */       MethodNode method = methods.next();
/*     */       
/* 136 */       if (Naming.M_tick.is(method.name, method.desc)) {
/* 137 */         BetterFps.log.info("Patching World Event...");
/* 138 */         InsnList list = new InsnList();
/* 139 */         for (AbstractInsnNode node : method.instructions.toArray()) {
/* 140 */           if (node.getOpcode() == 177)
/*     */           {
/* 142 */             list.add(new MethodInsnNode(184, "me/guichaguri/betterfps/BetterFps", "worldTick", "()V", false));
/*     */           }
/*     */ 
/*     */           
/* 146 */           list.add(node);
/*     */         } 
/*     */         
/* 149 */         method.instructions.clear();
/* 150 */         method.instructions.add(list);
/* 151 */         patch = true;
/*     */       } 
/*     */     } 
/*     */     
/* 155 */     if (!patch) return bytes;
/*     */     
/* 157 */     ClassWriter writer = new ClassWriter(3);
/* 158 */     classNode.accept(writer);
/* 159 */     return writer.toByteArray();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] patchClientBrand(byte[] bytes) {
/* 164 */     ClassNode classNode = new ClassNode();
/* 165 */     ClassReader classReader = new ClassReader(bytes);
/* 166 */     classReader.accept(classNode, 4);
/*     */     
/* 168 */     Iterator<MethodNode> methods = classNode.methods.iterator();
/* 169 */     boolean patch = false;
/*     */     
/* 171 */     while (methods.hasNext()) {
/* 172 */       MethodNode method = methods.next();
/*     */       
/* 174 */       if (Naming.M_getClientModName.is(method.name, method.desc)) {
/* 175 */         BetterFps.log.info("Patching Client Brand...");
/* 176 */         InsnList list = new InsnList();
/* 177 */         for (AbstractInsnNode node : method.instructions.toArray()) {
/* 178 */           if (node instanceof LdcInsnNode) {
/* 179 */             LdcInsnNode ldc = (LdcInsnNode)node;
/* 180 */             if (ldc.cst instanceof String && ldc.cst.equals("vanilla")) {
/* 181 */               ldc.cst = "BetterFps-1.2.1";
/*     */             }
/*     */           } 
/* 184 */           list.add(node);
/*     */         } 
/*     */         
/* 187 */         method.instructions.clear();
/* 188 */         method.instructions.add(list);
/* 189 */         patch = true;
/*     */       } 
/*     */     } 
/*     */     
/* 193 */     if (!patch) return bytes;
/*     */     
/* 195 */     ClassWriter writer = new ClassWriter(3);
/* 196 */     classNode.accept(writer);
/* 197 */     return writer.toByteArray();
/*     */   }
/*     */   
/*     */   public byte[] patchClientWorldLoad(byte[] bytes) {
/* 201 */     ClassNode classNode = new ClassNode();
/* 202 */     ClassReader classReader = new ClassReader(bytes);
/* 203 */     classReader.accept(classNode, 4);
/* 204 */     boolean patch = false;
/*     */     
/* 206 */     for (MethodNode method : classNode.methods) {
/* 207 */       if (Naming.M_Constructor.is(method.name)) {
/* 208 */         BetterFps.log.info("Patching World Client Event...");
/* 209 */         InsnList list = new InsnList();
/* 210 */         for (AbstractInsnNode node : method.instructions.toArray()) {
/* 211 */           if (node.getOpcode() == 177)
/*     */           {
/* 213 */             list.add(new MethodInsnNode(184, "me/guichaguri/betterfps/BetterFpsClient", "worldLoad", "()V", false));
/*     */           }
/*     */ 
/*     */           
/* 217 */           list.add(node);
/*     */         } 
/*     */         
/* 220 */         method.instructions.clear();
/* 221 */         method.instructions.add(list);
/* 222 */         patch = true;
/*     */       } 
/*     */     } 
/*     */     
/* 226 */     if (!patch) return bytes;
/*     */     
/* 228 */     ClassWriter writer = new ClassWriter(3);
/* 229 */     classNode.accept(writer);
/* 230 */     return writer.toByteArray();
/*     */   }
/*     */   
/*     */   public byte[] patchServerStart(byte[] bytes) {
/* 234 */     ClassNode classNode = new ClassNode();
/* 235 */     ClassReader classReader = new ClassReader(bytes);
/* 236 */     classReader.accept(classNode, 4);
/* 237 */     boolean patch = false;
/*     */     
/* 239 */     for (MethodNode method : classNode.methods) {
/* 240 */       if (Naming.M_startServer.is(method.name, method.desc)) {
/* 241 */         BetterFps.log.info("Patching Server Start Event...");
/* 242 */         InsnList list = new InsnList();
/* 243 */         list.add(new MethodInsnNode(184, "me/guichaguri/betterfps/BetterFps", "serverStart", "()V", false));
/*     */         
/* 245 */         for (AbstractInsnNode node : method.instructions.toArray()) {
/* 246 */           list.add(node);
/*     */         }
/*     */         
/* 249 */         method.instructions.clear();
/* 250 */         method.instructions.add(list);
/* 251 */         patch = true;
/*     */       } 
/*     */     } 
/*     */     
/* 255 */     if (!patch) return bytes;
/*     */     
/* 257 */     ClassWriter writer = new ClassWriter(3);
/* 258 */     classNode.accept(writer);
/* 259 */     return writer.toByteArray();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\guichaguri\betterfps\transformers\EventTransformer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */