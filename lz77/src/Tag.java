public class Tag {
    int offset;
    int length;
    char nextChar;

    Tag(int offset, int length, char nextChar){
        this.offset = offset;
        this.length = length;
        this.nextChar = nextChar;
    }

    @Override
    public String toString() {
        return "< " + this.offset + ", " + this.length + ", " + this.nextChar + " >";
    }
}
