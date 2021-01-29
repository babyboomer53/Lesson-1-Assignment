package EDU.UCSD.Extension;

public class Lesson1IOStream {

    public void syntaxSummary() {
        var commandName = getClass().getSimpleName();
        System.out.printf("%n%-7s%-16s%-9s%s%n%-7s%-16s%-9s%s%n%-7s%-16s%-9s%s%n%-7s%-16s%-9s%s%n",
                "Usage:",
                commandName,
                "--help ",
                "# Displays this command syntax summary",
                "",
                commandName,
                "--text",
                "# writes/reads as text file and displays results on console",
                "",
                commandName,
                "--binary",
                "# writes/reads as binary file and displays results on console",
                "",
                commandName,
                "--object",
                "# writes/reads as object file and displays results on console");
    }

    public static void main(String[] arguments) {

        String regex = "^--[bhot]?[ieb]?[nljx]?[apet]?[rc]*[yt]*$";

        Lesson1IOStream lesson1IOStream = new Lesson1IOStream();
        String argument = "";
        try {
            argument = arguments[0];
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            System.err.println("Oops, an argument is required!");
            lesson1IOStream.syntaxSummary();
            System.exit(1);
        }
        for (String string : arguments) {
            System.out.printf("\"%s\" ", string);
        }
        System.out.println();

        switch (argument.substring(2)) {
            case "help":
                lesson1IOStream.syntaxSummary();
                break;
            case "object":
                System.out.println("Object I/O is not yet implemented.");
                break;
            case "binary":
                System.out.println("Binary I/O is not yet implemented.");
                break;
            case "text":
                System.out.println("Text I/O is not yet implemented.");
                break;
            default:
                System.out.printf("%n\"%s\" is not a valid argument!%n",argument);
                lesson1IOStream.syntaxSummary();
                System.exit(1);
        }
    }
}
