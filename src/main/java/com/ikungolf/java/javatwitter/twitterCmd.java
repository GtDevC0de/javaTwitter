/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ikungolf.java.javatwitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 *
 * @author 483671
 */
public class twitterCmd {

    private static Long messageId;
    private static Long tempMsgId;

    public static void main(String[] args) throws InterruptedException {
//        System.out.println("Hello, welcome to my first java application.");
//        System.out.println("");
//        String s = new String();
//        while (!"exit".equals(s)) {
//            try {
//                BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
//                s = bufferReader.readLine();
//                System.out.println(": " + s);
//                twitterCmd cmd = new twitterCmd();
//                String ip = cmd.getRemoteIpAddress();
//                System.out.println("IP Address: " + ip);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        twitterCmd getDM = new twitterCmd();
        if (args == null) {
            System.out.println(" ========== Start application. ");
            System.out.println("Showing @isongsee11's mentions.");

            if (!getDM.isOldMessageId()) {
                System.out.println("#################### Not Old Message. ");
                String command = new String();
                while (!"exit".equals(command)) {
                    Thread.sleep(15000);
                    Status s = new twitterCmd().getLastestMessage();
                    command = s.getText();
                    command = command.replace("@isongsee11", "");
                    command = command.replaceAll("\\s+", "");
                    System.out.println(" ##### command[" + command + "]");
                    if ("ipaddress".equals(command)) {
                        String ipAdd = getDM.getRemoteIpAddress();
                        System.out.println("IP Address: " + ipAdd);
                        String replied = "@isongsee11 >> my ipaddress " + ipAdd;
                        getDM.updateStatus(replied);
                    }

                    if ("hello".equals(command)) {
                        String msg = "Hi, how are you ?";
                        String replied = "@" + s.getUser().getScreenName() + " - " + msg;
                        System.out.println(">" + replied);
                        getDM.updateStatus(replied);
                    }
                }
            }
            System.out.println("  ========== End application. ");
        } else {
            if (args[0] == "cron") {
                System.out.println("Command crom:");
                Date date = new Date();
                String dateStr = String.format(" Date/Time : %tc", date);
                String ipAdd = getDM.getRemoteIpAddress();
                System.out.println("IP Address: " + ipAdd);
                String replied = "@isongsee11 >> my ipaddress " + ipAdd + " at " + dateStr;
                System.out.println("msg: " + replied);
                getDM.updateStatus(replied);
                System.out.println("Done.");
            }
        }
    }

    public boolean isOldMessageId() {
        boolean rs = false;
        System.out.println("TEMP: " + tempMsgId);
        System.out.println("MSG ID: " + messageId);
        return rs;
    }

    public Status getLastestMessage() {
        String msg = new String();
        Status s = null;
        // gets Twitter instance with default credentials
        Twitter twitter = new TwitterFactory().getInstance();
        try {
            User user = twitter.verifyCredentials();
            List<Status> statuses = twitter.getMentionsTimeline();

            s = statuses.get(0);
            msg = s.getText();
            System.out.println("Message: " + s.getText());
//            for (Status status : statuses) {
//                System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
//            }
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        }

        return s;
    }

    public String getRemoteIpAddress() {
        String ip = new String();
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));

            ip = in.readLine(); //you get the IP as a String
            System.out.println(ip);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ip;
    }

    public void updateStatus(String msg) {
        if (msg == null) {
            System.out.println("Usage: java twitter4j.examples.tweets.UpdateStatus [text]");
            System.exit(-1);
        }
        try {
            Twitter twitter = new TwitterFactory().getInstance();
            try {
                // get request token.
                // this will throw IllegalStateException if access token is already available
                RequestToken requestToken = twitter.getOAuthRequestToken();
                System.out.println("Got request token.");
                System.out.println("Request token: " + requestToken.getToken());
                System.out.println("Request token secret: " + requestToken.getTokenSecret());
                AccessToken accessToken = null;

                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                while (null == accessToken) {
                    System.out.println("Open the following URL and grant access to your account:");
                    System.out.println(requestToken.getAuthorizationURL());
                    System.out.print("Enter the PIN(if available) and hit enter after you granted access.[PIN]:");
                    String pin = br.readLine();
                    try {
                        if (pin.length() > 0) {
                            accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                        } else {
                            accessToken = twitter.getOAuthAccessToken(requestToken);
                        }
                    } catch (TwitterException te) {
                        if (401 == te.getStatusCode()) {
                            System.out.println("Unable to get the access token.");
                        } else {
                            te.printStackTrace();
                        }
                    }
                }
                System.out.println("Got access token.");
                System.out.println("Access token: " + accessToken.getToken());
                System.out.println("Access token secret: " + accessToken.getTokenSecret());
            } catch (IllegalStateException ie) {
                // access token is already available, or consumer key/secret is not set.
                if (!twitter.getAuthorization().isEnabled()) {
                    System.out.println("OAuth consumer key/secret is not set.");
                    System.exit(-1);
                }
            }
            Status status = twitter.updateStatus(msg);
            System.out.println("Successfully updated the status to [" + status.getText() + "].");
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("Failed to read the system input.");
            System.exit(-1);
        }
    }
}
