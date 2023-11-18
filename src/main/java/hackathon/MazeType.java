package hackathon;

public enum MazeType {
    EMPTY,
    WALL,
    ENTRY,
    EXIT,
    RIGHT_TO_UP,
    RIGHT_TO_DOWN,
    RIGHT_TO_UP_FIX,
    RIGHT_TO_DOWN_FIX;

    static MazeType getType(char myChar) {
        for (MazeType type : MazeType.values()) {
            if (type.ordinal() == Integer.parseInt(String.valueOf(myChar))) {
                return type;
            }
        }
        return MazeType.EMPTY;
    }

}
