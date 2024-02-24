/*     */ package com.me.guichaguri.betterfps.transformers;
/*     */ import com.me.guichaguri.betterfps.ASMUtils;
/*     */ import com.me.guichaguri.betterfps.BetterFps;
/*     */ import com.me.guichaguri.betterfps.tweaker.Naming;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.ClassNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.FieldInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.FrameNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.InsnList;
/*     */ import jdk.internal.org.objectweb.asm.tree.InsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.JumpInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.LabelNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.LocalVariableNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.MethodInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.MethodNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.VarInsnNode;
/*     */ 
/*     */ public class VisualChunkTransformer implements IClassTransformer {
/*     */   public byte[] transform(String name, String transformedName, byte[] bytes) {
/*  22 */     if (bytes == null) return bytes;
/*     */     
/*  24 */     if (Naming.C_WorldServer.is(name)) {
/*  25 */       ClassNode node = ASMUtils.readClass(bytes, 0);
/*  26 */       for (MethodNode m : node.methods) {
/*  27 */         if (Naming.M_updateBlocks.is(m.name, m.desc)) {
/*  28 */           BetterFps.log.info("PATCH TICK +++++++++++++++++++++++++ " + node.name);
/*  29 */           patchTick(m, "thunder");
/*     */         } 
/*     */       } 
/*  32 */       return ASMUtils.writeClass(node, 3);
/*  33 */     }  if (Naming.C_WorldClient.is(name)) {
/*  34 */       ClassNode node = ASMUtils.readClass(bytes, 0);
/*  35 */       for (MethodNode m : node.methods) {
/*  36 */         if (Naming.M_updateBlocks.is(m.name, m.desc)) {
/*  37 */           BetterFps.log.info("PATCH TICK +++++++++++++++++++++++++ " + node.name);
/*     */         }
/*     */       } 
/*     */       
/*  41 */       return ASMUtils.writeClass(node, 3);
/*  42 */     }  if (Naming.C_World.is(name)) {
/*  43 */       ClassNode node = ASMUtils.readClass(bytes, 0);
/*  44 */       for (MethodNode m : node.methods) {
/*  45 */         if (Naming.M_setActivePlayerChunksAndCheckLight.is(m.name, m.desc)) {
/*  46 */           patchTickableCheck(m);
/*     */         }
/*     */       } 
/*  49 */       return ASMUtils.writeClass(node, 3);
/*  50 */     }  if (Naming.C_ChunkCoordIntPair.is(name)) {
/*  51 */       ClassNode node = ASMUtils.readClass(bytes, 0);
/*  52 */       patchChunk(node);
/*  53 */       return ASMUtils.writeClass(node, 3);
/*     */     } 
/*     */     
/*  56 */     return bytes;
/*     */   }
/*     */   
/*     */   private void patchChunk(ClassNode node) {
/*  60 */     node.fields.add(new FieldNode(1, "isTickable", "Z", null, null));
/*     */     
/*  62 */     MethodNode m = new MethodNode(1, "<init>", "(IIZ)V", null, null);
/*  63 */     m.instructions = new InsnList();
/*     */     
/*  65 */     LabelNode l1 = new LabelNode();
/*  66 */     m.instructions.add(l1);
/*     */     
/*  68 */     m.instructions.add(new VarInsnNode(25, 0));
/*  69 */     m.instructions.add(new VarInsnNode(21, 1));
/*  70 */     m.instructions.add(new VarInsnNode(21, 2));
/*  71 */     m.instructions.add(new MethodInsnNode(183, node.name, "<init>", "(II)V", false));
/*     */ 
/*     */     
/*  74 */     m.instructions.add(new VarInsnNode(25, 0));
/*  75 */     m.instructions.add(new VarInsnNode(21, 3));
/*  76 */     m.instructions.add(new FieldInsnNode(181, node.name, "isTickable", "Z"));
/*     */     
/*  78 */     LabelNode l2 = new LabelNode();
/*  79 */     m.instructions.add(l2);
/*  80 */     m.instructions.add(new InsnNode(177));
/*     */ 
/*     */     
/*  83 */     m.localVariables.clear();
/*  84 */     m.localVariables.add(new LocalVariableNode("this", "L" + node.name + ";", null, l1, l2, 0));
/*  85 */     m.localVariables.add(new LocalVariableNode("f1", "I", null, l1, l2, 1));
/*  86 */     m.localVariables.add(new LocalVariableNode("f2", "I", null, l1, l2, 2));
/*  87 */     m.localVariables.add(new LocalVariableNode("f3", "Z", null, l1, l2, 3));
/*     */     
/*  89 */     node.methods.add(m);
/*     */   }
/*     */   
/*     */   private void patchTickableCheck(MethodNode method) {
/*  93 */     InsnList list = new InsnList();
/*  94 */     for (AbstractInsnNode node : method.instructions.toArray()) {
/*  95 */       if (node instanceof MethodInsnNode) {
/*  96 */         MethodInsnNode m = (MethodInsnNode)node;
/*  97 */         if (Naming.C_ChunkCoordIntPair.isASM(m.owner) && Naming.M_Constructor.is(m.name)) {
/*  98 */           BetterFps.log.info("Patching tickable chunks check...");
/*  99 */           list.add(new VarInsnNode(21, 6));
/* 100 */           list.add(new VarInsnNode(21, 7));
/* 101 */           list.add(new MethodInsnNode(184, "me/guichaguri/betterfps/BetterFps", "isTickable", "(II)Z", false));
/* 102 */           m.desc = "(IIZ)V";
/*     */         } 
/*     */       } 
/* 105 */       list.add(node);
/*     */     } 
/*     */     
/* 108 */     method.instructions.clear();
/* 109 */     method.instructions.add(list);
/*     */   }
/*     */   
/*     */   private HashMap<FrameNode, LabelNode> getFrames(HashMap<FrameNode, LabelNode> hm, AbstractInsnNode[] list, FrameNode after) {
/* 113 */     FrameNode frame = null;
/* 114 */     LabelNode lastLabel = null;
/* 115 */     for (AbstractInsnNode node : list) {
/* 116 */       if (node instanceof FrameNode) {
/*     */         
/* 118 */         if (node.getOpcode() != 3)
/* 119 */         { if (after == null || after == node) {
/* 120 */             after = null;
/* 121 */             if (frame == null) {
/* 122 */               frame = (FrameNode)node;
/*     */             } else {
/* 124 */               getFrames(hm, list, (FrameNode)node);
/*     */             } 
/*     */           }  }
/* 127 */         else { hm.put(frame, lastLabel);
/* 128 */           frame = null; }
/*     */       
/* 130 */       } else if (node instanceof LabelNode) {
/* 131 */         lastLabel = (LabelNode)node;
/*     */       } 
/*     */     } 
/* 134 */     return hm;
/*     */   }
/*     */   
/*     */   private void patchTick(MethodNode method, String afterLdcStr) {
/* 138 */     String coordName = null;
/* 139 */     List<Integer> lvs = new ArrayList<>();
/* 140 */     for (LocalVariableNode node : method.localVariables) {
/* 141 */       int s = node.desc.length();
/* 142 */       if (s <= 2)
/* 143 */         continue;  String c = node.desc.substring(1, s - 1);
/* 144 */       if (Naming.C_ChunkCoordIntPair.isASM(c)) {
/* 145 */         coordName = c;
/* 146 */         lvs.add(Integer.valueOf(node.index));
/* 147 */         BetterFps.log.info("PATCH TICK ----------------------- " + node.index + "  - " + method.name);
/*     */       } 
/*     */     } 
/* 150 */     if (lvs.isEmpty())
/*     */       return; 
/* 152 */     InsnList list = new InsnList();
/*     */     
/* 154 */     boolean afterLdc = (afterLdcStr != null);
/* 155 */     AbstractInsnNode[] instList = method.instructions.toArray();
/* 156 */     HashMap<FrameNode, LabelNode> frames = getFrames(new HashMap<>(), instList, null);
/* 157 */     VarInsnNode lastVar = null;
/* 158 */     FrameNode frame = null;
/*     */     
/* 160 */     for (AbstractInsnNode node : instList) {
/* 161 */       list.add(node);
/* 162 */       if (afterLdc && node instanceof LdcInsnNode) {
/* 163 */         if (afterLdcStr.equals(((LdcInsnNode)node).cst) && lastVar != null) {
/* 164 */           BetterFps.log.info(Integer.valueOf(frames.size()));
/* 165 */           addTickCheck(list, lastVar, coordName, frames.get(frame));
/*     */         } 
/* 167 */       } else if (node.getOpcode() == 58 && node instanceof VarInsnNode) {
/* 168 */         VarInsnNode var = (VarInsnNode)node;
/* 169 */         if (lvs.contains(Integer.valueOf(var.var))) {
/* 170 */           if (afterLdc) {
/* 171 */             lastVar = var;
/*     */           } else {
/* 173 */             BetterFps.log.info(Integer.valueOf(frames.size()));
/* 174 */             addTickCheck(list, var, coordName, frames.get(frame));
/*     */           } 
/*     */         }
/* 177 */       } else if (node instanceof FrameNode) {
/* 178 */         if (node.getOpcode() == 3) {
/* 179 */           frame = null;
/*     */         } else {
/* 181 */           frame = (FrameNode)node;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 186 */     method.instructions.clear();
/* 187 */     method.instructions.add(list);
/*     */   }
/*     */   
/*     */   private void addTickCheck(InsnList list, VarInsnNode var, String coordName, LabelNode loop) {
/* 191 */     list.add(new VarInsnNode(25, var.var));
/* 192 */     list.add(new FieldInsnNode(180, coordName, "isTickable", "Z"));
/* 193 */     LabelNode l1 = new LabelNode();
/* 194 */     list.add(new JumpInsnNode(153, l1));
/* 195 */     LabelNode l2 = new LabelNode();
/* 196 */     list.add(l2);
/* 197 */     if (loop != null) {
/* 198 */       BetterFps.log.info("----------- LOOP: " + loop.getOpcode());
/* 199 */       list.add(new JumpInsnNode(167, loop));
/*     */     } else {
/* 201 */       BetterFps.log.info("----------- RETURN ");
/* 202 */       list.add(new InsnNode(177));
/*     */     } 
/* 204 */     list.add(l1);
/* 205 */     list.add(new FrameNode(1, 1, new Object[] { coordName }, 0, null));
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\guichaguri\betterfps\transformers\VisualChunkTransformer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */