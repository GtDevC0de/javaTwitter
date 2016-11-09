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
 * @author ikungolf
 */
public class ipBot {

    public static void main(String[] args) throws InterruptedException {

        twitterCmd getDM = new twitterCmd();

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
