package EDU.UCSD.Extension;

import java.io.*;
import java.time.*;

class Employee implements Serializable {
    private String name;
    private double salary;
    private LocalDate hireDate;

    public Employee() {
    }

    public Employee(String name, double salary, int year, int month, int day) {
        this.name = name;
        this.salary = salary;
        hireDate = LocalDate.of(year, month, day);
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public LocalDate getHireDay() {
        return hireDate;
    }

    /**
     * Raises the salary of this employee.
     *
     * @byPercent the percentage of the raise
     */
    public void raiseSalary(double byPercent) {
        double raise = salary * byPercent / 100;
        salary += raise;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "[name=" + name
                + ",salary=" + salary
                + ",hireDate=" + hireDate
                + "]";
    }
}

class Manager extends Employee implements Serializable {
    private Employee secretary;

    /**
     * Constructs a Manager without a secretary
     *
     * @param name   the employee's name
     * @param salary the salary
     * @param year   the hire year
     * @param month  the hire month
     * @param day    the hire day
     */
    public Manager(String name, double salary, int year, int month, int day) {
        super(name, salary, year, month, day);
        this.secretary = null;
    }

    /**
     * Assigns a secretary to the manager.
     *
     * @param secretary the secretary
     */
    public void setSecretary(Employee secretary) {
        this.secretary = secretary;
    }

    @Override
    public String toString() {
        return super.toString() + "[secretary=" + secretary + "]";
    }
}


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
        Employee employee = new Employee("Edgar Cole", 75_000, 2021, 6, 24);
        Manager manager = new Manager("Grant Albright", 90_000, 2000, 5, 1);
        manager.setSecretary(employee);
        System.out.println(employee);
        System.out.println(manager);
        String argument = "";
        try {
            argument = arguments[0];
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            System.err.println("Oops, an argument is required!");
            lesson1IOStream.syntaxSummary();
            System.exit(1);
        }

        switch (argument) {
            case "--help":
                lesson1IOStream.syntaxSummary();
                break;
            case "--object":
                writeObjectStream("Employee.dat", 10_000);
                readObjectStream("Employee.dat");
                break;
            case "--binary":
                System.out.println("Binary I/O is not yet implemented.");
                break;
            case "--text":
                System.out.println("Text I/O is not yet implemented.");
                break;
            default:
                System.err.printf("%n\"%s\" is not a valid argument!%n", argument);
                lesson1IOStream.syntaxSummary();
                System.exit(1);
        }
    }

    private static void readObjectStream(String filename) {

        try (var in = new ObjectInputStream(new FileInputStream(filename))) {
            // retrieve all records into a new array

            var newStaff = (Employee[]) in.readObject();

            // raise secretary's salary
            newStaff[1].raiseSalary(10);

            // print the newly read employee records
            for (Employee employee : newStaff)
                System.out.println(employee);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException | ClassNotFoundException ioException) {
            ioException.printStackTrace();
        }
    }

    private static void writeObjectStream(String filename, int sampleSize) {
        var staff = new Employee[sampleSize];
        for (int index = 0; index < staff.length; index++) {
            staff[index] = new Employee("Edgar Cole", 75_000, 2021, 1, 1);
        }

        // save all employee records to the file employee.dat
        try (var out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(staff);
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }


    }
}
