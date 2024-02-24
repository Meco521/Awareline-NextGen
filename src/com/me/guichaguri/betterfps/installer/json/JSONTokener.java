/*     */ package com.me.guichaguri.betterfps.installer.json;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JSONTokener
/*     */ {
/*     */   private long character;
/*     */   private boolean eof;
/*     */   private long index;
/*     */   private long line;
/*     */   private char previous;
/*     */   private final Reader reader;
/*     */   private boolean usePrevious;
/*     */   
/*     */   public JSONTokener(Reader reader) {
/*  54 */     this.reader = reader.markSupported() ? reader : new BufferedReader(reader);
/*     */ 
/*     */     
/*  57 */     this.eof = false;
/*  58 */     this.usePrevious = false;
/*  59 */     this.previous = Character.MIN_VALUE;
/*  60 */     this.index = 0L;
/*  61 */     this.character = 1L;
/*  62 */     this.line = 1L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JSONTokener(InputStream inputStream) throws JSONException {
/*  72 */     this(new InputStreamReader(inputStream));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JSONTokener(String s) {
/*  82 */     this(new StringReader(s));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void back() throws JSONException {
/*  92 */     if (this.usePrevious || this.index <= 0L) {
/*  93 */       throw new JSONException("Stepping back two steps is not supported");
/*     */     }
/*  95 */     this.index--;
/*  96 */     this.character--;
/*  97 */     this.usePrevious = true;
/*  98 */     this.eof = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int dehexchar(char c) {
/* 110 */     if (c >= '0' && c <= '9') {
/* 111 */       return c - 48;
/*     */     }
/* 113 */     if (c >= 'A' && c <= 'F') {
/* 114 */       return c - 55;
/*     */     }
/* 116 */     if (c >= 'a' && c <= 'f') {
/* 117 */       return c - 87;
/*     */     }
/* 119 */     return -1;
/*     */   }
/*     */   
/*     */   public boolean end() {
/* 123 */     return (this.eof && !this.usePrevious);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean more() throws JSONException {
/* 134 */     next();
/* 135 */     if (end()) {
/* 136 */       return false;
/*     */     }
/* 138 */     back();
/* 139 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char next() throws JSONException {
/*     */     int c;
/* 150 */     if (this.usePrevious) {
/* 151 */       this.usePrevious = false;
/* 152 */       c = this.previous;
/*     */     } else {
/*     */       try {
/* 155 */         c = this.reader.read();
/* 156 */       } catch (IOException exception) {
/* 157 */         throw new JSONException(exception);
/*     */       } 
/*     */       
/* 160 */       if (c <= 0) {
/* 161 */         this.eof = true;
/* 162 */         c = 0;
/*     */       } 
/*     */     } 
/* 165 */     this.index++;
/* 166 */     if (this.previous == '\r') {
/* 167 */       this.line++;
/* 168 */       this.character = (c == 10) ? 0L : 1L;
/* 169 */     } else if (c == 10) {
/* 170 */       this.line++;
/* 171 */       this.character = 0L;
/*     */     } else {
/* 173 */       this.character++;
/*     */     } 
/* 175 */     this.previous = (char)c;
/* 176 */     return this.previous;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char next(char c) throws JSONException {
/* 189 */     char n = next();
/* 190 */     if (n != c) {
/* 191 */       throw syntaxError("Expected '" + c + "' and instead saw '" + n + "'");
/*     */     }
/*     */     
/* 194 */     return n;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String next(int n) throws JSONException {
/* 207 */     if (n == 0) {
/* 208 */       return "";
/*     */     }
/*     */     
/* 211 */     char[] chars = new char[n];
/* 212 */     int pos = 0;
/*     */     
/* 214 */     while (pos < n) {
/* 215 */       chars[pos] = next();
/* 216 */       if (end()) {
/* 217 */         throw syntaxError("Substring bounds error");
/*     */       }
/* 219 */       pos++;
/*     */     } 
/* 221 */     return new String(chars);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char nextClean() throws JSONException {
/*     */     char c;
/*     */     do {
/* 233 */       c = next();
/* 234 */     } while (c != '\000' && c <= ' ');
/* 235 */     return c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String nextString(char quote) throws JSONException {
/* 255 */     StringBuilder sb = new StringBuilder();
/*     */     while (true) {
/* 257 */       char c = next();
/* 258 */       switch (c) {
/*     */         case '\000':
/*     */         case '\n':
/*     */         case '\r':
/* 262 */           throw syntaxError("Unterminated string");
/*     */         case '\\':
/* 264 */           c = next();
/* 265 */           switch (c) {
/*     */             case 'b':
/* 267 */               sb.append('\b');
/*     */               continue;
/*     */             case 't':
/* 270 */               sb.append('\t');
/*     */               continue;
/*     */             case 'n':
/* 273 */               sb.append('\n');
/*     */               continue;
/*     */             case 'f':
/* 276 */               sb.append('\f');
/*     */               continue;
/*     */             case 'r':
/* 279 */               sb.append('\r');
/*     */               continue;
/*     */             case 'u':
/* 282 */               sb.append((char)Integer.parseInt(next(4), 16));
/*     */               continue;
/*     */             case '"':
/*     */             case '\'':
/*     */             case '/':
/*     */             case '\\':
/* 288 */               sb.append(c);
/*     */               continue;
/*     */           } 
/* 291 */           throw syntaxError("Illegal escape.");
/*     */       } 
/*     */ 
/*     */       
/* 295 */       if (c == quote) {
/* 296 */         return sb.toString();
/*     */       }
/* 298 */       sb.append(c);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String nextTo(char delimiter) throws JSONException {
/* 312 */     StringBuilder sb = new StringBuilder();
/*     */     while (true) {
/* 314 */       char c = next();
/* 315 */       if (c == delimiter || c == '\000' || c == '\n' || c == '\r') {
/* 316 */         if (c != '\000') {
/* 317 */           back();
/*     */         }
/* 319 */         return sb.toString().trim();
/*     */       } 
/* 321 */       sb.append(c);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String nextTo(String delimiters) throws JSONException {
/* 335 */     StringBuilder sb = new StringBuilder();
/*     */     while (true) {
/* 337 */       char c = next();
/* 338 */       if (delimiters.indexOf(c) >= 0 || c == '\000' || c == '\n' || c == '\r') {
/*     */         
/* 340 */         if (c != '\000') {
/* 341 */           back();
/*     */         }
/* 343 */         return sb.toString().trim();
/*     */       } 
/* 345 */       sb.append(c);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object nextValue() throws JSONException {
/* 358 */     char c = nextClean();
/*     */ 
/*     */     
/* 361 */     switch (c) {
/*     */       case '"':
/*     */       case '\'':
/* 364 */         return nextString(c);
/*     */       case '{':
/* 366 */         back();
/* 367 */         return new JSONObject(this);
/*     */       case '[':
/* 369 */         back();
/* 370 */         return new JSONArray(this);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 382 */     StringBuilder sb = new StringBuilder();
/* 383 */     while (c >= ' ' && ",:]}/\\\"[{;=#".indexOf(c) < 0) {
/* 384 */       sb.append(c);
/* 385 */       c = next();
/*     */     } 
/* 387 */     back();
/*     */     
/* 389 */     String string = sb.toString().trim();
/* 390 */     if (string.isEmpty()) {
/* 391 */       throw syntaxError("Missing value");
/*     */     }
/* 393 */     return JSONObject.stringToValue(string);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char skipTo(char to) throws JSONException {
/*     */     char c;
/*     */     try {
/* 408 */       long startIndex = this.index;
/* 409 */       long startCharacter = this.character;
/* 410 */       long startLine = this.line;
/* 411 */       this.reader.mark(1000000);
/*     */       do {
/* 413 */         c = next();
/* 414 */         if (c == '\000') {
/* 415 */           this.reader.reset();
/* 416 */           this.index = startIndex;
/* 417 */           this.character = startCharacter;
/* 418 */           this.line = startLine;
/* 419 */           return c;
/*     */         } 
/* 421 */       } while (c != to);
/* 422 */     } catch (IOException exception) {
/* 423 */       throw new JSONException(exception);
/*     */     } 
/* 425 */     back();
/* 426 */     return c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JSONException syntaxError(String message) {
/* 437 */     return new JSONException(message + toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 447 */     return " at " + this.index + " [character " + this.character + " line " + this.line + "]";
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\guichaguri\betterfps\installer\json\JSONTokener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */