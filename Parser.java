import java.awt.*;
import java.applet.*;
public class Parser {
    private static class Node {
        String me;
        Node[] children;

        public Node(String url, Node[] childs) {
            me = url;
            children = childs;
        }
        public Node(String url) {
            me = url;
            children = new Node[0];
        }
        public Node[] getChild() {
            if(children == null)
                return new Node[0];
            return children;
        }

        public String getNode() {
            return me;
        }
    }

    public static int removeDuplicates(String a[]) {
        int n = a.length;
        if (n == 0 || n == 1) {
            return n;
        }
        // creating another array for only storing
        // the unique elements
        String[] temp = new String[n];
        int j = 0;
        for (int i = 0; i < n - 1; i++) {
            if (a[i].compareTo(a[i + 1]) != 0) {
                temp[j++] = a[i];
            }
        }
        temp[j++] = a[n - 1];
        // Changing the original array
        for (int i = 0; i < j; i++) {
            a[i] = temp[i];
        }
        return j;
    }
    public static void printarr(String[] in){
        for(String s: in)
            System.out.println(s);
    }
    /**
     * returns Node array of entries that share the same begininning as url
     * all matches are shortened to their next "/" after the matching url
     * remove duplicates and turn the string list into Nodes
     * each node may have children so this method is called recursively
     * 
     * final level behaviour is like url = https://www.x.com/camel/reads/
     *  and the only one that matches ends  in x.html (no forward slash)
     * so program mustn't shorten to forward slash bc that would make it the same 
     * @param url source url
     * @param list all urls
     * @return Node array of selected urls whose beginning are the same as url but length is longer
     */
    public static Node[] childHelper(String url, String[] list) { 
        Node[] output;
        int j = 0;
        String[] vurl = new String[list.length];
        if(url == null)
            return null;
        for (int i = 0; i < list.length; i++) { // get all urls that have parent url as their begining
            if (list[i].contains(url) && list[i].indexOf("/", url.length() + 1) != -1) {
                vurl[j++] = list[i].substring(0, list[i].indexOf("/", url.length())+1);
                //System.out.println("url = "+url+" list[i] = "+list[i]+" scrapped: "+vurl[j-1]);
            }else if(list[i].contains(url) && list[i].compareTo(url) != 0){
                vurl[j++] = list[i];
                //System.out.println("url = "+url+" list[i] = "+list[i]+" scrapped: "+vurl[j-1]);
            }
        }
        if (j == 0) // base case there are no URLs that contain [a very specific URL]
            return null;
        //get rid of null slots and duplicates
        String[] temp = new String[j];
        for (int i = 0; i < j; i++) {
            temp[i] = vurl[i];
        }        
        String[] urlStrings = new String[removeDuplicates(temp)];
        for(int i = 0; i < urlStrings.length; i++){
            urlStrings[i] = temp[i];
        }
        // end array manipulation
        output = new Node[urlStrings.length];
        for (int i = 0; i < urlStrings.length; i++) {
            output[i] = new Node(urlStrings[i], childHelper(urlStrings[i], list));
            //System.out.println("node: "+output[i].getNode()+" has "+output[i].getChild().length+" children");
        }
        return output;
    }//END HELPER

