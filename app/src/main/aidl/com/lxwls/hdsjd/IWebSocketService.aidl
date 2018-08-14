// IWebSocketService.aidl
package com.lxwls.hdsjd;

// Declare any non-default types here with import statements

interface IWebSocketService {

   void setHost(String host);
   void sendMessage(String msg);
   void connect();
   void disconnect();
}
