/*    */ package net.optifine.expr;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.PushbackReader;
/*    */ import java.io.Reader;
/*    */ import java.io.StringReader;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public class TokenParser
/*    */ {
/*    */   public static Token[] parse(String str) throws IOException, ParseException {
/* 14 */     Reader reader = new StringReader(str);
/* 15 */     PushbackReader pushbackreader = new PushbackReader(reader);
/* 16 */     List<Token> list = new ArrayList<>();
/*    */ 
/*    */     
/*    */     while (true) {
/* 20 */       int i = pushbackreader.read();
/*    */       
/* 22 */       if (i < 0) {
/*    */         
/* 24 */         Token[] atoken = list.<Token>toArray(new Token[0]);
/* 25 */         return atoken;
/*    */       } 
/*    */       
/* 28 */       char c0 = (char)i;
/*    */       
/* 30 */       if (!Character.isWhitespace(c0)) {
/*    */         
/* 32 */         TokenType tokentype = TokenType.getTypeByFirstChar(c0);
/*    */         
/* 34 */         if (tokentype == null)
/*    */         {
/* 36 */           throw new ParseException("Invalid character: '" + c0 + "', in: " + str);
/*    */         }
/*    */         
/* 39 */         Token token = readToken(c0, tokentype, pushbackreader);
/* 40 */         list.add(token);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private static Token readToken(char chFirst, TokenType type, PushbackReader pr) throws IOException {
/* 47 */     StringBuffer stringbuffer = new StringBuffer();
/* 48 */     stringbuffer.append(chFirst);
/*    */ 
/*    */     
/*    */     while (true) {
/* 52 */       int i = pr.read();
/*    */       
/* 54 */       if (i < 0) {
/*    */         break;
/*    */       }
/*    */ 
/*    */       
/* 59 */       char c0 = (char)i;
/*    */       
/* 61 */       if (!type.hasCharNext(c0)) {
/*    */         
/* 63 */         pr.unread(c0);
/*    */         
/*    */         break;
/*    */       } 
/* 67 */       stringbuffer.append(c0);
/*    */     } 
/*    */     
/* 70 */     return new Token(type, stringbuffer.toString());
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\expr\TokenParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */