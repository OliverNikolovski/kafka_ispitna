package src;

import java.util.Locale;

public class VmMessage {
    private VmType type;
    private int ram;
    private int cores;
    private double executionTime;

    public VmMessage() {
    }

    public VmMessage(VmType type, int ram, int cores, double executionTime) {
        this.type = type;
        this.ram = ram;
        this.cores = cores;
        this.executionTime = executionTime;
    }

    public static VmMessage of(String input) {
        String[] parts = input.split(":");
        VmType type = VmType.valueOf(parts[0].toUpperCase(Locale.ROOT));
        int ram = Integer.parseInt(parts[1]);
        int cores = Integer.parseInt(parts[2]);
        double executionTime = Double.parseDouble(parts[3]);
        return new VmMessage(type, ram, cores, executionTime);
    }

    public VmType getType() {
        return type;
    }

    public void setType(VmType type) {
        this.type = type;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public int getCores() {
        return cores;
    }

    public void setCores(int cores) {
        this.cores = cores;
    }

    public double getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(int executionTime) {
        this.executionTime = executionTime;
    }

    @Override
    public String toString() {
        return "VmMessage{" +
                "type=" + type +
                ", ram=" + ram +
                ", cores=" + cores +
                ", executionTime=" + executionTime +
                '}';
    }
}
