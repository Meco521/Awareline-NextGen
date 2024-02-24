/*     */ package net.optifine.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.optifine.render.VboRange;
/*     */ 
/*     */ 
/*     */ public class LinkedListTest
/*     */ {
/*     */   public static void main(String[] args) {
/*  12 */     LinkedList<VboRange> linkedlist = new LinkedList<>();
/*  13 */     List<VboRange> list = new ArrayList<>();
/*  14 */     List<VboRange> list1 = new ArrayList<>();
/*  15 */     Random random = new Random();
/*  16 */     int i = 100;
/*     */     
/*  18 */     for (int j = 0; j < i; j++) {
/*     */       
/*  20 */       VboRange vborange = new VboRange();
/*  21 */       vborange.setPosition(j);
/*  22 */       list.add(vborange);
/*     */     } 
/*     */     
/*  25 */     for (int k = 0; k < 100000; k++) {
/*     */       
/*  27 */       checkLists(list, list1, i);
/*  28 */       checkLinkedList(linkedlist, list1.size());
/*     */       
/*  30 */       if (k % 5 == 0)
/*     */       {
/*  32 */         dbgLinkedList(linkedlist);
/*     */       }
/*     */       
/*  35 */       if (random.nextBoolean()) {
/*     */         
/*  37 */         if (!list.isEmpty()) {
/*     */           
/*  39 */           VboRange vborange3 = list.get(random.nextInt(list.size()));
/*  40 */           LinkedList.Node<VboRange> node2 = vborange3.getNode();
/*     */           
/*  42 */           if (random.nextBoolean()) {
/*     */             
/*  44 */             linkedlist.addFirst(node2);
/*  45 */             dbg("Add first: " + vborange3.getPosition());
/*     */           }
/*  47 */           else if (random.nextBoolean()) {
/*     */             
/*  49 */             linkedlist.addLast(node2);
/*  50 */             dbg("Add last: " + vborange3.getPosition());
/*     */           }
/*     */           else {
/*     */             
/*  54 */             if (list1.isEmpty()) {
/*     */               continue;
/*     */             }
/*     */ 
/*     */             
/*  59 */             VboRange vborange1 = list1.get(random.nextInt(list1.size()));
/*  60 */             LinkedList.Node<VboRange> node1 = vborange1.getNode();
/*  61 */             linkedlist.addAfter(node1, node2);
/*  62 */             dbg("Add after: " + vborange1.getPosition() + ", " + vborange3.getPosition());
/*     */           } 
/*     */           
/*  65 */           list.remove(vborange3);
/*  66 */           list1.add(vborange3);
/*     */         }  continue;
/*     */       } 
/*  69 */       if (!list1.isEmpty()) {
/*     */         
/*  71 */         VboRange vborange2 = list1.get(random.nextInt(list1.size()));
/*  72 */         LinkedList.Node<VboRange> node = vborange2.getNode();
/*  73 */         linkedlist.remove(node);
/*  74 */         dbg("Remove: " + vborange2.getPosition());
/*  75 */         list1.remove(vborange2);
/*  76 */         list.add(vborange2);
/*     */       } 
/*     */       continue;
/*     */     } 
/*     */   }
/*     */   private static void dbgLinkedList(LinkedList<VboRange> linkedList) {
/*  82 */     StringBuffer stringbuffer = new StringBuffer();
/*     */     
/*  84 */     linkedList.iterator().forEachRemaining(vboRangeNode -> {
/*     */           LinkedList.Node<VboRange> node = vboRangeNode;
/*     */           
/*     */           if (node.getItem() == null) {
/*     */             return;
/*     */           }
/*     */           
/*     */           VboRange vborange = node.getItem();
/*     */           
/*     */           if (stringbuffer.length() > 0) {
/*     */             stringbuffer.append(", ");
/*     */           }
/*     */           stringbuffer.append(vborange.getPosition());
/*     */         });
/*  98 */     dbg("List: " + stringbuffer);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void checkLinkedList(LinkedList<VboRange> linkedList, int used) {
/* 103 */     if (linkedList.getSize() != used)
/*     */     {
/* 105 */       throw new RuntimeException("Wrong size, linked: " + linkedList.getSize() + ", used: " + used);
/*     */     }
/*     */ 
/*     */     
/* 109 */     int i = 0;
/*     */     
/* 111 */     for (LinkedList.Node<VboRange> node = linkedList.getFirst(); node != null; node = node.getNext())
/*     */     {
/* 113 */       i++;
/*     */     }
/*     */     
/* 116 */     if (linkedList.getSize() != i)
/*     */     {
/* 118 */       throw new RuntimeException("Wrong count, linked: " + linkedList.getSize() + ", count: " + i);
/*     */     }
/*     */ 
/*     */     
/* 122 */     int j = 0;
/*     */     
/* 124 */     for (LinkedList.Node<VboRange> node1 = linkedList.getLast(); node1 != null; node1 = node1.getPrev())
/*     */     {
/* 126 */       j++;
/*     */     }
/*     */     
/* 129 */     if (linkedList.getSize() != j)
/*     */     {
/* 131 */       throw new RuntimeException("Wrong count back, linked: " + linkedList.getSize() + ", count: " + j);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkLists(List<VboRange> listFree, List<VboRange> listUsed, int count) {
/* 139 */     int i = listFree.size() + listUsed.size();
/*     */     
/* 141 */     if (i != count)
/*     */     {
/* 143 */       throw new RuntimeException("Total size: " + i);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void dbg(String str) {
/* 149 */     System.out.println(str);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifin\\util\LinkedListTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */