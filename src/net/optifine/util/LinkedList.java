/*     */ package net.optifine.util;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ 
/*     */ public class LinkedList<T>
/*     */ {
/*     */   private Node<T> first;
/*     */   private Node<T> last;
/*     */   private int size;
/*     */   
/*     */   public void addFirst(Node<T> tNode) {
/*  12 */     checkNoParent(tNode);
/*     */     
/*  14 */     if (isEmpty()) {
/*  15 */       this.first = tNode;
/*  16 */       this.last = tNode;
/*     */     } else {
/*  18 */       Node<T> node = this.first;
/*  19 */       tNode.setNext(node);
/*  20 */       node.setPrev(tNode);
/*  21 */       this.first = tNode;
/*     */     } 
/*     */     
/*  24 */     tNode.setParent(this);
/*  25 */     this.size++;
/*     */   }
/*     */   
/*     */   public void addLast(Node<T> tNode) {
/*  29 */     checkNoParent(tNode);
/*     */     
/*  31 */     if (isEmpty()) {
/*  32 */       this.first = tNode;
/*  33 */       this.last = tNode;
/*     */     } else {
/*  35 */       Node<T> node = this.last;
/*  36 */       tNode.setPrev(node);
/*  37 */       node.setNext(tNode);
/*  38 */       this.last = tNode;
/*     */     } 
/*     */     
/*  41 */     tNode.setParent(this);
/*  42 */     this.size++;
/*     */   }
/*     */   
/*     */   public void addAfter(Node<T> nodePrev, Node<T> tNode) {
/*  46 */     if (nodePrev == null) {
/*  47 */       addFirst(tNode);
/*  48 */     } else if (nodePrev == this.last) {
/*  49 */       addLast(tNode);
/*     */     } else {
/*  51 */       checkParent(nodePrev);
/*  52 */       checkNoParent(tNode);
/*  53 */       Node<T> nodeNext = nodePrev.getNext();
/*  54 */       nodePrev.setNext(tNode);
/*  55 */       tNode.setPrev(nodePrev);
/*  56 */       nodeNext.setPrev(tNode);
/*  57 */       tNode.setNext(nodeNext);
/*  58 */       tNode.setParent(this);
/*  59 */       this.size++;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Node<T> remove(Node<T> tNode) {
/*  64 */     checkParent(tNode);
/*  65 */     Node<T> prev = tNode.getPrev();
/*  66 */     Node<T> next = tNode.getNext();
/*     */     
/*  68 */     if (prev != null) {
/*  69 */       prev.setNext(next);
/*     */     } else {
/*  71 */       this.first = next;
/*     */     } 
/*     */     
/*  74 */     if (next != null) {
/*  75 */       next.setPrev(prev);
/*     */     } else {
/*  77 */       this.last = prev;
/*     */     } 
/*     */     
/*  80 */     tNode.setPrev(null);
/*  81 */     tNode.setNext(null);
/*  82 */     tNode.setParent(null);
/*  83 */     this.size--;
/*  84 */     return tNode;
/*     */   }
/*     */ 
/*     */   
/*     */   public void moveAfter(Node<T> nodePrev, Node<T> node) {
/*  89 */     remove(node);
/*  90 */     addAfter(nodePrev, node);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean find(Node<T> nodeFind, Node<T> nodeFrom, Node<T> nodeTo) {
/*  95 */     checkParent(nodeFrom);
/*     */     
/*  97 */     if (nodeTo != null)
/*     */     {
/*  99 */       checkParent(nodeTo);
/*     */     }
/*     */     
/*     */     Node<T> node;
/*     */     
/* 104 */     for (node = nodeFrom; node != null && node != nodeTo; node = node.getNext()) {
/*     */       
/* 106 */       if (node == nodeFind)
/*     */       {
/* 108 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 112 */     if (node != nodeTo)
/*     */     {
/* 114 */       throw new IllegalArgumentException("Sublist is not linked, from: " + nodeFrom + ", to: " + nodeTo);
/*     */     }
/*     */ 
/*     */     
/* 118 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkParent(Node<T> node) {
/* 124 */     if (node.parent != this)
/*     */     {
/* 126 */       throw new IllegalArgumentException("Node has different parent, node: " + node + ", parent: " + node.parent + ", this: " + this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkNoParent(Node<T> node) {
/* 132 */     if (node.parent != null)
/*     */     {
/* 134 */       throw new IllegalArgumentException("Node has different parent, node: " + node + ", parent: " + node.parent + ", this: " + this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(Node<T> node) {
/* 140 */     return (node.parent == this);
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<Node<T>> iterator() {
/* 145 */     Iterator<Node<T>> iterator = new Iterator<Node<T>>()
/*     */       {
/* 147 */         LinkedList.Node<T> node = LinkedList.this.getFirst();
/*     */         
/*     */         public boolean hasNext() {
/* 150 */           return (this.node != null);
/*     */         }
/*     */         
/*     */         public LinkedList.Node<T> next() {
/* 154 */           LinkedList.Node<T> node = this.node;
/*     */           
/* 156 */           if (this.node != null)
/*     */           {
/* 158 */             this.node = this.node.next;
/*     */           }
/*     */           
/* 161 */           return node;
/*     */         }
/*     */         
/*     */         public void remove() {
/* 165 */           throw new UnsupportedOperationException("remove");
/*     */         }
/*     */       };
/* 168 */     return iterator;
/*     */   }
/*     */ 
/*     */   
/*     */   public Node<T> getFirst() {
/* 173 */     return this.first;
/*     */   }
/*     */ 
/*     */   
/*     */   public Node<T> getLast() {
/* 178 */     return this.last;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSize() {
/* 183 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 188 */     return (this.size <= 0);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 192 */     StringBuffer stringbuffer = new StringBuffer();
/*     */     
/* 194 */     for (Iterator<Node<T>> it = iterator(); it.hasNext(); ) {
/* 195 */       Node<T> node = it.next();
/* 196 */       if (stringbuffer.length() > 0) {
/* 197 */         stringbuffer.append(", ");
/*     */       }
/* 199 */       stringbuffer.append(node.getItem());
/*     */     } 
/*     */     
/* 202 */     return this.size + " [" + stringbuffer + "]";
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Node<T>
/*     */   {
/*     */     private final T item;
/*     */     private Node<T> prev;
/*     */     Node<T> next;
/*     */     LinkedList<T> parent;
/*     */     
/*     */     public Node(T item) {
/* 214 */       this.item = item;
/*     */     }
/*     */ 
/*     */     
/*     */     public T getItem() {
/* 219 */       return this.item;
/*     */     }
/*     */ 
/*     */     
/*     */     public Node<T> getPrev() {
/* 224 */       return this.prev;
/*     */     }
/*     */ 
/*     */     
/*     */     public Node<T> getNext() {
/* 229 */       return this.next;
/*     */     }
/*     */ 
/*     */     
/*     */     void setPrev(Node<T> prev) {
/* 234 */       this.prev = prev;
/*     */     }
/*     */ 
/*     */     
/*     */     void setNext(Node<T> next) {
/* 239 */       this.next = next;
/*     */     }
/*     */ 
/*     */     
/*     */     void setParent(LinkedList<T> parent) {
/* 244 */       this.parent = parent;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 249 */       return String.valueOf(this.item);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifin\\util\LinkedList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */