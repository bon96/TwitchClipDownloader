import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URL;
import java.util.Scanner;

/**
 * Tommi
 * Date: 16.10.2018
 * Time: 21.18
 */

public class TwitchClipDownloader {
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static String path = "./";


    private static String getClip(String url) {
        try {
            String clipname = "https://api.twitch.tv/kraken/clips/" + url.split(".tv/")[1];
            String source = okHttpClient.newCall(new Request.Builder().url(clipname)
                    .addHeader("Client-ID", ClientIDHandler.getClientID())
                    .addHeader("Accept", "application/vnd.twitchtv.v5+json")
                    .build()).execute().body().string();

            String string = source.split("clips-media-assets2.twitch.tv/")[1].split("-preview-")[0];
            return "https://clips-media-assets2.twitch.tv/" + string + ".mp4";
        } catch (Exception e) {
            System.out.println("Failed to get clip url for " + url + " - " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private static boolean downloadURL(String url, String name) {
        File file = new File(path + name + ".mp4");
        try {
            FileUtils.copyURLToFile(new URL(url), file);
        } catch (Exception e) {
            System.out.println("Failed to download " + url + " - " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        System.out.println("Successfully downloaded " + url + " to " + file.getAbsolutePath());
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n");
        System.out.println(ascii);

        System.out.println(
                        "paste Twitch clip URLs below and press enter to start download.\n" +
                        "Clips will be downloaded by default to the current folder.\n" +
                        "To change the folder use the command setpath.\n" +
                        "When finished type quit, q or exit to close the program.");

        System.out.println("\n");
        while(true) {
            System.out.print("> ");
            String next = scanner.nextLine();

            if (!next.isEmpty()) {
                if (next.equalsIgnoreCase("quit") || next.equalsIgnoreCase("q") || next.equalsIgnoreCase("exit")) {
                    System.out.println("Thank you for using T-DOWN!");
                    break;
                }

                if (next.contains("setpath")) {
                    try {
                        path = next.split("setpath ")[1];
                        System.out.println("Path set to " + path);
                    } catch (Exception e) {
                        System.out.println("Failed to set path");
                    }
                    continue;
                }

                String clipUrl = getClip(next);
                if (clipUrl != null) {
                    downloadURL(clipUrl, next.split(".tv/")[1]);
                }
            }
        }
    }

    public static void main2(String[] args) {
        System.out.println(getClip("https://clips.twitch.tv/SpotlessBlueSpaghettiWholeWheat"));
    }

    private static String ascii =
            " /$$$$$$$$         /$$$$$$$   /$$$$$$  /$$      /$$ /$$   /$$\n" +
            "|__  $$__/        | $$__  $$ /$$__  $$| $$  /$ | $$| $$$ | $$\n" +
            "   | $$           | $$  \\ $$| $$  \\ $$| $$ /$$$| $$| $$$$| $$\n" +
            "   | $$    /$$$$$$| $$  | $$| $$  | $$| $$/$$ $$ $$| $$ $$ $$\n" +
            "   | $$   |______/| $$  | $$| $$  | $$| $$$$_  $$$$| $$  $$$$\n" +
            "   | $$           | $$  | $$| $$  | $$| $$$/ \\  $$$| $$\\  $$$\n" +
            "   | $$           | $$$$$$$/|  $$$$$$/| $$/   \\  $$| $$ \\  $$\n" +
            "   |__/           |_______/  \\______/ |__/     \\__/|__/  \\__/\n" +
            "                                                             \n" +
            "                                                             \n" +
            "                                                             ";
}
