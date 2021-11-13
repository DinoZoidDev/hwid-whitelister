package me.dinozoid.whitelister;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LicenceUtil {

    public static String licence() {
        SystemInfo systemInfo = new SystemInfo();
        OperatingSystem operatingSystem = systemInfo.getOperatingSystem();
        HardwareAbstractionLayer hardwareAbstractionLayer = systemInfo.getHardware();
        CentralProcessor centralProcessor = hardwareAbstractionLayer.getProcessor();

        String osManufacturer = operatingSystem.getManufacturer();
        String family = operatingSystem.getFamily();
        String productName = "Strife";
        String manufacturer = baseboardFetch("Manufacturer");
        String product = baseboardFetch("Product");
        String processorIdentifier = centralProcessor.getProcessorIdentifier().getIdentifier();
        int processors = centralProcessor.getPhysicalProcessorCount();

        char[] delimiter = new char[]{'@', '#', '|', '/', '?', '<'};

        String licence = osManufacturer +
                delimiter[0] +
                family +
                delimiter[1] +
                productName +
                delimiter[2] +
                manufacturer +
                delimiter[3] +
                product +
                delimiter[4] +
                processorIdentifier +
                delimiter[5] +
                processors;

        String hashedLicence = null;
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(licence.getBytes(StandardCharsets.UTF_8));
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            hashedLicence = bigInt.toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashedLicence;
    }

    public static String baseboardFetch(String param) {
        StringBuilder product = new StringBuilder();
        String[] command = {"wmic", "baseboard", "get", param};
        try {
            Process process = Runtime.getRuntime().exec(command);
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = reader.readLine()) != null) {
                product.append(line);
            }
            process.waitFor();
            reader.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return product.toString().replaceAll(param, "").trim();
    }

}
