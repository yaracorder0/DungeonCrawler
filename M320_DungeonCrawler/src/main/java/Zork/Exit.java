package Zork;

public class Exit {

    private String dir;
    private Room src;
    private Room dest;
    private boolean isLocked;
    private Key key;

    public Exit(String dir, Room src, Room dest) {
        this.dir = dir;
        this.src = src;
        this.dest = dest;
        this.isLocked = false;
        this.key = null;
    }

    public Exit(String dir, Room src, Room dest, Key key) {
        this.dir = dir;
        this.src = src;
        this.dest = dest;
        this.isLocked = true;
        this.key = key;
    }

    public String getDir() {
        return dir;
    }

    public Room getSrc() {
        return src;
    }

    public Room getDest() {
        return dest;
    }

    public boolean isLocked(){
        return isLocked;
    }

    public void unlock(Key key) {
        if (this.key != null && this.key.equals(key)) {
            this.isLocked = false;
        }
    }


    public class NoExitException extends Exception {
        public NoExitException(String errorMessage) {
            super(errorMessage);
        }
    }
}
