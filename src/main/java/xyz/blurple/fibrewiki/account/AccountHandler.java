package xyz.blurple.fibrewiki.account;

import xyz.blurple.fibrewiki.FibreWiki;

import java.net.InetAddress;
import java.util.*;

public class AccountHandler implements Runnable {

    public static HashMap<UUID, List<InetAddress>> savedAccounts = new HashMap<>();
    public static List<LoginCode> stagedAccounts = new ArrayList<>();

    @Override
    public void run() {
        FibreWiki.LOGGER.info("Starting Account System...");
    }

    public String genNewCode(InetAddress address) {
        LoginCode lc = new LoginCode(address);
        stagedAccounts.add(lc);
        return lc.HEX;
    }

    public static void saveAccount(UUID player, InetAddress address) {
        List<InetAddress> addressList = savedAccounts.get(player);
        addressList.add(address);
        savedAccounts.put(player, addressList);
    }
}
