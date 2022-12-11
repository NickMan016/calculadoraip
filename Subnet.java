class Subnet extends Network{

    private Integer number_subnet;
    private Integer range;

    public Subnet(Integer number_subnet, Integer range) {
        this.number_subnet = number_subnet;
        this.range = range;
    }

    public void formatSubnet() {
        String[] networkSplit = this.getNetwork().split( "\\." );
        String[] maskSplit = this.getMask().split( "\\." );
        Integer position = 4;

        for (int i = 0; i < maskSplit.length; i++) {
            if ( Integer.parseInt( maskSplit[i] ) == 0){
                position = position - 1;
            }
        }

        networkSplit[position] = "" + ((range * number_subnet) - 64);
        setNetwork(networkSplit[0] + "." + networkSplit[1] + "." + networkSplit[2] + "." + networkSplit[3]);
        setStartHost(networkSplit[0] + "." + networkSplit[1] + "." + networkSplit[2] + "." + (Integer.parseInt(networkSplit[3]) + 1));

        for (int i = 0; i < maskSplit.length; i++) {
            if ( Integer.parseInt( maskSplit[i] ) == 0){
                networkSplit[i] = "255";
            }
        }
        networkSplit[position] = "" + ((range * number_subnet) - 1);

        setEndHost(networkSplit[0] + "." + networkSplit[1] + "." + networkSplit[2] + "." + (Integer.parseInt(networkSplit[3]) - 1));
        setBroadcast(networkSplit[0] + "." + networkSplit[1] + "." + networkSplit[2] + "." + networkSplit[3]);

    }
}
