/*    */ package com.me.guichaguri.betterfps.transformers;
/*    */ 
/*    */ import com.me.guichaguri.betterfps.BetterFps;
/*    */ import com.me.guichaguri.betterfps.IClassTransformer;
/*    */ import com.me.guichaguri.betterfps.tweaker.Naming;
/*    */ import java.util.Iterator;
/*    */ import jdk.internal.org.objectweb.asm.ClassReader;
/*    */ import jdk.internal.org.objectweb.asm.ClassWriter;
/*    */ import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;
/*    */ import jdk.internal.org.objectweb.asm.tree.ClassNode;
/*    */ import jdk.internal.org.objectweb.asm.tree.FieldInsnNode;
/*    */ import jdk.internal.org.objectweb.asm.tree.InsnList;
/*    */ import jdk.internal.org.objectweb.asm.tree.InsnNode;
/*    */ import jdk.internal.org.objectweb.asm.tree.JumpInsnNode;
/*    */ import jdk.internal.org.objectweb.asm.tree.LabelNode;
/*    */ import jdk.internal.org.objectweb.asm.tree.MethodNode;
/*    */ 
/*    */ public class CapTransformer
/*    */   implements IClassTransformer {
/*    */   public byte[] transform(String name, String transformedName, byte[] bytes) {
/* 21 */     if (bytes == null) return null;
/*    */     
/* 23 */     if (Naming.C_PrimedTNT.is(name)) {
/* 24 */       return patchEntityUpdateCap(bytes, "TNT_TICKS", "MAX_TNT_TICKS");
/*    */     }
/*    */     
/* 27 */     return bytes;
/*    */   }
/*    */ 
/*    */   
/*    */   private byte[] patchEntityUpdateCap(byte[] bytes, String fieldName, String maxFieldName) {
/* 32 */     ClassNode classNode = new ClassNode();
/* 33 */     ClassReader classReader = new ClassReader(bytes);
/* 34 */     classReader.accept(classNode, 4);
/*    */     
/* 36 */     Iterator<MethodNode> methods = classNode.methods.iterator();
/* 37 */     boolean patch = false;
/*    */     
/* 39 */     while (methods.hasNext()) {
/* 40 */       MethodNode method = methods.next();
/* 41 */       if (Naming.M_onUpdate.is(method.name, method.desc)) {
/* 42 */         BetterFps.log.info("Patching Entity Cap... (" + classNode.name + ")");
/* 43 */         InsnList list = new InsnList();
/*    */         
/* 45 */         boolean b = false;
/* 46 */         for (AbstractInsnNode node : method.instructions.toArray()) {
/* 47 */           if (!b && node instanceof LabelNode) {
/*    */ 
/*    */             
/* 50 */             list.add(new FieldInsnNode(178, "me/guichaguri/betterfps/BetterFps", fieldName, "I"));
/*    */             
/* 52 */             list.add(new InsnNode(89));
/* 53 */             list.add(new InsnNode(4));
/* 54 */             list.add(new InsnNode(96));
/* 55 */             list.add(new FieldInsnNode(179, "me/guichaguri/betterfps/BetterFps", fieldName, "I"));
/*    */             
/* 57 */             list.add(new FieldInsnNode(178, "me/guichaguri/betterfps/BetterFps", maxFieldName, "I"));
/* 58 */             list.add(new JumpInsnNode(164, (LabelNode)node));
/* 59 */             list.add(new InsnNode(177));
/* 60 */             list.add(node);
/* 61 */             b = true;
/*    */           } else {
/*    */             
/* 64 */             list.add(node);
/*    */           } 
/*    */         } 
/* 67 */         method.instructions.clear();
/* 68 */         method.instructions.add(list);
/* 69 */         patch = true;
/*    */       } 
/*    */     } 
/*    */     
/* 73 */     if (patch) {
/* 74 */       ClassWriter writer = new ClassWriter(3);
/* 75 */       classNode.accept(writer);
/* 76 */       return writer.toByteArray();
/*    */     } 
/*    */     
/* 79 */     return bytes;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\guichaguri\betterfps\transformers\CapTransformer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */