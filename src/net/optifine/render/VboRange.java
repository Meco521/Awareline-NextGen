/*    */ package net.optifine.render;
/*    */ 
/*    */ import net.optifine.util.LinkedList;
/*    */ 
/*    */ public class VboRange
/*    */ {
/*  7 */   private int position = -1;
/*    */   private int size;
/*  9 */   private final LinkedList.Node<VboRange> node = new LinkedList.Node(this);
/*    */ 
/*    */   
/*    */   public int getPosition() {
/* 13 */     return this.position;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSize() {
/* 18 */     return this.size;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPositionNext() {
/* 23 */     return this.position + this.size;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setPosition(int position) {
/* 28 */     this.position = position;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setSize(int size) {
/* 33 */     this.size = size;
/*    */   }
/*    */ 
/*    */   
/*    */   public LinkedList.Node<VboRange> getNode() {
/* 38 */     return this.node;
/*    */   }
/*    */ 
/*    */   
/*    */   public VboRange getPrev() {
/* 43 */     LinkedList.Node<VboRange> node = this.node.getPrev();
/* 44 */     return (node == null) ? null : (VboRange)node.getItem();
/*    */   }
/*    */ 
/*    */   
/*    */   public VboRange getNext() {
/* 49 */     LinkedList.Node<VboRange> node = this.node.getNext();
/* 50 */     return (node == null) ? null : (VboRange)node.getItem();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 55 */     return this.position + "/" + this.size + "/" + (this.position + this.size);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\render\VboRange.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */