/*    */ package com.profesorfalken.jpowershell;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PowerShellResponse
/*    */ {
/*    */   private final boolean error;
/*    */   private final String commandOutput;
/*    */   private final boolean timeout;
/*    */   
/*    */   PowerShellResponse(boolean isError, String commandOutput, boolean timeout) {
/* 31 */     this.error = isError;
/* 32 */     this.commandOutput = commandOutput;
/* 33 */     this.timeout = timeout;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isError() {
/* 45 */     return this.error;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandOutput() {
/* 54 */     return this.commandOutput;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isTimeout() {
/* 63 */     return this.timeout;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\profesorfalken\jpowershell\PowerShellResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */