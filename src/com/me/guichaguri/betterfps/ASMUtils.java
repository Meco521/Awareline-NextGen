/*     */ package com.me.guichaguri.betterfps;
/*     */ import com.me.guichaguri.betterfps.tweaker.Naming;
/*     */ import java.util.List;
/*     */ import jdk.internal.org.objectweb.asm.ClassReader;
/*     */ import jdk.internal.org.objectweb.asm.ClassWriter;
/*     */ import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.AnnotationNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.ClassNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.FieldNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.InsnList;
/*     */ import jdk.internal.org.objectweb.asm.tree.LabelNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.LocalVariableNode;
/*     */ import jdk.internal.org.objectweb.asm.tree.MethodNode;
/*     */ 
/*     */ public class ASMUtils {
/*     */   public static ClassNode readClass(byte[] bytes, int flags) {
/*  17 */     ClassNode classNode = new ClassNode();
/*  18 */     ClassReader classReader = new ClassReader(bytes);
/*  19 */     classReader.accept(classNode, flags);
/*  20 */     return classNode;
/*     */   }
/*     */   
/*     */   public static byte[] writeClass(ClassNode node, int flags) {
/*  24 */     ClassWriter writer = new ClassWriter(flags);
/*  25 */     node.accept(writer);
/*  26 */     return writer.toByteArray();
/*     */   }
/*     */   
/*     */   public static MethodNode findMethod(ClassNode node, Naming naming) {
/*  30 */     for (MethodNode m : node.methods) {
/*  31 */       if (naming.is(m.name, m.desc)) return m; 
/*     */     } 
/*  33 */     return null;
/*     */   }
/*     */   
/*     */   public static FieldNode findField(ClassNode node, Naming naming) {
/*  37 */     for (FieldNode m : node.fields) {
/*  38 */       if (naming.is(m.name, m.desc)) return m; 
/*     */     } 
/*  40 */     return null;
/*     */   }
/*     */   
/*     */   public static MethodNode findMethod(ClassNode node, String name) {
/*  44 */     for (MethodNode m : node.methods) {
/*  45 */       if (m.name.equals(name)) return m; 
/*     */     } 
/*  47 */     return null;
/*     */   }
/*     */   
/*     */   public static int getNextAvailableIndex(List<LocalVariableNode> nodes) {
/*  51 */     return getNextAvailableIndex(nodes, 0);
/*     */   }
/*     */   
/*     */   private static int getNextAvailableIndex(List<LocalVariableNode> nodes, int index) {
/*  55 */     for (LocalVariableNode node : nodes) {
/*  56 */       if (index == node.index) {
/*  57 */         return getNextAvailableIndex(nodes, node.index + 1);
/*     */       }
/*     */     } 
/*  60 */     return index;
/*     */   }
/*     */   
/*     */   public static InsnList appendNodeList(InsnList initial, InsnList extra) {
/*  64 */     return mergeNodeLists(extra, initial);
/*     */   }
/*     */   
/*     */   public static InsnList prependNodeList(InsnList initial, InsnList extra) {
/*  68 */     InsnList list = new InsnList();
/*     */     
/*  70 */     boolean added = false;
/*  71 */     for (AbstractInsnNode node : initial.toArray()) {
/*  72 */       if (!added && node instanceof LabelNode) {
/*  73 */         list.add(extra);
/*  74 */         added = true;
/*     */       } 
/*  76 */       list.add(node);
/*     */     } 
/*  78 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public static InsnList mergeNodeLists(InsnList from, InsnList to) {
/*  83 */     InsnList list = new InsnList();
/*     */     
/*  85 */     AbstractInsnNode[] nodes = to.toArray();
/*  86 */     int lastReturn = -1; int i;
/*  87 */     for (i = 0; i < nodes.length; i++) {
/*  88 */       AbstractInsnNode node = nodes[i];
/*  89 */       if (node.getOpcode() == 177) lastReturn = i; 
/*     */     } 
/*  91 */     for (i = 0; i < nodes.length; i++) {
/*  92 */       AbstractInsnNode node = nodes[i];
/*  93 */       if (i == lastReturn) {
/*  94 */         list.add(from);
/*     */       }
/*  96 */       list.add(node);
/*     */     } 
/*     */     
/*  99 */     return list;
/*     */   }
/*     */   
/*     */   public static void setVariableToMaxPeriod(AbstractInsnNode[] nodes, LocalVariableNode node) {
/* 103 */     LabelNode first = null, last = null;
/* 104 */     for (AbstractInsnNode n : nodes) {
/* 105 */       if (n instanceof LabelNode) {
/* 106 */         last = (LabelNode)n;
/* 107 */         if (first == null) first = last; 
/*     */       } 
/*     */     } 
/* 110 */     node.start = first;
/* 111 */     node.end = last;
/*     */   }
/*     */   
/*     */   public static void addToInsnList(InsnList list, AbstractInsnNode[] nodes) {
/* 115 */     for (AbstractInsnNode node : nodes) {
/* 116 */       list.add(node);
/*     */     }
/*     */   }
/*     */   
/*     */   public static String getAnnotationValue(AnnotationNode node, String k) {
/* 121 */     return getAnnotationValue(node, k, String.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public static <T> T getAnnotationValue(AnnotationNode node, String k, Class<T> type) {
/* 126 */     if (node.values == null) return null; 
/* 127 */     boolean isEnum = type.isEnum();
/* 128 */     for (int x = 0; x < node.values.size() - 1; x += 2) {
/* 129 */       Object key = node.values.get(x);
/* 130 */       Object value = node.values.get(x + 1);
/* 131 */       if (key instanceof String && key.equals(k)) {
/* 132 */         if (isEnum) {
/* 133 */           if (value instanceof String[]) {
/* 134 */             return (T)Enum.valueOf((Class)type, ((String[])value)[1]);
/*     */           }
/*     */         } else {
/* 137 */           if (value instanceof String[]) {
/* 138 */             return (T)((String[])value)[1];
/*     */           }
/* 140 */           return (T)value;
/*     */         } 
/*     */       }
/*     */     } 
/* 144 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\guichaguri\betterfps\ASMUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */