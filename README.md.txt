# Custom JSON Parser

A from-scratch, educational JSON parser built in Java. This project demonstrates the fundamental principles of compiler design, including lexical analysis (tokenizing) and syntactic analysis (parsing), using a hand-written recursive descent parser.

The parser adheres to the standard JSON specification, converting a JSON-formatted string into a `java.util.Map` (for JSON objects) and `java.util.List` (for JSON arrays).

## Table of Contents
- [Features](#features)
- [Project Structure](#project-structure)
- [How It Works](#how-it-works)
  - [Phase 1: Lexical Analysis (Tokenizing)](#phase-1-lexical-analysis-tokenizing)
  - [Phase 2: Syntactic Analysis (Parsing)](#phase-2-syntactic-analysis-parsing)
- [Grammar](#grammar)
- [How to Use](#how-to-use)
- [Future Improvements](#future-improvements)

## Features
- **No External Dependencies:** Built using only standard Java libraries.
- **Full JSON Support:** Parses all standard JSON data types:
  - Objects (`{...}`)
  - Arrays (`[...]`)
  - Strings (`"..."`), with support for escape characters.
  - Numbers (integers and floating-point).
  - Booleans (`true` and `false`).
  - `null`.
- **Clean Architecture:** Follows the Single Responsibility Principle with a clear separation between the tokenizer and the parser.
- **Robust Error Handling:** Provides meaningful `ParseException` messages for malformed JSON.

## Project Structure
The project is organized into logical packages to separate concerns:

```
src/main/java/org/example/
├── lexer/         # (Future/Alternative) Lower-level lexical analysis components.
├── parser/        # Contains the Parser interface and the main JsonParser implementation.
├── tokenizer/     # Converts the raw string into a stream of tokens.
└── utility/       # Helper classes for tasks like reading files.
```

- **`tokenizer` Package**:
  - `JSONTokenizer`: Implements the `Tokenizer` interface. Its main job is to scan the raw JSON string and convert it into a `List<Token>`.
  - `Token`: Represents a single lexical unit (e.g., a left brace `{`, a string literal, a number).
  - `JSONTokens`: An `enum` defining all possible token types.

- **`parser` Package**:
  - `Parser`: A generic interface for parsing, designed to be reusable for other formats.
  - `JsonParser`: The main class that implements the parsing logic. It takes a list of tokens and recursively builds the corresponding Java `Map` and `List` objects.
  - `ParseException`: A custom exception for handling syntax errors.

## How It Works
The parsing process is a classic two-stage pipeline, common in compiler and interpreter design.

### Phase 1: Lexical Analysis (Tokenizing)
The `JSONTokenizer` scans the input `String` character by character. It identifies sequences of characters that form meaningful "words" or **tokens** according to the JSON specification.

For example, the string `{"key": 123}` is broken down into the following sequence of tokens:
1. `TOKEN_OPEN_CURLYBRACKET` (`{`)
2. `TOKEN_STRING` (`key`)
3. `TOKEN_COLON` (`:`)
4. `TOKEN_NUMBER` (`123`)
5. `TOKEN_CLOSE_CURLYBRACKET` (`}`)

Whitespace is ignored during this phase. This process simplifies the next stage, as the parser no longer has to deal with individual characters.

### Phase 2: Syntactic Analysis (Parsing)
The `JsonParser` takes the list of tokens generated in Phase 1 and validates them against a formal grammar. This project uses a **Recursive Descent Parser**, which is a top-down parsing technique where a set of mutually recursive methods is used to process the input.

- **LL(1) Grammar**: The JSON grammar is an **LL(1)** grammar, which means it can be parsed by looking ahead at just **one** token at a time to make a decision, without ever needing to backtrack. This makes the parser fast and efficient.
- **Recursive Methods**: The parser has methods like `parseObject()`, `parseArray()`, and `parseValue()`. These methods call each other based on the current token, mirroring the recursive nature of the JSON grammar (e.g., an object can contain another object).
- **Object Creation**: As the parser successfully validates a grammar rule, it simultaneously builds the corresponding Java object (`Map` or `List`). If at any point a token is found that violates the grammar, a `ParseException` is thrown.

## Grammar
The parser implements the following EBNF-style (Extended Backus-Naur Form) grammar:

- **`object`** `::=` `{` `( pair ( "," pair )* )?` `}`
- **`pair`** `::=` `string` `:` `value`
- **`array`** `::=` `[` `( value ( "," value )* )?` `]`
- **`value`** `::=` `string` | `number` | `object` | `array` | `true` | `false` | `null`

## How to Use
The entry point is the `JsonParser` class. You can read a JSON file or use a string directly, and then pass it to the parser's `parse()` method.

```java
import org.example.parser.JsonParser;
import org.example.parser.ParseException;
import org.example.tokenizer.JSONTokenizer;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String jsonString = "{\"name\": \"John Doe\", \"age\": 30, \"isStudent\": false, \"courses\": [\"History\", \"Math\"]}";

        // 1. Instantiate the tokenizer and parser
        JSONTokenizer tokenizer = new JSONTokenizer();
        JsonParser parser = new JsonParser(tokenizer);

        try {
            // 2. Parse the string
            Map<String, Object> jsonData = parser.parse(jsonString);

            // 3. Work with the resulting Java Map
            System.out.println("Successfully parsed JSON!");
            System.out.println("Name: " + jsonData.get("name"));
            System.out.println("Age: " + jsonData.get("age"));
            System.out.println("Courses: " + jsonData.get("courses"));

        } catch (ParseException e) {
            System.err.println("Failed to parse JSON: " + e.getMessage());
        }
    }
}
```

## Future Improvements
This project serves as a strong foundation. Potential enhancements include:
- [ ] **Performance Optimization:** Use a streaming tokenizer that doesn't read the whole string into memory.
- [ ] **Enhanced Error Reporting:** Provide line and column numbers in `ParseException`.
- [ ] **JSON Generation:** Add functionality to convert a Java `Map` back into a JSON string (serialization).
- [ ] **Data Binding:** Implement an optional layer to automatically map JSON objects to custom Java POJOs (Plain Old Java Objects).