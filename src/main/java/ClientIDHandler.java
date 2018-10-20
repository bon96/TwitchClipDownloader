import java.util.ArrayList;
import java.util.List;

/**
 * Tommi
 * Date: 16.10.2018
 * Time: 21.48
 */
public  class ClientIDHandler {
    private ClientIDHandler() {}

    static List<ClientID> clientIDList = new ArrayList<ClientID>();

    static {
        clientIDList.add(new ClientID("2qduubdv9se8zv3xcogrp4nf7g7nzo"));
        clientIDList.add(new ClientID("kimne78kx3ncx6brgo4mv6wki5h1ko"));
        clientIDList.add(new ClientID("b31o4btkqth5bzbvr9ub2ovr79umhh"));
        clientIDList.add(new ClientID("jzkbprff40iqj646a697cyrvl0zt2m6"));
        clientIDList.add(new ClientID("ohry5vn3qvc0gcyxnl18p17babqk8p"));
        clientIDList.add(new ClientID("r84i710q1xk50wmuuketk65fbego1j5"));
        clientIDList.add(new ClientID("3jh10527qkef249bh2gtynqby7w8m4"));
        clientIDList.add(new ClientID("isrm9f8baj2m5qpylrutcjl64gu26x"));
        clientIDList.add(new ClientID("wk86b4ucvw4qda1xrtf0y38m81prvf"));
        clientIDList.add(new ClientID("2uwsn83vf25p699odwsxuaug02iezh"));
        clientIDList.add(new ClientID("6imz0sx6jp1jl0vzsrkvcim65ggnbx"));
    }

    public static String sleepUntilClientID() throws InterruptedException {
        String clientID;
        boolean informed = false;
        while ((clientID = ClientIDHandler.getClientID()) == null) {
            if (!informed) {
                System.out.println("Waiting for clientID");
                informed = true;
            }
            Thread.sleep(1000);
        }
        return clientID;
    }

    public static String getClientID() {
        for (ClientID clientID : clientIDList) {
            String string;
            if ((string = clientID.getClientID()) != null) {
                return string;
            }
        }
        return null;
    }

    static class ClientID {
        int rateLimit = 30;
        int rateLimitResetTime = 60*1000;

        String clientID = null;
        List<Long> useTimes = new ArrayList<>();

        private String getClientID() {
            update();
            if (useTimes.size() < rateLimit) {
                useTimes.add(System.currentTimeMillis());
                return clientID;
            }
            return null;
        }

        private void update() {
            for (int i = 0; i < useTimes.size(); i++) {
                if ((useTimes.get(i) + rateLimitResetTime) < System.currentTimeMillis()) {
                    useTimes.remove(i);
                }
            }
        }

        public ClientID(String clientID) {
            this.clientID = clientID;
        }
    }



    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            try {
                System.out.println(Integer.toString(i) + " " + ClientIDHandler.sleepUntilClientID());
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }
}
