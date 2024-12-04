# Games

Code for programming exercises & online games

# Dependencies

For java:
* Sylordis' [java commons](https://github.com/Sylordis/commons)

# Run

## Java

From `modules/java/target/classes` directory:

```bash
java -cp '.;<path-to-commons-jar>' <class> [arguments] < ../../../../data/<input-file>
```

## Python

From `modules/python`

Update the `games/bin/app.py` (dynamic load still TBD)

```bash
python -m games ../../data/<input-file>
```