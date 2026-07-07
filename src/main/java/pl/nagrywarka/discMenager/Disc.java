package pl.nagrywarka.discMenager;

public class Disc{
    
    private String node;
    private String name;
    private String size;

    public Disc() {
    }

    public String getNode() {
        return node;
    }
    public void setNode(String node) {
        this.node = node;
    }
    public String getSize() {
        return size;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + " (" + size + ")"; 
    }
}
