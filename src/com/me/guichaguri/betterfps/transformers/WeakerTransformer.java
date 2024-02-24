/*    */ package com.me.guichaguri.betterfps.transformers;
/*    */ 
/*    */ import com.me.guichaguri.betterfps.IClassTransformer;
/*    */ import com.me.guichaguri.betterfps.tweaker.Naming;
/*    */ import jdk.internal.org.objectweb.asm.ClassReader;
/*    */ import jdk.internal.org.objectweb.asm.ClassWriter;
/*    */ import jdk.internal.org.objectweb.asm.tree.AbstractInsnNode;
/*    */ import jdk.internal.org.objectweb.asm.tree.ClassNode;
/*    */ import jdk.internal.org.objectweb.asm.tree.FieldInsnNode;
/*    */ import jdk.internal.org.objectweb.asm.tree.FieldNode;
/*    */ import jdk.internal.org.objectweb.asm.tree.InsnList;
/*    */ import jdk.internal.org.objectweb.asm.tree.MethodInsnNode;
/*    */ import jdk.internal.org.objectweb.asm.tree.MethodNode;
/*    */ 
/*    */ public class WeakerTransformer
/*    */   implements IClassTransformer
/*    */ {
/*    */   public byte[] transform(String name, String transformedName, byte[] bytes) {
/* 19 */     if (bytes == null) return null;
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 24 */     return bytes;
/*    */   }
/*    */   
/*    */   public byte[] patchWeakKeys(byte[] bytes, Naming[] fieldsToWeak) {
/* 28 */     ClassNode classNode = new ClassNode();
/* 29 */     ClassReader classReader = new ClassReader(bytes);
/* 30 */     classReader.accept(classNode, 4);
/* 31 */     boolean patch = false;
/*    */     
/* 33 */     for (FieldNode field : classNode.fields) {
/*    */       
/* 35 */       for (Naming f : fieldsToWeak) {
/* 36 */         if (f.is(field.name, field.desc)) {
/* 37 */           String oldDesc = field.desc;
/* 38 */           field.desc = "Ljava/lang/ref/WeakReference;";
/* 39 */           field.signature = "Ljava/lang/ref/WeakReference<" + oldDesc + ">;";
/* 40 */           patch = true;
/*    */           
/*    */           break;
/*    */         } 
/*    */       } 
/*    */     } 
/* 46 */     for (MethodNode method : classNode.methods) {
/*    */       
/* 48 */       InsnList newList = new InsnList();
/* 49 */       AbstractInsnNode n = null;
/*    */ 
/*    */       
/* 52 */       for (AbstractInsnNode node : method.instructions.toArray()) {
/* 53 */         if (n != null) newList.add(n); 
/* 54 */         n = node;
/* 55 */         if (node instanceof FieldInsnNode) {
/* 56 */           FieldInsnNode fNode = (FieldInsnNode)node;
/* 57 */           if (fNode.owner.equals(classNode.name))
/*    */           {
/* 59 */             for (Naming f : fieldsToWeak) {
/* 60 */               if (f.is(fNode.name, fNode.desc)) {
/* 61 */                 fNode.desc = "Ljava/lang/ref/WeakReference;";
/* 62 */                 if (fNode.getOpcode() == 181) {
/* 63 */                   newList.add(new MethodInsnNode(183, "java/lang/ref/WeakReference", "<init>", "(Ljava/lang/Object;)V", false));
/* 64 */                   newList.add(fNode);
/* 65 */                   n = null;
/* 66 */                 } else if (fNode.getOpcode() == 180) {
/* 67 */                   newList.add(fNode);
/* 68 */                   newList.add(new MethodInsnNode(182, "java/lang/ref/WeakReference", "get", "()Ljava/lang/Object;", false));
/* 69 */                   n = null;
/*    */                 } 
/* 71 */                 patch = true;
/*    */                 break;
/*    */               } 
/*    */             }  } 
/*    */         } 
/*    */       } 
/* 77 */       if (n != null) newList.add(n);
/*    */       
/* 79 */       method.instructions.clear();
/* 80 */       method.instructions.add(newList);
/*    */     } 
/*    */     
/* 83 */     if (patch) {
/* 84 */       ClassWriter writer = new ClassWriter(3);
/* 85 */       classNode.accept(writer);
/* 86 */       return writer.toByteArray();
/*    */     } 
/*    */     
/* 89 */     return bytes;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\guichaguri\betterfps\transformers\WeakerTransformer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */