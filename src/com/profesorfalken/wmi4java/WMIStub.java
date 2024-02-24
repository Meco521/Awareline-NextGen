package com.profesorfalken.wmi4java;

import java.util.List;

interface WMIStub {
  String listClasses(String paramString1, String paramString2) throws WMIException;
  
  String listObject(String paramString1, String paramString2, String paramString3) throws WMIException;
  
  String queryObject(String paramString1, List<String> paramList1, List<String> paramList2, String paramString2, String paramString3) throws WMIException;
  
  String listProperties(String paramString1, String paramString2, String paramString3) throws WMIException;
}


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\profesorfalken\wmi4java\WMIStub.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */