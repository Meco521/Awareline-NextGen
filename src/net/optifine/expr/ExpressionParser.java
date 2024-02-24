/*     */ package net.optifine.expr;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Deque;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import net.minecraft.src.Config;
/*     */ 
/*     */ public class ExpressionParser {
/*     */   public ExpressionParser(IExpressionResolver expressionResolver) {
/*  14 */     this.expressionResolver = expressionResolver;
/*     */   }
/*     */   private final IExpressionResolver expressionResolver;
/*     */   
/*     */   public IExpressionFloat parseFloat(String str) throws ParseException {
/*  19 */     IExpression iexpression = parse(str);
/*     */     
/*  21 */     if (!(iexpression instanceof IExpressionFloat))
/*     */     {
/*  23 */       throw new ParseException("Not a float expression: " + iexpression.getExpressionType());
/*     */     }
/*     */ 
/*     */     
/*  27 */     return (IExpressionFloat)iexpression;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IExpressionBool parseBool(String str) throws ParseException {
/*  33 */     IExpression iexpression = parse(str);
/*     */     
/*  35 */     if (!(iexpression instanceof IExpressionBool))
/*     */     {
/*  37 */       throw new ParseException("Not a boolean expression: " + iexpression.getExpressionType());
/*     */     }
/*     */ 
/*     */     
/*  41 */     return (IExpressionBool)iexpression;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IExpression parse(String str) throws ParseException {
/*     */     try {
/*  49 */       Token[] atoken = TokenParser.parse(str);
/*     */       
/*  51 */       if (atoken == null)
/*     */       {
/*  53 */         return null;
/*     */       }
/*     */ 
/*     */       
/*  57 */       Deque<Token> deque = new ArrayDeque<>(Arrays.asList(atoken));
/*  58 */       return parseInfix(deque);
/*     */     
/*     */     }
/*  61 */     catch (IOException ioexception) {
/*     */       
/*  63 */       throw new ParseException(ioexception.getMessage(), ioexception);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private IExpression parseInfix(Deque<Token> deque) throws ParseException {
/*  69 */     if (deque.isEmpty())
/*     */     {
/*  71 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  75 */     List<IExpression> list = new LinkedList<>();
/*  76 */     List<Token> list1 = new LinkedList<>();
/*  77 */     IExpression iexpression = parseExpression(deque);
/*  78 */     checkNull(iexpression, "Missing expression");
/*  79 */     list.add(iexpression);
/*     */ 
/*     */     
/*     */     while (true) {
/*  83 */       Token token = deque.poll();
/*     */       
/*  85 */       if (token == null)
/*     */       {
/*  87 */         return makeInfix(list, list1);
/*     */       }
/*     */       
/*  90 */       if (token.getType() != TokenType.OPERATOR)
/*     */       {
/*  92 */         throw new ParseException("Invalid operator: " + token);
/*     */       }
/*     */       
/*  95 */       IExpression iexpression1 = parseExpression(deque);
/*  96 */       checkNull(iexpression1, "Missing expression");
/*  97 */       list1.add(token);
/*  98 */       list.add(iexpression1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private IExpression makeInfix(List<IExpression> listExpr, List<Token> listOper) throws ParseException {
/* 105 */     List<FunctionType> list = new LinkedList<>();
/*     */     
/* 107 */     for (Token token : listOper) {
/*     */       
/* 109 */       FunctionType functiontype = FunctionType.parse(token.getText());
/* 110 */       checkNull(functiontype, "Invalid operator: " + token);
/* 111 */       list.add(functiontype);
/*     */     } 
/*     */     
/* 114 */     return makeInfixFunc(listExpr, list);
/*     */   }
/*     */ 
/*     */   
/*     */   private IExpression makeInfixFunc(List<IExpression> listExpr, List<FunctionType> listFunc) throws ParseException {
/* 119 */     if (listExpr.size() != listFunc.size() + 1)
/*     */     {
/* 121 */       throw new ParseException("Invalid infix expression, expressions: " + listExpr.size() + ", operators: " + listFunc.size());
/*     */     }
/* 123 */     if (listExpr.size() == 1)
/*     */     {
/* 125 */       return listExpr.get(0);
/*     */     }
/*     */ 
/*     */     
/* 129 */     int i = Integer.MAX_VALUE;
/* 130 */     int j = Integer.MIN_VALUE;
/*     */     
/* 132 */     for (FunctionType functiontype : listFunc) {
/*     */       
/* 134 */       i = Math.min(functiontype.getPrecedence(), i);
/* 135 */       j = Math.max(functiontype.getPrecedence(), j);
/*     */     } 
/*     */     
/* 138 */     if (j >= i && j - i <= 10) {
/*     */       
/* 140 */       for (int k = j; k >= i; k--)
/*     */       {
/* 142 */         mergeOperators(listExpr, listFunc, k);
/*     */       }
/*     */       
/* 145 */       if (listExpr.size() == 1 && listFunc.isEmpty())
/*     */       {
/* 147 */         return listExpr.get(0);
/*     */       }
/*     */ 
/*     */       
/* 151 */       throw new ParseException("Error merging operators, expressions: " + listExpr.size() + ", operators: " + listFunc.size());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 156 */     throw new ParseException("Invalid infix precedence, min: " + i + ", max: " + j);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void mergeOperators(List<IExpression> listExpr, List<FunctionType> listFuncs, int precedence) throws ParseException {
/* 163 */     for (int i = 0; i < listFuncs.size(); i++) {
/*     */       
/* 165 */       FunctionType functiontype = listFuncs.get(i);
/*     */       
/* 167 */       if (functiontype.getPrecedence() == precedence) {
/*     */         
/* 169 */         listFuncs.remove(i);
/* 170 */         IExpression iexpression = listExpr.remove(i);
/* 171 */         IExpression iexpression1 = listExpr.remove(i);
/* 172 */         IExpression iexpression2 = makeFunction(functiontype, new IExpression[] { iexpression, iexpression1 });
/* 173 */         listExpr.add(i, iexpression2);
/* 174 */         i--;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private IExpression parseExpression(Deque<Token> deque) throws ParseException {
/*     */     FunctionType functiontype, functiontype1;
/* 181 */     Token token = deque.poll();
/* 182 */     checkNull(token, "Missing expression");
/*     */     
/* 184 */     switch (token.getType()) {
/*     */       
/*     */       case NUMBER:
/* 187 */         return makeConstantFloat(token);
/*     */       
/*     */       case IDENTIFIER:
/* 190 */         functiontype = getFunctionType(token, deque);
/*     */         
/* 192 */         if (functiontype != null)
/*     */         {
/* 194 */           return makeFunction(functiontype, deque);
/*     */         }
/*     */         
/* 197 */         return makeVariable(token);
/*     */       
/*     */       case BRACKET_OPEN:
/* 200 */         return makeBracketed(token, deque);
/*     */       
/*     */       case OPERATOR:
/* 203 */         functiontype1 = FunctionType.parse(token.getText());
/* 204 */         checkNull(functiontype1, "Invalid operator: " + token);
/*     */         
/* 206 */         if (functiontype1 == FunctionType.PLUS)
/*     */         {
/* 208 */           return parseExpression(deque);
/*     */         }
/* 210 */         if (functiontype1 == FunctionType.MINUS) {
/*     */           
/* 212 */           IExpression iexpression1 = parseExpression(deque);
/* 213 */           return makeFunction(FunctionType.NEG, new IExpression[] { iexpression1 });
/*     */         } 
/* 215 */         if (functiontype1 == FunctionType.NOT) {
/*     */           
/* 217 */           IExpression iexpression = parseExpression(deque);
/* 218 */           return makeFunction(FunctionType.NOT, new IExpression[] { iexpression });
/*     */         } 
/*     */         break;
/*     */     } 
/* 222 */     throw new ParseException("Invalid expression: " + token);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static IExpression makeConstantFloat(Token token) throws ParseException {
/* 228 */     float f = Config.parseFloat(token.getText(), Float.NaN);
/*     */     
/* 230 */     if (f == Float.NaN)
/*     */     {
/* 232 */       throw new ParseException("Invalid float value: " + token);
/*     */     }
/*     */ 
/*     */     
/* 236 */     return new ConstantFloat(f);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private FunctionType getFunctionType(Token token, Deque<Token> deque) throws ParseException {
/* 242 */     Token token1 = deque.peek();
/*     */     
/* 244 */     if (token1 != null && token1.getType() == TokenType.BRACKET_OPEN) {
/*     */       
/* 246 */       FunctionType enumfunctiontype1 = FunctionType.parse(token1.getText());
/* 247 */       checkNull(enumfunctiontype1, "Unknown function: " + token1);
/* 248 */       return enumfunctiontype1;
/*     */     } 
/*     */ 
/*     */     
/* 252 */     FunctionType functiontype = FunctionType.parse(token.getText());
/*     */     
/* 254 */     if (functiontype == null)
/*     */     {
/* 256 */       return null;
/*     */     }
/* 258 */     if (functiontype.getParameterCount(new IExpression[0]) > 0)
/*     */     {
/* 260 */       throw new ParseException("Missing arguments: " + functiontype);
/*     */     }
/*     */ 
/*     */     
/* 264 */     return functiontype;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IExpression makeFunction(FunctionType type, Deque<Token> deque) throws ParseException {
/* 271 */     if (type.getParameterCount(new IExpression[0]) == 0) {
/*     */       
/* 273 */       Token token = deque.peek();
/*     */       
/* 275 */       if (token == null || token.getType() != TokenType.BRACKET_OPEN)
/*     */       {
/* 277 */         return makeFunction(type, new IExpression[0]);
/*     */       }
/*     */     } 
/*     */     
/* 281 */     Token token1 = deque.poll();
/* 282 */     Deque<Token> deque2 = getGroup(deque, TokenType.BRACKET_CLOSE, true);
/* 283 */     IExpression[] aiexpression = parseExpressions(deque2);
/* 284 */     return makeFunction(type, aiexpression);
/*     */   }
/*     */ 
/*     */   
/*     */   private IExpression[] parseExpressions(Deque<Token> deque) throws ParseException {
/* 289 */     List<IExpression> list = new ArrayList<>();
/*     */ 
/*     */     
/*     */     while (true) {
/* 293 */       Deque<Token> deque2 = getGroup(deque, TokenType.COMMA, false);
/* 294 */       IExpression iexpression = parseInfix(deque2);
/*     */       
/* 296 */       if (iexpression == null) {
/*     */         
/* 298 */         IExpression[] aiexpression = list.<IExpression>toArray(new IExpression[0]);
/* 299 */         return aiexpression;
/*     */       } 
/*     */       
/* 302 */       list.add(iexpression);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static IExpression makeFunction(FunctionType type, IExpression[] args) throws ParseException {
/* 308 */     ExpressionType[] aexpressiontype = type.getParameterTypes(args);
/*     */     
/* 310 */     if (args.length != aexpressiontype.length)
/*     */     {
/* 312 */       throw new ParseException("Invalid number of arguments, function: \"" + type.getName() + "\", count arguments: " + args.length + ", should be: " + aexpressiontype.length);
/*     */     }
/*     */ 
/*     */     
/* 316 */     for (int i = 0; i < args.length; i++) {
/*     */       
/* 318 */       IExpression iexpression = args[i];
/* 319 */       ExpressionType expressiontype = iexpression.getExpressionType();
/* 320 */       ExpressionType expressiontype1 = aexpressiontype[i];
/*     */       
/* 322 */       if (expressiontype != expressiontype1)
/*     */       {
/* 324 */         throw new ParseException("Invalid argument type, function: \"" + type.getName() + "\", index: " + i + ", type: " + expressiontype + ", should be: " + expressiontype1);
/*     */       }
/*     */     } 
/*     */     
/* 328 */     if (type.getExpressionType() == ExpressionType.FLOAT)
/*     */     {
/* 330 */       return new FunctionFloat(type, args);
/*     */     }
/* 332 */     if (type.getExpressionType() == ExpressionType.BOOL)
/*     */     {
/* 334 */       return new FunctionBool(type, args);
/*     */     }
/* 336 */     if (type.getExpressionType() == ExpressionType.FLOAT_ARRAY)
/*     */     {
/* 338 */       return new FunctionFloatArray(type, args);
/*     */     }
/*     */ 
/*     */     
/* 342 */     throw new ParseException("Unknown function type: " + type.getExpressionType() + ", function: " + type.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IExpression makeVariable(Token token) throws ParseException {
/* 349 */     if (this.expressionResolver == null)
/*     */     {
/* 351 */       throw new ParseException("Model variable not found: " + token);
/*     */     }
/*     */ 
/*     */     
/* 355 */     IExpression iexpression = this.expressionResolver.getExpression(token.getText());
/*     */     
/* 357 */     if (iexpression == null)
/*     */     {
/* 359 */       throw new ParseException("Model variable not found: " + token);
/*     */     }
/*     */ 
/*     */     
/* 363 */     return iexpression;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IExpression makeBracketed(Token token, Deque<Token> deque) throws ParseException {
/* 370 */     Deque<Token> deque2 = getGroup(deque, TokenType.BRACKET_CLOSE, true);
/* 371 */     return parseInfix(deque2);
/*     */   }
/*     */ 
/*     */   
/*     */   private static Deque<Token> getGroup(Deque<Token> deque, TokenType tokenTypeEnd, boolean tokenEndRequired) throws ParseException {
/* 376 */     Deque<Token> deque3 = new ArrayDeque<>();
/* 377 */     int i = 0;
/* 378 */     Iterator<Token> iterator = deque.iterator();
/*     */     
/* 380 */     while (iterator.hasNext()) {
/*     */       
/* 382 */       Token token = iterator.next();
/* 383 */       iterator.remove();
/*     */       
/* 385 */       if (i == 0 && token.getType() == tokenTypeEnd)
/*     */       {
/* 387 */         return deque3;
/*     */       }
/*     */       
/* 390 */       deque3.add(token);
/*     */       
/* 392 */       if (token.getType() == TokenType.BRACKET_OPEN)
/*     */       {
/* 394 */         i++;
/*     */       }
/*     */       
/* 397 */       if (token.getType() == TokenType.BRACKET_CLOSE)
/*     */       {
/* 399 */         i--;
/*     */       }
/*     */     } 
/*     */     
/* 403 */     if (tokenEndRequired)
/*     */     {
/* 405 */       throw new ParseException("Missing end token: " + tokenTypeEnd);
/*     */     }
/*     */ 
/*     */     
/* 409 */     return deque3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkNull(Object obj, String message) throws ParseException {
/* 415 */     if (obj == null)
/*     */     {
/* 417 */       throw new ParseException(message);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\expr\ExpressionParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */