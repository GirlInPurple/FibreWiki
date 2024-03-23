package xyz.blurple.fibrewiki.account;

import java.net.InetAddress;
import java.time.Instant;
import java.util.Random;

public class LoginCode {

    InetAddress IP;
    Instant NOW;
    String HEX;

    public LoginCode(InetAddress address) {
        this.IP = address;
        this.NOW = Instant.now();
        this.HEX = Integer.toHexString(new Random().nextInt());
    }
}