    public static void insertionSort(String[] arr) {
        for (int x = 1; x < arr.length; x++) {
            String current = arr[x];
            int y = x - 1;
            while (y >= 0 && current.compareTo(arr[y]) < 0) {
                arr[y + 1] = arr[y];
                y--;
            }
            arr[y + 1] = current;
        }
    }
    public static void main(String[] args){
        //testing
        /*
        String[] input = { "https://www.nintendo.com/",
                "https://www.smashbros.com/en_US/index.html",
                "https://www.nintendo.com/switch/",
                "https://www.smashbros.com/en_US/index.html",
                "https://www.smashbros.com/en_US/about/index.html",
                "https://www.smashbros.com/en_US/fighter/index.html",
                "https://www.smashbros.com/en_US/howtoplay/index.html",
                "https://www.smashbros.com/en_US/stage/index.html",
                "https://www.smashbros.com/en_US/item/index.html",
                "https://www.smashbros.com/en_US/sound/index.html",
                "https://www.smashbros.com/en_US/movie/index.html",
                "https://www.smashbros.com/en_US/blog/index.html",
                "https://www.smashbros.com/en_US/howtoplay/basic.html#",
                "https://www.smashbros.com/en_US/howtoplay/basic.html#",
                "https://smashbros.nintendo.com/buy/",
                "https://www.smashbros.com/en_US/howtoplay/basic.html",
                "https://www.smashbros.com/en_US/howtoplay/mode.html",
                "https://www.smashbros.com/en_US/howtoplay/spirits.html",
                "https://www.smashbros.com/en_US/howtoplay/technique.html",
                "https://www.smashbros.com/en_US/howtoplay/communication.html",
                "https://www.smashbros.com/en_US/howtoplay/amiibo.html",
                "https://www.smashbros.com/en_US/howtoplay/index.html",
                "https://www.smashbros.com/en_US/index.html",
                "https://twitter.com/share?url=https%3A%2F%2Fwww.smashbros.com%2Fen_US%2Fhowtoplay%2Fbasic.html&text=How%20to%20Smash%20%7C%20Super%20Smash%20Bros.%20Ultimate%20for%20the%20Nintendo%20Switch%20System%20%7C%20Official%20Site",
                "https://www.facebook.com/sharer/sharer.php?u=https%3A%2F%2Fwww.smashbros.com%2Fen_US%2Fhowtoplay%2Fbasic.html",
                "https://www.smashbros.com/en_US/howtoplay/basic.html#top",
                "https://www.smashbros.com/en_US/index.html",
                "https://www.smashbros.com/en_US/about/index.html",
                "https://www.smashbros.com/en_US/fighter/index.html",
                "https://www.smashbros.com/en_US/howtoplay/index.html",
                "https://www.smashbros.com/en_US/stage/index.html",
                "https://www.smashbros.com/en_US/item/index.html",
                "https://www.smashbros.com/en_US/sound/index.html",
                "https://www.smashbros.com/en_US/movie/index.html",
                "https://www.smashbros.com/en_US/blog/index.html",
                "http://www.esrb.org/ratings/ratings_guide.jsp",
                "http://www.esrb.org/confirm/nintendo-confirmation.aspx",
                "https://www.nintendo.com/",
                "https://www.nintendo.com/privacy-policy",
                "https://www.nintendo.com/interest-based-ads-policy",
                "https://www.nintendo.com/terms-of-use/ncl",
                "https://www.smashbros.com/en_US/howtoplay/basic.html#",
                "https://www.smashbros.com/wiiu-3ds/us/index.html",
                "https://www.nintendo.com/interest-based-ads-policy/",
                "https://www.nintendo.com/privacy-policy",
                "https://www.nintendo.com/privacy-policy",
                "https://www.smashbros.com/en_US/howtoplay/basic.html#",
                "https://www.smashbros.com/en_US/howtoplay/basic.html#",
                "https://www.smashbros.com/en_US/howtoplay/basic.html#",
                "https://www.smashbros.com/en_US/howtoplay/basic.html#",
                "https://www.smashbros.com/en_US/howtoplay/basic.html#",
                "https://www.smashbros.com/en_US/howtoplay/basic.html#",
                "https://www.smashbros.com/en_US/howtoplay/basic.html#",
                "https://www.smashbros.com/en_US/howtoplay/basic.html#",
                "https://www.smashbros.com/en_US/howtoplay/basic.html#",
                "https://www.smashbros.com/en_US/howtoplay/basic.html#",
                "https://www.smashbros.com/en_US/howtoplay/basic.html#",
                "https://www.smashbros.com/en_US/howtoplay/basic.html#",
                "https://www.smashbros.com/en_US/howtoplay/basic.html#",
                "https://my.nintendo.com/",
                "https://accounts.nintendo.com/",
                "https://www.smashbros.com/en_US/howtoplay/basic.html" };
            */ 
        //implementation
        String[] input = args; 
        Node out = output(input);
        System.out.println("PRINTING NODES");
        recursePrint(out.getChild());
        //recurse print is also how to access the nodes in order!
    }

    public static Node output(String[] urls) {
        //parse data by sorting alphabetically and removing elements that are identical
        String[] input = urls;
        insertionSort(input);
        String[] shorten = new String[removeDuplicates(input)];
        for (int k = 0; k < shorten.length; k++)
            shorten[k] = input[k];
        input = shorten;
        //input is now an ordered list of unique urls
        String[] temp = new String[input.length];
        for (int i = 0; i < input.length; i++) {
            temp[i] = input[i].substring(0, input[i].indexOf("/", 9));
        }
        int stemp = removeDuplicates(temp);
        shorten = new String[stemp];
        Node[] output = new Node[stemp];
        for (int i = 0; i < stemp; i++) {
            output[i] = new Node(temp[i], childHelper(temp[i], input));
        }
        Node out = new Node("for Kaush",output);
        return out;
    }

    public static void recursePrint(Node[] nodes) {
        if (nodes == null)
            return;
        for (int i = 0; i < nodes.length; i++) {
            if(nodes[i] == null)
                continue;
            System.out.println(nodes[i].getNode());
            System.out.println("has children:");
            recursePrint(nodes[i].getChild());
            System.out.println("");
        }
    }
}