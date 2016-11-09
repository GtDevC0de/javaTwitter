/*
 * Copyright 2007 Yusuke Yamamoto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ikungolf.java.javatwitter.directmessage;

import twitter4j.*;

import java.util.List;

/**
 * Example application that gets all direct messages sent to the specified
 * account in twitter4j.properties.<br>
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public class GetDirectMessages {

    /**
     * Usage: java twitter4j.examples.directmessage.GetDirectMessages
     *
     * @param args String[]
     */
    private static Long messageId;
    private static Long tempMsgId;
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Start application. ");
        GetDirectMessages getDM = new GetDirectMessages();
        if (!getDM.isOldMessageId()) {
            System.out.println("#################### Not Old Message. ");
            String command = new String();
            while (!"exit".equals(command)) {
                command = new GetDirectMessages().getLastestMessage();
                System.out.println("Command is :" + command);
                Thread.sleep(30000);
            }
        }
        System.out.println("Start application. ");
    }
    
    private boolean isOldMessageId() {
        boolean rs = false;
        System.out.println("TEMP: " + tempMsgId);
        System.out.println("MSG ID: " + messageId);
        return rs;
    }
    
    private String getLastestMessage() {
        Twitter twitter = new TwitterFactory().getInstance();
        String msg = new String();
        try {
            Paging paging = new Paging(1);
            List<DirectMessage> messages;
            
            messages = twitter.getDirectMessages(paging);
            System.out.println(messages.size());
            DirectMessage dm = messages.get(0);
            
            System.out.println("Message: " + dm.getText());
            messageId = dm.getId();
            if (messageId != tempMsgId) {
                tempMsgId = messageId;
            }
            msg = dm.getText();
            
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get messages: " + te.getMessage());
            System.exit(-1);
        }
        
        return msg;
    }
}
