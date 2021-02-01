package EDU.UCSD.Extension;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.util.Scanner;

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

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @return
     */
    public double getSalary() {
        return salary;
    }

    /**
     * @return
     */
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

    /**
     *
     */
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

    public static void main(String[] arguments) throws IOException {

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

        switch (argument) {
            case "--help":
                lesson1IOStream.syntaxSummary();
                break;
            case "--object":
                writeObjectStream("Employee.dat", 10_000);
                readObjectStream("Employee.dat");
                break;
            case "--binary":
                writeBinaryStream("Employee.dat", 10_000);
                readBinaryStream("Employee.dat");
                break;
            case "--text":
                writeTextStream("Employee.dat", 10_000);
                readTextStream("Employee.dat");
                break;
            default:
                System.err.printf("%n\"%s\" is not a valid argument!%n", argument);
                lesson1IOStream.syntaxSummary();
                System.exit(1);
        }

    }

    /**
     * @param filename
     */
    private static void readBinaryStream(String filename) {

        try (var in = new ObjectInputStream(new FileInputStream(filename))) {
            // retrieve all records into a new array

            var newStaff = (Employee[]) in.readObject();

            // raise secretary's salary
            newStaff[1].raiseSalary(10);

            // print the newly read employee records
            for (Employee e : newStaff)
                System.out.println(e);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException | ClassNotFoundException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * @param filename
     * @param sampleSize
     */
    private static void writeBinaryStream(String filename, int sampleSize) {

        // save all employee records to the file Employee.dat
        try (var out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(generateData(10_000));
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * @param sampleSize
     * @return
     */
    private static Employee[] generateData(int sampleSize) {
        Employee[] temp = new Employee[sampleSize];
        for (int index = 0; index < sampleSize; index++) {
            temp[index] = new Employee("Edgar Cole", 75_000, 2021, 1, 1);
        }
        return temp;
    }

    /**
     * Writes employee data to a print writer
     *
     * @param out the print writer
     */
    public static void writeEmployee(PrintWriter out, Employee employee) {
        out.println(employee.getName() + "|" + employee.getSalary() + "|" + employee.getHireDay());
    }

    /**
     * Writes all employees in an array to a print writer
     *
     * @param employees an array of employees
     * @param out       a print writer
     */
    private static void writeData(Employee[] employees, PrintWriter out)
            throws IOException {
        // write number of employees
        out.println(employees.length);

        for (Employee employee : employees) {
            writeEmployee(out, employee);
        }
    }

    /**
     * Reads employee data from a buffered reader
     *
     * @param in the scanner
     */
    public static Employee readEmployee(Scanner in) {
        String line = in.nextLine();
        String[] tokens = line.split("\\|");
        String name = tokens[0];
        double salary = Double.parseDouble(tokens[1]);
        LocalDate hireDate = LocalDate.parse(tokens[2]);
        int year = hireDate.getYear();
        int month = hireDate.getMonthValue();
        int day = hireDate.getDayOfMonth();
        return new Employee(name, salary, year, month, day);
    }

    /**
     * Reads an array of employees from a scanner
     *
     * @param in the scanner
     * @return the array of employees
     */
    private static Employee[] readData(Scanner in) {
        // retrieve the array size
        int n = in.nextInt();
        in.nextLine(); // consume newline

        var employees = new Employee[n];
        for (int i = 0; i < n; i++) {
            employees[i] = readEmployee(in);
        }
        return employees;
    }

    private static void readTextStream(String filename) {

        try (var in = new Scanner(new FileInputStream(filename), "UTF-8")) {
            Employee[] newStaff = readData(in);
            // print the newly read employee records
            for (Employee employee : newStaff) {
                System.out.println(employee);
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

    /**
     * @param filename
     * @param sampleSize
     * @throws IOException
     */
    private static void writeTextStream(String filename, int sampleSize) throws IOException {

        // save all employee records to the file Employee.dat
        try (var out = new PrintWriter(filename, StandardCharsets.UTF_8)) {
            writeData(generateData(sampleSize), out);
        }
    }

    /**
     * @param filename
     */
    private static void readObjectStream(String filename) {
        try (var in = new ObjectInputStream(new FileInputStream(filename))) {
            // retrieve all records into a new array
            var staff = (Employee[]) in.readObject();
            // raise secretary's salary
            staff[1].raiseSalary(10);
            for (Employee employee : staff)
                System.out.println(employee);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException | ClassNotFoundException ioException) {
            ioException.printStackTrace();
        }
    }

    private static void writeObjectStream(String filename, int sampleSize) {
        // save all employee records to the file Employee.dat
        try (var out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(generateData(sampleSize));
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }

    }
}
