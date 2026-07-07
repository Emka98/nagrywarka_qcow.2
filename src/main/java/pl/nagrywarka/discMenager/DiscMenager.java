package pl.nagrywarka.discMenager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Stream;

import pl.nagrywarka.distribution.Distribution;


public class DiscMenager {

    private BufferedReader execCommand(String[] command) {
        try {
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.redirectErrorStream(true);
            Process process = builder.start();
            return new BufferedReader(new InputStreamReader(process.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
        
    public ArrayList<Disc> creatDiscList() throws IOException{
        ArrayList<Disc> discList = new ArrayList<>();
        BufferedReader reader = execCommand(new String[]{"lsblk", "-o", "TRAN,NAME,SIZE,MODEL"});
        String line;
        while ((line = reader.readLine()) != null) {
            if(line.contains("usb")){
                Disc disc = new Disc();
                String[] parts = line.trim().split("\\s+");
                disc.setNode(parts[1]);
                disc.setSize(parts[2]);
                disc.setName("");
                for (int i = 3; i < parts.length; i++) {
                    disc.setName(disc.getName() + parts[i] + " ");
                }
                discList.add(disc);
            }
        }
        return discList;
    }

    public Stream<Double> clearDisc(Disc disc) throws IOException {
        BufferedReader sizeReader = execCommand(new String[]{"lsblk", "-b", "-d", "-n", "-o", "SIZE", "/dev/" + disc.getNode()});        
        String sizeLine = sizeReader.readLine();
        sizeReader.close();

        if (sizeLine == null || sizeLine.trim().isEmpty()) {
            throw new IOException("Nie udało się odczytać rozmiaru dysku dla /dev/" + disc.getNode());
        }

        final double discSize = Double.parseDouble(sizeLine.trim().split("\\s+")[0]);

        BufferedReader ddReader = execCommand(new String[]{"sudo", "dd", "if=/dev/zero", "of=/dev/" + disc.getNode(), "bs=4M","status=progress"});
        // BufferedReader ddReader = execCommand(new String[]{"sudo", "dcfldd", "if=/dev/zero", "of=/dev/" + disc.getNode(),"bs=4M", "status=progress"});

        return ddReader.lines()
            .map(String::trim)
            .map(l -> l.split("\\s+"))
            .filter(parts -> parts.length > 0 && !parts[0].isEmpty())
            .map(parts -> {
                try {
                    return Double.parseDouble(parts[0]) / discSize;
                } catch (NumberFormatException e) {
                    return null;
                }
            })
            .filter(Objects::nonNull);
    }

    public Stream<Double> recordDisc(Disc disc, Distribution distribution) throws IOException{

        BufferedReader ddReader = execCommand(new String[]{"sudo", "qemu-img", "convert", "-p", distribution.getFilePath(),
            "-O", "raw", "/dev/" + disc.getNode()});

        return ddReader.lines()
                .map(line -> line.replace("(", "").replace("/100%)", "").trim())
                .filter(line -> !line.isEmpty()) 
                .mapToDouble(Double::parseDouble)
                .boxed();
    }
}
