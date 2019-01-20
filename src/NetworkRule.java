public class NetworkRule {
    protected String direction;
    protected String protocol;
    protected int port;
    protected long ipAddress;
    protected long hashCode;

    //constructor for building rules
    public NetworkRule(String direction, String protocol, String port, String ipAddress) {
        this.direction = direction;
        this.protocol = protocol;
        this.port = Integer.parseInt(port);
        this.ipAddress = Long.parseLong(ipAddress.replaceAll("\\.", "")); //convert string ipAddress with periods to just a number
        this.hashCode =  31 * (this.ipAddress + this.port + direction.hashCode() + protocol.hashCode()); //get unique key from all the components
    }

    //constructor for building rules
    public NetworkRule(String direction, String protocol, String port, long ipAddress) {
        this.direction = direction;
        this.protocol = protocol;
        this.port = Integer.parseInt(port);
        this.ipAddress = ipAddress;
        this.hashCode =  31 * (this.ipAddress + this.port + direction.hashCode() + protocol.hashCode()); //get unique key from all the components
    }

    //constructor for building rules
    public NetworkRule(String direction, String protocol, int port, long ipAddress) {
        this.direction = direction;
        this.protocol = protocol;
        this.port = port;
        this.ipAddress = ipAddress;
        this.hashCode =  31 * (this.ipAddress + this.port + direction.hashCode() + protocol.hashCode()); //get unique key from all the components
    }

    //constructor for acceptPacket function
    public NetworkRule(String direction, String protocol, int port, String ipAddress) {
        this.direction = direction;
        this.protocol = protocol;
        this.port = port;
        this.ipAddress = Long.parseLong(ipAddress.replaceAll("\\.", "")); //convert string ipAddress with periods to just a number
        this.hashCode =  31 * (this.ipAddress + this.port + direction.hashCode() + protocol.hashCode()); //get unique key from all the components
    }
}