class Calculadora {
    private String address;
    private String netmask;
    private String netmaskBits;
    private String newNetmask;
    private String newNetmaskBits;

    public Calculadora(String address, String netmask, String newNetmask) {
        this.address = address.trim();
        formatMask( netmask );
        formatNewMask( newNetmask );
        calculateAddressByMask();
    }

    // GETERS

    public String getAddress() {
        return address;
    }

    public String getNetmask() {
        return netmask;
    }

    public String getNetmaskBits() {
        return netmaskBits;
    }

    public String getNewNetmask() {
        return newNetmask;
    }

    public String getNewNetmaskBits() {
        return newNetmaskBits;
    }

    // SETERS

    public void setAddress( String address ) {
        this.address = address.trim();
    }

    public void setNetmask( String netmask ) {
        this.netmask = netmask.trim();
    }

    public void setNetmaskBits( String netmaskBits ) {
        this.netmaskBits = netmaskBits.trim();
    }

    public void setNetmaskBits( Integer netmaskBits ) {
        this.netmaskBits = "" + netmaskBits;
    }

    public void setNewNetmask( String newNetmask ) {
        this.newNetmask = newNetmask.trim();
    }

    public void setNewNetmaskBits( String newNetmaskBits ) {
        this.newNetmaskBits = newNetmaskBits.trim();
    }

    public void setNewNetmaskBits( Integer newNetmaskBits ) {
        this.newNetmaskBits = "" + newNetmaskBits;
    }

    // METHODS

    private void formatMask( String mask ) {
        String maskTemp = "", maskSplit = mask;
        Integer maskBits;
        
        String[] parts = maskSplit.split( "\\." );
        if ( parts.length == 1 ) {
            this.netmaskBits = parts[0];
            maskBits = Integer.parseInt( parts[0] );
            for (int i = 0; i < 4; i++) {
                if ( maskBits == 0 ) {
                    maskTemp += "0";
                } else if ( maskBits < 8 && maskBits > 0) {
                    maskTemp += formatMaskToDecimal( maskBits );
                } else {
                    maskBits = maskBits - 8;
                    maskTemp += formatMaskToDecimal( 8 );
                }
                if ( i < 3 )
                    maskTemp += ".";
            }
            setNetmask( maskTemp );
        } else {
            maskBits = 0;
            for (int i = 0; i < 4; i++) {
                maskBits = maskBits + formatMaskToBits( Integer.parseInt( parts[i] ) );
            }

            setNetmaskBits( maskBits );
        }
    }

    private void formatNewMask( String mask ) {
        String maskTemp = "", maskSplit = mask;
        Integer maskBits;
        
        String[] parts = maskSplit.split( "\\." );
        if ( parts.length == 1 ) {
            this.newNetmaskBits = parts[0];
            maskBits = Integer.parseInt( parts[0] );
            for (int i = 0; i < 4; i++) {
                if ( maskBits == 0 ) {
                    maskTemp += "0";
                } else if ( maskBits < 8 && maskBits > 0) {
                    maskTemp += formatMaskToDecimal( maskBits );
                } else {
                    maskTemp += formatMaskToDecimal( 8 );
                }
                maskBits = maskBits - 8;

                if ( maskBits < 0 )
                    maskBits = 0;
                if ( i < 3 )
                    maskTemp += ".";
            }
            setNewNetmask( maskTemp );
        } else {
            maskBits = 0;
            for (int i = 0; i < 4; i++) {
                maskBits = maskBits + formatMaskToBits( Integer.parseInt( parts[i] ) );
            }
            setNewNetmaskBits( maskBits );
        }
    }

    public Integer calculateSubnets( String bits, String newBits ) {
        Integer bitsDifference = Integer.parseInt( newBits ) - Integer.parseInt( bits );
        return (int) Math.pow(2, bitsDifference);
    }

    public Integer calculateHosts( String bits, String newBits ) {
        Integer bitsDifference = Integer.parseInt( newBits ) - Integer.parseInt( bits ),
        bitsMask = 32 - Integer.parseInt( bits ),
        bitsMaskDifference = bitsMask - bitsDifference,
        hosts = 0;

        for (int i = (bitsMaskDifference - 1); i > 0; i--) {
            hosts = (int) (hosts + Math.pow(2, i));
        }
        
        return hosts;
    }

    public Integer calculateRange( String bits, String newBits ) {
        Integer bitsDifference = Integer.parseInt( newBits ) - Integer.parseInt( bits );
        return 256 - formatMaskToDecimal( bitsDifference );
    }

    public Integer calculateHostsTotal( Integer hosts, Integer subnets ) {
        return hosts * subnets;
    }

    private Integer formatMaskToDecimal( Integer bits ) {
        Integer decimal = 0;
        for (int i = 7; i >= ( 8 - bits ); i--) {
            decimal = (int) (decimal + Math.pow(2, i));
        }

        return decimal;
    }

    private Integer formatMaskToBits( Integer decimal ) {
        Integer bits = 0;
        
        while (decimal != 0) {
            decimal = Math.round( decimal/2 );
            bits++;
        }

        return bits;
    }

    private void calculateAddressByMask() {
        Integer wildcard = ( 32 - Integer.parseInt( this.netmaskBits ) ) / 8;
        String[] parts = this.address.split( "\\." );
        String addressFinal = "";

        for (int i = 0; i < ( 4 - wildcard ); i++) {
            addressFinal += parts[i] + ".";
        }

        for (int i = 0; i < wildcard; i++) {
            addressFinal += "0";
            if (i != ( wildcard - 1 ))
                addressFinal += ".";
        }

        setAddress( addressFinal );
    }
}
