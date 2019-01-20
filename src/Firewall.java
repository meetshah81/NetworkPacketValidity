import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Firewall {
    static HashSet<Long> set = new HashSet<Long>();

    public static void main(String[] args) {
        try {
            Firewall f = new Firewall("networkrules.csv");
            //boolean test1 = f.acceptPacket("outbound", "tcp", 20000, "192.168.10.11");
            boolean test1 = f.accept_packet("inbound", "tcp",80,"192.168.1.2");
            boolean test2 = f.accept_packet("inbound", "udp",53,"192.168.1.2");
            boolean test3 = f.accept_packet("outbound", "tcp", 10234, "192.168.10.11");
            boolean test4 = f.accept_packet("inbound", "tcp", 81, "192.168.1.2");
            boolean test5 = f.accept_packet("inbound", "udp", 24, "52.12.48.92");
            System.out.println(test1);
            System.out.println(test2);
            System.out.println(test3);
            System.out.println(test4);
            System.out.println(test5);

        }catch (FileNotFoundException e) {
            System.out.println("Exception occurred");
        }
    }

    public Firewall(String file) throws FileNotFoundException{
        //read csv line by line
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            for(String line; (line = br.readLine()) != null;) {

                //split line to individual components
                String [] rule = line.split(",");

                if (rule[2].contains("-")) { // port w/ range
                    String [] portRanges = rule[2].split("-");
                    int portMin = Integer.parseInt(portRanges[0]);
                    int portMax = Integer.parseInt(portRanges[1]);
                    int portRange = portMax - portMin;

                    if (rule[3].contains("-")) {// port w/ range, ipAddress w/ a range
                        String [] ipAddressRanges = rule[3].split("-");
                        long ipAddressMin = Long.parseLong(ipAddressRanges[0].replaceAll("\\.", ""));
                        long ipAddressMax = Long.parseLong(ipAddressRanges[1].replaceAll("\\.", ""));
                        long ipRange = ipAddressMax - ipAddressMin;

                        //iterate through all possible ports and ips and add them to map
                        for (int i = 0; i <= portRange; i++) {
                            for (int j = 0; j <= ipRange; j++) {
                                NetworkRule currRule = new NetworkRule(rule[0], rule[1], portMin + i, ipAddressMin + j);
                                set.add(currRule.hashCode);
                            }
                        }

                    }
                    else { // port w/ range, ipAddress w/ NO range

                        //iterate through all possible ports add them to map
                        for (int i = 0; i <= portRange; i++) {
                            NetworkRule currRule = new NetworkRule(rule[0],rule[1], portMin + i, rule[3]);
                            set.add(currRule.hashCode);
                        }
                    }

                }
                else { // port w/ NO range
                    if (rule[3].contains("-")) {// port w/ NO range, ipAddress w/ a range
                        String [] ipAddressRanges = rule[3].split("-");
                        long ipAddressMin = Long.parseLong(ipAddressRanges[0].replaceAll("\\.", ""));
                        long ipAddressMax = Long.parseLong(ipAddressRanges[1].replaceAll("\\.", ""));
                        long ipRange = ipAddressMax - ipAddressMin;

                        //iterate through all possible ips add them to map
                        for (int i = 0; i <= ipRange; i++) {
                            NetworkRule currRule = new NetworkRule(rule[0],rule[1],rule[2], ipAddressMin + i);
                            set.add(currRule.hashCode);
                        }
                    }
                    else { // port w/ NO range, ipAddress w/ NO range
                        NetworkRule currRule = new NetworkRule(rule[0],rule[1],rule[2],rule[3]);
                        set.add(currRule.hashCode);
                    }

                }

            }
        }
        catch (IOException e) {
            System.out.println("Exception occurred");
        }

    }

    public boolean accept_packet(String direction, String protocol, int port, String ipAddress) {
        NetworkRule rule = new NetworkRule(direction, protocol, port, ipAddress);
        if (this.set.contains(rule.hashCode)) {
            return true;
        }
        else {
            return false;
        }

    } //wrapper class to contain a network rule used for hashing key

}

