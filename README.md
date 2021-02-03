# Lesson-1-Assignment

The  Lesson1IOStream class  implements methods for writing and reading 10,000
Employee  objects  stored  as  binary,  text,  or  serialized  objects.  When
invoked,  the program expects one of the following four arguments: --help, --
binary,   --object   or   --text.   Invoking   Lesson1IOStream   without   an
argument  generates  an  error.  Invoking  Lesson1IOStream  with  an  invalid
argument  also  generates an  error. In  either case, an  error message and a
syntax summary are displayed on the console.

Invoking Lesson1IOStream with the "--help" argument displays a command syntax
diagram on the console. Invoking Lesson1IOStream with the "--binary" argument
first  writes then  reads 10,000  Employee objects  as binary  data. Invoking
Lesson1IOStream  with the  "--object" argument first writes then reads 10,000
Employee  objects  using object  serialization. Invoking Lesson1IOStream with
the "--text" argument first writes then reads 10,000 Employee objects as text
data. Data retrieved during the read operations is displayed on the console.
