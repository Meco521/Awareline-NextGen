/*    */ package com.profesorfalken.wmi4java;
/*    */ 
/*    */ public final class WMI4JavaUtil
/*    */ {
/*    */   public static String join(String delimiter, Iterable<?> parts) {
/*  6 */     StringBuilder joinedString = new StringBuilder();
/*    */     
/*  8 */     for (Object part : parts) {
/*  9 */       joinedString.append(part);
/* 10 */       joinedString.append(delimiter);
/*    */     } 
/* 12 */     joinedString.delete(joinedString.length() - delimiter.length(), joinedString.length());
/*    */     
/* 14 */     return joinedString.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\profesorfalken\wmi4java\WMI4JavaUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */