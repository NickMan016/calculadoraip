import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        String addressTemp, netmaskTemp, newNetmaskTemp;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("\nIntroduce la Direcci\u00f3n IP: ");
        addressTemp = reader.readLine();
        String[] addressSplit = addressTemp.split("/");

        if (addressSplit.length == 1 || addressSplit[1] == "") {
            System.out.print("\nIntroduce la M\u00e1scara de Subred (bits): ");
            netmaskTemp = reader.readLine();
        } else {
            netmaskTemp = addressSplit[1];
        }
        addressTemp = addressSplit[0];

        System.out.print("\nIntroduce la nueva M\u00e1scara de Subred (bits): ");
        newNetmaskTemp = reader.readLine();

        Calculadora calculadora = new Calculadora(addressTemp, netmaskTemp, newNetmaskTemp);
        Network network = new Network();
        network.setNetwork(calculadora.getAddress());
        network.setMask(calculadora.getNetmask());
        network.calculateClassNetwork();
        network.calculateNetwork();

        System.out.println("\n\nNetwork Class " + network.getClassNetwork());
        System.out.println("Address: \t" + network.getNetwork());
        System.out.println("Netmask: \t" + network.getMask());
        System.out.println("Start Host: \t" + network.getStartHost());
        System.out.println("End Host: \t" + network.getEndHost());
        System.out.println("Broadcast: \t" + network.getBroadcast());
        System.out.println("Hosts: \t\t" + network.getHosts() + "\n\n");

        if (!calculadora.getNetmaskBits().equals(calculadora.getNewNetmaskBits())) {
            Integer numberSubnets = calculadora.calculateSubnets(calculadora.getNetmaskBits(), calculadora.getNewNetmaskBits()),
                    hosts = calculadora.calculateHosts(calculadora.getNetmaskBits(), calculadora.getNewNetmaskBits()),
                    hostsTotal = calculadora.calculateHostsTotal(hosts, numberSubnets),
                    range = calculadora.calculateRange(calculadora.getNetmaskBits(), calculadora.getNewNetmaskBits());
            Subnet[] subnets = new Subnet[numberSubnets];
            for (int i = 0; i < numberSubnets; i++) {
                subnets[i] = new Subnet((i + 1), range);
                subnets[i].setClassNetwork(network.getClassNetwork());
                subnets[i].setNetwork(network.getNetwork());
                subnets[i].setMask(network.getMask());

                subnets[i].formatSubnet();
                
                System.out.println("------ Subnet " + (i + 1) + " ------");
                System.out.println("Address: \t" + subnets[i].getNetwork());
                System.out.println("Start Host: \t" + subnets[i].getStartHost());
                System.out.println("End Host: \t" + subnets[i].getEndHost());
                System.out.println("Broadcast: \t" + subnets[i].getBroadcast());
                System.out.println("Hosts: \t\t" + (hostsTotal / numberSubnets) + "\n\n");

            }
            
            System.out.println("\nSubnets: \t" + numberSubnets);
            System.out.println("Hosts: \t\t" + hostsTotal);
        }
    }
}