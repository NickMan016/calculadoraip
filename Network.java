class Network {
    private String network;
    private String mask;
    private String classNetwork;
    private String startHost;
    private String endHost;
    private String broadcast;
    private Integer hosts;

    public String getNetwork() {
        return network;
    }

    public String getMask() {
        return mask;
    }

    public String getClassNetwork() {
        return classNetwork;
    }

    public String getStartHost() {
        return startHost;
    }

    public String getEndHost() {
        return endHost;
    }

    public String getBroadcast() {
        return broadcast;
    }

    public Integer getHosts() {
        return hosts;
    }

    public void setNetwork( String network ) {
        this.network = network;
    }

    public void setMask( String mask ) {
        this.mask = mask;
    }

    public void setClassNetwork( String classNetwork ) {
        this.classNetwork = classNetwork;
    }

    public void setStartHost( String startHost) {
        this.startHost = startHost;
    }

    public void setEndHost( String endHost ) {
        this.endHost = endHost;
    }

    public void setBroadcast( String broadcast ) {
        this.broadcast = broadcast;
    }

    public void setHosts( Integer hosts ) {
        this.hosts = hosts;
    }

    public void calculateNetwork() {
        String[] networkSplit = this.network.split( "\\." );
        String[] maskSplit = this.mask.split( "\\." );
        Integer bits = 0;
        
        for (int i = 0; i < maskSplit.length; i++) {
            if ( Integer.parseInt( maskSplit[i] ) == 0 ){
                bits = bits + 8;
                
                networkSplit[i] = "255";
            }
        }

        setHosts( (int) Math.pow(2, bits) - 2 );
        setBroadcast( networkSplit[0] + "." + networkSplit[1] + "." + networkSplit[2] + ".255" );
        setStartHost( networkSplit[0] + "." + networkSplit[1] + "." + networkSplit[2] + ".1" );
        setEndHost( networkSplit[0] + "." + networkSplit[1] + "." + networkSplit[2] + ".254" );
    }

    public void calculateClassNetwork() {
        String[] networkSplit = this.network.split( "\\." );
        Integer networkSplit1 = Integer.parseInt( networkSplit[0]),
        networkSplit2 = Integer.parseInt( networkSplit[1]),
        networkSplit3 = Integer.parseInt( networkSplit[2]),
        networkSplit4 = Integer.parseInt( networkSplit[3]);

        if ( networkSplit1 >= 0 && networkSplit2 >= 0 && networkSplit3 >= 0 && networkSplit4 >= 0 && networkSplit1 <= 127 && networkSplit2 <= 255 && networkSplit3 <= 255 && networkSplit4 <= 255 ) {
            setClassNetwork( "A" );
        }

        if ( networkSplit1 >= 128 && networkSplit2 >= 0 && networkSplit3 >= 0 && networkSplit4 >= 0 && networkSplit1 <= 191 && networkSplit2 <= 255 && networkSplit3 <= 255 && networkSplit4 <= 255 ) {
            setClassNetwork( "B" );
        }

        if ( networkSplit1 >= 192 && networkSplit2 >= 0 && networkSplit3 >= 0 && networkSplit4 >= 0 && networkSplit1 <= 223 && networkSplit2 <= 255 && networkSplit3 <= 255 && networkSplit4 <= 255 ) {
            setClassNetwork( "C" );
        }
    }
}